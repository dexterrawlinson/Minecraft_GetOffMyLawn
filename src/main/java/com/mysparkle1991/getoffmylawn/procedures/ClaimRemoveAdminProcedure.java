package com.mysparkle1991.getoffmylawn.procedures;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

public class ClaimRemoveAdminProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, CommandContext<CommandSourceStack> arguments, Entity entity) {
        if (entity instanceof Player player) {
            if (!player.level().isClientSide()) {
                player.displayClientMessage(Component.literal("§eAdmin system coming soon!"), false);
            }
        }
    }
}