package io.github.jack9761.MicroComputers;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.jack9761.MicroComputers.block.entity.MicroComputerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

public class ModMenu {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(MicroComputers.MOD_ID, Registries.MENU);
    public static RegistrySupplier<MenuType<MicroComputerMenu>> MICROCOMPUTER_SCREEN_MENU = MENUS.register("microcomputer_menu", ()-> new MenuType<>(MicroComputerMenu::new, FeatureFlagSet.of()));
}
