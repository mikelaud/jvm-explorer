package com.blogspot.mikelaud.je.domain;

import java.util.Collection;

import com.blogspot.mikelaud.je.domain.pojo.DomainType;
import com.blogspot.mikelaud.je.utils.StringUtils;
import com.google.inject.Inject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class DomainImpl implements Domain {

	private final StringProperty TYPES_SOURCE;
	//
	private final ObservableList<DomainType> TYPES;
	private final FilteredList<DomainType> TYPES_FILTERED;
	private final SortedList<DomainType> TYPES_SORTED;
	
	@Inject
	private DomainImpl() {
		TYPES_SOURCE = new SimpleStringProperty();
		//
		TYPES = FXCollections.observableArrayList();
		TYPES_FILTERED = new FilteredList<>(TYPES, p -> true);
		TYPES_SORTED = new SortedList<>(TYPES_FILTERED, (a, b) -> a.getName().compareTo(b.getName()));
	}

	@Override public final void setTypesSource(String aTypesSource) { TYPES_SOURCE.set(StringUtils.nvl(aTypesSource)); }
	@Override public final String getTypesSource() { return TYPES_SOURCE.get(); }
	@Override public final StringProperty takeTypesSource() { return TYPES_SOURCE; }

	@Override
	public final void setTypes(Collection<DomainType> aTypes) {
		if (null == aTypes) {
			TYPES.clear();
		}
		else {
			TYPES.addAll(aTypes);
		}
	}
	
	@Override public final ObservableList<DomainType> getTypes() { return TYPES; }
	@Override public final FilteredList<DomainType> getTypesFiltered() { return TYPES_FILTERED; }
	@Override public final SortedList<DomainType> getTypesSorted() { return TYPES_SORTED; }
	
}
