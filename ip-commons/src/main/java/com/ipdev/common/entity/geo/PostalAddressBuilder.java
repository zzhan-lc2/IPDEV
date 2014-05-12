package com.ipdev.common.entity.geo;

import javax.xml.registry.infomodel.PostalAddress;

import org.apache.ws.scout.registry.infomodel.PostalAddressImpl;

public final class PostalAddressBuilder {

	public static PostalAddress newPostalAddress() {
		return new PostalAddressImpl();
	}
}
