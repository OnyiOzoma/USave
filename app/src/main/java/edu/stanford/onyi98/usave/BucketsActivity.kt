package edu.stanford.onyi98.usave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.stanford.onyi98.usave.models.Bucket
import kotlinx.android.synthetic.main.activity_buckets.*

private const val TAG = "BucketsActivity"
class BucketsActivity : AppCompatActivity() {

    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var buckets: MutableList<Bucket>
    private lateinit var adapter: BucketsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buckets)

        // Creating the layout file to represent one post - DONE
        // Create Data source - DONE
        buckets = mutableListOf()
        // Create the adapter
        adapter = BucketsAdapter(this, buckets)
        // Bind the adapter and layout manager to the rv
        rvBuckets.adapter = adapter
        rvBuckets.layoutManager = LinearLayoutManager(this)
        // make a query to Firestore to retrieve buckets data
        firestoreDb = FirebaseFirestore.getInstance()
        val bucketsReference = firestoreDb
            .collection("buckets")
            .limit(20) // limits the number of buckets that we get back
            .orderBy("goal_amount", Query.Direction.DESCENDING) // think about what attribute we want to order posts by, should users be able to control this?
        // snapshot listener - inform us when there is change in this collection
        bucketsReference.addSnapshotListener{snapshot, exception ->
            if(exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying buckets", exception)
                return@addSnapshotListener
            }
            val bucketList = snapshot.toObjects(Bucket::class.java)
            buckets.clear()
            buckets.addAll(bucketList)
            adapter.notifyDataSetChanged()
            for(bucket in bucketList){
                Log.i(TAG, "Bucket ${bucket}")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_buckets, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_profile){
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}