package dev.jmiahman.hearthwind.skills.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import dev.jmiahman.hearthwind.skills.SkillGates;

/**
 * Crafting gate enforcement.
 *
 * <p>The {@code levelz/crafting} corpus was loaded but never enforced
 * (135 ids, e.g. golden pickaxe behind mining 8, diamond armor behind
 * defense 24). This clears a gated result the moment vanilla computes it,
 * so the player sees an empty output slot plus the standard gate-hint
 * toast instead of taking an item they have not earned.
 */
@Mixin(CraftingMenu.class)
public abstract class CraftingGateMixin {
    @Inject(method = "slotChangedCraftingGrid", at = @At("TAIL"))
    private static void hearthwind$gateCraftingResult(AbstractContainerMenu menu,
            ServerLevel level, Player player, CraftingContainer craftSlots,
            ResultContainer result, RecipeHolder<CraftingRecipe> recipe,
            CallbackInfo ci) {
        if (!(player instanceof ServerPlayer sp)) {
            return;
        }
        ItemStack out = result.getItem(0);
        if (out.isEmpty()) {
            return;
        }
        SkillGates.Gate gate = SkillGates.craftGate(out);
        if (gate != null && !SkillGates.allowed(sp, gate)) {
            result.setItem(0, ItemStack.EMPTY);
            SkillGates.deny(sp, gate, "craft",
                    net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(out.getItem()));
        }
    }
}
