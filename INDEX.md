# 🗺️ AxPlugin Cordova - Índice de Navegação

**Plugin Cordova para usar AxPlugin nativo em aplicativos OutSystems**

---

## 🚀 Comece Aqui

### Para quem tem pressa
👉 **[QUICK_START.md](QUICK_START.md)** - Começar em 5 minutos

### Para uso no OutSystems
👉 **[OUTSYSTEMS_GUIDE.md](OUTSYSTEMS_GUIDE.md)** - Guia completo passo a passo

### Para entender tudo
👉 **[README.md](README.md)** - Documentação completa

---

## 📚 Documentação por Objetivo

### Quero usar no OutSystems
1. **[OUTSYSTEMS_GUIDE.md](OUTSYSTEMS_GUIDE.md)** - Guia detalhado
2. **[QUICK_START.md](QUICK_START.md)** - Resumo rápido
3. Execute `./create-zip.sh` para criar o ZIP

### Quero usar em app Cordova normal
1. **[README.md](README.md)** - Como instalar e usar
2. **[example.html](example.html)** - Exemplo funcional
3. Instale: `cordova plugin add .`

### Quero entender como funciona
1. **[STRUCTURE.md](STRUCTURE.md)** - Arquitetura completa
2. **[src/android/AxPluginCordova.java](src/android/AxPluginCordova.java)** - Código Java
3. **[www/AxPlugin.js](www/AxPlugin.js)** - Código JavaScript

### Quero ver histórico e mudanças
1. **[CHANGELOG.md](CHANGELOG.md)** - Histórico de versões
2. **[LICENSE](LICENSE)** - Licença MIT

---

## 📁 Arquivos Importantes

### Configuração
- **[plugin.xml](plugin.xml)** - Configuração principal do Cordova
- **[package.json](package.json)** - Metadados NPM
- **[src/android/build.gradle](src/android/build.gradle)** - Config Gradle

### Código
- **[www/AxPlugin.js](www/AxPlugin.js)** - Interface JavaScript
- **[src/android/AxPluginCordova.java](src/android/AxPluginCordova.java)** - Ponte Java
- **[src/android/libs/axplugin-release.aar](src/android/libs/axplugin-release.aar)** - Biblioteca nativa

### Documentação
- **[README.md](README.md)** - Docs principal
- **[OUTSYSTEMS_GUIDE.md](OUTSYSTEMS_GUIDE.md)** - Guia OutSystems
- **[STRUCTURE.md](STRUCTURE.md)** - Arquitetura
- **[QUICK_START.md](QUICK_START.md)** - Início rápido
- **[CHANGELOG.md](CHANGELOG.md)** - Histórico
- **[RESUMO_COMPLETO.md](RESUMO_COMPLETO.md)** - Resumo de tudo

### Exemplos e Scripts
- **[example.html](example.html)** - Exemplo de teste
- **[create-zip.sh](create-zip.sh)** - Criar ZIP para OutSystems

---

## 🎯 Casos de Uso

### Caso 1: Integrar no OutSystems

```
1. Leia: OUTSYSTEMS_GUIDE.md
2. Execute: ./create-zip.sh
3. Faça upload do ZIP no OutSystems
4. Crie as Client Actions conforme o guia
5. Use na sua Screen
```

### Caso 2: Usar em app Cordova

```
1. Leia: README.md (seção Instalação)
2. Execute: cordova plugin add /caminho/para/plugin-cordova
3. Use: cordova.plugins.AxPlugin.setup(), start(), stop()
4. Veja: example.html para referência
```

### Caso 3: Desenvolver/Modificar o plugin

```
1. Leia: STRUCTURE.md (entender arquitetura)
2. Modifique: www/AxPlugin.js ou src/android/AxPluginCordova.java
3. Atualize: CHANGELOG.md
4. Teste: Use example.html ou crie app teste
5. Build: ./create-zip.sh
```

---

## 📊 Estrutura Visual

```
plugin-cordova/
│
├── 📖 Documentação
│   ├── INDEX.md (você está aqui)
│   ├── README.md ⭐ Leia primeiro
│   ├── OUTSYSTEMS_GUIDE.md ⭐ Para OutSystems
│   ├── QUICK_START.md
│   ├── STRUCTURE.md
│   ├── CHANGELOG.md
│   └── RESUMO_COMPLETO.md
│
├── ⚙️ Configuração
│   ├── plugin.xml
│   ├── package.json
│   └── LICENSE
│
├── 💻 Código
│   ├── www/
│   │   └── AxPlugin.js
│   └── src/android/
│       ├── AxPluginCordova.java
│       ├── build.gradle
│       └── libs/
│           └── axplugin-release.aar
│
└── 🛠️ Ferramentas
    ├── example.html
    ├── create-zip.sh
    └── .gitignore
```

---

## ⚡ Comandos Rápidos

### Criar ZIP para OutSystems
```bash
./create-zip.sh
```

### Instalar localmente
```bash
cordova plugin add /Users/emersonsampaio/Documents/plugin-cordova
```

### Verificar instalação
```bash
cordova plugin list
# Deve aparecer: cordova-plugin-axplugin
```

### Ver estrutura
```bash
tree -L 3
```

---

## 🔍 Busca Rápida

**Procurando por...**

- **Como instalar?** → [README.md](README.md#instalação)
- **Como usar no OutSystems?** → [OUTSYSTEMS_GUIDE.md](OUTSYSTEMS_GUIDE.md)
- **API JavaScript?** → [README.md](README.md#api-completa)
- **Exemplo de código?** → [example.html](example.html)
- **Como funciona?** → [STRUCTURE.md](STRUCTURE.md)
- **Arquitetura?** → [STRUCTURE.md](STRUCTURE.md#fluxo-de-dados)
- **Código Java?** → [src/android/AxPluginCordova.java](src/android/AxPluginCordova.java)
- **Código JavaScript?** → [www/AxPlugin.js](www/AxPlugin.js)
- **Problemas comuns?** → [README.md](README.md#troubleshooting)
- **Criar ZIP?** → Execute `./create-zip.sh`

---

## 📞 Precisa de Ajuda?

### 1️⃣ Leia a documentação relevante
- **OutSystems**: [OUTSYSTEMS_GUIDE.md](OUTSYSTEMS_GUIDE.md)
- **Cordova**: [README.md](README.md)
- **Arquitetura**: [STRUCTURE.md](STRUCTURE.md)

### 2️⃣ Veja exemplos
- [example.html](example.html) - Exemplo completo funcional

### 3️⃣ Verifique logs
```bash
adb logcat | grep -i axplugin
```

---

## ✅ Checklist Inicial

Antes de começar, certifique-se:

- [ ] Leu [README.md](README.md) ou [QUICK_START.md](QUICK_START.md)
- [ ] Tem Cordova >= 9.0.0 instalado
- [ ] Tem Android SDK configurado
- [ ] Para OutSystems: Leu [OUTSYSTEMS_GUIDE.md](OUTSYSTEMS_GUIDE.md)

---

## 🎉 Versão Atual

**v1.0.0** - 05/01/2026

Veja [CHANGELOG.md](CHANGELOG.md) para detalhes.

---

**💡 Dica**: Se é sua primeira vez, comece por [QUICK_START.md](QUICK_START.md) ou [OUTSYSTEMS_GUIDE.md](OUTSYSTEMS_GUIDE.md)!

---

Criado com ❤️ para simplificar integração de plugins nativos em OutSystems
