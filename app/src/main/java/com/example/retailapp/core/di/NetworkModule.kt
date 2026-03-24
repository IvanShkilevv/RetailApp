package com.example.retailapp.core.di

import com.example.retailapp.app.Constants.BASE_API_URL
import com.example.retailapp.app.Constants.TIMEOUT_SECONDS_CONNECT
import com.example.retailapp.app.Constants.TIMEOUT_SECONDS_READ
import com.example.retailapp.app.Constants.TIMEOUT_SECONDS_WRITE
import com.example.retailapp.feature.common.data.ProductsService
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Named("retrofit")
    @Singleton
    fun providesRetrofit(
        @Named("OkHttpClient") okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Named("OkHttpClient")
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS_CONNECT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS_WRITE, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesConverterFactory(typeAdapterFactory: TypeAdapterFactory): Converter.Factory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .registerTypeAdapterFactory(typeAdapterFactory)
                .setPrettyPrinting()
                .create()
        )
    }

    @Provides
    @Singleton
    fun providesTypeAdapterFactory(): TypeAdapterFactory {
        return object : TypeAdapterFactory {
            override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {
                val delegate = gson.getDelegateAdapter(this, type)
                val elementAdapter = gson.getAdapter(
                    JsonElement::class.java
                )
                return object : TypeAdapter<T>() {
                    @Throws(IOException::class)
                    override fun write(out: JsonWriter, value: T) {
                        delegate.write(out, value)
                    }

                    @Throws(IOException::class)
                    override fun read(`in`: JsonReader): T {
                        var jsonElement = elementAdapter.read(`in`)
                        if (jsonElement.isJsonObject) {
                            val jsonObject = jsonElement.asJsonObject
                            if (jsonObject.has("result") && jsonObject["result"].isJsonObject) {
                                jsonElement = jsonObject["result"]
                            }
                        }
                        return delegate.fromJsonTree(jsonElement)
                    }
                }.nullSafe()
            }
        }
    }

    @Provides
    @Singleton
    fun providesProductsService(@Named("retrofit") retrofit: Retrofit): ProductsService {
        return retrofit.create(ProductsService::class.java)
    }

}