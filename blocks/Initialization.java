package fr.pandaax.mytrilium.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.Mytrilium;
import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class Initialization
{
    public static Item seed;
    public static Item nuggets;

    public static void init()
    {
        seed = new ItemsCulture(Register.cultureMytrilium, Blocks.farmland).setUnlocalizedName("seed").setCreativeTab(Mytrilium.tabItems).setTextureName(Reference.MOD_ID + ":seed");
        nuggets = new Item().setUnlocalizedName("nuggets").setCreativeTab(Mytrilium.tabItems).setTextureName(Reference.MOD_ID + ":nuggets");


    }

    public static void register()
    {
        GameRegistry.registerItem(seed, "seed");
        GameRegistry.registerItem(nuggets, "nuggets");
    }
}
