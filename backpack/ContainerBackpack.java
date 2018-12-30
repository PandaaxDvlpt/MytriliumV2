package fr.pandaax.mytrilium.backpack;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerBackpack extends Container
{
    int rows;
    InventoryBackpack inventoryMEPortableChest;

    public ContainerBackpack(InventoryPlayer inventory, InventoryBackpack inventoryMEPortableChest)
    {
        this.inventoryMEPortableChest = inventoryMEPortableChest;
        this.rows = (inventoryMEPortableChest.getSizeInventory() / 9);
        for (int j = 0; j < this.rows; j++)
        {
            for (int k = 0; k < 9; k++)
            {
                addSlotToContainer(new SlotBackpack(inventoryMEPortableChest, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
        bindPlayerInventory(inventory);
    }


    private void bindPlayerInventory(InventoryPlayer inventory)
    {
        int i;
        for (i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
            }
        }
        for (i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 198));
        }
    }


    public void writeToNBT(ItemStack stack)
    {
        if (stack == null)
        {
            return;
        }
        if (stack.getItem() == null)
        {
            return;
        }
        if (!(stack.getItem() instanceof ItemBackpack))
        {
            return;
        }
        if (!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }
        this.inventoryMEPortableChest.writeToNBT(stack.getTagCompound());
    }

    public boolean canInteractWith(EntityPlayer player)
    {
        writeToNBT(player.getHeldItem());
        boolean open = false;
        if (player.getHeldItem().getTagCompound().hasKey("Open"))
        {
            open = player.getHeldItem().getTagCompound().getBoolean("Open");
        }
        if ((player.getHeldItem() == null) || (!(player.getHeldItem().getItem() instanceof ItemBackpack)) || (player.getHeldItem().stackSize > 1) || (!open))
        {
            player.closeScreen();
            return false;
        }
        return true;
    }

    public void onContainerClosed(EntityPlayer player)
    {
        writeToNBT(player.getHeldItem());
        player.getHeldItem().getTagCompound().setBoolean("Open", false);
        super.onContainerClosed(player);
    }

    protected boolean mergeItemStack(ItemStack stack, int p_75135_2_, int p_75135_3_, boolean p_75135_4_)
    {
        return super.mergeItemStack(stack, p_75135_2_, p_75135_3_, p_75135_4_);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);
        if ((slot != null) && (slot.getHasStack()))
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if ((itemstack.getItem() instanceof ItemBackpack))
            {
                writeToNBT(player.getHeldItem());
                return null;
            }
            if (index < this.inventoryMEPortableChest.getSizeInventory())
            {
                if (!mergeItemStack(itemstack1, this.inventoryMEPortableChest.getSizeInventory(), this.inventorySlots
                        .size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemstack1, 0, this.inventoryMEPortableChest.getSizeInventory(), false))
            {
                return null;
            }
            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        writeToNBT(player.getHeldItem());
        return itemstack;
    }

    public ItemStack slotClick(int slotIndex, int buttonPressed, int flag, EntityPlayer player)
    {
        writeToNBT(player.getHeldItem());
        if ((flag == 2) && (buttonPressed == player.inventory.currentItem))
        {
            return null;
        }
        if (slotIndex - this.inventoryMEPortableChest.getSizeInventory() - 27 == player.inventory.currentItem)
        {
            return null;
        }
        return super.slotClick(slotIndex, buttonPressed, flag, player);
    }
}

