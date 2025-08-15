package io.github.jack9761.MicroComputers.item;

import io.github.jack9761.MicroComputers.MicroComputers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> ADDON_CARDS = TagKey.create(Registries.ITEM, new ResourceLocation(MicroComputers.MOD_ID, "addon_cards"));
}
