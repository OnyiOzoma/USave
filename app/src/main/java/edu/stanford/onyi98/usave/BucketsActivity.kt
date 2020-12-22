package edu.stanford.onyi98.usave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.stanford.onyi98.usave.models.Bucket

private const val TAG = "BucketsActivity"
class BucketsActivity : AppCompatActivity() {

    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buckets)

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