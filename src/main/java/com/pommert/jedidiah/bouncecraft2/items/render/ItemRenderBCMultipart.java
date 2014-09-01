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
import com.pommert.jedidiah.bouncecraft2.log.BCLog;
import com.pommert.jedidiah.bouncecraft2.util.ByteMap;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemRenderBCMultipart implements IItemRenderer {

	private static final ByteMap<IModelCustom> models = new ByteMap<IModelCustom>();
	private static final ByteMap<ResourceLocation> textures = new ByteMap<ResourceLocation>();

	public static void createTextures() {
		Set<Byte> keys = Index.VALUES.keySet();
		for (Byte key : keys) {
			if (Index.VALUES.containsKey(key)) {
				BCPartLogic logic = BCPartLogic.newLogic(key.byteValue(), null);
				models.put(key, AdvancedModelLoader.loadModel(logic.getModel()));
				textures.put(key, logic.getTexture());
			}
		}
		System.out.println("models: " + models);
		System.out.println("textures: " + textures);
	}

	public ItemRenderBCMultipart() {
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
				.bindTexture((ResourceLocation) textures.get(Byte
						.valueOf((byte) damage)));

		// Render
		((IModelCustom) models.get(Byte.valueOf((byte) damage))).renderAll();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
