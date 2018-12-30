package fr.pandaax.mytrilium.dynamite;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class DispenceDynamite extends DispenseWeaponProjectile
{
    protected IProjectile getProjectileEntity(World world, IPosition pos)
    {
        return new EntityDynamite(world, pos.getX(), pos.getY(), pos.getZ());
    }

    protected void playDispenseSound(IBlockSource blocksource)
    {
        blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(), "random.fuse", 1.0F, 1.2F);
    }
}

