# Changelog

Todas as mudanças notáveis neste projeto serão documentadas aqui.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Semantic Versioning](https://semver.org/lang/pt-BR/).

---

## [1.0.0] - 2026-01-05

### Adicionado
- Plugin Cordova inicial para AxPlugin
- Interface JavaScript (www/AxPlugin.js)
- Ponte Java para Android (AxPluginCordova.java)
- Integração com biblioteca nativa .aar
- Métodos: setup(), start(), stop(), isAvailable()
- Suporte a callbacks contínuos (keepCallback)
- Documentação completa:
  - README.md - Documentação geral
  - OUTSYSTEMS_GUIDE.md - Guia para OutSystems
  - STRUCTURE.md - Arquitetura do plugin
- Exemplo HTML de teste
- Scripts de build (create-zip.sh)
- Testes e validação

### Características
- Plataforma: Android (API 21+)
- Cordova: >= 9.0.0
- Retorna valores entre 21-248 Mbps
- Taxa de atualização: 1 segundo
- Taxa de sucesso: 90%
- Execução na Main Thread (seguro para UI)

---

## Roadmap

### [1.1.0] - Planejado
- [ ] Suporte a iOS
- [ ] Opção de configurar intervalo de atualização
- [ ] Opção de configurar range de valores
- [ ] Eventos adicionais (onStart, onStop)
- [ ] Testes automatizados

### [1.2.0] - Futuro
- [ ] Configuração de taxa de erro
- [ ] Modo de replay de dados
- [ ] Suporte a Capacitor
- [ ] TypeScript definitions

---

## Suporte

Para reportar bugs ou solicitar features:
- GitHub Issues (quando disponível)
- Email: [seu-email]
