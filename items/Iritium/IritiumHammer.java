package fr.pandaax.mytrilium.items.Iritium;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class IritiumHammer extends ItemPickaxe
{
    public IritiumHammer(ToolMaterial material)
    {
        super(material);
    }

    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase living)
    {
        if (living instanceof EntityPlayer && !world.isRemote)
        {
            switch (determineOrientation(world, x, y, z, living))
            {
                case 0:
                    for (int x1 = -1; x1 < 2; x1++)
                    {
                        for (int z1 = -1; z1 < 2; z1++)
                        {
                            if (world.getBlock(x + x1, y, z + z1).getBlockHardness(world, x1, y, z1) >= 0.0F)
                            {
                                world.getBlock(x + x1, y, z + z1).harvestBlock(world, (EntityPlayer) living, x + x1, y, z + z1, world.getBlockMetadata(x + x1, y, z + z1));
                                world.setBlockToAir(x + x1, y, z + z1);
                            }
                        }
                    }
                    break;
                case 1:
                    for (int y1 = -1; y1 < 2; y1++)
                    {
                        for (int z1 = -1; z1 < 2; z1++)
                        {
                            if (world.getBlock(x, y + y1, z + z1).getBlockHardness(world, x, y1, z1) >= 0.0F)
                            {
                                world.getBlock(x, y + y1, z + z1).harvestBlock(world, (EntityPlayer) living, x, y + y1, z + z1, world.getBlockMetadata(x, y + y1, z + z1));
                                world.setBlockToAir(x, y + y1, z + z1);
                            }
                        }
                    }
                    break;
                case 2:
                    for (int x1 = -1; x1 < 2; x1++)
                    {
                        for (int y1 = -1; y1 < 2; y1++)
                        {
                            if (world.getBlock(x + x1, y + y1, z).getBlockHardness(world, x1, y1, z) >= 0.0F)
                            {
                                world.getBlock(x + x1, y + y1, z).harvestBlock(world, (EntityPlayer) living, x + x1, y + y1, z, world.getBlockMetadata(x + x1, y + y1, z));
                                world.setBlockToAir(x + x1, y + y1, z);
                            }
                        }
                    }
                    break;
            }
        }
        return super.onBlockDestroyed(stack, world, block, x, y, z, living);
    }

    public int determineOrientation(World world, int x, int y, int z, EntityLivingBase living)
    {
        if (MathHelper.abs((float) living.posX - x) < 2.0F && MathHelper.abs((float) living.posZ - z) < 2.0F)
        {
            double d0 = living.posY + 1.82D - (double) living.yOffset;

            if (d0 - y > 2.0D || y - d0 > 0.0D)
            {
                return 0;
            }
        }
        float rotation = MathHelper.abs(living.rotationYaw);
        return (rotation > 45F && rotation < 135F) || (rotation > 225F && rotation < 315F) ? 1 : 2;
    }
}

