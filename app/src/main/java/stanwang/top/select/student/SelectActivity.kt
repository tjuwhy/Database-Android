package stanwang.top.select.student

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_select.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import stanwang.top.select.R
import stanwang.top.select.common.network.RxDoHttpClient
import stanwang.top.select.common.preference.MyPreference
import stanwang.top.select.common.withItems

class SelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        sel_rec.layoutManager = LinearLayoutManager(this)
        launch(UI) {
            RxDoHttpClient.api.getCourseList().await().apply {
                when(err_code){
                    "0" -> {
                        data?.apply {
                            val a = filter {
                                it.suitable_grade==MyPreference.add_year
                            }
                            sel_rec.withItems(a.map { SelectItem(this@SelectActivity,it) })
                        }
                    }
                    "-1" -> {
                        Toast.makeText(this@SelectActivity,"ERROR",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}
