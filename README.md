# TaskIt - Organizador de Tarefas com PostIt's
Projeto Final implementado em Java para a disciplina MC322 - Programação Orientada a Objetos, ministrada pelo Prof. Dr. Marcelo da Silva Reis na Universidade Estadual de Campinas (UNICAMP).

## Autores:
Victor Yaegashi Setti - RA: 206362<br>
Iago Lucini da Silva - RA: 281244<br>
Agnes Manoel - RA: 232879<br>
Bernardo Correia Prados Nascimento - RA: 245837

## Diferenças com a Proposta Original:
Houveram algumas mudanças com relação a proposta original devido a um prazo de tempo curto, e uma maior dificuldade com o manejo da interface gráfica do que o esperado.<br>
Dentre elas estão:
- O conceito de "metas" foi aglutinado por "subtarefas"
- Tasks não serã compartilhadas entre diferentes usuários

## Interfaces e Classes Abstratas
- **Interface:** `Task`
- **Classes Abstratas:** `TaskAbstrata` e `Usuario`

## Interface gráfica:
- `JavaX Swing`

## Exceções Implementadas:
- `DataInvalidaException`
- `NomeDuplicadoException`
- `UsuarioInvalidoException`

## Gravação e Leitura de Arquivos:
- `JavaIO Serializable`

# Diagrama UML:
Este diagrama UML está bem diferente do enviado na Atividade 3, mas representa de maneira muito mais organizada e fiel o estado atual do projeto.<br>
**Classes responsáveis pela interface gráfica e seu funcionamento foram desconsideradas na elaboração do diagrama, uma vez que não têm relação direta com as classes do projeto. De mesmo modo, os getters e setters foram ocultados a fim de manter um maior grau de clareza e simplicidade, todos os atributos possuem getters e setters**
![](diagrama/diagrama-finalizado.svg)
