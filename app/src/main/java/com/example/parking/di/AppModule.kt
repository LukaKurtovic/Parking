package com.example.parking.di

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import android.telephony.SmsManager
import androidx.room.Room
import com.example.parking.data.TicketDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, TicketDatabase::class.java, "ticket_database")
        .build()

    @Provides
    fun provideTaskDao(db: TicketDatabase) = db.getTicketDao()

    @Provides
    fun provideGeocoder(
        app: Application
    ) = Geocoder(app, Locale.getDefault())

    @Provides
    fun provideSmsManager(): SmsManager = SmsManager.getDefault()

    @Provides
    fun provideLocationManager(
        @ApplicationContext context: Context
    ): LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @Provides
    fun provideAlarmManager(
        @ApplicationContext context: Context
    ): AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
}