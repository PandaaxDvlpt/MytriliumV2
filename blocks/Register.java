package fr.pandaax.mytrilium.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.block.Block;

public class Register
{

    public static Block cultureMytrilium;

    public static void init()
    {
        cultureMytrilium = new Crops().setBlockName("cultureMytrilium").setBlockTextureName(Reference.MOD_ID + ":cultureMytrilium");
    }

    public static void register()
    {
        GameRegistry.registerBlock(cultureMytrilium, cultureMytrilium.getUnlocalizedName().substring(5));
    }
}
