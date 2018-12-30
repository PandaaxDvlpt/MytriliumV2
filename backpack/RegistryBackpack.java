package fr.pandaax.mytrilium.backpack;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class RegistryBackpack
{
    public static Item backpack;

    public static void init()
    {
        backpack = new ItemBackpack();

        GameRegistry.registerItem(backpack, "backpack");
    }
}
