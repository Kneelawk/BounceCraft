package com.pommert.jedidiah.bouncecraft.proxy;

import com.pommert.jedidiah.bouncecraft.items.BCItemsClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

	@Override
	@SideOnly(Side.CLIENT)
	public void init() {
		super.init();
		BCItemsClient.init();
	}
}
