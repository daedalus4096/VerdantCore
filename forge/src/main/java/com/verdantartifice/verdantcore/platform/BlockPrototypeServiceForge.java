package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.IBlockPrototypeService;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BlockPrototypeServiceForge implements IBlockPrototypeService {
    @Override
    public <T extends Block> Supplier<FlowerPotBlock> flowerPot(@Nullable Supplier<FlowerPotBlock> emptyBlock, Supplier<T> flowerSupplier, BlockBehaviour.Properties properties) {
        return () -> new FlowerPotBlock(emptyBlock, flowerSupplier, properties);
    }
}
