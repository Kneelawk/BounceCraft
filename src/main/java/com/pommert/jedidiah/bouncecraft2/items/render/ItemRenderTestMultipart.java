package com.pommert.jedidiah.bouncecraft2.items.render;

import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic;
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic.Index;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemRenderTestMultipart implements IItemRenderer {

	private static final IModelCustom[] models = new IModelCustom[Index.VALUES
			.largestKey() + 1];
	private static final ResourceLocation[] textures = new ResourceLocation[Index.VALUES
			.largestKey() + 1];

	public static void createTextures() {
		Set<Object> keys = Index.VALUES.keySet();
		for (Object key : keys) {
			if (Index.VALUES.containsKey(key)) {
				BCPartLogic logic = BCPartLogic.newLogic(
						((Byte) key).byteValue(), null);
				models[((Byte) key).byteValue()] = AdvancedModelLoader
						.loadModel(logic.getModel());
				textures[((Byte) key).byteValue()] = logic.getTexture();
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
		if (Index.VALUES.get((byte) damage) == null)
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
		models[damage].renderAll();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
