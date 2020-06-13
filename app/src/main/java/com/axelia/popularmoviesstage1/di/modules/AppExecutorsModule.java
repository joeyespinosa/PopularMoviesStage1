package com.axelia.popularmoviesstage1.di.modules;

import com.axelia.popularmoviesstage1.utils.AppExecutors;

import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppExecutorsModule {

    @Singleton
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3),
                new AppExecutors.MainThreadExecutor());
    }
}
