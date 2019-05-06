package stanwang.top.select.admin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_avg_course.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import stanwang.top.select.R
import stanwang.top.select.common.network.RxDoHttpClient
import stanwang.top.select.common.withItems

class AvgCourseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avg_course)
        rec_course_avg.layoutManager = LinearLayoutManager(this)
        launch(UI) {
            RxDoHttpClient.api.getCourseAvg().await().apply {
                when(err_code){
                    "0"->{
                        data?.apply {
                            rec_course_avg.withItems(map { CourseAvgItem(this@AvgCourseActivity, it) })
                        }
                    }
                    "-1" ->{
                        Toast.makeText(this@AvgCourseActivity,message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}
