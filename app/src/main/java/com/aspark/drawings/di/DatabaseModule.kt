package com.aspark.drawings.di

import android.app.Application
import android.content.Context
import com.aspark.drawings.room.AppDatabase
import com.aspark.drawings.room.AppDatabase.Companion.getDatabase
import com.aspark.drawings.room.DrawingDao
import com.aspark.drawings.room.MarkerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return getDatabase(context)
    }

    @Provides
    fun provideDrawingDao(database: AppDatabase): DrawingDao {
        return database.drawingDao()
    }

    @Provides
    fun providerMarkerDao(database: AppDatabase): MarkerDao {
        return database.markerDao()
    }
}