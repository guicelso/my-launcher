package app.mylauncher.ui.settings

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.ui.window.PopupProperties
import app.mylauncher.data.prefs.AppOrder
import app.mylauncher.data.prefs.DashboardLayout
import app.mylauncher.data.prefs.DrawerAnimation
import app.mylauncher.data.prefs.IconShape
import app.mylauncher.data.prefs.RailPosition
import app.mylauncher.data.prefs.DriverSide
import app.mylauncher.data.prefs.WidgetType
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.mylauncher.AppContainer
import app.mylauncher.BuildConfig
import app.mylauncher.core.CrashGuard
import app.mylauncher.core.FileLog
import app.mylauncher.core.LogExporter
import app.mylauncher.data.media.MediaListenerService
import app.mylauncher.data.prefs.Destination
import app.mylauncher.data.prefs.LauncherSettings
import app.mylauncher.data.prefs.TextScale
import app.mylauncher.data.prefs.ThemePref
import app.mylauncher.data.vehicle.Signal
import app.mylauncher.data.vehicle.topway.LinkStatus
import app.mylauncher.data.vehicle.topway.TwUtilProbe
import app.mylauncher.nav.NavIntents
import app.mylauncher.ui.components.AppIcon
import app.mylauncher.ui.components.PillShape
import app.mylauncher.ui.components.ScreenPanel
import app.mylauncher.ui.components.TouchIconButton
import app.mylauncher.ui.icons.LauncherIcons
import app.mylauncher.ui.media.MediaSources
import app.mylauncher.ui.media.PillButton
import app.mylauncher.ui.theme.LocalLauncherColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class SettingsSection(val label: String) {
    APPEARANCE("Aparência"),
    DASHBOARD("Painel"),
    RAIL("Barra de navegação"),
    APPS("Apps"),
    NAVIGATION("Navegação"),
    MEDIA("Mídia"),
    VEHICLE("Veículo"),
    SYSTEM("Sistema"),
}

/** Ajustes do launcher (spec 007). Artboard "Ajustes do launcher". */
@Composable
fun SettingsScreen(
    container: AppContainer,
    onBack: () -> Unit,
    onOpenDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    initialSection: SettingsSection = SettingsSection.APPEARANCE,
) {
    val c = LocalLauncherColors.current
    var section by rememberSaveable(initialSection) { mutableStateOf(initialSection) }
    val settings by container.settings.settings.collectAsStateWithLifecycle()

    ScreenPanel(modifier) {
        Row(Modifier.fillMaxSize()) {
            Column(
                Modifier.width(340.dp).fillMaxHeight().verticalScroll(rememberScrollState()).padding(horizontal = 12.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                    TouchIconButton(LauncherIcons.Back, "Voltar", onBack, iconSize = 32.dp)
                    Text("Ajustes", style = MaterialTheme.typography.headlineMedium, color = c.onSurface)
                }
                SettingsSection.entries.forEach { s ->
                    val on = s == section
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(76.dp)
                            .clip(PillShape)
                            .background(if (on) c.selectedBg else androidx.compose.ui.graphics.Color.Transparent)
                            .clickable(role = Role.Tab) { section = s }
                            .padding(horizontal = 28.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Text(s.label, style = MaterialTheme.typography.titleMedium, color = if (on) c.onSelected else c.onSurface)
                    }
                }
            }
            Box(Modifier.width(1.dp).fillMaxHeight().background(c.outline))
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 36.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                when (section) {
                    SettingsSection.APPEARANCE -> Appearance(container, settings)
                    SettingsSection.DASHBOARD -> DashboardSection(container, settings)
                    SettingsSection.RAIL -> RailSection(container, settings, onOpenDrawer)
                    SettingsSection.APPS -> AppsSection(container, settings)
                    SettingsSection.NAVIGATION -> Navigation(container, settings)
                    SettingsSection.MEDIA -> Media(container, settings)
                    SettingsSection.VEHICLE -> Vehicle(container, settings)
                    SettingsSection.SYSTEM -> SystemSection(container)
                }
            }
        }
    }
}

