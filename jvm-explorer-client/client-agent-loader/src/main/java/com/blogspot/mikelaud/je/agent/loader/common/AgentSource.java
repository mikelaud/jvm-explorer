package com.blogspot.mikelaud.je.agent.loader.common;

import com.blogspot.mikelaud.je.common.file_source.FileSource;

public interface AgentSource {

	FileSource get(AgentSourceType aSourceType);
	//
	FileSource getBios();
	FileSource getHead();
	FileSource getBody();

}
