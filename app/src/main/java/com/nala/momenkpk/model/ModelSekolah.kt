package com.nala.momenkpk.model

class ModelSekolah {

    var key: String? = null
    var namaSekolah: String? = null
    var namaKepsek: String? = null
    var id_sekolah: String? = null

    constructor() {}

    constructor(namaBarang: String?, namaKepala: String?, hargaBarang: String?) {
        namaSekolah = namaBarang
        namaKepsek = namaKepala
        id_sekolah = hargaBarang
    }
}