package io.github.jack9761.MicroComputers.client.gui.buttons;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ImageButton extends Button {

    private ResourceLocation texture;
    private int uOffset;
    public Boolean disabled = false;
    public ImageButton(int x, int y, int uOffset, ResourceLocation texture, OnPress onPress) {
        super(x, y, 20, 20, net.minecraft.network.chat.Component.empty(), onPress, Button.DEFAULT_NARRATION);
        this.texture = texture;
        this.uOffset = uOffset;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if(!this.isActive()){
            guiGraphics.blit(this.texture,getX(),getY(),20,20,this.uOffset,40,20,20,60,60);
        }
        else if(this.isHoveredOrFocused()){
            guiGraphics.blit(this.texture,getX(),getY(),20,20,this.uOffset,20,20,20,60,60);
        }
        else{
            guiGraphics.blit(this.texture,getX(),getY(),20,20,this.uOffset,0,20,20,60,60);
        }
    }
}
