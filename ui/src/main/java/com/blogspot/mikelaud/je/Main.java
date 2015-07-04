package com.blogspot.mikelaud.je;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage aWindow) throws Exception {
		ProgramView programView = new ProgramView(aWindow);
		programView.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
	
}
