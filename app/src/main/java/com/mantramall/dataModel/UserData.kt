package com.mantramall.dataModel

data class UserData(private var mobileno: Long? = null,

            private var name:String?= null,
            private var nameid: String? = null,
            private var userid: String? = null,
            private var imageurl: String? = null
)
{
    fun UserData() {

    }
    fun Userdata(mobileno: Long, name: String,nameid: String?, Userid: String,imageurl:String) {
        this.mobileno = mobileno
        this.name=name
        this.nameid=nameid
        this.userid=userid

        this.imageurl=imageurl


    }
    fun getMobileno(): Long? {
        return mobileno
    }

    fun setMobileno(mobileNo: Long?) {
        this.mobileno = mobileno
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
    fun getNameid(): String? {
        return nameid
    }

    fun setNameid(nameid: String?) {
        this.nameid = nameid
    }
    fun getUserid(): String? {
        return userid

    }
    fun setUserid(userid:String?){
        this.userid=userid
    }
    fun getImageurl():String?{
        return  imageurl
    }
    fun setImageurl(imageurl: String?){
        this.imageurl=imageurl
    }



}

