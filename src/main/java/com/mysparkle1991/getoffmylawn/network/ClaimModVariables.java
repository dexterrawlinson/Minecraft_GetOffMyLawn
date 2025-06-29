// Fix 4: ClaimModVariables.java - Simplified attachment system without serializer
package com.mysparkle1991.getoffmylawn.network;

import com.mysparkle1991.getoffmylawn.GetOffMyLawn;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ClaimModVariables {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, GetOffMyLawn.MODID);

    public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables",
            () -> AttachmentType.builder(PlayerVariables::new).build());

    public static class PlayerVariables {
        public double playerClaimCount = 0.0;
        public String lastClaimName = "";
        public double lastX = 0.0;
        public double lastZ = 0.0;

        public PlayerVariables() {}

        public void syncPlayerVariables(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer) {
                // Sync to client if needed
            }
        }
    }

    @EventBusSubscriber
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer) {
                player.getData(PLAYER_VARIABLES).syncPlayerVariables(player);
            }
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer) {
                player.getData(PLAYER_VARIABLES).syncPlayerVariables(player);
            }
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
            Player player = event.getEntity();
            if (player instanceof ServerPlayer) {
                player.getData(PLAYER_VARIABLES).syncPlayerVariables(player);
            }
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
            PlayerVariables clone = new PlayerVariables();
            clone.playerClaimCount = original.playerClaimCount;
            clone.lastClaimName = original.lastClaimName;
            clone.lastX = original.lastX;
            clone.lastZ = original.lastZ;

            if (!event.isWasDeath()) {
                // Keep data on respawn
            }

            event.getEntity().setData(PLAYER_VARIABLES, clone);
        }
    }
}
