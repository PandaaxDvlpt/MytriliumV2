package fr.pandaax.mytrilium.items.Iritium;


import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class IritiumArmor extends ItemArmor
{
    public IritiumArmor(ArmorMaterial material, int metaData)
    {
        super(material, 0, metaData);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        if (stack.getItem() == IritiumItems.iritiumLeggins)
        {
            return Reference.MOD_ID + ":textures/models/armor/iritium2.png";
        }
        else if (stack.getItem() == IritiumItems.iritiumHelmet || stack.getItem() == IritiumItems.iritiumChestplate || stack.getItem() == IritiumItems.iritiumBoots)
        {
            return Reference.MOD_ID + ":textures/models/armor/iritium1.png";
        }
        return null;
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {


        if (this == IritiumItems.iritiumChestplate)
        {
            player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 660, 0));


        }
        if (this == IritiumItems.iritiumLeggins)
        {
            player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 660, 0));

        }

        if (this == IritiumItems.iritiumHelmet)
        {
            player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 660, 0));
        }
    }
}
