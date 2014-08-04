package com.pommert.jedidiah.bouncecraft2.fmp.logic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;

import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiPart;
import com.pommert.jedidiah.bouncecraft2.log.BCLog;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BCPartLogic {

	public static enum Index {
		BOUNCE_BLOCK_BCPARTLOGIC(BounceBlockPartLogic.class), NULL_BCPARTLOGIC(
				NullBCPartLogic.class, (byte) (Byte.MAX_VALUE));

		public static final Index[] VALUES = createValues();

		private static Index[] createValues() {
			Index[] values = values();
			int size = values.length;

			for (int i = 0; i < values.length; i++) {
				if (values[i].getId() >= size) {
					size = values[i].getId() + 1;
				}
			}

			Index[] is = new Index[size];

			for (int i = 0; i < values.length; i++) {
				int id = values[i].getId();
				is[id] = values[i];
			}

			return is;
		}

		private Class<? extends BCPartLogic> clazz;
		private byte id;

		private Index(Class<? extends BCPartLogic> clazz) {
			this.clazz = clazz;
			id = (byte) ordinal();
		}

		private Index(Class<? extends BCPartLogic> clazz, byte id) {
			this.clazz = clazz;
			this.id = id;
		}

		public Class<? extends BCPartLogic> get() {
			return clazz;
		}

		public byte getId() {
			return id;
		}
	}

	public static BCPartLogic newLogic(int id, BCMultiPart part) {
		if (id < 0 || id >= Index.VALUES.length)
			return null;
		if (Index.VALUES[id] == null)
			id = Index.NULL_BCPARTLOGIC.getId();
		Class<? extends BCPartLogic> clazz = Index.VALUES[id].get();
		BCPartLogic logic = null;
		try {
			Constructor<? extends BCPartLogic> construct = clazz
					.getConstructor(BCMultiPart.class, Index.class);
			logic = construct.newInstance(part, Index.VALUES[id]);
		} catch (InstantiationException e) {
			BCLog.warn("Unable to create logic: ", e);
		} catch (IllegalAccessException e) {
			BCLog.warn("Unable to create logic: ", e);
		} catch (NoSuchMethodException e) {
			BCLog.warn("Unable to create logic: ", e);
		} catch (SecurityException e) {
			BCLog.warn("Unable to create logic: ", e);
		} catch (IllegalArgumentException e) {
			BCLog.warn("Unable to create logic: ", e);
		} catch (InvocationTargetException e) {
			BCLog.warn("Unable to create logic: ", e);
		}
		return logic;
	}

	protected final BCMultiPart part;

	protected final Index id;

	public BCPartLogic(BCMultiPart part, Index index) {
		this.part = part;
		this.id = index;
	}

	public BCMultiPart getPart() {
		return part;
	}

	public Index getId() {
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

	public boolean activate(EntityPlayer player, MovingObjectPosition pos,
			ItemStack stack) {
		return false;
	}
}
