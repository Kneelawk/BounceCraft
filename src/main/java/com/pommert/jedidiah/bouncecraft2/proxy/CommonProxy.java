package com.pommert.jedidiah.bouncecraft2.proxy;

import com.pommert.jedidiah.bouncecraft2.blocks.BCBlocks;
import com.pommert.jedidiah.bouncecraft2.fmp.BCFMP;
import com.pommert.jedidiah.bouncecraft2.items.BCItems;

public abstract class CommonProxy implements IProxy {

	@Override
	public void preInit() {
		BCBlocks.init();
		BCItems.init();
	}

	@Override
	public void init() {
		BCFMP.init();
		BCItems.initCrafting();
	}
}
