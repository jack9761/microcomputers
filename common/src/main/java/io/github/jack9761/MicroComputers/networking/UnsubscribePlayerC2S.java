package io.github.jack9761.MicroComputers.networking;

import io.github.jack9761.MicroComputers.MicroComputers;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record UnsubscribePlayerC2S(BlockPos BlockEntityPosition) {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(MicroComputers.MOD_ID,"unsubscribeplayerc2s");
    public FriendlyByteBuf write(){
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeBlockPos(BlockEntityPosition);
        return buf;
    }
    public static UnsubscribePlayerC2S read(FriendlyByteBuf buf){
        return new UnsubscribePlayerC2S(buf.readBlockPos());
    }
}
