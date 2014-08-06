package com.pommert.jedidiah.bouncecraft2.fmp.logic

import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiPart
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic.Index
import net.minecraft.item.ItemStack
import com.pommert.jedidiah.bouncecraft2.items.BCItems
import net.minecraft.util.ResourceLocation
import com.pommert.jedidiah.bouncecraft2.ref.ModRef
import net.minecraft.entity.Entity
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side
import com.pommert.jedidiah.bouncecraft2.util.EntityUtil
import java.lang.Iterable
import codechicken.lib.vec.Cuboid6
import java.util.Arrays

class LowBounceBlockPartLogic(part: BCMultiPart, index: Index) extends BCPartLogic(part, index) {

	@Override
	@SideOnly(Side.CLIENT)
	def getModel = new ResourceLocation(ModRef.MOD_ID, "/models/blockLowBounce.obj")

	@Override
	@SideOnly(Side.CLIENT)
	def getTexture = new ResourceLocation(ModRef.MOD_ID, "/textures/blocks/blockLowBounce.png")

	@Override
	override def onEntityCollision(entity: Entity) {
		EntityUtil.fall(entity)
		PositionableMotionLogic.rotations(part.facing.getOpposite().ordinal())(entity, 2)
	}

	@Override
	override def getCollisionBoxes: Iterable[Cuboid6] = Arrays.asList[Cuboid6]()
}