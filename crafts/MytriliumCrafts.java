package fr.pandaax.mytrilium.crafts;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.blocks.Initialization;
import fr.pandaax.mytrilium.items.Mytrilium.MytriliumItems;
import net.minecraft.item.ItemStack;

public class MytriliumCrafts
{
    public static void register()
    {
        // Craft de l'ingot //
        GameRegistry.addRecipe(new ItemStack(MytriliumItems.mytriliumingot, 1), new Object[]{"###", "###", "###", '#', Initialization.nuggets});

    }
}
