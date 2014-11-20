package com.pommert.jedidiah.bouncecraft2.fmp.logic

import java.lang.Iterable
import java.util.ArrayList
import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiBlock
import com.pommert.jedidiah.bouncecraft2.fmp.logic.render.ModelRender
import com.pommert.jedidiah.bouncecraft2.ref.RecRef
import codechicken.lib.vec.Cuboid6
import codechicken.lib.vec.Vector3
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.entity.Entity
import net.minecraftforge.client.model.AdvancedModelLoader
import cpw.mods.fml.relauncher.Side

class MoveGrateBlockLogic(block: BCMultiBlock, id: BCBlockLogic.Index) extends BCBlockLogic(block, id) {

	@Override
	@SideOnly(Side.CLIENT)
	def renderBlock(pos: Vector3, f: Float) {
		ModelRender.render(MoveGrateBlockLogic.model, RecRef.Textures.MOVE_GRATE)
	}

	@Override
	@SideOnly(Side.CLIENT)
	def renderHand() {
		ModelRender.render(MoveGrateBlockLogic.model, RecRef.Textures.MOVE_GRATE)
	}

	@Override
	override def onEntityCollision(e: Entity) {
		e.fallDistance = 0
		if (e.isSneaking())
			PositionableMotionLogic.move(e, getBlock.dir, -0.06)
		else
			PositionableMotionLogic.move(e, getBlock.dir, 0.2)
	}
	
	@Override
	override def getCollisionBoxes(): Iterable[Cuboid6] = {
		new ArrayList[Cuboid6]()
	}
}

object MoveGrateBlockLogic {
	@SideOnly(Side.CLIENT)
	var model = AdvancedModelLoader.loadModel(RecRef.Models.MOVE_GRATE)
}