package com.verdantartifice.verdantcore.common.research;

import com.mojang.serialization.Codec;
import com.verdantartifice.verdantcore.common.stats.Stat;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a type of knowledge, that being a form of currency the player spends in pursuit of research.
 *
 * @author Daedalus4096
 */
public class KnowledgeType implements StringRepresentable {
    private static final Map<ResourceLocation, KnowledgeType> TYPES = new HashMap<>();

    public static final Codec<KnowledgeType> CODEC = ResourceLocation.CODEC.xmap(KnowledgeType::get, KnowledgeType::getName);
    public static final StreamCodec<ByteBuf, KnowledgeType> STREAM_CODEC = ResourceLocation.STREAM_CODEC.map(KnowledgeType::get, KnowledgeType::getName);

    private final int id;
    private final ResourceLocation name;
    private final short progression;  // How many points make a complete level for this knowledge type
    private final ResourceLocation iconLocation;
    private final Optional<Stat> trackerStatOpt;
    
    private KnowledgeType(int id, @NotNull ResourceLocation name, int progression, @NotNull ResourceLocation iconLocation, @NotNull Optional<Stat> trackerStatOpt) {
        this.id = id;
        this.name = name;
        this.progression = (short)progression;
        this.iconLocation = iconLocation;
        this.trackerStatOpt = trackerStatOpt;
        TYPES.put(name, this);
    }
    
    public int getId() {
        return this.id;
    }

    public ResourceLocation getName() {
        return this.name;
    }
    
    public int getProgression() {
        return this.progression;
    }
    
    @NotNull
    public ResourceLocation getIconLocation() {
        return this.iconLocation;
    }

    @NotNull
    public Optional<Stat> getTrackerStatOpt() {
        return this.trackerStatOpt;
    }
    
    @NotNull
    public String getNameTranslationKey() {
        return String.join(".", "knowledge_type", this.name.getNamespace(), this.name.getPath());
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof KnowledgeType that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Nullable
    public static KnowledgeType get(String nameStr) {
        return get(ResourceLocation.tryParse(nameStr));
    }

    @Nullable
    public static KnowledgeType get(ResourceLocation name) {
        return TYPES.get(name);
    }
}