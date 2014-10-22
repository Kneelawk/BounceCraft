package com.pommert.jedidiah.bouncecraft2.fmp

import java.util.Arrays
import java.util.{ List => JList }
import org.lwjgl.opengl.GL11
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCBlockLogic
import com.pommert.jedidiah.bouncecraft2.log.BCLog
import com.pommert.jedidiah.bouncecraft2.util.NumberUtils
import codechicken.lib.data.MCDataInput
import codechicken.lib.data.MCDataOutput
import codechicken.lib.vec.Cuboid6
import codechicken.lib.vec.Vector3
import codechicken.multipart.TCuboidPart
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.MovingObjectPosition
import net.minecraftforge.common.util.ForgeDirection
import cpw.mods.fml.relauncher.Side

// Block Rotation
class BR(d: ForgeDirection, r: Byte) {
	var dir = d
	var rot = r
}

class BCMultiBlock(l: BCBlockLogic, c: Boolean) extends TCuboidPart {

	val client = c

	// old rotation system
	// removed because of inefficiencies
	//	var rotX: Byte = 0
	//	var rotY: Byte = 0
	//	var rotZ: Byte = 0

	// new rotation system
	var dir: ForgeDirection = ForgeDirection.UP
	var rot: Byte = 0

	var logic: BCBlockLogic = if (l != null) l else BCBlockLogic.newLogic(this, BCBlockLogic.Index.NULL_BCBLOCKLOGIC.getId)

	def this(client: Boolean) = this(null, client)

	@Override
	def getBounds = new Cuboid6(0, 0, 0, 1, 1, 1)

	@Override
	def getType = "bc_multiblock"

	@Override
	override def load(tag: NBTTagCompound) {
		dir = ForgeDirection.getOrientation(NumberUtils.mod2(tag.getByte("dir"), 0, 6))
		rot = NumberUtils.mod2(tag.getByte("rot"), 0, 4).byteValue()
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
		tag.setByte("dir", dir.ordinal().byteValue())
		tag.setByte("rot", rot)
		tag.setByte("logic_id", logic.getId.getId)
		logic.save(tag)
	}

	@Override
	override def readDesc(packet: MCDataInput) {
		dir = ForgeDirection.getOrientation(NumberUtils.mod2(packet.readByte(), 0, 6))
		rot = NumberUtils.mod2(packet.readByte(), 0, 4).byteValue()
		logic = BCBlockLogic.newLogic(this, packet.readByte())
		logic.readDesc(packet)
	}

	@Override
	override def writeDesc(packet: MCDataOutput) {
		packet.writeByte(dir.ordinal().byteValue())
		packet.writeByte(rot)
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
		val stackName =
			if (stack != null)
				stack.getItem().getUnlocalizedName(stack).toLowerCase()
			else
				""
		val isScrewDriver = (stackName.contains("screw") && stackName.contains("driver")) || stackName.contains("wrench") || stackName.contains("hammer")
		var worked = false
		if (isScrewDriver) {
			val nr = BCMultiBlock.rotationApplications(dir.ordinal())(hit.sideHit)(rot)
			if (nr != null) {
				if (logic.canRotate(player, hit, stack, dir, rot, nr.dir, nr.rot)) {
					dir = nr.dir
					rot = nr.rot
				}
			}
			worked = true
		}
		return logic.activate(player, hit, stack) || worked
	}

	@Override
	@SideOnly(Side.CLIENT)
	override def renderDynamic(pos: Vector3, f: Float, pass: Int) {
		if (pass == 0) {
			GL11.glPushMatrix()
			GL11.glTranslated(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5)
			// rotate block
			BCMultiBlock.renderTransformations(dir.ordinal())(rot)
			GL11.glScaled(1D - 1D / 4096D, 1D - 1D / 4096D, 1D - 1D / 4096D)
			GL11.glTranslated(-0.5, -0.5, -0.5)
			logic.renderBlock(pos, f)
			GL11.glPopMatrix()
		}
	}
}

