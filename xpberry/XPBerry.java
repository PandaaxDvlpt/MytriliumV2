package fr.pandaax.mytrilium.xpberry;

import fr.pandaax.mytrilium.Mytrilium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class XPBerry extends Item
{
	public XPBerry() 
	{
		setMaxStackSize(64);
		setUnlocalizedName("experienceberry");
		setCreativeTab(Mytrilium.tabItems);
		
	}
	
	public ItemStack onItemRightClick(ItemStack stack , World world , EntityPlayer player)
	{
		stack.stackSize -= 1;
		player.addExperience(20);
		return stack;
	}
}
