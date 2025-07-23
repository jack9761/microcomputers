package io.github.jack9761.MicroComputers.forge.inventory;

import io.github.jack9761.MicroComputers.inventory.IItemHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ForgeItemHandler implements IItemHandler {
    private final ItemStackHandler handler;

    public ForgeItemHandler(int size) {
        handler = new ItemStackHandler(size);
    }

    @Override
    public int getSlots() {
        return handler.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return handler.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return handler.insertItem(slot,stack,simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return handler.extractItem(slot,amount,simulate);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        handler.setStackInSlot(slot,stack);
    }
}
