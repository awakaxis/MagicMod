package net.awakaxis.magic.utils;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.explosion.Explosion;

import java.util.LinkedList;

public class Utilities {

    static LinkedList<Entity> activeEntities = new LinkedList<>();
    static NbtCompound ownerPlayers = new NbtCompound();

    public static void UpdateCollision() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (int i = 0; i < activeEntities.size(); i++) {
                Entity e = activeEntities.get(i);
                if (!e.getWorld().getBlockState(e.getBlockPos()).isAir()) { //REMINDER read Entity.isinsidewall() code - may be able to use it instead of this solution and entity.noclip = true
                    if (ownerPlayers.contains(String.format("awakaxis.magic.caster.of " + e.getUuid()))) {
                        e.getWorld().createExplosion(null, DamageSource.player(e.getWorld().getPlayerByUuid(ownerPlayers.getUuid(String.format("awakaxis.magic.caster.of " + e.getUuid())))),
                                null, e.getX(), e.getY(), e.getZ(), 5f, true,
                                Explosion.DestructionType.DESTROY);
                    }
                    activeEntities.remove(i);
                    e.discard();
                    e.remove(Entity.RemovalReason.DISCARDED);
                }
                if (!e.isAlive()) {
                    ownerPlayers.remove(String.format("awakaxis.magic.caster.of " + e.getUuid()));
                }
            }
            System.out.println(activeEntities.size());
            System.out.println(ownerPlayers.getKeys());
        });
    }
    public static void Test(ServerPlayerEntity player, ServerWorld world) {
        CowEntity cow = new CowEntity(EntityType.COW, world);

        ownerPlayers.putUuid(String.format("awakaxis.magic.caster.of " + cow.getUuid()), player.getUuid());
        activeEntities.add(cow);

        cow.setNoDrag(true);
        cow.setNoGravity(true);
        cow.noClip = true;
        cow.setVelocity(player.getRotationVector().multiply(3d));
        cow.setPosition(player.getEyePos().subtract(0d, 0.5d, 0d).add(player.getRotationVector().multiply(3d)));
        world.spawnEntity(cow);

        player.sendMessage(Text.of(cow.getUuidAsString()), false);
        player.sendMessage(Text.of(String.format("pitch yaw roll - " + player.getYaw() + " " + player.getPitch() + " " + player.getRoll())));
        player.sendMessage(Text.of(String.format("rot vector - " + player.getRotationVector())));
    }
}
