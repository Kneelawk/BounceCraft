package com.pommert.jedidiah.bouncecraft2.fmp

import codechicken.multipart.TCuboidPart
import codechicken.lib.vec.Cuboid6
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCBlockLogic
import net.minecraft.nbt.NBTTagCompound
import codechicken.lib.data.MCDataInput
import codechicken.lib.data.MCDataOutput
import net.minecraft.item.ItemStack
import java.util.Arrays
import java.util.{ List => JList }
import net.minecraft.util.MovingObjectPosition
import net.minecraft.entity.Entity
import com.pommert.jedidiah.bouncecraft2.log.BCLog
import cpw.mods.fml.relauncher.SideOnly
import codechicken.lib.vec.Vector3
import cpw.mods.fml.relauncher.Side
import org.lwjgl.opengl.GL11
import net.minecraft.entity.player.EntityPlayer
import com.pommert.jedidiah.bouncecraft2.util.NumberUtils

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

	@Override
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

	@Override
	override def save(tag: NBTTagCompound) {
		tag.setByte("rotX", rotX)
		tag.setByte("rotY", rotY)
		tag.setByte("rotZ", rotZ)
		tag.setByte("logic_id", logic.getId.getId)
		logic.save(tag)
	}

	@Override
	override def readDesc(packet: MCDataInput) {
		rotX = packet.readByte()
		rotY = packet.readByte()
		rotZ = packet.readByte()
		logic = BCBlockLogic.newLogic(this, packet.readByte())
		logic.readDesc(packet)
	}

	@Override
	override def writeDesc(packet: MCDataOutput) {
		packet.writeByte(rotX)
		packet.writeByte(rotY)
		packet.writeByte(rotZ)
		BCLog.info("logic: " + logic)
		BCLog.info("logic.getId: " + logic.getId)
		packet.writeByte(logic.getId.getId)
		logic.writeDesc(packet)
	}

	def getItem = logic.getItem

	@Override
	override def getDrops(): JList[ItemStack] = Arrays.asList(getItem)

	@Override
	override def pickItem(hit: MovingObjectPosition) = getItem

	@Override
	override def onEntityCollision(entity: Entity) = logic.onEntityCollision(entity)

	@Override
	override def activate(player: EntityPlayer, hit: MovingObjectPosition, stack: ItemStack): Boolean = {
		val stackName = stack.getItem().getUnlocalizedName(stack).toLowerCase()
		val isScrewDriver = (stackName.contains("screw") && stackName.contains("driver")) || stackName.contains("wrench") || stackName.contains("hammer")
		var worked = false
		if (isScrewDriver) {
			hit.sideHit match {
				case 0 => {
					rotY = NumberUtils.rotate(rotY, 0, 4, 1).byteValue
					BCLog.info("rotY: " + rotY)
				}
				case 1 => {
					rotY = NumberUtils.rotate(rotY, 0, 4, -1).byteValue
					BCLog.info("rotY: " + rotY)
				}
				case _ => {

				}
			}
			worked = true
		}
		return logic.activate(player, hit, stack) || worked
	}

	@Override
	@SideOnly(Side.CLIENT)
	override def renderDynamic(pos: Vector3, f: Float, pass: Int) {
		GL11.glPushMatrix()
		GL11.glTranslated(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5)
		GL11.glRotated(rotX * 90, 1, 0, 0)
		GL11.glRotated(rotY * 90, 0, 1, 0)
		GL11.glRotated(rotZ * 90, 0, 0, 1)
		GL11.glTranslated(-0.5, -0.5, -0.5)
		logic.renderBlock(pos, f)
		GL11.glPopMatrix()
	}
}