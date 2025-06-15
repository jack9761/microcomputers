package io.github.jack9761.MicroComputers.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.jack9761.MicroComputers.MicroComputers;
import io.github.jack9761.MicroComputers.ModCreativeTab;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;


public class ModItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MicroComputers.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> SILICON =  ITEMS.register("silicon",() -> new Item(new Item.Properties().arch$tab(ModCreativeTab.MICROCOMPUTERS_CREATIVE_TAB)));
}
