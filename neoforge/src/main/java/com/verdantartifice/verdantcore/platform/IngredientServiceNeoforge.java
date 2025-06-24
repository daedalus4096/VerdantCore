package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.IIngredientService;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientServiceNeoforge implements IIngredientService {
    @Override
    public boolean isSimple(Ingredient ingredient) {
        return ingredient.isSimple();
    }
}
