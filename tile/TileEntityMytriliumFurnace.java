package fr.pandaax.mytrilium.tile;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMytriliumFurnace extends TileEntity
        implements ISidedInventory
{
    private static final int[] slotsTop = {0};
    private static final int[] slotsBottom = {2, 1};
    private static final int[] slotsSides = {1};
    public int furnaceBurnTime;
    public int currentBurnTime;
    public int furnaceCookTime;
    private ItemStack[] furnaceItemStacks = new ItemStack[3];
    private String furnaceName;

    public static int getItemBurnTime(ItemStack p_145952_0_)
    {
        if (p_145952_0_ == null)
        {
            return 0;
        }
        Item item = p_145952_0_.getItem();
        if (((item instanceof ItemBlock)) && (Block.getBlockFromItem(item) != Blocks.air))
        {
            Block block = Block.getBlockFromItem(item);
            if (block == Blocks.wooden_slab)
            {
                return 150;
            }
            if (block.getMaterial() == Material.wood)
            {
                return 300;
            }
            if (block == Blocks.coal_block)
            {
                return 16000;
            }
        }
        if (((item instanceof ItemTool)) && (((ItemTool) item).getToolMaterialName().equals("WOOD")))
        {
            return 200;
        }
        if (((item instanceof ItemSword)) && (((ItemSword) item).getToolMaterialName().equals("WOOD")))
        {
            return 200;
        }
        if (((item instanceof ItemHoe)) && (((ItemHoe) item).getToolMaterialName().equals("WOOD")))
        {
            return 200;
        }
        if (item == Items.stick)
        {
            return 100;
        }
        if (item == Items.coal)
        {
            return 1600;
        }
        if (item == Items.lava_bucket)
        {
            return 20000;
        }
        if (item == Item.getItemFromBlock(Blocks.sapling))
        {
            return 100;
        }
        if (item == Items.blaze_rod)
        {
            return 2400;
        }
        return GameRegistry.getFuelValue(p_145952_0_);
    }

    public static boolean isItemFuel(ItemStack p_145954_0_)
    {
        return getItemBurnTime(p_145954_0_) > 0;
    }

    public void furnaceName(String string)
    {
        this.furnaceName = string;
    }

    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }

    public ItemStack getStackInSlot(int slot)
    {
        return this.furnaceItemStacks[slot];
    }

    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (this.furnaceItemStacks[p_70298_1_] != null)
        {
            if (this.furnaceItemStacks[p_70298_1_].stackSize <= p_70298_2_)
            {
                ItemStack itemstack = this.furnaceItemStacks[p_70298_1_];
                this.furnaceItemStacks[p_70298_1_] = null;
                return itemstack;
            }
            ItemStack itemstack = this.furnaceItemStacks[p_70298_1_].splitStack(p_70298_2_);
            if (this.furnaceItemStacks[p_70298_1_].stackSize == 0)
            {
                this.furnaceItemStacks[p_70298_1_] = null;
            }
            return itemstack;
        }
        return null;
    }

    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        if (this.furnaceItemStacks[p_70304_1_] != null)
        {
            ItemStack itemstack = this.furnaceItemStacks[p_70304_1_];
            this.furnaceItemStacks[p_70304_1_] = null;
            return itemstack;
        }
        return null;
    }

    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.furnaceItemStacks[p_70299_1_] = p_70299_2_;
        if ((p_70299_2_ != null) && (p_70299_2_.stackSize > getInventoryStackLimit()))
        {
            p_70299_2_.stackSize = getInventoryStackLimit();
        }
    }

    public String getInventoryName()
    {
        return hasCustomInventoryName() ? this.furnaceName : "Mytrilium Furnace";
    }

    public boolean hasCustomInventoryName()
    {
        return (this.furnaceName != null) && (this.furnaceName.length() > 0);
    }

    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if ((b0 >= 0) && (b0 < this.furnaceItemStacks.length))
            {
                this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        this.furnaceBurnTime = p_145839_1_.getShort("BurnTime");
        this.furnaceCookTime = p_145839_1_.getShort("CookTime");
        this.currentBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
        if (p_145839_1_.hasKey("CustomName", 8))
        {
            this.furnaceName = p_145839_1_.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setShort("BurnTime", (short) this.furnaceBurnTime);
        p_145841_1_.setShort("CookTime", (short) this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.furnaceItemStacks.length; i++)
        {
            if (this.furnaceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        p_145841_1_.setTag("Items", nbttaglist);
        if (hasCustomInventoryName())
        {
            p_145841_1_.setString("CustomName", this.furnaceName);
        }
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int par1)
    {
        return this.furnaceCookTime * par1 / 25;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int p_145955_1_)
    {
        if (this.currentBurnTime == 0)
        {
            this.currentBurnTime = 50;
        }
        return this.furnaceBurnTime * p_145955_1_ / this.currentBurnTime;
    }

    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    public void updateEntity()
    {
        boolean flag = this.furnaceBurnTime > 0;
        boolean flag1 = false;
        if (this.furnaceBurnTime > 0)
        {
            this.furnaceBurnTime -= 1;
        }
        if (!this.worldObj.isRemote)
        {
            if ((this.furnaceBurnTime != 0) || ((this.furnaceItemStacks[1] != null) && (this.furnaceItemStacks[0] != null)))
            {
                if ((this.furnaceBurnTime == 0) && (canSmelt()))
                {
                    this.currentBurnTime = (this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]));
                    if (this.furnaceBurnTime > 0)
                    {
                        flag1 = true;
                        if (this.furnaceItemStacks[1] != null)
                        {
                            this.furnaceItemStacks[1].stackSize -= 1;
                            if (this.furnaceItemStacks[1].stackSize == 0)
                            {
                                this.furnaceItemStacks[1] = this.furnaceItemStacks[1].getItem().getContainerItem(this.furnaceItemStacks[1]);
                            }
                        }
                    }
                }
                if ((isBurning()) && (canSmelt()))
                {
                    this.furnaceCookTime += 1;
                    if (this.furnaceCookTime == 25)
                    {
                        this.furnaceCookTime = 0;
                        smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.furnaceCookTime = 0;
                }
            }
            if (flag != this.furnaceBurnTime > 0)
            {
                flag1 = true;
                MytriliumFurnace.updateBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (flag1)
        {
            markDirty();
        }
    }

    private boolean canSmelt()
    {
        if (this.furnaceItemStacks[0] == null)
        {
            return false;
        }
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
        if (itemstack == null)
        {
            return false;
        }
        if (this.furnaceItemStacks[2] == null)
        {
            return true;
        }
        if (!this.furnaceItemStacks[2].isItemEqual(itemstack))
        {
            return false;
        }
        int result = this.furnaceItemStacks[2].stackSize + itemstack.stackSize;
        return (result <= getInventoryStackLimit()) && (result <= this.furnaceItemStacks[2].getMaxStackSize());
    }

    public void smeltItem()
    {
        if (canSmelt())
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0]);
            if (this.furnaceItemStacks[2] == null)
            {
                this.furnaceItemStacks[2] = itemstack.copy();
            }
            else if (this.furnaceItemStacks[2].getItem() == itemstack.getItem())
            {
                this.furnaceItemStacks[2].stackSize += itemstack.stackSize;
            }
            this.furnaceItemStacks[0].stackSize -= 1;
            if (this.furnaceItemStacks[0].stackSize <= 0)
            {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
    }

    public void openInventory()
    {
    }

    public void closeInventory()
    {
    }

    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return p_94041_1_ != 2;
    }

    public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 1 ? slotsTop : p_94128_1_ == 0 ? slotsBottom : slotsSides;
    }

    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_)
    {
        return isItemValidForSlot(p_102007_1_, p_102007_2_);
    }

    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_)
    {
        return (p_102008_3_ != 0) || (p_102008_1_ != 1) || (p_102008_2_.getItem() == Items.bucket);
    }
}