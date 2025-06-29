package com.mysparkle1991.getoffmylawn.init;

import net.minecraft.world.level.GameRules;

public class ClaimModGameRules {
    public static GameRules.Key<GameRules.IntegerValue> MAX_CLAIM_COUNT;
    public static GameRules.Key<GameRules.BooleanValue> CLAIM_PERMISSION;

    public static void register() {
        MAX_CLAIM_COUNT = GameRules.register("maxClaimCount", GameRules.Category.MISC, GameRules.IntegerValue.create(5));
        CLAIM_PERMISSION = GameRules.register("claimPermission", GameRules.Category.MISC, GameRules.BooleanValue.create(true));
    }
}