# Estrutura do Plugin Cordova AxPlugin

Documentação da estrutura e arquitetura do plugin.

---

## 📁 Estrutura de Arquivos

```
plugin-cordova/
│
├── plugin.xml                          # ⚙️ Configuração principal do plugin Cordova
├── package.json                        # 📦 Metadados NPM
│
├── www/                                # 🌐 Interface JavaScript
│   └── AxPlugin.js                    # API JavaScript exposta ao app
│
├── src/                                # 💻 Código nativo
│   └── android/                       # 🤖 Implementação Android
│       ├── AxPluginCordova.java      # Ponte Cordova ↔ .aar
│       ├── build.gradle              # Configuração Gradle
│       └── libs/                      # 📚 Bibliotecas nativas
│           └── axplugin-release.aar  # Biblioteca AxPlugin nativa
│
├── README.md                           # 📖 Documentação principal
├── OUTSYSTEMS_GUIDE.md                # 🎯 Guia OutSystems
├── STRUCTURE.md                       # 📋 Este arquivo
├── example.html                       # 🧪 Exemplo de teste
└── create-zip.sh                      # 🔨 Script para criar ZIP
```

---

## 🔄 Fluxo de Dados

```
┌─────────────────────────────────────────────────────────────┐
│                    OutSystems / JavaScript                   │
│                                                              │
│  cordova.plugins.AxPlugin.start(onSuccess, onError)         │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ↓
┌─────────────────────────────────────────────────────────────┐
│                    www/AxPlugin.js                           │
│                                                              │
│  exec(success, error, 'AxPlugin', 'start', [])              │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ↓
┌─────────────────────────────────────────────────────────────┐
│              Cordova Framework (Bridge)                      │
│                                                              │
│  Roteia chamada para o plugin nativo                        │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ↓
┌─────────────────────────────────────────────────────────────┐
│           src/android/AxPluginCordova.java                   │
│                                                              │
│  public boolean execute(String action, ...)                 │
│  → executeStart(callbackContext)                            │
│  → axPlugin.start()                                         │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ↓
┌─────────────────────────────────────────────────────────────┐
│      src/android/libs/axplugin-release.aar                   │
│                (Biblioteca Nativa Android)                   │
│                                                              │
│  class AxPlugin {                                           │
│    fun start() { ... }                                      │
│    → Gera valores 21-248                                    │
│    → Chama onSuccess(valor)                                 │
│  }                                                           │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ↓
┌─────────────────────────────────────────────────────────────┐
│           src/android/AxPluginCordova.java                   │
│                                                              │
│  new AxPluginEvents() {                                     │
│    onSuccess(int value) {                                   │
│      PluginResult result = new PluginResult(OK, value)      │
│      callbackContext.sendPluginResult(result)               │
│    }                                                         │
│  }                                                           │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ↓
┌─────────────────────────────────────────────────────────────┐
│              Cordova Framework (Bridge)                      │
│                                                              │
│  Envia resultado de volta para JavaScript                   │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ↓
┌─────────────────────────────────────────────────────────────┐
│                    www/AxPlugin.js                           │
│                                                              │
│  success(value) → onSuccess callback                        │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ↓
┌─────────────────────────────────────────────────────────────┐
│                    OutSystems / JavaScript                   │
│                                                              │
│  function(velocidade) {                                     │
│    console.log("Velocidade:", velocidade);                  │
│  }                                                           │
└─────────────────────────────────────────────────────────────┘
```

---

## 📄 Descrição dos Arquivos

### plugin.xml

**Propósito**: Define o plugin para o Cordova
**Conteúdo**:
- Metadados (nome, versão, autor)
- Dependências (engines)
- Mapeamento JavaScript → Java
- Arquivos a serem copiados no build

**Elementos chave**:
```xml
<js-module src="www/AxPlugin.js" name="AxPlugin">
    <clobbers target="cordova.plugins.AxPlugin" />
</js-module>
```
→ Torna o plugin acessível via `cordova.plugins.AxPlugin`

```xml
<source-file src="src/android/AxPluginCordova.java" />
```
→ Copia classe Java para o projeto Android

```xml
<lib-file src="src/android/libs/axplugin-release.aar"/>
```
→ Inclui a biblioteca .aar nativa

---

### www/AxPlugin.js

**Propósito**: Interface JavaScript que o desenvolvedor usa
**Funções**:
- `setup(apiKey, success, error)` - Configura o plugin
- `start(success, error)` - Inicia medições
- `stop(success, error)` - Para medições
- `isAvailable(success, error)` - Verifica disponibilidade

**Funcionamento**:
```javascript
exec(success, error, 'AxPlugin', 'start', []);
```
- **success**: Callback de sucesso
- **error**: Callback de erro
- **'AxPlugin'**: Nome do serviço (mapeia para classe Java)
- **'start'**: Ação a executar
- **[]**: Argumentos

---

### src/android/AxPluginCordova.java

**Propósito**: Ponte entre Cordova e a biblioteca nativa
**Classe**: Extends `CordovaPlugin`
**Métodos principais**:

#### `execute(String action, JSONArray args, CallbackContext callback)`
Roteador de ações. Recebe chamadas do JavaScript.

#### `executeSetup(JSONArray args, CallbackContext callback)`
Configura o AxPlugin nativo com a API key.

#### `executeStart(CallbackContext callback)`
Inicia o plugin nativo e registra callbacks para receber eventos.

#### `executeStop(CallbackContext callback)`
Para o plugin nativo e limpa callbacks.

