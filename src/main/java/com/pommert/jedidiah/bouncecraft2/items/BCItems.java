package com.pommert.jedidiah.bouncecraft2.items;

import java.util.TreeMap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.pommert.jedidiah.bouncecraft2.creativetabs.BCCreativeTabs;
import com.pommert.jedidiah.bouncecraft2.ref.ModRef;

import cpw.mods.fml.common.registry.GameRegistry;

public class BCItems {
	public static final TreeMap<String, BCItem> items = new TreeMap<String, BCItem>();

	public static void init() {
		addItem(new ItemBCMultipart(), "itemBCMultiPart");
		addItem(new ItemScrewDriver(), "itemScrewDriver").setMaxStackSize(1);
	}

	public static BCItem addItem(BCItem item, String name) {
		item.setUnlocalizedName(name);
		item.setTextureName(ModRef.MOD_ID + ":" + name);
		GameRegistry.registerItem(item, name, ModRef.MOD_ID);
		items.put(name, item);
		item.setCreativeTab(BCCreativeTabs.tabBounceCraft());
		return item;
	}

	public static void initCrafting() {
		GameRegistry.addRecipe(new ShapedOreRecipe(
				items.get("itemScrewDriver"), " i ", " i ", "gGg", 'i',
				"ingotIron", 'g', "blockGlass", 'G', "ingotGold"));
	}
}
