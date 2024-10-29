package com.example.searchinlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.searchinlist.data.Student
import java.util.Locale

class StudentAdapter(
    context: Context, private val studentList: List<Student>
) : ArrayAdapter<Student>(context, R.layout.adapter_layout, studentList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup) : View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.adapter_layout, parent, false)
        val student = studentList[position]
        val nameTextView = view.findViewById<TextView>(R.id.student_name)
        val idTextView = view.findViewById<TextView>(R.id.student_id)
        nameTextView.text = student.name
        idTextView.text = student.id.toString()
        return view

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<Student>()
                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(studentList)
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                    for (student in studentList) {
                        if (student.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(student)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                if (results?.values is List<*>) {
                    val filteredList = results.values as List<*>
                    addAll(filteredList.filterIsInstance<Student>())
                }
                notifyDataSetChanged()
            }
        }
    }
}