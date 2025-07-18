package com.verdantartifice.verdantcore.common.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a packet of knowledge levels (e.g. 3 levels worth of observations).  Primarily used to
 * parse knowledge requirements for research and display them in the grimoire.
 * 
 * @author Daedalus4096
 */
public class Knowledge {
    public static final Codec<Knowledge> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            KnowledgeType.CODEC.fieldOf("type").forGetter(k -> k.type), 
            ExtraCodecs.POSITIVE_INT.fieldOf("amount").forGetter(k -> k.amount)
        ).apply(instance, Knowledge::new));
    
    protected KnowledgeType type;
    protected int amount;
    
    protected Knowledge(@Nonnull KnowledgeType type, int amount) {
        this.type = type;
        this.amount = amount;
    }
    
    @Nullable
    public static Knowledge parse(@Nullable String str) {
        if (str != null) {
            String[] tokens = str.split(";");
            if (tokens.length == 2) {
                KnowledgeType type;
                try {
                    type = KnowledgeType.get(tokens[0]);
                } catch (Exception e) {
                    return null;
                }
                int amount;
                try {
                    amount = Integer.parseInt(tokens[1]);
                } catch (NumberFormatException e) {
                    amount = 0;
                }
                if (type != null && amount > 0) {
                    return new Knowledge(type, amount);
                }
            }
        }
        
        // Return null if the given string does not represent a valid knowledge packet
        return null;
    }
    
    @Nonnull
    public KnowledgeType getType() {
        return this.type;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    @Override
    public String toString() {
        return this.type.getSerializedName() + ";" + this.amount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Knowledge other = (Knowledge) obj;
        if (amount != other.amount) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }
}
