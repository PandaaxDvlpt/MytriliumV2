package fr.pandaax.mytrilium.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.Mytrilium;
import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class RaimbowLamp
{
    public static Block rlamp;

    public static void init()
    {
        rlamp = new rlampInit(Material.iron).setBlockName("rlamp").setHardness(5.0F).setBlockTextureName(Reference.MOD_ID + ":rlamp").setCreativeTab(Mytrilium.tabBlockM).setTickRandomly(true);
    }

    public static void register()
    {
        // NUFooq6
        GameRegistry.registerBlock(rlamp, rlamp.getUnlocalizedName().substring(5));
    }
}
