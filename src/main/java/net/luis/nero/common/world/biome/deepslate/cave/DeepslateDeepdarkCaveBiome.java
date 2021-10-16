package net.luis.nero.common.world.biome.deepslate.cave;

import net.luis.nero.common.enums.BiomeEffects;
import net.luis.nero.common.world.biome.deepslate.DeepslateBiome;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;

public class DeepslateDeepdarkCaveBiome extends DeepslateBiome {
	
	public DeepslateDeepdarkCaveBiome(BiomeEffects biomeEffects) {
		super(biomeEffects);
	}

	@Override
	public float getTemperature() {
		return 0.5F;
	}
	
	@Override
	public BiomeSpecialEffects getBiomeSpecialEffects() {
		BiomeSpecialEffects.Builder specialEffectsBuilder = new BiomeSpecialEffects.Builder();
		specialEffectsBuilder.waterColor(4400);
		specialEffectsBuilder.waterFogColor(30);
		specialEffectsBuilder.fogColor(9800);
		specialEffectsBuilder.skyColor(this.calculateSkyColor(0.8F));
		specialEffectsBuilder.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
		specialEffectsBuilder.grassColorOverride(213328);
		specialEffectsBuilder.foliageColorOverride(5153);
		return specialEffectsBuilder.build();
	}

}
