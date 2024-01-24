import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.fat.R
import com.project.fat.data.store.StoreAvata
import com.project.fat.databinding.StoreViewBinding

class StorePagerAdapter(
    private var storeAvataList: MutableList<StoreAvata>,
    private val onSelectButtonClickListener: OnSelectButtonClickListener,
    private val onLockButtonClickListener : OnLockButtonClickListener
) : RecyclerView.Adapter<StorePagerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= StoreViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = storeAvataList[position]
        holder.bind(data)
        holder.binding.select.setOnClickListener {
            onSelectButtonClickListener.onSelectButtonClick(storeAvataList, position)
        }
        holder.binding.lock.setOnClickListener {
            onLockButtonClickListener.onLockButtonClickListener(data, position)
        }
    }

    override fun getItemCount(): Int = storeAvataList.size

    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(val binding : StoreViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoreAvata) {

            Glide.with(binding.root)
                .load(data.fatmanImage)
                .into(binding.fatmanImage)
            binding.fatmanName.text = data.fatmanName

            if(data.achieved){
                binding.select.visibility = View.VISIBLE
                binding.lockBackground.visibility = View.GONE
                binding.lock.visibility = View.GONE
            }else{
                binding.select.visibility = View.GONE
                binding.lockBackground.visibility = View.VISIBLE
                binding.lock.visibility = View.VISIBLE
            }

            if(data.selected){
                binding.select.setImageResource(R.drawable.selected_store_avata)
            }else{
                binding.select.setImageResource(R.drawable.default_store_avata)
            }
        }
    }

    interface OnSelectButtonClickListener {
        fun onSelectButtonClick(data: MutableList<StoreAvata>, position: Int)
    }

    interface OnLockButtonClickListener {
        fun onLockButtonClickListener(data: StoreAvata, position: Int)
    }
}
