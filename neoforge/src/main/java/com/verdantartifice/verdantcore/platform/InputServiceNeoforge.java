package com.verdantartifice.verdantcore.platform;

import com.mojang.blaze3d.platform.InputConstants;
import com.verdantartifice.verdantcore.platform.services.IInputService;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

/**
 * Neoforge implementation of the input service.
 *
 * @author Daedalus4096
 */
public class InputServiceNeoforge implements IInputService {
    @Override
    public boolean isKeyDown(KeyMapping keybind) {
        if (keybind.isUnbound()) {
            return false;
        } else {
            Minecraft mc = Minecraft.getInstance();
            boolean isDown = switch (keybind.getKey().getType()) {
                case KEYSYM -> InputConstants.isKeyDown(mc.getWindow().getWindow(), keybind.getKey().getValue());
                case MOUSE -> GLFW.glfwGetMouseButton(mc.getWindow().getWindow(), keybind.getKey().getValue()) == GLFW.GLFW_PRESS;
                default -> false;
            };
            return isDown && keybind.getKeyConflictContext().isActive() && keybind.getKeyModifier().isActive(keybind.getKeyConflictContext());
        }
    }

    @Override
    public InputConstants.Key getKey(KeyMapping keybind) {
        return keybind.getKey();
    }
}
