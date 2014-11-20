package com.pommert.jedidiah.bouncecraft2.fmp.logic

import java.util.Arrays
import java.util.TreeMap
import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiBlock
import com.pommert.jedidiah.bouncecraft2.items.BCItems
import com.pommert.jedidiah.bouncecraft2.log.BCLog
import com.pommert.jedidiah.bouncecraft2.util.ByteMap
import codechicken.lib.data.MCDataInput
import codechicken.lib.data.MCDataOutput
import codechicken.lib.vec.Cuboid6
import codechicken.lib.vec.Vector3
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.MovingObjectPosition
import cpw.mods.fml.relauncher.Side
import java.lang.{ Iterable => JIterable }
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.MinecraftForge
import com.pommert.jedidiah.bouncecraft2.events.InitBounceCraftBlockLogicEvent

abstract class BCBlockLogic(block: BCMultiBlock, id: BCBlockLogic.Index) {
	def getBlock = block

	def getId = id

	def getItem = new ItemStack(BCItems.items.get("itemBCMultiBlock"), 1, id.getId)

	@SideOnly(Side.CLIENT)
	def renderBlock(pos: Vector3, f: Float)

	@SideOnly(Side.CLIENT)
	def renderHand()

	def load(tag: NBTTagCompound) {}

	def save(tag: NBTTagCompound) {}

	def readDesc(packet: MCDataInput) {}

	def writeDesc(packet: MCDataOutput) {}

	def onEntityCollision(entity: Entity) {}

	def activate(player: EntityPlayer, hit: MovingObjectPosition, item: ItemStack): Boolean = false

	def canRotate(player: EntityPlayer, hit: MovingObjectPosition, item: ItemStack, oldDirection: ForgeDirection, oldRotation: Byte, newDirection: ForgeDirection, newRotation: Byte): Boolean = true

	def getCollisionBoxes(): JIterable[Cuboid6] = {
		Arrays.asList(block.getBounds)
	}

	def directionWhenPlaced(dir: ForgeDirection): ForgeDirection = dir
}

object BCBlockLogic {
	class Index(c: Class[_], id: Byte) {
		private val clazz = c.asInstanceOf[Class[BCBlockLogic]]

		def this(clazz: Class[_]) = this(clazz, Index.nextId_++)

		def getId = id

		def get = clazz
	}

	object Index {
		val indices = new TreeMap[String, Index]
		val VALUES = new ByteMap[Index]

		var nextId: Byte = 0

		def nextId_++(): Byte = {
			val constId = nextId
			nextId = (nextId + 1).byteValue()
			return constId
		}

		var NULL_BCBLOCKLOGIC: Index = null

		def init {
			// register BounceCraft's logics
			register(classOf[MoveGrateBlockLogic], "MOVE_GRATE_BCBLOCKLOGIC")

			// register other mods logics
			MinecraftForge.EVENT_BUS.post(new InitBounceCraftBlockLogicEvent());

			// register null logic
			NULL_BCBLOCKLOGIC = register(classOf[NullBCBlockLogic], Byte.MaxValue, "NULL_BCBLOCKLOGIC")
		}

		def register(clazz: Class[_], name: String): Index = {
			val i = new Index(clazz)
			VALUES.put(i.getId, i)
			if (indices.containsKey(name))
				throw new IllegalArgumentException("Part Logic Index name: "
					+ name + " is already taken!")
			indices.put(name, i)
			return i
		}

		def register(clazz: Class[_], id: Byte, name: String): Index = {
			val i = new Index(clazz, id)
			if (VALUES.containsKey(id))
				throw new IllegalArgumentException("Part Logic Index id: " + id
					+ " is already taken!")
			VALUES.put(id, i)
			if (indices.containsKey(name))
				throw new IllegalArgumentException("Part Logic Index name: "
					+ name + " is already taken!")
			indices.put(name, i)
			return i
		}
	}

	def newLogic(id: Byte, block: BCMultiBlock): BCBlockLogic = {
		if (!Index.VALUES.containsKey(id))
			return null

		val clazz = Index.VALUES.get(id).get
		var logic: BCBlockLogic = null

		try {
			val construct = clazz.getConstructor(classOf[BCMultiBlock], classOf[Index])
			logic = construct.newInstance(block, Index.VALUES.get(id))
		} catch {
			case t: Throwable => BCLog.warn("Unable to create logic: ", t)
		}

		return logic
	}
}