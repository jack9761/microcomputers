package io.github.jack9761.MicroComputers.client.gui;

import dev.architectury.networking.NetworkManager;
import io.github.jack9761.MicroComputers.MicroComputers;
import io.github.jack9761.MicroComputers.networking.SetPagePacket;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Environment(EnvType.CLIENT)
public class InstructionManualScreen extends Screen {
    public static final ResourceLocation MONOSPACE_FONT = new ResourceLocation(MicroComputers.MOD_ID,"monocraft");
    public int current_page;
    public static int end_page = 2;

    private final ArrayList<ArrayList<IPageElement>> pageContent = new ArrayList<ArrayList<IPageElement>>();

    private final ArrayList<AbstractWidget> widgetQueueforInit = new ArrayList<AbstractWidget>();


    public Button next_page_button;
    public Button prev_page_button;
    public Button back_to_start_button;

    public InstructionManualScreen(int page) {
        super(Component.translatable("screen.microcomputers.instruction_manual.title"));
        current_page = page;
        parse_page(current_page);
    }

    public Font getFont(){
        return this.font;
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
                    case "recipe":
                        //linePageContent.add(new RecipeElement(AffectedText));
                        break;
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
        rebuildWidgets();
    }

    @Override
    protected void init() {
        super.init();
        //Back to Start
        this.back_to_start_button = this.addRenderableWidget(Button.builder(Component.literal("Back to Start"), (button) -> {
            this.current_page=0;
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeInt(current_page);
            NetworkManager.sendToServer(SetPagePacket.PACKET_ID, buf);
            parse_page(current_page);
        }).bounds(this.width / 2 - 122, this.height / 4 + 24, 50, 20).build());
        //Next Page
        this.next_page_button = this.addRenderableWidget(Button.builder(Component.literal("Next ->"), (button) -> {
            this.current_page++;
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeInt(current_page);
            NetworkManager.sendToServer(SetPagePacket.PACKET_ID, buf);
            parse_page(current_page);
        }).bounds(this.width / 2 - 122, this.height / 4 + 44, 50, 20).build());
        //Previous Page
        this.prev_page_button = this.addRenderableWidget(Button.builder(Component.literal("<- Prev"), (button) -> {
            this.current_page=this.current_page-1;
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeInt(current_page);
            NetworkManager.sendToServer(SetPagePacket.PACKET_ID, buf);
            parse_page(current_page);
        }).bounds(this.width / 2 - 122, this.height / 4 + 64, 50, 20).build());
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
        int textStartY = backgroundStartY + 12;
        int backgroundEndY = this.height / 2 +90;
        int textEndY = backgroundEndY - 4;
        int lineSpacing = font.lineHeight+2; // The magic number!
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
            for (IPageElement element : lines) {
                element.render(guiGraphics, this.font, cursorX, cursorY, mouseX, mouseY);
                cursorX+= element.getWidth();
            }
            cursorY += lineSpacing;
            guiGraphics.fill(textStartX,cursorY-3,textEndX+1,cursorY-2,lineColor);
            guiGraphics.fill(textStartX,cursorY-2,textEndX,cursorY-1,underlineColor);
        }
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen(){
        return false;
    }
}
