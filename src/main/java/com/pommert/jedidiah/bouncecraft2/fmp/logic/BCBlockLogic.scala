package com.pommert.jedidiah.bouncecraft2.fmp.logic

import java.lang.{ Iterable => JIterable }
import java.util.Arrays
import java.util.TreeMap
import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiBlock
import com.pommert.jedidiah.bouncecraft2.fmp.logic.render.RotatableRender
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

abstract class BCBlockLogic(block: BCMultiBlock, id: BCBlockLogic.Index) {
	def getBlock = block

	def getId = id

	def getItem = new ItemStack(BCItems.items.get("itemBCMultiBlock"), 1, id.getId)

	@SideOnly(Side.CLIENT)
	def render(pos: Vector3, f: Float) {
		RotatableRender.render(pos, block.rotX, block.rotY, block.rotZ)
	}

	def load(tag: NBTTagCompound) {}

	def save(tag: NBTTagCompound) {}

	def readDesc(packet: MCDataInput) {}

	def writeDesc(packet: MCDataOutput) {}

	def onEntityCollision(entity: Entity) {}

	def activate(player: EntityPlayer, pos: MovingObjectPosition, item: ItemStack) {}

	def getCollisionBoxes(): JIterable[Cuboid6] = {
		Arrays.asList(block.getBounds)
	}
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

		val NULL_BCBLOCKLOGIC: Index = null

		def init {

		}
	}

	def newLogic(block: BCMultiBlock, id: Byte): BCBlockLogic = {
		if (!Index.VALUES.containsKey(id))
			return null

		val clazz = Index.VALUES.get(id).get
		var logic: BCBlockLogic = null

		try {
			val construct = clazz.getConstructor(classOf[BCBlockLogic], classOf[Index])
			logic = construct.newInstance(block, Index.VALUES.get(id))
		} catch {
			case t: Throwable => BCLog.warn("Unable to create logic: ", t)
		}

		return logic
	}
}