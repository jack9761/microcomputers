package io.github.jack9761.MicroComputers.client.gui;

import dev.architectury.networking.NetworkManager;
import io.github.jack9761.MicroComputers.MicroComputers;
import io.github.jack9761.MicroComputers.client.gui.buttons.ImageButton;
import io.github.jack9761.MicroComputers.client.gui.pageElements.*;
import io.github.jack9761.MicroComputers.networking.SetPagePacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Environment(EnvType.CLIENT)
public class InstructionManualScreen extends Screen {
    private static final ResourceLocation MONOSPACE_FONT = new ResourceLocation(MicroComputers.MOD_ID,"monocraft");
    private static final ResourceLocation PAGE_TURN_BUTTONS = new ResourceLocation(MicroComputers.MOD_ID, "textures/gui/page_turn_buttons.png");

    public int current_page;
    private static int end_page = 2;

    private final ArrayList<ArrayList<IPageElement>> pageContent = new ArrayList<ArrayList<IPageElement>>();

    private final ArrayList<AbstractWidget> widgetQueueforInit = new ArrayList<AbstractWidget>();

    public ImageButton next_page_button;
    public ImageButton prev_page_button;
    public ImageButton back_to_start_button;

    public static Component formattedCharSequenceToComponent(FormattedCharSequence seq) {
        StringBuilder sb = new StringBuilder();
        seq.accept((index, style, codePoint) -> {
            sb.appendCodePoint(codePoint);
            return true;
        });
        return Component.literal(sb.toString());
    }

    public InstructionManualScreen(int page) {
        super(Component.translatable("screen.microcomputers.instruction_manual.title"));
        current_page = page;
        parse_page(current_page);
    }

    public void openPage(int page) {
        this.current_page = page;
        SetPagePacket packet = new SetPagePacket(page);
        FriendlyByteBuf buf =  packet.write();
        NetworkManager.sendToServer(SetPagePacket.PACKET_ID, buf);
        this.rebuildWidgets();
    }

    public void parse_page(int targetPage) {
        pageContent.clear();
        String[] StringLines = Component.translatable("instruction_manual.page." + current_page + ".contents").getString().split("\\n");
        Pattern formattingCommand = Pattern.compile("\\[([^\\]]+)\\](?:\\(([^)]+)\\))?");
        for (String lineString : StringLines) {
            int cursor = 0;
            ArrayList<IPageElement> linePageContent = new ArrayList<IPageElement>();
            pageContent.add(linePageContent);
            Matcher matcher = formattingCommand.matcher(lineString);
            while(matcher.find()){
                if(matcher.start() > cursor){
                    linePageContent.add(new PlainTextElement(lineString.substring(cursor, matcher.start())));
                }
                String[] CommandWithParams = matcher.group(1).split(",");
                String AffectedText = matcher.group(2);
                switch (CommandWithParams[0]) {
                    case "link":
                        linePageContent.add(new LinkElement(AffectedText, Integer.parseInt(CommandWithParams[1])));
                        break;
                    case "rightalign":
                        linePageContent.add(new RightAlignElement(AffectedText));
                        break;
                    case "indent":
                        linePageContent.add(new IndentElement(Integer.parseInt(CommandWithParams[1])));
                        break;
                    case "recipe":
                        linePageContent.add(new RecipeElement(CommandWithParams[1]));
                        break;
                    case "itemdisplay":
                        linePageContent.add(new ItemDisplayElement(CommandWithParams[1]));
                        break;
                    case "literal":
                        linePageContent.add(new LiteralTextElement(AffectedText));
                    default:
                        linePageContent.add(new PlainTextElement(lineString.substring(matcher.start(), matcher.end())));
                        break;
                }
                cursor = matcher.end();
            }
            if(cursor < lineString.length()){
                linePageContent.add(new PlainTextElement(lineString.substring(cursor)));
            }
        }
        //Word wrapping
        for (int lineindex = 0; lineindex < pageContent.size(); lineindex++) {
            int cursorX = 0;
            for (IPageElement PageElement :pageContent.get(lineindex)) {
                if(cursorX+PageElement.getWidth()>140){
                    if (PageElement instanceof PlainTextElement){
                        List<FormattedCharSequence> splittext = Minecraft.getInstance().font.split(((PlainTextElement) PageElement).getText(),140-cursorX);
                        ((PlainTextElement) PageElement).setText(formattedCharSequenceToComponent(splittext.get(0)));
                        pageContent.add(lineindex+1,new ArrayList<IPageElement>());
                        StringBuilder newpagetext = new StringBuilder();
                        for (int i = 1; i < splittext.size(); i++) {
                            newpagetext.append(formattedCharSequenceToComponent(splittext.get(i)).getString());
                            if (i != splittext.size() - 1) {
                                newpagetext.append(" ");
                            }
                        }
                        pageContent.get(lineindex+1).add(new PlainTextElement(String.valueOf(newpagetext)));
                        break;
                    }
                    else{
                        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Page:"+current_page+" Line:"+lineindex+" is too long."));
                    }
                }
                cursorX+=PageElement.getWidth();
            }

        }
    }

