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
import com.pommert.jedidiah.bouncecraft2.ref.RecRef

class BounceBlockPartLogic(part: BCMultiPart, index: Index) extends BCPartLogic(part, index) {

	@Override
	@SideOnly(Side.CLIENT)
	def getTexture = RecRef.Textures.BOUNCE_BLOCK

	@Override
	@SideOnly(Side.CLIENT)
	def getModel = RecRef.Models.BOUNCE_BLOCK

	@Override
	override def onEntityCollision(entity: Entity) {
		EntityUtil.fall(entity)
		PositionableMotionLogic.move(entity, part.facing.getOpposite(), 1)
	}
}