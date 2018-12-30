package fr.pandaax.mytrilium.slime;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.Mytrilium;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class RegistrySlime
{
    public static Block jumpBlock;

    public static void init()
    {
        jumpBlock = new PadBlock(Material.rock, 2).setBlockName("jump_block").setCreativeTab(Mytrilium.tabBlockM).setHardness(5.0F);
        GameRegistry.registerBlock(jumpBlock, jumpBlock.getUnlocalizedName().substring(5));

    }
}
