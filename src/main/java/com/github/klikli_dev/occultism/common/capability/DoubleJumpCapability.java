/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.common.capability;

import com.github.klikli_dev.occultism.registry.OccultismCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DoubleJumpCapability implements INBTSerializable<CompoundTag> {

    //region Fields
    private int jumps = 0;
    //endregion Fields

    //region Initialization
    public DoubleJumpCapability() {
    }
    //endregion Initialization

    //region Getter / Setter
    public int getJumps() {
        return this.jumps;
    }

    public void setJumps(int i) {
        this.jumps = i;
    }
    //endregion Getter / Setter

    //region Methods
    public void addJump() {
        this.jumps++;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.putInt("jumps", this.jumps);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.jumps = nbt.getInt("jumps");
    }
    //endregion Methods

    public static class Dispatcher implements ICapabilitySerializable<CompoundTag> {

        //region Fields
        private final LazyOptional<DoubleJumpCapability> doubleJumpCapability = LazyOptional.of(
                DoubleJumpCapability::new);
        //endregion Fields

        //region Overrides
        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == OccultismCapabilities.DOUBLE_JUMP) {
                return this.doubleJumpCapability.cast();
            }
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.doubleJumpCapability.map(DoubleJumpCapability::serializeNBT).orElse(new CompoundTag());
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.doubleJumpCapability.ifPresent(capability -> capability.deserializeNBT(nbt));
        }

        //endregion Overrides

    }
}
