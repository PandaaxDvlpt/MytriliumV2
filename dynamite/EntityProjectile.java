package fr.pandaax.mytrilium.dynamite;

import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class EntityProjectile extends EntityArrow
        implements IThrowableEntity
{
    public static final int NO_PICKUP = 0;
    public static final int PICKUP_ALL = 1;
    public static final int PICKUP_CREATIVE = 2;
    public static final int PICKUP_OWNER = 3;
    public int pickupMode;
    public boolean beenInGround;
    public float extraDamage;
    public int knockBack;
    protected int xTile;
    protected int yTile;
    protected int zTile;
    protected Block inTile;
    protected int inData;
    protected boolean inGround;
    protected int ticksInGround;
    protected int ticksInAir;

    public EntityProjectile(World world)
    {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = null;
        this.inData = 0;
        this.inGround = false;
        this.arrowShake = 0;
        this.ticksInAir = 0;
        this.yOffset = 0.0F;
        this.pickupMode = 0;

        this.extraDamage = 0.0F;
        this.knockBack = 0;

        setSize(0.5F, 0.5F);
    }

    protected void entityInit()
    {
        super.entityInit();
    }

    public Entity getThrower()
    {
        return this.shootingEntity;
    }

    public void setThrower(Entity entity)
    {
        this.shootingEntity = entity;
    }

    protected void setPickupModeFromEntity(EntityLivingBase entityliving)
    {
        if ((entityliving instanceof EntityPlayer))
        {
            if (((EntityPlayer) entityliving).capabilities.isCreativeMode)
            {
                setPickupMode(2);
            }
        }
        else
        {
            setPickupMode(0);
        }
    }

    public void setThrowableHeading(double x, double y, double z, float speed, float deviation)
    {
        float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
        x /= f2;
        y /= f2;
        z /= f2;
        x += this.rand.nextGaussian() * 0.007499999832361937D * deviation;
        y += this.rand.nextGaussian() * 0.007499999832361937D * deviation;
        z += this.rand.nextGaussian() * 0.007499999832361937D * deviation;
        x *= speed;
        y *= speed;
        z *= speed;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f3 = MathHelper.sqrt_double(x * x + z * z);
        this.prevRotationYaw = (this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / 3.141592653589793D));
        this.prevRotationPitch = (this.rotationPitch = (float) (Math.atan2(y, f3) * 180.0D / 3.141592653589793D));
        this.ticksInGround = 0;
    }

    public void setVelocity(double d, double d1, double d2)
    {
        this.motionX = d;
        this.motionY = d1;
        this.motionZ = d2;
        if ((aimRotation()) && (this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
        {
            float f = MathHelper.sqrt_double(d * d + d2 * d2);
            this.prevRotationYaw = (this.rotationYaw = (float) (Math.atan2(d, d2) * 180.0D / 3.141592653589793D));
            this.prevRotationPitch = (this.rotationPitch = (float) (Math.atan2(d1, f) * 180.0D / 3.141592653589793D));
            setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    public void onUpdate()
    {
        onEntityUpdate();
    }

    public void onEntityUpdate()
    {
        super.onEntityUpdate();
        if (aimRotation())
        {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = (this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
            this.prevRotationPitch = (this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0D / 3.141592653589793D));
        }
        Block i = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
        if (i != null)
        {
            i.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB axisalignedbb = i.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
            if ((axisalignedbb != null) && (axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))))
            {
                this.inGround = true;
            }
        }
        if (this.arrowShake > 0)
        {
            this.arrowShake -= 1;
        }
        if (this.inGround)
        {
            Block j = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
            int k = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
            if ((j == this.inTile) && (k == this.inData))
            {
                this.ticksInGround += 1;
                int t = getMaxLifetime();
                if ((t != 0) && (this.ticksInGround >= t))
                {
                    setDead();
                }
            }
            else
            {
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2F;
                this.motionY *= this.rand.nextFloat() * 0.2F;
                this.motionZ *= this.rand.nextFloat() * 0.2F;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
            return;
        }
        this.ticksInAir += 1;

        Vec3 vec3d = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec3d1 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec3d, vec3d1, false, true, false);
        vec3d = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        vec3d1 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (movingobjectposition != null)
        {
            vec3d1 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        Entity entity = null;

        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for (int l = 0; l < list.size(); l++)
        {
            Entity entity1 = (Entity) list.get(l);
            if ((entity1.canBeCollidedWith()) && ((entity1 != this.shootingEntity) || (this.ticksInAir >= 5)))
            {
                float f4 = 0.3F;
                AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f4, f4, f4);
                MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3d, vec3d1);
                if (movingobjectposition1 != null)
                {
                    double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
                    if ((d1 < d) || (d == 0.0D))
                    {
                        entity = entity1;
                        d = d1;
                    }
                }
            }
        }
        if (entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }
        if (movingobjectposition != null)
        {
            if (movingobjectposition.entityHit != null)
            {
                onEntityHit(movingobjectposition.entityHit);
            }
            else
            {
                onGroundHit(movingobjectposition);
            }
        }
        if (getIsCritical())
        {
            for (int i1 = 0; i1 < 2; i1++)
            {
                this.worldObj.spawnParticle("crit", this.posX + this.motionX * i1 / 4.0D, this.posY + this.motionY * i1 / 4.0D, this.posZ + this.motionZ * i1 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        if (aimRotation())
        {
            float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = ((float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
            for (this.rotationPitch = ((float) (Math.atan2(this.motionY, f2) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
            }
            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }
            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }
            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }
            this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
            this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
        }
        float res = getAirResistance();
        float grav = getGravity();
        if (isInWater())
        {
            this.beenInGround = true;
            for (int i1 = 0; i1 < 4; i1++)
            {
                float f6 = 0.25F;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * f6, this.posY - this.motionY * f6, this.posZ - this.motionZ * f6, this.motionX, this.motionY, this.motionZ);
            }
            res *= 0.8080808F;
        }
        this.motionX *= res;
        this.motionY *= res;
        this.motionZ *= res;
        this.motionY -= grav;
        setPosition(this.posX, this.posY, this.posZ);
        func_145775_I();
    }

    public void onEntityHit(Entity entity)
    {
        bounceBack();
        applyEntityHitEffects(entity);
    }

    public void applyEntityHitEffects(Entity entity)
    {
        if ((isBurning()) && (!(entity instanceof EntityEnderman)))
        {
            entity.setFire(5);
        }
        if ((entity instanceof EntityLivingBase))
        {
            EntityLivingBase entityliving = (EntityLivingBase) entity;
            if (this.knockBack > 0)
            {
                float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                if (f > 0.0F)
                {
                    entity.addVelocity(this.motionX * this.knockBack * 0.6D / f, 0.1D, this.motionZ * this.knockBack * 0.6D / f);
                }
            }
            if ((this.shootingEntity instanceof EntityLivingBase))
            {
                EnchantmentHelper.func_151384_a(entityliving, this.shootingEntity);
                EnchantmentHelper.func_151385_b((EntityLivingBase) this.shootingEntity, entityliving);
            }
            if (((this.shootingEntity instanceof EntityPlayerMP)) && (this.shootingEntity != entity) && ((entity instanceof EntityPlayer)))
            {
                ((EntityPlayerMP) this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
            }
        }
    }

    public void onGroundHit(MovingObjectPosition mop)
    {
        this.xTile = mop.blockX;
        this.yTile = mop.blockY;
        this.zTile = mop.blockZ;
        this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
        this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
        this.motionX = (mop.hitVec.xCoord - this.posX);
        this.motionY = (mop.hitVec.yCoord - this.posY);
        this.motionZ = (mop.hitVec.zCoord - this.posZ);
        float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.posX -= this.motionX / f1 * 0.05D;
        this.posY -= this.motionY / f1 * 0.05D;
        this.posZ -= this.motionZ / f1 * 0.05D;
        this.inGround = true;
        this.beenInGround = true;
        setIsCritical(false);
        this.arrowShake = getMaxArrowShake();
        playHitSound();
        if (this.inTile != null)
        {
            this.inTile.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
        }
    }

    protected void bounceBack()
    {
        this.motionX *= -0.1D;
        this.motionY *= -0.1D;
        this.motionZ *= -0.1D;
        this.rotationYaw += 180.0F;
        this.prevRotationYaw += 180.0F;
        this.ticksInAir = 0;
    }

    public final double getTotalVelocity()
    {
        return Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
    }

    public boolean aimRotation()
    {
        return true;
    }

    public int getMaxLifetime()
    {
        return 1200;
    }

    public ItemStack getPickupItem()
    {
        return null;
    }

    public float getAirResistance()
    {
        return 0.99F;
    }

    public float getGravity()
    {
        return 0.05F;
    }

    public int getMaxArrowShake()
    {
        return 7;
    }

    public void playHitSound()
    {
    }

    public boolean canBeCritical()
    {
        return false;
    }

    public boolean getIsCritical()
    {
        return (canBeCritical()) && (this.dataWatcher.getWatchableObjectByte(16) != 0);
    }

    public void setIsCritical(boolean flag)
    {
        if (canBeCritical())
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (flag ? 1 : 0)));
        }
    }

    public void setExtraDamage(float f)
    {
        this.extraDamage = f;
    }

    public void setKnockbackStrength(int i)
    {
        this.knockBack = i;
    }

    public int getPickupMode()
    {
        return this.pickupMode;
    }

    public void setPickupMode(int i)
    {
        this.pickupMode = i;
    }

    public boolean canPickup(EntityPlayer entityplayer)
    {
        if (this.pickupMode == 1)
        {
            return true;
        }
        if (this.pickupMode == 2)
        {
            return entityplayer.capabilities.isCreativeMode;
        }
        if (this.pickupMode == 3)
        {
            return entityplayer == this.shootingEntity;
        }
        return false;
    }

    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        if ((this.inGround) && (this.arrowShake <= 0))
        {
            if (canPickup(entityplayer))
            {
                if (!this.worldObj.isRemote)
                {
                    ItemStack item = getPickupItem();
                    if (item == null)
                    {
                        return;
                    }
                    if (((this.pickupMode == 2) && (entityplayer.capabilities.isCreativeMode)) || (entityplayer.inventory.addItemStackToInventory(item)))
                    {
                        this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                        onItemPickup(entityplayer);
                        setDead();
                    }
                }
            }
        }
    }

    protected void onItemPickup(EntityPlayer entityplayer)
    {
        entityplayer.onItemPickup(this, 1);
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    protected boolean canTriggerWalking()
    {
        return false;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short) this.xTile);
        nbttagcompound.setShort("yTile", (short) this.yTile);
        nbttagcompound.setShort("zTile", (short) this.zTile);
        nbttagcompound.setByte("inTile", (byte) Block.getIdFromBlock(this.inTile));
        nbttagcompound.setByte("inData", (byte) this.inData);
        nbttagcompound.setByte("shake", (byte) this.arrowShake);
        nbttagcompound.setBoolean("inGround", this.inGround);
        nbttagcompound.setBoolean("beenInGround", this.beenInGround);
        nbttagcompound.setByte("pickup", (byte) this.pickupMode);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        this.xTile = nbttagcompound.getShort("xTile");
        this.yTile = nbttagcompound.getShort("yTile");
        this.zTile = nbttagcompound.getShort("zTile");
        this.inTile = Block.getBlockById(nbttagcompound.getByte("inTile") & 0xFF);
        this.inData = (nbttagcompound.getByte("inData") & 0xFF);
        this.arrowShake = (nbttagcompound.getByte("shake") & 0xFF);
        this.inGround = nbttagcompound.getBoolean("inGround");
        this.beenInGround = nbttagcompound.getBoolean("beenInGrond");
        this.pickupMode = nbttagcompound.getByte("pickup");
    }
}
