package net.awakaxis.magic.utils;

import net.awakaxis.magic.registry.ModRegistry;
import net.awakaxis.magic.entity.custom.SpellProjectileEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.explosion.Explosion;

import java.util.LinkedList;

public class Utilities {

    static LinkedList<Entity> activeEntities = new LinkedList<>();
    static NbtCompound ownerPlayers = new NbtCompound();

    public static void RegisterCollisionListener() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (int i = 0; i < activeEntities.size(); i++) {
                Entity e = activeEntities.get(i);
                Box box = Box.of(e.getPos(),e.getWidth(), e.getHeight(), e.getWidth());
                if (BlockPos.stream(box).anyMatch(blockPos -> {
                    BlockState blockState = e.getWorld().getBlockState((blockPos));
                    return !blockState.isAir() && VoxelShapes.matchesAnywhere(blockState.getCollisionShape(e.getWorld(),
                            blockPos).offset(blockPos.getX(),blockPos.getY(),blockPos.getZ()), VoxelShapes.cuboid(box), BooleanBiFunction.AND);
                })) {
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
        });
    }
    public static void Test(ServerPlayerEntity player, ServerWorld world) {
        Vec3d force = player.getRotationVector().multiply(1d);
        Vec3d spawnPos = player.getEyePos().subtract(0d, 0.5d, 0d).add(player.getRotationVector().multiply(3d));
        SpellProjectileEntity entity = new SpellProjectileEntity(ModRegistry.SPELL_PROJECTILE, world);

        ownerPlayers.putUuid(String.format("awakaxis.magic.caster.of " + entity.getUuid()), player.getUuid());
        activeEntities.add(entity);

        entity.setImmuneToExplosion(true);
        entity.setFireTrail(true);
        entity.setNoGravity(true);
        entity.setVelocity(force);
        entity.setPosition(spawnPos);
        world.spawnEntity(entity);
    }
}
