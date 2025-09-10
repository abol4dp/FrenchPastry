package com.example.mynotes.mynotes.mynotes.frenchpastry_kh.login.db

class LocalRoomRepository (private  val loginDao: LoginDao){
suspend fun savePhoneNumber(phone : String){

    val entity = LoginEntity(phoneNumber = phone)
    loginDao.insertUser(entity)
}

    suspend fun getPhoneNumber(): String? {
        val user = loginDao.getUser()
        return user?.phoneNumber
    }


}