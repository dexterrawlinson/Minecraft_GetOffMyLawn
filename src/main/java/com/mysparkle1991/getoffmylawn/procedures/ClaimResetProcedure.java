package com.mysparkle1991.getoffmylawn.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

public class ClaimResetProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity instanceof Player player) {
            if (!player.level().isClientSide()) {
                player.displayClientMessage(Component.literal("§eReset function coming soon!"), false);
            }
        }
    }
}