@Composable
private fun Appearance(container: AppContainer, s: LauncherSettings) {
    SettingsGroup("Tema", "No automático, o tema segue os faróis do carro (sinal da MCU); sem sinal, o horário.") {
        ChoicePills(
            listOf(ThemePref.AUTO to "Automático", ThemePref.DAY to "Dia", ThemePref.NIGHT to "Noite"),
            s.theme, container.settings::setTheme,
        )
    }
    SettingsGroup("Tamanho do texto") {
        ChoicePills(listOf(TextScale.NORMAL to "Normal", TextScale.LARGE to "Grande"), s.textScale, container.settings::setTextScale)
    }
    SettingsGroup("Formato dos ícones", "Redondo é o padrão do Android Auto.") {
        ChoicePills(IconShape.entries.map { it to it.label }, s.iconShape, container.settings::setIconShape)
    }
    val packs = remember { container.apps.iconPacks.installed() }
    SettingsGroup(
        "Pacote de ícones",
        "Instale um pacote de ícones (formato ADW/Nova) pela Play Store e escolha aqui. Apps sem ícone no pacote usam o próprio.",
    ) {
        RadioRow("Nenhum (ícones dos apps)", s.iconPack == null) { container.settings.setIconPack(null) }
        packs.forEach { pack ->
            RadioRow(pack.label, s.iconPack == pack.packageName, leading = { AppIcon(container.apps, pack.packageName, 44.dp) }) {
                container.settings.setIconPack(pack.packageName)
            }
        }
        if (packs.isEmpty()) SettingRow("Nenhum pacote instalado")
    }
    SettingsGroup("Animação da gaveta de apps") {
        DrawerAnimation.entries.forEach { anim ->
            RadioRow(anim.label, s.drawerAnimation == anim) { container.settings.setDrawerAnimation(anim) }
        }
    }
    SettingsGroup("Barra", "Vertical na lateral ou horizontal embaixo, como a barra de navegação do Android Auto.") {
        ChoicePills(RailPosition.entries.map { it to it.label }, s.railPosition, container.settings::setRailPosition)
    }
    SettingsGroup(
        "Lado do motorista",
        "Com o volante à direita, a barra vertical vai para a direita e a horizontal se inverte, deixando os botões perto do motorista.",
    ) {
        ChoicePills(DriverSide.entries.map { it to it.label }, s.driverSide, container.settings::setDriverSide)
    }
}

@Composable
private fun DashboardSection(container: AppContainer, s: LauncherSettings) {
    SettingsGroup("Grade", "Quantos cards ficam ao lado do mapa. Dica: toque e segure um card no painel (parado) para trocá-lo.") {
        ChoicePills(DashboardLayout.entries.map { it to it.label }, s.dashboardLayout, container.settings::setDashboardLayout)
    }
    SettingsGroup("Widgets") {
        (0 until s.dashboardLayout.slots).forEach { slot ->
            WidgetPickerRow("Card ${slot + 1}", s.widgets[slot]) { container.settings.setWidget(slot, it) }
        }
    }
}

@Composable
private fun WidgetPickerRow(title: String, current: WidgetType, onPick: (WidgetType) -> Unit) {
    val c = LocalLauncherColors.current
    var open by remember { mutableStateOf(false) }
    Box {
        SettingRow(title, current.label, onClick = { open = true }) {
            Icon(LauncherIcons.Chevron, null, Modifier.size(28.dp), tint = c.onSurfaceVariant)
        }
        DropdownMenu(
            expanded = open,
            onDismissRequest = { open = false },
            properties = PopupProperties(focusable = false),
            containerColor = c.surface,
        ) {
            WidgetType.entries.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.label, style = MaterialTheme.typography.titleMedium, color = c.onSurface) },
                    trailingIcon = { if (type == current) Icon(LauncherIcons.Check, null, Modifier.size(24.dp), tint = c.primary) },
                    onClick = { onPick(type); open = false },
                    modifier = Modifier.height(68.dp),
                )
            }
        }
    }
}

