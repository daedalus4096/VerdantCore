package com.verdantartifice.verdantcore.common.rewards;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.verdantartifice.verdantcore.common.books.BookLanguage;
import com.verdantartifice.verdantcore.common.books.BookLanguagesVC;
import com.verdantartifice.verdantcore.common.books.LinguisticsManager;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.util.RegistryUtils;
import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.Optional;

/**
 * Linguistics grid reward that grants comprehension of a language.
 * 
 * @author Daedalus4096
 */
public class ComprehensionReward extends AbstractReward<ComprehensionReward> {
    private static final ResourceLocation ICON_LOC = ResourceUtils.loc("textures/gui/sprites/scribe_table/gain_comprehension.png");
    
    public static final MapCodec<ComprehensionReward> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ResourceKey.codec(RegistryKeysVC.BOOK_LANGUAGES).fieldOf("language").forGetter(r -> r.language),
            Codec.INT.fieldOf("points").forGetter(r -> r.points)
        ).apply(instance, ComprehensionReward::new));
    public static final StreamCodec<ByteBuf, ComprehensionReward> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(RegistryKeysVC.BOOK_LANGUAGES), r -> r.language,
            ByteBufCodecs.VAR_INT, r -> r.points,
            ComprehensionReward::new);
    
    private ResourceKey<BookLanguage> language;
    private int points;
    private Optional<Component> pointsText = Optional.empty();
    
    public ComprehensionReward(ResourceKey<BookLanguage> language, int points) {
        Verify.verifyNotNull(language, "Invalid language for comprehension reward");
        this.language = language;
        this.setPoints(points);
    }
    
    @Override
    protected RewardType<ComprehensionReward> getType() {
        return RewardTypesVC.COMPREHENSION.get();
    }
    
    public int getPoints() {
        return this.points;
    }

    protected void setPoints(int points) {
        this.points = points;
        this.pointsText = Optional.of(Component.literal(Integer.toString(this.points)));
    }
    
    @Override
    public void grant(ServerPlayer player) {
        RegistryUtils.getHolder(RegistryKeysVC.BOOK_LANGUAGES, this.language, player.registryAccess()).ifPresent(langHolder -> {
            LinguisticsManager.incrementComprehension(player, langHolder, this.points);
        });
    }

    @Override
    public Component getDescription(Player player) {
        Component amountText = Component.translatable("label.verdantcore.comprehension_gain." + Mth.clamp(this.points, 0, 5));
        Component langText = RegistryUtils.getHolderOrDefault(RegistryKeysVC.BOOK_LANGUAGES, this.language, player.registryAccess(), BookLanguagesVC.DEFAULT).value().getName();
        return Component.translatable("label.verdantcore.scribe_table.grid.reward.comprehension", langText, amountText);
    }

    @Override
    public ResourceLocation getIconLocation(Player player) {
        return ICON_LOC;
    }

    @Override
    public Optional<Component> getAmountText() {
        return this.pointsText;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ComprehensionReward that)) return false;
        return points == that.points && Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, points);
    }

    public static class Builder {
        protected final ResourceKey<BookLanguage> language;
        protected int points = 0;
        
        protected Builder(ResourceKey<BookLanguage> language) {
            this.language = Preconditions.checkNotNull(language);
        }
        
        public static Builder reward(ResourceKey<BookLanguage> language) {
            return new Builder(language);
        }
        
        public Builder points(int points) {
            this.points = points;
            return this;
        }
        
        private void validate() {
            if (this.points < 0) {
                throw new IllegalStateException("Points value must be non-negative");
            }
        }
        
        public ComprehensionReward build() {
            this.validate();
            return new ComprehensionReward(this.language, this.points);
        }
    }
}
