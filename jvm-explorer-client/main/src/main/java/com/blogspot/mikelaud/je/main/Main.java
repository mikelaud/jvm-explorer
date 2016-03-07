package com.blogspot.mikelaud.je.main;

import com.blogspot.mikelaud.je.agent.loader.AgentLoaderModule;
import com.blogspot.mikelaud.je.agent.loader.common.AgentSource;
import com.blogspot.mikelaud.je.agent.loader.source.FileSource;
import com.blogspot.mikelaud.je.mvc.MvcController;
import com.blogspot.mikelaud.je.ssh.SshModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage aStage) throws Exception {
		Injector injector = Guice.createInjector(new MainModule());
		MvcController controller = injector.getInstance(MvcController.class);
		controller.getModel().setParameters(getParameters());
		controller.getModel().setStage(aStage);
		controller.showApplication();
	}

	public static void main(String[] args) {
		try {
			Injector injector = Guice.createInjector(new AgentLoaderModule(), new SshModule());
			AgentSource as = injector.getInstance(AgentSource.class);
			FileSource fs = as.getBios();
			System.out.println("agent source: " + as);
			System.out.println("bios source : " + fs);
			System.out.println("bios content: " + fs.takeContent());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		//Application.launch(args);
	}

}
