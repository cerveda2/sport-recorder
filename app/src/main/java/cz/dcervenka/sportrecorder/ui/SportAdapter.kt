package cz.dcervenka.sportrecorder.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.dcervenka.sportrecorder.R
import cz.dcervenka.sportrecorder.db.Sport
import cz.dcervenka.sportrecorder.other.SortType
import cz.dcervenka.sportrecorder.other.StringUtil.getFormattedDate
import cz.dcervenka.sportrecorder.other.StringUtil.getFormattedDuration

class SportAdapter internal constructor(
    private val context: Context,
    private val itemListener: ItemListener
) : RecyclerView.Adapter<SportAdapter.SportViewHolder>() {

    private val diffCallback = object  : DiffUtil.ItemCallback<Sport>() {
        override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Sport>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportViewHolder {
        return SportViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sport,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: SportViewHolder, position: Int) {
        val sport = differ.currentList[position]
        holder.bind(sport)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class SportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.tvName)
        private val date: TextView = itemView.findViewById(R.id.tvDate)
        private val place: TextView = itemView.findViewById(R.id.tvPlace)
        private val duration: TextView = itemView.findViewById(R.id.tvDuration)
        private val distance: TextView = itemView.findViewById(R.id.tvDistance)
        private val type: TextView = itemView.findViewById(R.id.tvType)


        fun bind(sport: Sport) {
            if (sport.storageType == SortType.REMOTE) {
                itemView.setBackgroundColor(context.getColor(R.color.grey))
            }
            itemView.setOnLongClickListener {
                itemListener.onLongClick(sport)
                true
            }
            date.text = getFormattedDate(sport.timestamp)
            name.text = sport.name
            place.text = sport.place
            val durationMins = getFormattedDuration(sport.durationInMillis)
            duration.text = durationMins
            val distanceMeters = "${sport.distanceInMeters} m"
            distance.text = distanceMeters
            type.text = sport.storageType.name
        }

    }

    interface ItemListener {
        fun onLongClick(sport: Sport)
    }

}