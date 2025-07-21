package com.verdantartifice.verdantcore.common.books;

import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.util.ResourceUtils;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Base datapack registry for languages in which mod books can be written/encoded.
 *
 * @author Daedalus4096
 */
public class BookLanguagesVC {
    // Register book languages
    public static final ResourceKey<BookLanguage> DEFAULT = create("default");
    public static final ResourceKey<BookLanguage> GALACTIC = create("galactic");
    public static final ResourceKey<BookLanguage> ILLAGER = create("illager");

    public static ResourceKey<BookLanguage> create(String name) {
        return ResourceKey.create(RegistryKeysVC.BOOK_LANGUAGES, ResourceUtils.loc(name));
    }

    public static void bootstrap(BootstrapContext<BookLanguage> context) {
        context.register(BookLanguagesVC.DEFAULT, new BookLanguage(ResourceUtils.loc("default"), Style.EMPTY.withFont(ResourceLocation.withDefaultNamespace("default")), 0, false));
        context.register(BookLanguagesVC.GALACTIC, new BookLanguage(ResourceUtils.loc("galactic"), Style.EMPTY.withFont(ResourceLocation.withDefaultNamespace("alt")), -1, false));
        context.register(BookLanguagesVC.ILLAGER, new BookLanguage(ResourceUtils.loc("illager"), Style.EMPTY.withFont(ResourceLocation.withDefaultNamespace("illageralt")), -1, false));
    }
}
