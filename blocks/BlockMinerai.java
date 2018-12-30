package fr.pandaax.mytrilium.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.Mytrilium;
import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMinerai
{
    public static Block mytriliumBlock;
    public static Block iritiumBlock;
    public static Block pesiumBlock;
    public static Block trilliumBlock;

    public static void init()
    {
        mytriliumBlock = new InitB(Material.iron).setHardness(5.0F).setBlockTextureName(Reference.MOD_ID + ":mytriliumBlock").setCreativeTab(Mytrilium.tabBlockM).setBlockName("mytriliumBlock");
        GameRegistry.registerBlock(mytriliumBlock, mytriliumBlock.getUnlocalizedName().substring(5));

    }
}
