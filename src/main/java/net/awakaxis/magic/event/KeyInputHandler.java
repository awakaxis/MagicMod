package net.awakaxis.magic.event;

import net.awakaxis.magic.networking.ModNetworking;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_MAGIC = "key.category.magic.magiccategory";
    public static final String KEY_TEST = "key.magic.test";

    public static KeyBinding testKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(testKey.isPressed()) {
                ClientPlayNetworking.send(ModNetworking.TEST_ID, PacketByteBufs.create());
            }
        });
    }

    public static void register() {
        testKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TEST,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY_MAGIC

        ));
        registerKeyInputs();
    }
}
