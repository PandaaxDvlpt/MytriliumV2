package fr.pandaax.mytrilium.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import fr.pandaax.mytrilium.backpack.ContainerBackpack;
import fr.pandaax.mytrilium.backpack.InventoryBackpack;
import fr.pandaax.mytrilium.backpack.ItemBackpack;
import fr.pandaax.mytrilium.container.ContainerMt;
import fr.pandaax.mytrilium.tile.TileEntityMytriliumFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler

{

    @Override

    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        switch (ID)
        {

            case 0:


                if (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemBackpack))
                    return null;
                return new ContainerBackpack(player.inventory, new InventoryBackpack(player.getHeldItem()));
            case 5:

                TileEntityMytriliumFurnace tileEntityMytriliumFurnace = (TileEntityMytriliumFurnace) world.getTileEntity(x, y, z);
                return new ContainerMt(player.inventory, tileEntityMytriliumFurnace);


        }

        return null;

    }


    @Override

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {

        switch (ID)
        {

            case 0:


                if (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemBackpack))
                    return null;
                return new GuiBackpack(player.inventory, new InventoryBackpack(player.getHeldItem()));
            case 5:

                TileEntityMytriliumFurnace tileEntityMytriliumFurnace = (TileEntityMytriliumFurnace) world.getTileEntity(x, y, z);
                return new GuiMytriliumFurnace(player.inventory, tileEntityMytriliumFurnace);


        }

        return null;

    }

}
