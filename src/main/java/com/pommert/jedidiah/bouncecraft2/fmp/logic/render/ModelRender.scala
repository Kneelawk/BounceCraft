package com.pommert.jedidiah.bouncecraft2.fmp.logic.render

import cpw.mods.fml.relauncher.SideOnly
import codechicken.lib.vec.Vector3
import cpw.mods.fml.relauncher.Side
import net.minecraftforge.client.model.IModelCustom
import net.minecraft.util.ResourceLocation
import net.minecraft.client.Minecraft

object ModelRender {
	@SideOnly(Side.CLIENT)
	def render(model: IModelCustom, texture: ResourceLocation){
		Minecraft.getMinecraft().renderEngine.bindTexture(texture)
		model.renderAll()
	}
}