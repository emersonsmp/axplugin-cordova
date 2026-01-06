# Guia de Integração OutSystems

Guia passo a passo para integrar o AxPlugin no **OutSystems**.

---

## 📦 Passo 1: Preparar o Plugin

### 1.1. Criar arquivo ZIP

Compacte a pasta `plugin-cordova` inteira em um arquivo `.zip`:

```bash
cd /Users/emersonsampaio/Documents/
zip -r cordova-plugin-axplugin.zip plugin-cordova/
```

Ou manualmente:
- Selecione a pasta `plugin-cordova`
- Clique com botão direito → Compactar

---

## 🚀 Passo 2: Adicionar no OutSystems

### 2.1. Via Service Studio

1. Abra seu **Mobile App** no Service Studio
2. Vá em **Module → Manage Dependencies**
3. Na aba **Extensibility Configurations**
4. Clique em **Add Plugin**
5. Escolha **Upload Plugin Package**
6. Selecione o arquivo `cordova-plugin-axplugin.zip`
7. Clique em **Apply**

### 2.2. Via JSON (Alternativa)

Adicione no `extensibility-configurations.json`:

```json
{
  "plugins": {
    "cordova-plugin-axplugin": {
      "url": "file:///caminho/para/plugin-cordova"
    }
  }
}
```

---

## 🎨 Passo 3: Criar Actions

### 3.1. Action: ConfigurarPlugin

**Tipo**: Client Action
**Inputs**:
- `ApiKey` (Text)

**Outputs**:
- `Success` (Boolean)

**JavaScript**:
```javascript
cordova.plugins.AxPlugin.setup(
    $parameters.ApiKey,
    function(result) {
        $resolve({ Success: true });
    },
    function(error) {
        console.error("Erro ao configurar plugin:", error);
        $resolve({ Success: false });
    }
);
```

---

### 3.2. Action: IniciarTeste

**Tipo**: Client Action
**Inputs**:
- `OnVelocidadeAtualizada` (Event Handler)

**JavaScript**:
```javascript
// Armazena o callback globalmente para que possa ser cancelado depois
window.axPluginCallback = function(velocidade) {
    $parameters.OnVelocidadeAtualizada(velocidade);
};

cordova.plugins.AxPlugin.start(
    window.axPluginCallback,
    function(error) {
        console.error("Erro no teste:", error);
    }
);

$resolve();
```

---

### 3.3. Action: PararTeste

**Tipo**: Client Action
**Outputs**:
- `Success` (Boolean)

**JavaScript**:
```javascript
cordova.plugins.AxPlugin.stop(
    function(result) {
        // Limpa o callback global
        delete window.axPluginCallback;
        $resolve({ Success: true });
    },
    function(error) {
        console.error("Erro ao parar:", error);
        $resolve({ Success: false });
    }
);
```

---

### 3.4. Action: AtualizarEstatisticas

**Tipo**: Client Action
**Inputs**:
- `NovaVelocidade` (Integer)

**Lógica** (Flow):

1. **Assign: AtualizarContadores**
   ```
   VelocidadeAtual = NovaVelocidade
   TotalMedicoes = TotalMedicoes + 1
   SomaTotal = SomaTotal + NovaVelocidade
   ```

2. **Assign: CalcularMedia**
   ```
   VelocidadeMedia = SomaTotal / TotalMedicoes
   ```

3. **If: AtualizarMinimo**
   ```
   Condition: NovaVelocidade < VelocidadeMin OR TotalMedicoes = 1
   True: VelocidadeMin = NovaVelocidade
   ```

4. **If: AtualizarMaximo**
   ```
   Condition: NovaVelocidade > VelocidadeMax OR TotalMedicoes = 1
   True: VelocidadeMax = NovaVelocidade
   ```

5. **Refresh Data**

---

## 📱 Passo 4: Criar a Screen

### 4.1. Variáveis Locais

Crie as seguintes **Local Variables** na Screen:

```
VelocidadeAtual: Integer = 0
TesteLigado: Boolean = False
TotalMedicoes: Integer = 0
SomaTotal: Integer = 0
VelocidadeMedia: Decimal = 0.0
VelocidadeMin: Integer = 999
VelocidadeMax: Integer = 0
PluginConfigurado: Boolean = False
```

---

### 4.2. Layout da Screen

#### Container Principal
```
Container (Nome: ContainerPrincipal, Style: padding-24)
    └── Container (Nome: CardVelocidade, Style: card elevation-2)
        ├── Text (Nome: LabelTitulo)
            └── "Teste de Velocidade"
        ├── Container (Nome: DisplayVelocidade, Style: text-center)
            └── Expression (Nome: VelocidadeDisplay)
                └── VelocidadeAtual + " Mbps"
                └── Style: display-1 bold primary-color
        ├── Container (Nome: StatusContainer)
            └── Expression (Nome: StatusTexto)
                └── If(TesteLigado, "Executando...", "Parado")
        ├── Container (Nome: EstatisticasContainer)
            ├── Text: "Total de medições: " + TotalMedicoes
            ├── Text: "Velocidade média: " + Round(VelocidadeMedia, 1) + " Mbps"
            ├── Text: "Velocidade mínima: " + VelocidadeMin + " Mbps"
            ├── Text: "Velocidade máxima: " + VelocidadeMax + " Mbps"
        └── Container (Nome: BotoesContainer, Style: button-group)
            ├── Button (Nome: BtnIniciar)
                └── Label: "Iniciar"
                └── Enabled: PluginConfigurado AND NOT TesteLigado
                └── OnClick: → IniciarTesteFlow
            ├── Button (Nome: BtnParar)
                └── Label: "Parar"
                └── Enabled: TesteLigado
                └── OnClick: → PararTesteFlow
            └── Button (Nome: BtnReset)
                └── Label: "Resetar"
                └── OnClick: → ResetarEstatisticasFlow
```

