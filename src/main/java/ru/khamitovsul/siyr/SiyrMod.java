package ru.khamitovsul.siyr;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.server.world.ServerWorld;

public final class SiyrMod implements ModInitializer {
    public static final String MOD_ID = "siyr";
    public static final Identifier SIYR_DANCE_ID = Identifier.of(MOD_ID, "siyr_dance");
    public static final RegistryEntry<SoundEvent> SIYR_DANCE_SOUND = Registry.registerReference(
            Registries.SOUND_EVENT,
            SIYR_DANCE_ID,
            SoundEvent.of(SIYR_DANCE_ID)
    );

    @Override
    public void onInitialize() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof CowEntity cow) {
                SiyrDanceLogic.onCowLoaded(cow, (ServerWorld) world);
            }
        });
        ServerTickEvents.END_WORLD_TICK.register(world -> world.iterateEntities().forEach(entity -> {
            if (entity instanceof CowEntity cow) {
                SiyrDanceLogic.tick(cow, world);
            }
        }));
    }

    public static boolean isSiyr(CowEntity cow) {
        return "Siyr".equals(cow.getName().getString());
    }
}
