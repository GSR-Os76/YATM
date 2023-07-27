package com.gsr.gsr_yatm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class YATMRuleCommand
{
	public static final String PLANT_DATA_SAVE_KEY = "unboundHorizontalGrowth";
	
	public static final String BASE_PARAMETER = "yatm";
	public static final String READ_PARAMETER = "read";
	public static final String UNBOUND_HORIZONTAL_GROWTH_PARAMETER = "unboundHorizontalGrowth";
	
	public static final String SET_UNBOUND_HORIZONTAL_GROWTH_TRANSLATION_KEY = "command." + YATMRuleCommand.BASE_PARAMETER + "." + YATMRuleCommand.UNBOUND_HORIZONTAL_GROWTH_PARAMETER;
	public static final String READ_UNBOUND_HORIZONTAL_GROWTH_TRANSLATION_KEY = "command." + YATMRuleCommand.BASE_PARAMETER + "." + YATMRuleCommand.UNBOUND_HORIZONTAL_GROWTH_PARAMETER + "." + YATMRuleCommand.READ_PARAMETER;
	
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		dispatcher.register(Commands.literal(YATMRuleCommand.BASE_PARAMETER)
				.requires((stack) -> stack.hasPermission(2))
				.then(Commands.literal(YATMRuleCommand.UNBOUND_HORIZONTAL_GROWTH_PARAMETER)
						.then(
								Commands.argument(YATMRuleCommand.UNBOUND_HORIZONTAL_GROWTH_PARAMETER, BoolArgumentType.bool())
								.executes((s) -> YATMRuleCommand.setUnboundHorizontalGrowth(s.getSource(), BoolArgumentType.getBool(s, UNBOUND_HORIZONTAL_GROWTH_PARAMETER))
										)
								)
						.then(Commands.literal(YATMRuleCommand.READ_PARAMETER)
								.executes((s) -> YATMRuleCommand.readUnboundHorizontalGrowth(s.getSource())
										)
								)
						)
				);
	} // end register
	
	
	
	
	private static int setUnboundHorizontalGrowth(CommandSourceStack context, boolean value) 
	{
		ServerLevel level = context.getLevel();
		level.getDataStorage().computeIfAbsent((t) -> PlantData.from(t), () -> new PlantData(), YATMRuleCommand.PLANT_DATA_SAVE_KEY).unboundHorizontalGrow(value);;
		context.sendSuccess(() -> Component.translatable(YATMRuleCommand.SET_UNBOUND_HORIZONTAL_GROWTH_TRANSLATION_KEY, value)/* .append(Component.literal(level.toString())) */, true);
		return value ? 1 : 0;
	} // end setUnboundHorizontalGrowth()
	
	private static int readUnboundHorizontalGrowth(CommandSourceStack context) 
	{
		ServerLevel level = context.getLevel();
		boolean value = level.getDataStorage().computeIfAbsent((t) -> PlantData.from(t), () -> new PlantData(), YATMRuleCommand.PLANT_DATA_SAVE_KEY).isHorizontalGrowthBound();
		context.sendSuccess(() -> Component.translatable(YATMRuleCommand.READ_UNBOUND_HORIZONTAL_GROWTH_TRANSLATION_KEY, value), false);
		return 1;
	} // end readUnboundHorizontalGrowth()
	
} // end class