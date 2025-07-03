package com.verdantartifice.verdantcore.common.crafting;

import com.verdantartifice.verdantcore.common.research.requirements.AbstractRequirement;

import java.util.Optional;

/**
 * Interface for a crafting recipe that optionally requires mod research to unlock.
 * 
 * @author Daedalus4096
 */
public interface IHasRequirement {
    /**
     * Get the requirement for the recipe.
     * 
     * @return the requirement for the recipe
     */
    Optional<AbstractRequirement<?>> getRequirement();
}
