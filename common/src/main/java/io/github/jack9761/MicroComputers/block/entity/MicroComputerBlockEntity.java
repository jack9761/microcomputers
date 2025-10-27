package io.github.jack9761.MicroComputers.block.entity;

import dev.architectury.networking.NetworkManager;
import io.github.jack9761.MicroComputers.engine.MicroComputerEngine;
import io.github.jack9761.MicroComputers.networking.MicroComputerS2C;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumMap;
import java.util.HashSet;


public class MicroComputerBlockEntity extends BaseContainerBlockEntity implements MenuProvider {

    public static enum MicroComputerState{
        STOPPED,
        EXECUTING,
        BLOCKED_WRITE,
        BLOCKED_READ
    }

    public static enum MicroComputerTextures{
        BLANK,
        CABLE,
        REDSTONE_0,
        REDSTONE_1,
        REDSTONE_2,
        REDSTONE_3,
        REDSTONE_4,
        REDSTONE_5,
        REDSTONE_6,
        REDSTONE_7,
        REDSTONE_8,
        REDSTONE_9,
        REDSTONE_10,
        REDSTONE_11,
        REDSTONE_12,
        REDSTONE_13,
        REDSTONE_14,
        REDSTONE_15
    }
    public static enum MicroComputerTextureBasic{
        BLANK,
        CABLE,
        REDSTONE
        }
    private MicroComputerEngine computerEngine;

    private HashSet<ServerPlayer> watchingplayers;

    public static final int ADDON_SLOTS = 2;

    private NonNullList<ItemStack> addonList;

    public EnumMap<Direction,MicroComputerTextures> clientTextureMap;

    public MicroComputerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntity.MICROCOMPUTER_BLOCK_ENTITY.get(), pos, blockState);
        this.addonList = NonNullList.withSize(ADDON_SLOTS,ItemStack.EMPTY);
        watchingplayers = new HashSet<ServerPlayer>();
        computerEngine = new MicroComputerEngine();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.addonList);
        tag.put("microcomputer_engine",computerEngine.serializeCompoundTag());
    }

    @Override
    public void load(CompoundTag nbt) {
        if(level.isClientSide()){

        }
        super.load(nbt);
        ContainerHelper.loadAllItems(nbt, this.addonList);
        computerEngine = MicroComputerEngine.microComputerEnginefromCompoundTag((CompoundTag) nbt.get("microcomputer_engine"));
    }

    @Override
    public CompoundTag getUpdateTag() {

        return super.getUpdateTag();
    }


    @Override
    public int getContainerSize() {
        return ADDON_SLOTS;
    }

    @Override
    public boolean isEmpty() {
        return this.addonList.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.addonList.get(slot);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return super.canPlaceItem(index, stack);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(addonList,slot,amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(addonList,slot);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.addonList.set(slot,stack);
        if(!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()){
            stack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this,player);
    }

    @Override
    public void clearContent() {
        this.addonList.clear();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.microcomputers.microcomputer");
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("blockentity.microcomputers.microcomputer");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new MicroComputerMenu(containerId,inventory);
    }

    public static class Ticker<T extends BlockEntity> implements BlockEntityTicker<T>{
        @Override
        public void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
            if(blockEntity instanceof MicroComputerBlockEntity){
                MicroComputerBlockEntity microComputerBlockEntity = (MicroComputerBlockEntity) blockEntity;
                if(microComputerBlockEntity.computerEngine!=null){
                    microComputerBlockEntity.computerEngine.step();
                }
            }
        }
    }

    public void SyncS2C(ServerPlayer player){
        MicroComputerS2C packet = new MicroComputerS2C(computerEngine.serializeCompoundTag());
        NetworkManager.sendToPlayer(player,MicroComputerS2C.PACKET_ID,packet.write());
        watchingplayers.add(player);
    }
}
