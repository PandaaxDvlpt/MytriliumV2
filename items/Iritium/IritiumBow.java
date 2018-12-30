package fr.pandaax.mytrilium.items.Iritium;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.pandaax.mytrilium.Mytrilium;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class IritiumBow extends ItemBow
{
    public static final String[] bowPullIconNameArray = new String[]{"1", "2", "3"}; // c'est pour les textures
    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;


    public IritiumBow()
    {

        this.maxStackSize = 1;
        this.setMaxDamage(800);
        this.setCreativeTab(Mytrilium.tabCombat);
        this.setFull3D();
    }


    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int par4)
    {
        int j = this.getMaxItemUseDuration(stack) - par4;

        ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if (flag || player.inventory.hasItem(Items.arrow))
        {
            float f = (float) j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double) f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(world, player, f * 2.0F);

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double) k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
            {
                entityarrow.setFire(100);
            }

            stack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                player.inventory.consumeInventoryItem(Items.arrow);
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(entityarrow);
            }
        }
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 2500;
    }

    /*
     *renvoie l'action qui sp�cifie ce que l'animation � jouer lorsque l'item et utilis�
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.bow;
    }

    /*
     * Appel� � chaque fois que cet objet est �quip� et le bouton droit de la souris est enfonc�
     */
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        ArrowNockEvent event = new ArrowNockEvent(player, stack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return event.result;
        }

        if (player.capabilities.isCreativeMode || player.inventory.hasItem(Items.arrow))
        {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }

        return stack;
    }

    public int getItemEnchantability()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister IconRegister)
    {
        this.itemIcon = IconRegister.registerIcon(this.getIconString());
        this.iconArray = new IIcon[bowPullIconNameArray.length];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = IconRegister.registerIcon(this.getIconString() + "_" + bowPullIconNameArray[i]);
        }
    }


    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if (usingItem != null && usingItem.getItem().equals(this))
        {
            int k = usingItem.getMaxItemUseDuration() - useRemaining;
            if (k >= 18)
                return iconArray[2];
            if (k > 13)
                return iconArray[1];
            if (k > 0)
                return iconArray[0];
        }
        return getIconIndex(stack);
    }

}
