package com.pommert.jedidiah.bouncecraft2.fmp.logic

import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiPart
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic.Index
import com.pommert.jedidiah.bouncecraft2.ref.ModRef
import com.pommert.jedidiah.bouncecraft2.util.EntityUtil
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import cpw.mods.fml.relauncher.Side
import com.pommert.jedidiah.bouncecraft2.ref.RecRef

class MediumSpeedBlockPartLogic(part: BCMultiPart, index: Index) extends BCPartLogic(part, index) {

	@Override
	@SideOnly(Side.CLIENT)
	def getModel = RecRef.Models.MEDIUM_SPEED_BLOCK

	@Override
	@SideOnly(Side.CLIENT)
	def getTexture = RecRef.Textures.MEDIUM_SPEED_BLOCK

	@Override
	override def onEntityCollision(entity: Entity) {
		EntityUtil.fall(entity)
		val dir = RotatableDirectionLogic.getDirection(part.facing, part.rotation)
		PositionableMotionLogic.move(entity, dir, 3)
	}
}