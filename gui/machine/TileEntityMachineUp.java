package fr.pandaax.mytrilium.gui.machine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineUp extends TileEntity implements IInventory

{
    private ItemStack[] contents = new ItemStack[3]; //0, 1 et 2 sont les inputs et 3 est l'output
    
    

    
    
    
    private int workingTime = 0; //Temps de cuisson actuel
    private int workingTimeNeeded = 200; //Temps de cuisson nécessaire
    
    @Override 
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.contents.length; ++i) //pour les slots
        {
            if (this.contents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.contents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        compound.setTag("Items", nbttaglist);
        compound.setShort("workingTime",(short)this.workingTime); //On les enregistrent en short
        compound.setShort("workingTimeNeeded", (short)this.workingTimeNeeded);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.contents = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) //Encore une fois pour les slots
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.contents.length)
            {
                this.contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        
        this.workingTime = compound.getShort("workingTime"); //On lit nos valeurs
        this.workingTimeNeeded = compound.getShort("workingTimeNeeded");
    }
    
    public int getSizeInventory() { //Tout est dans le nom, retourne la taille de l'inventaire, pour notre bloc c'est quatre
        return this.contents.length;
    }
    
    public ItemStack getStackInSlot(int slotIndex) { //Renvoie L'itemStack se trouvant dans le slot passé en argument
        return this.contents[slotIndex];
    }
    
    public ItemStack decrStackSize(int slotIndex, int amount) {
         if (this.contents[slotIndex] != null)
            {
                ItemStack itemstack;

                if (this.contents[slotIndex].stackSize <= amount)
                {
                    itemstack = this.contents[slotIndex];
                    this.contents[slotIndex] = null;
                    this.markDirty();
                    return itemstack;
                }
                else
                {
                    itemstack = this.contents[slotIndex].splitStack(amount);

                    if (this.contents[slotIndex].stackSize == 0)
                    {
                        this.contents[slotIndex] = null;
                    }

                    this.markDirty();
                    return itemstack;
                }
            }
            else
            {
                return null;
            }
    }

    public ItemStack getStackInSlotOnClosing(int slotIndex) {
        if (this.contents[slotIndex] != null)
        {
            ItemStack itemstack = this.contents[slotIndex];
            this.contents[slotIndex] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int slotIndex, ItemStack stack) {
        this.contents[slotIndex] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    public String getInventoryName() { //J'ai décider qu'on ne pouvait pas mettre de nom custom
        return "tile.machineUp";
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openInventory() {
        
    }

    public void closeInventory() {
        
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot == 2 ? false : true;
    }
    
    public boolean isBurning()
    {
        return this.workingTime > 0;
    }
    
    private boolean canSmelt()
    {
        if (this.contents[0] == null || this.contents[1] == null) //Si les trois premiers slots sont vides
        {
            return false; //On ne peut pas lancer le processus
        }
        else
        {
            
            
            ItemStack itemstack = MachineUpRecipes.smelting().getSmeltingResult(new ItemStack[]{this.contents[0], this.contents[1]}); //Il y a une erreur ici, c'est normal, on y vient après (c'est pour les recettes)
            if (itemstack == null) return false; //rapport avec les recettes
            if (this.contents[2] == null) return true; //vérifications du slot d'output
            if (!this.contents[2].isItemEqual(itemstack)) return false; //ici aussi
            int result = contents[2].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.contents[2].getMaxStackSize(); //Et là aussi décidément
            
        }
    }
    
    public void updateEntity() //Méthode exécutée à chaque tick
    {
        if(this.isBurning() && this.canSmelt()) //Si on "cuit" et que notre recette et toujours bonne, on continue
        {
            ++this.workingTime; //incrémentation
        }
        if(this.canSmelt() && !this.isBurning()) //Si la recette est bonne mais qu'elle n'est toujours pas lancée, on la lance
        {
            this.workingTime = 1; //La méthode isBurning() renverra true maintenant (1>0)
        }
        if(this.canSmelt() && this.workingTime == this.workingTimeNeeded) //Si on est arrivé au bout du temps de cuisson et que la recette est toujours bonne
        {
            this.smeltItem(); //on "cuit" les items
            this.workingTime = 0; //et on réinitialise le temps de cuisson
        }
        if(!this.canSmelt()) //Si la recette la recette n'est plus bonne
        {
               this.workingTime= 0; //le temps de cuisson est de 0
        }
    }
    
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = MachineUpRecipes.smelting().getSmeltingResult(new ItemStack[]{this.contents[0], this.contents[1]}); //On récupère l'output de la recette
             if (this.contents[2] == null) //Si il y a rien dans le slot d'output
             {
                  this.contents[2] = itemstack.copy(); //On met directement l'ItemStack
             }
             else if (this.contents[2].getItem() == itemstack.getItem()) //Et si l'item que l'on veut est le même que celui qu'il y a déjà
             {
                  this.contents[2].stackSize += itemstack.stackSize; // Alors ont incrémente l'ItemStack
             }

             --this.contents[0].stackSize; //On décrémente les slots d'input
             --this.contents[1].stackSize;

             if (this.contents[0].stackSize <= 0) //Si les slots sont vides, on remet à null le slot
             {
                 this.contents[0] = null;
             }
             if (this.contents[1].stackSize <= 0)
             {
                 this.contents[1] = null;
             }
        }
    }
    
    
    
      @SideOnly(Side.CLIENT)
      public int getCookProgress()
      {
        return this.workingTime * 100 / this.workingTimeNeeded;
      }
    
}
