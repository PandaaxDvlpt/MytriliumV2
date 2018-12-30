package fr.pandaax.mytrilium.items.sticks;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.Mytrilium;
import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.item.Item;

public class RegisterStick
{
    public static Item healStick, jumpStick, speedStick;

    public static void init()
    {
        healStick = new HealStick().setUnlocalizedName("healStick").setCreativeTab(Mytrilium.tabCombat).setTextureName(Reference.MOD_ID + ":healStick").setFull3D();
        speedStick = new SpeedStick().setUnlocalizedName("speedStick").setCreativeTab(Mytrilium.tabCombat).setTextureName(Reference.MOD_ID + ":speedStick").setFull3D();
    }

    public static void register()
    {
        GameRegistry.registerItem(healStick, "healStick");
        GameRegistry.registerItem(speedStick, "speedStick");
    }

}
