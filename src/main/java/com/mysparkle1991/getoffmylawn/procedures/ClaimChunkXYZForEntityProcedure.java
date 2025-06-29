package com.mysparkle1991.getoffmylawn.procedures;

import com.mysparkle1991.getoffmylawn.init.ClaimModBlocks;
import com.mysparkle1991.getoffmylawn.network.ClaimModVariables;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;

public class ClaimChunkXYZForEntityProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        LevelChunk theChunk = (LevelChunk) world.getChunk(new BlockPos((int) x, (int) y, (int) z));
        Entity owner = entity;
        double y1 = -63.0;

        // Place the claim block
        world.setBlock(BlockPos.containing(theChunk.getPos().x, y1, theChunk.getPos().z),
                ClaimModBlocks.BEDROCK_CLAIM.get().defaultBlockState(), 3);

        if (entity instanceof Player player) {
            if (!player.level().isClientSide()) {
                player.displayClientMessage(Component.literal("§aChunk successfully claimed!"), false);
            }
        }

        // Update player claim count
        ClaimModVariables.PlayerVariables vars = entity.getData(ClaimModVariables.PLAYER_VARIABLES);
        vars.playerClaimCount = 1.0 + vars.playerClaimCount;
        vars.syncPlayerVariables(entity);

        // Set claim data
        SetClaimDataProcedure.execute(world, theChunk.getPos().x, y1, theChunk.getPos().z, entity);
    }
}