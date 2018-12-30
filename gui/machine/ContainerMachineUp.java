package fr.pandaax.mytrilium.gui.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineUp extends Container 

{

    
    private TileEntityMachineUp tileMachineUp;
    
    
    
    public ContainerMachineUp(TileEntityMachineUp tile, InventoryPlayer inventory)
    {
        this.tileMachineUp = tile;
        this.addSlotToContainer(new Slot(tile, 0, 48, 28)); //Lancez votre jeu en debug pour calibrer vos slots
        this.addSlotToContainer(new Slot(tile, 1, 88, 28));
        this.addSlotToContainer(new SlotResultUp(tile, 2, 88, 88)); //Ici c'est un slot que j'ai créer, on le fera après
        this.bindPlayerInventory(inventory); //Les containers ont été vus dans un tutoriel de robin, merci de d'y référer   
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileMachineUp.isUseableByPlayer(player);
    }
    
    private void bindPlayerInventory(InventoryPlayer inventory)
    {
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 17 + j * 18, 125 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventory, i, 17 + i * 18, 183));
        }
    }
    
    public ItemStack transferStackInSlot(EntityPlayer player, int quantity)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(quantity);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (quantity < this.tileMachineUp.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.tileMachineUp.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.tileMachineUp.getSizeInventory(), false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
    
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        this.tileMachineUp.closeInventory();
    }
}