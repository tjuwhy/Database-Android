package stanwang.top.select.student

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import stanwang.top.select.R
import stanwang.top.select.common.Item
import stanwang.top.select.common.ItemController
import stanwang.top.select.common.network.CourseBean
import stanwang.top.select.common.network.RxDoHttpClient
import stanwang.top.select.common.preference.MyPreference

class SelectItem(val context: Context, val bean: CourseBean) : Item {

    companion object Controller : ItemController {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_select, parent, false)
            val cname = view.findViewById<TextView>(R.id.course_name)
            val tname = view.findViewById<TextView>(R.id.teacher_name)
            val credit = view.findViewById<TextView>(R.id.credit)
            return ViewHolder(view, cname, credit, tname)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as SelectItem
            holder.cname.text = item.bean.cname
            holder.credit.text = "学分：${item.bean.credit}"
            holder.tname.text = "授课教师：${item.bean.tname}"
            holder.itemView.setOnClickListener {
                val builder = AlertDialog.Builder(item.context)
                builder.setTitle("请确认选课信息：")
                builder.setMessage("课程名：${item.bean.cname} \n授课教师：${item.bean.tname}")
                builder.setPositiveButton("确认选课") { _, _ ->
                    val a = RxDoHttpClient.api.insertRecord(MyPreference.studentNum,item.bean.cid.toString())
                    launch(UI) {
                        when(a.await().err_code){
                            "0" -> {
                                Toast.makeText(item.context,"选课成功！",Toast.LENGTH_SHORT).show()
                            }
                            "-1" -> {
                                Toast.makeText(item.context,"选课失败",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                builder.setNegativeButton("取消") { _, _ ->

                }
                builder.show()
            }
        }

    }

    override val controller: ItemController
        get() = Controller

    class ViewHolder(itemView: View, val cname: TextView, val credit: TextView, val tname: TextView) : RecyclerView.ViewHolder(itemView)

}