# cordova-plugin-axplugin

Plugin Cordova que permite usar o AxPlugin nativo Android em aplicativos **OutSystems**.

Este plugin simula testes de velocidade de internet retornando valores aleatórios entre 21 e 248 Mbps.

---

## 📦 Instalação

### Opção 1: Instalação local (desenvolvimento)

```bash
cordova plugin add /caminho/para/plugin-cordova
```

### Opção 2: No OutSystems

1. Faça upload do plugin como arquivo `.zip`
2. Ou adicione via URL do repositório Git

---

## 🚀 Uso Básico

### JavaScript / OutSystems

```javascript
// 1. Configurar o plugin
cordova.plugins.AxPlugin.setup(
    "minha-chave-123",
    function(success) {
        console.log("Plugin configurado:", success);
    },
    function(error) {
        console.error("Erro ao configurar:", error);
    }
);

// 2. Iniciar teste de velocidade
cordova.plugins.AxPlugin.start(
    function(velocidade) {
        // Chamado a cada 1 segundo com valor entre 21-248
        console.log("Velocidade:", velocidade + " Mbps");
        // Atualizar UI com o valor
        document.getElementById("velocidade").innerText = velocidade + " Mbps";
    },
    function(error) {
        console.error("Erro:", error);
    }
);

// 3. Parar teste
cordova.plugins.AxPlugin.stop(
    function(success) {
        console.log("Plugin parado:", success);
    },
    function(error) {
        console.error("Erro ao parar:", error);
    }
);
```

---

## 🎯 Uso no OutSystems

### 1. Adicionar o Plugin

No **Service Studio**:

1. Vá em **Module → Manage Dependencies**
2. Adicione o plugin `cordova-plugin-axplugin`
3. Ou faça upload manual do `.zip`

### 2. Criar JavaScript para Configurar

Crie um **Client Action** com JavaScript:

```javascript
// Action: ConfigurarPlugin
// Input: ApiKey (Text)
// Output: Success (Boolean)

cordova.plugins.AxPlugin.setup(
    $parameters.ApiKey,
    function(result) {
        $resolve({ Success: true });
    },
    function(error) {
        $resolve({ Success: false });
    }
);
```

### 3. Criar JavaScript para Iniciar

Crie um **Client Action** com JavaScript:

```javascript
// Action: IniciarTeste
// Output: Velocidade (Integer) - via callback

cordova.plugins.AxPlugin.start(
    function(velocidade) {
        // Atualizar variável local ou chamar outra action
        $parameters.OnSpeedUpdate(velocidade);
    },
    function(error) {
        console.error("Erro:", error);
    }
);

$resolve();
```

### 4. Criar JavaScript para Parar

Crie um **Client Action** com JavaScript:

```javascript
// Action: PararTeste

cordova.plugins.AxPlugin.stop(
    function(result) {
        $resolve({ Success: true });
    },
    function(error) {
        $resolve({ Success: false });
    }
);
```

### 5. Usar nas Screens

Na sua **Screen**:

1. **OnReady**: Chame `ConfigurarPlugin` com a chave
2. **Botão Iniciar**: Chame `IniciarTeste`
3. **Botão Parar**: Chame `PararTeste`
4. **OnSpeedUpdate**: Handler que atualiza a UI com o valor

---

## 📋 API Completa

### `setup(apiKey, successCallback, errorCallback)`

Configura o plugin com a chave de API.

**Parâmetros:**
- `apiKey` (string): Chave de autenticação
- `successCallback` (function): Chamado quando configurado
- `errorCallback` (function): Chamado em caso de erro

**Exemplo:**
```javascript
cordova.plugins.AxPlugin.setup("minha-chave", onSuccess, onError);
```

---

### `start(speedCallback, errorCallback)`

Inicia o teste de velocidade. O callback será chamado a cada 1 segundo.

**Parâmetros:**
- `speedCallback` (function): Chamado com valor inteiro (21-248 Mbps)
- `errorCallback` (function): Chamado em caso de erro

**Exemplo:**
```javascript
cordova.plugins.AxPlugin.start(
    function(speed) {
        console.log("Velocidade:", speed);
    },
    function(error) {
        console.error(error);
    }
);
```

**Comportamento:**
- Retorna valores a cada **1 segundo**
- Valores entre **21 e 248** (simulando Mbps)
- 90% de taxa de sucesso, 10% de falhas simuladas

---

### `stop(successCallback, errorCallback)`

Para o teste de velocidade.

