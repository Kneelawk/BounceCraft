package com.pommert.jedidiah.bouncecraft2.items.render;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic;
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic.Index;
import com.pommert.jedidiah.bouncecraft2.ref.ModRef;

import cpw.mods.fml.client.FMLClientHandler;

public class ItemRenderTestMultipart implements IItemRenderer {

	private static final IModelCustom[] models = new IModelCustom[Index.VALUES.length];
	private static final ResourceLocation[] textures = new ResourceLocation[Index.VALUES.length];

	static {
		for (int i = 0; i < Index.VALUES.length; i++) {
			if (Index.VALUES[i] != null) {
				BCPartLogic logic = BCPartLogic.newLogic(i, null);
				models[i] = AdvancedModelLoader.loadModel(logic.getModel());
				textures[i] = logic.getTexture();
			}
		}
	}

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
			render(-0.5F, -0.1F, -0.5F, item.getItemDamage());
			return;
		}
		case EQUIPPED: {
			render(0.0F, 0.3F, 0.0F, item.getItemDamage());
			return;
		}
		case EQUIPPED_FIRST_PERSON: {
			render(0.0F, 0.5F, 0.0F, item.getItemDamage());
			return;
		}
		case INVENTORY: {
			render(-1.0F, -0.9F, -1.0F, item.getItemDamage());
			return;
		}
		default:
		}
	}

	private void render(float x, float y, float z, int damage) {
		if (Index.VALUES[damage] == null)
			damage = Index.NULL_BCPARTLOGIC.getId();

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		// Scale, Translate, Rotate
		GL11.glScalef(1F, 1F, 1F);
		GL11.glTranslatef(x + 0.5F, y, z + 0.5F);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glTranslatef(-0.5F, -1F / 8F, -0.5F);

		// Bind texture
		FMLClientHandler.instance().getClient().renderEngine
				.bindTexture(textures[damage]);

		// Render
		models[damage].renderPart("Cube");

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
