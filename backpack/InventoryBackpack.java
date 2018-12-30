package fr.pandaax.mytrilium.backpack;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryBackpack implements IInventory
{
    ItemStack[] content;

    public InventoryBackpack(ItemStack item)
    {
        this.content = new ItemStack[54];
        if (!item.hasTagCompound())
        {
            item.setTagCompound(new NBTTagCompound());
        }
        readFromNBT(item.getTagCompound());
    }

    public int getSizeInventory()
    {
        return this.content.length;
    }

    public ItemStack getStackInSlot(int slot)
    {
        return this.content[slot];
    }

    public ItemStack decrStackSize(int slotIndex, int amount)
    {
        if (this.content[slotIndex] != null)
        {
            if (this.content[slotIndex].stackSize <= amount)
            {
                ItemStack itemstack = this.content[slotIndex];
                this.content[slotIndex] = null;
                markDirty();
                return itemstack;
            }
            ItemStack itemstack = this.content[slotIndex].splitStack(amount);
            if (this.content[slotIndex].stackSize == 0)
            {
                this.content[slotIndex] = null;
            }
            markDirty();
            return itemstack;
        }
        return null;
    }

    public ItemStack getStackInSlotOnClosing(int slotIndex)
    {
        if (this.content[slotIndex] != null)
        {
            ItemStack itemstack = this.content[slotIndex];
            this.content[slotIndex] = null;
            return itemstack;
        }
        return null;
    }

    public void setInventorySlotContents(int slotIndex, ItemStack stack)
    {
        this.content[slotIndex] = stack;
        if ((stack != null) && (stack.stackSize > getInventoryStackLimit()))
        {
            stack.stackSize = getInventoryStackLimit();
        }
        markDirty();
    }

    public String getInventoryName()
    {
        return "Item.Backpack";
    }

    public boolean hasCustomInventoryName()
    {
        return false;
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public void markDirty()
    {
    }

    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }

    public void openInventory()
    {
    }

    public void closeInventory()
    {
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return true;
    }

    public void readFromNBT(NBTTagCompound comp)
    {
        NBTTagList nbtlist = comp.getTagList("Inventory", 10);
        for (int i = 0; i < nbtlist.tagCount(); i++)
        {
            NBTTagCompound comp1 = nbtlist.getCompoundTagAt(i);
            int slot = comp1.getInteger("Slot");
            this.content[slot] = ItemStack.loadItemStackFromNBT(comp1);
        }
    }

    public void writeToNBT(NBTTagCompound comp)
    {
        NBTTagList nbtlist = new NBTTagList();
        for (int i = 0; i < this.content.length; i++)
        {
            if (this.content[i] != null)
            {
                NBTTagCompound comp1 = new NBTTagCompound();
                comp1.setInteger("Slot", i);
                this.content[i].writeToNBT(comp1);
                nbtlist.appendTag(comp1);
            }
        }
        comp.setTag("Inventory", nbtlist);
    }
}

