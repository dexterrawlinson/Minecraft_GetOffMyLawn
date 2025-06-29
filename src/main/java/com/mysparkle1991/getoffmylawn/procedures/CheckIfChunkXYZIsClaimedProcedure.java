package com.mysparkle1991.getoffmylawn.procedures;

import com.mysparkle1991.getoffmylawn.init.ClaimModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;

public class CheckIfChunkXYZIsClaimedProcedure {
    public static boolean execute(LevelAccessor world, double x, double y, double z) {
        LevelChunk theChunk = (LevelChunk) world.getChunk(new BlockPos((int) x, (int) y, (int) z));
        double y1 = -63.0;

        if (ClaimModBlocks.BEDROCK_CLAIM.get() == world.getBlockState(BlockPos.containing(theChunk.getPos().x, y1, theChunk.getPos().z)).getBlock()) {
            return true;
        }

        return false;
    }
}