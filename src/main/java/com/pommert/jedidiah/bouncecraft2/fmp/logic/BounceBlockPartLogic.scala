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

class BounceBlockPartLogic(part: BCMultiPart, index: Index) extends BCPartLogic(part, index) {

	@Override
	def getItem = new ItemStack(BCItems.items.get("itemBCMultiPart"), 1, 0)

	@Override
	def getTexture = new ResourceLocation(ModRef.MOD_ID, "/textures/blocks/blockBounce.png")

	@Override
	def getModel = new ResourceLocation(ModRef.MOD_ID, "/models/blockBounce.obj")

	@Override
	def load(tag: NBTTagCompound) {}

	@Override
	def save(tag: NBTTagCompound) {}

	@Override
	def readDesc(packet: MCDataInput) {}

	@Override
	def writeDesc(packet: MCDataOutput) {}

	@Override
	override def onEntityCollision(entity: Entity) {
		// TODO add moving stuff
	}
}