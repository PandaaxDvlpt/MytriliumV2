package fr.pandaax.mytrilium.items;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.Mytrilium;
import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.item.Item;

public class Core
{
    public static Item mytriliumCore;
    public static Item iritiumCore;

    public static void init()
    {
        mytriliumCore = new Item().setCreativeTab(Mytrilium.tabItems).setUnlocalizedName("mytriliumCore").setTextureName(Reference.MOD_ID + ":mytriliumCore");
        GameRegistry.registerItem(mytriliumCore, "mytriliumCore");

        iritiumCore = new Item().setCreativeTab(Mytrilium.tabItems).setUnlocalizedName("iritiumCore").setTextureName(Reference.MOD_ID + ":iritiumCore");
        GameRegistry.registerItem(iritiumCore, "iritiumCore");
    }
}
