package stanwang.top.select.student

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import stanwang.top.select.R
import stanwang.top.select.common.Item
import stanwang.top.select.common.ItemController
import stanwang.top.select.common.network.CourseBean
import stanwang.top.select.common.network.RecordBean

class RecordItem(val bean : RecordBean,val map: Map<String,CourseBean>):Item {

    companion object Controller :ItemController{
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_record,parent,false)
            val cname = view.findViewById<TextView>(R.id.course_name)
            val tname = view.findViewById<TextView>(R.id.teacher_name)
            val score = view.findViewById<TextView>(R.id.score)
            return ViewHolder(view, tname, cname, score)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as RecordItem
            item.bean.cid.apply {
                val mBean = item.map[this]
                holder.apply {
                    tname.text=mBean?.tname
                    cname.text="授课教师：${mBean?.cname}"
                    score.text="成绩：${item.bean.grade}"
                }
            }
        }

    }

    override val controller: ItemController
        get() = Controller

    class ViewHolder(view:View,val tname:TextView,val cname:TextView,val score:TextView) : RecyclerView.ViewHolder(view)

}