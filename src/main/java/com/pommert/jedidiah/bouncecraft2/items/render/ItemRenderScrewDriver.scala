package com.pommert.jedidiah.bouncecraft2.items.render

import net.minecraftforge.client.IItemRenderer
import net.minecraft.item.ItemStack
import net.minecraftforge.client.IItemRenderer.ItemRenderType
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper
import net.minecraftforge.client.model.AdvancedModelLoader
import com.pommert.jedidiah.bouncecraft2.ref.ModRef
import net.minecraft.util.ResourceLocation
import cpw.mods.fml.client.FMLClientHandler
import org.lwjgl.opengl.GL11

class ItemRenderScrewDriver extends IItemRenderer {

	@Override
	def handleRenderType(stack: ItemStack, irt: ItemRenderType) = true

	@Override
	def shouldUseRenderHelper(irt: ItemRenderType, stack: ItemStack, helper: ItemRendererHelper) = true

	@Override
	def renderItem(irt: ItemRenderType, item: ItemStack, data: Object*) {
		irt match {
			case ItemRenderType.ENTITY => {
				render(0.5F, -0.1F, 0.5F)
				return
			}
			case ItemRenderType.EQUIPPED => {
				GL11.glRotatef(65, -1, 0, 1)
				render(1.2F, -0.9F, 1.2F)
				return
			}
			case ItemRenderType.EQUIPPED_FIRST_PERSON => {
				render(1.2F, 0.5F, 1.2F)
				return
			}
			case ItemRenderType.INVENTORY => {
				render(-1.0F, -1.9F, -1.0F)
				return
			}
			case ItemRenderType.FIRST_PERSON_MAP => return
		}
	}

	private def render(x: Float, y: Float, z: Float) {
		GL11.glPushMatrix()
		GL11.glDisable(GL11.GL_LIGHTING)

		// Scale, Translate, Rotate
		GL11.glScalef(1F, 1F, 1F)
		GL11.glTranslatef(x, y, z)

		// Bind texture
		FMLClientHandler.instance().getClient().renderEngine
			.bindTexture(ItemRenderScrewDriver.texture)

		// Render
		ItemRenderScrewDriver.model.renderAll()

		GL11.glEnable(GL11.GL_LIGHTING)
		GL11.glPopMatrix()
	}
}

object ItemRenderScrewDriver {
	val model = AdvancedModelLoader.loadModel(new ResourceLocation(ModRef.MOD_ID, "/models/itemScrewDriver.obj"))

	val texture = new ResourceLocation(ModRef.MOD_ID, "/textures/items/itemScrewDriver.png")
}