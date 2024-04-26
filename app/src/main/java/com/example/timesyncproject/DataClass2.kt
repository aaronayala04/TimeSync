package com.example.timesyncproject

class DataClass2 {
    var dataDate: String? = null
    var dataDay: String? = null
    var dataEvent: String? = null
    var dataTime: String? = null
    var dataPeriod: String? = null
    var key: String? = null

    constructor(dataDate: String, dataDay: String, dataEvent: String, dataTime: String, dataPeriod: String) {
        this.dataDate = dataDate
        this.dataDay = dataDay
        this.dataEvent = dataEvent
        this.dataTime = dataTime
        this.dataPeriod = dataPeriod

    }

    constructor() {}
}
