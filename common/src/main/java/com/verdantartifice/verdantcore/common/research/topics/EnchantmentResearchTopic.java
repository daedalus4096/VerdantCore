package com.verdantartifice.verdantcore.common.research.topics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Objects;

/**
 * Research topic that points to a rune enchantment entry in the Grimoire.
 * 
 * @author Daedalus4096
 */
public class EnchantmentResearchTopic extends AbstractResearchTopic<EnchantmentResearchTopic> {
    public static final MapCodec<EnchantmentResearchTopic> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Enchantment.CODEC.fieldOf("enchantment").forGetter(EnchantmentResearchTopic::getEnchantment),
            Codec.INT.fieldOf("page").forGetter(EnchantmentResearchTopic::getPage)
        ).apply(instance, EnchantmentResearchTopic::new));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, EnchantmentResearchTopic> STREAM_CODEC = StreamCodec.composite(
            Enchantment.STREAM_CODEC, EnchantmentResearchTopic::getEnchantment,
            ByteBufCodecs.VAR_INT, EnchantmentResearchTopic::getPage,
            EnchantmentResearchTopic::new);
    
    protected final Holder<Enchantment> enchantment;
    
    public EnchantmentResearchTopic(Holder<Enchantment> enchantment, int page) {
        super(page);
        this.enchantment = enchantment;
    }
    
    public Holder<Enchantment> getEnchantment() {
        return this.enchantment;
    }

    @Override
    public ResearchTopicType<EnchantmentResearchTopic> getType() {
        return ResearchTopicTypesVC.ENCHANTMENT.get();
    }

    @Override
    public EnchantmentResearchTopic withPage(int newPage) {
        return new EnchantmentResearchTopic(this.enchantment, newPage);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EnchantmentResearchTopic that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(enchantment, that.enchantment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), enchantment);
    }
}
