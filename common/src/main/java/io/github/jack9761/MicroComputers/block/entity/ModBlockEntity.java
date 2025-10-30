package io.github.jack9761.MicroComputers.block.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.jack9761.MicroComputers.MicroComputers;
import io.github.jack9761.MicroComputers.block.ModBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntity {
    public static BlockEntityType.BlockEntitySupplier<? extends MicroComputerBlockEntity> MICROCOMPUTER_BLOCK_ENTITY_SUPPLIER;
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(MicroComputers.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public static final RegistrySupplier<BlockEntityType<? extends MicroComputerBlockEntity>> MICROCOMPUTER_BLOCK_ENTITY = BLOCK_ENTITIES.register("microcomputer_block_entity",
            () -> BlockEntityType.Builder.of(MICROCOMPUTER_BLOCK_ENTITY_SUPPLIER, ModBlock.MICROCOMPUTER_BLOCK.get()).build(null));
}
