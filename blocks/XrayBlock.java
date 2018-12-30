package fr.pandaax.mytrilium.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.Mytrilium;
import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class XrayBlock
{
    public static Block xrayBlock;

    public static void init()
    {
        xrayBlock = new InitXray(Material.iron).setBlockName("xrayBlock").setHardness(5.0F).setBlockTextureName(Reference.MOD_ID + ":xrayBlock").setCreativeTab(Mytrilium.tabBlockM);
    }

    public static void register()
    {
        GameRegistry.registerBlock(xrayBlock, xrayBlock.getUnlocalizedName().substring(5));


    }
}
