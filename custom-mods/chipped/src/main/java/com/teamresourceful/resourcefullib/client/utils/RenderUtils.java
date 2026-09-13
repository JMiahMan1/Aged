package com.teamresourceful.resourcefullib.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Objects;

public class RenderUtils {
    public static AutoCloseable createScissorBox(Minecraft mc, PoseStack pose, int x, int y, int width, int height) {
        return () -> {};
    }
}
