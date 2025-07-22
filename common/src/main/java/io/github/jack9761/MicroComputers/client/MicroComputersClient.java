package io.github.jack9761.MicroComputers.client;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import io.github.jack9761.MicroComputers.block.ModBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class MicroComputersClient {
    public static void init() {
        RenderTypeRegistry.register(RenderType.cutout(), ModBlock.MICROCOMPUTER_BLOCK.get());
    }
}