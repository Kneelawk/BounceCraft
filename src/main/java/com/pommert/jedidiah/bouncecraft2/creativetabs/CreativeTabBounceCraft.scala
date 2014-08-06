package com.pommert.jedidiah.bouncecraft2.creativetabs

import net.minecraft.creativetab.CreativeTabs
import com.pommert.jedidiah.bouncecraft2.items.BCItems
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side
import net.minecraft.item.ItemStack

class CreativeTabBounceCraft extends CreativeTabs("tabBounceCraft") {

	@Override
	@SideOnly(Side.CLIENT)
	def getTabIconItem = BCItems.items.get("itemBCMultiPart")
}