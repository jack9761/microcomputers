package io.github.jack9761.MicroComputers.item;

import io.github.jack9761.MicroComputers.client.gui.InstructionManualScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InstructionManualItem extends Item {
    public static final String PAGE_NBT_TAG = "page_number";
    public InstructionManualItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(player.getItemInHand(usedHand).getTag()==null||!player.getItemInHand(usedHand).getTag().contains("page_number")){
            player.getItemInHand(usedHand).getOrCreateTag().putInt(PAGE_NBT_TAG,0);
        }
        if(level.isClientSide){
            Minecraft.getInstance().setScreen(new InstructionManualScreen(player.getItemInHand(usedHand).getTag().getInt(PAGE_NBT_TAG)));
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(usedHand), level.isClientSide);
    }
}
