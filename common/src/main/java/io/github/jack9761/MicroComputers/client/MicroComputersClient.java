package io.github.jack9761.MicroComputers.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import io.github.jack9761.MicroComputers.block.ModBlock;
import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;
import io.github.jack9761.MicroComputers.block.entity.ModBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RedStoneWireBlock;

import java.util.Map;
import java.util.Optional;

import static io.github.jack9761.MicroComputers.MicroComputers.MOD_ID;

@Environment(EnvType.CLIENT)
public class MicroComputersClient {
    public static final ResourceLocation LOADER_ID = new ResourceLocation(MOD_ID, "microcomputer_loader");
    private static final Map<Integer, Direction> TintIndexDirection = Map.of(
            0, Direction.DOWN,
            1, Direction.UP,
            2, Direction.NORTH,
            3, Direction.SOUTH,
            4, Direction.WEST,
            5, Direction.EAST
    );
    public static void init() {
        RenderTypeRegistry.register(RenderType.cutout(), ModBlock.MICROCOMPUTER_BLOCK.get());
        ColorHandlerRegistry.registerBlockColors(((blockState, blockAndTintGetter, blockPos, i) -> {
            Optional<MicroComputerBlockEntity> BlockEntityOptional = blockAndTintGetter.getBlockEntity(blockPos, ModBlockEntity.MICROCOMPUTER_BLOCK_ENTITY.get());
            if(BlockEntityOptional.isPresent()){
                MicroComputerBlockEntity.MicroComputerTextures texture = BlockEntityOptional.get().clientTextureMap.get(TintIndexDirection.get(i));
                if(texture==null||texture==MicroComputerBlockEntity.MicroComputerTextures.BLANK||texture==MicroComputerBlockEntity.MicroComputerTextures.CABLE){
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