package io.github.jack9761.MicroComputers;

import dev.architectury.networking.NetworkManager;
import io.github.jack9761.MicroComputers.item.InstructionManualItem;
import io.github.jack9761.MicroComputers.networking.SetPagePacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class MicroComputers {
    public static final String MOD_ID = "microcomputers";

    public static void init() {
        //Networking
        NetworkManager.registerReceiver(NetworkManager.Side.C2S,SetPagePacket.PACKET_ID, (buf,context) -> {
            Player player = context.getPlayer();
            SetPagePacket packet = SetPagePacket.read(buf);
            if(player != null){
                if(player.getMainHandItem().getItem() instanceof InstructionManualItem) {
                    player.getMainHandItem().getTag().putInt(InstructionManualItem.PAGE_NBT_TAG, packet.pageNumber());
                }
            }
        });
    }
}
