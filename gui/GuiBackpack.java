package fr.pandaax.mytrilium.gui;

import fr.pandaax.mytrilium.backpack.ContainerBackpack;
import fr.pandaax.mytrilium.backpack.InventoryBackpack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiBackpack extends GuiContainer
{
    public static final ResourceLocation texture = new ResourceLocation("mytrilium:textures/gui/container/Backpack.png");
    public int rows;
    protected InventoryBackpack inv;
    protected InventoryPlayer playerInv;

    public GuiBackpack(InventoryPlayer playerInv, InventoryBackpack inv)
    {
        super(new ContainerBackpack(playerInv, inv));
        this.playerInv = playerInv;
        this.inv = inv;
        this.allowUserInput = false;

        this.rows = inv.getSizeInventory() / 9;

        this.ySize = 114 + this.rows * 18;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        this.fontRendererObj.drawString(I18n.format(this.inv.getInventoryName(), new Object[0]), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInv.hasCustomInventoryName() ? this.playerInv.getInventoryName() : I18n.format(this.playerInv.getInventoryName(), new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float prt, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);

        // Centering GUI
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.rows * 18 + 17);

        this.drawTexturedModalRect(k, l + this.rows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
