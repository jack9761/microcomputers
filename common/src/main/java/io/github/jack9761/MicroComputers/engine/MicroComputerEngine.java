package io.github.jack9761.MicroComputers.engine;

import io.github.jack9761.MicroComputers.block.MicroComputerBlock;
import io.github.jack9761.MicroComputers.block.entity.MicroComputerBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

import java.util.Arrays;
import java.util.EnumMap;

public class MicroComputerEngine {
    static final byte MAX_LINE_NUMBERS = 15;
    static final byte MAX_ACCUMULATORS = 3;
    static final byte MAX_INTERNAL_CABLE_CONNECTIONS = 6;
    
    String[] microComputerCode;
    Direction[] internalCableConnections;
    MicroComputerBlockEntity.MicroComputerState microcomputerState;
    byte microcomputerCursor;
    boolean[] microcomputerBreakpoints;
    int[] accumulators;
    int readWriteBuffer;

    public MicroComputerEngine() {
        this.microComputerCode = new String[MAX_LINE_NUMBERS];
        Arrays.fill(this.microComputerCode, "");
        this.internalCableConnections = new Direction[MAX_INTERNAL_CABLE_CONNECTIONS];
        Arrays.fill(this.internalCableConnections, null);
        this.microcomputerState = MicroComputerBlockEntity.MicroComputerState.STOPPED;
        this.microcomputerCursor = 0;
        this.microcomputerBreakpoints = new boolean[MAX_LINE_NUMBERS];
        this.accumulators = new int[MAX_ACCUMULATORS];
        this.readWriteBuffer = 0;
    }

    public MicroComputerEngine(String[] microComputerCode, Direction[] internalCableConnections, MicroComputerBlockEntity.MicroComputerState microcomputerState, byte microcomputerCursor, boolean[] microcomputerBreakpoints, int[] accumulators, int readWriteBuffer) {
        this.microComputerCode = microComputerCode;
        this.internalCableConnections = internalCableConnections;
        this.microcomputerState = microcomputerState;
        this.microcomputerCursor = microcomputerCursor;
        this.microcomputerBreakpoints = microcomputerBreakpoints;
        this.accumulators = accumulators;
        this.readWriteBuffer = readWriteBuffer;
    }

    public CompoundTag serializeCompoundTag(){
        CompoundTag tag = new CompoundTag();
        tag.putString("microComputerCode", String.join("\n", microComputerCode));

        int[] internalCableConnectionsInt = new int[6];
        for (int i = 0; i < 6; i++) {
            internalCableConnectionsInt[i] = internalCableConnections[i].ordinal();
        }
        tag.putIntArray("internalCableConnections", internalCableConnectionsInt);

        tag.putInt("microComputerState", microcomputerState.ordinal());
        tag.putByte("microcomputerCursor", microcomputerCursor);
        byte[] breakpointsBytes = new byte[microcomputerBreakpoints.length];
        for (int i = 0; i < microcomputerBreakpoints.length; i++) {
            breakpointsBytes[i] = (byte) (microcomputerBreakpoints[i] ? 1 : 0);
        }
        tag.putByteArray("microcomputerBreakpoints", breakpointsBytes);
        tag.putIntArray("accumulators", accumulators);
        tag.putInt("readWriteBuffer", readWriteBuffer);
        return tag;
    }

//    public FriendlyByteBuf serializePacketBuf(FriendlyByteBuf buf){
//        buf.writeCollection(Arrays.asList(microComputerCode), FriendlyByteBuf::writeUtf);
//        buf.writeCollection(Arrays.asList(internalCableConnections), FriendlyByteBuf::writeEnum);
//        buf.writeEnum(microcomputerState);
//        buf.writeByte(microcomputerCursor);
//        buf.writeByteArray(microcomputerBreakpoints);
//        buf.writeVarIntArray(accumulators);
//        return buf;
//    }
//
//    public static MicroComputerEngine microComputerEnginefromFriendlyPacketBuf(FriendlyByteBuf buf){
//        String[] code = buf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf).toArray(new String[0]);
//        Direction[] connections = buf.readCollection(ArrayList::new,b -> b.readEnum(Direction.class)).toArray(new Direction[0]);
//        MicroComputerBlockEntity.MicroComputerState state = buf.readEnum(MicroComputerBlockEntity.MicroComputerState.class);
//        byte cursor = buf.readByte();
//        byte[] breakpoints = buf.readByteArray();
//        int[] accumulators = buf.readVarIntArray();
//        return new MicroComputerEngine(code, connections, state, cursor, breakpoints, accumulators);
//    }

    public static MicroComputerEngine microComputerEnginefromCompoundTag(CompoundTag tag){
        String[] microComputerCode = tag.getString("microComputerCode").split("\n");

        int[] internalCableConnectionsInt = tag.getIntArray("internalCableConnections");
        Direction[] internalCableConnections = new Direction[6];
        for (int i = 0; i < 6; i++) {
            internalCableConnections[i] = Direction.values()[internalCableConnectionsInt[i]];
        }

        MicroComputerBlockEntity.MicroComputerState microcomputerState = MicroComputerBlockEntity.MicroComputerState.values()[tag.getInt("microComputerState")];
        byte microcomputerCursor = tag.getByte("microcomputerCursor");
        byte[] breakpointsBytes = tag.getByteArray("microcomputerBreakpoints");
        boolean[] microcomputerBreakpoints = new boolean[breakpointsBytes.length];
        for (int i = 0; i < breakpointsBytes.length; i++) {
            microcomputerBreakpoints[i] = breakpointsBytes[i] != 0;
        }
        int[] accumulators = tag.getIntArray("accumulators");
        int readWriteBuffer = tag.getInt("readWriteBuffer");
        return new MicroComputerEngine(microComputerCode, internalCableConnections, microcomputerState, microcomputerCursor, microcomputerBreakpoints, accumulators, readWriteBuffer);
    }


    public void step() {

    }
}
