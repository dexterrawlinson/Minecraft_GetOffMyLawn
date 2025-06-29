package com.mysparkle1991.getoffmylawn.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ClaimInfoProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        double cY;
        double cX;
        double cZ;

        if (CheckIfChunkXYZIsClaimedProcedure.execute(world, x, y, z)) {
            cY = YCoordsOfBedrockClaimProcedure.execute(world, x, y, z);
            cX = (world.getChunk(new BlockPos((int) x, (int) y, (int) z)).getPos()).x;
            cZ = (world.getChunk(new BlockPos((int) x, (int) y, (int) z)).getPos()).z;

            BlockPos claimPos = BlockPos.containing(cX, cY, cZ);
            BlockEntity blockEntity = world.getBlockEntity(claimPos);

            if (blockEntity != null && entity instanceof Player player) {
                if (!player.level().isClientSide()) {
                    var data = blockEntity.getPersistentData();
                    String claimName = data.getString("claimname");
                    String ownerDisplay = data.getString("ownerdisplay");
                    String category = data.getString("category");
                    String claimDate = data.getString("Cdate");

                    player.displayClientMessage(Component.literal("§aChunk Name: §e" + claimName), false);
                    player.displayClientMessage(Component.literal("§aOwner: §e" + ownerDisplay), false);
                    player.displayClientMessage(Component.literal("§aCategory: §e" + category), false);
                    player.displayClientMessage(Component.literal("§aClaimed Since: §e" + claimDate), false);
                }
            }
        } else if (entity instanceof Player player) {
            if (!player.level().isClientSide()) {
                player.displayClientMessage(Component.literal("§cThis area is currently unclaimed. It is part of the §aWildlands§c."), false);
            }
        }
    }
}