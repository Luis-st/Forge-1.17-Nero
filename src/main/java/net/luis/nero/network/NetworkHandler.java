package net.luis.nero.network;

import net.luis.nero.Nero;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
	
	private static final String VERSION = "1";
	@SuppressWarnings("unused")
	private static int id = 0;
	public static SimpleChannel simpleChannel;

	public static void init() {
		NetworkRegistry.newSimpleChannel(new ResourceLocation(Nero.MOD_ID, "simple_chnanel"), () -> VERSION, VERSION::equals, VERSION::equals);
	}

}
