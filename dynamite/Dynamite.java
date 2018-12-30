package fr.pandaax.mytrilium.dynamite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.pandaax.mytrilium.Mytrilium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Dynamite extends Item
{
    public Dynamite(String id)
    {
        this.maxStackSize = 64;
        this.setTextureName("mytrilium:dynamite");
        this.setCreativeTab(Mytrilium.tabCombat);
        this.setUnlocalizedName("dynamite");
    }

    public int getItemEnchantability()
    {
        return 0;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        if (entityplayer.inventory.consumeInventoryItem(this))
        {
            world.playSoundAtEntity(entityplayer, "game.tnt.primed", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isRemote)
            {
                world.spawnEntityInWorld(new EntityDynamite(world, entityplayer, 40 + itemRand.nextInt(10)));
            }
        }
        return itemstack;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

}
