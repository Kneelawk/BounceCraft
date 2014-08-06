package com.pommert.jedidiah.bouncecraft2.fmp.logic

import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiPart
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic.Index
import cpw.mods.fml.relauncher.SideOnly
import com.pommert.jedidiah.bouncecraft2.ref.ModRef
import net.minecraft.util.ResourceLocation
import cpw.mods.fml.relauncher.Side
import net.minecraft.entity.Entity
import com.pommert.jedidiah.bouncecraft2.util.EntityUtil

class MediumBounceBlockPartLogic(part: BCMultiPart, index: Index) extends BCPartLogic(part, index) {

	@Override
	@SideOnly(Side.CLIENT)
	def getModel = new ResourceLocation(ModRef.MOD_ID, "/models/blockMediumBounce.obj")

	@Override
	@SideOnly(Side.CLIENT)
	def getTexture = new ResourceLocation(ModRef.MOD_ID, "/textures/blocks/blockMediumBounce.png")

	@Override
	override def onEntityCollision(entity: Entity) {
		EntityUtil.fall(entity)
		PositionableMotionLogic.rotations(part.facing.getOpposite().ordinal())(entity, 3)
	}
}