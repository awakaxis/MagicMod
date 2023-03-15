package net.awakaxis.magic.networking;

import net.awakaxis.magic.Magic;
import net.awakaxis.magic.networking.packet.TestC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModNetworking {
    public static Identifier TEST_ID = new Identifier(Magic.MOD_ID, "test");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(TEST_ID, TestC2SPacket::receive);
    }

    public static void registerS2CPackets() {}
}
