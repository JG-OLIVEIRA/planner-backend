# Resumo do projeto
Projeto realizado durante a  16ª edição do Next Level Week, uma semana de aulas com muito código, desafios e networking com um único objetivo: te levar para o próximo nível, seja qual for seu momento de carreira.

## 🔨 Requisitos funcionais

- O usuário cadastra uma viagem informando o local de destino, data de início, data de término, e-mails dos convidados e também seu nome completo e endereço de e-mail;
- O criador da viagem recebe um e-mail para confirmar a nova viagem através de um link. Ao clicar no link, a viagem é confirmada, os convidados recebem e-mails de confirmação de presença e o criador é redirecionado para a página da viagem;
- Os convidados, ao clicarem no link de confirmação de presença, são redirecionados para a aplicação onde devem inserir seu nome (além do e-mail que já estará preenchido) e então estarão confirmados na viagem;
- Na página do evento, os participantes da viagem podem adicionar links importantes da viagem como reserva do AirBnB, locais para serem visitados, etc...
- Ainda na página do evento, o criador e os convidados podem adicionar atividades que irão ocorrer durante a viagem com título, data e horário;
- Novos participantes podem ser convidados dentro da página do evento através do e-mail e assim devem passar pelo fluxo de confirmação como qualquer outro convidado

## ✔️ Técnicas e tecnologias utilizadas

- [Java 22](https://www.oracle.com/java/technologies/javase-downloads.html)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Paradigma de orientação a objetos](https://en.wikipedia.org/wiki/Object-oriented_programming)

## 📦 Dependências

- [Spring Web](https://spring.io/projects/spring-web)
- [Flyway](https://flywaydb.org/)
- [Dev Tools](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-devtools)
- [Lombok](https://projectlombok.org/)
- [JPA](https://www.oracle.com/java/technologies/persistence-jsp.html)
- [H2 Database](https://www.h2database.com/html/main.html)

## Features Extras

- [x] Adicionar uma validação nos campos de data
   - [x] A data de começo da viagem é inferior à data de término da viagem
   - [x] A data de uma atividade está entre as datas da viagem

     **Exemplo:**
     Viagem entre os dias 20 a 25 de julho no Rio de Janeiro

     ⇒ Atividade 19 de julho

     ⇒ Atividade 21 de julho

- [x] Extração do core das trips para dentro de uma classe Service
- [ ] Mapeamento das exceções da aplicação
   - [ ] Tratativas de erro personalizadas
- [ ] Adicionar testes unitários e integração com o GitHub Actions


## 📁 Acesso ao projeto
Você pode acessar os arquivos do projeto clicando [aqui](https://github.com/JG-OLIVEIRA/planner-backend/tree/master/src).