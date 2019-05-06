package stanwang.top.select.admin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.w3c.dom.Text
import stanwang.top.select.R
import stanwang.top.select.common.Item
import stanwang.top.select.common.ItemController
import stanwang.top.select.common.network.*

class StuManItem(val con:Context,val bean: StudentBean): Item {

    companion object Controller:ItemController{
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_stu_man,parent,false)
            val textView = view.findViewById<TextView>(R.id.stu_info)
            val imageView = view.findViewById<ImageView>(R.id.edit)
            val del = view.findViewById<ImageView>(R.id.delete)
            val avg = view.findViewById<TextView>(R.id.average)
            return ViewHolder(view,textView,imageView,del,avg)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as StuManItem
            item.bean.apply {
                holder.text.text = "$sid $sname $gender  ${class_num}班"
            }
            launch(UI) {
                RxDoHttpClient.api.getAverageSid(item.bean.sid).await().data?.apply {
                    val s = substring(0,indexOf('.')+2)
                    holder.avg.text = "平均成绩：$s"
                }
            }
            holder.edit.setOnClickListener {
                val builder = AlertDialog.Builder(item.con)
                builder.setMessage("修改学号为${item.bean.sid}的同学")
                val innerView = View.inflate(item.con,R.layout.view_edit_student,null)
                val l:LinearLayout = innerView.findViewById(R.id.l_sid)
                l.visibility = View.GONE
                val name:EditText = innerView.findViewById(R.id.edit_name)
                val gender:EditText = innerView.findViewById(R.id.edit_gender)
                val add_year:EditText = innerView.findViewById(R.id.edit_add_year)
                val add_age:EditText = innerView.findViewById(R.id.edit_add_age)
                val classNum:EditText = innerView.findViewById(R.id.edit_class)
                item.bean.apply {
                    name.hint = sname
                    gender.hint = this.gender
                    add_age.hint = addmission_age.toString()
                    add_year.hint = addmission_year.toString()
                    classNum.hint = class_num.toString()
                }
                builder.setView(innerView)
                builder.setPositiveButton("确认修改"){_,_->
                    val names:String = name.text.toString()
                    val genders:String = gender.text.toString()
                    val ayears:String = add_year.text.toString()
                    val aages:String = add_age.text.toString()
                    val classs:String = classNum.text.toString()
                    if (names!=""){
                        launch(UI) {
                            RxDoHttpClient.api.updateSname(item.bean.sid,names).await().apply {
                                when(err_code){
                                    "0" -> {
                                        Toast.makeText(item.con,"修改成功",Toast.LENGTH_SHORT).show()
                                    }
                                    "-1" -> {
                                        Toast.makeText(item.con,message,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                    if (genders!=""){
                        launch(UI) {
                            RxDoHttpClient.api.updateGender(item.bean.sid,genders.trim()).await().apply {
                                when(err_code){
                                    "0" -> {
                                        Toast.makeText(item.con,"修改成功",Toast.LENGTH_SHORT).show()
                                    }
                                    "-1" -> {
                                        Toast.makeText(item.con,message,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                    if (ayears!=""){
                        launch(UI) {
                            RxDoHttpClient.api.updateAddYear(item.bean.sid,ayears).await().apply {
                                when(err_code){
                                    "0" -> {
                                        Toast.makeText(item.con,"修改成功",Toast.LENGTH_SHORT).show()
                                    }
                                    "-1" -> {
                                        Toast.makeText(item.con,message,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                    if (aages!=""){
                        launch(UI) {
                            RxDoHttpClient.api.updateAddAge(item.bean.sid,aages).await().apply {
                                when(err_code){
                                    "0" -> {
                                        Toast.makeText(item.con,"修改成功",Toast.LENGTH_SHORT).show()
                                    }
                                    "-1" -> {
                                        Toast.makeText(item.con,message,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                    if (classs!=""){
                        launch(UI) {
                            RxDoHttpClient.api.updateClass(item.bean.sid,classs).await().apply {
                                when(err_code){
                                    "0" -> {
                                        Toast.makeText(item.con,"修改成功",Toast.LENGTH_SHORT).show()
                                    }
                                    "-1" -> {
                                        Toast.makeText(item.con,message,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                    (item.con as Refreshable).refresh()
                }
                builder.show()
            }
            holder.del.setOnClickListener {
                val builder = AlertDialog.Builder(item.con)
                builder.setMessage("确认删除学号为${item.bean.sid}，姓名为${item.bean.sname}的学生吗？")
                builder.setPositiveButton("确认"){_,_->
                    launch(UI) {
                        RxDoHttpClient.api.deleteStudent(item.bean.sid).await().apply {

                            when(err_code){
                                "0" -> {
                                    Toast.makeText(item.con,"删除成功",Toast.LENGTH_SHORT).show()
                                    (item.con as Refreshable).refresh()
                                }
                                "-1" -> {
                                    Toast.makeText(item.con,message,Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                builder.setNegativeButton("取消"){_,_->}
                builder.show()
            }
        }


    }

    override val controller: ItemController
        get() = Controller

    class ViewHolder(view:View,val text:TextView,val edit:ImageView,val del:ImageView,val avg:TextView) :RecyclerView.ViewHolder(view)
}

class CouManItem(val con:Context,val bean: CourseBean): Item {

    companion object Controller:ItemController{
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_stu_man,parent,false)
            val hide = view.findViewById<TextView>(R.id.average)
            val textView = view.findViewById<TextView>(R.id.stu_info)
            val imageView = view.findViewById<ImageView>(R.id.edit)
            val del = view.findViewById<ImageView>(R.id.delete)
            return ViewHolder(view,textView,imageView,del,hide)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as CouManItem
            item.bean.apply {
                holder.text.text = "$cid $cname 教师：$tname 学分：${credit}"
            }
            holder.edit.setOnClickListener {
                val builder = AlertDialog.Builder(item.con)
                builder.setMessage("修改课程编号为${item.bean.cid}的课程")
                val view = View.inflate(item.con,R.layout.view_edit_course,null)
                val l_cid = view.findViewById<LinearLayout>(R.id.l_cid)
                val cnames = view.findViewById<EditText>(R.id.edit_cname)
                val tnames = view.findViewById<EditText>(R.id.edit_tname)
                val suitable_grades = view.findViewById<EditText>(R.id.edit_suitable_grade)
                val credit = view.findViewById<EditText>(R.id.edit_credit)
                l_cid.visibility=View.GONE
                item.bean.apply {
                    cnames.hint = this.cname
                    tnames.hint = this.tname
                    suitable_grades.hint = suitable_grade.toString()
                    credit.hint = this.credit.toString()
                }
                builder.setView(view)
                builder.setPositiveButton("确认修改"){_,_ ->
                    val cname = cnames.text.toString()
                    val tname = tnames.text.toString()
                    val suitable = suitable_grades.text.toString()
                    val cre = credit.text.toString()
                    if (cname!=""){
                        launch(UI) {
                            RxDoHttpClient.api.updateCName(item.bean.cid,cname).await().apply {
                                when(err_code){
                                    "0" -> {
                                        Toast.makeText(item.con,"修改成功",Toast.LENGTH_SHORT).show()
                                    }
                                    "-1" -> {
                                        Toast.makeText(item.con,message,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                    if (tname!=""){
                        launch(UI) {
                            RxDoHttpClient.api.updateTName(item.bean.cid,tname).await().apply {
                            when(err_code){
                                    "0" -> {
                                        Toast.makeText(item.con,"修改成功",Toast.LENGTH_SHORT).show()
                                    }
                                    "-1" -> {
                                        Toast.makeText(item.con,message,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                    if (suitable!=""){
                        launch(UI) {
                            RxDoHttpClient.api.updateSG(item.bean.cid,suitable).await().apply {
                                when(err_code){
                                    "0" -> {
                                        Toast.makeText(item.con,"修改成功",Toast.LENGTH_SHORT).show()
                                    }
                                    "-1" -> {
                                        Toast.makeText(item.con,message,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                    if (cre!=""){
                        launch(UI) {
                            RxDoHttpClient.api.updateCredit(item.bean.cid,cre).await().apply {
                                when(err_code){
                                    "0" -> {
                                        Toast.makeText(item.con,"修改成功",Toast.LENGTH_SHORT).show()
                                    }
                                    "-1" -> {
                                        Toast.makeText(item.con,message,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                    builder.show()
                    (item.con as Refreshable).refresh()
                }
            }
            holder.del.setOnClickListener {
                val builder = AlertDialog.Builder(item.con)
                builder.setMessage("确认删除编号为${item.bean.cid}，课程名为${item.bean.cname}的课程吗？")
                builder.setPositiveButton("确认"){_,_->
                    launch(UI) {
                        RxDoHttpClient.api.deleteCourse(item.bean.cid).await().apply {
                            when(err_code){
                                "0" -> {
                                    Toast.makeText(item.con,"删除成功",Toast.LENGTH_SHORT).show()
                                    (item.con as Refreshable).refresh()
                                }
                                "-1" -> {
                                    Toast.makeText(item.con,message,Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                builder.setNegativeButton("取消"){_,_->}
                builder.show()
            }
            holder.hide.visibility=View.GONE
        }

    }

    override val controller: ItemController
        get() = Controller

    class ViewHolder(view:View,val text:TextView,val edit:ImageView,val del:ImageView,val hide:TextView) :RecyclerView.ViewHolder(view)
}

class RecManItem(val con: Context,val bean: RecordBean):Item{

    override val controller: ItemController
        get() = Controller

    companion object Controller:ItemController{
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stu_man,parent,false)
            val cname:TextView = view.findViewById(R.id.stu_info)
            val score:TextView = view.findViewById(R.id.average)
            val edit:ImageView = view.findViewById(R.id.edit)
            val del:ImageView = view.findViewById(R.id.delete)
            return ViewHolder(view,cname,score,edit,del)
        }


        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as RecManItem
            holder.apply {
                launch(UI) {
                    info.text = "课程：${RxDoHttpClient.api.getCourseByCid(item.bean.cid).await().data?.cname}  学生：${RxDoHttpClient.api.getStudentBySid(item.bean.sid).await().data?.sname}"
                    grade.text = "分数：${item.bean.grade}"
                }
                val builder = AlertDialog.Builder(item.con)
                builder.setMessage("修改选课信息：")
                val view = View.inflate(item.con,R.layout.view_edit_record,null)
                val sel_years:EditText = view.findViewById(R.id.edit_sel_year)
                val scores :EditText = view.findViewById(R.id.edit_score)
                item.bean.apply {
                    scores.hint = grade.toString()
                }
                builder.setView(view)
                builder.setPositiveButton("确认修改"){_,_->
                    launch(UI) {
                        if(sel_years.text.toString()!=""){
                            RxDoHttpClient.api.updateSelectYear(item.bean.sid,item.bean.cid,sel_years.text.toString()).await().apply {
                                when(err_code){
                                    "0" -> {
                                        Toast.makeText(item.con,"修改成功",Toast.LENGTH_SHORT).show()
                                    }
                                    "-1" -> {
                                        Toast.makeText(item.con,message,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                        if (scores.text.toString()!=""){
                            RxDoHttpClient.api.updateGrade(item.bean.sid,item.bean.cid,scores.text.toString()).await().apply {
                                when(err_code){
                                    "0" -> {
                                        Toast.makeText(item.con,"修改成功",Toast.LENGTH_SHORT).show()
                                    }
                                    "-1" -> {
                                        Toast.makeText(item.con,message,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                }
                builder.setNegativeButton("取消"){_,_->}
                edit.setOnClickListener {
                    builder.show()
                }
                val builder1 = AlertDialog.Builder(item.con)
                builder1.setMessage("确认删除这条记录吗？")
                builder1.setPositiveButton("确认"){_,_->
                    launch(UI) {
                        RxDoHttpClient.api.deleteRecord(item.bean.sid,item.bean.cid).await().apply {
                            when(err_code){
                                "0" -> {
                                    Toast.makeText(item.con,"删除成功",Toast.LENGTH_SHORT).show()
//                                            (item.con as Refreshable).refresh()
                                }
                                "-1" -> {
                                    Toast.makeText(item.con,message,Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                builder1.setNegativeButton("取消"){_,_->}
                del.setOnClickListener {
                    builder1.show()
                }
            }
        }
    }


    class ViewHolder(view: View,val info:TextView,val grade:TextView,val edit:ImageView,val del:ImageView):RecyclerView.ViewHolder(view)
}

class ClassAvgItem(val con:Context,val bean: ClassAverageBean):Item {

    companion object Controller:ItemController{
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_record,parent,false)
            val classText = view.findViewById<TextView>(R.id.course_name)
            val hide = view.findViewById<TextView>(R.id.teacher_name)
            val score = view.findViewById<TextView>(R.id.score)
            return ViewHolder(view,classText,hide,score)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as ClassAvgItem
            holder.apply {
                hide.visibility = View.GONE
                classTextView.text = "${item.bean.class_num} 班"
                score.text = "${item.bean.grade}"
            }
        }

    }

    class ViewHolder(view:View,val classTextView: TextView,val hide: TextView,val score:TextView):RecyclerView.ViewHolder(view)

    override val controller: ItemController
        get() = Controller //To change initializer of created properties use File | Settings | File Templates.

}

class CourseAvgItem(val con: Context,val bean: CourseAverageBean):Item{




    companion object Controller:ItemController{



        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_record,parent,false)
            val classText = view.findViewById<TextView>(R.id.course_name)
            val hide = view.findViewById<TextView>(R.id.teacher_name)
            val score = view.findViewById<TextView>(R.id.score)
            return ViewHolder(view,classText,hide,score)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as CourseAvgItem
            holder.apply {
                cid.text = "cid:${item.bean.cid}"
                classTextView.text = item.bean.cname
                score.text = "${item.bean.grade}"
                val builder = AlertDialog.Builder(item.con)
                builder.setPositiveButton("确认"){_,_->}
                itemView.setOnClickListener {
                    launch(UI) {
                        RxDoHttpClient.api.getDistribution(item.bean.cid.toString()).await().apply {
                            when(err_code){
                                "0" -> {

                                    builder.setTitle("${item.bean.cname} 的课程成绩分布")
                                    data?.apply {
                                        builder.setMessage("""
                                            90~100分：$rate_u100 人
                                            80~90 分：$rate_u90 人
                                            70~80 分：$rate_u80 人
                                            60~70 分：$rate_u70 人
                                            低于60分：$rate_u60 人
                                        """.trimIndent())
                                    }
                                    builder.show()
                                }
                                "-1"->{
                                    Toast.makeText(item.con,message,Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    class ViewHolder(view:View,val classTextView: TextView,val cid: TextView,val score:TextView):RecyclerView.ViewHolder(view)

    override val controller: ItemController
        get() = Controller
}