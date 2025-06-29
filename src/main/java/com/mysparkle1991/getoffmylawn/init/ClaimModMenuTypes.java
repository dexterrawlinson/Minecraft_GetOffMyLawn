package com.mysparkle1991.getoffmylawn.init;

import com.mysparkle1991.getoffmylawn.GetOffMyLawn;
import com.mysparkle1991.getoffmylawn.gui.ClaimMenu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ClaimModMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.MENU, GetOffMyLawn.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ClaimMenu>> CLAIM_MENU = REGISTRY.register("claim_menu",
            () -> IMenuTypeExtension.create((id, inventory, data) -> {
                return new ClaimMenu(id, inventory, data.readBlockPos());
            }));
}