package ru.khamitovsul.siyr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.CowEntity;
import ru.khamitovsul.siyr.SiyrDanceState;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin implements SiyrDanceState {
    @Unique
    private static final TrackedData<Integer> SIYR_DANCE_TICKS =
            DataTracker.registerData(CowEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void siyr$initDataTracker(DataTracker.Builder builder, CallbackInfo callbackInfo) {
        builder.add(SIYR_DANCE_TICKS, 0);
    }

    @Override
    public int siyr$getDanceTicks() {
        return ((CowEntity) (Object) this).getDataTracker().get(SIYR_DANCE_TICKS);
    }

    @Override
    public void siyr$setDanceTicks(int ticks) {
        ((CowEntity) (Object) this).getDataTracker().set(SIYR_DANCE_TICKS, Math.max(0, ticks));
    }
}
