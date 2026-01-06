package com.axplugin.cordova;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import com.axplugin.AxPlugin;
import com.axplugin.AxPluginEvents;

/**
 * Plugin Cordova que faz a ponte entre JavaScript e o AxPlugin nativo (.aar)
 *
 * Este plugin permite que aplicativos OutSystems usem o AxPlugin nativo Android
 * para simular testes de velocidade de internet.
 */
public class AxPluginCordova extends CordovaPlugin {

    private AxPlugin axPlugin;
    private CallbackContext startCallbackContext;
    private boolean isConfigured = false;

    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
        axPlugin = new AxPlugin();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if ("setup".equals(action)) {
            return executeSetup(args, callbackContext);
        }
        else if ("start".equals(action)) {
            return executeStart(callbackContext);
        }
        else if ("stop".equals(action)) {
            return executeStop(callbackContext);
        }
        else if ("isAvailable".equals(action)) {
            return executeIsAvailable(callbackContext);
        }

        return false;
    }

    /**
     * Configura o plugin com a chave de API
     */
    private boolean executeSetup(JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (args.length() < 1) {
            callbackContext.error("API Key é obrigatória");
            return true;
        }

        String apiKey = args.getString(0);

        if (apiKey == null || apiKey.isEmpty()) {
            callbackContext.error("API Key não pode ser vazia");
            return true;
        }

        // Configura o plugin nativo com eventos
        axPlugin.setup(apiKey, new AxPluginEvents() {
            @Override
            public void onSuccess(int value) {
                // Este callback será chamado pelo método start()
                // Aqui não fazemos nada pois os eventos são tratados no start()
            }

            @Override
            public void onFail(String value) {
                // Este callback será chamado pelo método start()
                // Aqui não fazemos nada pois os eventos são tratados no start()
            }
        });

        isConfigured = true;
        callbackContext.success("Plugin configurado com sucesso");
        return true;
    }

    /**
     * Inicia o teste de velocidade
     * Os valores serão retornados continuamente via callback
     */
    private boolean executeStart(CallbackContext callbackContext) {
        if (!isConfigured) {
            callbackContext.error("Plugin não configurado. Chame setup() primeiro.");
            return true;
        }

        // Armazena o callback para enviar múltiplos resultados
        startCallbackContext = callbackContext;

        // Reconfigura o plugin com os callbacks atualizados
        axPlugin.setup("configured-key", new AxPluginEvents() {
            @Override
            public void onSuccess(int value) {
                if (startCallbackContext != null) {
                    PluginResult result = new PluginResult(PluginResult.Status.OK, value);
                    result.setKeepCallback(true); // Mantém o callback ativo para múltiplos eventos
                    startCallbackContext.sendPluginResult(result);
                }
            }

            @Override
            public void onFail(String errorMessage) {
                if (startCallbackContext != null) {
                    PluginResult result = new PluginResult(PluginResult.Status.ERROR, errorMessage);
                    result.setKeepCallback(true); // Mantém o callback ativo
                    startCallbackContext.sendPluginResult(result);
                }
            }
        });

        // Inicia o plugin nativo
        axPlugin.start();

        // Não envia resultado imediato, os resultados virão via callbacks
        return true;
    }

    /**
     * Para o teste de velocidade
     */
    private boolean executeStop(CallbackContext callbackContext) {
        axPlugin.stop();

        // Limpa o callback do start
        if (startCallbackContext != null) {
            PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
            result.setKeepCallback(false); // Remove o callback
            startCallbackContext.sendPluginResult(result);
            startCallbackContext = null;
        }

        callbackContext.success("Plugin parado com sucesso");
        return true;
    }

    /**
     * Verifica se o plugin está disponível (sempre true no Android)
     */
    private boolean executeIsAvailable(CallbackContext callbackContext) {
        callbackContext.success(1); // true
        return true;
    }

    /**
     * Limpa recursos quando o plugin é destruído
     */
    @Override
    public void onDestroy() {
        if (axPlugin != null) {
            axPlugin.stop();
        }
        super.onDestroy();
    }
}
