package io.github.jack9761.MicroComputers.block.entity;

import io.github.jack9761.MicroComputers.ModMenu;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MicroComputerMenu extends AbstractContainerMenu {

    private final Container container;
    private final ContainerData data;

    public MicroComputerMenu(int containerID, Inventory playerInventory) {
        this(containerID, playerInventory, new SimpleContainer(2), new SimpleContainerData(1));
    }

    // Server Side Constructor
    public MicroComputerMenu(int containerID, Inventory playerInventory, Container container, ContainerData containerData) {
        super(ModMenu.MICROCOMPUTER_SCREEN_MENU.get(), containerID);
        checkContainerSize(container, 2);
        checkContainerDataCount(containerData, 1);

        this.container = container;
        this.data = containerData;

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

        // Own slots
        this.addSlot(new Slot(container, 0, 62, 35));
        this.addSlot(new Slot(container, 1, 98, 35));

        // Data slots
        this.addDataSlots(containerData);
    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}
