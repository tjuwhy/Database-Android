package stanwang.top.select.student

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_student_home.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import stanwang.top.select.R
import stanwang.top.select.common.network.RxDoHttpClient
import stanwang.top.select.common.network.StudentBean
import stanwang.top.select.common.preference.MyPreference

class StudentHomeActivity : AppCompatActivity(){

    var studentBean: StudentBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)
        launch(UI) {
            RxDoHttpClient.api.getStudentBySid(MyPreference.studentNum).await().data?.apply {
                student_name.text = "夜深了，${this.sname}"
                add_year.text = "${addmission_year}级本科生"
                class_number.text = "$class_num"
                MyPreference.add_year= addmission_year
            }
        }
        my_course.setOnClickListener {
            startActivity(Intent(this,RecordActivity::class.java))
        }
        select.setOnClickListener {
            startActivity(Intent(this,SelectActivity::class.java))
        }
    }
}
