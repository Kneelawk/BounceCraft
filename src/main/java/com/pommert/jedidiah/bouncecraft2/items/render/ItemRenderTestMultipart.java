package com.pommert.jedidiah.bouncecraft2.items.render;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import com.pommert.jedidiah.bouncecraft2.ref.ModRef;

import cpw.mods.fml.client.FMLClientHandler;

public class ItemRenderTestMultipart implements IItemRenderer {

	private static final IModelCustom model = AdvancedModelLoader
			.loadModel(new ResourceLocation(ModRef.MOD_ID,
					"models/blockBounce.obj"));
	private static final ResourceLocation texture = new ResourceLocation(
			ModRef.MOD_ID, "textures/blocks/blockBounce.png");

	public ItemRenderTestMultipart() {
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
		case ENTITY: {
			render(-0.5F, -0.1F, -0.5F);
			return;
		}
		case EQUIPPED: {
			render(0.0F, 0.3F, 0.0F);
			return;
		}
		case EQUIPPED_FIRST_PERSON: {
			render(0.0F, 0.5F, 0.0F);
			return;
		}
		case INVENTORY: {
			render(-1.0F, -0.9F, -1.0F);
			return;
		}
		default:
		}
	}

	private void render(float x, float y, float z) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		// Scale, Translate, Rotate
		GL11.glScalef(1F, 1F, 1F);
		GL11.glTranslatef(x, y, z);
		// GL11.glRotatef(-90F, 1F, 0, 0);

		// Bind texture
		FMLClientHandler.instance().getClient().renderEngine
				.bindTexture(texture);

		// Render
		model.renderPart("Cube");

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
