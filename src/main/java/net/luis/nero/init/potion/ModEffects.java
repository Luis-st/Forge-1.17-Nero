package net.luis.nero.init.potion;

import net.luis.nero.Nero;
import net.luis.nero.common.potion.IceEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects {
	
	// https://www.mathsisfun.com/hexadecimal-decimal-colors.html
	
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Nero.MOD_ID);
	
	
	public static final RegistryObject<MobEffect> HARVEST = MOB_EFFECTS.register("harvest", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 1301533));
	public static final RegistryObject<MobEffect> HARVEST_FATIGUE = MOB_EFFECTS.register("harvest_fatigue", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 8978478));
	public static final RegistryObject<IceEffect> ICE = MOB_EFFECTS.register("ice", () -> new IceEffect(MobEffectCategory.HARMFUL, 10878975));
	public static final RegistryObject<MobEffect> BRIDGE = MOB_EFFECTS.register("bridge", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0));

}
