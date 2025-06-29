// Fix 3: ClaimModBlockEntities.java - Fix BlockEntityType creation
package com.mysparkle1991.getoffmylawn.init;

import com.mysparkle1991.getoffmylawn.GetOffMyLawn;
import com.mysparkle1991.getoffmylawn.block.entity.BedrockClaimBlockEntity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = GetOffMyLawn.MODID)
public class ClaimModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, GetOffMyLawn.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BedrockClaimBlockEntity>> BEDROCK_CLAIM = register("bedrock_claim",
            ClaimModBlocks.BEDROCK_CLAIM, BedrockClaimBlockEntity::new);

    private static DeferredHolder<BlockEntityType<?>, BlockEntityType<BedrockClaimBlockEntity>> register(String registryname,
                                                                                                         DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<BedrockClaimBlockEntity> supplier) {
        return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BEDROCK_CLAIM.get(),
                (blockEntity, side) -> blockEntity.getItemHandler());
    }
}