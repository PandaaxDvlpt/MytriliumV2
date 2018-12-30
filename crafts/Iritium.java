package fr.pandaax.mytrilium.crafts;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.items.Iritium.IritiumItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Iritium
{
    public static void register()
    {

        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumStick, 1), new Object[]{"   ", " # ", " # ", '#', IritiumItems.iritiumIngot});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumSword, 1), new Object[]{" # ", " # ", " A ", '#', IritiumItems.iritiumIngot, 'A', IritiumItems.iritiumStick});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumPick, 1), new Object[]{"###", " A ", " A ", '#', IritiumItems.iritiumIngot, 'A', IritiumItems.iritiumStick});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumShovel, 1), new Object[]{" # ", " A ", " A ", '#', IritiumItems.iritiumIngot, 'A', IritiumItems.iritiumStick});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumAxe, 1), new Object[]{"## ", " A#", " A ", '#', IritiumItems.iritiumIngot, 'A', IritiumItems.iritiumStick});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.dynamite, 1), new Object[]{" # ", " A ", " A ", '#', Items.string, 'A', Blocks.tnt});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.dynamite, 1), new Object[]{"  #", "  A", "  A", '#', Items.string, 'A', Blocks.tnt});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.dynamite, 1), new Object[]{"#  ", "A  ", "A  ", '#', Items.string, 'A', Blocks.tnt});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumShovel, 1), new Object[]{" # ", " A ", " A ", '#', IritiumItems.iritiumIngot, 'A', IritiumItems.iritiumStick});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumBow, 1), new Object[]{"A# ", "A #", "A# ", '#', IritiumItems.iritiumStick, 'A', Items.string});

        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumHelmet, 1), new Object[]{"###", "# #", "   ", '#', IritiumItems.iritiumIngot});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumBoots, 1), new Object[]{"# #", "# #", "   ", '#', IritiumItems.iritiumIngot});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumLeggins, 1), new Object[]{"###", "# #", "# #", '#', IritiumItems.iritiumIngot});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumChestplate, 1), new Object[]{"# #", "###", "###", '#', IritiumItems.iritiumIngot});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumHelmet, 1), new Object[]{"   ", "###", "# #", '#', IritiumItems.iritiumIngot});
        GameRegistry.addRecipe(new ItemStack(IritiumItems.iritiumBoots, 1), new Object[]{"   ", "# #", "# #", '#', IritiumItems.iritiumIngot});


    }
}
