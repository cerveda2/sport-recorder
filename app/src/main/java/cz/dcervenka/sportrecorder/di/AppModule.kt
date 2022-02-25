package cz.dcervenka.sportrecorder.di

import android.content.Context
import androidx.room.Room
import cz.dcervenka.sportrecorder.db.SportDatabase
import cz.dcervenka.sportrecorder.other.Constants.SPORT_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSportDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        SportDatabase::class.java,
        SPORT_DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideSportDao(db: SportDatabase) = db.getSportDao()

}