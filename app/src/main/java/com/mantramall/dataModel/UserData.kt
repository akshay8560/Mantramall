package com.mantramall.dataModel

data class UserData(
            private var mobileno: String? = null,
            private var name:String?= null,
            private var nameid: Int? = null,
            private var tradeid: Int? = null,
            private var userid: String? = null,
            private var imageurl: String? = null
)
{
    fun UserData() {

    }
    fun Userdata(mobileno: String, name: String,nameid: Int?,tradeid: Int?, Userid: String,imageurl:String) {
        this.mobileno = mobileno
        this.name=name
        this.nameid=nameid
        this.tradeid=tradeid
        this.userid=userid

        this.imageurl=imageurl


    }
    fun getMobileno(): String? {
        return mobileno
    }

    fun setMobileno(mobileNo: String?) {
        this.mobileno = mobileno
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
    fun getNameid(): Int? {
        return nameid
    }

    fun setNameid(nameid: Int?) {
        this.nameid = nameid
    }
    fun getTradeid(): Int? {
        return tradeid
    }
    fun setTradeid(tradeid: Int?) {
        this.tradeid = tradeid
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

