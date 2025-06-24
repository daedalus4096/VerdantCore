package com.verdantartifice.verdantcore.platform.services;

import com.verdantartifice.verdantcore.common.entities.pixies.PixieRank;
import com.verdantartifice.verdantcore.common.entities.pixies.companions.AbstractPixieEntity;
import com.verdantartifice.verdantcore.common.items.entities.PixieHouseItem;
import com.verdantartifice.verdantcore.common.items.misc.ArcanometerItem;
import com.verdantartifice.verdantcore.common.items.misc.BurnableBlockItem;
import com.verdantartifice.verdantcore.common.items.misc.EarthshatterHammerItem;
import com.verdantartifice.verdantcore.common.items.misc.IgnyxItem;
import com.verdantartifice.verdantcore.common.items.misc.ManaFontBlockItem;
import com.verdantartifice.verdantcore.common.items.misc.ManaInjectorBlockItem;
import com.verdantartifice.verdantcore.common.items.misc.ManaRelayBlockItem;
import com.verdantartifice.verdantcore.common.items.misc.SpellcraftingAltarBlockItem;
import com.verdantartifice.verdantcore.common.items.tools.ForbiddenBowItem;
import com.verdantartifice.verdantcore.common.items.tools.ForbiddenTridentItem;
import com.verdantartifice.verdantcore.common.items.tools.HallowsteelShieldItem;
import com.verdantartifice.verdantcore.common.items.tools.HallowsteelTridentItem;
import com.verdantartifice.verdantcore.common.items.tools.HexiumShieldItem;
import com.verdantartifice.verdantcore.common.items.tools.HexiumTridentItem;
import com.verdantartifice.verdantcore.common.items.tools.ManaOrbItem;
import com.verdantartifice.verdantcore.common.items.tools.PrimaliteShieldItem;
import com.verdantartifice.verdantcore.common.items.tools.PrimaliteTridentItem;
import com.verdantartifice.verdantcore.common.items.tools.SpelltomeItem;
import com.verdantartifice.verdantcore.common.items.tools.TieredBowItem;
import com.verdantartifice.verdantcore.common.items.wands.ModularStaffItem;
import com.verdantartifice.verdantcore.common.items.wands.ModularWandItem;
import com.verdantartifice.verdantcore.common.items.wands.MundaneWandItem;
import com.verdantartifice.verdantcore.common.misc.DeviceTier;
import com.verdantartifice.verdantcore.common.sources.Source;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface IItemPrototypeService {
    <T extends Block> Supplier<BurnableBlockItem> burnable(Supplier<T> block, int burnTicks, Item.Properties properties);

    Supplier<ArcanometerItem> arcanometer();
    Supplier<EarthshatterHammerItem> earthshatterHammer();
    Supplier<IgnyxItem> ignyx(Item.Properties properties);

    <T extends Block> Supplier<ManaFontBlockItem> manaFont(Supplier<T> blockSupplier, Item.Properties properties);
    <T extends Block> Supplier<SpellcraftingAltarBlockItem> spellcraftingAltar(Supplier<T> blockSupplier, Item.Properties properties);
    <T extends Block> Supplier<ManaRelayBlockItem> manaRelay(Supplier<T> blockSupplier, Item.Properties properties);
    <T extends Block> Supplier<ManaInjectorBlockItem> manaInjector(Supplier<T> blockSupplier, Item.Properties properties);

    Supplier<PixieHouseItem> pixieHouse(Item.Properties properties);

    Supplier<PrimaliteShieldItem> primaliteShield(Item.Properties properties);
    Supplier<HexiumShieldItem> hexiumShield(Item.Properties properties);
    Supplier<HallowsteelShieldItem> hallowsteelShield(Item.Properties properties);

    Supplier<PrimaliteTridentItem> primaliteTrident(Item.Properties properties);
    Supplier<HexiumTridentItem> hexiumTrident(Item.Properties properties);
    Supplier<HallowsteelTridentItem> hallowsteelTrident(Item.Properties properties);
    Supplier<ForbiddenTridentItem> forbiddenTrident(Item.Properties properties);

    Supplier<TieredBowItem> tieredBow(Tier tier, Item.Properties properties);
    Supplier<ForbiddenBowItem> forbiddenBow(Item.Properties properties);

    Supplier<SpelltomeItem> spelltome(DeviceTier tier, Item.Properties properties);
    Supplier<ManaOrbItem> manaOrb(DeviceTier tier, Item.Properties properties);

    Supplier<MundaneWandItem> mundaneWand();
    Supplier<ModularWandItem> modularWand(Item.Properties properties);
    Supplier<ModularStaffItem> modularStaff(Item.Properties properties);

    Supplier<SpawnEggItem> deferredSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Item.Properties props);
    Supplier<SpawnEggItem> pixie(Supplier<EntityType<? extends AbstractPixieEntity>> typeSupplier, PixieRank rank, Source source, Item.Properties properties);
}
