package com.verdantartifice.verdantcore.common.theorycrafting;

import com.verdantartifice.verdantcore.common.util.WeightedRandomBag;
import com.verdantartifice.verdantcore.platform.ServicesVC;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Primary access point for theorycraft-related methods.
 * 
 * @author Daedalus4096
 */
public class TheorycraftManager {
    @Nonnull
    public static Project createRandomProject(@Nonnull Registry<ProjectTemplate> templateRegistry, @Nonnull ServerPlayer player, @Nonnull BlockPos tablePos) {
        WeightedRandomBag<ProjectTemplate> templateBag = new WeightedRandomBag<>();
        templateRegistry.stream().forEach(template -> templateBag.add(template, template.getWeight(player)));
        
        // Determine what blocks are nearby so that aid blocks can be checked
        Set<Block> nearby = new HashSet<>();
        Level level = player.level();
        if (ServicesVC.LEVEL.isAreaLoaded(level, tablePos, 5)) {
            Iterable<BlockPos> positions = BlockPos.betweenClosed(tablePos.offset(-5, -5, -5), tablePos.offset(5, 5, 5));
            for (BlockPos pos : positions) {
                nearby.add(level.getBlockState(pos).getBlock());
            }
        }
        
        Project retVal = null;
        int attempts = 0;   // Don't allow an infinite loop
        while (retVal == null && attempts < 1000) {
            attempts++;
            ProjectTemplate selectedTemplate = templateBag.getRandom(player.getRandom());
            Project initializedProject = selectedTemplate.initialize(player, nearby);
            
            // Only select the project if it initializes successfully
            if (initializedProject != null) {
                retVal = initializedProject;
            }
        }
        return retVal;
    }
    
    @Nonnull
    protected static Set<ResourceLocation> getAllAidBlockIds(@Nonnull Registry<ProjectTemplate> templateRegistry) {
        return templateRegistry.stream().flatMap(t -> t.aidBlocks().stream()).filter(Objects::nonNull).collect(Collectors.toSet());
    }
    
    @Nonnull
    public static Set<Block> getNearbyAidBlocks(@Nonnull Registry<ProjectTemplate> templateRegistry, Level level, BlockPos pos) {
        Set<ResourceLocation> allAids = getAllAidBlockIds(templateRegistry);
        return getSurroundingsInner(level, pos, b -> allAids.contains(ServicesVC.BLOCKS_REGISTRY.getKey(b)));
    }
    
    @Nonnull
    public static Set<Block> getSurroundings(Level level, BlockPos pos) {
        return getSurroundingsInner(level, pos, b -> true);
    }
    
    @Nonnull
    protected static Set<Block> getSurroundingsInner(Level level, BlockPos pos, Predicate<Block> filter) {
        Set<Block> retVal = new HashSet<>();
        
        if (ServicesVC.LEVEL.isAreaLoaded(level, pos, 5)) {
            Iterable<BlockPos> positions = BlockPos.betweenClosed(pos.offset(-5, -5, -5), pos.offset(5, 5, 5));
            for (BlockPos searchPos : positions) {
                Block block = level.getBlockState(searchPos).getBlock();
                if (filter.test(block)) {
                    retVal.add(block);
                }
            }
        }
        
        return retVal;
    }
}
