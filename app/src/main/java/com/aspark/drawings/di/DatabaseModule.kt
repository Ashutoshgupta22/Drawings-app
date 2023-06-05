package com.aspark.drawings.di

import android.app.Application
import android.content.Context
import com.aspark.drawings.room.AppDatabase.Companion.getDatabase
import com.aspark.drawings.room.DrawingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object DatabaseModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideDrawingDao(context: Context): DrawingDao {

        return getDatabase(context).drawingDao()
    }
}