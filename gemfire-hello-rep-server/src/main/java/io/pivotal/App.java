package io.pivotal;

import org.apache.geode.distributed.LocatorLauncher;
import org.apache.geode.distributed.ServerLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;

@SpringBootApplication
@CacheServerApplication(name = "AccessingGemFireDataRestFunctionApplication", logLevel = "error")
public class App implements ApplicationRunner {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		//startLocator("locator", 13490);
		//startServer("server2", 40406);
	}

	private void startLocator(String locatorName, int locatorPort) { //13489
		LocatorLauncher locatorLauncher = new LocatorLauncher.Builder().setMemberName(locatorName).setPort(locatorPort).build();

		try {
			locatorLauncher.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void startServer (String serverName, int serverPort) { //40405
		ServerLauncher serverLauncher = new ServerLauncher.Builder().setMemberName(serverName).setServerPort(serverPort)
				.set("jmx-manager", "true").set("jmx-manager-start", "true").build();
		
		try {
			serverLauncher.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
