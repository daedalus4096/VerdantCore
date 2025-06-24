package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.tiles.crafting.AbstractCalcinatorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.crafting.ConcocterTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.DesalinatorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.DissolutionChamberTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceCaskTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceTransmuterTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.HoneyExtractorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.InfernalFurnaceTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.SanguineCrucibleTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.AutoChargerTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaBatteryTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.WandChargerTileEntity;
import com.verdantartifice.verdantcore.common.tiles.rituals.RitualAltarTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface IBlockEntityTickerService {
    // Crafting tiles
    BlockEntityTicker<AbstractCalcinatorTileEntity> calcinator();
    BlockEntityTicker<AbstractCalcinatorTileEntity> essenceFurnace();
    BlockEntityTicker<ConcocterTileEntity> concocter();

    // Device tiles
    BlockEntityTicker<DesalinatorTileEntity> desalinator();
    BlockEntityTicker<DissolutionChamberTileEntity> dissolutionChamber();
    BlockEntityTicker<EssenceCaskTileEntity> essenceCask();
    BlockEntityTicker<EssenceTransmuterTileEntity> essenceTransmuter();
    BlockEntityTicker<HoneyExtractorTileEntity> honeyExtractor();
    BlockEntityTicker<InfernalFurnaceTileEntity> infernalFurnace();
    BlockEntityTicker<SanguineCrucibleTileEntity> sanguineCrucible();

    // Mana tiles
    BlockEntityTicker<AutoChargerTileEntity> autoCharger();
    BlockEntityTicker<ManaBatteryTileEntity> manaBattery();
    BlockEntityTicker<WandChargerTileEntity> wandCharger();

    // Ritual tiles
    BlockEntityTicker<RitualAltarTileEntity> ritualAltar();
}
