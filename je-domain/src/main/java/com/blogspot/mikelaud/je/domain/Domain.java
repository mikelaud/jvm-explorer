package com.blogspot.mikelaud.je.domain;

import java.util.Collection;

import com.blogspot.mikelaud.je.agent.beans.TypesMXBean;
import com.blogspot.mikelaud.je.domain.pojo.DomainType;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public interface Domain {

	TypesMXBean getTypesBean();
	//
	void setTypesSource(String aTypesSource);
	String getTypesSource();
	StringProperty takeTypesSource();
	//
	void setTypes(Collection<DomainType> aTypes);
	ObservableList<DomainType> getTypes();
	FilteredList<DomainType> getTypesFiltered();
	SortedList<DomainType> getTypesSorted();

}
