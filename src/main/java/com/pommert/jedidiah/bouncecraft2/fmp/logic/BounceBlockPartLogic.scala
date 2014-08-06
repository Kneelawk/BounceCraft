package com.pommert.jedidiah.bouncecraft2.fmp.logic

import net.minecraft.item.ItemStack
import com.pommert.jedidiah.bouncecraft2.items.BCItems
import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiPart
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic.Index
import com.pommert.jedidiah.bouncecraft2.ref.ModRef
import net.minecraft.util.ResourceLocation
import net.minecraft.nbt.NBTTagCompound
import codechicken.lib.data.MCDataInput
import codechicken.lib.data.MCDataOutput
import net.minecraft.entity.Entity
import net.minecraftforge.common.util.ForgeDirection
import codechicken.lib.vec.Quat
import codechicken.lib.vec.Vector3
import com.pommert.jedidiah.bouncecraft2.log.BCLog
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side
import com.pommert.jedidiah.bouncecraft2.util.EntityUtil
import codechicken.lib.vec.Cuboid6
import java.util.Arrays
import java.lang.Iterable

class BounceBlockPartLogic(part: BCMultiPart, index: Index) extends BCPartLogic(part, index) {

	@Override
	@SideOnly(Side.CLIENT)
	def getTexture = new ResourceLocation(ModRef.MOD_ID, "/textures/blocks/blockBounce.png")

	@Override
	@SideOnly(Side.CLIENT)
	def getModel = new ResourceLocation(ModRef.MOD_ID, "/models/blockBounce.obj")

	@Override
	override def onEntityCollision(entity: Entity) {
		EntityUtil.fall(entity)
		PositionableMotionLogic.rotations(part.facing.getOpposite().ordinal())(entity, 1)
	}

	@Override
	override def getCollisionBoxes: Iterable[Cuboid6] = Arrays.asList[Cuboid6]()
}