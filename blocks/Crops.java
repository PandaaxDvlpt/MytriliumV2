package fr.pandaax.mytrilium.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;

public class Crops extends BlockCrops
{
    protected Item func_149866_i()
    {
        return Initialization.seed;
    }

    protected Item func_149865_P()
    {
        return Initialization.nuggets;
    }
}
