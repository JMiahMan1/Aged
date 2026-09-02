package dev.jmiahman.hearthwind.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import dev.jmiahman.hearthwind.skills.SkillGateHintPayload;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Gate-denial toast (Aged parity): when the server denies a break/use/use-item/
 * use-entity action behind a skill gate, it sends {@link SkillGateHintPayload}
 * and this HUD renders the reason as a toast near the top of the screen.
 *
 * <p>The gate check itself is still server-authoritative; this is purely a
 * presentation layer over the structured hint. Vanilla clients without the
 * companion mod fall back to the server's overlay message (see
 * {@code SkillGates.deny}).
 *
 * <p>Toasts queue up to two at a time and stack vertically; each fades out
 * after a few seconds. A fourth arriving while three are still showing drops
 * the oldest, so a denial spam can never grow the queue unbounded.
 */
public final class GateHintHud implements HudElement {
    public static final GateHintHud INSTANCE = new GateHintHud();
    private static final int MAX_TOASTS = 3;
    private static final int FADE_TICKS = 60;      // 3s at 20/s
    private static final int STAGGER_TICKS = 8;     // 0.4s between toasts

    private static final ConcurrentLinkedQueue<Toast> TOASTS =
            new ConcurrentLinkedQueue<>();

    private GateHintHud() {}

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(
                SkillGateHintPayload.TYPE,
                (payload, context) -> context.client().execute(() ->
                        onHint(payload.skill(), payload.level(),
                                payload.action(), payload.targetId())));

        HudElementRegistry.attachElementBefore(
                VanillaHudElements.HOTBAR,
                Identifier.fromNamespaceAndPath("hearthwind", "gate_hint_hud"),
                INSTANCE);
    }

    static void onHint(String skill, int level, String action,
            net.minecraft.resources.Identifier targetId) {
        long now = Minecraft.getInstance().level != null
                ? Minecraft.getInstance().level.getGameTime() : 0L;
        TOASTS.add(new Toast(skill, level, action, now));
        while (TOASTS.size() > MAX_TOASTS) {
            Toast oldest = TOASTS.poll();
            if (oldest == null) break;
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics,
            DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            return;
        }
        long now = mc.level.getGameTime();
        TOASTS.removeIf(t -> now - t.spawnTick > FADE_TICKS + 60);
        if (TOASTS.isEmpty()) {
            return;
        }

        Font font = mc.font;
        int w = graphics.guiWidth();
        int x = w / 2;
        int y = 24;
        int i = 0;
        for (Toast t : TOASTS) {
            renderToast(graphics, font, t, x, y + i * (22 + STAGGER_TICKS), now);
            i++;
        }
    }

    private static void renderToast(GuiGraphicsExtractor graphics, Font font,
            Toast t, int x, int y, long now) {
        long age = now - t.spawnTick;
        float alpha;
        if (age < 10) {
            alpha = age / 10f;
        } else if (age < FADE_TICKS) {
            alpha = 1f;
        } else {
            alpha = Math.max(0f, 1f - (age - FADE_TICKS) / 10f);
        }
        if (alpha <= 0.01f) {
            return;
        }
        int a = ((int) (alpha * 255f)) & 0xFF;
        if (a == 0) return;

        String skillCap = t.skill.substring(0, 1).toUpperCase()
                + t.skill.substring(1).toLowerCase();
        String title = "Skill Gate";
        String line = skillCap + " Lv " + t.level + " needed to " + t.action
                + " this";

        int titleW = font.width(title);
        int lineW = font.width(line);
        int boxW = Math.max(titleW, lineW) + 40;
        int boxH = 26;
        int bx = x - boxW / 2;

        int border = (a << 24) | 0xD4AF37;          // gold
        int bg = (Math.min(a, 0xC0) << 24) | 0x1A1A1A;
        int titleColor = (a << 24) | 0xFFAA00;
        int lineColor = (a << 24) | 0xFFFFFF;

        graphics.fill(bx, y, bx + boxW, y + boxH, border);
        graphics.fill(bx + 1, y + 1, bx + boxW - 1, y + boxH - 1, bg);

        graphics.item(new ItemStack(getSkillIcon(t.skill)), bx + 6, y + 5);
        graphics.text(font, title, bx + 26, y + 4, titleColor, true);
        graphics.text(font, line, bx + 26, y + 14, lineColor, true);
    }

    private static Item getSkillIcon(String skill) {
        if (skill == null) return Items.EXPERIENCE_BOTTLE;
        return switch (skill.toLowerCase()) {
            case "farming" -> Items.WHEAT;
            case "mining" -> Items.IRON_PICKAXE;
            case "smithing" -> Items.ANVIL;
            case "strength" -> Items.IRON_SWORD;
            case "agility" -> Items.LEATHER_BOOTS;
            case "defense" -> Items.IRON_CHESTPLATE;
            case "health" -> Items.GOLDEN_APPLE;
            case "stamina" -> Items.FEATHER;
            case "luck" -> Items.RABBIT_FOOT;
            case "archery" -> Items.BOW;
            case "alchemy" -> Items.BREWING_STAND;
            case "trade" -> Items.EMERALD;
            default -> Items.EXPERIENCE_BOTTLE;
        };
    }

    private static final class Toast {
        final String skill;
        final int level;
        final String action;
        final long spawnTick;
        Toast(String skill, int level, String action, long spawnTick) {
            this.skill = skill;
            this.level = level;
            this.action = action;
            this.spawnTick = spawnTick;
        }
    }
}