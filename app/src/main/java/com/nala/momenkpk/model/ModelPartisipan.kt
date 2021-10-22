package com.nala.momenkpk.model

class ModelPartisipan {
    var username: String? = null
    var key: String? = null
    var email: String? = null
    var password: String? = null
    var sekolah: String? = null
    var role: String? = null

    constructor() {}

    constructor(emailUser: String?, nameUser: String?, passUser:String?, sekolahUser:String?, roleUser: String?){
        username  = nameUser
        email     = emailUser
        password  = passUser
        sekolah  = sekolahUser
        role      = roleUser
    }
}