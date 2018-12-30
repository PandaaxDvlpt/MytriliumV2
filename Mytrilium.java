// Mytrilium 2017 - Toute reproduction ou de disbrutions des codes sera puni ! Et non pas par le jeux mais par la lois 
// Ce Mod est copyright num�ro du d�pot : 59686
package fr.pandaax.mytrilium;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.pandaax.mytrilium.backpack.RegistryBackpack;
import fr.pandaax.mytrilium.blocks.*;
import fr.pandaax.mytrilium.crafts.Iritium;
import fr.pandaax.mytrilium.crafts.MytriliumCrafts;
import fr.pandaax.mytrilium.crafts.Tiles;
import fr.pandaax.mytrilium.dynamite.EntityDynamite;
import fr.pandaax.mytrilium.gui.GuiHandler;
import fr.pandaax.mytrilium.items.Core;
import fr.pandaax.mytrilium.items.Iritium.IritiumItems;
import fr.pandaax.mytrilium.items.Mytrilium.MytriliumItems;
import fr.pandaax.mytrilium.items.sticks.RegisterStick;
import fr.pandaax.mytrilium.proxy.CommonProxy;
import fr.pandaax.mytrilium.proxy.Reference;
import fr.pandaax.mytrilium.slime.RegistrySlime;
import fr.pandaax.mytrilium.tile.TileEntityMytriliumFurnace;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)

public class Mytrilium
{
    @Instance(Reference.MOD_ID)
    public static Mytrilium instance;
    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static CommonProxy proxy;
    public static CreativeTabs tabBlockM = new CreativeTabs("tabBlock")
    {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return RegistryBackpack.backpack;
        }
    };
    public static CreativeTabs tabItems = new CreativeTabs("tabItems")
    {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return Core.mytriliumCore;
        }
    };
    public static CreativeTabs tabCombat = new CreativeTabs("tabCombat")
    {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return MytriliumItems.mytriliumingot;
        }
    };

    // Creatives Tabs

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Register.init();
        Register.register();
        XrayBlock.init();
        XrayBlock.register();
        RaimbowLamp.init();
        RaimbowLamp.register();
        RegistrySlime.init();
        BlockRegistry.init();
        BlockMinerai.init();
        IritiumItems.init();
        IritiumItems.register();
        proxy.registerRenders();
        Initialization.init();
        Initialization.register();
        MytriliumItems.init();
        RegisterStick.init();
        RegisterStick.register();
        RegistryBackpack.init();
        FurnaceReg.init();
        Tiles.register();
        Iritium.register();
        MytriliumCrafts.register();
        Core.init();


    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.registerRenders();
        EntityRegistry.registerModEntity(EntityDynamite.class, "Dynamite", 6, this.instance, 80, 3, true);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        GameRegistry.registerTileEntity(TileEntityMytriliumFurnace.class, "mytrilium:tilemytriliumfurnace");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
