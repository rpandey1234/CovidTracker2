package edu.stanford.rkpandey.covidtracker2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firestoreDb = FirebaseFirestore.getInstance()

        firestoreDb.collection("workers").addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Error retrieving worker info $exception")
                return@addSnapshotListener
            }
            var workersString = "WORKERS:\n"
            for (document in snapshot.documents) {
                val worker = document.toObject(Worker::class.java)
                Log.i(TAG, "Name: ${document.getString("name")}, role: ${document.getString("role")}")
                Log.i(TAG, "$worker")
                workersString += "${worker?.name}\n"
            }
            tvWorkers.text = workersString
        }
    }
}
