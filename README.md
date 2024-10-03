# Pokedéx

## 👨‍💻 Integrantes

- [x] Artur Muniz
- [x] Gabriel Meirinhos
- [x] João Gabriel
- [x] Ana Beatriz
- [x] Pedro Perazzo
- [x] Luiz Fernando

## 🖼 Figma

https://www.figma.com/file/wnxEIH8TGpnLTKQu1hfmyK?node-id=0:1&locale=en&type=design

# Documento de Requisitos - Aplicativo Mobile Pokedéx

## Visão Geral

### Objetivo
O aplicativo Pokédex será desenvolvido em Kotlin para Android e consumirá dados da PokeAPI. Ele permitirá aos usuários visualizar informações detalhadas sobre diferentes Pokémon e criar sua própria Pokédex personalizada, capturando e renomeando Pokémon. O aplicativo também contará com cadastro de usuários e login.

### Escopo

- [x] Interface de cadastro e login de usuários.
- [x] Mudança dos dados cadastrais.
- [x] Listagem de Pokémon com opção de busca.
- [x] Visualização de detalhes do Pokémon com atributos e evoluções.
- [x] Funcionalidade de capturar, renomear e libertar Pokémon.

## Requisitos Funcionais
### Autenticação de Usuários
- [x] **RF01**: O sistema deve permitir que novos usuários se cadastrem com nome, email e senha.
- [x] **RF02**: O sistema deve permitir o login de usuários cadastrados com email e senha.
- [x] **RF03**: O sistema deve exibir mensagens de erro em caso de falha no login (credenciais incorretas) ou cadastro (email já existente).

### Tela de Busca de Pokémon
- [x] **RF04**: Após o login, o sistema deve exibir uma lista de Pokémon em ordem alfabética, utilizando dados da PokeAPI.
- [x] **RF05**: O sistema deve permitir a busca por nome de Pokémon específico.
- [x] **RF06**: O sistema deve permitir ao usuário clicar em um Pokémon da lista para visualizar seus detalhes.

### Visualização de Detalhes do Pokémon
- [x] **RF07**: O sistema deve exibir os detalhes do Pokémon selecionado, incluindo imagem, descrição, atributos, e informações de evolução, tudo consumido da PokeAPI.
- [x] **RF08**: O sistema deve permitir que o usuário renomeie o Pokémon clicando no ícone de lápis.
- [x] **RF09**: O sistema deve oferecer a opção de capturar o Pokémon para adicionar à Pokédex do usuário.

### Pokédex Personalizada
- [x] **RF10**: O sistema deve ter uma seção chamada "Minha Pokédex", onde o usuário pode visualizar os Pokémon que ele capturou.
- [x] **RF11**: O sistema deve permitir que o usuário clique em um Pokémon capturado para visualizar suas informações.
- [x] **RF12**: O sistema deve permitir que o usuário renomeie um Pokémon capturado.
- [x] **RF13**: O sistema deve permitir que o usuário liberte um Pokémon capturado (removendo-o da Pokédex).

## Requisitos Não Funcionais

### Usabilidade
- [x] **RNF02**: O aplicativo deve ter uma interface intuitiva, permitindo navegação fácil entre as telas de busca, detalhes do Pokémon e Pokédex do usuário.

### Segurança
- [x] **RNF03**: As senhas dos usuários devem ser armazenadas de maneira segura utilizando criptografia.
- [x] **RNF04**: O aplicativo deve utilizar autenticação de sessão para proteger as informações do usuário.

## Fluxo de Navegação
### Fluxo de Cadastro e Login
- O usuário é recebido pela tela de login com a opção de "Entrar" ou "Cadastrar-se".
- Se o usuário não tiver uma conta, ele pode clicar em "Cadastrar-se" e preencher o formulário de cadastro.
- Após o cadastro, o usuário é redirecionado à tela de login para acessar o sistema.
- O usuário insere seu email e senha para fazer login.

## Fluxo de Busca de Pokémon
- Após o login, o usuário é levado à tela de busca, onde uma lista de Pokémon é exibida em ordem alfabética.
- O usuário pode clicar em um Pokémon para ver seus detalhes ou utilizar a barra de busca para localizar um Pokémon específico.

## Fluxo de Detalhes do Pokémon
- O usuário vê os detalhes do Pokémon selecionado, incluindo sua imagem, atributos e evoluções.
- O usuário pode clicar no ícone de lápis para renomear o Pokémon.
- O usuário pode clicar no botão "Capturar" para adicionar o Pokémon à sua Pokédex.

## Fluxo de Minha Pokédex
- O usuário clica em "Minha Pokédex" para visualizar a lista de Pokémon que capturou.
- O usuário pode clicar em um Pokémon capturado para ver seus detalhes e poderá alterar seu nome ou clicar em "Libertar" para remove-lo da Pokedéx

## Tecnologias e Ferramentas
- [x] Linguagem de Programação: Kotlin
- [x] API de Dados: PokeAPI
- [x] Autenticação: Firebase Authentication (ou similar)
- [x] Banco de Dados: Firebase Realtime Database / Postgre
- [x] IDE: Android Studio


