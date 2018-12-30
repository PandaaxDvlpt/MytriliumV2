package fr.pandaax.mytrilium.items.Iritium;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.pandaax.mytrilium.Mytrilium;
import fr.pandaax.mytrilium.dynamite.Dynamite;
import fr.pandaax.mytrilium.items.Iritium.Arguments.IritiumAxe;
import fr.pandaax.mytrilium.items.Iritium.Arguments.IritiumPick;
import fr.pandaax.mytrilium.items.Iritium.Arguments.IritiumShovel;
import fr.pandaax.mytrilium.items.Iritium.Arguments.IritiumSword;
import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class IritiumItems

{

    // TOOLS MATERIALS ARMOR

    public static ItemArmor.ArmorMaterial armorIritium = EnumHelper.addArmorMaterial("armorIritium", 55, new int[]{4, 8, 6, 4}, 30);


    // TOOL MATERIAL OUTILS

    public static Item.ToolMaterial Iritium = EnumHelper.addToolMaterial("Iritium", 5, 2100, 20.0F, 6.0F, 30);


    public static Item iritiumSword;
    public static Item iritiumHammer;
    public static Item iritiumBow;
    public static Item iritiumHelmet;
    public static Item iritiumChestplate;
    public static Item iritiumLeggins;
    public static Item iritiumBoots;
    public static Item iritiumPick;
    public static Item iritiumIngot;
    public static Item iritiumAxe;
    public static Item iritiumShovel;
    public static Item iritiumMultitools;
    public static Item iritiumStick;
    public static Item dynamite;


    public static void init()
    {
        //Outils

        iritiumBow = new IritiumBow().setUnlocalizedName("iritiumBow").setTextureName(Reference.MOD_ID + ":iritiumBow");

        iritiumPick = new IritiumPick(Iritium).setUnlocalizedName("iritiumPickaxe").setTextureName(Reference.MOD_ID + ":iritiumPick").setCreativeTab(Mytrilium.tabCombat);

        iritiumShovel = new IritiumShovel(Iritium).setUnlocalizedName("iritiumShovel").setTextureName(Reference.MOD_ID + ":iritiumShovel").setCreativeTab(Mytrilium.tabCombat);

        iritiumAxe = new IritiumAxe(Iritium).setUnlocalizedName("iritiumAxe").setCreativeTab(Mytrilium.tabCombat).setTextureName(Reference.MOD_ID + ":iritiumAxe");

        iritiumSword = new IritiumSword(Iritium).setUnlocalizedName("iritiumSword").setCreativeTab(Mytrilium.tabCombat).setTextureName(Reference.MOD_ID + ":iritiumSword");


        //Items

        iritiumIngot = new Item().setUnlocalizedName("iritiumIngot").setTextureName(Reference.MOD_ID + ":iritiumIngot").setCreativeTab(Mytrilium.tabItems);
        iritiumStick = new Item().setUnlocalizedName("iritiumStick").setTextureName(Reference.MOD_ID + ":iritiumStick").setCreativeTab(Mytrilium.tabItems).setFull3D();

        //Armures

        iritiumHelmet = new IritiumArmor(armorIritium, 0).setUnlocalizedName("iritiumHelmet").setTextureName(Reference.MOD_ID + ":iritiumHelmet").setCreativeTab(Mytrilium.tabCombat);
        iritiumChestplate = new IritiumArmor(armorIritium, 1).setUnlocalizedName("iritiumChestplate").setTextureName(Reference.MOD_ID + ":iritiumChestplate").setCreativeTab(Mytrilium.tabCombat);
        iritiumLeggins = new IritiumArmor(armorIritium, 2).setUnlocalizedName("iritiumLeggins").setTextureName(Reference.MOD_ID + ":iritiumLeggins").setCreativeTab(Mytrilium.tabCombat);
        iritiumBoots = new IritiumArmor(armorIritium, 3).setUnlocalizedName("iritiumBoots").setTextureName(Reference.MOD_ID + ":iritiumBoots").setCreativeTab(Mytrilium.tabCombat);
        // Dynamite
        dynamite = new Dynamite("dynamite");

        // Hammer

        iritiumHammer = new IritiumHammer(Iritium).setUnlocalizedName("iritiumHammer").setCreativeTab(Mytrilium.tabCombat).setTextureName(Reference.MOD_ID + ":iritiumHammer");


    }

    public static void register()
    {
        //Outils
        GameRegistry.registerItem(iritiumBow, "iritiumBow");

        GameRegistry.registerItem(iritiumPick, "iritiumPick");

        GameRegistry.registerItem(iritiumShovel, "iritiumShovel");

        GameRegistry.registerItem(iritiumAxe, "iritiumAxe");
        GameRegistry.registerItem(dynamite, "dynamite");
        GameRegistry.registerItem(iritiumSword, "iritiumSword");

        GameRegistry.registerItem(iritiumHammer, "iritiumHammer");

        //ITEMS

        GameRegistry.registerItem(iritiumIngot, "iritiumIngot");
        GameRegistry.registerItem(iritiumStick, "iritiumStick");

        //Armures

        GameRegistry.registerItem(iritiumHelmet, "iritiumHelmet");
        GameRegistry.registerItem(iritiumChestplate, "iritiumChestplate");
        GameRegistry.registerItem(iritiumLeggins, "iritiumLeggins");
        GameRegistry.registerItem(iritiumBoots, "iritiumBoots");


        //CRAFTS
    }
}