@Composable
private fun AppsSection(container: AppContainer, s: LauncherSettings) {
    val apps by container.apps.apps.collectAsStateWithLifecycle()
    SettingsGroup("Acesso rápido", "Como no Android Auto, \"Sair\" vem antes dos apps e volta ao launcher original da multimídia.") {
        ToggleRow("Mostrar \"Sair\" primeiro", s.showExitTile, onChange = container.settings::setShowExitTile)
    }
    SettingsGroup("Ordem da gaveta", "Na personalizada, use Editar na gaveta e arraste os ícones (só com o carro parado).") {
        ChoicePills(AppOrder.entries.map { it to it.label }, s.appOrder, container.settings::setAppOrder)
        if (s.customOrder.isNotEmpty()) {
            PillButton("Redefinir ordem personalizada", primary = false) {
                container.settings.setCustomOrder(emptyList())
                container.settings.setAppOrder(AppOrder.ALPHABETICAL)
            }
        }
    }
    val hidden = apps.filter { it.packageName in s.hidden }
    SettingsGroup("Apps ocultos", "Ocultos não aparecem na gaveta. Para ocultar, toque e segure um app na gaveta.") {
        if (hidden.isEmpty()) SettingRow("Nenhum app oculto")
        hidden.forEach { app ->
            SettingRow(app.label) {
                PillButton("Mostrar", primary = false) { container.settings.unhide(app.packageName) }
            }
        }
    }
}

@Composable
private fun RailSection(container: AppContainer, s: LauncherSettings, onOpenDrawer: () -> Unit) {
    val apps = container.apps
    SettingsGroup(
        "Apps fixados",
        "Até ${LauncherSettings.MAX_RAIL_APPS} apps fixos na barra; as vagas livres mostram os recentes.",
    ) {
        if (s.pinned.isEmpty()) SettingRow("Nenhum app fixado", "A barra mostra os apps usados recentemente")
        s.pinned.forEach { pkg ->
            SettingRow(apps.label(pkg)) {
                TouchIconButton(LauncherIcons.Close, "Desafixar ${apps.label(pkg)}", { container.settings.setPinned(s.pinned - pkg) }, iconSize = 28.dp)
            }
        }
        PillButton("Escolher na gaveta de apps", onClick = onOpenDrawer)
    }
    SettingsGroup("Botões e status", "Dica: toque e segure um app na barra para fixar, desafixar ou remover dos recentes.") {
        ToggleRow(
            "Selo de notificações", s.showBell,
            subtitle = "Mostra quantas notificações há e abre a Central de notificações",
            onChange = container.settings::setShowBell,
        )
        ToggleRow("Comando de voz", s.showVoice, onChange = container.settings::setShowVoice)
        ToggleRow("Clima na barra", s.showWeatherInBar, subtitle = "Temperatura junto do relógio", onChange = container.settings::setShowWeatherInBar)
    }
}

@Composable
private fun Navigation(container: AppContainer, s: LauncherSettings) {
    val apps = container.apps
    val options = (NavIntents.KNOWN + s.navApp).distinct().filter(apps::isInstalled)
    SettingsGroup("App de navegação padrão", "Usado pela busca do mapa, pelos destinos e pelo botão \"Abrir no…\".") {
        if (options.isEmpty()) SettingRow("Nenhum app de navegação instalado", "Instale o Waze, o Google Maps ou o HERE")
        options.forEach { pkg ->
            RadioRow(apps.label(pkg), pkg == s.navApp, leading = { AppIcon(apps, pkg, 44.dp) }) { container.settings.setNavApp(pkg) }
        }
    }
    SettingsGroup(
        "Destinos",
        "Sem digitar: toque em \"Salvar local atual\" no painel, segure um ponto no mapa ou compartilhe um local do Maps/WhatsApp para o Lumia.",
    ) {
        if (s.destinations.isEmpty()) SettingRow("Nenhum destino salvo")
        s.home?.let { d -> SettingRow("Casa", d.query) { PillButton("Remover", primary = false) { container.settings.setHome(null) } } }
        s.work?.let { d -> SettingRow("Trabalho", d.query) { PillButton("Remover", primary = false) { container.settings.setWork(null) } } }
        s.favorites.forEach { d ->
            SettingRow(d.label, d.query) { PillButton("Remover", primary = false) { container.settings.removeFavorite(d.label) } }
        }
    }
    SettingsGroup(
        "Aprender locais frequentes",
        "Ao desligar o carro, o Lumia guarda a posição e sugere Casa/Trabalho depois de 3 paradas no mesmo lugar. Tudo fica só nesta multimídia.",
    ) {
        ToggleRow("Aprender locais", s.learnPlaces, onChange = container.settings::setLearnPlaces)
        PillButton("Apagar histórico de locais", primary = false) { container.placesLearner.clearHistory() }
    }
    DestinationEditor("Casa (digitar endereço)", s.home) { container.settings.setHome(it?.copy(label = "Casa")) }
    DestinationEditor("Trabalho (digitar endereço)", s.work) { container.settings.setWork(it?.copy(label = "Trabalho")) }
}

