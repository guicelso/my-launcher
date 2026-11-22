package app.mylauncher.ui.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.mylauncher.data.media.MediaError
import app.mylauncher.ui.components.pressable
import app.mylauncher.ui.icons.LauncherIcons
import kotlinx.coroutines.delay

/** Mensagem de estado (wireframes MxStateLogin / MxStateOffline). */
data class MessageState(
    val icon: ImageVector,
    val title: String,
    val body: String,
    val primary: String?,
    val secondary: String,
    /** Login e ajustes só com o carro parado (R18.17, R18.21). */
    val lockWhileDriving: Boolean,
)

/** `STATE_ERROR` da sessão → mensagem: login (com "Entrar") ou erro genérico. */
fun errorState(e: MediaError, appLabel: String): MessageState = when {
    e.isAuth || e.resolutionIntent != null && e.resolutionLabel != null -> MessageState(
        icon = LauncherIcons.Person,
        // O código do erro nem sempre chega pelo controlador da plataforma: "Entrar"/"Login" no
        // rótulo da ação também indica login.
        title = if (e.isAuth || e.resolutionLabel.isLoginLabel()) "Entre na sua conta $appLabel" else e.resolutionLabel ?: "Ação necessária",
        body = e.message.ifBlank { "Para ouvir, entre na sua conta. A tela de login abre aqui na multimídia." },
        primary = e.resolutionLabel ?: "Entrar",
        secondary = "Trocar de app",
        lockWhileDriving = true,
    )
    else -> MessageState(
        icon = LauncherIcons.Alert,
        title = "Não foi possível tocar",
        body = e.message.ifBlank { "O app $appLabel informou um erro." },
        primary = null,
        secondary = "Trocar de app",
        lockWhileDriving = false,
    )
}

private fun String?.isLoginLabel(): Boolean {
    val t = this?.lowercase() ?: return false
    return "entr" in t || "login" in t || "sign" in t || "conect" in t
}

fun offlineState(): MessageState = MessageState(
    icon = LauncherIcons.CloudOff,
    title = "Sem conexão com a internet",
    body = "O conteúdo baixado no app continua disponível.",
    primary = "Tentar de novo",
    secondary = "Trocar de app",
    lockWhileDriving = false,
)

/**
 * Mensagem centrada com ícone, título, texto e botões (R18.17). Com [MessageState.lockWhileDriving]
 * e o carro andando, o botão principal fica desabilitado com cadeado e "Disponível com o carro parado".
 */
@Composable
fun MediaMessage(
    state: MessageState,
    accent: Color,
    driving: Boolean,
    onPrimary: () -> Unit,
    onSecondary: () -> Unit,
    modifier: Modifier = Modifier,
    compact: Boolean = false,
    onArt: Boolean = false,
) {
    val m = if (onArt) OnArtMedia else mediaPalette()
    val locked = state.lockWhileDriving && driving
    Column(
        modifier.padding(horizontal = if (compact) 8.dp else 112.dp).padding(bottom = if (compact) 0.dp else 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(if (compact) 14.dp else 20.dp, Alignment.CenterVertically),
    ) {
        Box(Modifier.size(if (compact) 88.dp else 112.dp).clip(CircleShape).background(m.skeleton), contentAlignment = Alignment.Center) {
            Icon(state.icon, null, Modifier.size(if (compact) 44.dp else 56.dp), tint = accent)
        }
        Text(state.title, fontSize = if (compact) 28.sp else 34.sp, color = m.text, textAlign = TextAlign.Center, modifier = Modifier.widthIn(max = 760.dp))
        Text(state.body, fontSize = if (compact) 20.sp else 24.sp, lineHeight = if (compact) 27.sp else 32.sp, color = m.muted, textAlign = TextAlign.Center, modifier = Modifier.widthIn(max = 720.dp))
        Row(Modifier.padding(top = if (compact) 4.dp else 12.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            if (state.primary != null) {
                if (locked) {
                    Row(
                        Modifier.height(76.dp).clip(CircleShape).background(m.key).padding(horizontal = 40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Icon(LauncherIcons.Lock, null, Modifier.size(24.dp), tint = m.disabled)
                        Text(state.primary, fontSize = 24.sp, fontWeight = FontWeight.Medium, color = m.disabled)
                    }
                } else {
                    Box(
                        Modifier.height(76.dp).clip(CircleShape).background(m.play).pressable(onClickLabel = state.primary, onClick = onPrimary).padding(horizontal = 40.dp),
                        contentAlignment = Alignment.Center,
                    ) { Text(state.primary, fontSize = 24.sp, fontWeight = FontWeight.Medium, color = m.onPlay) }
                }
            }
            if (!compact || state.primary == null) {
                MediaPill(state.secondary, null, onSecondary, height = 76.dp, background = Color.Transparent, border = m.outline, color = m.text, textSize = 24.sp, endPad = 36.dp)
            }
        }
        if (locked) Text("Disponível com o carro parado", fontSize = 20.sp, color = m.subtle)
    }
}

/**
 * Carregando (wireframe MxState): esqueleto da grade; o aviso "Carregando" só aparece depois de
 * 1 s, como o Media Center do AOSP (R18.18).
 */
@Composable
fun MediaLoading(accent: Color, modifier: Modifier = Modifier) {
    val m = mediaPalette()
    var showSpinner by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { delay(1_000); showSpinner = true }
    Box(modifier) {
        Column(Modifier.fillMaxSize().padding(start = 88.dp, top = 8.dp)) {
            Box(Modifier.padding(top = 14.dp, bottom = 20.dp).size(260.dp, 28.dp).clip(RoundedCornerShape(8.dp)).background(m.skeleton))
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                repeat(5) {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(16.dp)).background(Color(0xFF313332).takeIf { m === NightMedia } ?: m.skeleton2))
                        Box(Modifier.fillMaxWidth(0.8f).height(20.dp).clip(RoundedCornerShape(6.dp)).background(m.skeleton))
                        Box(Modifier.fillMaxWidth(0.55f).height(16.dp).clip(RoundedCornerShape(6.dp)).background(m.skeleton2))
                    }
                }
            }
            Box(Modifier.padding(top = 28.dp).size(220.dp, 28.dp).clip(RoundedCornerShape(8.dp)).background(m.skeleton))
        }
        if (showSpinner) {
            Row(
                Modifier.align(Alignment.Center).shadow(24.dp, CircleShape).height(64.dp).clip(CircleShape).background(m.key).padding(start = 18.dp, end = 26.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                Spinner(accent, 32.dp)
                Text("Carregando", fontSize = 22.sp, color = m.text)
            }
        }
    }
}

/** Pílulas de esqueleto no lugar das abas enquanto carrega. */
@Composable
fun SkeletonTabs() {
    val m = mediaPalette()
    Row(Modifier.padding(start = 12.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(Modifier.size(130.dp, 48.dp).clip(CircleShape).background(m.skeleton))
        Box(Modifier.size(150.dp, 48.dp).clip(CircleShape).background(m.skeleton2))
        Box(Modifier.size(130.dp, 48.dp).clip(CircleShape).background(m.skeleton2))
    }
}

@Composable
fun EmptySpacer() = Spacer(Modifier.width(0.dp))
