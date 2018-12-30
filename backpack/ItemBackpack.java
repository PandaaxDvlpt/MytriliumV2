package fr.pandaax.mytrilium.backpack;

import fr.pandaax.mytrilium.Mytrilium;
import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemBackpack extends Item
{
    public static final String NAME = "backpack";

    public ItemBackpack()
    {
        this.setUnlocalizedName(Reference.MOD_ID + "_" + NAME);
        this.setTextureName(Reference.MOD_ID + ":" + NAME);
        this.setCreativeTab(Mytrilium.tabItems);
        this.setTextureName("mytrilium:backpack");
        this.maxStackSize = 1;

    }


    /**
     * Used to open the gui
     */
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            player.openGui(Mytrilium.instance, 0, world, 0, 0, 0);
            NBTTagCompound nbt = player.getHeldItem().getTagCompound();
            nbt.setBoolean("Open", true);
        }
        return stack;
    }

    public void onUpdate(ItemStack stack, World world, Entity e, int in, boolean b)
    {
        if ((stack.hasTagCompound()) && (stack.getTagCompound().hasKey("Open")) &&
                (!b))
        {
            stack.getTagCompound().setBoolean("Open", false);
        }
        super.onUpdate(stack, world, e, in, b);
    }
}

