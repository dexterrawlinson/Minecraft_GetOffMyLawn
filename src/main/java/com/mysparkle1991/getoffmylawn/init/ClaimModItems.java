package com.mysparkle1991.getoffmylawn.init;

import com.mysparkle1991.getoffmylawn.GetOffMyLawn;
import com.mysparkle1991.getoffmylawn.item.ClaimGUIItem;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ClaimModItems {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(GetOffMyLawn.MODID);

    public static final DeferredItem<Item> BEDROCK_CLAIM = block(ClaimModBlocks.BEDROCK_CLAIM);
    public static final DeferredItem<Item> CLAIM_GUI = REGISTRY.register("claim_gui", ClaimGUIItem::new);

    private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
        return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }
}