package com.blogspot.mikelaud.je;

import com.blogspot.mikelaud.je.common.Method;
import com.blogspot.mikelaud.je.common.Type;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class OpenMethod {

	@SuppressWarnings("unused")
	private static interface Const {
		// void
	}
	
	private final ObservableList<Method> DATA;
	private final FilteredList<Method> FILTERED_DATA;
	private final SortedList<Method> SORTED_DATA;
	
	public ObservableList<Method> getData() { return DATA; }
	public FilteredList<Method> getFilteredData() { return FILTERED_DATA; }
	public SortedList<Method> getSortedData() { return SORTED_DATA; }
	
	public void setType(Type aType) {
		if (null == aType) {
			DATA.clear();
		}
		else {
			DATA.setAll(aType.getMethods());
		}
	}
	
	public OpenMethod() {
		DATA = FXCollections.observableArrayList();
		FILTERED_DATA = new FilteredList<>(DATA, p -> true);
		SORTED_DATA = new SortedList<>(FILTERED_DATA, (a, b) -> a.getName().compareTo(b.getName()));
	}
	
}
