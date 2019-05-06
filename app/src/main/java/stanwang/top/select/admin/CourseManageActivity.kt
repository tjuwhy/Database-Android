package stanwang.top.select.admin

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_course_manage.*
import kotlinx.android.synthetic.main.activity_student_manage.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import stanwang.top.select.R
import stanwang.top.select.common.network.RxDoHttpClient
import stanwang.top.select.common.withItems

class CourseManageActivity : AppCompatActivity(),Refreshable {
    override fun refresh() {
        launch(UI) {
            RxDoHttpClient.api.getCourseList().await().data?.apply {
                cou_man_rec.withItems(map { CouManItem(this@CourseManageActivity,it) })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_manage)
        cou_man_rec.layoutManager=LinearLayoutManager(this)
        launch(UI) {
            RxDoHttpClient.api.getCourseList().await().data?.apply {
                cou_man_rec.withItems(map { CouManItem(this@CourseManageActivity,it) })
            }
        }
        icon_search_c.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            intent.putExtra("type","course")
            startActivity(intent)
        }
        icon_add_c.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("增加课程")
            val view = View.inflate(this,R.layout.view_edit_course,null)
            val cids = view.findViewById<EditText>(R.id.edit_sid)
            val cnames = view.findViewById<EditText>(R.id.edit_cname)
            val tnames = view.findViewById<EditText>(R.id.edit_tname)
            val suitable_grades = view.findViewById<EditText>(R.id.edit_suitable_grade)
            val credits = view.findViewById<EditText>(R.id.edit_credit)
            builder.setView(view)
            builder.setPositiveButton("确定添加"){_,_->
                if (cids.text.toString()!=""&&cnames.text.toString()!=""&&tnames.text.toString()!=""&&suitable_grades.text.toString()!=""&&credits.text.toString()!=""){
                    launch(UI) {
                        RxDoHttpClient.api.insertCourse(cids.text.toString(),cnames.text.toString(),tnames.text.toString(),credits.text.toString(),suitable_grades.text.toString()).await().apply {
                            when(err_code){
                                "0" -> {
                                    Toast.makeText(this@CourseManageActivity,"添加成功",Toast.LENGTH_SHORT).show()
                                    launch(UI) {
                                        RxDoHttpClient.api.getCourseList().await().data?.apply {
                                            cou_man_rec.withItems(map { CouManItem(this@CourseManageActivity,it) })
                                        }
                                    }
                                }
                                "-1" -> {
                                    Toast.makeText(this@CourseManageActivity,message,Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this,"以上选项均为必填，请检查", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}