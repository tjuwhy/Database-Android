package stanwang.top.select.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import stanwang.top.select.R
import stanwang.top.select.login.LoginActivity
import stanwang.top.select.common.preference.MyPreference

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (!MyPreference.isLogin){
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            // TODO-判断学生还是admin进入不同的页面。
        }
    }
}
