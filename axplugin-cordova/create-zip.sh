#!/bin/bash

echo "======================================"
echo "Criando ZIP do Plugin Cordova"
echo "======================================"
echo ""

# Nome do arquivo
ZIP_NAME="cordova-plugin-axplugin.zip"

# Remove ZIP anterior se existir
if [ -f "../$ZIP_NAME" ]; then
    rm "../$ZIP_NAME"
    echo "✓ ZIP anterior removido"
fi

# Verifica se o .aar existe
if [ ! -f "src/android/libs/axplugin-release.aar" ]; then
    echo "⚠️  ATENÇÃO: .aar não encontrado em src/android/libs/"
    echo "Execute primeiro o build do plugin Android"
    exit 1
fi

# Cria o ZIP
echo "Criando arquivo ZIP..."
cd ..
zip -r "$ZIP_NAME" plugin-cordova/ \
    -x "*.DS_Store" \
    -x "*/.git/*" \
    -x "*/node_modules/*" \
    -x "*.sh"

if [ -f "$ZIP_NAME" ]; then
    SIZE=$(du -h "$ZIP_NAME" | cut -f1)
    echo ""
    echo "======================================"
    echo "✓ ZIP criado com sucesso!"
    echo "======================================"
    echo ""
    echo "Arquivo: $ZIP_NAME"
    echo "Tamanho: $SIZE"
    echo "Localização: $(pwd)/$ZIP_NAME"
    echo ""
    echo "Próximos passos:"
    echo "1. Faça upload deste ZIP no OutSystems"
    echo "2. Ou instale via: cordova plugin add $(pwd)/$ZIP_NAME"
else
    echo ""
    echo "✗ Erro ao criar ZIP"
    exit 1
fi