@Composable
private fun DestinationEditor(label: String, current: Destination?, onSave: (Destination?) -> Unit) {
    var text by remember(current) { mutableStateOf(current?.query.orEmpty()) }
    SettingsGroup(label, "Endereço ou coordenadas \"lat, lon\" (ex.: -23.5505, -46.6333).") {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            TextInput(text, { text = it }, "Endereço de $label", Modifier.weight(1f))
            PillButton("Salvar") { onSave(text.trim().takeIf { it.isNotEmpty() }?.let { Destination(label, it) }) }
            if (current != null) PillButton("Remover", primary = false) { text = ""; onSave(null) }
        }
    }
}

@Composable
private fun Media(container: AppContainer, s: LauncherSettings) {
    val apps = container.apps
    val granted by MediaListenerService.connected.collectAsStateWithLifecycle()
    val options = MediaSources.list(s.musicApp, null, apps::isInstalled)
    SettingsGroup("App de música padrão", "Retomado pelo card de mídia quando nada está tocando.") {
        if (options.isEmpty()) SettingRow("Nenhum app de música instalado")
        options.forEach { pkg ->
            RadioRow(apps.label(pkg), pkg == s.musicApp, leading = { AppIcon(apps, pkg, 44.dp) }) { container.settings.setMusicApp(pkg) }
        }
    }
    SettingsGroup("Comportamento") {
        ToggleRow(
            "Abrir apps de mídia em tela dividida", s.splitMedia,
            "Tocar num app de música na barra mostra o player ao lado do mapa", container.settings::setSplitMedia,
        )
    }
    SettingsGroup("Chave do carro", "Ao tirar a chave, a multimídia entra em espera e fecha os apps que não estão na lista da TopWay.") {
        ToggleRow(
            "Manter o app de música aberto com a chave desligada", s.keepMusicAlive,
            "A música continua de onde parou, sem precisar abrir o app de novo", container.settings::setKeepMusicAlive,
        )
        ToggleRow(
            "Retomar a música ao ligar o carro", s.resumeOnWake,
            "Só se ela estava tocando quando a chave foi desligada", container.settings::setResumeOnWake,
        )
    }
    SettingsGroup("Acesso às notificações", "Necessário para ver o que está tocando e as mensagens recebidas.") {
        SettingRow(if (granted) "Concedido" else "Não concedido", if (granted) null else "Toque para abrir a tela do sistema") {
            if (!granted) PillButton("Permitir") { container.systemActions.openNotificationAccess() }
        }
    }
}

