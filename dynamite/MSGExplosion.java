package fr.pandaax.mytrilium.dynamite;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MSGExplosion extends WMMessage
{
    private double x;
    private double y;
    private double z;
    private float size;
    private List<ChunkPosition> blocks;
    private boolean smallParticles;
    private boolean bigParticles;

    public MSGExplosion(AdvancedExplosion explosion, boolean smallparts, boolean bigparts)
    {
        this.x = explosion.explosionX;
        this.y = explosion.explosionY;
        this.z = explosion.explosionZ;
        this.size = explosion.explosionSize;
        this.blocks = explosion.affectedBlockPositions;
        this.smallParticles = smallparts;
        this.bigParticles = bigparts;
    }

    public MSGExplosion()
    {
    }

    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buf)
    {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.size = buf.readFloat();
        this.smallParticles = buf.readBoolean();
        this.bigParticles = buf.readBoolean();

        int size = buf.readInt();
        this.blocks = new ArrayList(size);
        for (int i = 0; i < size; i++)
        {
            int ix = buf.readByte() + (int) this.x;
            int iy = buf.readByte() + (int) this.y;
            int iz = buf.readByte() + (int) this.z;
            this.blocks.add(new ChunkPosition(ix, iy, iz));
        }
    }

    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buf)
    {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.size);
        buf.writeBoolean(this.smallParticles);
        buf.writeBoolean(this.bigParticles);

        int n = this.blocks.size();
        buf.writeInt(n);
        for (int i = 0; i < n; i++)
        {
            ChunkPosition pos = (ChunkPosition) this.blocks.get(i);
            int dx = pos.chunkPosX - (int) this.x;
            int dy = pos.chunkPosY - (int) this.y;
            int dz = pos.chunkPosZ - (int) this.z;
            buf.writeByte(dx);
            buf.writeByte(dy);
            buf.writeByte(dz);
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleClientSide(EntityPlayer player)
    {
        World world = FMLClientHandler.instance().getWorldClient();
        AdvancedExplosion expl = new AdvancedExplosion(world, null, this.x, this.y, this.z, this.size);
        expl.setAffectedBlockPositions(this.blocks);
        expl.doParticleExplosion(this.smallParticles, this.bigParticles);
    }

    public void handleServerSide(EntityPlayer player)
    {
    }
}
