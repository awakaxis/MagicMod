package net.awakaxis.magic;

import net.awakaxis.magic.event.KeyInputHandler;
import net.awakaxis.magic.networking.ModNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.option.KeyBinding;

public class MagicClient implements ClientModInitializer {
    private static KeyBinding keytest;
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModNetworking.registerS2CPackets();
    }
}
