package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.tiles.crafting.AbstractCalcinatorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.crafting.ConcocterTileEntity;
import com.verdantartifice.verdantcore.common.tiles.crafting.RunecarvingTableTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.DesalinatorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.DissolutionChamberTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceCaskTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceTransmuterTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.HoneyExtractorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.InfernalFurnaceTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.ResearchTableTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.SanguineCrucibleTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.ScribeTableTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.AutoChargerTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaBatteryTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaInjectorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaRelayTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.WandChargerTileEntity;
import com.verdantartifice.verdantcore.common.tiles.misc.CarvedBookshelfTileEntity;
import com.verdantartifice.verdantcore.common.tiles.rituals.OfferingPedestalTileEntity;
import com.verdantartifice.verdantcore.common.tiles.rituals.RitualAltarTileEntity;
import com.verdantartifice.verdantcore.common.tiles.rituals.RitualLecternTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface IBlockEntityPrototypeService {
    // Crafting tiles
    BlockEntityType.BlockEntitySupplier<AbstractCalcinatorTileEntity> calcinator();
    BlockEntityType.BlockEntitySupplier<AbstractCalcinatorTileEntity> essenceFurnace();
    BlockEntityType.BlockEntitySupplier<ConcocterTileEntity> concocter();
    BlockEntityType.BlockEntitySupplier<RunecarvingTableTileEntity> runecarvingTable();

    // Device tiles
    BlockEntityType.BlockEntitySupplier<DesalinatorTileEntity> desalinator();
    BlockEntityType.BlockEntitySupplier<DissolutionChamberTileEntity> dissolutionChamber();
    BlockEntityType.BlockEntitySupplier<EssenceCaskTileEntity> essenceCask();
    BlockEntityType.BlockEntitySupplier<EssenceTransmuterTileEntity> essenceTransmuter();
    BlockEntityType.BlockEntitySupplier<HoneyExtractorTileEntity> honeyExtractor();
    BlockEntityType.BlockEntitySupplier<InfernalFurnaceTileEntity> infernalFurnace();
    BlockEntityType.BlockEntitySupplier<ResearchTableTileEntity> researchTable();
    BlockEntityType.BlockEntitySupplier<SanguineCrucibleTileEntity> sanguineCrucible();
    BlockEntityType.BlockEntitySupplier<ScribeTableTileEntity> scribeTable();

    // Mana tiles
    BlockEntityType.BlockEntitySupplier<AutoChargerTileEntity> autoCharger();
    BlockEntityType.BlockEntitySupplier<ManaBatteryTileEntity> manaBattery();
    BlockEntityType.BlockEntitySupplier<ManaInjectorTileEntity> manaInjector();
    BlockEntityType.BlockEntitySupplier<ManaRelayTileEntity> manaRelay();
    BlockEntityType.BlockEntitySupplier<WandChargerTileEntity> wandCharger();

    // Misc tiles
    BlockEntityType.BlockEntitySupplier<CarvedBookshelfTileEntity> carvedBookshelf();

    // Ritual tiles
    BlockEntityType.BlockEntitySupplier<OfferingPedestalTileEntity> offeringPedestal();
    BlockEntityType.BlockEntitySupplier<RitualAltarTileEntity> ritualAltar();
    BlockEntityType.BlockEntitySupplier<RitualLecternTileEntity> ritualLectern();
}
