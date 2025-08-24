package io.github.jack9761.MicroComputers.networking;

import io.github.jack9761.MicroComputers.MicroComputers;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record SetPagePacket(int pageNumber){
    public static final ResourceLocation PACKET_ID = new ResourceLocation(MicroComputers.MOD_ID, "sendpagepacket");
    public FriendlyByteBuf write(){
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(pageNumber);
        return buf;
    }
    public static SetPagePacket read(FriendlyByteBuf buf){
        return new SetPagePacket(buf.readInt());
    }
}