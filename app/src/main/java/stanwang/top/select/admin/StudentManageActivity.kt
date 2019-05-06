package stanwang.top.select.admin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_student_manage.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import stanwang.top.select.R
import stanwang.top.select.common.network.RxDoHttpClient
import stanwang.top.select.common.withItems

class StudentManageActivity : AppCompatActivity(),Refreshable {
    override fun refresh() {
        launch(UI) {
            RxDoHttpClient.api.getStudent().await().data?.apply {
                stu_man_rec.withItems(
                        map { StuManItem(this@StudentManageActivity,it) }
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_manage)
        icon_add.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val innerView = View.inflate(this,R.layout.view_edit_student,null)
            val esid :EditText = innerView. findViewById(R.id.edit_sid)
            var name: EditText = innerView.findViewById(R.id.edit_name)
            var gender: EditText = innerView.findViewById(R.id.edit_gender)
            var add_year: EditText = innerView.findViewById(R.id.edit_add_year)
            var add_age: EditText = innerView.findViewById(R.id.edit_add_age)
            var classNum: EditText = innerView.findViewById(R.id.edit_class)
            builder.setView(innerView)
            builder.setPositiveButton("确认添加"){_,_->
                if (esid.text.toString()!=""&&name.text.toString()!=""&&gender.text.toString()!=""&&add_year.text.toString()!=""&&add_age.text.toString()!=""&&classNum.text.toString()!=""){
                    launch(UI) {
                        RxDoHttpClient.api.insertStudent(esid.text.toString(),name.text.toString(),gender.text.toString(),add_age.text.toString(),add_year.text.toString(),classNum.text.toString()).await().apply {
                            when(err_code){
                                "0" ->{
                                    Toast.makeText(this@StudentManageActivity,"添加成功",Toast.LENGTH_SHORT).show()
                                    launch(UI) {
                                        RxDoHttpClient.api.getStudent().await().data?.apply {
                                            stu_man_rec.withItems(map { StuManItem(this@StudentManageActivity,it) })
                                        }
                                    }
                                }
                                "-1" -> {
                                    Toast.makeText(this@StudentManageActivity,"添加失败",Toast.LENGTH_SHORT).show()

                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this,"以上选项均为必填，请注意",Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("取消"){_,_->}
            builder.show()
        }
        icon_search.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            intent.putExtra("type","student")
            startActivity(intent)
        }
        stu_man_rec.layoutManager = LinearLayoutManager(this)
        launch(UI) {
            RxDoHttpClient.api.getStudent().await().data?.apply {
                stu_man_rec.withItems(map { StuManItem(this@StudentManageActivity,it) })
            }
        }
    }
}
