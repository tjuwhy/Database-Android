package stanwang.top.select.common.network

import android.renderscript.ScriptGroup
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("login")
    fun login(@Query("username") userName:String, @Query("pass") pass :String): Deferred<CommonBody<String>>

    @GET("student")
    fun getStudent():Deferred<CommonBody<List<StudentBean>>>

    @GET("studentbysid")
    fun getStudentBySid(@Query("sid") sid:String):Deferred<CommonBody<StudentBean>>

    @GET("studentbysname")
    fun getStudentBySname(@Query("sname") sname:String):Deferred<CommonBody<List<StudentBean>>>

    @GET("course")
    fun getCourseList():Deferred<CommonBody<List<CourseBean>>>

    @GET("coursebycname")
    fun getCourseByCname(@Query("cname") cname:String):Deferred<CommonBody<List<CourseBean>>>

    @GET("coursebycid")
    fun getCourseByCid(@Query("cid") cid:String):Deferred<CommonBody<CourseBean>>

    @GET("recordbycid")
    fun getRecordByCid(@Query("cid") cid:String):Deferred<CommonBody<List<RecordBean>>>

    @GET("recordbysid")
    fun getRecordBySid(@Query("sid") sid:String):Deferred<CommonBody<List<RecordBean>>>

    @GET("add/course")
    fun insertCourse(@Query("cid") cid:String,@Query("cname")cname: String,@Query("teacher_name")tname:String,@Query("credit") credit: String,@Query("suitable_grade") sui_grade:String):Deferred<CommonBody<Any>>

    @GET("add/record")
    fun insertRecord(@Query("sid") sid:String, @Query("cid") cid:String, @Query("grade") grade:Int=90, @Query("select_year") sel_year:Int = 2019 ):Deferred<CommonBody<Any>>

    @GET("add/student")
    fun insertStudent(@Query("sid") sid:String,@Query("sname") sname: String,@Query("gender") gender:String,@Query("add_age") add_age: String,@Query("add_year") add_year: String,@Query("class_num") class_num: String):Deferred<CommonBody<Any>>

    @GET("/delete/record")
    fun deleteRecord(@Query("sid") sid: String,@Query("cid")cid: String):Deferred<CommonBody<Any>>

    @GET("/delete/student")
    fun deleteStudent(@Query("sid") sid:String):Deferred<CommonBody<Any>>

    @GET("/delete/course")
    fun deleteCourse(@Query("cid") sid:String):Deferred<CommonBody<Any>>

    @GET("/update/student/sname")
    fun updateSname(@Query("sid") sid:String,@Query("sname") sname:String):Deferred<CommonBody<Any>>

    @GET("/update/student/gender")
    fun updateGender(@Query("sid") sid:String,@Query("gender") gender:String):Deferred<CommonBody<Any>>

    @GET("/update/student/add_year")
    fun updateAddYear(@Query("sid") sid:String,@Query("add_year") add_year:String):Deferred<CommonBody<Any>>

    @GET("/update/student/add_age")
    fun updateAddAge(@Query("sid") sid:String,@Query("add_age") add_age:String):Deferred<CommonBody<Any>>

    @GET("/update/student/class")
    fun updateClass(@Query("sid") sid:String,@Query("class") class_num :String):Deferred<CommonBody<Any>>

    @GET("/update/course/name")
    fun updateCName(@Query("cid")cid:String,@Query("cname") cname:String):Deferred<CommonBody<Any>>

    @GET("/update/course/teacher_name")
    fun updateTName(@Query("cid")cid:String,@Query("teacher_name") cname:String):Deferred<CommonBody<Any>>

    @GET("/update/course/suitable_grade")
    fun updateSG(@Query("cid")cid:String,@Query("suitable_grade") sg:String):Deferred<CommonBody<Any>>

    @GET("/update/course/credit")
    fun updateCredit(@Query("cid") cid:String,@Query("credit")credit:String):Deferred<CommonBody<Any>>

    @GET("/update/record/select_year")
    fun updateSelectYear(@Query("sid") sid: String,@Query("cid")cid: String,@Query("sel_year") sel_year: String):Deferred<CommonBody<Any>>

    @GET("/update/record/grade")
    fun updateGrade(@Query("sid") sid: String,@Query("cid")cid: String,@Query("grade") grade: String):Deferred<CommonBody<Any>>

    @GET("/average/student")
    fun getAverageSid(@Query("sid") sid: String):Deferred<CommonBody<String>>

    @GET("/average/course")
    fun getCourseAvg():Deferred<CommonBody<List<CourseAverageBean>>>

    @GET("/average/class")
    fun getClassAvg():Deferred<CommonBody<List<ClassAverageBean>>>

    @GET("/distribution")
    fun getDistribution(@Query("cid")cid: String):Deferred<CommonBody<DistributionBean>>
}