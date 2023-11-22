import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Home
import com.example.myapplication.adapter.homeClass
import com.example.myapplication.databinding.HomeReyclerViewBinding

class HomeRecyclerAdapter(val context: Context, val homeList: ArrayList<homeClass>) : RecyclerView.Adapter<HomeRecyclerAdapter.HomeHolder>() {
    class HomeHolder(val binding: HomeReyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val context = parent.context
        val binding = HomeReyclerViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return HomeHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.binding.homeLabel.text = homeList[position].homeTitle
        holder.binding.homeLabel.text = homeList[position].homeCategory
        holder.binding.homeLabel.text = homeList[position].homeImage
    }
}
