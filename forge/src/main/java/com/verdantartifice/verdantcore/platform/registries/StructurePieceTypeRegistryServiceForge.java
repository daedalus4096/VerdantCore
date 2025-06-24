package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.platform.services.registries.IStructurePieceTypeRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Forge implementation of the structure piece type registry service.
 *
 * @author Daedalus4096
 */
public class StructurePieceTypeRegistryServiceForge extends AbstractBuiltInRegistryServiceForge<StructurePieceType> implements IStructurePieceTypeRegistryService {
    private static final DeferredRegister<StructurePieceType> TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<StructurePieceType>> getDeferredRegisterSupplier() {
        return () -> TYPES;
    }

    @Override
    protected Registry<StructurePieceType> getRegistry() {
        return BuiltInRegistries.STRUCTURE_PIECE;
    }
}
