import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalhub.R


class CustomAdapter(private val selectedItems: MutableList<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val selectedItemText: TextView = itemView.findViewById(R.id.selectedItem)
        val deleteItemButton: ImageView = itemView.findViewById(R.id.deleteItem)
        val firstText: EditText = itemView.findViewById(R.id.one)
        val secondText: TextView = itemView.findViewById(R.id.second)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedItem = selectedItems[position]
        holder.selectedItemText.text = selectedItem


        // Handle item deletion
        holder.deleteItemButton.setOnClickListener {
            selectedItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }


    override fun getItemCount(): Int {
        return selectedItems.size
    }

    // Function to get the selected items
    fun getSelectedItems(): List<String> {
        return selectedItems
    }
}