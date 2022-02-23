package cz.dcervenka.sportrecorder.other

enum class SortType {
    ALL, LOCAL, REMOTE;

    companion object {
        private val map = values().associateBy(SortType::name)
        fun fromString(type: String) = map[type]
    }
}