@Composable
private fun Vehicle(container: AppContainer, s: LauncherSettings) {
    val c = LocalLauncherColors.current
    val vehicle by container.vehicle.collectAsStateWithLifecycle()
    val car by container.topWay.carLink.status.collectAsStateWithLifecycle()
    val cmd by container.topWay.commandLink.status.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    var probe by remember { mutableStateOf<String?>(null) }
    val fmt = remember { SimpleDateFormat("HH:mm:ss", Locale("pt", "BR")) }

    SettingsGroup("Preferências") {
        ToggleRow("Baixar o volume na marcha à ré", s.duckOnReverse, onChange = container.settings::setDuckOnReverse)
        ToggleRow("Mostrar tensão da bateria", s.showBattery, "Abaixo do relógio, quando a MCU informa", container.settings::setShowBattery)
        ToggleRow("Simular veículo", s.simulateVehicle, "Para testes sem o carro (toque longo no relógio)", container.settings::setSimulateVehicle)
    }
    SettingsGroup("Diagnóstico", "Valor atual de cada sinal, canal de origem e horário do último evento (R6.7).") {
        LinkRow("CarService (C1)", car)
        LinkRow("CommandService (C2)", cmd)
        Signal.entries.forEach { sig ->
            val info = vehicle.info[sig]
            SettingRow(
                sig.label,
                info?.let { "${it.channel} · ${fmt.format(Date(it.at))}" } ?: "Indisponível",
            ) {
                Text(info?.value ?: "—", style = MaterialTheme.typography.titleMedium, color = if (info != null) c.onSurface else c.onSurfaceVariant)
            }
        }
    }
    SettingsGroup(
        "Testes de pesquisa",
        "Executados só ao tocar. S6.1 verifica se a biblioteca da MCU (TWUtil) abre para um app comum; " +
            "S8.1 pede à MCU para rodar o comando somente leitura `id` como root (spec 008).",
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PillButton("Spike S6.1 · TWUtil", primary = false) {
                scope.launch { probe = withContext(Dispatchers.IO) { TwUtilProbe.probeOpen() } }
            }
            PillButton("Spike S8.1 · root", primary = false) {
                scope.launch { probe = withContext(Dispatchers.IO) { TwUtilProbe.probeRootShell(container.context) } }
            }
        }
        probe?.let { SettingRow("Resultado", it) }
    }
}

@Composable
private fun LinkRow(name: String, status: LinkStatus) {
    val c = LocalLauncherColors.current
    SettingRow(name, status.detail.ifBlank { null }) {
        Text(
            status.state.label,
            style = MaterialTheme.typography.labelLarge,
            color = if (status.state == app.mylauncher.data.vehicle.topway.LinkState.CONNECTED) c.success else c.onSurfaceVariant,
        )
    }
}

@Composable
private fun SystemSection(container: AppContainer) {
    val c = LocalLauncherColors.current
    val scope = rememberCoroutineScope()
    var exportResult by remember { mutableStateOf<String?>(null) }
    SettingsGroup("Sobre") {
        SettingRow("Versão", "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})")
    }
    SettingsGroup("Logs", "Gera arquivos em Android/data/app.mylauncher/files/logs (use `adb pull`).") {
        PillButton("Exportar logs", primary = false) {
            scope.launch {
                exportResult = withContext(Dispatchers.IO) { LogExporter.export(container.context) }
                    .fold({ "Exportado para ${it.absolutePath}" }, { "Falhou: ${it.message}" })
            }
        }
        exportResult?.let { SettingRow("Resultado", it) }
    }
    SettingsGroup("Recuperação", "Abre a escolha de tela inicial do sistema e o launcher original da multimídia.") {
        Box(
            Modifier
                .height(64.dp)
                .clip(PillShape)
                .border(2.dp, c.danger, PillShape)
                .clickable(role = Role.Button) { restoreStockLauncher(container) }
                .padding(horizontal = 26.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text("Voltar ao launcher original", style = MaterialTheme.typography.labelLarge, color = c.danger)
        }
    }
}

/** R7.7: como app comum não podemos definir o HOME de outro app; abrimos a escolha do sistema. */
private fun restoreStockLauncher(container: AppContainer) {
    val ctx = container.context
    val opened = runCatching {
        ctx.startActivity(Intent(Settings.ACTION_HOME_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        true
    }.getOrDefault(false)
    if (!opened) {
        FileLog.w("ACTION_HOME_SETTINGS indisponível; abrindo o launcher original direto")
        CrashGuard.openFallback(ctx)
    }
}
