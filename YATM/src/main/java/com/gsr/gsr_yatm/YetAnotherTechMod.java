package com.gsr.gsr_yatm;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(YetAnotherTechMod.MODID)
public class YetAnotherTechMod
{
	public static final String MODID = "gsr_yatm";
	public static final Logger LOGGER = LogUtils.getLogger();
	
	

	public YetAnotherTechMod()
	{
		ModLoadingContext.get().registerConfig(Type.COMMON, YATMConfigs.SPEC);
		YATMModEvents.register(FMLJavaModLoadingContext.get().getModEventBus());
		YATMGameEvents.register(MinecraftForge.EVENT_BUS);	
	} // end constructor
	
} // end outer class