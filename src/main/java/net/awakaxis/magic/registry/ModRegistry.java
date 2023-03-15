package net.awakaxis.magic.registry;

import net.awakaxis.magic.Magic;
import net.awakaxis.magic.entity.custom.SpellProjectileEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRegistry {
    public static final EntityType<SpellProjectileEntity> SPELL_PROJECTILE = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(Magic.MOD_ID, "spell_projectile"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SpellProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(.5f,.5f)).build());
    public static void registerAll() {
        Magic.LOGGER.debug("Registered all mod registries.");
    }
}
