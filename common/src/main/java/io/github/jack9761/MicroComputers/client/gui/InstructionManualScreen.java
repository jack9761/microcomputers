package io.github.jack9761.MicroComputers.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
@Environment(EnvType.CLIENT)
public class InstructionManualScreen extends Screen {
    public InstructionManualScreen() {
        super(Component.translatable("screen.microcomputers.instruction_manual.title"));
    }

    @Override
    protected void init() {
        super.init();
        //Back to Start
        this.addRenderableWidget(Button.builder(Component.literal("Back to Start"), (button) -> {
            System.out.println("Back To Start");
        }).bounds(this.width / 2 - 102, this.height / 4 + 24, 100, 20).build());

        // Button 2: Next Page
        this.addRenderableWidget(Button.builder(Component.literal("Next ->"), (button) -> {
            System.out.println("Next Page button clicked!");
            // Here you would put your logic to send a packet to the server
        }).bounds(this.width / 2 + 2, this.height / 4 + 24, 100, 20).build());

        // Button 3: Previous Page
        this.addRenderableWidget(Button.builder(Component.literal("<- Prev"), (button) -> {
            System.out.println("Previous Page button clicked!");
        }).bounds(this.width / 2 - 102, this.height / 4 + 48, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        // Draw some text
        guiGraphics.drawCenteredString(this.font, "My Awesome Book", this.width / 2, 20, 0xFFFFFF); // White text
        guiGraphics.drawString(this.font, "This is the content of the page.", 30, 50, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen(){
        return false;
    }
}