object BCMultiBlock {
	val renderTransformations: Array[(Byte) => Unit] = Array(
		(rotation: Byte) => {
			val r = if (rotation.&(1) == 0) {
				rotation
			} else {
				(rotation + 2) % 4
			}
			GL11.glRotated(180, 0, 0, 1)
			GL11.glRotated(90 * r, 0, 1, 0)
		},
		(rotation: Byte) => {
			val r = if (rotation.&(1) == 0) {
				rotation
			} else {
				(rotation + 2) % 4
			}
			GL11.glRotated(90 * r, 0, 1, 0)
		},
		(rotation: Byte) => {
			GL11.glRotated(90 * rotation, 0, 0, 1)
			GL11.glRotated(270, 1, 0, 0)
		},
		(rotation: Byte) => {
			val r = if (rotation.&(1) == 0) {
				rotation
			} else {
				(rotation + 2) % 4
			}
			GL11.glRotated(90, 1, 0, 0)
			GL11.glRotated(90 * r, 0, 1, 0)
		},
		(rotation: Byte) => {
			val r = if (rotation.&(1) == 0) {
				rotation
			} else {
				(rotation + 2) % 4
			}
			GL11.glRotated(90, 0, 0, 1)
			GL11.glRotated(90 * r, 0, 1, 0)
		},
		(rotation: Byte) => {
			val r = if (rotation.&(1) == 0) {
				rotation
			} else {
				(rotation + 2) % 4
			}
			GL11.glRotated(270, 0, 0, 1)
			GL11.glRotated(90 * r, 0, 1, 0)
		})

	val rotationApplications: Array[Array[(Byte) => BR]] = Array(
		Array(
			(rot: Byte) => {
				new BR(ForgeDirection.DOWN, NumberUtils.rotate(rot, 0, 4, 1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.DOWN, NumberUtils.rotate(rot, 0, 4, -1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.EAST, rot)
			},
			(rot: Byte) => {
				new BR(ForgeDirection.WEST, rot)
			},
			(rot: Byte) => {
				new BR(ForgeDirection.NORTH, NumberUtils.rotate(rot, 0, 4, 2).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.SOUTH, NumberUtils.rotate(rot, 0, 4, 2).byteValue())
			}),
		Array(
			(rot: Byte) => {
				new BR(ForgeDirection.UP, NumberUtils.rotate(rot, 0, 4, -1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.UP, NumberUtils.rotate(rot, 0, 4, 1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.WEST, rot)
			},
			(rot: Byte) => {
				new BR(ForgeDirection.EAST, rot)
			},
			(rot: Byte) => {
				new BR(ForgeDirection.SOUTH, rot)
			},
			(rot: Byte) => {
				new BR(ForgeDirection.NORTH, rot)
			}),
		Array(
			(rot: Byte) => {
				new BR(ForgeDirection.WEST, NumberUtils.rotate(rot, 0, 4, -1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.EAST, NumberUtils.rotate(rot, 0, 4, 1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.NORTH, NumberUtils.rotate(rot, 0, 4, 1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.NORTH, NumberUtils.rotate(rot, 0, 4, -1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.UP, rot)
			},
			(rot: Byte) => {
				new BR(ForgeDirection.DOWN, NumberUtils.rotate(rot, 0, 4, 2).byteValue())
			}),
		Array(
			(rot: Byte) => {
				new BR(ForgeDirection.EAST, NumberUtils.rotate(rot, 0, 4, -1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.WEST, NumberUtils.rotate(rot, 0, 4, 1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.SOUTH, NumberUtils.rotate(rot, 0, 4, -1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.SOUTH, NumberUtils.rotate(rot, 0, 4, 1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.DOWN, NumberUtils.rotate(rot, 0, 4, 2).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.UP, rot)
			}),
		Array(
			(rot: Byte) => {
				new BR(ForgeDirection.SOUTH, NumberUtils.rotate(rot, 0, 4, -1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.NORTH, NumberUtils.rotate(rot, 0, 4, 1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.DOWN, rot)
			},
			(rot: Byte) => {
				new BR(ForgeDirection.UP, rot)
			},
			(rot: Byte) => {
				new BR(ForgeDirection.WEST, NumberUtils.rotate(rot, 0, 4, 1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.WEST, NumberUtils.rotate(rot, 0, 4, -1).byteValue())
			}),
		Array(
			(rot: Byte) => {
				new BR(ForgeDirection.NORTH, NumberUtils.rotate(rot, 0, 4, -1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.SOUTH, NumberUtils.rotate(rot, 0, 4, 1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.UP, rot)
			},
			(rot: Byte) => {
				new BR(ForgeDirection.DOWN, rot)
			},
			(rot: Byte) => {
				new BR(ForgeDirection.EAST, NumberUtils.rotate(rot, 0, 4, -1).byteValue())
			},
			(rot: Byte) => {
				new BR(ForgeDirection.EAST, NumberUtils.rotate(rot, 0, 4, 1).byteValue())
			}))
}