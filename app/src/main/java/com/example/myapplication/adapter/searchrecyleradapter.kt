import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.searchClass
import com.example.myapplication.databinding.SearchReyclerViewBinding

class SearchRecyclerAdapter(val context: Context, val searchList: ArrayList<searchClass>) : RecyclerView.Adapter<SearchRecyclerAdapter.SearchHolder>() {
    class SearchHolder(val binding: SearchReyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val context = parent.context
        val binding = SearchReyclerViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return SearchHolder(binding)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.binding.searchLabel.text = searchList[position].searchTitle


        val drawableResourceId = context.resources.getIdentifier(searchList[position].searchImage, "drawable", context.packageName)
        holder.binding.searchImage.setImageResource(drawableResourceId)
    }
}
