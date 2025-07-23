package io.github.jack9761.MicroComputers.inventory;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class InventoryUtils{
    @ExpectPlatform
    public static IItemHandler createHandler(int size){
        throw new AssertionError();
    }

}
