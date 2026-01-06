var exec = require('cordova/exec');

/**
 * Plugin Cordova para AxPlugin - Simulador de Teste de Velocidade
 *
 * Uso no OutSystems:
 *
 * 1. Setup:
 *    cordova.plugins.AxPlugin.setup("sua-chave", successCallback, errorCallback);
 *
 * 2. Start:
 *    cordova.plugins.AxPlugin.start(onSpeedUpdate, onError);
 *
 * 3. Stop:
 *    cordova.plugins.AxPlugin.stop(successCallback, errorCallback);
 */

var AxPlugin = {
    /**
     * Configura o plugin com a chave de API
     * @param {string} apiKey - Chave de autenticação do plugin
     * @param {function} success - Callback de sucesso
     * @param {function} error - Callback de erro
     */
    setup: function(apiKey, success, error) {
        if (!apiKey || typeof apiKey !== 'string') {
            error && error('API Key é obrigatória e deve ser uma string');
            return;
        }

        exec(success, error, 'AxPlugin', 'setup', [apiKey]);
    },

    /**
     * Inicia o teste de velocidade
     * Os valores de velocidade serão retornados via callback de sucesso
     * @param {function} success - Callback chamado a cada medição com o valor (21-248 Mbps)
     * @param {function} error - Callback de erro
     */
    start: function(success, error) {
        if (!success || typeof success !== 'function') {
            console.error('Success callback é obrigatório');
            return;
        }

        exec(success, error, 'AxPlugin', 'start', []);
    },

    /**
     * Para o teste de velocidade
     * @param {function} success - Callback de sucesso
     * @param {function} error - Callback de erro
     */
    stop: function(success, error) {
        exec(success, error, 'AxPlugin', 'stop', []);
    },

    /**
     * Verifica se o plugin está disponível (apenas Android)
     * @param {function} success - Callback de sucesso com boolean
     * @param {function} error - Callback de erro
     */
    isAvailable: function(success, error) {
        exec(success, error, 'AxPlugin', 'isAvailable', []);
    }
};

module.exports = AxPlugin;
