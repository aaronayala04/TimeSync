package com.example.timesyncproject

class DataClass {
    var dataTitle: String? = null
    var dataDesc: String? = null
    var dataLang: String? = null
    var key: String? = null

    constructor(dataTitle: String, dataDesc: String, dataLang: String) {
        this.dataTitle = dataTitle
        this.dataDesc = dataDesc
        this.dataLang = dataLang
    }

    constructor() {}
}
