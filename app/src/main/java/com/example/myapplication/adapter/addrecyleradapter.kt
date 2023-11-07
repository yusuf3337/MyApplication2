import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Informations.AdInformationOne
import com.example.myapplication.adapter.addClass
import com.example.myapplication.databinding.AddReyclerViewBinding

class AddRecyclerAdapter(val context: Context, val addList: ArrayList<addClass>) : RecyclerView.Adapter<AddRecyclerAdapter.AddHolder>() {
    class AddHolder(val binding: AddReyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddHolder {
        val context = parent.context
        val binding = AddReyclerViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return AddHolder(binding)
    }

    override fun getItemCount(): Int {
        return addList.size
    }

    override fun onBindViewHolder(holder: AddHolder, position: Int) {
       holder.binding.addLabel.text = addList[position].addTitle


        val drawableResourceId = context.resources.getIdentifier(addList[position].addImage, "drawable", context.packageName)
        holder.binding.addImage.setImageResource(drawableResourceId)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AdInformationOne::class.java)
            intent.putExtra("Kategori", addList[position].addCategory)
            context.startActivity(intent)

        }



    }


}

