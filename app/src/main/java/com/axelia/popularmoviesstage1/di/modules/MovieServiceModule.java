package com.axelia.popularmoviesstage1.di.modules;

import com.axelia.popularmoviesstage1.data.remote.api.ApiClient;
import com.axelia.popularmoviesstage1.data.remote.api.AuthInterceptor;
import com.axelia.popularmoviesstage1.data.remote.api.MovieService;
import com.axelia.popularmoviesstage1.utils.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MovieServiceModule {

    @Singleton
    @Provides
    MovieService provideMovieService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new AuthInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(MovieService.class);
    }
}
