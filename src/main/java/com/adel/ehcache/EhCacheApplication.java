package com.adel.ehcache;

import com.adel.ehcache.dto.Bar;
import com.adel.ehcache.dto.Foo;
import com.adel.ehcache.handler.CacheHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.util.StopWatch;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootApplication
public class EhCacheApplication implements CommandLineRunner {
    public static final int END_EXCLUSIVE = 1_000_000;
    private final CacheHelper cacheHelper;

    public EhCacheApplication(CacheHelper cacheHelper) {
        this.cacheHelper = cacheHelper;
    }

    @Override
    public void run(final String... args) throws Exception {
        final String fooTime = runFoo();
        final String barTime = runBar();
        System.out.println(fooTime);
        System.out.println(barTime);
    }

    private String runBar() {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start("BAR");
        IntStream.range(1, END_EXCLUSIVE)
                .forEach(i -> {
                    final String id = String.valueOf(i);
                    cacheHelper.putBar(id,
                            new Bar(id, UUID.randomUUID().toString())
                    );
                });
        IntStream.range(1, END_EXCLUSIVE)
                .forEach(i -> {
//                    System.out.println(cacheHelper.getBar(String.valueOf(i)));
                });
        stopWatch.stop();
        return stopWatch.prettyPrint();
    }

    private String runFoo() {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start("FOO");
        IntStream.range(1, END_EXCLUSIVE)
                .forEach(i -> {
                    final String id = String.valueOf(i);
                    cacheHelper.putFoo(id,
                            new Foo(id, UUID.randomUUID().toString())
                    );
                });
        IntStream.range(1, END_EXCLUSIVE)
                .forEach(i -> {
//                    System.out.println(cacheHelper.getFoo(String.valueOf(i)));
                });
        stopWatch.stop();
        return stopWatch.prettyPrint();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(EhCacheApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
