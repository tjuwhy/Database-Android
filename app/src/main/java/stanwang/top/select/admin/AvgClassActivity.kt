package stanwang.top.select.admin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_avg_class.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import stanwang.top.select.R
import stanwang.top.select.common.network.RxDoHttpClient
import stanwang.top.select.common.withItems

class AvgClassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avg_class)
        rec_class_avg.layoutManager = LinearLayoutManager(this)
        launch(UI) {
            RxDoHttpClient.api.getClassAvg().await().apply {
                when(err_code){
                    "0" -> {
                        data?.apply {
                            rec_class_avg.withItems(map { ClassAvgItem(this@AvgClassActivity,it) })
                        }
                    }
                    "-1" -> {
                        Toast.makeText(this@AvgClassActivity,message,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
