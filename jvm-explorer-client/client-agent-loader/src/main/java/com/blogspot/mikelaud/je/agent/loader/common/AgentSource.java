package com.blogspot.mikelaud.je.agent.loader.common;

import com.blogspot.mikelaud.je.agent.loader.source.FileSource;

public interface AgentSource {

	FileSource get(AgentSourceType aSourceType);
	//
	FileSource getBios();
	FileSource getHead();
	FileSource getBody();

}
