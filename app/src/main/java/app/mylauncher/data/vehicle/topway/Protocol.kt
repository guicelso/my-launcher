package app.mylauncher.data.vehicle.topway

/**
 * Descritores e códigos de transação dos serviços da TopWay (spec 006, R6.4).
 * Evidências e tabela completa: docs/03-topway-protocol.md.
 */
object CarInfo {
    const val PACKAGE = "com.tw.carinfoservice"
    const val ACTION = "com.tw.carinfoservice.CarService.Bind"
    const val DESCRIPTOR = "com.tw.carinfoservice.CarServiceAidl"
    const val CALLBACK_DESCRIPTOR = "com.tw.carinfoservice.CarServiceCallBack"

    // Serviço (c/b/a/b.java)
    const val REGISTER = 1
    const val UNREGISTER = 2
    const val REQUEST_DATA = 3
    const val GET_VOLTAGE = 4
    const val GET_HEADLIGHT = 5

    // Callback (c/b/a/c.java)
    const val CB_VOLTAGE = 1 // float, String
    const val CB_HEADLIGHT = 2 // int(bool)
    const val CB_HANDBRAKE = 11 // int(bool)
    const val CB_SPEED = 15 // int km/h
    const val CB_CAN_REVERSE = 24 // int(bool), ré via CAN
}

object Command {
    const val PACKAGE = "com.tw.service.xt"
    const val ACTION = "com.tw.service.xt.CommandService.Bind"
    const val DESCRIPTOR = "com.tw.service.xt.aidl.ITWCommandAidl"
    const val CALLBACK_DESCRIPTOR = "com.tw.service.xt.aidl.ITWCommandCallbackAidl"

    // Serviço (aidl/ITWCommandAidl.java:288-355)
    const val REGISTER = 1
    const val UNREGISTER = 2
    const val RADIO_NEXT = 11
    const val RADIO_PRE = 12
    const val BT_PLAY = 23
    const val BT_PRE = 24
    const val BT_NEXT = 25
    const val MUSIC_NEXT = 31
    const val MUSIC_PRE = 32
    const val MUSIC_PLAY = 33
    const val MUSIC_PAUSE = 34
    const val GET_VOLUME = 46
    const val GET_REVERSE = 52
    const val MEDIA_NEXT = 55
    const val MEDIA_PRE = 56
    const val MEDIA_PLAY = 57
    const val MEDIA_PAUSE = 58
    const val EXTENDED = 67

    // Callback (ITWCommandCallbackAidl.java:52-133)
    const val CB_SYSTEM_VOLUME = 1
    const val CB_VOLUME_STATUS = 2
    const val CB_REVERSE = 6
    const val CB_SLEEP = 7
    const val CB_EXTENDED = 8

    const val KEY_ACTION = "action"
    const val ACTION_SOURCE = "Source_recieve" // grafia original
    const val KEY_SOURCE = "SourceValue"
    const val ACTION_LIGHT = "light_recieve"
}

object TopWayBroadcasts {
    const val ACC_ON = "com.unisound.intent.action.ACC_ON"
    const val ACC_OFF = "com.unisound.intent.action.ACC_OFF"
    const val MUSIC_INFO = "com.tw.music.info"
    const val LAUNCHER_MUSIC_INFO = "com.tw.launcher.musicinfo"
    const val MUSIC_PROGRESS = "com.tw.launcher.music_progress_duration"
}

object TopWayPackages {
    const val RADIO = "com.tw.radio"
    const val MUSIC = "com.tw.music"
    const val BT = "com.tw.bt"
}
