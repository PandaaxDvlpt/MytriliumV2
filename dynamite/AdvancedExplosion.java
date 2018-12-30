package fr.pandaax.mytrilium.dynamite;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AdvancedExplosion
        extends Explosion
{

    protected static final Random rand = new Random();
    public World worldObj;
    protected boolean blocksCalculated;

    public AdvancedExplosion(World world, Entity entity, double d, double d1, double d2, float f)
    {
        super(world, entity, d, d1, d2, f);
        this.worldObj = world;
    }

    public void setAffectedBlockPositions(List<ChunkPosition> list)
    {
        this.affectedBlockPositions = list;
        this.blocksCalculated = true;
    }

    public void doEntityExplosion()
    {
        doEntityExplosion(DamageSource.setExplosionSource(this));
    }

    public void doEntityExplosion(DamageSource damagesource)
    {
        float size = this.explosionSize * 2.0F;
        int i0 = MathHelper.floor_double(this.explosionX - size - 1.0D);
        int i1 = MathHelper.floor_double(this.explosionX + size + 1.0D);
        int j0 = MathHelper.floor_double(this.explosionY - size - 1.0D);
        int j1 = MathHelper.floor_double(this.explosionY + size + 1.0D);
        int k0 = MathHelper.floor_double(this.explosionZ - size - 1.0D);
        int k1 = MathHelper.floor_double(this.explosionZ + size + 1.0D);

        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBox(i0, j0, k0, i1, j1, k1));
        Vec3 vec31 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity) list.get(i);
            double dr = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / size;
            if (dr <= 1.0D)
            {
                double dx = entity.posX - this.explosionX;
                double dy = entity.posY - this.explosionY;
                double dz = entity.posZ - this.explosionZ;
                double d = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
                if (d != 0.0D)
                {
                    dx /= d;
                    dy /= d;
                    dz /= d;
                    double dens = this.worldObj.getBlockDensity(vec31, entity.boundingBox);
                    double var36 = (1.0D - dr) * dens;
                    int damage = (int) ((var36 * var36 + var36) / 2.0D * 8.0D * size + 1.0D);
                    entity.attackEntityFrom(damagesource, damage);
                    entity.motionX += dx * var36;
                    entity.motionY += dy * var36;
                    entity.motionZ += dz * var36;
                }
            }
        }
    }

    public void doBlockExplosion()
    {
        if (!this.blocksCalculated)
        {
            calculateBlockExplosion();
        }
        for (int i = this.affectedBlockPositions.size() - 1; i >= 0; i--)
        {
            ChunkPosition chunkposition = (ChunkPosition) this.affectedBlockPositions.get(i);
            int x = chunkposition.chunkPosX;
            int y = chunkposition.chunkPosY;
            int z = chunkposition.chunkPosZ;
            Block block = this.worldObj.getBlock(x, y, z);
            if (block != null)
            {
                if (block.canDropFromExplosion(this))
                {
                    block.dropBlockAsItemWithChance(this.worldObj, x, y, z, this.worldObj.getBlockMetadata(x, y, z), 1.0F / this.explosionSize, 0);
                }
                this.worldObj.setBlock(x, y, z, Blocks.air, 0, 3);
                block.onBlockDestroyedByExplosion(this.worldObj, x, y, z, this);
            }
        }
    }

    public void doParticleExplosion(boolean smallparticles, boolean bigparticles)
    {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        if (bigparticles)
        {
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 0.0D, 0.0D, 0.0D);
        }
        if (!smallparticles)
        {
            return;
        }
        if (!this.blocksCalculated)
        {
            calculateBlockExplosion();
        }
        for (int i = this.affectedBlockPositions.size() - 1; i >= 0; i--)
        {
            ChunkPosition chunkposition = (ChunkPosition) this.affectedBlockPositions.get(i);
            int j = chunkposition.chunkPosX;
            int k = chunkposition.chunkPosY;
            int l = chunkposition.chunkPosZ;

            double px = j + this.worldObj.rand.nextFloat();
            double py = k + this.worldObj.rand.nextFloat();
            double pz = l + this.worldObj.rand.nextFloat();
            double dx = px - this.explosionX;
            double dy = py - this.explosionY;
            double dz = pz - this.explosionZ;
            double distance = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
            dx /= distance;
            dy /= distance;
            dz /= distance;
            double d7 = 0.5D / (distance / this.explosionSize + 0.1D);
            d7 *= (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
            dx *= d7;
            dy *= d7;
            dz *= d7;
            this.worldObj.spawnParticle("explode", (px + this.explosionX * 1.0D) / 2.0D, (py + this.explosionY * 1.0D) / 2.0D, (pz + this.explosionZ * 1.0D) / 2.0D, dx, dy, dz);
            this.worldObj.spawnParticle("smoke", px, py, pz, dx, dy, dz);
        }
    }

    protected void calculateBlockExplosion()
    {
        byte maxsize = 16;
        Set<ChunkPosition> set = new HashSet();
        for (int i = 0; i < maxsize; i++)
        {
            for (int j = 0; j < maxsize; j++)
            {
                for (int k = 0; k < maxsize; k++)
                {
                    if ((i == 0) || (i == maxsize - 1) || (j == 0) || (j == maxsize - 1) || (k == 0) || (k == maxsize - 1))
                    {
                        double rx = i / (maxsize - 1.0F) * 2.0F - 1.0F;
                        double ry = j / (maxsize - 1.0F) * 2.0F - 1.0F;
                        double rz = k / (maxsize - 1.0F) * 2.0F - 1.0F;
                        double rd = Math.sqrt(rx * rx + ry * ry + rz * rz);
                        rx /= rd;
                        ry /= rd;
                        rz /= rd;
                        float strength = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        double dx = this.explosionX;
                        double dy = this.explosionY;
                        double dz = this.explosionZ;
                        for (float f = 0.3F; strength > 0.0F; strength -= f * 0.75F)
                        {
                            int x = MathHelper.floor_double(dx);
                            int y = MathHelper.floor_double(dy);
                            int z = MathHelper.floor_double(dz);
                            Block block = this.worldObj.getBlock(x, y, z);
                            if (block != null)
                            {
                                strength -= (block.getExplosionResistance(this.exploder, this.worldObj, x, y, z, this.explosionX, this.explosionY, this.explosionZ) + 0.3F) * f;
                            }
                            if (strength > 0.0F)
                            {
                                set.add(new ChunkPosition(x, y, z));
                            }
                            dx += rx * f;
                            dy += ry * f;
                            dz += rz * f;
                        }
                    }
                }
            }
        }
        this.affectedBlockPositions.addAll(set);
        this.blocksCalculated = true;
    }

}
