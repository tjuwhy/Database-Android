package stanwang.top.select.common.preference

import com.orhanobut.hawk.Hawk
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object MyPreference{
    var isLogin:Boolean by hawk("is_login",true)
    var userName:String by hawk("user_name","")
    var studentNum:String by hawk("student_num","")
    var add_year:Int by hawk("add_year",0)
}

fun <T> hawk(key: String, default: T) = object : ReadWriteProperty<Any?, T> {

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T = Hawk.get(key, default)

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        Hawk.put(key, value)
    }

}