package com.verdantartifice.verdantcore.common.research.topics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.research.ResearchEntry;
import com.verdantartifice.verdantcore.common.research.keys.ResearchEntryKey;
import com.verdantartifice.verdantcore.common.util.RegistryUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.Optional;

/**
 * Research topic that points to a mod research entry in the Grimoire.
 * 
 * @author Daedalus4096
 */
public class EntryResearchTopic extends AbstractResearchTopic<EntryResearchTopic> {
    public static final MapCodec<EntryResearchTopic> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResearchEntryKey.CODEC.fieldOf("entry").forGetter(EntryResearchTopic::getEntry),
            Codec.INT.fieldOf("page").forGetter(EntryResearchTopic::getPage)
        ).apply(instance, EntryResearchTopic::new));
    
    public static final StreamCodec<ByteBuf, EntryResearchTopic> STREAM_CODEC = StreamCodec.composite(
            ResearchEntryKey.STREAM_CODEC, EntryResearchTopic::getEntry,
            ByteBufCodecs.VAR_INT, EntryResearchTopic::getPage,
            EntryResearchTopic::new);
    
    protected final ResearchEntryKey entry;
    
    public EntryResearchTopic(ResearchEntryKey entryKey, int page) {
        super(page);
        this.entry = entryKey;
    }

    public EntryResearchTopic(ResourceKey<ResearchEntry> entryRawKey, int page) {
        super(page);
        this.entry = new ResearchEntryKey(entryRawKey);
    }
    
    public ResearchEntryKey getEntry() {
        return this.entry;
    }

    @Override
    public ResearchTopicType<EntryResearchTopic> getType() {
        return ResearchTopicTypesVC.RESEARCH_ENTRY.get();
    }

    @Override
    public EntryResearchTopic withPage(int newPage) {
        return new EntryResearchTopic(this.entry, newPage);
    }

    @Override
    public boolean isUnread(Player player) {
        ResearchEntry e = RegistryUtils.getEntry(this.entry.getRegistryKey(), this.entry.getRootKey(), player.registryAccess());
        return e != null && e.isUnread(player);
    }

    @Override
    public Optional<Component> getUnreadTooltip(Player player) {
        return this.isUnread(player) ? Optional.of(Component.translatable("tooltip.verdantcore.unread_count.entry.single")) : Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EntryResearchTopic that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(entry, that.entry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), entry);
    }
}
