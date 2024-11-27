package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val editName = findViewById<EditText>(R.id.edit_student_name)
        val editId = findViewById<EditText>(R.id.edit_student_id)
        val saveButton = findViewById<Button>(R.id.button_save)

        saveButton.setOnClickListener {
            val newName = editName.text.toString()
            val newId = editId.text.toString()
            val newStudent = StudentModel(newName, newId)
            val resultIntent = Intent()
            resultIntent.putExtra("student", newStudent)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}