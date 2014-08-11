package com.pommert.jedidiah.bouncecraft2.fmp

import codechicken.multipart.TCuboidPart
import codechicken.lib.vec.Cuboid6
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCBlockLogic
import net.minecraft.nbt.NBTTagCompound
import codechicken.lib.data.MCDataInput
import codechicken.lib.data.MCDataOutput
import net.minecraft.item.ItemStack
import java.util.Arrays
import java.util.{List => JList}
import net.minecraft.util.MovingObjectPosition

class BCMultiBlock(l: BCBlockLogic, c: Boolean) extends TCuboidPart {

	val client = c

	var rotX: Byte = 0
	var rotY: Byte = 0
	var rotZ: Byte = 0

	var logic: BCBlockLogic = if (l != null) l else BCBlockLogic.newLogic(this, BCBlockLogic.Index.NULL_BCBLOCKLOGIC.getId)

	def this(client: Boolean) = this(null, client)

	@Override
	def getBounds = new Cuboid6(0, 0, 0, 1, 1, 1)

	@Override
	def getType = "bc_multiblock"

	override def load(tag: NBTTagCompound) {
		rotX = tag.getByte("rotX")
		rotY = tag.getByte("rotY")
		rotZ = tag.getByte("rotz")
		val id =
			if (tag.hasKey("logic_id")) {
				tag.getByte("logic_id")
			} else {
				BCBlockLogic.Index.NULL_BCBLOCKLOGIC.getId
			}
		logic = BCBlockLogic.newLogic(this, id)
		logic.load(tag)
	}

	override def save(tag: NBTTagCompound) {
		tag.setByte("rotX", rotX)
		tag.setByte("rotY", rotY)
		tag.setByte("rotZ", rotZ)
		tag.setByte("logic_id", logic.getId.getId)
		logic.save(tag)
	}

	override def readDesc(packet: MCDataInput) {
		rotX = packet.readByte()
		rotY = packet.readByte()
		rotZ = packet.readByte()
		logic = BCBlockLogic.newLogic(this, packet.readByte())
		logic.readDesc(packet)
	}

	override def writeDesc(packet: MCDataOutput) {
		packet.writeByte(rotX)
		packet.writeByte(rotY)
		packet.writeByte(rotZ)
		packet.writeByte(logic.getId.getId)
		logic.writeDesc(packet)
	}
	
	def getItem = logic.getItem
	
	override def getDrops(): JList[ItemStack] = Arrays.asList(getItem)
	
	override def pickItem(hit: MovingObjectPosition) = getItem
}