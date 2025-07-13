package io.github.jack9761.MicroComputers.client.gui.pageElements;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class LiteralTextElement extends PlainTextElement{

    public LiteralTextElement(String text) {
        super(text);
    }

    public LiteralTextElement(Component text) {
        super(text);
    }
}
