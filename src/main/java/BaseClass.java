import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class BaseClass {

    public static AndroidDriver<AndroidElement> driver;
    Properties prop;
    private String appiumServerHost;
    private String appiumServerPort;


    @BeforeSuite
    public void loadConfiguration(){
        prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("src\\main\\resources\\Configuration.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public AndroidDriver<AndroidElement> addCapabilityToDriver(){

        appiumServerHost = prop.get("appiumServerHost").toString();
        appiumServerPort = prop.get("appiumServerPort").toString();

        File f = new File("src\\main\\resources\\apkFiles\\");
        File fs = new File(f, prop.getProperty("appFileName").toString());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,prop.get("emulatorName").toString());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,prop.get("automationName").toString());
        capabilities.setCapability(MobileCapabilityType.APP,fs.getAbsolutePath());

        try {
            driver = new AndroidDriver<AndroidElement>(new URL("http://" + appiumServerHost + ":" + appiumServerPort +"/wd/hub"),
                    capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;

    }


}
