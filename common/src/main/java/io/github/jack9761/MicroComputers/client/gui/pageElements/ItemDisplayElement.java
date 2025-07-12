package io.github.jack9761.MicroComputers.client.gui.pageElements;

import io.github.jack9761.MicroComputers.MicroComputers;
import io.github.jack9761.MicroComputers.client.gui.InstructionManualScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class ItemDisplayElement implements IPageElement{

    private static ResourceLocation INVENTORY_SLOT_TEXTURE = new ResourceLocation(MicroComputers.MOD_ID, "textures/gui/inventory_slots.png");

    private ItemStack displayItemStack;

    public ItemDisplayElement(Item item) {
        displayItemStack = new ItemStack(item);
    }
    public ItemDisplayElement(String itemTag){
        displayItemStack = new ItemStack(BuiltInRegistries.ITEM.get(new ResourceLocation(itemTag)));
    }

    @Override
    public void init(InstructionManualScreen screen) {}

    @Override
    public void render(GuiGraphics guiGraphics, Font font, int x, int y, int mouseX, int mouseY) {
        Boolean isHovered = mouseX >= x && mouseX < x + getWidth() && mouseY >= y && mouseY < y + getHeight();
        if(isHovered){
            guiGraphics.blit(INVENTORY_SLOT_TEXTURE,x,y,18,18,0,19,18,18,18,38);
            guiGraphics.renderTooltip(Minecraft.getInstance().font, displayItemStack,mouseX,mouseY);
        }
        else{
            guiGraphics.blit(INVENTORY_SLOT_TEXTURE,x,y,18,18,0,0,18,18,18,38);
        }
        guiGraphics.renderFakeItem(displayItemStack,x+1,y+1);
    }

    @Override
    public int getWidth() {
        return 18;
    }

    @Override
    public int getHeight() {
        return 20;
    }
}
