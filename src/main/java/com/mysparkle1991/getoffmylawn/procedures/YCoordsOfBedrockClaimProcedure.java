package com.mysparkle1991.getoffmylawn.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;

public class YCoordsOfBedrockClaimProcedure {
    public static double execute(LevelAccessor world, double x, double y, double z) {
        LevelChunk theChunk = (LevelChunk) world.getChunk(new BlockPos((int) x, (int) y, (int) z));
        double y1 = -63.0;

        if (CheckIfChunkXYZIsClaimedProcedure.execute(world, x, y, z)) {
            return y1;
        }

        return -999.0;
    }
}