package app.mylauncher.ui.media

/** Fontes exibidas no seletor: app de música padrão + apps de mídia conhecidos instalados. */
object MediaSources {
    val KNOWN = listOf(
        "com.spotify.music",
        "com.google.android.apps.youtube.music",
        "com.soundcloud.android",
        "com.tw.radio",
        "com.tw.music",
        "com.tw.bt",
    )

    fun list(musicApp: String, current: String?, isInstalled: (String) -> Boolean): List<String> =
        (listOfNotNull(current, musicApp) + KNOWN).distinct().filter(isInstalled)
}