    @Override
    protected void init() {
        super.init();
        parse_page(current_page);
        //Back to Start
        this.back_to_start_button = this.addRenderableWidget(new ImageButton(this.width / 2 - 74,this.height / 2 - 100,40,PAGE_TURN_BUTTONS,(button) -> {
            openPage(0);
        }));
        //Next Page
        this.next_page_button = this.addRenderableWidget(new ImageButton(this.width / 2 + 54,this.height / 2 +74,20,PAGE_TURN_BUTTONS,(button) -> {
            openPage(current_page+1);
        }));
        //Previous Page
        this.prev_page_button = this.addRenderableWidget(new ImageButton(this.width / 2 - 74,this.height / 2 +74,0,PAGE_TURN_BUTTONS,(button) -> {
            openPage(current_page-1);
        }));
        for (ArrayList<IPageElement> Lines : pageContent) {
            for (IPageElement line : Lines) {
                line.init(this);
            }
        }
        AddWidgetsFromQueue();
        updateButtonStates();
    }
    private void updateButtonStates(){
        this.prev_page_button.active = (current_page != 0);
        this.next_page_button.active = (current_page != end_page);
    }

    public void AppendWidgetToQueue(AbstractWidget widget){
        widgetQueueforInit.add(widget);
    }

    private void AddWidgetsFromQueue(){
        for (int i = 0; i < widgetQueueforInit.size(); i++) {
            addRenderableWidget(widgetQueueforInit.get(i));
        }
        widgetQueueforInit.clear();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int backgroundStartX = this.width / 2 - 74;
        int textStartX = backgroundStartX+5;
        int backgroundEndX = this.width / 2 + 74;
        int textEndX = backgroundEndX-4;
        int backgroundStartY = this.height / 2 - 100;
        int textStartY = backgroundStartY + 20;
        int backgroundEndY = this.height / 2 +90;
        int textEndY = backgroundEndY - 4;
        int lineSpacing = font.lineHeight+2;
        int lineColor = 0xFF707070; // A light grey color
        int underlineColor = 0xFFB0B0B0; // Lighter grey
        //Background
        guiGraphics.fill(backgroundStartX,backgroundStartY,backgroundEndX, backgroundEndY,0xFFFCFCE0);
        //Title Bar
        Component title = Component.translatable("instruction_manual.page." + current_page + ".title");
        int titleTextWidth = this.font.width(title);
        guiGraphics.drawString(this.font, title, (this.width - titleTextWidth) / 2, backgroundStartY + 2, 0xFF000000, false);
        //Page Number
        guiGraphics.drawString(this.font, String.valueOf(current_page),backgroundEndX-10, backgroundStartY + 2, 0xFF000000, false);
        int cursorY = textStartY;
        for (ArrayList<IPageElement> lines : pageContent) {
            int cursorX = textStartX;
            Boolean drawLine = true;
            int lineheight=0;
            for (IPageElement element : lines) {
                if(element instanceof RightAlignElement){
                    cursorX = textEndX-element.getWidth();
                    element.render(guiGraphics, this.font, cursorX, cursorY, mouseX, mouseY);
                }
                else{
                    element.render(guiGraphics, this.font, cursorX, cursorY, mouseX, mouseY);
                    cursorX+= element.getWidth();
                }
                if (element instanceof RecipeElement) {
                    drawLine = false;
                }
                lineheight = Math.max(lineheight, element.getHeight());
            }
            cursorY = cursorY + lineheight;
            if (drawLine) {
                guiGraphics.fill(textStartX, cursorY - 2, textEndX + 1, cursorY - 1, lineColor);
                guiGraphics.fill(textStartX, cursorY-1, textEndX, cursorY, underlineColor);
                cursorY=cursorY+1;
            }
        }
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen(){
        return false;
    }
}
