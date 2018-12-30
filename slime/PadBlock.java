package fr.pandaax.mytrilium.slime;

import fr.pandaax.mytrilium.proxy.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PadBlock extends Block
{

    private IIcon top, bottom;

    public PadBlock(Material p_i45394_1_, int i)
    {
        super(p_i45394_1_);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);

    }

    public IIcon getIcon(int side, int metadata)
    {
        if (side == 0)
        {
            return this.bottom;
        }
        else if (side == 1)
        {
            return this.top;
        }
        return this.blockIcon;
    }

    public void registerBlockIcons(IIconRegister iiconRegister)
    {
        this.blockIcon = iiconRegister.registerIcon(Reference.MOD_ID + ":slimeblockGreen");
        this.top = iiconRegister.registerIcon(Reference.MOD_ID + ":jumpBlock");
        this.bottom = iiconRegister.registerIcon(Reference.MOD_ID + ":slimeblockGreen");


    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (!world.isBlockIndirectlyGettingPowered(x, y, z))
        {
            double moveX = 0.0D;
            double moveZ = 0.0D;

            double speed = 0.25D;

            int meta = world.getBlockMetadata(x, y, z);
            switch (meta % 8)
            {
                case 6:
                    moveX += speed;
                    break;
                case 7:
                    moveX += speed;
                    moveZ += speed;
                    break;
                case 0:
                    moveZ += speed;
                    break;
                case 1:
                    moveZ += speed;
                    moveX -= speed;
                    break;
                case 2:
                    moveX -= speed;
                    break;
                case 3:
                    moveX -= speed;
                    moveZ -= speed;
                    break;
                case 4:
                    moveZ -= speed;
                    break;
                case 5:
                    moveZ -= speed;
                    moveX += speed;
            }
            if ((entity instanceof EntityItem))
            {
                entity.posY += 1.0D;
            }
            entity.fallDistance = 0.0F;
            entity.addVelocity(moveX, speed * 2.0D, moveZ);
            world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, this.stepSound.getStepResourcePath(), this.stepSound
                    .getVolume() / 2.0F, this.stepSound.getPitch() * 0.65F);
        }
    }

    public boolean isBlockReplaceable(World world, int x, int y, int z)
    {
        return false;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        int face = MathHelper.floor_double(entity.rotationYaw * 8.0F / 360.0F + 0.5D) + (entity.isSneaking() ? 4 : 0) & 0x7;

        int meta = world.getBlockMetadata(x, y, z) & 0x8;
        world.setBlockMetadataWithNotify(x, y, z, face | meta, 2);
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public int getRenderBlockPass()
    {
        return 1;
    }
}

