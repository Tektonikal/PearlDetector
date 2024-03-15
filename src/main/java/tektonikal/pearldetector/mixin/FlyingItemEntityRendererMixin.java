package tektonikal.pearldetector.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.pearldetector.config.Config;

import java.util.ArrayList;
import java.util.List;

@Mixin(net.minecraft.client.render.entity.FlyingItemEntityRenderer.class)
public class FlyingItemEntityRendererMixin<T extends Entity> {
    @Mutable
    @Final
    @Shadow private float scale;
    @Mutable
    @Shadow @Final private boolean lit;
    @Unique
    public List<Entity> notified = new ArrayList<>();

    @Inject(method = "render", at = @At("TAIL"))
    public void updated(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci){
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(Config.isEnabled && entity instanceof EnderPearlEntity){
            this.scale = Config.pearlScale;
            this.lit = Config.lit;
        if(entity.age <= 2 && !notified.contains(entity) && entity.getPos().distanceTo(player.getEyePos()) > Config.minDistance && entity.getPos().distanceTo(player.getEyePos()) < Config.maxDistance){
            if(Config.playSound){
                try {
                    Identifier soundId = new Identifier(Config.soundID);
                    player.playSound(SoundEvent.of(soundId), Config.volume,Config.pitch);
                } catch (InvalidIdentifierException exception) {
                    player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1,1);
                }
            }
                notified.add(entity);
        }
        }
        else{
            this.scale = 1;
        }
    }

}
