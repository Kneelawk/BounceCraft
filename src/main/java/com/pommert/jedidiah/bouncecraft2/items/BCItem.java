package com.pommert.jedidiah.bouncecraft2.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class BCItem extends Item {

	public String getUnlocalizedName(ItemStack stack) {
		if (getHasSubtypes())
			return super.getUnlocalizedName(stack) + "|"
					+ stack.getItemDamage();
		return super.getUnlocalizedName(stack);
	}

	public abstract boolean getHasSubtypes();
}
