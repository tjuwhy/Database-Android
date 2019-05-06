package stanwang.top.select.admin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import stanwang.top.select.R
import stanwang.top.select.common.network.RxDoHttpClient
import stanwang.top.select.common.withItems

class SearchActivity : AppCompatActivity() ,Refreshable {

    lateinit var type:String
    override fun refresh() {
        when (type) {
            "student" -> {
                edit_search.hint = "请输入学号，或输入#加姓名如 #张三 查询"
                icon_searchs.setOnClickListener {
                    val s = edit_search.text.trim().toString()
                    when (s[0]) {
                        '#' -> {
                            val name = s.substring(1)
                            launch(UI) {
                                RxDoHttpClient.api.getStudentBySname(name).await().apply {
                                    when (err_code) {
                                        "0" -> {
                                            ser_rec.withItems(data!!.map { StuManItem(this@SearchActivity, it) })
                                        }
                                        else -> {
                                            Toast.makeText(this@SearchActivity, message, Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                        else -> {
                            launch(UI) {
                                RxDoHttpClient.api.getStudentBySid(s).await().apply {
                                    when (err_code) {
                                        "0" -> {
                                            ser_rec.withItems(mutableListOf(StuManItem(this@SearchActivity, data!!)))
                                        }
                                        else -> {
                                            Toast.makeText(this@SearchActivity, message, Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            "course" -> {
                edit_search.hint = "请输入课程号，或输入#加课程名如 #体育 查询"
                icon_searchs.setOnClickListener {
                    val s = edit_search.text.trim().toString()
                    when (s[0]) {
                        '#' -> {
                            val name = s.substring(1)
                            launch(UI) {
                                RxDoHttpClient.api.getCourseByCname(name).await().apply {
                                    when (err_code) {
                                        "0" -> {
                                            ser_rec.withItems(data!!.map { CouManItem(this@SearchActivity, it) })
                                        }
                                        else -> {
                                            Toast.makeText(this@SearchActivity, message, Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                        else -> {
                            launch(UI) {
                                RxDoHttpClient.api.getCourseByCid(s).await().apply {
                                    when (err_code) {
                                        "0" -> {
                                            ser_rec.withItems(mutableListOf(CouManItem(this@SearchActivity, data!!)))
                                        }
                                        else -> {
                                            Toast.makeText(this@SearchActivity, message, Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            "record" -> {
                edit_search.hint = "请输入课程号(eg:cid=1.)，或输入学生号(eg:sid=3017218142/)"
                icon_searchs.setOnClickListener {
                    edit_search.text.toString().apply {
                        val a = indexOf("sid=")
                        val b = indexOf("/")
                        val c = indexOf("cid=")
                        val d = indexOf(".")
                        if (c != -1 && d != -1) {
                            val cid = substring(c+4, d)
                            launch(UI) {
                                RxDoHttpClient.api.getRecordByCid(cid).await().apply {
                                    when(err_code){
                                        "0"-> {
                                            data?.apply {
                                                ser_rec.withItems(map{RecManItem(this@SearchActivity,it)})
                                            }
                                        }
                                        "-1" -> {
                                            Toast.makeText(this@SearchActivity,message,Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }

                        } else if (c == -1 && a != -1) {
                            val sid = substring(a+4, b)
                            launch(UI) {
                                RxDoHttpClient.api.getRecordBySid(sid).await().apply {
                                    when(err_code){
                                        "0"-> {
                                            data?.apply {
                                                ser_rec.withItems(map{RecManItem(this@SearchActivity,it)})
                                            }
                                        }
                                        "-1" -> {
                                            Toast.makeText(this@SearchActivity,message,Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        ser_rec.layoutManager = LinearLayoutManager(this)
        type=intent.getStringExtra("type")
        when(type){
            "student" -> {
                edit_search.hint = "请输入学号，或输入#加姓名如 #张三 查询"
                icon_searchs.setOnClickListener {
                    val s = edit_search.text.trim().toString()
                    when(s[0]){
                        '#' -> {
                            val name = s.substring(1)
                            launch(UI){
                                RxDoHttpClient.api.getStudentBySname(name).await().apply {
                                    when(err_code){
                                        "0" ->{
                                            ser_rec.withItems(data!!.map { StuManItem(this@SearchActivity,it) })
                                        }
                                        else -> {
                                            Toast.makeText(this@SearchActivity,message,Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                        else -> {
                            launch (UI){
                                RxDoHttpClient.api.getStudentBySid(s).await().apply {
                                    when(err_code){
                                        "0" -> {
                                            ser_rec.withItems(mutableListOf(StuManItem(this@SearchActivity,data!!)))
                                        }
                                        else -> {
                                            Toast.makeText(this@SearchActivity,message,Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            "course" -> {
                edit_search.hint = "请输入课程号，或输入#加课程名如 #体育 查询"
                icon_searchs.setOnClickListener {
                    val s = edit_search.text.trim().toString()
                    when(s[0]){
                        '#' -> {
                            val name = s.substring(1)
                            launch(UI){
                                RxDoHttpClient.api.getCourseByCname(name).await().apply {
                                    when(err_code){
                                        "0" ->{
                                            ser_rec.withItems(data!!.map { CouManItem(this@SearchActivity,it) })
                                        }
                                        else -> {
                                            Toast.makeText(this@SearchActivity,message,Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                        else -> {
                            launch (UI){
                                RxDoHttpClient.api.getCourseByCid(s).await().apply {
                                    when(err_code){
                                        "0" -> {
                                            ser_rec.withItems(mutableListOf(CouManItem(this@SearchActivity,data!!)))
                                        }
                                        else -> {
                                            Toast.makeText(this@SearchActivity,message,Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            "record" -> {
                edit_search.hint = "请输入课程号(eg:cid1.)，或输入学生号(eg:sid3017218142/)"
                icon_searchs.setOnClickListener {
                    edit_search.text.toString().apply {
                        val a = indexOf("sid=")
                        val b = indexOf(".")
                        val c = indexOf("cid=")
                        val d = indexOf(".")
                        if (c != -1 && d != -1) {
                            val cid = substring(c+4, d)
                            launch(UI) {
                                RxDoHttpClient.api.getRecordByCid(cid).await().apply {
                                    when(err_code){
                                        "0"-> {
                                            data?.apply {
                                                ser_rec.withItems(map{RecManItem(this@SearchActivity,it)})
                                            }
                                        }
                                        "-1" -> {
                                            Toast.makeText(this@SearchActivity,message,Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }

                        }
                        if (b != -1 && a != -1) {
                            val sid = substring(a+4, b)
                            launch(UI) {
                                RxDoHttpClient.api.getRecordBySid(sid).await().apply {
                                    when(err_code){
                                        "0"-> {
                                            data?.apply {
                                                ser_rec.withItems(map{RecManItem(this@SearchActivity,it)})
                                            }
                                        }
                                        "-1" -> {
                                            Toast.makeText(this@SearchActivity,message,Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
