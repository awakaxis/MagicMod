package net.awakaxis.magic;

import net.awakaxis.magic.networking.ModNetworking;
import net.awakaxis.magic.utils.Utilities;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Magic implements ModInitializer {
	public static final String MOD_ID = "magic";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		ModNetworking.registerC2SPackets();
		Utilities.UpdateCollision();
	}
}
