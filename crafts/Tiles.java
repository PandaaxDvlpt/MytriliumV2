package fr.pandaax.mytrilium.crafts;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.backpack.RegistryBackpack;
import fr.pandaax.mytrilium.blocks.FurnaceReg;
import fr.pandaax.mytrilium.items.Mytrilium.MytriliumItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Tiles
{
    public static void register()
    {
        // - Mytrilium Furnace - //
        GameRegistry.addRecipe(new ItemStack(FurnaceReg.mytriliumFurnace, 1), new Object[]{"###", "# #", "###", '#', MytriliumItems.mytriliumingot});

        // - Backpack - //
        GameRegistry.addRecipe(new ItemStack(RegistryBackpack.backpack, 1), new Object[]{"XXX", "YZY", "YYY", Character.valueOf('X'), new ItemStack(MytriliumItems.mytriliumingot), Character.valueOf('Z'), new ItemStack(Blocks.chest), Character.valueOf('Y'), new ItemStack(Items.leather)});
    }
}
