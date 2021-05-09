package com.chocowind.science

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance().reference

        context = this

        service()
    }

    private fun service() {
        database.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("MainActivity-a", snapshot.toString())
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("MainActivity-c", snapshot.toString())
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.d("MainActivity-r", snapshot.toString())
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("MainActivity-m", snapshot.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        val adapter = object : ArrayAdapter<Int>(
            context,
            R.layout.item,
            R.id.floor,
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val now = getItem(position)
                val floor = view.floor

                floor.text = now.toString()
                floor.setBackgroundResource(R.drawable.round_bg)

                floor.setOnClickListener {
                    database.child("current").setValue(position + 1)
                    Toast.makeText(context, "已按下 ${position + 1} 樓", Toast.LENGTH_SHORT).show()
                    floor.setBackgroundResource(R.drawable.round_bg_clicked)
                }

                return view
            }
        }

        grid_view.adapter = adapter

    }
}