package com.pommert.jedidiah.bouncecraft.proxy;

import com.pommert.jedidiah.bouncecraft.blocks.BCBlocks;
import com.pommert.jedidiah.bouncecraft.fmp.BCFMP;
import com.pommert.jedidiah.bouncecraft.items.BCItems;

public abstract class CommonProxy implements IProxy {

	@Override
	public void preInit() {
		BCBlocks.init();
		BCItems.init();
	}

	@Override
	public void init() {
		BCFMP.init();
	}
}
