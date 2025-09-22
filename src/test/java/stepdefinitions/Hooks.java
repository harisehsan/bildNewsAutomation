package stepdefinitions;

import cucumber.api.Scenario;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import util.BaseDriver;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class Hooks extends BaseDriver {

    private BaseDriver base;


    public Hooks(BaseDriver base) {
        this.base = base;
    }


    private static List<String> runCmd(String... cmd) throws Exception {
        Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }
        }
        return lines;
    }

    @Before
    public void InitializeTest() throws Exception {
        List<String> devices = runCmd("adb", "devices");
        String firstUdid = devices.stream()
                .filter(l -> l.endsWith("device") && !l.startsWith("List"))
                .map(l -> l.split("\\s+")[0])
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Android device connected"));

        List<String> versionLines =
                runCmd("adb", "-s", firstUdid, "shell", "getprop", "ro.build.version.release");
        String androidVersion = versionLines.isEmpty() ? "unknown" : versionLines.get(0);

        List<String> modelLines =
                runCmd("adb", "-s", firstUdid, "shell", "getprop", "ro.product.model");
        String deviceName = modelLines.isEmpty() ? "Android Device" : modelLines.get(0);

        System.out.println("Using device: " + deviceName + " (" + firstUdid + "), Android " + androidVersion);

        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid(firstUdid)
                .setDeviceName(deviceName)
                .setPlatformName("Android")
                .setPlatformVersion(androidVersion)
                .setAutomationName("UiAutomator2")
                .setAppPackage("com.netbiscuits.bild.android")
                .setAppActivity("de.bild.android.app.MainActivity")
                .autoGrantPermissions();

        androidDriver = new AndroidDriver(new URL("http://192.168.2.40:4723/"), options);
        startVideo();
        }

    @After
    public void TearDownTest(Scenario scenario) throws Exception {;
        if (scenario.isFailed()) {
            takeScreenshot(scenario);
        }
        stopVideoAndAttach(scenario);
        androidDriver.quit();
    }

    private void takeScreenshot(Scenario scenario) throws IOException {
        byte[] screenshot = ((TakesScreenshot) androidDriver)
                .getScreenshotAs(OutputType.BYTES);
        String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9-_\\.]", "_");
        Path out = Paths.get("screenshots", safeName + ".png");
        Files.createDirectories(out.getParent());
        Files.write(out, screenshot);
        Allure.addAttachment("Failure Screenshot",
           new ByteArrayInputStream(screenshot));
    }

    private void startVideo() {
        if (androidDriver != null) {
            androidDriver.startRecordingScreen(
                    AndroidStartScreenRecordingOptions.startScreenRecordingOptions()
                            .withTimeLimit(Duration.ofMinutes(10))
                            .withBitRate(5_000_000)
                            .withVideoSize("1280x720")
                            .enableBugReport()
            );
        }
    }

    private void stopVideoAndAttach(Scenario scenario) throws Exception {
        String b64 = null;
        if (androidDriver != null) {
            b64 = androidDriver.stopRecordingScreen();
        }

        if (b64 != null && !b64.isEmpty()) {
            byte[] video = Base64.getDecoder().decode(b64);
            String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9-_\\.]", "_");
            Path out = Paths.get("videos", safeName + ".mp4");
            Files.createDirectories(out.getParent());
            Files.write(out, video);
            Allure.addAttachment("Execution Video", "video/mp4",
                    new ByteArrayInputStream(video), ".mp4");
        }
    }
}