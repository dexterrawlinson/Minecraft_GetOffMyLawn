// Fix 5: ClaimMenu.java - Fix getGameRules() access
package com.mysparkle1991.getoffmylawn.gui;

import com.mysparkle1991.getoffmylawn.init.ClaimModMenuTypes;
import com.mysparkle1991.getoffmylawn.network.ClaimModVariables;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ClaimMenu extends AbstractContainerMenu {
    private final Player player;
    private final Level level;
    private final BlockPos claimPos;

    // Constructor for opening from command or item
    public ClaimMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, BlockPos.ZERO);
    }

    // Constructor with specific claim position
    public ClaimMenu(int id, Inventory playerInventory, BlockPos claimPos) {
        super(ClaimModMenuTypes.CLAIM_MENU.get(), id);
        this.player = playerInventory.player;
        this.level = player.level();
        this.claimPos = claimPos;

        // Add player inventory slots (standard 9x4 layout)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Add hotbar slots
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return level;
    }

    public BlockPos getClaimPos() {
        return claimPos;
    }

    // Get current claim count for display
    public int getPlayerClaimCount() {
        return (int) player.getData(ClaimModVariables.PLAYER_VARIABLES).playerClaimCount;
    }

    // Get max claim count from game rules
    public int getMaxClaimCount() {
        if (level.isClientSide) {
            return 5; // Default fallback for client
        }
        return level.getServer().getGameRules().getInt(com.mysparkle1991.getoffmylawn.init.ClaimModGameRules.MAX_CLAIM_COUNT);
    }
}
