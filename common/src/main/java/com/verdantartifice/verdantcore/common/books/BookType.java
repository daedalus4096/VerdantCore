package com.verdantartifice.verdantcore.common.books;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Definition of a book type.  Determines the background texture used when rendering the book GUI.
 * 
 * @author Daedalus4096
 */
public class BookType implements StringRepresentable {
    private static final Map<ResourceLocation, BookType> TYPES = new HashMap<>();

    public static final Codec<BookType> CODEC = ResourceLocation.CODEC.xmap(BookType::get, BookType::getTag);
    public static final StreamCodec<ByteBuf, BookType> STREAM_CODEC = ResourceLocation.STREAM_CODEC.map(BookType::get, BookType::getTag);

    private final ResourceLocation tag;
    private final ResourceLocation bgTexture;
    
    public BookType(ResourceLocation tag, ResourceLocation bgTexture) {
        this.tag = tag;
        this.bgTexture = bgTexture;
    }
    
    public ResourceLocation getTag() {
        return this.tag;
    }
    
    public ResourceLocation getBackgroundTexture() {
        return this.bgTexture;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.tag.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BookType bookType)) return false;
        return Objects.equals(tag, bookType.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tag);
    }

    @Nullable
    public static BookType get(ResourceLocation tag) {
        return TYPES.get(tag);
    }
}
