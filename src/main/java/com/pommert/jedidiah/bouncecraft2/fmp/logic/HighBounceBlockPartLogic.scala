package com.pommert.jedidiah.bouncecraft2.fmp.logic

import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiPart
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic.Index
import com.pommert.jedidiah.bouncecraft2.ref.ModRef
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.util.ResourceLocation
import cpw.mods.fml.relauncher.Side
import org.apache.http.util.EntityUtils
import net.minecraft.entity.Entity
import com.pommert.jedidiah.bouncecraft2.util.EntityUtil
import java.lang.Iterable
import codechicken.lib.vec.Cuboid6
import java.util.Arrays
import com.pommert.jedidiah.bouncecraft2.ref.RecRef

class HighBounceBlockPartLogic(part: BCMultiPart, index: Index) extends BCPartLogic(part, index) {

	@Override
	@SideOnly(Side.CLIENT)
	def getModel = RecRef.Models.HIGH_BOUNCE_PART

	@Override
	@SideOnly(Side.CLIENT)
	def getTexture = RecRef.Textures.HIGH_BOUNCE_PART

	@Override
	override def onEntityCollision(entity: Entity) {
		EntityUtil.fall(entity)
		PositionableMotionLogic.move(entity, part.facing.getOpposite(), 5)
	}
}