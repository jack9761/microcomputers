package io.github.jack9761.MicroComputers;

import io.github.jack9761.MicroComputers.networking.ModPackets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MicroComputers {
    public static final String MOD_ID = "microcomputers";
    public static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        //Networking
        ModPackets.registerS2CPackets();
        ModPackets.registerC2SPackets();
    }
}
