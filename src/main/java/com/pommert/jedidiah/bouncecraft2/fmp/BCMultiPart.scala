package com.pommert.jedidiah.bouncecraft2.fmp

import codechicken.multipart.TCuboidPart
import codechicken.lib.vec.Cuboid6
import codechicken.lib.vec.Rotation
import codechicken.lib.vec.Vector3
import net.minecraftforge.client.model.AdvancedModelLoader
import com.pommert.jedidiah.bouncecraft2.ref.ModRef
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.obj.WavefrontObject
import net.minecraftforge.common.util.ForgeDirection
import net.minecraft.nbt.NBTTagCompound
import codechicken.lib.data.MCDataInput
import codechicken.lib.data.MCDataOutput
import net.minecraft.item.ItemStack
import com.pommert.jedidiah.bouncecraft2.items.BCItems
import java.util.Arrays
import java.lang.{ Iterable => JIterable }
import net.minecraft.util.MovingObjectPosition
import net.minecraft.entity.Entity
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side
import net.minecraft.client.Minecraft
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic
import org.lwjgl.opengl.GL11
import com.pommert.jedidiah.bouncecraft2.log.BCLog
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic.Index

class BCMultiPart(f: ForgeDirection, l: BCPartLogic, r: Byte) extends TCuboidPart {

	var facing: ForgeDirection = f
	BCLog.info("Placed: " + facing)
	var rotation: Byte = r
	var logic: BCPartLogic = if (l != null) l else BCPartLogic.newLogic(Index.NULL_BCPARTLOGIC.getId, this)

	@SideOnly(Side.CLIENT)
	val texture = logic.getTexture()

	@SideOnly(Side.CLIENT)
	val model = AdvancedModelLoader.loadModel(logic.getModel())

	def this() = this(ForgeDirection.DOWN, null, 0)

	@Override
	def getBounds = BCMultiPart.sides(facing.ordinal)

	@Override
	def getType = "bc_multipart"

	@Override
	override def load(tag: NBTTagCompound) {
		facing = ForgeDirection.VALID_DIRECTIONS(tag.getByte("facing"))
		rotation = tag.getByte("rotation")
		val id =
			if (tag.hasKey("logic_id")) {
				tag.getInteger("logic_id")
			} else {
				Index.NULL_BCPARTLOGIC.getId
			}
		logic = BCPartLogic.newLogic(id, this)
		logic.load(tag)
	}

	@Override
	override def save(tag: NBTTagCompound) {
		tag.setByte("facing", if (facing != null) facing.ordinal.asInstanceOf[Byte] else 0)
		tag.setByte("rotation", rotation)
		tag.setInteger("logic_id", logic.getId.getId)
		logic.save(tag)
	}

	@Override
	override def readDesc(packet: MCDataInput) {
		facing = ForgeDirection.VALID_DIRECTIONS(packet.readByte())
		rotation = packet.readByte()
		logic = BCPartLogic.newLogic(packet.readInt(), this)
		logic.readDesc(packet)
	}

	@Override
	override def writeDesc(packet: MCDataOutput) {
		packet.writeByte(if (facing != null) facing.ordinal.asInstanceOf[Byte] else 0)
		packet.writeByte(rotation)
		packet.writeInt(logic.getId.getId)
		logic.writeDesc(packet)
	}

	def getItem = logic.getItem()

	@Override
	override def getDrops = Arrays.asList(getItem)

	@Override
	override def pickItem(hit: MovingObjectPosition) = getItem

	@Override
	override def onEntityCollision(entity: Entity) { logic.onEntityCollision(entity) }

	@Override
	@SideOnly(Side.CLIENT)
	override def renderDynamic(pos: Vector3, f: Float, pass: Int) {
		if (pass == 0) {
			GL11.glPushMatrix()
			GL11.glTranslated(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5)
			BCMultiPart.renderTransformations(facing.ordinal)(rotation)
			Minecraft.getMinecraft().renderEngine.bindTexture(texture)
			model.renderAll()
			GL11.glPopMatrix()
		}
	}
}

object BCMultiPart {
	val sides = new Array[Cuboid6](6)
	sides(0) = new Cuboid6(0, 0, 0, 1, 1 / 16D, 1)
	for (i <- 1 to 5) {
		val t = Rotation.sideRotations(i).at(Vector3.center)
		sides(i) = sides(0).copy().apply(t)
	}

	val renderTransformations: Array[(Byte) => Unit] = Array(
		(rotation: Byte) => {
			GL11.glTranslated(0, -0.5 + 1D / 32D, 0)
			GL11.glRotated(180, 0, 0, 1)
			GL11.glRotated(90 * rotation, 0, 1, 0)
			GL11.glTranslated(-0.5, -1D / 32D, -0.5)
		},
		(rotation: Byte) => {
			var r = if (rotation.&(1) == 0) {
				rotation
			} else {
				(rotation + 2) % 4
			}
			GL11.glRotated(90 * r, 0, 1, 0)
			GL11.glTranslated(-0.5, 0.5 - 1D / 16D, -0.5)
		},
		(rotation: Byte) => {
			GL11.glRotated(90 * rotation, 0, 1, 0)
			GL11.glTranslated(-0.5, -0.5, -0.5 + 1D / 16D)
			GL11.glRotated(270, 1, 0, 0)
		},
		(rotation: Byte) => {
			GL11.glTranslated(0, 0, 0.5 - 1D / 32D)
			GL11.glRotated(180, 0, 1, 0)
			GL11.glRotated(90 * rotation, 0, 1, 0)
			GL11.glTranslated(-0.5, -0.5, 1D / 32D)
			GL11.glRotated(270, 1, 0, 0)
		},
		(rotation: Byte) => {
			GL11.glTranslated(-0.5 + 1D / 16D, 0, 0)
			GL11.glRotated(90, 0, 0, 1)
			GL11.glRotated(90, 0, 1, 0)
			GL11.glRotated(90 * rotation, 0, 1, 0)
			GL11.glTranslated(-0.5, 0, -0.5)
		},
		(rotation: Byte) => {
			GL11.glRotated(90 * rotation, 0, 1, 0)
			GL11.glTranslated(0.5, 0, 0)
			GL11.glRotated(90, 0, 0, 1)
			GL11.glRotated(90, 0, 1, 0)
			GL11.glTranslated(0, 1D / 32D, -0.5)
			GL11.glRotated(180, 0, 0, 1)
			GL11.glTranslated(-0.5, -1D / 32D, 0)
		})
}