package io.github.jack9761.MicroComputers.block.entity;

import io.github.jack9761.MicroComputers.inventory.IItemHandler;
import io.github.jack9761.MicroComputers.inventory.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;


public class MicroComputerBlockEntity extends BlockEntity implements MenuProvider {

    private final IItemHandler addonSlots;

    public MicroComputerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.MICROCOMPUTER_BLOCK_ENTITY.get(), pos, blockState);
        this.addonSlots = InventoryUtils.createHandler(2);
        int i = 1;
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }
}
