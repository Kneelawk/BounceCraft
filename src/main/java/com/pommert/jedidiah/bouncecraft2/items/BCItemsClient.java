package com.pommert.jedidiah.bouncecraft2.items;

import net.minecraftforge.client.MinecraftForgeClient;

import com.pommert.jedidiah.bouncecraft2.items.render.ItemRenderBCMultiblock;
import com.pommert.jedidiah.bouncecraft2.items.render.ItemRenderBCMultipart;
import com.pommert.jedidiah.bouncecraft2.items.render.ItemRenderScrewDriver;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BCItemsClient {

	@SideOnly(Side.CLIENT)
	public static void init() {
		MinecraftForgeClient.registerItemRenderer(
				BCItems.items.get("itemBCMultiPart"),
				new ItemRenderBCMultipart());
		MinecraftForgeClient.registerItemRenderer(
				BCItems.items.get("itemBCMultiBlock"),
				new ItemRenderBCMultiblock());
		MinecraftForgeClient.registerItemRenderer(
				BCItems.items.get("itemScrewDriver"),
				new ItemRenderScrewDriver());
	}
}
