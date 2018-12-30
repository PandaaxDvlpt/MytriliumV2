package fr.pandaax.mytrilium.dynamite;


import fr.pandaax.mytrilium.items.Iritium.IritiumItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;


public class EntityDynamite extends EntityProjectile
{
    public boolean stuck = true;
    private int explodefuse;
    private boolean extinguished;

    public EntityDynamite(World world)
    {
        super(world);
        setPickupMode(0);
        this.extinguished = false;
        this.explodefuse = 500;
    }

    public EntityDynamite(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
    }

    public EntityDynamite(World world, EntityLivingBase entityliving, int i)
    {
        this(world);
        this.shootingEntity = entityliving;
        setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
        this.posY -= 0.1D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
        setPosition(this.posX, this.posY, this.posZ);
        this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F));
        this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F));
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.141593F));
        setThrowableHeading(this.motionX, this.motionY, this.motionZ, 0.7F, 4.0F);
        this.explodefuse = i;
    }

    protected void entityInit()
    {
    }

    public void onUpdate()
    {
        super.onUpdate();
        if ((!this.inGround) && (!this.beenInGround))
        {
            this.rotationPitch -= 50.0F;
        }
        else
        {
            this.rotationPitch = 180.0F;
        }
        if ((isInWater()) && (!this.extinguished))
        {
            this.extinguished = true;
            this.worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            for (int k = 0; k < 8; k++)
            {
                float f6 = 0.25F;
                this.worldObj.spawnParticle("explode", this.posX - this.motionX * f6, this.posY - this.motionY * f6, this.posZ - this.motionZ * f6, this.motionX, this.motionY, this.motionZ);
            }
        }
        this.explodefuse -= 1;
        if (this.explodefuse <= 0)
        {
            detonate();
            setDead();
        }
        else if (this.explodefuse > 0)
        {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    public void onEntityHit(Entity entity)
    {
        DamageSource damagesource = null;
        if (this.shootingEntity == null)
        {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
        }
        else
        {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this.shootingEntity);
        }
        if (entity.attackEntityFrom(damagesource, 1.0F))
        {
            applyEntityHitEffects(entity);
            playHitSound();
            setVelocity(0.0D, 0.0D, 0.0D);
            this.ticksInAir = 0;
        }
    }

    public void onGroundHit(MovingObjectPosition mop)
    {
        this.xTile = mop.blockX;
        this.yTile = mop.blockY;
        this.zTile = mop.blockZ;
        this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
        this.motionX = ((float) (mop.hitVec.xCoord - this.posX));
        this.motionY = ((float) (mop.hitVec.yCoord - this.posY));
        this.motionZ = ((float) (mop.hitVec.zCoord - this.posZ));
        float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.posX -= this.motionX / f1 * 0.05D;
        this.posY -= this.motionY / f1 * 0.05D;
        this.posZ -= this.motionZ / f1 * 0.05D;

        this.motionX *= -0.2D;
        this.motionZ *= -0.2D;
        if (mop.sideHit == 1)
        {
            this.inGround = true;
            this.beenInGround = true;
        }
        else
        {
            this.inGround = false;
            this.worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        }
        if (this.inTile != null)
        {
            this.inTile.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
        }
    }

    private void detonate()
    {
        float f = 4.5F;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, f, true);
    }

    public boolean aimRotation()
    {
        return false;
    }

    public int getMaxArrowShake()
    {
        return 0;
    }

    public ItemStack getPickupItem()
    {
        return new ItemStack(IritiumItems.dynamite);
    }

    public void playHitSound()
    {
        this.worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setByte("fuse", (byte) this.explodefuse);
        nbttagcompound.setBoolean("off", this.extinguished);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        this.explodefuse = nbttagcompound.getByte("fuse");
        this.extinguished = nbttagcompound.getBoolean("off");
    }
}
