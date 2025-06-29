package com.mysparkle1991.getoffmylawn.init;

import com.mysparkle1991.getoffmylawn.GetOffMyLawn;
import com.mysparkle1991.getoffmylawn.block.BedrockClaimBlock;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ClaimModBlocks {
    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(GetOffMyLawn.MODID);

    public static final DeferredBlock<Block> BEDROCK_CLAIM = REGISTRY.register("bedrock_claim", BedrockClaimBlock::new);
}