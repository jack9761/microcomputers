package io.github.jack9761.MicroComputers.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import io.github.jack9761.MicroComputers.block.ModBlock;
import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;
import io.github.jack9761.MicroComputers.block.entity.ModBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RedStoneWireBlock;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static io.github.jack9761.MicroComputers.MicroComputers.MOD_ID;

@Environment(EnvType.CLIENT)
public class MicroComputersClient {
    public static final ResourceLocation LOADER_ID = new ResourceLocation(MOD_ID, "microcomputer_loader");

    @Contract("_, _ -> new")
    public static @NotNull BakedQuad copyBakedQuadwithNewSprite(BakedQuad original, TextureAtlasSprite newSprite){
        int[] vertexData = original.getVertices().clone();
        TextureAtlasSprite oldSprite = original.getSprite();

        int vertexSize = vertexData.length / 4;

        for (int i = 0; i < 4; i++) {
            int offset = i * vertexSize;

            // UV coordinates are at offset 4 and 5 in default format
            float u = Float.intBitsToFloat(vertexData[offset + 4]);
            float v = Float.intBitsToFloat(vertexData[offset + 5]);

            // Remap from old sprite to new sprite
            float normalizedU = (u - oldSprite.getU0()) / (oldSprite.getU1() - oldSprite.getU0());
            float normalizedV = (v - oldSprite.getV0()) / (oldSprite.getV1() - oldSprite.getV0());

            float newU = newSprite.getU0() + normalizedU * (newSprite.getU1() - newSprite.getU0());
            float newV = newSprite.getV0() + normalizedV * (newSprite.getV1() - newSprite.getV0());

            vertexData[offset + 4] = Float.floatToRawIntBits(newU);
            vertexData[offset + 5] = Float.floatToRawIntBits(newV);
        }

        return new BakedQuad(
                vertexData,
                original.getTintIndex(),
                original.getDirection(),
                newSprite,
                original.isShade()
        );
    }

    public static void init() {
        RenderTypeRegistry.register(RenderType.cutout(), ModBlock.MICROCOMPUTER_BLOCK.get());
        ColorHandlerRegistry.registerBlockColors(((blockState, blockAndTintGetter, blockPos, i) -> {
            Optional<MicroComputerBlockEntity> BlockEntityOptional = blockAndTintGetter.getBlockEntity(blockPos, ModBlockEntity.MICROCOMPUTER_BLOCK_ENTITY.get());
            if(BlockEntityOptional.isPresent()){
                MicroComputerBlockEntity.MicroComputerTextures texture = BlockEntityOptional.get().clientTextureMap.get(commonMicroComputerBakedModel.TintIndexDirectionMap.get(i));
                                if (texture == null || texture == MicroComputerBlockEntity.MicroComputerTextures.BLANK || texture == MicroComputerBlockEntity.MicroComputerTextures.CABLE) {
                    //Nothing should happen
                    return -1;
                }
                else{
                    return RedStoneWireBlock.getColorForPower(Integer.parseInt(texture.toString().substring(9)));
                }
            }
            return -1;
        }),ModBlock.MICROCOMPUTER_BLOCK.get());
    }
}