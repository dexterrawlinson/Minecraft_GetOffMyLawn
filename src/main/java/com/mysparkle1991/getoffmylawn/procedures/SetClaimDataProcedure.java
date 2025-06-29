package com.mysparkle1991.getoffmylawn.procedures;

import java.util.Calendar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SetClaimDataProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        // Set owner UUID
        if (!world.isClientSide()) {
            BlockPos bp = BlockPos.containing(x, y, z);
            BlockEntity blockEntity = world.getBlockEntity(bp);
            BlockState bs = world.getBlockState(bp);
            if (blockEntity != null) {
                blockEntity.getPersistentData().putString("owneruuid", entity.getStringUUID());
            }
            if (world instanceof Level level) {
                level.sendBlockUpdated(bp, bs, bs, 3);
            }
        }

        // Set owner display name
        if (!world.isClientSide()) {
            BlockPos bp = BlockPos.containing(x, y, z);
            BlockEntity blockEntity = world.getBlockEntity(bp);
            BlockState bs = world.getBlockState(bp);
            if (blockEntity != null) {
                blockEntity.getPersistentData().putString("ownerdisplay", entity.getDisplayName().getString());
            }
            if (world instanceof Level level) {
                level.sendBlockUpdated(bp, bs, bs, 3);
            }
        }

        // Set default claim name
        if (!world.isClientSide()) {
            BlockPos bp = BlockPos.containing(x, y, z);
            BlockEntity blockEntity = world.getBlockEntity(bp);
            BlockState bs = world.getBlockState(bp);
            if (blockEntity != null) {
                blockEntity.getPersistentData().putString("claimname", entity.getDisplayName().getString() + "'s claim");
            }
            if (world instanceof Level level) {
                level.sendBlockUpdated(bp, bs, bs, 3);
            }
        }

        // Set default category
        if (!world.isClientSide()) {
            BlockPos bp = BlockPos.containing(x, y, z);
            BlockEntity blockEntity = world.getBlockEntity(bp);
            BlockState bs = world.getBlockState(bp);
            if (blockEntity != null) {
                blockEntity.getPersistentData().putString("category", "None");
            }
            if (world instanceof Level level) {
                level.sendBlockUpdated(bp, bs, bs, 3);
            }
        }

        // Set claim date
        if (!world.isClientSide()) {
            BlockPos bp = BlockPos.containing(x, y, z);
            BlockEntity blockEntity = world.getBlockEntity(bp);
            BlockState bs = world.getBlockState(bp);
            if (blockEntity != null) {
                Calendar calendar = Calendar.getInstance();
                String date = calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                        (calendar.get(Calendar.MONTH) + 1) + "/" +
                        calendar.get(Calendar.YEAR);
                blockEntity.getPersistentData().putString("Cdate", date);
            }
            if (world instanceof Level level) {
                level.sendBlockUpdated(bp, bs, bs, 3);
            }
        }

        // Initialize admin count
        if (!world.isClientSide()) {
            BlockPos bp = BlockPos.containing(x, y, z);
            BlockEntity blockEntity = world.getBlockEntity(bp);
            BlockState bs = world.getBlockState(bp);
            if (blockEntity != null) {
                blockEntity.getPersistentData().putDouble("admincount", 0.0);
            }
            if (world instanceof Level level) {
                level.sendBlockUpdated(bp, bs, bs, 3);
            }
        }

        // Set public/private status (default private)
        if (!world.isClientSide()) {
            BlockPos bp = BlockPos.containing(x, y, z);
            BlockEntity blockEntity = world.getBlockEntity(bp);
            BlockState bs = world.getBlockState(bp);
            if (blockEntity != null) {
                blockEntity.getPersistentData().putBoolean("Cpublic", false);
            }
            if (world instanceof Level level) {
                level.sendBlockUpdated(bp, bs, bs, 3);
            }
        }
    }
}