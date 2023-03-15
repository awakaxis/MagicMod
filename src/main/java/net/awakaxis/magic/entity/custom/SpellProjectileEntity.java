package net.awakaxis.magic.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SpellProjectileEntity extends Entity {
    protected static final TrackedData<Boolean> FIRE_TRAIL = DataTracker.registerData(SpellProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> EXPLOSION_IMMUNE = DataTracker.registerData(SpellProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public SpellProjectileEntity(EntityType<SpellProjectileEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        this.setPosition(this.getPos().add(this.getVelocity()));

        if(!this.hasNoGravity()) {
            this.addVelocity(0,-.04,0);
        }

        if(this.hasFireTrail()) {
            for(int i = 0; i < 30; i++) {
                this.world.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        }
        for (PlayerEntity player: this.getWorld().getPlayers()) {
            player.sendMessage(Text.of(String.format(this.getUuidAsString() + " hasFireTrail = " + this.hasFireTrail())));
        }
    }

    public void setImmuneToExplosion(boolean immuneToExplosion){this.dataTracker.set(EXPLOSION_IMMUNE, immuneToExplosion);}
    public void setFireTrail(boolean FireTrail){this.dataTracker.set(FIRE_TRAIL, FireTrail);}

    @Override
    public boolean isImmuneToExplosion(){return this.dataTracker.get(EXPLOSION_IMMUNE);}
    public boolean hasFireTrail(){return this.dataTracker.get(FIRE_TRAIL);}

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(FIRE_TRAIL, false);
        this.dataTracker.startTracking(EXPLOSION_IMMUNE, false);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}