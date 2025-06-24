package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.tiles.crafting.AbstractCalcinatorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.crafting.CalcinatorTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.crafting.ConcocterTileEntity;
import com.verdantartifice.verdantcore.common.tiles.crafting.ConcocterTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.crafting.EssenceFurnaceTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.devices.DesalinatorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.DesalinatorTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.devices.DissolutionChamberTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.DissolutionChamberTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceCaskTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceCaskTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceTransmuterTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.EssenceTransmuterTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.devices.HoneyExtractorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.HoneyExtractorTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.devices.InfernalFurnaceTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.InfernalFurnaceTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.devices.SanguineCrucibleTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.SanguineCrucibleTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.mana.AutoChargerTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.AutoChargerTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaBatteryTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaBatteryTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.mana.WandChargerTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.WandChargerTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.rituals.RitualAltarTileEntity;
import com.verdantartifice.verdantcore.common.tiles.rituals.RitualAltarTileEntityNeoforge;
import com.verdantartifice.verdantcore.platform.services.IBlockEntityTickerService;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public class BlockEntityTickerServiceNeoforge implements IBlockEntityTickerService {
    @Override
    public BlockEntityTicker<AbstractCalcinatorTileEntity> calcinator() {
        return CalcinatorTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<AbstractCalcinatorTileEntity> essenceFurnace() {
        return EssenceFurnaceTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<ConcocterTileEntity> concocter() {
        return ConcocterTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<DesalinatorTileEntity> desalinator() {
        return DesalinatorTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<DissolutionChamberTileEntity> dissolutionChamber() {
        return DissolutionChamberTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<EssenceCaskTileEntity> essenceCask() {
        return EssenceCaskTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<EssenceTransmuterTileEntity> essenceTransmuter() {
        return EssenceTransmuterTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<HoneyExtractorTileEntity> honeyExtractor() {
        return HoneyExtractorTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<InfernalFurnaceTileEntity> infernalFurnace() {
        return InfernalFurnaceTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<SanguineCrucibleTileEntity> sanguineCrucible() {
        return SanguineCrucibleTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<AutoChargerTileEntity> autoCharger() {
        return AutoChargerTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<ManaBatteryTileEntity> manaBattery() {
        return ManaBatteryTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<WandChargerTileEntity> wandCharger() {
        return WandChargerTileEntityNeoforge::tick;
    }

    @Override
    public BlockEntityTicker<RitualAltarTileEntity> ritualAltar() {
        return RitualAltarTileEntityNeoforge::tick;
    }
}
