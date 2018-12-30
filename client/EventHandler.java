package fr.pandaax.mytrilium.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class EventHandler
{
    public static final String[] directions = {"South", "West", "North", "East"};
    public Minecraft mc;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPre(RenderGameOverlayEvent.Pre event)
    {

        if (event.type == RenderGameOverlayEvent.ElementType.DEBUG)
        {
            FontRenderer f = Minecraft.getMinecraft().fontRenderer;

            event.setCanceled(true);
            drawString(Minecraft.getMinecraft().fontRenderer, EnumChatFormatting.DARK_PURPLE + "Mytrilium V3" + EnumChatFormatting.LIGHT_PURPLE + " / " + EnumChatFormatting.DARK_PURPLE + Minecraft.getMinecraft().debug.split(",", 2)[0], 2, 2, 16777215);

            drawString(Minecraft.getMinecraft().fontRenderer, EnumChatFormatting.LIGHT_PURPLE + "X : " + EnumChatFormatting.DARK_PURPLE + (int) mc.getMinecraft().thePlayer.posX + " / " + EnumChatFormatting.LIGHT_PURPLE + "Y : " + EnumChatFormatting.DARK_PURPLE + (int) mc.getMinecraft().thePlayer.posY + " / " + EnumChatFormatting.LIGHT_PURPLE + "Z : " + EnumChatFormatting.DARK_PURPLE + (int) mc.getMinecraft().thePlayer.posZ, 2, 12, 16777215);

            drawString(Minecraft.getMinecraft().fontRenderer, EnumChatFormatting.LIGHT_PURPLE + "Direction: " + EnumChatFormatting.DARK_PURPLE + directions[
                    (net.minecraft.util.MathHelper.floor_double(
                            Minecraft.getMinecraft().thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3)], 2, 22, 16777215);

        }


    }


    public void drawString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
    {
        par1FontRenderer.drawStringWithShadow(par2Str, par3, par4, par5);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
        if (event.gui instanceof GuiIngameMenu)
            event.gui = new GuiEchapMenu();
    }
}