package edu.stanford.onyi98.usave

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.stanford.onyi98.usave.models.Bucket
import kotlinx.android.synthetic.main.item_bucket.view.*

class BucketsAdapter (val context: Context, val buckets: List<Bucket>) :
        RecyclerView.Adapter<BucketsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BucketsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bucket, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = buckets.size

    override fun onBindViewHolder(holder: BucketsAdapter.ViewHolder, position: Int) {
        holder.bind(buckets[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bucket: Bucket) {
            itemView.tvBucketName.text = bucket.user?.username
            itemView.tvDescription.text = bucket.description
            Glide.with(context).load(bucket.image_url).into(itemView.ivBucket)
            //skipped code on the time in part 7 9:30. maybe come back to that
            // upon adding in the other text views for potentially the date of the bucket, etc
        }
    }
}

