package io.github.jack9761.MicroComputers.forge.client;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import io.github.jack9761.MicroComputers.MicroComputers;
import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.RandomSource;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.client.model.geometry.IGeometryLoader;

import java.util.*;
import java.util.function.Function;

public class MicroComputerModelLoader implements IGeometryLoader<MicroComputerModelLoader.Geometry> {

    public static final ResourceLocation STATIC_MODEL_LOCATION = new ResourceLocation(MicroComputers.MOD_ID, "models/block/microcomputer");

    public void onResourceManagerReload(ResourceManager ResourceManager) {}
    @Override
    public Geometry read(JsonObject modelContents, JsonDeserializationContext deserializationContext) {
        return new Geometry();
    }

    public static class Geometry implements IUnbakedGeometry<Geometry> {
        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
            EnumMap<MicroComputerBlockEntity.MicroComputerTextures, TextureAtlasSprite> texture_map_TextureAtlasSprite = new EnumMap<>(MicroComputerBlockEntity.MicroComputerTextures.class);
            List<BakedQuad> staticQuads = new ArrayList<>();
            EnumMap<Direction, BakedQuad> dynamicQuads = new EnumMap<>(Direction.class);
            BakedModel staticModelBaked = bakery.bake(STATIC_MODEL_LOCATION, modelState);
            for (BakedQuad bakedQuad :staticModelBaked.getQuads(null, null, RandomSource.create(), ModelData.EMPTY, null)) {
                switch (bakedQuad.getSprite().contents().name().getPath()) {
                    case "block/microcomputer/placeholder_down":
                        dynamicQuads.put(Direction.DOWN, bakedQuad);
                        break;
                    case "block/microcomputer/placeholder_up":
                        dynamicQuads.put(Direction.UP, bakedQuad);
                        break;
                    case "block/microcomputer/placeholder_east":
                        dynamicQuads.put(Direction.EAST, bakedQuad);
                        break;
                    case "block/microcomputer/placeholder_west":
                        dynamicQuads.put(Direction.WEST, bakedQuad);
                        break;
                    case "block/microcomputer/placeholder_north":
                        dynamicQuads.put(Direction.NORTH, bakedQuad);
                        break;
                    case "block/microcomputer/placeholder_south":
                        dynamicQuads.put(Direction.SOUTH, bakedQuad);
                        break;
                    default:
                        staticQuads.add(bakedQuad);
                        break;
                }
            }
            return new MicroComputerBakedModelForge(texture_map_TextureAtlasSprite,staticQuads,dynamicQuads);
        }

        public Collection<Material> getMaterials(IGeometryBakingContext context, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
            return List.of(
                context.getMaterial("1"),
                context.getMaterial("8"),
                context.getMaterial("particle"),
                context.getMaterial("down"),
                context.getMaterial("up"),
                context.getMaterial("west"),
                context.getMaterial("east"),
                context.getMaterial("north"),
                context.getMaterial("south")
            );
        }
    }
}