## Customer API - Documentação

*Código fonte:* 

https://github1s.com/rscanesilva/sboot-builders-customer

*URL projeto Hospedado no GKE (Google Kubernetes Engine)*

????

### Desafio Proposto

**Desenvolver uma REST API com os seguites critérios:**

    - Permita criação de novos clientes;
    - Permita a atualização de clientes existentes;
    - Permita que seja possível listar os clientes de forma paginada;
    - Permita que buscas por atributos cadastrais do cliente;
    - É necessário também que cada elemento retornado pela api de clientes informe a idade;
    - Documente sua API e também disponibilize um arquivo Postman para fácil utilização da API.
 
### Tecnologias utilizadas

- Java 11 + Springboot
- Spring Data JPA + Mysql
- Junit + Mockito + RestAssured
- Spring Hateoas
- OpenAPI 3.0
- Docker + Docker Compose
- Kubernetes (deploy no Google Kubernetes Engine)

### SOLID
> Cinco princípios de orientação a objetos.

- **1 Single Responsiblity Principle - (Responsabilidade Única)**
    - As classes foram modeladas de forma que possuem fraco acoplamento e seus atributos e métodos são coesos.
- **2 Open-Closed Principle - (Aberto/Fechado)**
    - Se refere as entidades serem abertas para extenção e fechadas para modificação. Princípio implementado na modelagem do Objeto "Document".
- **3 Liskov Substitution Principle - (subustituição de Liskov)**  : *Não foi utilizado*
- **4 Interface Segregation Principle - (Segregaçõ de interface)**  : *Não foi utilizado*
- **5 Dependency Inversion Principle: - (Inversão de dependência)**
    - Utilizado neste projeto para acesso a base de dados de forma totalmente desacoplada do tipo da origem dos dados e frameworks utilizados para acessá-los. No módulo "Domain" para não termos nenhum acoplamento a origem e tipo dos dados externos, apenas disponibilizamos a interface (contrato) de acesso aos dados que foi implementada no módulo "Application". 
    Fazendo assim, o dominío não tem nenhum acoplamento com a modelo e ferramentas que fazem acesso aos dados. Se um dia precisar troca o banco de dados Mysql Para MongoDb por exemplo, a camada de domínio não seria impactada.

### Arquitetura Hexagonal
> Padrão arquitetural que visa descoplar a domínio de frameworks, datasources e qualquer dependência que não faça parte do domínio 

##### O microserviço foi desenvolvido com springboot + maven e divido em três módulos:
    - Application
        - As apis RESTFul e as implementações de acesso a banco de dados ficam nessa camada
    - Common
        - Classes utilitárias ( não utilizado nesse projeto )
    - Domain
        - Onde ficam as classes de domíno e interfaces para acesso externo.

### RESTFul
#####  O projeto implementa os 4 níveis de maturidade de Richardson

- **Nível 0: HTTP**
    - Ultiliza o protocolo de comunicação http
- **Nível 1: HTTP + Recursos**
    - Segue o modelo de recursos, ex "/customers", "/customer/12323423-1231-".
- **Nível 2: HTTP + Recursos + Verbos**
    - Uso dos verbos Http para cada ação: GET / POST / PUT / DELETE
- **Nível 3: HTTP + Recursos + Verbos + HATEOAS**
    - Fornece os hyperlinks na resposta da apis

### Teste
##### Testes desenvolvidos:
- Unitários
    - Implementado boa parte dos teste unitários que faziam sentido para aplicação;
- Funcionais
    - Vai desde o consumo das APIS até persistência dos dados.
    - No projeto foi implementado dois CRUDS completos, um para cliente PF e outro para cliente PJ, e mais alguns testes com dados de entrada inválidos para validar as respostas e status code.
    
### Documentação:
- #### OpenAPI 3.0
> http://localhost:8080/swagger-ui.html

### Como Rodar a aplicação local?
**Pré requisitos**

- Java 11 
- Maven 
- Docker
- Docker Composer 

**Instalação**

- Faça o clone do repositório git em: https://github.com/rscanesilva/sboot-builders-customer
- Acesse a past raiz do projeto e execute o comando
```
$ git clone https://github.com/rscanesilva/sboot-builders-customer.git
$ cd sboot-builders-custome
$ mvn client package
```

- Acesse raíz do módulo "Application" está está armazenado os arquivos *DockerFile* e *docker-compose.yaml*
- Execute o seguinte comando
```
$ docker-composer up
```
- **Pronto** - sua a aplicação irá iniciar no host localhost:8080 - acesso a página da documentação em https://localhost:8080/swagger-ui.html

