package io.github.jack9761.MicroComputers;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.jack9761.MicroComputers.item.ModItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab {
//    public static final CreativeModeTab CREATIVE_MODE_TAB = new CreativeModeTab.Builder()
//            /* Change to cable eventually */
//            .icon(() -> new ItemStack(ModItem.SILICON.get()))
//            .title(Component.translatable("creativetab.microcomputers"))
//            .displayItems((itemDisplayParameters, output) -> {
//                output.accept(ModItem.SILICON.get());
//            })
//            .build();
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(MicroComputers.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> MICROCOMPUTERS_CREATIVE_TAB = TABS.register(
            "microcomputer_creative_tab",
            () -> CreativeTabRegistry.create(
                    Component.translatable("creativetab.microcomputer"),
                    () -> new ItemStack(ModItem.SILICON.get())
            )
    );
}
