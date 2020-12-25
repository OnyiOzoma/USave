package edu.stanford.onyi98.usave

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.stanford.onyi98.usave.models.Bucket
import kotlinx.android.synthetic.main.item_bucket.view.*

/*
personally going through to recap what happened here.
ultimate goal was binding the content to the adapter from the profiles so that we can see.
to do this, we initialized that when the viewholder is created, we inflate the bucket item
that we sketched out so it can populate that.
Next, run a simple function to know the size. Then bind the corresponding bucket with the
position on the recycler view.
 */
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
/*
* The binding is done by setting the textView to pull the entered profile corresponding information
* then implementing glide via the github at glide android to be able to tie the image to it
*
* ONYI GO BACK TO ENSURE ALL PROPERTIES ARE ACCOUNTED FOR
*/
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

