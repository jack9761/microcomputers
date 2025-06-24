package io.github.jack9761.MicroComputers.client.gui.pageElements;

import io.github.jack9761.MicroComputers.client.gui.InstructionManualScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

@Environment(EnvType.CLIENT)
public interface IPageElement {
    void init(InstructionManualScreen screen);
    void render(GuiGraphics guiGraphics, Font font, int x, int y, int mouseX, int mouseY);
    int getWidth();
    int getHeight();
}