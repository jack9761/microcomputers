package io.github.jack9761.MicroComputers.forge.client;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import io.github.jack9761.MicroComputers.MicroComputers;
import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;
import io.github.jack9761.MicroComputers.client.MicroComputersClient;
import io.github.jack9761.MicroComputers.client.commonMicroComputerBakedModel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.client.model.geometry.IGeometryLoader;

import java.util.*;
import java.util.function.Function;

public class MicroComputerModelLoader implements IGeometryLoader<MicroComputerModelLoader.Geometry> {

    public static final ResourceLocation STATIC_MODEL_LOCATION = new ResourceLocation(MicroComputers.MOD_ID, "models/block/microcomputer");
    public static final ResourceLocation OVERLAY_MODEL_LOCATION = new ResourceLocation(MicroComputers.MOD_ID, "models/block/microcomputer_overlays");

    public void onResourceManagerReload(ResourceManager ResourceManager) {}
    @Override
    public Geometry read(JsonObject modelContents, JsonDeserializationContext deserializationContext) {
        return new Geometry();
    }

    public static class Geometry implements IUnbakedGeometry<Geometry> {
        private static final Material redstone_side = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(MicroComputers.MOD_ID, "block/microcomputer/redstone_0_side"));
        private static final Material wire_side = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(MicroComputers.MOD_ID, "block/microcomputer/wire_side"));
        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
            //EnumMap<MicroComputerBlockEntity.MicroComputerTextures, TextureAtlasSprite> texture_map_TextureAtlasSprite = new EnumMap<>(MicroComputerBlockEntity.MicroComputerTextures.class);
            List<BakedQuad> staticQuads = new ArrayList<>();
            EnumMap<Direction, EnumMap<MicroComputerBlockEntity.MicroComputerTextureBasic,BakedQuad>> dynamicQuads = new EnumMap<>(Direction.class);
            for(Direction direction : Direction.values()){
                dynamicQuads.put(direction,new EnumMap<>(MicroComputerBlockEntity.MicroComputerTextureBasic.class));
            }
            BakedModel staticModelBaked = bakery.bake(STATIC_MODEL_LOCATION, modelState,spriteGetter);
            for (BakedQuad bakedQuad : staticModelBaked.getQuads(null, null, RandomSource.create(), ModelData.EMPTY, null)) {
                Direction direction = null;
                switch (bakedQuad.getSprite().contents().name().getPath()) {
                    case "block/microcomputer/placeholder_down":
                        direction = Direction.DOWN;
                        break;
                    case "block/microcomputer/placeholder_up":
                        direction = Direction.UP;
                        break;
                    case "block/microcomputer/placeholder_east":
                        direction = Direction.EAST;
                        break;
                    case "block/microcomputer/placeholder_west":
                        direction = Direction.WEST;
                        break;
                    case "block/microcomputer/placeholder_north":
                        direction = Direction.NORTH;
                        break;
                    case "block/microcomputer/placeholder_south":
                        direction = Direction.SOUTH;
                        break;
                    default:
                        staticQuads.add(bakedQuad);
                        break;
                }
                                if (direction != null) {
                    dynamicQuads.get(direction).put(MicroComputerBlockEntity.MicroComputerTextureBasic.BLANK,bakedQuad);
                    dynamicQuads.get(direction).put(MicroComputerBlockEntity.MicroComputerTextureBasic.REDSTONE, MicroComputersClient.copyBakedQuadwithNewSprite(bakedQuad,spriteGetter.apply(redstone_side)));
                    dynamicQuads.get(direction).put(MicroComputerBlockEntity.MicroComputerTextureBasic.CABLE, MicroComputersClient.copyBakedQuadwithNewSprite(bakedQuad,spriteGetter.apply(wire_side)));
                }
            }
            EnumMap<Direction,BakedQuad> overlayQuads = new EnumMap<>(Direction.class);
            BakedModel overlayModelBaked = bakery.bake(OVERLAY_MODEL_LOCATION, modelState,spriteGetter);
            for (BakedQuad bakedQuad : overlayModelBaked.getQuads(null, null, RandomSource.create(), ModelData.EMPTY, null)){
                overlayQuads.put(commonMicroComputerBakedModel.TintIndexDirectionMap.get(bakedQuad.getTintIndex()),bakedQuad);
            }
            return new MicroComputerBakedModelForge(staticQuads,dynamicQuads,overlayQuads, staticModelBaked);
        }

        public Collection<Material> getMaterials(IGeometryBakingContext context, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
            ArrayList<Material> materials = new ArrayList<>();
            materials.add(context.getMaterial("1"));
            materials.add(context.getMaterial("8"));
            materials.add(context.getMaterial("particle"));
            materials.add(context.getMaterial("down"));
            materials.add(context.getMaterial("up"));
            materials.add(context.getMaterial("west"));
            materials.add(context.getMaterial("east"));
            materials.add(context.getMaterial("north"));
            materials.add(context.getMaterial("south"));
            materials.add(new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(MicroComputers.MOD_ID, "block/microcomputer/redstone_overlay_tint")));
            materials.add(wire_side);
            materials.add(redstone_side);
            return materials;
        }
    }
}