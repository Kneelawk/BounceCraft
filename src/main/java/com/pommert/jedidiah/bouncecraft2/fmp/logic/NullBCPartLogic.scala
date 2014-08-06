package com.pommert.jedidiah.bouncecraft2.fmp.logic

import com.pommert.jedidiah.bouncecraft2.ref.ModRef
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side
import net.minecraft.util.ResourceLocation
import net.minecraft.nbt.NBTTagCompound
import codechicken.lib.data.MCDataInput
import codechicken.lib.data.MCDataOutput
import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiPart
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic.Index

class NullBCPartLogic(part: BCMultiPart, index: Index) extends BCPartLogic(part, index) {

	@Override
	override def getItem = null

	@Override
	@SideOnly(Side.CLIENT)
	def getTexture = new ResourceLocation(ModRef.MOD_ID, "textures/blocks/blockMissingTexture.png")

	@Override
	@SideOnly(Side.CLIENT)
	def getModel = new ResourceLocation(ModRef.MOD_ID, "models/blockMissingTexture.obj")
}