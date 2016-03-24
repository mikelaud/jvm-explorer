package com.blogspot.mikelaud.je.main;

import java.awt.GraphicsEnvironment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;

public class Main {

	private final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private final String[] ARGS;

	private void runCli() {
		LOGGER.info("Console mode: done.");
	}

	private void runGui() {
		Application.launch(MainApplication.class, ARGS);
	}

	private boolean isCliMode() {
		return ARGS.length > 0 || GraphicsEnvironment.isHeadless();
	}

	private void run() {
		if (isCliMode()) runCli(); else runGui();
	}

	public Main(String[] aArgs) {
		ARGS = aArgs;
	}

	public static void main(String[] args) {
		new Main(args).run();
	}

}
