package io.github.jack9761.MicroComputers.client.gui.pageElements;

import io.github.jack9761.MicroComputers.client.gui.InstructionManualScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class PlainTextElement implements IPageElement {
    private Component text;
    public PlainTextElement(String text) { this.text = Component.literal(text); }
    public PlainTextElement(Component text) { this.text = text; }
    public Component getText() { return this.text; }
    public void setText(Component updatedText){this.text = updatedText;}
    public void setText(String updatedText){this.text = Component.literal(updatedText);}
    @Override public void init(InstructionManualScreen screen) {}
    @Override public int getWidth() { return Minecraft.getInstance().font.width(this.text); }
    @Override public int getHeight() { return Minecraft.getInstance().font.lineHeight; }
    @Override public void render(GuiGraphics guiGraphics, Font font, int x, int y, int mouseX, int mouseY) {
        guiGraphics.drawString(font, this.text, x, y, 0xFF000000,false);
    }
}