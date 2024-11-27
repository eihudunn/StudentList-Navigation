package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val position = intent.getIntExtra("position", -1)
        val student = intent.getSerializableExtra("student", StudentModel::class.java)

        val editName = findViewById<EditText>(R.id.edit_student_name)
        val editId = findViewById<EditText>(R.id.edit_student_id)
        val saveButton = findViewById<Button>(R.id.button_save)

        editName.setText(student?.studentName)
        editId.setText(student?.studentId)

        saveButton.setOnClickListener {
            val updatedName = editName.text.toString()
            val updatedId = editId.text.toString()
            val updatedStudent = StudentModel(updatedName, updatedId)
            val resultIntent = Intent()
            resultIntent.putExtra("position", position)
            resultIntent.putExtra("student", updatedStudent)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}