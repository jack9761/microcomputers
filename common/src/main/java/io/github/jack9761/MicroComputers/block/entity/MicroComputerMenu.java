package io.github.jack9761.MicroComputers.block.entity;

import io.github.jack9761.MicroComputers.ModMenu;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MicroComputerMenu extends AbstractContainerMenu {

    private final Container container;

    //Synced by Packets
    String[] microComputerCode;
    Direction[] baseInternalCableConnections;
    MicroComputerBlockEntity.MicroComputerState microComputerState;
    byte microcomputerCursor;
    byte[] microcomputerBreakpoints;
    int baseAccumulators;

    public MicroComputerMenu(int containerID, Inventory playerInventory) {
        this(containerID, playerInventory, new SimpleContainer(2));
    }

    // Server Side Constructor
    public MicroComputerMenu(int containerID, Inventory playerInventory, Container container) {
        super(ModMenu.MICROCOMPUTER_SCREEN_MENU.get(), containerID);
        checkContainerSize(container, 2);

        this.container = container;

        // Player inventory
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        // Player Hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        // Addon Slots
        this.addSlot(new Slot(container, 0, 62, 35));
        this.addSlot(new Slot(container, 1, 98, 35));
    }

    //Server Side
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if(slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (index < 36) {
                if (!moveItemStackTo(originalStack, 36, 36 + 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 36 + 2) {
                if (!moveItemStackTo(originalStack, 0, 36, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            slot.onTake(player, originalStack);
        }
        return newStack;
    }

    //Server Side
    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    //
    @Override
    public void removed(Player player) {
        super.removed(player);
    }
}
