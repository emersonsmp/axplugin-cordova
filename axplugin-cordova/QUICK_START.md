# Quick Start - AxPlugin Cordova

Guia rápido para começar a usar o plugin em 5 minutos.

---

## ⚡ Instalação Rápida

### 1. Adicionar o plugin

```bash
# Local
cordova plugin add /Users/emersonsampaio/Documents/plugin-cordova

# Ou via ZIP
cordova plugin add cordova-plugin-axplugin.zip
```

### 2. Código JavaScript básico

```javascript
// Após deviceready
document.addEventListener('deviceready', function() {

    // 1. Configurar
    cordova.plugins.AxPlugin.setup('minha-chave',
        function() { console.log('Configurado!'); },
        function(err) { console.error(err); }
    );

    // 2. Iniciar
    document.getElementById('btnStart').onclick = function() {
        cordova.plugins.AxPlugin.start(
            function(velocidade) {
                document.getElementById('speed').textContent = velocidade + ' Mbps';
            },
            function(err) { console.error(err); }
        );
    };

    // 3. Parar
    document.getElementById('btnStop').onclick = function() {
        cordova.plugins.AxPlugin.stop(
            function() { console.log('Parado'); },
            function(err) { console.error(err); }
        );
    };
});
```

### 3. HTML básico

```html
<div id="speed">-- Mbps</div>
<button id="btnStart">Iniciar</button>
<button id="btnStop">Parar</button>
```

---

## 🎯 OutSystems (Resumo)

### Passo 1: Upload
- Compacte `plugin-cordova` em ZIP
- Faça upload no OutSystems

### Passo 2: Criar Action JavaScript

```javascript
// ConfigurarPlugin
cordova.plugins.AxPlugin.setup(
    $parameters.ApiKey,
    function() { $resolve({ Success: true }); },
    function(err) { $resolve({ Success: false }); }
);
```

```javascript
// IniciarTeste
cordova.plugins.AxPlugin.start(
    function(velocidade) {
        $parameters.OnVelocidadeAtualizada(velocidade);
    },
    function(err) { console.error(err); }
);
$resolve();
```

### Passo 3: Usar na Screen
- OnReady → ConfigurarPlugin
- Botão Iniciar → IniciarTeste
- Atualizar UI com velocidade

---

## 📊 O que esperar

Após iniciar o teste:
- Valores entre **21-248 Mbps**
- Atualização a cada **1 segundo**
- 90% sucesso, 10% falhas simuladas

---

## 🔍 Testar se funcionou

```javascript
// Verificar se está disponível
cordova.plugins.AxPlugin.isAvailable(
    function(available) {
        console.log('Disponível:', available === 1);
    },
    console.error
);
```

---

## ⚠️ Problemas Comuns

**Plugin não encontrado?**
```bash
cordova plugin list
# Deve aparecer: cordova-plugin-axplugin
```

**Erro "Plugin não configurado"?**
```javascript
// Sempre chame setup() antes de start()
cordova.plugins.AxPlugin.setup('chave', onSuccess, onError);
```

**Nada acontece?**
```javascript
// Certifique-se de aguardar deviceready
document.addEventListener('deviceready', function() {
    // Seu código aqui
});
```

---

## 📚 Próximos Passos

- Veja **README.md** para documentação completa
- Veja **OUTSYSTEMS_GUIDE.md** para guia detalhado OutSystems
- Veja **STRUCTURE.md** para entender a arquitetura

---

Pronto para usar! 🚀
