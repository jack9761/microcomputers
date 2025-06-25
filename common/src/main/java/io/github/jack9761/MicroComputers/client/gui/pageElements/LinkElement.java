package io.github.jack9761.MicroComputers.client.gui.pageElements;

import dev.architectury.networking.NetworkManager;
import io.github.jack9761.MicroComputers.client.gui.InstructionManualScreen;
import io.github.jack9761.MicroComputers.client.gui.buttons.InvisibleButton;
import io.github.jack9761.MicroComputers.networking.SetPagePacket;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class LinkElement implements IPageElement {

    private final Component text;
    private InvisibleButton invisibleButton;
    private final int targetPage;

    public LinkElement(String text, int targetPage) {
        this.text = Component.literal(text);
        this.targetPage = targetPage;
    }
    public LinkElement(Component text, int targetPage) {
        this.text = text;
        this.targetPage = targetPage;
    }

    @Override
    public void init(InstructionManualScreen screen) {
        this.invisibleButton = new InvisibleButton(0,0,getWidth(),getHeight(),this.text,(press)-> {screen.openPage(targetPage);});
        screen.AppendWidgetToQueue(this.invisibleButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, Font font, int x, int y, int mouseX, int mouseY) {
        invisibleButton.setX(x);
        invisibleButton.setY(y);
        if(invisibleButton.isHoveredOrFocused()) {
            guiGraphics.drawString(font, this.text, x, y, 0x00CC00, false);
        }
        else{
            guiGraphics.drawString(font, this.text, x, y, 0x000000, false);
        }
    }

    @Override
    public int getWidth() {
        return Minecraft.getInstance().font.width(this.text);
    }

    @Override
    public int getHeight() {
        return Minecraft.getInstance().font.lineHeight;
    }
}
