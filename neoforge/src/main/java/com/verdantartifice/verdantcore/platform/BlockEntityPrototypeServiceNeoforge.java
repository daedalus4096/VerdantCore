package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.tiles.crafting.AbstractCalcinatorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.crafting.CalcinatorTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.crafting.ConcocterTileEntity;
import com.verdantartifice.verdantcore.common.tiles.crafting.ConcocterTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.crafting.EssenceFurnaceTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.crafting.RunecarvingTableTileEntity;
import com.verdantartifice.verdantcore.common.tiles.crafting.RunecarvingTableTileEntityNeoforge;
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
import com.verdantartifice.verdantcore.common.tiles.devices.ResearchTableTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.ResearchTableTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.devices.SanguineCrucibleTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.SanguineCrucibleTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.devices.ScribeTableTileEntity;
import com.verdantartifice.verdantcore.common.tiles.devices.ScribeTableTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.mana.AutoChargerTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.AutoChargerTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaBatteryTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaBatteryTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaInjectorTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaInjectorTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaRelayTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.ManaRelayTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.mana.WandChargerTileEntity;
import com.verdantartifice.verdantcore.common.tiles.mana.WandChargerTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.misc.CarvedBookshelfTileEntity;
import com.verdantartifice.verdantcore.common.tiles.misc.CarvedBookshelfTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.rituals.OfferingPedestalTileEntity;
import com.verdantartifice.verdantcore.common.tiles.rituals.OfferingPedestalTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.rituals.RitualAltarTileEntity;
import com.verdantartifice.verdantcore.common.tiles.rituals.RitualAltarTileEntityNeoforge;
import com.verdantartifice.verdantcore.common.tiles.rituals.RitualLecternTileEntity;
import com.verdantartifice.verdantcore.common.tiles.rituals.RitualLecternTileEntityNeoforge;
import com.verdantartifice.verdantcore.platform.services.IBlockEntityPrototypeService;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityPrototypeServiceNeoforge implements IBlockEntityPrototypeService {
    @Override
    public BlockEntityType.BlockEntitySupplier<AbstractCalcinatorTileEntity> calcinator() {
        return CalcinatorTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<AbstractCalcinatorTileEntity> essenceFurnace() {
        return EssenceFurnaceTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<ConcocterTileEntity> concocter() {
        return ConcocterTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<RunecarvingTableTileEntity> runecarvingTable() {
        return RunecarvingTableTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<DesalinatorTileEntity> desalinator() {
        return DesalinatorTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<DissolutionChamberTileEntity> dissolutionChamber() {
        return DissolutionChamberTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<EssenceCaskTileEntity> essenceCask() {
        return EssenceCaskTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<EssenceTransmuterTileEntity> essenceTransmuter() {
        return EssenceTransmuterTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<HoneyExtractorTileEntity> honeyExtractor() {
        return HoneyExtractorTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<InfernalFurnaceTileEntity> infernalFurnace() {
        return InfernalFurnaceTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<ResearchTableTileEntity> researchTable() {
        return ResearchTableTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<SanguineCrucibleTileEntity> sanguineCrucible() {
        return SanguineCrucibleTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<ScribeTableTileEntity> scribeTable() {
        return ScribeTableTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<AutoChargerTileEntity> autoCharger() {
        return AutoChargerTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<ManaBatteryTileEntity> manaBattery() {
        return ManaBatteryTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<ManaInjectorTileEntity> manaInjector() {
        return ManaInjectorTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<ManaRelayTileEntity> manaRelay() {
        return ManaRelayTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<WandChargerTileEntity> wandCharger() {
        return WandChargerTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<CarvedBookshelfTileEntity> carvedBookshelf() {
        return CarvedBookshelfTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<OfferingPedestalTileEntity> offeringPedestal() {
        return OfferingPedestalTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<RitualAltarTileEntity> ritualAltar() {
        return RitualAltarTileEntityNeoforge::new;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<RitualLecternTileEntity> ritualLectern() {
        return RitualLecternTileEntityNeoforge::new;
    }
}
