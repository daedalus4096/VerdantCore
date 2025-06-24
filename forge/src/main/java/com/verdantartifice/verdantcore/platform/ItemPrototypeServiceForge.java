package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.entities.pixies.PixieRank;
import com.verdantartifice.verdantcore.common.entities.pixies.companions.AbstractPixieEntity;
import com.verdantartifice.verdantcore.common.items.entities.PixieHouseItem;
import com.verdantartifice.verdantcore.common.items.misc.ArcanometerItem;
import com.verdantartifice.verdantcore.common.items.misc.ArcanometerItemForge;
import com.verdantartifice.verdantcore.common.items.misc.BurnableBlockItem;
import com.verdantartifice.verdantcore.common.items.misc.BurnableBlockItemForge;
import com.verdantartifice.verdantcore.common.items.misc.EarthshatterHammerItem;
import com.verdantartifice.verdantcore.common.items.misc.EarthshatterHammerItemForge;
import com.verdantartifice.verdantcore.common.items.misc.IgnyxItem;
import com.verdantartifice.verdantcore.common.items.misc.IgnyxItemForge;
import com.verdantartifice.verdantcore.common.items.misc.ManaFontBlockItem;
import com.verdantartifice.verdantcore.common.items.misc.ManaFontBlockItemForge;
import com.verdantartifice.verdantcore.common.items.misc.ManaInjectorBlockItem;
import com.verdantartifice.verdantcore.common.items.misc.ManaInjectorBlockItemForge;
import com.verdantartifice.verdantcore.common.items.misc.ManaRelayBlockItem;
import com.verdantartifice.verdantcore.common.items.misc.ManaRelayBlockItemForge;
import com.verdantartifice.verdantcore.common.items.misc.PixieHouseItemForge;
import com.verdantartifice.verdantcore.common.items.misc.PixieItemForge;
import com.verdantartifice.verdantcore.common.items.misc.SpellcraftingAltarBlockItem;
import com.verdantartifice.verdantcore.common.items.misc.SpellcraftingAltarBlockItemForge;
import com.verdantartifice.verdantcore.common.items.tools.ForbiddenBowItem;
import com.verdantartifice.verdantcore.common.items.tools.ForbiddenBowItemForge;
import com.verdantartifice.verdantcore.common.items.tools.ForbiddenTridentItem;
import com.verdantartifice.verdantcore.common.items.tools.ForbiddenTridentItemForge;
import com.verdantartifice.verdantcore.common.items.tools.HallowsteelShieldItem;
import com.verdantartifice.verdantcore.common.items.tools.HallowsteelShieldItemForge;
import com.verdantartifice.verdantcore.common.items.tools.HallowsteelTridentItem;
import com.verdantartifice.verdantcore.common.items.tools.HallowsteelTridentItemForge;
import com.verdantartifice.verdantcore.common.items.tools.HexiumShieldItem;
import com.verdantartifice.verdantcore.common.items.tools.HexiumShieldItemForge;
import com.verdantartifice.verdantcore.common.items.tools.HexiumTridentItem;
import com.verdantartifice.verdantcore.common.items.tools.HexiumTridentItemForge;
import com.verdantartifice.verdantcore.common.items.tools.ManaOrbItem;
import com.verdantartifice.verdantcore.common.items.tools.ManaOrbItemForge;
import com.verdantartifice.verdantcore.common.items.tools.PrimaliteShieldItem;
import com.verdantartifice.verdantcore.common.items.tools.PrimaliteShieldItemForge;
import com.verdantartifice.verdantcore.common.items.tools.PrimaliteTridentItem;
import com.verdantartifice.verdantcore.common.items.tools.PrimaliteTridentItemForge;
import com.verdantartifice.verdantcore.common.items.tools.SpelltomeItem;
import com.verdantartifice.verdantcore.common.items.tools.SpelltomeItemForge;
import com.verdantartifice.verdantcore.common.items.tools.TieredBowItem;
import com.verdantartifice.verdantcore.common.items.tools.TieredBowItemForge;
import com.verdantartifice.verdantcore.common.items.wands.ModularStaffItem;
import com.verdantartifice.verdantcore.common.items.wands.ModularStaffItemForge;
import com.verdantartifice.verdantcore.common.items.wands.ModularWandItem;
import com.verdantartifice.verdantcore.common.items.wands.ModularWandItemForge;
import com.verdantartifice.verdantcore.common.items.wands.MundaneWandItem;
import com.verdantartifice.verdantcore.common.items.wands.MundaneWandItemForge;
import com.verdantartifice.verdantcore.common.misc.DeviceTier;
import com.verdantartifice.verdantcore.common.sources.Source;
import com.verdantartifice.verdantcore.platform.services.IItemPrototypeService;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

