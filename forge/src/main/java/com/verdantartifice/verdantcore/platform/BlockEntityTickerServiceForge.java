package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.tiles.crafting.AbstractCalcinatorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.crafting.CalcinatorTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.crafting.ConcocterTileEntity;
import com.verdantartifice.verdantcore.common.tiles.crafting.ConcocterTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.crafting.EssenceFurnaceTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.devices.DesalinatorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.DesalinatorTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.devices.DissolutionChamberTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.DissolutionChamberTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceCaskTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceCaskTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceTransmuterTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceTransmuterTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.devices.HoneyExtractorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.HoneyExtractorTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.devices.InfernalFurnaceTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.InfernalFurnaceTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.devices.SanguineCrucibleTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.SanguineCrucibleTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.mana.AutoChargerTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.AutoChargerTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaBatteryTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaBatteryTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.mana.WandChargerTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.WandChargerTileEntityForge;
import com.verdantartifice.verdantcore.common.tiles.rituals.RitualAltarTileEntity;
import com.verdantartifice.verdantcore.common.tiles.rituals.RitualAltarTileEntityForge;
import com.verdantartifice.verdantcore.platform.services.IBlockEntityTickerService;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public class BlockEntityTickerServiceForge implements IBlockEntityTickerService {
    @Override
    public BlockEntityTicker<AbstractCalcinatorTileEntity> calcinator() {
        return CalcinatorTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<AbstractCalcinatorTileEntity> essenceFurnace() {
        return EssenceFurnaceTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<ConcocterTileEntity> concocter() {
        return ConcocterTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<DesalinatorTileEntity> desalinator() {
        return DesalinatorTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<DissolutionChamberTileEntity> dissolutionChamber() {
        return DissolutionChamberTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<EssenceCaskTileEntity> essenceCask() {
        return EssenceCaskTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<EssenceTransmuterTileEntity> essenceTransmuter() {
        return EssenceTransmuterTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<HoneyExtractorTileEntity> honeyExtractor() {
        return HoneyExtractorTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<InfernalFurnaceTileEntity> infernalFurnace() {
        return InfernalFurnaceTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<SanguineCrucibleTileEntity> sanguineCrucible() {
        return SanguineCrucibleTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<AutoChargerTileEntity> autoCharger() {
        return AutoChargerTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<ManaBatteryTileEntity> manaBattery() {
        return ManaBatteryTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<WandChargerTileEntity> wandCharger() {
        return WandChargerTileEntityForge::tick;
    }

    @Override
    public BlockEntityTicker<RitualAltarTileEntity> ritualAltar() {
        return RitualAltarTileEntityForge::tick;
    }
}
