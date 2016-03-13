package com.blogspot.mikelaud.je.ui.jvm;

import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;

import javafx.scene.layout.Pane;

public interface UiJvm {

	String getName();
	Pane getPane();
	//
	String getHost();
	void setJvmList(Stream<JvmIdentity> aJvmList);

}
