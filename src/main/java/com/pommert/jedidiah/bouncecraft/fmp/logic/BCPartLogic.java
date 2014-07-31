package com.pommert.jedidiah.bouncecraft.fmp.logic;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;

import com.pommert.jedidiah.bouncecraft.fmp.BCMultiPart;
import com.pommert.jedidiah.bouncecraft.log.BCLog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BCPartLogic {

	public static enum Index {
		NULL_BCPARTLOGIC(NullBCPartLogic.class);

		public static final Index[] VALUES = values();

		private Class<? extends BCPartLogic> clazz;

		private Index(Class<? extends BCPartLogic> clazz) {
			this.clazz = clazz;
		}

		public Class<? extends BCPartLogic> get() {
			return clazz;
		}
	}

	public static BCPartLogic newLogic(int id) {
		if (id < 0 || id >= Index.VALUES.length)
			return null;
		Class<? extends BCPartLogic> clazz = Index.VALUES[id].get();
		BCPartLogic logic = null;
		try {
			logic = clazz.newInstance();
			logic.id = id;
		} catch (InstantiationException e) {
			BCLog.warn("Unable to create logic: ", e);
		} catch (IllegalAccessException e) {
			BCLog.warn("Unable to create logic: ", e);
		}
		return logic;
	}

	protected BCMultiPart part;

	protected int id;

	public void setPart(BCMultiPart part) {
		this.part = part;
	}

	public BCMultiPart getPart() {
		return part;
	}

	public int getId() {
		return id;
	}

	public abstract ItemStack getItem();

	@SideOnly(Side.CLIENT)
	public abstract ResourceLocation getTexture();

	@SideOnly(Side.CLIENT)
	public abstract ResourceLocation getModel();

	public abstract void load(NBTTagCompound tag);

	public abstract void save(NBTTagCompound tag);

	public abstract void readDesc(MCDataInput packet);

	public abstract void writeDesc(MCDataOutput packet);

	public void onEntityCollision(Entity entity) {
	}
}