public class ItemPrototypeServiceForge implements IItemPrototypeService {
    @Override
    public <T extends Block> Supplier<BurnableBlockItem> burnable(Supplier<T> blockSupplier, int burnTicks, Item.Properties properties) {
        return () -> new BurnableBlockItemForge(blockSupplier.get(), burnTicks, properties);
    }

    @Override
    public Supplier<ArcanometerItem> arcanometer() {
        return ArcanometerItemForge::new;
    }

    @Override
    public Supplier<EarthshatterHammerItem> earthshatterHammer() {
        return EarthshatterHammerItemForge::new;
    }

    @Override
    public Supplier<IgnyxItem> ignyx(Item.Properties properties) {
        return () -> new IgnyxItemForge(properties);
    }

    @Override
    public <T extends Block> Supplier<ManaFontBlockItem> manaFont(Supplier<T> blockSupplier, Item.Properties properties) {
        return () -> new ManaFontBlockItemForge(blockSupplier.get(), properties);
    }

    @Override
    public <T extends Block> Supplier<SpellcraftingAltarBlockItem> spellcraftingAltar(Supplier<T> blockSupplier, Item.Properties properties) {
        return () -> new SpellcraftingAltarBlockItemForge(blockSupplier.get(), properties);
    }

    @Override
    public <T extends Block> Supplier<ManaRelayBlockItem> manaRelay(Supplier<T> blockSupplier, Item.Properties properties) {
        return () -> new ManaRelayBlockItemForge(blockSupplier.get(), properties);
    }

    @Override
    public <T extends Block> Supplier<ManaInjectorBlockItem> manaInjector(Supplier<T> blockSupplier, Item.Properties properties) {
        return () -> new ManaInjectorBlockItemForge(blockSupplier.get(), properties);
    }

    @Override
    public Supplier<PixieHouseItem> pixieHouse(Item.Properties properties) {
        return () -> new PixieHouseItemForge(properties);
    }

    @Override
    public Supplier<PrimaliteShieldItem> primaliteShield(Item.Properties properties) {
        return () -> new PrimaliteShieldItemForge(properties);
    }

    @Override
    public Supplier<HexiumShieldItem> hexiumShield(Item.Properties properties) {
        return () -> new HexiumShieldItemForge(properties);
    }

    @Override
    public Supplier<HallowsteelShieldItem> hallowsteelShield(Item.Properties properties) {
        return () -> new HallowsteelShieldItemForge(properties);
    }

    @Override
    public Supplier<PrimaliteTridentItem> primaliteTrident(Item.Properties properties) {
        return () -> new PrimaliteTridentItemForge(properties);
    }

    @Override
    public Supplier<HexiumTridentItem> hexiumTrident(Item.Properties properties) {
        return () -> new HexiumTridentItemForge(properties);
    }

    @Override
    public Supplier<HallowsteelTridentItem> hallowsteelTrident(Item.Properties properties) {
        return () -> new HallowsteelTridentItemForge(properties);
    }

    @Override
    public Supplier<ForbiddenTridentItem> forbiddenTrident(Item.Properties properties) {
        return () -> new ForbiddenTridentItemForge(properties);
    }

    @Override
    public Supplier<TieredBowItem> tieredBow(Tier tier, Item.Properties properties) {
        return () -> new TieredBowItemForge(tier, properties);
    }

    @Override
    public Supplier<ForbiddenBowItem> forbiddenBow(Item.Properties properties) {
        return () -> new ForbiddenBowItemForge(properties);
    }

    @Override
    public Supplier<SpelltomeItem> spelltome(DeviceTier tier, Item.Properties properties) {
        return () -> new SpelltomeItemForge(tier, properties);
    }

    @Override
    public Supplier<ManaOrbItem> manaOrb(DeviceTier tier, Item.Properties properties) {
        return () -> new ManaOrbItemForge(tier, properties);
    }

    @Override
    public Supplier<MundaneWandItem> mundaneWand() {
        return MundaneWandItemForge::new;
    }

    @Override
    public Supplier<ModularWandItem> modularWand(Item.Properties properties) {
        return () -> new ModularWandItemForge(properties);
    }

    @Override
    public Supplier<ModularStaffItem> modularStaff(Item.Properties properties) {
        return () -> new ModularStaffItemForge(properties);
    }

    @Override
    public Supplier<SpawnEggItem> deferredSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Item.Properties props) {
        return () -> new ForgeSpawnEggItem(type, backgroundColor, highlightColor, props);
    }

    @Override
    public Supplier<SpawnEggItem> pixie(Supplier<EntityType<? extends AbstractPixieEntity>> typeSupplier, PixieRank rank, Source source, Item.Properties properties) {
        return () -> new PixieItemForge(typeSupplier, rank, source, properties);
    }
}
