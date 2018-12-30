package fr.pandaax.mytrilium.dynamite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public abstract class WeaponModResources
{
    public static abstract class Textures
    {
        public static final ResourceLocation DYNAMITE = new ResourceLocation("mytrilium", "textures/entity/dynamite.png");

    }
}
