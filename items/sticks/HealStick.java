package fr.pandaax.mytrilium.items.sticks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class HealStick extends Item
{
    public HealStick()
    {
        setMaxStackSize(1);
        setMaxDamage(10);
    }

    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        if (!item.hasTagCompound())
        {
            item.setTagCompound(new NBTTagCompound());
            item.stackTagCompound.setInteger("timer", 0);
        }

        if (item.stackTagCompound.getInteger("timer") == 0)
        {
            item.damageItem(1, player);
            player.addPotionEffect(new PotionEffect(Potion.heal.id, 1, 0));
            item.stackTagCompound.setInteger("timer", 1);
        }

        else if (world.isRemote)
        {
            player.addChatComponentMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "H�h�, attend un peu", new Object[0]));
        }
        return item;
    }

    public void onUpdate(ItemStack item, World world, Entity player, int slotIndex, boolean inHand)
    {
        if (item.hasTagCompound())
        {
            if (item.stackTagCompound.getInteger("timer") > 0)
            {
                item.stackTagCompound.setInteger("timer", item.stackTagCompound.getInteger("timer") + 1);
            }

            if (item.stackTagCompound.getInteger("timer") >= 0)
            {
                item.stackTagCompound.setInteger("timer", 0);
            }
        }
        super.onUpdate(item, world, player, slotIndex, inHand);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item)
    {
        return true;
    }
}

