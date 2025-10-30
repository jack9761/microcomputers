package io.github.jack9761.MicroComputers.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.jack9761.MicroComputers.ModCreativeTab;
import io.github.jack9761.MicroComputers.ModMenu;
import io.github.jack9761.MicroComputers.block.ModBlock;
import io.github.jack9761.MicroComputers.block.entity.ModBlockEntity;
import io.github.jack9761.MicroComputers.client.MicroComputersClient;
import io.github.jack9761.MicroComputers.item.ModItem;
import io.github.jack9761.MicroComputers.forge.client.MicroComputerModelLoader;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import io.github.jack9761.MicroComputers.MicroComputers;

@Mod(MicroComputers.MOD_ID)
public final class MicroComputersForge {
    //Items
    //Creative Tab
    public MicroComputersForge() {
        EventBuses.registerModEventBus(MicroComputers.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        IEventBus ModEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlockEntity.MICROCOMPUTER_BLOCK_ENTITY_SUPPLIER = MicroComputerBlockEntityForge::new;
        ModItem.ITEMS.register();
        ModBlock.BLOCKS.register();
        ModCreativeTab.TABS.register();
        ModBlockEntity.BLOCK_ENTITIES.register();
        ModMenu.MENUS.register();
        ModEventBus.addListener(this::onClientSetup);
        ModEventBus.addListener(this::onRegisterGeometryLoaders);
        MicroComputers.init();
    }

    private void onRegisterGeometryLoaders(final ModelEvent.RegisterGeometryLoaders event) {
        event.register(MicroComputersClient.LOADER_ID.getPath(), new MicroComputerModelLoader());
    }

    private void onClientSetup(final FMLClientSetupEvent event){
        MicroComputersClient.init();
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event){

    }
}
