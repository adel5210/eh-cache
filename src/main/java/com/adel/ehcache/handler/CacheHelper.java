package com.adel.ehcache.handler;

import com.adel.ehcache.dto.Bar;
import com.adel.ehcache.dto.Foo;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Adel.Albediwy
 */

@Component
public class CacheHelper {
    public static final String FOO_CACHE = "fooCache";
    public static final String BAR_CACHE = "barCache";
    private final CacheManager cacheManager;
    private final PersistentCacheManager persistentCacheManager;
    private final Cache<String, Foo> fooCache;
    
    private final Cache<String, Bar> barCache;

    public CacheHelper() throws IOException {
        cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder()
                .build(true);

        fooCache = cacheManager.createCache(
                FOO_CACHE,
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        String.class, Foo.class,
                        ResourcePoolsBuilder.heap(10_000_000L)
                ));

        final Path path = Paths.get("C:/Users/Adel.Albediwy/Desktop/ehcache/");
        File ehcacheFile = path.toFile();
        if(Files.notExists(path)){
            ehcacheFile = Files.createDirectory(path).toFile();
        }
        persistentCacheManager = CacheManagerBuilder
                .newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(ehcacheFile))
                .build(true);
        
        barCache = persistentCacheManager.createCache(
                BAR_CACHE,
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        String.class, Bar.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(10_000_000L, EntryUnit.ENTRIES)
                                .disk(100L, MemoryUnit.MB, true)
                ));
    }

    public Cache<String, Foo> getFooCacheManager(){
        return cacheManager.getCache(FOO_CACHE, String.class, Foo.class);
    }

    public void putFoo(String key, Foo foo){
        fooCache.put(key, foo);
    }

    public Foo getFoo(String key){
        return fooCache.get(key);
    }

    public void putBar(String key, Bar bar){
        barCache.put(key, bar);
    }
    
    public Bar getBar(String key){
        return barCache.get(key);
    }

}
