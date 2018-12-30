package fr.pandaax.mytrilium.dynamite;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class WeaponDamageSource extends EntityDamageSourceIndirect
{
    private EntityProjectile projectileEntity;
    private Entity shooterEntity;

    public WeaponDamageSource(String s, EntityProjectile projectile, Entity entity)
    {
        super(s, projectile, entity);
        this.projectileEntity = projectile;
        this.shooterEntity = entity;
    }

    public static DamageSource causeProjectileWeaponDamage(EntityProjectile projectile, Entity entity)
    {
        return new WeaponDamageSource("weapon", projectile, entity).setProjectile();
    }

    public Entity getProjectile()
    {
        return this.projectileEntity;
    }

    public Entity getEntity()
    {
        return this.shooterEntity;
    }
}

