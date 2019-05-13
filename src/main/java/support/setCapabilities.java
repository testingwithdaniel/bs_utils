package support;

import org.openqa.selenium.remote.DesiredCapabilities;

public class setCapabilities {
    public DesiredCapabilities setDesCaps(String[] args,DesiredCapabilities caps) {
        for(int i=0;i<args.length;i++) {
            try {
                String cap = args[i].substring(0,args[i].indexOf(" "));
                caps.setCapability(cap, args[i].substring(args[i].indexOf(cap+" ")+cap.length()+1, args[i].length()).trim());
            }catch(Exception e) {}
			/*if(args[i].contains("browserName ")) {
				caps.setCapability("browserName", args[i].substring(args[i].indexOf("browserName ")+12, args[i].length()).trim());
			}
			if(args[i].contains("browser ")) {
				caps.setCapability("browser", args[i].substring(args[i].indexOf("browser ")+8, args[i].length()).trim());
			}
			if(args[i].contains("browserVersion ")) {
				caps.setCapability("browserVersion", args[i].substring(args[i].indexOf("browserVersion ")+15, args[i].length()).trim());
			}
			if(args[i].contains("browser_version ")) {
				caps.setCapability("browser_version", args[i].substring(args[i].indexOf("browser_version ")+16, args[i].length()).trim());
			}
			if(args[i].contains("device ")) {
				caps.setCapability("device", args[i].substring(args[i].indexOf("device ")+7, args[i].length()).trim());
			}
			if(args[i].contains("os ")) {
				caps.setCapability("os", args[i].substring(args[i].indexOf("os ")+3, args[i].length()).trim());
			}
			if(args[i].contains("os_version ")) {
				caps.setCapability("os_version", args[i].substring(args[i].indexOf("os_version ")+11, args[i].length()).trim());
			}
			if(args[i].contains("realMobile ")) {
				caps.setCapability("realMobile", args[i].substring(args[i].indexOf("realMobile ")+11, args[i].length()).trim());
			}
			if(args[i].contains("acceptSslCerts")) {
				caps.setCapability("acceptSslCerts", args[i+1]);
			}
			if(args[i].contains("browserstack.networkLogs")) {
				caps.setCapability("browserstack.networkLogs", args[i+1]);
			}
			if(args[i].contains("browserstack.local")) {
				caps.setCapability("browserstack.local", args[i+1]);
			}
			if(args[i].contains("browserstack.debug")) {
				caps.setCapability("browserstack.debug", args[i+1]);
			}
			if(args[i].contains("browserstack.autoWait")) {
				caps.setCapability("browserstack.autoWait", args[i+1]);
			}*/
        }
        caps.setCapability("build", "Test Build v1.0");
        return caps;
    }
}