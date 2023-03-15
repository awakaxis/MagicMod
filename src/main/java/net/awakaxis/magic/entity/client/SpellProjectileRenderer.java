package net.awakaxis.magic.entity.client;

import net.awakaxis.magic.Magic;
import net.awakaxis.magic.entity.custom.SpellProjectileEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SpellProjectileRenderer extends EntityRenderer<SpellProjectileEntity> {
    public static final Identifier TEXTURE = new Identifier(Magic.MOD_ID,
            "textures/entity/spell_projectile/test.png");

    public SpellProjectileRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(SpellProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(SpellProjectileEntity entity) {
        return TEXTURE;
    }
}