---

### 4.3. Screen Events

#### OnReady
```
Flow: ConfigurarPluginFlow

1. ConfigurarPlugin (ApiKey: "minha-chave-teste-123")
2. Assign: PluginConfigurado = ConfigurarPlugin.Success
3. If PluginConfigurado = False
   → Feedback Message: "Erro ao configurar plugin"
```

#### OnDestroy
```
Flow: LimparRecursos

1. If TesteLigado
   → PararTeste
```

---

### 4.4. Screen Flows

#### IniciarTesteFlow
```
1. Assign: TesteLigado = True
2. IniciarTeste (OnVelocidadeAtualizada: AtualizarEstatisticas)
3. Feedback Message: "Teste iniciado!"
```

#### PararTesteFlow
```
1. PararTeste
2. Assign: TesteLigado = False
3. Feedback Message: "Teste parado!"
```

#### ResetarEstatisticasFlow
```
1. Assign:
   - VelocidadeAtual = 0
   - TotalMedicoes = 0
   - SomaTotal = 0
   - VelocidadeMedia = 0
   - VelocidadeMin = 999
   - VelocidadeMax = 0
2. Refresh Data
3. Feedback Message: "Estatísticas resetadas!"
```

---

## 🎯 Passo 5: Testar

### 5.1. Gerar APK

1. No Service Studio, vá em **Module → Build Native Mobile App**
2. Escolha **Android**
3. Aguarde o build completar
4. Baixe o APK

### 5.2. Instalar no Dispositivo

```bash
adb install -r app-debug.apk
```

### 5.3. Testar Funcionalidades

1. Abra o app
2. Clique em "Iniciar"
3. Verifique se os valores aparecem a cada 1 segundo
4. Observe as estatísticas sendo atualizadas
5. Clique em "Parar"
6. Clique em "Resetar"

---

## 🔍 Debug

### Logs no Chrome DevTools

1. Conecte o dispositivo via USB
2. Abra `chrome://inspect`
3. Clique em "inspect" no seu app
4. Veja logs em Console

### Logs via ADB

```bash
adb logcat | grep -i "axplugin"
```

---

## 📊 Exemplo de Dados Esperados

Após 10 segundos de teste, você deve ver algo como:

```
Velocidade Atual: 187 Mbps
Total de medições: 10
Velocidade média: 156.3 Mbps
Velocidade mínima: 45 Mbps
Velocidade máxima: 237 Mbps
```

---

## ⚠️ Problemas Comuns

### 1. "cordova.plugins.AxPlugin is undefined"

**Causa**: Plugin não foi carregado
**Solução**:
- Verifique se o plugin foi adicionado corretamente
- Aguarde o evento `deviceready` antes de usar
- No OutSystems, use apenas em Client Actions

### 2. "Plugin não configurado"

**Causa**: `setup()` não foi chamado
**Solução**:
- Chame `ConfigurarPlugin` no `OnReady` da Screen
- Verifique se retornou sucesso

### 3. Valores não aparecem

**Causa**: Callback não está funcionando
**Solução**:
- Verifique se `IniciarTeste` foi chamado
- Confirme que o Event Handler está correto
- Verifique logs para erros JavaScript

### 4. Build falha

**Causa**: Plugin não foi empacotado corretamente
**Solução**:
- Verifique se o `.zip` contém todos os arquivos
- Confirme que `plugin.xml` está na raiz do ZIP
- Verifique se o `.aar` está em `src/android/libs/`

---

## 💡 Dicas

1. **Performance**: Os callbacks são executados na Main Thread, perfeito para UI
2. **Memória**: Sempre chame `PararTeste` ao sair da screen
3. **Testing**: Teste em dispositivo real, emulador pode ter comportamento diferente
4. **Debug**: Use `console.log()` nas actions para debug
5. **UX**: Mostre feedback visual quando teste está rodando

---

## 📚 Recursos Adicionais

- [Documentação OutSystems Plugins](https://success.outsystems.com/Documentation/11/Extensibility_and_Integration/Mobile_Plugins)
- [Cordova Plugin Development](https://cordova.apache.org/docs/en/latest/guide/hybrid/plugins/)
- [OutSystems Community](https://www.outsystems.com/community/)

---

## ✅ Checklist de Integração

- [ ] Plugin compactado em `.zip`
- [ ] Plugin adicionado no Service Studio
- [ ] Actions criadas (Configurar, Iniciar, Parar, Atualizar)
- [ ] Variáveis locais criadas na Screen
- [ ] Layout da Screen implementado
- [ ] OnReady configurado para chamar Setup
- [ ] Event handlers conectados
- [ ] Build APK gerado
- [ ] Testado em dispositivo real
- [ ] Logs verificados

---

Pronto para usar! 🚀
