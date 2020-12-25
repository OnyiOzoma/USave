package edu.stanford.onyi98.usave

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.stanford.onyi98.usave.models.Bucket
import edu.stanford.onyi98.usave.models.User
import kotlinx.android.synthetic.main.activity_buckets.*

private const val TAG = "BucketsActivity"
private const val EXTRA_USERNAME = "EXTRA_USERNAME"
open class BucketsActivity : AppCompatActivity() {

    private var signedInUser: User? = null
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

        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "signed in user: $signedInUser")
            }
            .addOnFailureListener { exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }
        var bucketsReference = firestoreDb
            .collection("buckets")
            .limit(20) // limits the number of buckets that we get back
            .orderBy("goal_amount", Query.Direction.DESCENDING) // think about what attribute we want to order posts by, should users be able to control this?

        // checking the intent to see if the username is null
        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            //set the title of that activity to the user's name
            supportActionBar?.title = username
            // set the new user
            bucketsReference = bucketsReference.whereEqualTo("user.username", username)
        }

        // snapshot listener - inform us when there is change in this collection
        bucketsReference . addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying buckets", exception)
                return@addSnapshotListener
            }
            val bucketList = snapshot.toObjects(Bucket::class.java)
            buckets.clear()
            buckets.addAll(bucketList)
            adapter.notifyDataSetChanged()
            for (bucket in bucketList) {
                Log.i(TAG, "Bucket ${bucket}")
            }
        }

        // simply creating an activity connected to the action button we made
        fabCreate.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_buckets, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_profile){
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, signedInUser?.username)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}