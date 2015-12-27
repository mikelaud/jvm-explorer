package com.blogspot.mikelaud.je.mvc;

import java.util.Collection;

import com.blogspot.mikelaud.je.mvc.domain.Type;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public interface Model {

	void setTypesSource(String aTypesSource);
	String getTypesSource();
	StringProperty takeTypesSource();
	//
	void setTypes(Collection<Type> aTypes);
	ObservableList<Type> getTypes();
	FilteredList<Type> getTypesFiltered();
	SortedList<Type> getTypesSorted();
	
}
