package dev.jmiahman.hearthwind.skills;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Server -> client hint for a denied skill-gate action.
 *
 * <p>The gate logic itself stays server-authoritative (see
 * {@code SkillGates}); this payload carries the <em>structured</em> reason
 * so the client can render it as a toast (icon + coloured text + dismiss)
 * instead of the raw overlay line it used to fall back to. The player still
 * sees the same information either way; the payload just lets the HUD
 * choose the presentation.
 *
 * <p>Sent from {@code SkillGates.deny} on every denied break/use/use-item/
 * use-entity/interact action. The client keeps only the most recent hint and
 * shows it for a few seconds; older hints are overwritten, never queued.
 */
public record SkillGateHintPayload(String skill, int level, String action,
        Identifier targetId) implements CustomPacketPayload {

    public static final Type<SkillGateHintPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("hearthwind", "gate_hint"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SkillGateHintPayload> CODEC =
            new StreamCodec<>() {
                @Override
                public SkillGateHintPayload decode(RegistryFriendlyByteBuf buf) {
                    String skill = buf.readUtf(32767);
                    int level = buf.readInt();
                    String action = buf.readUtf(32767);
                    String target = buf.readUtf(32767);
                    return new SkillGateHintPayload(skill, level, action,
                            Identifier.tryParse(target));
                }

                @Override
                public void encode(RegistryFriendlyByteBuf buf,
                        SkillGateHintPayload payload) {
                    buf.writeUtf(payload.skill());
                    buf.writeInt(payload.level());
                    buf.writeUtf(payload.action());
                    buf.writeUtf(payload.targetId().toString());
                }
            };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}