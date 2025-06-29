package com.mysparkle1991.getoffmylawn;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.mysparkle1991.getoffmylawn.init.ClaimModBlocks;
import com.mysparkle1991.getoffmylawn.init.ClaimModBlockEntities;
import com.mysparkle1991.getoffmylawn.init.ClaimModItems;
import com.mysparkle1991.getoffmylawn.init.ClaimModMenuTypes;
import com.mysparkle1991.getoffmylawn.init.ClaimModGameRules;
import com.mysparkle1991.getoffmylawn.network.ClaimModVariables;
import com.mysparkle1991.getoffmylawn.command.ClaimCommand;

@Mod(GetOffMyLawn.MODID)
public class GetOffMyLawn {
    public static final String MODID = "getoffmylawn";
    private static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold CreativeModeTabs
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a creative tab for the claim items
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CLAIM_TAB = CREATIVE_MODE_TABS.register("claim_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.getoffmylawn.claim"))
            .withTabsBefore(CreativeModeTabs.FUNCTIONAL_BLOCKS)
            .icon(() -> ClaimModItems.CLAIM_GUI.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ClaimModItems.BEDROCK_CLAIM.get());
                output.accept(ClaimModItems.CLAIM_GUI.get());
            }).build());

    public GetOffMyLawn(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register all the deferred registers
        ClaimModBlocks.REGISTRY.register(modEventBus);
        ClaimModBlockEntities.REGISTRY.register(modEventBus);
        ClaimModItems.REGISTRY.register(modEventBus);
        ClaimModMenuTypes.REGISTRY.register(modEventBus);
        ClaimModVariables.ATTACHMENT_TYPES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(ClaimModVariables.EventBusVariableHandlers.class);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        LOGGER.info("GetOffMyLawn mod initialized");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ClaimModGameRules.register();
        });

        LOGGER.info("GetOffMyLawn common setup complete");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ClaimModItems.BEDROCK_CLAIM);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ClaimModItems.CLAIM_GUI);
        }
    }
}