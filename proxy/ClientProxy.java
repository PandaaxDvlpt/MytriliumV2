package fr.pandaax.mytrilium.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import fr.pandaax.mytrilium.client.EventHandler;
import fr.pandaax.mytrilium.dynamite.EntityDynamite;
import fr.pandaax.mytrilium.dynamite.RenderDynamite;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, new RenderDynamite());
    }
}
