package io.github.jack9761.MicroComputers.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class IndentElement implements IPageElement{
    int indentWidth;
    public IndentElement(int indentWidth) {
        this.indentWidth = indentWidth;
    }
    @Override public void init(InstructionManualScreen screen) {}
    @Override public void render(GuiGraphics guiGraphics, Font font, int x, int y, int mouseX, int mouseY) {}
    @Override
    public int getWidth() {
        return indentWidth;
    }
    @Override
    public int getHeight() {
        return Minecraft.getInstance().font.lineHeight;
    }
}
