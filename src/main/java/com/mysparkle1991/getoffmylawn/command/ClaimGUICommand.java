package com.mysparkle1991.getoffmylawn.command;

import com.mysparkle1991.getoffmylawn.init.ClaimModItems;

import net.minecraft.commands.Commands;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber
public class ClaimGUICommand {

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("claimgui")
                        .executes(arguments -> {
                            Level world = arguments.getSource().getUnsidedLevel();
                            Entity entity = arguments.getSource().getEntity();

                            if (entity == null && world instanceof ServerLevel) {
                                entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                            }

                            if (entity instanceof Player player) {
                                // Give the player the GUI item
                                ItemStack guiItem = new ItemStack(ClaimModItems.CLAIM_GUI.get());
                                if (!player.getInventory().add(guiItem)) {
                                    player.drop(guiItem, false);
                                }

                                if (!player.level().isClientSide()) {
                                    player.displayClientMessage(Component.literal("§aReceived Claim GUI item! Right-click to open."), false);
                                }
                            }

                            return 0;
                        })
        );
    }
}