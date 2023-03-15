package net.awakaxis.magic;

import net.awakaxis.magic.registry.ModRegistry;
import net.awakaxis.magic.entity.client.SpellProjectileRenderer;
import net.awakaxis.magic.event.KeyInputHandler;
import net.awakaxis.magic.networking.ModNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class MagicClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModNetworking.registerS2CPackets();
        EntityRendererRegistry.register(ModRegistry.SPELL_PROJECTILE, SpellProjectileRenderer::new);//EntityModelLayerRegistry.registerModelLayer(MODEL_SPELL_PROJECTILE_LAYER, SpellProjectileModel::getTexturedModelData);
    }
}
