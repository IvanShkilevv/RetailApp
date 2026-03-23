package com.example.retailapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.retailapp.app.Constants
import com.example.retailapp.core.database.RetailDatabase
import com.example.retailapp.feature.common.data.cache.FavouriteProductsDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    fun provideFavouriteProductsDao(db: RetailDatabase): FavouriteProductsDAO =
        db.favouriteProductsDao()

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): RetailDatabase {
        return Room.databaseBuilder(context, RetailDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

}