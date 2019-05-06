package stanwang.top.select.common.network


data class CommonBody<T>(val err_code :String,val message:String,val data:T?)

data class StudentBean(val sid: String,val sname:String,val gender:String,val addmission_age:Int,val addmission_year:Int,val class_num :Int)

data class CourseBean(val cid:String,val cname:String,val tname:String,val credit:Int,val suitable_grade:Int)

data class RecordBean(val sid:String ,val cid:String,val grade:Int)

data class ClassAverageBean(val class_num: Int,val grade: Float)

data class CourseAverageBean(val cname: String,val cid: Int,val grade: Float)

data class DistributionBean(val cname: String,val cid: Int,val rate_u60:Int,val rate_u70:Int,val rate_u80:Int,val rate_u90: Int,val rate_u100:Int)
