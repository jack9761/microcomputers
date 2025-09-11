package io.github.jack9761.MicroComputers.networking;

import io.github.jack9761.MicroComputers.MicroComputers;
import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;
import io.netty.buffer.Unpooled;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public record MicroComputerS2C(CompoundTag MicroComputerTag) {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(MicroComputers.MOD_ID, "microcomputers2c");
    public FriendlyByteBuf write() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeNbt(MicroComputerTag);
        return buf;
    }
    public static MicroComputerS2C read(FriendlyByteBuf buf){
        return new MicroComputerS2C(buf.readNbt());
    }
}
