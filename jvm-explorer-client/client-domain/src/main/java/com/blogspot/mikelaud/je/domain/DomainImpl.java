package com.blogspot.mikelaud.je.domain;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.util.Collection;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.blogspot.mikelaud.je.agent.api.TypesMXBean;
import com.blogspot.mikelaud.je.common.utils.StringUtils;
import com.blogspot.mikelaud.je.domain.pojo.DomainType;
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
	//
	private TypesMXBean mTypesBean;

	@Inject
	private DomainImpl() {
		TYPES_SOURCE = new SimpleStringProperty();
		//
		TYPES = FXCollections.observableArrayList();
		TYPES_FILTERED = new FilteredList<>(TYPES, p -> true);
		TYPES_SORTED = new SortedList<>(TYPES_FILTERED, (a, b) -> a.getName().compareTo(b.getName()));
		//
		mTypesBean = null;
	}

	private JMXServiceURL getBeanRemoteUrl() {
		try {
			return new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:5000/jmxrmi");
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private ObjectName getBeanName() {
		try {
			return ObjectName.getInstance("JvmExplorer", "type", "Types");
		}
		catch (MalformedObjectNameException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unused")
	private TypesMXBean createLocalTypesBean() {
		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
		return JMX.newMXBeanProxy(beanServer, getBeanName(), TypesMXBean.class);
	}

	private TypesMXBean createRemoteTypesBean() {
		try {
			JMXConnector jmxc = JMXConnectorFactory.connect(getBeanRemoteUrl(), null);
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
			return JMX.newMBeanProxy(mbsc, getBeanName(), TypesMXBean.class, true);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public TypesMXBean getTypesBean() {
		if (null == mTypesBean) {
			mTypesBean = createRemoteTypesBean();
		}
		return mTypesBean;
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
