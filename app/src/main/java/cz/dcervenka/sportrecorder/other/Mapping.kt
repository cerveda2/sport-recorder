package cz.dcervenka.sportrecorder.other

import cz.dcervenka.sportrecorder.db.Sport
import cz.dcervenka.sportrecorder.network.model.Document
import kotlin.collections.ArrayList

object Mapping {

    fun mapRemoteToLocal(list: List<Document>?) : List<Sport> {
        val delimiter = "/"

        val sportsToBeAdded = ArrayList<Sport>()
        if (list != null) {
            for (sport in list) {
                val names = sport.name?.split(delimiter)
                val s = Sport(
                    sport.fields.timestamp.integerValue,
                    sport.fields.name.stringValue,
                    sport.fields.place.stringValue,
                    sport.fields.duration.integerValue,
                    sport.fields.distance.integerValue,
                    SortType.REMOTE,
                    names?.get(names.size - 1)
                )
                sportsToBeAdded.add(s)
            }
        }
        return sportsToBeAdded
    }

    /*fun mapRemoteToLocal(list: List<SportRemote>?) : List<Sport> {
        val sportsToBeAdded = ArrayList<Sport>()
        if (list != null) {
            for (sport in list) {
                val s = Sport(
                    sport.timestamp,
                    sport.name,
                    sport.place,
                    sport.durationInMillis,
                    sport.distanceInMeters,
                    sport.storageType
                )
                sportsToBeAdded.add(s)
            }
        }
        return sportsToBeAdded
    }*/
    //val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", Locale.getDefault())
}