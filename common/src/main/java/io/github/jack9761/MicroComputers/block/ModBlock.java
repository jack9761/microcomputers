package io.github.jack9761.MicroComputers.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.jack9761.MicroComputers.MicroComputers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlock {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(MicroComputers.MOD_ID, Registries.BLOCK);
    public static RegistrySupplier<Block> MICROCOMPUTER_BLOCK = BLOCKS.register("microcomputer",() -> new MicrocomputerBlock(BlockBehaviour.Properties.of().strength(1.5f)));
}
