package stanwang.top.select.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import stanwang.top.select.R
import stanwang.top.select.admin.AdminActivity
import stanwang.top.select.common.network.CommonBody
import stanwang.top.select.common.network.RxDoHttpClient
import stanwang.top.select.common.preference.MyPreference
import stanwang.top.select.student.StudentHomeActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener {
            if(login_username.text.toString().equals("admin")){
                startActivity(Intent(this,AdminActivity::class.java))
                return@setOnClickListener
            }
            launch(UI){
                val a:Deferred<CommonBody<String>> = RxDoHttpClient.api.login(login_username.text.toString(),login_password.text.toString())
                var res: CommonBody<String>? = a.await()
                res?.apply {
                    when(res.err_code){
                        "0" -> {
                            if (res.message=="Success"){
                                startActivity(Intent(this@LoginActivity,StudentHomeActivity::class.java))
                                MyPreference.studentNum=login_username.text.toString()
                            }

                        }
                        "-1" -> {
                            Toast.makeText(this@LoginActivity,res.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

