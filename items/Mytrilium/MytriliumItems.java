package fr.pandaax.mytrilium.items.Mytrilium;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.Mytrilium;
import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.item.Item;

public class MytriliumItems
{
    public static Item mytriliumingot;

    public static void init()
    {
        mytriliumingot = new Item().setCreativeTab(Mytrilium.tabItems).setUnlocalizedName("mytriliumingot").setTextureName(Reference.MOD_ID + ":mytriliumingot");
        GameRegistry.registerItem(mytriliumingot, "mytriliumingot");
    }

}
