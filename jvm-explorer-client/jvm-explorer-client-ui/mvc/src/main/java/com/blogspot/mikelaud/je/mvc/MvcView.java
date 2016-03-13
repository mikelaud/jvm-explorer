package com.blogspot.mikelaud.je.mvc;

import java.util.stream.Stream;

import com.blogspot.mikelaud.je.agent.bios.domain.JvmIdentity;
import com.blogspot.mikelaud.je.domain.pojo.DomainType;

public interface MvcView {

	void show();
	void showCode(DomainType aType);
	//
	String getJvmHost();
	void setJvmList(Stream<JvmIdentity> aJvmList);

}
