package io.github.jack9761.MicroComputers.inventory.forge;

import io.github.jack9761.MicroComputers.forge.inventory.ForgeItemHandler;
import io.github.jack9761.MicroComputers.inventory.IItemHandler;

@SuppressWarnings("unused")
public class InventoryUtilsImpl {
    public static IItemHandler createHandler(int size){
        return new ForgeItemHandler(size);
    }
}
