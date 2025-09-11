package io.github.jack9761.MicroComputers;

import io.github.jack9761.MicroComputers.networking.ModPackets;

public final class MicroComputers {
    public static final String MOD_ID = "microcomputers";

    public static void init() {
        //Networking
        ModPackets.registerS2CPackets();
        ModPackets.registerC2SPackets();
    }
}
