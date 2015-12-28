package com.blogspot.mikelaud.je.domain;

import java.util.Collection;

import com.blogspot.mikelaud.je.domain.types.Type;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public interface Domain {

	void setTypesSource(String aTypesSource);
	String getTypesSource();
	StringProperty takeTypesSource();
	//
	void setTypes(Collection<Type> aTypes);
	ObservableList<Type> getTypes();
	FilteredList<Type> getTypesFiltered();
	SortedList<Type> getTypesSorted();
	
}
