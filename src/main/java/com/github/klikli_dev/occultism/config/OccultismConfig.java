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

package com.github.klikli_dev.occultism.config;

import com.github.klikli_dev.occultism.config.value.CachedBoolean;
import com.github.klikli_dev.occultism.config.value.CachedFloat;
import com.github.klikli_dev.occultism.config.value.CachedInt;
import com.github.klikli_dev.occultism.config.value.CachedObject;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OccultismConfig extends ConfigBase {

    //region Fields
    public final StorageSettings storage;
    public final WorldGenSettings worldGen;
    public final RitualSettings rituals;
    public final DimensionalMineshaftSettings dimensionalMineshaft;
    public final ForgeConfigSpec spec;
    //endregion Fields

    //region Initialization
    public OccultismConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.storage = new StorageSettings(this, builder);
        this.worldGen = new WorldGenSettings(this, builder);
        this.rituals = new RitualSettings(this, builder);
        this.dimensionalMineshaft = new DimensionalMineshaftSettings(this, builder);
        this.spec = builder.build();
    }
    //endregion Initialization

    public class DimensionalMineshaftSettings extends ConfigCategoryBase {
        //region Fields
        public final MinerSpiritSettings minerFoliotUnspecialized;
        public final MinerSpiritSettings minerDjinniOres;
        //endregion Fields

        //region Initialization
        public DimensionalMineshaftSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Dimensional Mineshaft Settings").push("dimensional_mineshaft");

            this.minerFoliotUnspecialized =
                    new MinerSpiritSettings("miner_foliot_unspecialized", parent, builder, 400, 1, 1000);

            this.minerDjinniOres =
                    new MinerSpiritSettings("miner_djinni_ores", parent, builder, 400, 1, 100);

            builder.pop();
        }

        //endregion Initialization
        public class MinerSpiritSettings extends ConfigCategoryBase {
            //region Fields
            public final CachedInt maxMiningTime;
            public final CachedInt rollsPerOperation;
            public final CachedInt durability;
            //endregion Fields

            //region Initialization
            public MinerSpiritSettings(String oreName, IConfigCache parent, ForgeConfigSpec.Builder builder,
                                       int maxMiningTime, int rollsPerOperation, int durability) {
                super(parent, builder);
                builder.comment("Miner Spirit Settings").push(oreName);

                this.maxMiningTime = CachedInt.cache(this,
                        builder.comment("The amount of time it takes the spirit to perform one mining operation.")
                                .define("maxMiningTime", maxMiningTime));
                this.rollsPerOperation = CachedInt.cache(this,
                        builder.comment("The amount of blocks the spirit will obtain per mining operation")
                                .define("rollsPerOperation", rollsPerOperation));
                this.durability = CachedInt.cache(this,
                        builder.comment("The amount of mining operations the spirit can perform before breaking.")
                                .define("durability", durability));

                builder.pop();
            }
            //endregion Initialization
        }

    }

    public class RitualSettings extends ConfigCategoryBase {
        //region Fields
        public final CachedBoolean enableClearWeatherRitual;
        public final CachedBoolean enableRainWeatherRitual;
        public final CachedBoolean enableThunderWeatherRitual;
        public final CachedBoolean enableDayTimeRitual;
        public final CachedBoolean enableNightTimeRitual;
        //endregion Fields

        //region Initialization
        public RitualSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Ritual Settings").push("rituals");

            this.enableClearWeatherRitual = CachedBoolean.cache(this,
                    builder.comment("Enables the ritual to clear rainy weather.")
                            .define("enableClearWeatherRitual", true));
            this.enableRainWeatherRitual = CachedBoolean.cache(this,
                    builder.comment("Enables the ritual to start rainy weather.")
                            .define("enableRainWeatherRitual", true));
            this.enableThunderWeatherRitual = CachedBoolean.cache(this,
                    builder.comment("Enables the ritual to start a thunderstorm.")
                            .define("enableThunderWeatherRitual", true));
            this.enableDayTimeRitual = CachedBoolean.cache(this,
                    builder.comment("Enables the ritual to set time to day.")
                            .define("enableDayTimeRitual", true));
            this.enableNightTimeRitual = CachedBoolean.cache(this,
                    builder.comment("Enables the ritual to set time to night.")
                            .define("enableNightTimeRitual", true));
            builder.pop();
        }
        //endregion Initialization
    }

    public class StorageSettings extends ConfigCategoryBase {
        //region Fields
        public final CachedInt stabilizerTier1Slots;
        public final CachedInt stabilizerTier2Slots;
        public final CachedInt stabilizerTier3Slots;
        public final CachedInt stabilizerTier4Slots;
        public final CachedInt controllerBaseSlots;
        //endregion Fields

        //region Initialization
        public StorageSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Storage Settings").push("storage");
            this.stabilizerTier1Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 1 provides.")
                            .define("stabilizerTier1Slots", 128));
            this.stabilizerTier2Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 2 provides.")
                            .define("stabilizerTier2Slots", 256));
            this.stabilizerTier3Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 3 provides.")
                            .define("stabilizerTier3Slots", 512));
            this.stabilizerTier4Slots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage stabilizer tier 4 provides.")
                            .define("stabilizerTier4Slots", 1024));
            this.controllerBaseSlots = CachedInt.cache(this,
                    builder.comment("The amount of slots the storage actuator provides.")
                            .define("controllerBaseSlots", 128));
            builder.pop();
        }
        //endregion Initialization
    }

    public class WorldGenSettings extends ConfigCategoryBase {
        //region Fields
        public final UndergroundGroveGenSettings undergroundGroveGen;
        //endregion Fields

        //region Initialization
        public WorldGenSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("WorldGen Settings").push("worldgen");
            this.undergroundGroveGen = new UndergroundGroveGenSettings(this, builder);
            builder.pop();
        }
        //endregion Initialization

        public class UndergroundGroveGenSettings extends ConfigCategoryBase {
            //region Fields
            public CachedFloat grassChance;
            public CachedFloat treeChance;
            public CachedFloat vineChance;
            public CachedFloat ceilingLightChance;
            //endregion Fields

            //region Initialization
            public UndergroundGroveGenSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
                super(parent, builder);
                builder.comment("Underground Grove Settings").push("underground_grove");

                this.grassChance = CachedFloat.cache(this,
                        builder.comment("The chance grass will spawn in the underground grove.")
                                .define("grassChance", 0.6));
                this.treeChance = CachedFloat.cache(this,
                        builder.comment("The chance small trees will spawn in the underground grove.")
                                .define("treeChance", 0.1));
                this.vineChance = CachedFloat.cache(this,
                        builder.comment("The chance vines will spawn in the underground grove.")
                                .define("vineChance", 0.3));
                this.ceilingLightChance = CachedFloat.cache(this,
                        builder.comment("The chance glowstone will spawn in the ceiling of the underground grove.")
                                .define("ceilingLightChance", 0.1));

                builder.pop();
            }
            //endregion Initialization
        }
    }
}
