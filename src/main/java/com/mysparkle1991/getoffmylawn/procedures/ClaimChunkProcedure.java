package com.mysparkle1991.getoffmylawn.procedures;

import com.mysparkle1991.getoffmylawn.init.ClaimModGameRules;
import com.mysparkle1991.getoffmylawn.network.ClaimModVariables;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;

public class ClaimChunkProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        LevelChunk chunkToClaim = (LevelChunk) world.getChunk(new BlockPos((int) x, (int) y, (int) z));

        if (world.getLevelData().getGameRules().getBoolean(ClaimModGameRules.CLAIM_PERMISSION)) {
            if (!CheckIfChunkXYZIsClaimedProcedure.execute(world, x, y, z)) {
                if (entity.getData(ClaimModVariables.PLAYER_VARIABLES).playerClaimCount < world.getLevelData().getGameRules().getInt(ClaimModGameRules.MAX_CLAIM_COUNT)) {
                    ClaimChunkXYZForEntityProcedure.execute(world, x, y, z, entity);
                } else if (entity instanceof Player player) {
                    if (!player.level().isClientSide()) {
                        player.displayClientMessage(Component.literal("§cYou have reached the maximum number of claimable chunks."), false);
                    }
                }
            } else if (entity instanceof Player player) {
                if (!player.level().isClientSide()) {
                    player.displayClientMessage(Component.literal("§4This chunk is already claimed."), false);
                }
            }
        } else if (entity.hasPermissions(4)) {
            if (!CheckIfChunkXYZIsClaimedProcedure.execute(world, x, y, z)) {
                if (entity.getData(ClaimModVariables.PLAYER_VARIABLES).playerClaimCount < world.getLevelData().getGameRules().getInt(ClaimModGameRules.MAX_CLAIM_COUNT)) {
                    ClaimChunkXYZForEntityProcedure.execute(world, x, y, z, entity);
                } else if (entity instanceof Player player) {
                    if (!player.level().isClientSide()) {
                        player.displayClientMessage(Component.literal("§cYou have reached the maximum number of claimable chunks."), false);
                    }
                }
            } else if (entity instanceof Player player) {
                if (!player.level().isClientSide()) {
                    player.displayClientMessage(Component.literal("§4This chunk is already claimed."), false);
                }
            }
        } else if (entity instanceof Player player) {
            if (!player.level().isClientSide()) {
                player.displayClientMessage(Component.literal("§cClaiming is restricted to OPs only. You do not have permission to create claims."), false);
            }
        }
    }
}