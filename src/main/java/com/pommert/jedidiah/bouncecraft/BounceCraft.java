package com.pommert.jedidiah.bouncecraft;

import com.pommert.jedidiah.bouncecraft.blocks.BCBlocks;
import com.pommert.jedidiah.bouncecraft.fmp.BCFMP;
import com.pommert.jedidiah.bouncecraft.items.BCItems;
import com.pommert.jedidiah.bouncecraft.log.BCLog;
import com.pommert.jedidiah.bouncecraft.proxy.IProxy;
import com.pommert.jedidiah.bouncecraft.ref.ClassPathRef;
import com.pommert.jedidiah.bouncecraft.ref.ModRef;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModRef.MOD_ID)
public class BounceCraft {

	@Mod.Instance(ModRef.MOD_ID)
	private static BounceCraft instance;

	@SidedProxy(clientSide = ClassPathRef.PROXY_CLIENT, serverSide = ClassPathRef.PROXY_SERVER)
	private static IProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		BCLog.init(event.getModLog());
		proxy.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}

	public static BounceCraft getBC() {
		return instance;
	}

	public static IProxy getProxy() {
		return proxy;
	}
}