**Callbacks**:
```java
new AxPluginEvents() {
    @Override
    public void onSuccess(int value) {
        // Envia valor para JavaScript
        PluginResult result = new PluginResult(Status.OK, value);
        result.setKeepCallback(true); // Mantém callback ativo
        callbackContext.sendPluginResult(result);
    }
}
```

**Keep Callback**:
- `true`: Permite múltiplos eventos (usado em `start`)
- `false`: Callback de uso único (usado em `setup`, `stop`)

---

### src/android/libs/axplugin-release.aar

**Propósito**: Biblioteca nativa Android com a lógica real
**Tamanho**: ~4KB
**Conteúdo**:
- `AxPlugin.class` - Classe principal
- `AxPluginEvents.class` - Interface de callbacks
- AndroidManifest.xml
- resources/

**API**:
```kotlin
class AxPlugin {
    fun setup(apiKey: String, events: AxPluginEvents)
    fun start()
    fun stop()
}

interface AxPluginEvents {
    fun onSuccess(value: Int)
    fun onFail(value: String)
}
```

---

### src/android/build.gradle

**Propósito**: Configuração Gradle para incluir o .aar
**Conteúdo**:
```gradle
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.aar'])
}
```

---

## 🔌 Integração Cordova

### Como o Cordova encontra o plugin?

1. **plugin.xml** define:
   - ID: `cordova-plugin-axplugin`
   - Nome do serviço: `AxPlugin`
   - Classe Java: `com.axplugin.cordova.AxPluginCordova`

2. **config.xml** do app (gerado automaticamente):
```xml
<feature name="AxPlugin">
    <param name="android-package" value="com.axplugin.cordova.AxPluginCordova"/>
</feature>
```

3. Quando JavaScript chama `exec(..., 'AxPlugin', ...)`:
   - Cordova procura feature `AxPlugin`
   - Instancia `AxPluginCordova`
   - Chama método `execute()`

---

## 🎯 OutSystems Integration

### Como o OutSystems usa o plugin?

1. **Upload do Plugin**:
   - OutSystems lê `plugin.xml`
   - Copia arquivos para projeto Cordova
   - Inclui na build Android

2. **JavaScript Actions**:
   - OutSystems permite JavaScript em Client Actions
   - Acessa `cordova.plugins.AxPlugin` diretamente

3. **Build**:
   - OutSystems gera app Cordova
   - Cordova compila para Android
   - Inclui .aar no APK final

---

## 🔄 Ciclo de Vida

### Inicialização
```
1. App inicia
2. Cordova carrega
3. AxPlugin.js disponível
4. JavaScript pode chamar setup()
```

### Execução
```
1. JavaScript: cordova.plugins.AxPlugin.start()
2. Cordova: Roteia para AxPluginCordova.execute("start")
3. Java: Chama axPlugin.start()
4. AAR: Gera valores e chama onSuccess(value)
5. Java: Envia PluginResult para Cordova
6. Cordova: Chama success callback JavaScript
7. JavaScript: Atualiza UI
```

### Limpeza
```
1. JavaScript: cordova.plugins.AxPlugin.stop()
2. Java: axPlugin.stop()
3. Handler.removeCallbacks()
4. Callbacks limpos
```

---

## 📊 Threads

### JavaScript
- Executa na **WebView Thread** (UI Thread)

### Java (AxPluginCordova)
- `execute()` chamado na **Cordova Thread**
- Callbacks para JavaScript enviados via `callbackContext`

### AAR (AxPlugin)
- `Handler` executa na **Main Thread** (Looper.getMainLooper())
- Callbacks `onSuccess()` na **Main Thread**

**Resultado**: Tudo acontece na Main Thread → Seguro para UI

---

## 🛠️ Build Process

### Quando instala o plugin:
```bash
cordova plugin add cordova-plugin-axplugin
```

1. Cordova lê `plugin.xml`
2. Copia `www/AxPlugin.js` para `platforms/android/assets/www/plugins/`
3. Copia `AxPluginCordova.java` para `platforms/android/src/`
4. Copia `axplugin-release.aar` para `platforms/android/libs/`
5. Atualiza `config.xml` com feature
6. Atualiza `build.gradle` com dependências

### Quando compila o app:
```bash
cordova build android
```

1. Gradle compila Java sources
2. Inclui .aar como dependência
3. Empacota tudo em APK
4. WebView pode acessar `cordova.plugins.AxPlugin`

---

## ✅ Validação

### Checklist de Funcionamento:

- [ ] `plugin.xml` válido
- [ ] `www/AxPlugin.js` exporta módulo
- [ ] `AxPluginCordova.java` extends CordovaPlugin
- [ ] `axplugin-release.aar` existe em libs/
- [ ] build.gradle inclui .aar
- [ ] Callbacks mantêm keepCallback=true
- [ ] Threads corretas (Main Thread)

---

## 🐛 Debug

### Logs JavaScript:
```javascript
console.log("AxPlugin:", cordova.plugins.AxPlugin);
```

### Logs Java:
```java
Log.d("AxPluginCordova", "Message");
```

### Ver logs:
```bash
adb logcat | grep -E "(AxPlugin|Cordova)"
```

---

## 📚 Referências

- [Cordova Plugin Development Guide](https://cordova.apache.org/docs/en/latest/guide/hybrid/plugins/)
- [Android Plugin Development](https://cordova.apache.org/docs/en/latest/guide/platforms/android/plugin.html)
- [PluginResult API](https://cordova.apache.org/docs/en/latest/guide/platforms/android/plugin.html#threading)

---

Estrutura completa documentada! 🚀
