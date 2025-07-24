package com.verdantartifice.verdantcore.common.registries;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record RegistryEncodedResourceKey<T>(ResourceLocation registry, ResourceLocation location) {
    public static <E> Codec<RegistryEncodedResourceKey<E>> codec() {
        return RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("registry").forGetter(RegistryEncodedResourceKey::registry),
                ResourceLocation.CODEC.fieldOf("location").forGetter(RegistryEncodedResourceKey::location)
            ).apply(instance, RegistryEncodedResourceKey::new));
    }

    public static <E> StreamCodec<ByteBuf, RegistryEncodedResourceKey<E>> streamCodec() {
        return StreamCodec.composite(
                ResourceLocation.STREAM_CODEC, RegistryEncodedResourceKey::registry,
                ResourceLocation.STREAM_CODEC, RegistryEncodedResourceKey::location,
                RegistryEncodedResourceKey::new
            );
    }

    public static <E> Codec<Holder<E>> holderCodec() {
        return new Codec<>() {
            @Override
            public <T> DataResult<Pair<Holder<E>, T>> decode(DynamicOps<T> ops, T value) {
                if (ops instanceof RegistryOps<?> registryOps) {
                    return RegistryEncodedResourceKey.<E>codec().decode(ops, value).flatMap(pair -> {
                        RegistryEncodedResourceKey<E> rerk = pair.getFirst();
                        Optional<HolderGetter<E>> getterOpt = registryOps.getter(rerk.registryKey());
                        if (getterOpt.isPresent()) {
                            HolderGetter<E> getter = getterOpt.get();
                            return getter.get(rerk.toResourceKey())
                                    .map(DataResult::success)
                                    .orElseGet(() -> DataResult.error(() -> "Failed to get element " + rerk.location()))
                                    .map(obj -> Pair.of((Holder<E>)obj, pair.getSecond()))
                                    .setLifecycle(Lifecycle.stable());
                        } else {
                            return DataResult.error(() -> "Can't access registry " + rerk.registryKey());
                        }
                    });
                } else {
                    return DataResult.error(() -> "Given ops " + ops + " is not a RegistryOps!");
                }
            }

            @Override
            public <T> DataResult<T> encode(Holder<E> holder, DynamicOps<T> ops, T value) {
                if (ops instanceof RegistryOps<?> registryOps) {
                    return holder.unwrap().map(refResourceKey -> {
                        Optional<HolderOwner<E>> ownerOpt = registryOps.owner(refResourceKey.registryKey());
                        if (ownerOpt.isPresent()) {
                            if (holder.canSerializeIn(ownerOpt.get())) {
                                return RegistryEncodedResourceKey.<E>codec().encode(RegistryEncodedResourceKey.fromResourceKey(refResourceKey), ops, value);
                            } else {
                                return DataResult.error(() -> "Element " + holder + " is not valid in registry " + refResourceKey.location());
                            }
                        } else {
                            return DataResult.error(() -> "Can't access registry " + refResourceKey.registryKey());
                        }
                    }, directValue -> {
                        return DataResult.error(() -> "Element " + directValue + " can't be serialized to a value!");
                    });
                } else {
                    return DataResult.error(() -> "Given ops " + ops + " is not a RegistryOps!");
                }
            }
        };
    }

    public static <E> RegistryEncodedResourceKey<E> fromResourceKey(ResourceKey<E> resourceKey) {
        return new RegistryEncodedResourceKey<>(resourceKey.registry(), resourceKey.location());
    }

    public ResourceKey<Registry<T>> registryKey() {
        return ResourceKey.createRegistryKey(this.registry);
    }

    public ResourceKey<T> toResourceKey() {
        return ResourceKey.create(ResourceKey.createRegistryKey(this.registry), this.location);
    }
}
