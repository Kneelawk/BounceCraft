package com.pommert.jedidiah.bouncecraft2.proxy;

import com.pommert.jedidiah.bouncecraft2.items.BCItemsClient;
import com.pommert.jedidiah.bouncecraft2.items.render.ItemRenderTestMultipart;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

	@Override
	@SideOnly(Side.CLIENT)
	public void init() {
		super.init();
		ItemRenderTestMultipart.createTextures();
		BCItemsClient.init();
	}
}
