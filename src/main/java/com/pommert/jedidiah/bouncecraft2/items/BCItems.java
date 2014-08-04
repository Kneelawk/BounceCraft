package com.pommert.jedidiah.bouncecraft2.items;

import java.util.TreeMap;

import net.minecraft.creativetab.CreativeTabs;

import com.pommert.jedidiah.bouncecraft2.ref.ModRef;

import cpw.mods.fml.common.registry.GameRegistry;

public class BCItems {
	public static final TreeMap<String, BCItem> items = new TreeMap<String, BCItem>();

	public static void init() {
		addItem(new ItemBCMultipart(), "itemBCMultiPart");
	}

	public static void addItem(BCItem item, String name) {
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name, ModRef.MOD_ID);
		items.put(name, item);
		item.setCreativeTab(CreativeTabs.tabBlock);
	}
}
