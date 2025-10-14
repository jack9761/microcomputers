package io.github.jack9761.MicroComputers.forge;

import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import javax.annotation.Nonnull;
import java.util.EnumMap;

public class MicroComputerBlockEntityForge extends MicroComputerBlockEntity {

    public MicroComputerBlockEntityForge(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public static final ModelProperty<EnumMap<Direction, MicroComputerTextures>> sideStates = new ModelProperty<>();

    @Nonnull
    @Override
    public ModelData getModelData() {
        return ModelData.builder().with(sideStates, clientTextureMap).build();
    }
}
