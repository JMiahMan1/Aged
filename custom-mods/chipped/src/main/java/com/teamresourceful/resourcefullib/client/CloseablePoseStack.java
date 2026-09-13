package com.teamresourceful.resourcefullib.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

public class CloseablePoseStack implements AutoCloseable {
    private final PoseStack pose;

    public CloseablePoseStack(GuiGraphics graphics) {
        this.pose = graphics.pose();
    }

    public CloseablePoseStack(PoseStack pose) {
        this.pose = pose;
    }

    public PoseStack pose() {
        return pose;
    }

    public void translate(double x, double y, double z) {
        pose.translate(x, y, z);
    }

    public void scale(float x, float y, float z) {
        pose.scale(x, y, z);
    }

    public void pushPose() {
        pose.pushPose();
    }

    public void popPose() {
        pose.popPose();
    }

    @Override
    public void close() {
        // no-op: pose stack is managed by GuiGraphics
    }
}
