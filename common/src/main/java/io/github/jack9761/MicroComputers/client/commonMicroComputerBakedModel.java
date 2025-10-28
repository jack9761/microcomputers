package io.github.jack9761.MicroComputers.client;

import io.github.jack9761.MicroComputers.MicroComputers;
import io.github.jack9761.MicroComputers.block.MicroComputerBlock;
import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public abstract class commonMicroComputerBakedModel implements BakedModel {
    public static final Map<Integer, Direction> TintIndexDirectionMap = Map.of(
            0, Direction.DOWN,
            1, Direction.UP,
            2, Direction.NORTH,
            3, Direction.SOUTH,
            4, Direction.WEST,
            5, Direction.EAST
    );
    private final List<BakedQuad> staticQuads;
    private final EnumMap<Direction, EnumMap<MicroComputerBlock.SideTexture, BakedQuad>> dynamicQuads;
    private final EnumMap<Direction, BakedQuad> overlayQuads;
    protected final BakedModel staticModelBaked;

    public commonMicroComputerBakedModel(List<BakedQuad> staticQuads, EnumMap<Direction, EnumMap<MicroComputerBlock.SideTexture, BakedQuad>> dynamicQuads, EnumMap<Direction, BakedQuad> overlayQuads, BakedModel staticModel) {
        this.staticQuads = staticQuads;
        this.dynamicQuads = dynamicQuads;
        this.overlayQuads = overlayQuads;
        this.staticModelBaked = staticModel;

    }

    public List<BakedQuad> getAgnosticQuads(EnumMap<Direction, MicroComputerBlockEntity.MicroComputerTextures> sideStates, Direction side, RandomSource rand) {
        if(side!=null){
            MicroComputers.LOGGER.info("getAgnosticQuads called for side: {}", side == null ? "null" : side.getName());
        }
        List<BakedQuad> quads = new ArrayList<>(staticQuads);
        for(Direction direction :sideStates.keySet()){
                        if (sideStates.get(direction) == MicroComputerBlockEntity.MicroComputerTextures.BLANK) {
                quads.add(dynamicQuads.get(direction).get(MicroComputerBlock.SideTexture.BLANK));
            }
                        else if (sideStates.get(direction) == MicroComputerBlockEntity.MicroComputerTextures.CABLE) {
                quads.add(dynamicQuads.get(direction).get(MicroComputerBlock.SideTexture.CABLE));
            }
            else{
                quads.add(dynamicQuads.get(direction).get(MicroComputerBlock.SideTexture.REDSTONE));
                quads.add(overlayQuads.get(direction));
            }
        }
        return quads;
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
