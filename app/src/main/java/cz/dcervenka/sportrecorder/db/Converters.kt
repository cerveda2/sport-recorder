package cz.dcervenka.sportrecorder.db

import androidx.room.TypeConverter
import cz.dcervenka.sportrecorder.other.SortType

class Converters {

    @TypeConverter
    fun toType(value: String) = enumValueOf<SortType>(value)

    @TypeConverter
    fun fromType(value: SortType) = value.name

}