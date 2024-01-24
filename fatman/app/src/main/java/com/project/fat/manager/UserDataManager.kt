package com.project.fat.manager

//API의 getUser를 통한 초기 세팅을 하는 코드가 없으므로 항상 getMoney() 리턴값은 -1
object UserDataManager {
    private var money : Long? = null

    fun setMoney(money : Long){
        this.money = money
    }
    fun getMoney() = if(money != null) money else -1
}