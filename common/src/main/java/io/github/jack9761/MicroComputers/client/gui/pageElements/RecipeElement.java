package io.github.jack9761.MicroComputers.client.gui.pageElements;

import io.github.jack9761.MicroComputers.MicroComputers;
import io.github.jack9761.MicroComputers.client.gui.InstructionManualScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.ArrayList;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class RecipeElement implements IPageElement {

    private static final ResourceLocation CRAFTING_ARROW_TEXTURE = new ResourceLocation(MicroComputers.MOD_ID, "textures/gui/recipe_arrow.png");

    private ArrayList<ItemDisplayElement> ingedientDisplayElementGrid = new ArrayList<ItemDisplayElement>();
    private ItemDisplayElement resultDisplayElement;
    private byte GridSizeX;
    private byte GridSizeY;
    public RecipeElement(String RecipeID) {
        Optional<? extends Recipe<?>> recipeOptional = Minecraft.getInstance().level.getRecipeManager().byKey(ResourceLocation.tryParse(RecipeID));
        recipeOptional.ifPresent(foundRecipe -> {
            if(foundRecipe instanceof ShapedRecipe) {
                GridSizeX = (byte) ((ShapedRecipe) foundRecipe).getHeight();
                GridSizeY = (byte) ((ShapedRecipe) foundRecipe).getWidth();
            }
            else if(foundRecipe instanceof ShapelessRecipe){
                if (foundRecipe.canCraftInDimensions(2,2)){
                    GridSizeX = 2;
                    GridSizeY = 2;
                }
                else {
                    GridSizeX = 3;
                    GridSizeY = 3;
                }
            }
            for(int row = 0; row<GridSizeX; row++){
                for(int column = 0; column<GridSizeY; column++) {
                    int index = row * GridSizeX + column;
                    ItemStack[] ingredient = foundRecipe.getIngredients().get(index).getItems();
                    if(ingredient.length != 0){
                        ingedientDisplayElementGrid.add(new ItemDisplayElement(ingredient[0].getItem()));
                    }
                }
            }
            resultDisplayElement = new ItemDisplayElement(foundRecipe.getResultItem(Minecraft.getInstance().level.registryAccess()).getItem());
        });
    }

    @Override
    public void init(InstructionManualScreen screen) {}

    @Override
    public void render(GuiGraphics guiGraphics, Font font, int x, int y, int mouseX, int mouseY) {
        for (int row = 0; row<GridSizeX; row++ ) {
            for (int column = 0; column<GridSizeY; column++) {
                int index = row * GridSizeX + column;
                if(index < ingedientDisplayElementGrid.size()) {
                    ingedientDisplayElementGrid.get(index).render(guiGraphics, font, x + column * 18, y + row * 18, mouseX, mouseY);
                }
            }
        }
        //Arrow U: 90 V: 35 , Arrow Size: 22x15
        guiGraphics.blit(CRAFTING_ARROW_TEXTURE, x + getWidth()/2, y + getHeight()/2 - 7,0,0, 23, 17,23,17);
        resultDisplayElement.render(guiGraphics,font, x+getWidth()-20, y + getHeight()/2 - 9, mouseX, mouseY);
    }

    @Override
    public int getWidth() {
        //Page Width
        return 139;
    }

    @Override
    public int getHeight() {
        return GridSizeY*20 ;
    }
}
