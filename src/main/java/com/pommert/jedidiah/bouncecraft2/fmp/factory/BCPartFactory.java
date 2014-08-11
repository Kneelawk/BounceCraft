package com.pommert.jedidiah.bouncecraft2.fmp.factory;

import codechicken.multipart.TMultiPart;

import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiPart;

public class BCPartFactory implements IBCPartFactory {

	@Override
	public TMultiPart createPart(String name, boolean client) {
		return new BCMultiPart(client);
	}

}
