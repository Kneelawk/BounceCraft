package com.pommert.jedidiah.bouncecraft.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class BCItem extends Item {

	public String getUnlocalizedName(ItemStack stack) {
		if (hasSubTypes())
			return super.getUnlocalizedName(stack) + "|"
					+ stack.getItemDamage();
		return super.getUnlocalizedName(stack);
	}

	public abstract boolean hasSubTypes();
}
