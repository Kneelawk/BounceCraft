package com.pommert.jedidiah.bouncecraft2.items;

import net.minecraftforge.client.MinecraftForgeClient;

import com.pommert.jedidiah.bouncecraft2.items.render.ItemRenderScrewDriver;
import com.pommert.jedidiah.bouncecraft2.items.render.ItemRenderTestMultipart;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BCItemsClient {

	@SideOnly(Side.CLIENT)
	public static void init() {
		MinecraftForgeClient.registerItemRenderer(
				BCItems.items.get("itemBCMultiPart"),
				new ItemRenderTestMultipart());
		MinecraftForgeClient.registerItemRenderer(BCItems.items.get("itemScrewDriver"), new ItemRenderScrewDriver());
	}
}
