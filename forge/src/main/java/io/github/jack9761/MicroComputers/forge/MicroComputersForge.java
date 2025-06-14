package io.github.jack9761.MicroComputers.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.jack9761.MicroComputers.item.ModCreativeMode;
import io.github.jack9761.MicroComputers.item.ModItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import io.github.jack9761.MicroComputers.MicroComputers;

@Mod(MicroComputers.MOD_ID)
public final class MicroComputersForge {
    //Items
    //Creative Tab
    public MicroComputersForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(MicroComputers.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        IEventBus ModEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItem.ITEMS.register();
        ModCreativeMode.TABS.register();
        // Run our common setup.
        MicroComputers.init();
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event){

    }
}
