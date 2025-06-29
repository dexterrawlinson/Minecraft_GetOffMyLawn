// Fix 7: ClaimChunkProcedure.java - Fix imports and getGameRules()
package com.mysparkle1991.getoffmylawn.procedures;

import com.mysparkle1991.getoffmylawn.init.ClaimModGameRules;
import com.mysparkle1991.getoffmylawn.network.ClaimModVariables;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;

public class ClaimChunkProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        LevelChunk chunkToClaim = (LevelChunk) world.getChunk(new BlockPos((int) x, (int) y, (int) z));

        // Get game rules from server if available
        boolean claimPermission = true;
        int maxClaimCount = 5;

        if (world instanceof Level level && !level.isClientSide && level.getServer() != null) {
            claimPermission = level.getServer().getGameRules().getBoolean(ClaimModGameRules.CLAIM_PERMISSION);
            maxClaimCount = level.getServer().getGameRules().getInt(ClaimModGameRules.MAX_CLAIM_COUNT);
        }

        if (claimPermission) {
            if (!CheckIfChunkXYZIsClaimedProcedure.execute(world, x, y, z)) {
                if (entity.getData(ClaimModVariables.PLAYER_VARIABLES).playerClaimCount < maxClaimCount) {
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
        } else if (entity instanceof Player player && player.hasPermissions(4)) {
            if (!CheckIfChunkXYZIsClaimedProcedure.execute(world, x, y, z)) {
                if (entity.getData(ClaimModVariables.PLAYER_VARIABLES).playerClaimCount < maxClaimCount) {
                    ClaimChunkXYZForEntityProcedure.execute(world, x, y, z, entity);
                } else if (!player.level().isClientSide()) {
                    player.displayClientMessage(Component.literal("§cYou have reached the maximum number of claimable chunks."), false);
                }
            } else if (!player.level().isClientSide()) {
                player.displayClientMessage(Component.literal("§4This chunk is already claimed."), false);
            }
        } else if (entity instanceof Player player) {
            if (!player.level().isClientSide()) {
                player.displayClientMessage(Component.literal("§cClaiming is restricted to OPs only. You do not have permission to create claims."), false);
            }
        }
    }
}