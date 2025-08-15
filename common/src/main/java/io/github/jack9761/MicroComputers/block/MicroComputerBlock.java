package io.github.jack9761.MicroComputers.block;

import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class MicroComputerBlock extends BaseEntityBlock {

    private static VoxelShape COLLISON_SHAPE = Block.box(0,0,0,16,16,16);

//    private static VoxelShape OLD_COLLISON_SHAPE = Stream.of(
//        Block.box(0, 0, 2, 16, 16, 14),
//        Block.box(2, 0, 0, 14, 16, 2),
//        Block.box(2, 0, 14, 14, 16, 16)
//        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static VoxelShape VISUAL_SHAPE = Stream.of(
            Shapes.join(Block.box(2, 0, 2, 14, 1, 14), Block.box(3, 1, 3, 13, 2, 13), BooleanOp.OR),
            Shapes.join(Block.box(2, 2, 0, 14, 14, 1), Block.box(3, 3, 1, 13, 13, 2), BooleanOp.OR),
            Shapes.join(Block.box(2, 2, 15, 14, 14, 16), Block.box(3, 3, 14, 13, 13, 15), BooleanOp.OR),
            Shapes.join(Block.box(3, 14, 3, 13, 15, 13), Block.box(2, 15, 2, 14, 16, 14), BooleanOp.OR),
            Shapes.join(Block.box(1, 3, 3, 2, 13, 13), Block.box(0, 2, 2, 1, 14, 14), BooleanOp.OR),
            Shapes.join(Block.box(14, 3, 3, 15, 13, 13), Block.box(15, 2, 2, 16, 14, 14), BooleanOp.OR),
            Block.box(2, 2, 2, 14, 14, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public MicroComputerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISON_SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return VISUAL_SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return super.getTicker(level, state, blockEntityType);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MicroComputerBlockEntity(pos,state);
    }
}
