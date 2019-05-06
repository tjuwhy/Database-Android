package stanwang.top.select.student

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import stanwang.top.select.R
import stanwang.top.select.common.network.CourseBean
import stanwang.top.select.common.network.RxDoHttpClient
import stanwang.top.select.common.preference.MyPreference
import stanwang.top.select.common.withItems

class RecordActivity : AppCompatActivity() {

    private val map = mutableMapOf<String, CourseBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        rec_rec.layoutManager = LinearLayoutManager(this)
        launch(UI) {
            val records = RxDoHttpClient.api.getRecordBySid(MyPreference.studentNum)
            val course = RxDoHttpClient.api.getCourseList()
            course.await().data?.apply {
                forEach {
                    map[it.cid] = it
                }
                records.await().data?.apply {
                    rec_rec.withItems(map { RecordItem(it,map) })
                }
            }

        }
    }
}
