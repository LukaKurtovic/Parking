package com.example.parking.di

import android.app.Application
import android.location.Geocoder
import androidx.room.Room
import com.example.parking.data.TicketDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, TicketDatabase::class.java, "ticket_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideTaskDao(db: TicketDatabase) = db.getTicketDao()

    @Provides
    fun provideGeocoder(
        app: Application
    ) = Geocoder(app, Locale.getDefault())
}