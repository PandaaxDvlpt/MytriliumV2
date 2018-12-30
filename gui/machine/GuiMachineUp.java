package fr.pandaax.mytrilium.gui.machine;

import org.lwjgl.opengl.GL11;

import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;


public class GuiMachineUp extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,"textures/gui/container/guiMachineUp.png");

    private TileEntityMachineUp tileMachineUp;
    private IInventory playerInv;
    
    public GuiMachineUp(TileEntityMachineUp tile, InventoryPlayer inventory) 
    {
        super(new ContainerMachineUp(tile, inventory));
        this.tileMachineUp = tile;
        this.playerInv = inventory;
        this.allowUserInput = false;
        this.ySize = 208;
        this.xSize = 196;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int x, int y) 
    {
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        if(this.tileMachineUp.isBurning())
        {
            int i = this.tileMachineUp.getCookProgress(); 
            this.drawTexturedModalRect(k + 45, l + 60, 0, 209, i + 2, 16);
        }
        
        
        
        
        
        
        
            
    }
    
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        this.fontRendererObj.drawString(this.playerInv.hasCustomInventoryName() ? this.playerInv.getInventoryName() : I18n.format(this.playerInv.getInventoryName()), 10, this.ySize - 98, 4210752);
    }

}