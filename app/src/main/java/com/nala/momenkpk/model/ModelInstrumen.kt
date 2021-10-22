package com.nala.momenkpk.model

class ModelInstrumen {

    var key: String? = null
    var nama: String? = null
    var id_pertanyaan: String? = null
    var answer: Int = 0

    constructor() {}

    constructor(namaBarang: String?, hargaBarang: String?) {
        nama = namaBarang
        id_pertanyaan = hargaBarang
    }
}