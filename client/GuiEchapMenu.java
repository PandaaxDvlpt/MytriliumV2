package fr.pandaax.mytrilium.client;

import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GuiEchapMenu extends GuiScreen
{
    private int field_146445_a;
    private int field_146444_f;

    @Override
    public void initGui()
    {
        this.field_146445_a = 0;
        this.buttonList.clear();
        byte b0 = -16;
        boolean flag = true;

        if (!this.mc.isIntegratedServerRunning())
        {
            ((GuiButton) this.buttonList.get(0)).displayString = I18n.format("Quitter le serveur !", new Object[0]);
        }

        int bw = 120;
        int bh = 20;
        int spacing = 40;
        int xBase = this.width - bw - spacing;
        int ySpacing = 10;
        int yBase = ((this.height - ((bh + ySpacing) * 6)) / 2) + 10;
        int y0 = bh + ySpacing;

        this.buttonList.add(new CustomGuiButton(4, xBase, yBase, bw, bh, EnumChatFormatting.DARK_PURPLE + I18n.format("Retour sur Mytrilium")));
        this.buttonList.add(new CustomGuiButton(0, xBase, yBase + y0, bw, bh, EnumChatFormatting.DARK_PURPLE + I18n.format("Options")));
        this.buttonList.add(new CustomGuiButton(12, xBase, yBase + y0 * 2, bw, bh, EnumChatFormatting.DARK_PURPLE + I18n.format("Teamspeak")));
        this.buttonList.add(new CustomGuiButton(7, xBase, yBase + y0 * 3, bw, bh, EnumChatFormatting.LIGHT_PURPLE + I18n.format("Site Web")));
        this.buttonList.add(new CustomGuiButton(5, xBase, yBase + y0 * 4, bw, bh, EnumChatFormatting.DARK_PURPLE + I18n.format("Radio ON/OFF")));
        this.buttonList.add(new CustomGuiButton(1, xBase, yBase + y0 * 5, bw, bh, EnumChatFormatting.RED + I18n.format("Quitter le serveur")));
        //this.buttonList.add(new GuiButton(6, xBase, yBase + (bh + ySpacing) * 6, bw, bh, EnumChatFormatting.DARK_PURPLE + I18n.format("Stats", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 1:
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient) null);
                this.mc.displayGuiScreen(new GuiMainMenu());
            case 2:
            case 3:
            default:
                break;
            case 4:
                this.mc.displayGuiScreen((GuiScreen) null);
                this.mc.setIngameFocus();
                break;
            case 5:
                RadioPlayer.action();

                break;
            case 6:
                if (this.mc.thePlayer != null)
                    this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                break;
        }

        if (button.id == 7)
        {
            if (Desktop.isDesktopSupported())
            {
                try
                {
                    URI url = new URI("http://mytrilium.fr");
                    Desktop.getDesktop().browse(url);
                } catch (IOException e)
                {
                    e.printStackTrace();
                } catch (URISyntaxException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146444_f;
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game.mytrilium"), this.width / 2, 40, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    @Override
    public void drawDefaultBackground()
    {
        super.drawDefaultBackground();

        // ########### //
        //  Draw logo  //
        // ########### //

        int lw = 621;
        int lh = 720;
        int lx = 35;
        int ly = (this.height - lh) / 2;

        this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID + ":textures/gui/logo.png"));
        this.drawTexturedModalRect(lx, ly, 0, 0, lw, lh);
    }
}