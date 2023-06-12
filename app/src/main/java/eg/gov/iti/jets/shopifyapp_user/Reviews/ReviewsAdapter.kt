package eg.gov.iti.jets.shopifyapp_user.Reviews

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eg.gov.iti.jets.shopifyapp_user.base.model.Review
import eg.gov.iti.jets.shopifyapp_user.databinding.ReviewItemBinding

class ReviewsAdapter(
    private var context: Context,
    private var reviewsList: List<Review>
): RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
    private lateinit var binding:ReviewItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ReviewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtViewReviewerName.text = reviewsList[position].reviewerName
        holder.binding.reviewRate.rating = reviewsList[position].rate.toFloat()
        holder.binding.reviewerComment.text = reviewsList[position].review
        Glide.with(context)
            .load(reviewsList[position].reviewerImg)
            .into(holder.binding.imgViewCategory)
    }

    override fun getItemCount()= reviewsList.size


    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(reviewsList: List<Review>) {
        this.reviewsList = reviewsList
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root)
}