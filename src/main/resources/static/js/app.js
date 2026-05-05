// URL base da API (como está no mesmo servidor, usa caminho relativo)
const API_URL = '/api';

// Quando a página carregar, executa estas funções
document.addEventListener('DOMContentLoaded', function() {
    consultarStatus();  // Busca status inicial
    // Atualiza automaticamente a cada 5 segundos
    setInterval(consultarStatus, 5000);
});

// Função para consultar o status atual da API
async function consultarStatus() {
    try {
        const resposta = await fetch(`${API_URL}/status`);
        const dados = await resposta.json();
        atualizarInterface(dados);
    } catch (erro) {
        console.error('Erro ao consultar status:', erro);
        adicionarLog('Erro de conexão com o servidor', 'erro');
    }
}

// Função para registrar entrada
async function registrarEntrada() {
    try {
        const resposta = await fetch(`${API_URL}/entrada`, {
            method: 'POST'  // Envia requisição POST
        });
        const dados = await resposta.json();
        atualizarInterface(dados);
        adicionarLog(`Entrada: ${dados.acaoRealizada} - ${dados.quantidadeDePessoasPresentes} pessoas`, 'entrada');
    } catch (erro) {
        console.error('Erro ao registrar entrada:', erro);
        adicionarLog('Erro ao registrar entrada', 'erro');
    }
}

// Função para registrar saída
async function registrarSaida() {
    try {
        const resposta = await fetch(`${API_URL}/saida`, {
            method: 'POST'
        });
        const dados = await resposta.json();
        atualizarInterface(dados);
        adicionarLog(`Saída: ${dados.acaoRealizada} - ${dados.quantidadeDePessoasPresentes} pessoas`, 'saida');
    } catch (erro) {
        console.error('Erro ao registrar saída:', erro);
        adicionarLog('Erro ao registrar saída', 'erro');
    }
}

// Função para configurar o limite
async function configurarLimite() {
    const novoLimite = document.getElementById('novoLimite').value;
    
    // Validação
    if (novoLimite < 1 || novoLimite > 20) {
        alert('Por favor, insira um limite entre 1 e 20');
        return;
    }
    
    try {
        const resposta = await fetch(`${API_URL}/config/limite?limite=${novoLimite}`, {
            method: 'POST'
        });
        const dados = await resposta.json();
        atualizarInterface(dados);
        adicionarLog(`Limite atualizado para: ${novoLimite}`, 'entrada');
    } catch (erro) {
        console.error('Erro ao configurar limite:', erro);
        adicionarLog('Erro ao atualizar limite', 'erro');
    }
}

// Função para resetar o sistema
async function resetarSistema() {
    // Confirmação antes de resetar
    if (!confirm('Tem certeza que deseja resetar o sistema?')) {
        return;
    }
    
    try {
        const resposta = await fetch(`${API_URL}/reset`, {
            method: 'POST'
        });
        const dados = await resposta.json();
        atualizarInterface(dados);
        adicionarLog('Sistema resetado com sucesso', 'saida');
    } catch (erro) {
        console.error('Erro ao resetar sistema:', erro);
        adicionarLog('Erro ao resetar sistema', 'erro');
    }
}

// Função para atualizar a interface com os dados recebidos
function atualizarInterface(dados) {
    // Atualiza os números na tela
    document.getElementById('pessoasPresentes').textContent = dados.quantidadeDePessoasPresentes;
    document.getElementById('limiteMaximo').textContent = dados.limiteMaximoPermitido;
    
    // Atualiza o status visual (círculo e texto)
    const statusCircle = document.getElementById('statusCircle');
    const statusText = document.getElementById('statusText');
    
    if (dados.estadoDoAmbiente === 'LOTADO') {
        statusCircle.className = 'circle lotado';  // Classe CSS para vermelho
        statusText.textContent = '🔴 LOTADO';
        statusText.style.color = '#dc3545';
    } else {
        statusCircle.className = 'circle disponivel';  // Classe CSS para verde
        statusText.textContent = '🟢 DISPONÍVEL';
        statusText.style.color = '#28a745';
    }
    
    // Atualiza o campo de input do limite
    document.getElementById('novoLimite').value = dados.limiteMaximoPermitido;
}

// Função para adicionar mensagem no log
function adicionarLog(mensagem, tipo) {
    const logContainer = document.getElementById('logActions');
    const logItem = document.createElement('div');
    logItem.className = `log-item ${tipo}`;  // Adiciona classe CSS baseada no tipo
    
    // Adiciona horário atual
    const horario = new Date().toLocaleTimeString('pt-BR');
    logItem.textContent = `[${horario}] ${mensagem}`;
    
    // Remove mensagem inicial "Aguardando..." se existir
    if (logContainer.children[0]?.textContent === 'Aguardando ações...') {
        logContainer.innerHTML = '';
    }
    
    // Adiciona nova mensagem no início da lista
    logContainer.insertBefore(logItem, logContainer.firstChild);
    
    // Mantém apenas os últimos 10 logs (remove os mais antigos)
    while (logContainer.children.length > 10) {
        logContainer.removeChild(logContainer.lastChild);
    }
}