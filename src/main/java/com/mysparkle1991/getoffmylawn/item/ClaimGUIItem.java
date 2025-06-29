// Fix 1: ClaimGUIItem.java - Use correct InteractionResultHolder
package com.mysparkle1991.getoffmylawn.item;

import com.mysparkle1991.getoffmylawn.gui.ClaimMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ClaimGUIItem extends Item {

    public ClaimGUIItem() {
        super(new Item.Properties()
                .stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BlockPos playerPos = player.blockPosition();
            serverPlayer.openMenu(new ClaimMenuProvider(playerPos));
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    // Simple menu provider implementation
    private static class ClaimMenuProvider implements MenuProvider {
        private final BlockPos pos;

        public ClaimMenuProvider(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public Component getDisplayName() {
            return Component.literal("Claim Management");
        }

        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
            return new ClaimMenu(id, inventory, pos);
        }
    }
}