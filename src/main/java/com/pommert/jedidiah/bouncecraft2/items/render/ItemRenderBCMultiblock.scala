package com.pommert.jedidiah.bouncecraft2.items.render

import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side
import net.minecraftforge.client.IItemRenderer
import net.minecraft.item.ItemStack
import net.minecraftforge.client.IItemRenderer.ItemRenderType
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCBlockLogic
import org.lwjgl.opengl.GL11
import com.pommert.jedidiah.bouncecraft2.util.ByteMap
import codechicken.lib.vec.Vector3

@SideOnly(Side.CLIENT)
class ItemRenderBCMultiblock extends IItemRenderer {

	@Override
	def handleRenderType(stack: ItemStack, irt: ItemRenderType) = true

	@Override
	def shouldUseRenderHelper(irt: ItemRenderType, stack: ItemStack, helper: ItemRendererHelper) = true

	@Override
	def renderItem(irt: ItemRenderType, item: ItemStack, data: Object*) {
		irt match {
			case ItemRenderType.ENTITY => {
				render(-0.5F, -0.1F, -0.5F, item.getItemDamage())
				return
			}
			case ItemRenderType.EQUIPPED => {
				render(0.0F, 0.3F, 0.0F, item.getItemDamage())
				return
			}
			case ItemRenderType.EQUIPPED_FIRST_PERSON => {
				render(0.0F, 0.5F, 0.0F, item.getItemDamage())
				return
			}
			case ItemRenderType.INVENTORY => {
				render(-1.0F, 0.0F, -1.0F, item.getItemDamage())
				return
			}
			case ItemRenderType.FIRST_PERSON_MAP => return
		}
	}

	private def render(x: Float, y: Float, z: Float, d: Int) {
		var damage = d.byteValue()
		if (!ItemRenderBCMultiblock.logics.containsKey(damage))
			damage = BCBlockLogic.Index.NULL_BCBLOCKLOGIC.getId

		GL11.glPushMatrix()
		GL11.glScalef(1, 1, 1)
		GL11.glTranslatef(x + 0.5F, y, z + 0.5F)
		GL11.glRotatef(180, 1, 0, 0)
		GL11.glTranslatef(-0.5f, -1F / 8F, -0.5f)

		ItemRenderBCMultiblock.logics.get(damage).renderHand

		GL11.glPopMatrix()
	}
}

object ItemRenderBCMultiblock {
	val logics = new ByteMap[BCBlockLogic]

	val keys = BCBlockLogic.Index.VALUES.keySet
	val keyIt = keys.iterator()

	while (keyIt.hasNext()) {
		val key = keyIt.next()

		if (BCBlockLogic.Index.VALUES.containsKey(key)) {
			val logic = BCBlockLogic.newLogic(null, key)
			logics.put(key, logic)
		}
	}
}