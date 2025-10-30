package io.github.jack9761.MicroComputers.forge.client;

import io.github.jack9761.MicroComputers.block.MicroComputerBlock;
import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;
import io.github.jack9761.MicroComputers.client.commonMicroComputerBakedModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.List;

import static io.github.jack9761.MicroComputers.forge.MicroComputerBlockEntityForge.sideStates;
@OnlyIn(Dist.CLIENT)
public class MicroComputerBakedModelForge extends commonMicroComputerBakedModel implements IDynamicBakedModel {

    public MicroComputerBakedModelForge(List<BakedQuad> staticQuads, EnumMap<Direction, EnumMap<MicroComputerBlock.SideTexture, BakedQuad>> dynamicQuads, EnumMap<Direction, BakedQuad> overlayQuads, BakedModel staticModel) {
        super(staticQuads, dynamicQuads, overlayQuads, staticModel);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, @Nonnull RandomSource rand, @Nonnull ModelData extraData, RenderType renderType) {
        if (extraData.has(sideStates)) {
            EnumMap<Direction, MicroComputerBlockEntity.MicroComputerTextures> textureMap = extraData.get(sideStates);
            if (textureMap != null) {
                return this.getAgnosticQuads(textureMap, side, rand);
            }
        }
        return staticModelBaked.getQuads(state, side, rand, extraData, renderType);
    }
}