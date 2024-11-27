package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )

  private lateinit var addStudentLauncher: ActivityResultLauncher<Intent>
  private lateinit var editStudentLauncher: ActivityResultLauncher<Intent>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)

    val studentListView = findViewById<ListView>(R.id.list_view_students)
    studentAdapter = StudentAdapter(this, students, ::onEditStudent, ::onDeleteStudent)
    studentListView.adapter = studentAdapter

    registerForContextMenu(studentListView)

    addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      if (result.resultCode == RESULT_OK) {
        val newStudent = result.data?.getSerializableExtra("student", StudentModel::class.java)
        if (newStudent != null) {
          students.add(newStudent)
        }
        studentAdapter.notifyDataSetChanged()
      }
    }

    editStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      if (result.resultCode == RESULT_OK) {
        val position = result.data?.getIntExtra("position", -1) ?: -1
        val updatedStudent = result.data?.getSerializableExtra("student", StudentModel::class.java)
        if (position != -1) {
          if (updatedStudent != null) {
            students[position] = updatedStudent
          }
          studentAdapter.notifyDataSetChanged()
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.add_new -> {
        val intent = Intent(this, AddStudentActivity::class.java)
        addStudentLauncher.launch(intent)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    return when (item.itemId) {
      R.id.edit -> {
        val intent = Intent(this, EditStudentActivity::class.java)
        intent.putExtra("position", info.position)
        intent.putExtra("student", students[info.position])
        editStudentLauncher.launch(intent)
        true
      }
      R.id.remove -> {
        val removedStudent = students.removeAt(info.position)
        studentAdapter.notifyDataSetChanged()
        Snackbar.make(findViewById(R.id.list_view_students), "Đã xóa sinh viên", Snackbar.LENGTH_LONG)
          .setAction("Undo") {
            students.add(info.position, removedStudent)
            studentAdapter.notifyDataSetChanged()
          }.show()
        true
      }
      else -> super.onContextItemSelected(item)
    }
  }

  private fun onEditStudent(student: StudentModel, position: Int) {
    val intent = Intent(this, EditStudentActivity::class.java)
    intent.putExtra("position", position)
    intent.putExtra("student", student)
    editStudentLauncher.launch(intent)
  }

  private fun onDeleteStudent(position: Int) {
    val removedStudent = students.removeAt(position)
    studentAdapter.notifyDataSetChanged()
    Snackbar.make(findViewById(R.id.list_view_students), "Đã xóa sinh viên", Snackbar.LENGTH_LONG)
      .setAction("Undo") {
        students.add(position, removedStudent)
        studentAdapter.notifyDataSetChanged()
      }.show()
  }
}