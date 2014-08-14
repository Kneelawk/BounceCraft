package com.pommert.jedidiah.bouncecraft2.fmp.logic

import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiBlock
import cpw.mods.fml.relauncher.SideOnly
import codechicken.lib.vec.Vector3
import cpw.mods.fml.relauncher.Side
import net.minecraftforge.client.model.AdvancedModelLoader
import com.pommert.jedidiah.bouncecraft2.ref.RecRef
import com.pommert.jedidiah.bouncecraft2.fmp.logic.render.ModelRender

class NullBCBlockLogic(block: BCMultiBlock, id: BCBlockLogic.Index) extends BCBlockLogic(block, id) {

	@Override
	@SideOnly(Side.CLIENT)
	def renderBlock(pos: Vector3, f: Float) {
		ModelRender.render(NullBCBlockLogic.model, RecRef.Textures.NULL_BLOCK)
	}

	@Override
	@SideOnly(Side.CLIENT)
	def renderHand() {
		ModelRender.render(NullBCBlockLogic.model, RecRef.Textures.NULL_BLOCK)
	}
}

object NullBCBlockLogic {
	@SideOnly(Side.CLIENT)
	var model = AdvancedModelLoader.loadModel(RecRef.Models.NULL_BLOCK)
}