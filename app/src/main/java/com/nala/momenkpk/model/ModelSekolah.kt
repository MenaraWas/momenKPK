package com.nala.momenkpk.model

class ModelSekolah {

    var key: String? = null
    var nama: String? = null
    var Kepsek: String? = null
    var id_sekolah: String? = null

    constructor() {}

    constructor(namaSekolah: String?, namaKepsek: String?, idSekolah: String?) {
        nama = namaSekolah
        Kepsek = namaKepsek
        id_sekolah = idSekolah
    }
}