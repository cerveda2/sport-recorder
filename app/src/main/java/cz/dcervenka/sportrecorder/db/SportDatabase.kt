package cz.dcervenka.sportrecorder.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Sport::class],
    version = 2,
    exportSchema = false
)

abstract class SportDatabase : RoomDatabase() {

    abstract fun getSportDao(): SportDao

}