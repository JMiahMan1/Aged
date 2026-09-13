package com.teamresourceful.resourcefullib.client.screens;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * Stub for resourcefullib 5.0.x client API.
 * When upstream resourcefullib publishes a 26.2 build, replace this
 * with the real artifact. Stubs only the methods Chipped actually calls.
 */
public abstract class AbstractContainerCursorScreen<M extends AbstractContainerMenu> extends AbstractContainerScreen<M> {
    protected AbstractContainerCursorScreen(M menu, net.minecraft.world.entity.player.Inventory inv, net.minecraft.network.chat.Component title) {
        super(menu, inv, title);
    }
}
