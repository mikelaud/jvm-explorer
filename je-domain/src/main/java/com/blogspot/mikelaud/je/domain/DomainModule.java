package com.blogspot.mikelaud.je.domain;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class DomainModule extends AbstractModule {

	@Override
	protected final void configure() {
		bind(Domain.class).to(DomainImpl.class).in(Singleton.class);
	}

}