**Parâmetros:**
- `successCallback` (function): Chamado quando parado
- `errorCallback` (function): Chamado em caso de erro

**Exemplo:**
```javascript
cordova.plugins.AxPlugin.stop(onSuccess, onError);
```

---

### `isAvailable(successCallback, errorCallback)`

Verifica se o plugin está disponível (sempre true no Android).

**Parâmetros:**
- `successCallback` (function): Chamado com 1 (true) ou 0 (false)
- `errorCallback` (function): Chamado em caso de erro

**Exemplo:**
```javascript
cordova.plugins.AxPlugin.isAvailable(
    function(available) {
        if (available) {
            console.log("Plugin disponível!");
        }
    },
    onError
);
```

---

## 📊 Exemplo Completo OutSystems

### Screen Variables

```
- VelocidadeAtual: Integer = 0
- TesteLigado: Boolean = False
- TotalMedicoes: Integer = 0
- VelocidadeMedia: Decimal = 0
- VelocidadeMin: Integer = 999
- VelocidadeMax: Integer = 0
```

### Client Actions

#### ConfigurarPlugin
```javascript
cordova.plugins.AxPlugin.setup(
    "minha-chave-teste",
    function() { $resolve({ Success: true }); },
    function(err) { $resolve({ Success: false }); }
);
```

#### IniciarTeste
```javascript
cordova.plugins.AxPlugin.start(
    function(velocidade) {
        // Chama Server Action ou atualiza variável
        $actions.AtualizarVelocidade(velocidade);
    },
    function(error) {
        console.error(error);
    }
);
$resolve();
```

#### PararTeste
```javascript
cordova.plugins.AxPlugin.stop(
    function() { $resolve({ Success: true }); },
    function(err) { $resolve({ Success: false }); }
);
```

#### AtualizarVelocidade (Client Action)
```
Input: Velocidade (Integer)

Logic:
- Assign VelocidadeAtual = Velocidade
- Assign TotalMedicoes = TotalMedicoes + 1
- Assign VelocidadeMedia = (VelocidadeMedia * (TotalMedicoes - 1) + Velocidade) / TotalMedicoes
- If Velocidade < VelocidadeMin: Assign VelocidadeMin = Velocidade
- If Velocidade > VelocidadeMax: Assign VelocidadeMax = Velocidade
- Refresh Screen
```

---

## 🗂️ Estrutura do Plugin

```
plugin-cordova/
├── plugin.xml                    # Configuração do plugin Cordova
├── package.json                  # Metadados npm
├── README.md                     # Esta documentação
├── www/
│   └── AxPlugin.js              # Interface JavaScript
└── src/
    └── android/
        ├── AxPluginCordova.java # Ponte Java (Cordova ↔ .aar)
        ├── build.gradle         # Configuração Gradle
        └── libs/
            └── axplugin-release.aar  # Biblioteca nativa Android
```

---

## ⚠️ Requisitos

- **Plataforma**: Android apenas
- **Cordova**: >= 9.0.0
- **Cordova Android**: >= 9.0.0
- **Android SDK**: Min 21, Target 34

---

## 🔧 Desenvolvimento

### Gerar novo .aar

Se você modificar o plugin nativo:

1. Vá para `/Users/emersonsampaio/Documents/plugin`
2. Execute: `./gradlew :axplugin:assembleRelease`
3. Copie o `.aar` gerado para `plugin-cordova/src/android/libs/`

---

## 📝 Notas Importantes

1. **Apenas simulação**: Os valores são aleatórios e não representam velocidade real
2. **Taxa de atualização**: 1 valor por segundo
3. **Range de valores**: 21-248 Mbps
4. **Taxa de sucesso**: 90% sucesso, 10% falhas simuladas
5. **Thread**: Callbacks executam na Main Thread (pode atualizar UI diretamente)

---

## 🐛 Troubleshooting

### Plugin não encontrado

Verifique se o plugin foi adicionado corretamente:
```bash
cordova plugin list
```

Deve aparecer: `cordova-plugin-axplugin`

### Erro "Plugin não configurado"

Certifique-se de chamar `setup()` antes de `start()`

### Valores não aparecem

1. Verifique se `start()` foi chamado
2. Verifique o callback de sucesso
3. Verifique logs: `adb logcat | grep AxPlugin`

### Build falha

Verifique se o `.aar` está em `src/android/libs/`

---

## 📄 Licença

MIT

---

## 👤 Autor

Emerson Sampaio
