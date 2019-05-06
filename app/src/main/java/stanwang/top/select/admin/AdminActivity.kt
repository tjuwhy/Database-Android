package stanwang.top.select.admin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_admin.*
import stanwang.top.select.R

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        student_manage.setOnClickListener {
            startActivity(Intent(this,StudentManageActivity::class.java))
        }
        course_manage.setOnClickListener {
            startActivity(Intent(this,CourseManageActivity::class.java))
        }
        record_manage.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            intent.putExtra("type","record")
            startActivity(intent)
        }
        class_avg.setOnClickListener {
            startActivity(Intent(this,AvgClassActivity::class.java))
        }
        course_avg.setOnClickListener {
            startActivity(Intent(this,AvgCourseActivity::class.java))
        }
    }
}
