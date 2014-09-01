package com.pommert.jedidiah.bouncecraft2.fmp.logic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.TreeMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.vec.Cuboid6;

import com.pommert.jedidiah.bouncecraft2.events.InitBounceCraftLogicEvent;
import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiPart;
import com.pommert.jedidiah.bouncecraft2.items.BCItems;
import com.pommert.jedidiah.bouncecraft2.log.BCLog;
import com.pommert.jedidiah.bouncecraft2.util.ByteMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BCPartLogic {

	public static class Index {

		public static final TreeMap<String, Index> indices = new TreeMap<String, Index>();
		public static final ByteMap<Index> VALUES = new ByteMap<Index>();

		public static Index NULL_BCPARTLOGIC;

		public static void init() {
			// register BounceCraft's logics
			register(BounceBlockPartLogic.class, "BOUNCE_BLOCK_BCPARTLOGIC");
			register(LowBounceBlockPartLogic.class,
					"LOW_BOUNCE_BLOCK_BCPARTLOGIC");
			register(MediumBounceBlockPartLogic.class,
					"MEDIUM_BOUNCE_BLOCK_BCPARTLOGIC");
			register(HighBounceBlockPartLogic.class,
					"HIGH_BOUNCE_BLOCK_BCPARTLOGIC");
			register(SpeedBlockPartLogic.class, "SPEED_BLOCK_BCPARTLOGIC");
			register(LowSpeedBlockPartLogic.class,
					"LOW_SPEED_BLOCK_BCPARTLOGIC");
			register(MediumSpeedBlockPartLogic.class,
					"MEDIUM_SPEED_BLOCK_BCPARTLOGIC");
			register(HighSpeedBlockPartLogic.class,
					"HIGH_SPEED_BLOCK_BCPARTLOGIC");

			// register other mod's logics
			MinecraftForge.EVENT_BUS.post(new InitBounceCraftLogicEvent());

			// register null logic
			NULL_BCPARTLOGIC = register(NullBCPartLogic.class,
					(byte) (Byte.MAX_VALUE), "NULL_BCPARTLOGIC");
		}

		private static byte nextId = 0;

		private final Class<? extends BCPartLogic> clazz;
		private final byte id;

		public Index(Class<? extends BCPartLogic> clazz) {
			this.clazz = clazz;
			id = nextId++;
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

		/**
		 * Creates a new index, and registers it.<br>
		 * The id of the index is automatically calculated.
		 * 
		 * @param clazz
		 *            the class of the <code>{@link BCPartLogic}</code> that
		 *            this index represents.
		 * @param name
		 *            the name of the index (for registry).
		 * @return the new Index
		 */
		public static Index register(Class<? extends BCPartLogic> clazz,
				String name) {
			Index i = new Index(clazz);
			VALUES.put(Byte.valueOf(i.id), i);
			if (indices.containsKey(name))
				throw new IllegalArgumentException("Part Logic Index name: "
						+ name + " is already taken!");
			indices.put(name, i);
			return i;
		}

		/**
		 * Creates a new index, and registers it.<br>
		 * The id of the index is automatically calculated.
		 * 
		 * @param clazz
		 *            the class of the <code>{@link BCPartLogic}</code> that
		 *            this index represents.
		 * @param id
		 *            the id of the index (for registry).
		 * @param name
		 *            the name of the index (for registry).
		 * @return the new Index
		 */
		public static Index register(Class<? extends BCPartLogic> clazz,
				byte id, String name) {
			Index i = new Index(clazz, id);
			if (VALUES.containsKey(Byte.valueOf(id)))
				throw new IllegalArgumentException("Part Logic Index id: " + id
						+ " is already taken!");
			VALUES.put(Byte.valueOf(i.id), i);
			if (indices.containsKey(name))
				throw new IllegalArgumentException("Part Logic Index name: "
						+ name + " is already taken!");
			indices.put(name, i);
			return i;
		}
	}

	public static BCPartLogic newLogic(byte id, BCMultiPart part) {
		if (!Index.VALUES.containsKey(id))
			id = Index.NULL_BCPARTLOGIC.getId();
		Class<? extends BCPartLogic> clazz = ((Index) Index.VALUES.get(id))
				.get();
		BCPartLogic logic = null;
		try {
			Constructor<? extends BCPartLogic> construct = clazz
					.getConstructor(BCMultiPart.class, Index.class);
			logic = construct.newInstance(part, Index.VALUES.get(id));
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

	public ItemStack getItem() {
		return new ItemStack(BCItems.items.get("itemBCMultiPart"), 1,
				id.getId());
	}

	@SideOnly(Side.CLIENT)
	public abstract ResourceLocation getTexture();

	@SideOnly(Side.CLIENT)
	public abstract ResourceLocation getModel();

	public void load(NBTTagCompound tag) {
	}

	public void save(NBTTagCompound tag) {
	}

	public void readDesc(MCDataInput packet) {
	}

	public void writeDesc(MCDataOutput packet) {
	}

	public void onEntityCollision(Entity entity) {
	}

	public boolean activate(EntityPlayer player, MovingObjectPosition pos,
			ItemStack stack) {
		return false;
	}

	public Iterable<Cuboid6> getCollisionBoxes() {
		return Arrays.asList(part.getBounds());
	}
}
