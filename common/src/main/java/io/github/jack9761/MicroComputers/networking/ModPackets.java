package io.github.jack9761.MicroComputers.networking;

import dev.architectury.networking.NetworkManager;
import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;
import io.github.jack9761.MicroComputers.item.InstructionManualItem;
import net.minecraft.world.entity.player.Player;

public class ModPackets {
    public static void registerC2SPackets(){
        NetworkManager.registerReceiver(NetworkManager.Side.C2S,SetPagePacket.PACKET_ID, (buf, context) -> {
            Player player = context.getPlayer();
            SetPagePacket packet = SetPagePacket.read(buf);
            if(player != null){
                if(player.getMainHandItem().getItem() instanceof InstructionManualItem) {
                    player.getMainHandItem().getTag().putInt(InstructionManualItem.PAGE_NBT_TAG, packet.pageNumber());
                }
            }
        });
        NetworkManager.registerReceiver(NetworkManager.Side.C2S,UnsubscribePlayerC2S.PACKET_ID, (buf, context) -> {
            Player player = context.getPlayer();
            UnsubscribePlayerC2S packet = UnsubscribePlayerC2S.read(buf);
            if(player != null){
                if(packet.BlockEntityPosition() != null && player.level.getBlockEntity(packet.BlockEntityPosition()) != null){
                    if(player.level.getBlockEntity(packet.BlockEntityPosition()) instanceof MicroComputerBlockEntity){

                    }
                }
            }
        }
    }

    public static void registerS2CPackets(){
        //Server to Client
    }
}
