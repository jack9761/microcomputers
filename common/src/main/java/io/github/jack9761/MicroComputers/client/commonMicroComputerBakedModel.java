package io.github.jack9761.MicroComputers.client;

import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
public abstract class commonMicroComputerBakedModel implements BakedModel {
    private final EnumMap<MicroComputerBlockEntity.MicroComputerTextures, TextureAtlasSprite> TEXTURE_MAP;
    private final List<BakedQuad> staticQuads;
    private final EnumMap<Direction, BakedQuad> dynamicQuads;

    public commonMicroComputerBakedModel(EnumMap<MicroComputerBlockEntity.MicroComputerTextures, TextureAtlasSprite> TEXTURE_MAP, List<BakedQuad> staticQuads, EnumMap<Direction, BakedQuad> dynamicQuads) {
        this.TEXTURE_MAP = TEXTURE_MAP;
        this.staticQuads = staticQuads;
        this.dynamicQuads = dynamicQuads;
    }

    public List<BakedQuad> getAgnosticQuads(EnumMap<Direction, MicroComputerBlockEntity.MicroComputerTextures> sideStates, Direction side, RandomSource rand) {

        return Collections.emptyList();
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction direction, RandomSource random) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return null;
    }

    @Override
    public ItemTransforms getTransforms() {
        return null;
    }

    @Override
    public ItemOverrides getOverrides() {
        return null;
    }
}
