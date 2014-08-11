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
import com.pommert.jedidiah.bouncecraft2.ref.RecRef

class NullBCPartLogic(part: BCMultiPart, index: Index) extends BCPartLogic(part, index) {

	@Override
	override def getItem = null

	@Override
	@SideOnly(Side.CLIENT)
	def getTexture = RecRef.Textures.NULL_BLOCK

	@Override
	@SideOnly(Side.CLIENT)
	def getModel = RecRef.Models.NULL_BLOCK
}