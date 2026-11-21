# 011 — Gestão da gaveta de apps e aparência dos ícones

**Status:** Implementada (2026-11-21) · testada no emulador · **Depende de:** 003, 009

## Objetivo
Organizar a gaveta de apps: reordenar, ocultar e mostrar de novo, desinstalar, e escolher o formato e o pacote de ícones. A segurança ao dirigir vem em primeiro lugar.

## Decisões de UX (boas práticas)
- **A ordem padrão é alfabética**, como no Android Auto e no Pixel. O usuário pode trocar para uma **ordem personalizada**.
- **Toda edição fica concentrada num "modo de edição"** (botão "Editar" na gaveta). Isso evita mudanças acidentais com o carro em movimento.
- **Edição só com o carro parado:** com velocidade > 5 km/h (MCU ou GPS), o modo de edição fica indisponível, com o aviso "Disponível com o carro parado". É o mesmo princípio de bloqueio de distração do Android Auto.
- **Ocultar não é escondido em segredo.** O app some da gaveta, mas aparece em Ajustes → Apps → **Apps ocultos**, com um botão "Mostrar" em cada um. O fim da gaveta mostra a linha "N apps ocultos", que leva até lá.
- **Ações destrutivas ficam separadas e em vermelho** no fim do menu ("Desinstalar"). Elas usam a confirmação do próprio sistema e não aparecem para apps de sistema.

## Requisitos
- **R11.1** O menu de toque longo (spec 009) DEVE ganhar **Ocultar da gaveta** e, separado e em vermelho, **Desinstalar** (`Intent.ACTION_DELETE`). Apps de sistema não têm a opção de desinstalar.
- **R11.2** Ajustes → Apps DEVE listar os apps ocultos, cada um com o botão **Mostrar**.
- **R11.3** Ajustes → Apps DEVE oferecer **Ordem: Alfabética / Personalizada**.
- **R11.4** No modo de edição com ordem personalizada, os ícones DEVEM poder ser arrastados para reordenar: toque longo + arrastar, com animação de reacomodação dos vizinhos. A ordem é persistida.
- **R11.5** Ajustes → Aparência DEVE ter **Formato dos ícones: Redondo / Quadrado arredondado / Original do app**. O padrão é Redondo.
- **R11.6** Ajustes → Aparência DEVE permitir escolher um **pacote de ícones** instalado, nos formatos ADW/Nova (`org.adw.launcher.THEMES`, `com.novalauncher.THEME`, com `appfilter.xml`). Apps sem ícone no pacote usam o ícone original no formato escolhido.
- **R11.7** ENQUANTO a velocidade for > 5 km/h, o modo de edição DEVE ficar desabilitado.

## Critérios de aceite
- [ ] **A1** Ocultar um app pela gaveta → ele some da lista e aparece em Ajustes → Apps ocultos. "Mostrar" o traz de volta.
- [ ] **A2** Reordenar no modo de edição persiste após reiniciar a multimídia.
- [ ] **A3** "Desinstalar" abre a confirmação do sistema e não aparece para apps de sistema.
- [ ] **A4** Trocar o formato dos ícones aplica na hora, na barra e na gaveta.
- [ ] **A5** Com um pacote de ícones instalado (ex.: um pack redondo da Play Store), os ícones mudam.
