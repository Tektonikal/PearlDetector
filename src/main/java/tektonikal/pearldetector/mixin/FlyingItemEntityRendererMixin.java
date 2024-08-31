package tektonikal.pearldetector.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.pearldetector.config.Config;

import java.util.ArrayList;
import java.util.List;

@Mixin(net.minecraft.client.render.entity.FlyingItemEntityRenderer.class)
public abstract class FlyingItemEntityRendererMixin<T extends Entity & FlyingItemEntity> extends EntityRenderer<T> {
    @Mutable
    @Final
    @Shadow
    private float scale;
    @Mutable
    @Shadow @Final private ItemRenderer itemRenderer;
    @Unique
    public List<Entity> notified = new ArrayList<>();

    protected FlyingItemEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void updated(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (Config.isEnabled && entity instanceof EnderPearlEntity) {
            this.scale = Config.pearlScale;
            if ( entity.age < 1 &&
                    !notified.contains(entity) &&
                            entity.getPos().distanceTo(
                                    new Vec3d(MinecraftClient.getInstance().player.getX(),
                                            MinecraftClient.getInstance().player.getEyeY() - 0.10000000149011612,
                                            MinecraftClient.getInstance().player.getZ())) > Config.minDistance &&
                            entity.getPos().distanceTo(
                                    new Vec3d(MinecraftClient.getInstance().player.getX(),
                                            MinecraftClient.getInstance().player.getEyeY() - 0.10000000149011612,
                                            MinecraftClient.getInstance().player.getZ())) < Config.maxDistance) {
                if (Config.playSound) {
                    try {
                        Identifier soundId = Identifier.of(Config.soundID);
                        MinecraftClient.getInstance().player.playSound(SoundEvent.of(soundId), Config.volume, Config.pitch);
                    } catch (InvalidIdentifierException exception) {
                        MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    }
                }
                notified.add(entity);
            }

            ci.cancel();
        } else {
            scale = 1;
        }
        matrices.push();
        matrices.scale(this.scale, this.scale, this.scale);
        matrices.multiply(this.dispatcher.getRotation());
        this.itemRenderer.renderItem(((FlyingItemEntity) entity).getStack(), ModelTransformationMode.GROUND, getLight(entity), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), entity.getId());
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, getLight(entity));
    }
    @Unique
    private int getLight(T entity){
        if(Config.isEnabled && Config.lightLevel != -1){
            return Config.lightLevel;
        }
        else{
            BlockPos blockPos = BlockPos.ofFloored(entity.getX(), entity.getY(), entity.getZ());
            return entity.getWorld().isChunkLoaded(blockPos) ? WorldRenderer.getLightmapCoordinates(entity.getWorld(), blockPos) : 0;
        }
    }
}
