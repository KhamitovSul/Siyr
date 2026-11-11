package ru.khamitovsul.siyr;

import net.minecraft.entity.passive.CowEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class SiyrDanceLogic {
    private static final Set<UUID> ACTIVE_COWS = new HashSet<>();

    private SiyrDanceLogic() {
    }

    public static void tick(CowEntity cow, ServerWorld world) {
        UUID id = cow.getUuid();
        if (!SiyrMod.isSiyr(cow)) {
            ACTIVE_COWS.remove(id);
            return;
        }

        if (ACTIVE_COWS.add(id)) {
            start(cow, world);
        }
    }

    public static boolean isDancing(CowEntity cow) {
        return SiyrMod.isSiyr(cow);
    }

    private static void start(CowEntity cow, ServerWorld world) {
        world.playSound(
                null,
                cow.getBlockPos(),
                SiyrMod.SIYR_DANCE_SOUND.value(),
                SoundCategory.NEUTRAL,
                1.0F,
                1.0F
        );
    }

}
