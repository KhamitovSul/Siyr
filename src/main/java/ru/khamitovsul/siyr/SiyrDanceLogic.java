package ru.khamitovsul.siyr;

import net.minecraft.entity.passive.CowEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;

public final class SiyrDanceLogic {
    private static final int DANCE_LENGTH_TICKS = 20 * 20;

    private SiyrDanceLogic() {
    }

    public static void onCowLoaded(CowEntity cow, ServerWorld world) {
        if (SiyrMod.isSiyr(cow) && !isDancing(cow)) {
            start(cow, world);
        }
    }

    public static void tick(CowEntity cow, ServerWorld world) {
        if (!SiyrMod.isSiyr(cow)) {
            if (isDancing(cow)) {
                state(cow).siyr$setDanceTicks(0);
            }
            return;
        }

        if (!isDancing(cow)) {
            start(cow, world);
            return;
        }

        state(cow).siyr$setDanceTicks(state(cow).siyr$getDanceTicks() - 1);
    }

    public static boolean isDancing(CowEntity cow) {
        return state(cow).siyr$getDanceTicks() > 0;
    }

    private static void start(CowEntity cow, ServerWorld world) {
        state(cow).siyr$setDanceTicks(DANCE_LENGTH_TICKS);
        world.playSound(
                null,
                cow.getBlockPos(),
                SiyrMod.SIYR_DANCE_SOUND,
                SoundCategory.NEUTRAL,
                1.0F,
                1.0F
        );
    }

    private static SiyrDanceState state(CowEntity cow) {
        return (SiyrDanceState) cow;
    }
}
