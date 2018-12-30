package fr.pandaax.mytrilium.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.Mytrilium;
import fr.pandaax.mytrilium.tile.MytriliumFurnace;
import net.minecraft.block.Block;

public class FurnaceReg
{

    public static Block mytriliumFurnace;

    public static void init()
    {
        mytriliumFurnace = new MytriliumFurnace(true, "mytrilium_furnace_off").setBlockName("mytriliumFurnace").setCreativeTab(Mytrilium.tabBlockM).setHardness(5.0F);
        GameRegistry.registerBlock(mytriliumFurnace, mytriliumFurnace.getUnlocalizedName().substring(5));
    }
}
