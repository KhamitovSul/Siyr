package ru.khamitovsul.siyr;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.Vec3d;

public final class SiyrClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_WORLD_TICK.register(SiyrClient::animateDancingCows);
    }

    private static void animateDancingCows(ClientWorld world) {
        if (MinecraftClient.getInstance().player == null) {
            return;
        }
        for (CowEntity cow : world.getEntitiesByClass(
                CowEntity.class,
                MinecraftClient.getInstance().player.getBoundingBox().expand(64.0),
                SiyrDanceLogic::isDancing
        )) {
            int age = cow.age;
            float spin = (age % 360) * 7.0F;
            cow.setYaw(spin);
            cow.bodyYaw = spin;
            cow.headYaw = spin;

            if (age % 8 == 0) {
                cow.setVelocity(cow.getVelocity().x, 0.22, cow.getVelocity().z);
            }

            spawnLaserParticles(world, cow);
        }
    }

    private static void spawnLaserParticles(ClientWorld world, CowEntity cow) {
        Vec3d eye = cow.getPos().add(0.0, cow.getHeight() * 0.78, 0.0);
        Vec3d forward = Vec3d.fromPolar(0.0F, cow.getYaw()).normalize();
        Vec3d side = new Vec3d(-forward.z, 0.0, forward.x).normalize().multiply(0.12);
        for (int eyeIndex = -1; eyeIndex <= 1; eyeIndex += 2) {
            Vec3d origin = eye.add(side.multiply(eyeIndex));
            for (int i = 1; i <= 12; i++) {
                Vec3d point = origin.add(forward.multiply(i * 0.25));
                world.addParticle(
                        new DustParticleEffect(0xFF0000, 1.0F),
                        point.x,
                        point.y,
                        point.z,
                        1.0,
                        0.0,
                        0.0
                );
            }
        }
    }
}
