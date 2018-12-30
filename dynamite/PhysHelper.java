package fr.pandaax.mytrilium.dynamite;


import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PhysHelper
{
    private static double kbMotionX = 0.0D;
    private static double kbMotionY = 0.0D;
    private static double kbMotionZ = 0.0D;
    private static int knockBackModifier = 0;

    public static AdvancedExplosion createStandardExplosion(World world, Entity entity, double d, double d1, double d2, float size)
    {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size);
        explosion.doEntityExplosion();
        explosion.doBlockExplosion();
        explosion.doParticleExplosion(true, true);
        sendExplosion(world, explosion, true, true);
        return explosion;
    }

    public static AdvancedExplosion createAdvancedExplosion(World world, Entity entity, double d, double d1, double d2, float size, boolean destroyBlocks, boolean spawnSmallParticles, boolean spawnBigParticles)
    {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size);
        explosion.doEntityExplosion();
        if (destroyBlocks)
        {
            explosion.doBlockExplosion();
        }
        explosion.doParticleExplosion(spawnSmallParticles, spawnBigParticles);
        sendExplosion(world, explosion, spawnSmallParticles, spawnBigParticles);
        return explosion;
    }

    public static AdvancedExplosion createAdvancedExplosion(World world, Entity entity, DamageSource damagesource, double d, double d1, double d2, float size, boolean destroyBlocks, boolean spawnSmallParticles, boolean spawnBigParticles)
    {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size);
        explosion.doEntityExplosion(damagesource);
        if (destroyBlocks)
        {
            explosion.doBlockExplosion();
        }
        explosion.doParticleExplosion(spawnSmallParticles, spawnBigParticles);
        sendExplosion(world, explosion, spawnSmallParticles, spawnBigParticles);
        return explosion;
    }

    public static AdvancedExplosion createAdvancedExplosion(World world, Entity entity, double d, double d1, double d2, float size, boolean destroyBlocks, boolean spawnParticles)
    {
        AdvancedExplosion explosion = new AdvancedExplosion(world, entity, d, d1, d2, size);
        explosion.doEntityExplosion();
        if (destroyBlocks)
        {
            explosion.doBlockExplosion();
        }
        explosion.doParticleExplosion(spawnParticles, spawnParticles);
        sendExplosion(world, explosion, spawnParticles, spawnParticles);
        return explosion;
    }

    private static void sendExplosion(World world, AdvancedExplosion explosion, boolean smallparts, boolean bigparts)
    {
        if (((world instanceof WorldServer)) && (!world.isRemote))
        {
            MSGExplosion localMsgExplosion = new MSGExplosion(explosion, smallparts, bigparts);
        }
    }

    public static void knockBack(EntityLivingBase entityliving, EntityLivingBase attacker, float knockback)
    {
        entityliving.motionX = kbMotionX;
        entityliving.motionY = kbMotionY;
        entityliving.motionZ = kbMotionZ;

        double dx = attacker.posX - entityliving.posX;
        for (double dz = attacker.posZ - entityliving.posZ; dx * dx + dz * dz < 1.0E-4D; dz = (Math.random() - Math.random()) * 0.01D)
        {
            dx = (Math.random() - Math.random()) * 0.01D;
        }
        entityliving.attackedAtYaw = ((float) (Math.atan2(dx, dx) * 180.0D / 3.141592653589793D) - entityliving.rotationYaw);

        float f = MathHelper.sqrt_double(dx * dx + dx * dx);
        entityliving.motionX -= dx / f * knockback;
        entityliving.motionY += knockback;
        entityliving.motionZ -= dx / f * knockback;
        if (entityliving.motionY > 0.4D)
        {
            entityliving.motionY = 0.4D;
        }
        if (knockBackModifier > 0)
        {
            dx = -Math.sin(Math.toRadians(attacker.rotationYaw)) * knockBackModifier * 0.5D;
            dx = Math.cos(Math.toRadians(attacker.rotationYaw)) * knockBackModifier * 0.5D;
            entityliving.addVelocity(dx, 0.1D, dx);
        }
        knockBackModifier = 0;
        kbMotionX = kbMotionY = kbMotionZ = 0.0D;
    }

    public static void prepareKnockbackOnEntity(EntityLivingBase attacker, EntityLivingBase entity)
    {
        knockBackModifier = EnchantmentHelper.getKnockbackModifier(attacker, entity);
        if (attacker.isSprinting())
        {
            knockBackModifier += 1;
        }
        kbMotionX = entity.motionX;
        kbMotionY = entity.motionY;
        kbMotionZ = entity.motionZ;
    }
}
