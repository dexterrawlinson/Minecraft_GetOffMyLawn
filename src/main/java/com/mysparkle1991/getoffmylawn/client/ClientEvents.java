package com.mysparkle1991.getoffmylawn.client;

import com.mysparkle1991.getoffmylawn.GetOffMyLawn;
import com.mysparkle1991.getoffmylawn.gui.ClaimScreen;
import com.mysparkle1991.getoffmylawn.init.ClaimModMenuTypes;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = GetOffMyLawn.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ClaimModMenuTypes.CLAIM_MENU.get(), ClaimScreen::new);
    }
}