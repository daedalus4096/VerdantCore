package com.verdantartifice.verdantcore.common.books;

import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Container for mod lexicons.
 * 
 * @author Daedalus4096
 */
public class LexiconManager {
    public static final ResourceLocation LOREM_IPSUM = ResourceUtils.loc("lorem_ipsum");
    protected static final Map<ResourceLocation, Lexicon> REGISTRY = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();
    
    public static void setLexicon(ResourceLocation id, Lexicon lexicon) {
        if (REGISTRY.containsKey(id)) {
            LOGGER.info("Replacing lexicon for {} with {} entries", id.toString(), lexicon.size());
        } else {
            LOGGER.info("Registered {} lexicon with {} entries", id.toString(), lexicon.size());
        }
        REGISTRY.put(id, lexicon);
    }
    
    public static Optional<Lexicon> getLexicon(ResourceLocation id) {
        return Optional.ofNullable(REGISTRY.get(id));
    }
    
    public static Collection<Lexicon> getAllLexicons() {
        return Collections.unmodifiableCollection(REGISTRY.values());
    }
}
