# 🚪 Sistema de Controle de Acesso IoT

### 🖥️ Interface Web (Thymeleaf)
![Interface do Sistema](images/frontend.png)
*Figura : Painel de controle responsivo com status em tempo real.*

> Backend Spring Boot + Frontend Thymeleaf + ESP32 (Wokwi) + Cloudflare Tunnel

## 📖 Sobre o Projeto

Sistema de monitoramento e controle de fluxo de pessoas em ambientes com capacidade limitada. A solução integra um backend REST em Java/Spring Boot, uma interface web com Thymeleaf atualizada em tempo real, e um dispositivo IoT (ESP32) simulado no Wokwi, comunicando-se via túnel seguro do Cloudflare.

**Cenário de aplicação:** Controle de salas, elevadores, estacionamentos ou laboratórios com limite físico de ocupação.

---

## 🏗️ Arquitetura do Sistema
┌─────────────┐ HTTP/JSON ┌──────────────────┐ WiFi/HTTP ┌──────────────┐
│ Navegador │ ◄──────────────► │ Spring Boot │ ◄──────────────► │ ESP32 │
│ (Frontend) │ (Thymeleaf + │ (REST API + │ (Cloudflare │ (Wokwi Sim.) │
│ │ Fetch API) │ Lógica IoT) │ Tunnel) │ │
└─────────────┘ └──────────────────┘ └──────────────┘
▲ ▲ ▲
│ │ │
HTML/CSS/JS Java 17 + Spring 3.2.5 Arduino C++ + WiFi
Atualização a cada 5s Porta 8080 + Tomcat Embarcado GPIOs: Botões + LEDs


---

## ✨ Funcionalidades

| Recurso | Descrição |
|---------|-----------|
| 🟢 **Indicadores Visuais** | LED verde (disponível) e vermelho (lotado) com travamento lógico |
| 🔢 **Contador em Tempo Real** | Atualização automática do frontend a cada 5 segundos |
| 🚫 **Bloqueio Inteligente** | Impede novas entradas quando pessoas >= limite |
| ⚙️ **Configuração Dinâmica** | Alteração do limite máximo sem reiniciar o sistema |
| 🌐 **Acesso Remoto Seguro** | Cloudflare Tunnel expondo a API local para o Wokwi |
| 📊 **Log de Operações** | Histórico timestampado de entradas, saídas e alterações |

---

## 🛠️ Stack Tecnológico

| Camada | Tecnologia | Versão |
|--------|------------|--------|
| **Backend** | Java 17, Spring Boot 3.2.5, Maven | LTS |
| **Frontend** | Thymeleaf 3.1, HTML5, CSS3, ES6+ | - |
| **IoT** | ESP32, Arduino Core, WiFi HTTPClient | 2.0+ |
| **Infra** | Cloudflare Tunnel (quick-tunnel) | 2025.x |
| **Simulação** | Wokwi ESP32 Simulator | Web |

---

## 🚀 Guia de Execução

### 1. Pré-requisitos
- JDK 17+
- Apache Maven 3.8+
- Git
- Conta no Wokwi



### 2. Subir o Backend


git clone https://github.com/MaiG11/controlle-acesso-iot.git
cd controlle-acesso-iot
mvn spring-boot:run
Aguarde a mensagem: Tomcat started on port 8080 (http)

3. Acessar o Sistema
Frontend: http://localhost:8080
API Docs (JSON): http://localhost:8080/api/status


##  Referência da API REST

Todos os endpoints retornam `application/json`.

| Método | Rota | Descrição | Parâmetros |
|--------|------|-----------|------------|
| `GET` | `/api/status` | Estado atual do ambiente | - |
| `POST` | `/api/entrada` | Registra 1 entrada | - |
| `POST` | `/api/saida` | Registra 1 saída | - |
| `POST` | `/api/config/limite` | Altera capacidade máxima | `?limite=10` |
| `POST` | `/api/reset` | Zera contador e restaura padrão | - |

**Exemplo de Resposta JSON:**
```json
{
  "quantidadeDePessoasPresentes": 3,
  "limiteMaximoPermitido": 5,
  "estadoDoAmbiente": "DISPONIVEL",
  "acaoRealizada": "ENTRADA_PERMITIDA"
}

## 🔌 Montagem do Circuito (Wokwi)

| Componente | Pino ESP32 | Observação Técnica |
|------------|------------|-------------------|
| Botão Entrada | `GPIO 27` | `INPUT_PULLUP` (lógica invertida) |
| Botão Saída | `GPIO 26` | `INPUT_PULLUP` |
| LED Verde | `GPIO 4` | Resistor 220Ω em série (Ânodo → GPIO) |
| LED Vermelho | `GPIO 15` | Resistor 220Ω em série (Ânodo → GPIO) |
| GND | Comum | Terra compartilhado entre botões e LEDs |

###  Simulação IoT (Wokwi)
![Simulação ESP32](images/wokwi.png)
*Figura : Circuito com ESP32, botões e LEDs integrado à API.*

💡 Cenários de Teste
Fluxo Normal: 0/5 → Entrada → 1/5 → Entrada → ... → 5/5 🔴 → Entrada bloqueada
Liberação: 5/5 → Saída → 4/5 → Entrada permitida
Reconfiguração: Alterar limite para 3 → Sistema ajusta comportamento imediatamente.

## 🐛 Troubleshooting Rápido

| Sintoma | Causa Provável | Solução |
|---------|----------------|---------|
| `502 Bad Gateway` | API caída ou Cloudflare sem destino | Rode `mvn spring-boot:run` e reinicie o túnel |
| LED não acende | Pino errado ou polaridade invertida | Verifique GPIO 4/15 e perna maior do LED |
| Erro HTTP no Serial | URL do Cloudflare expirada | Copie a nova URL e atualize no `sketch.ino` |
| Página 404 | Arquivos fora de `templates/` ou `static/` | Valide a estrutura `src/main/resources/` |/


👨‍💻 Autora
Desenvolvido por: Maira Geraldo
Disciplina: Internet das Coisas | Período: Maio/2026
Spring Boot 3 Documentation
Thymeleaf Standard Dialect
ESP32 Technical Reference Manual
Cloudflare Quick Tunnels
