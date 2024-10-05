# RIBs (Router, Interactor, Builder, Component): Arquitetura Modular da Uber

A arquitetura RIBs (Router, Interactor, Builder, Component) foi desenvolvida pela Uber para resolver problemas específicos enfrentados por aplicativos móveis de grande escala, especialmente em ambientes com equipes grandes e funcionalidades complexas. Este modelo de arquitetura foi projetado com foco em modularização, escalabilidade e testabilidade.

## Funcionamento da Arquitetura RIBs

A arquitetura RIBs é uma abordagem centrada em módulos que separa a aplicação em diferentes unidades independentes chamadas RIBs. Esses módulos podem ser combinados e organizados para criar a aplicação inteira. A arquitetura é composta por quatro componentes principais:

- Router: O Router é responsável pela navegação entre diferentes módulos da aplicação. Ele define a estrutura de hierarquia e gerencia o fluxo entre os componentes (ou RIBs). Um Router também pode conter sub-RIBs, que se conectam a ele para formar uma estrutura de árvore. Esta abordagem torna o controle de navegação mais explícito e previsível, o que é essencial em aplicativos com navegações complexas. A modularização da navegação torna a manutenção mais simples, uma vez que cada RIB cuida apenas de sua parte específica da árvore de navegação.
  
- Interactor: O Interactor é o núcleo da lógica de negócios. Ele lida com as interações do usuário e a comunicação com outras camadas da aplicação, como as redes e bancos de dados. No Interactor, todas as regras de negócios são definidas, e ele também é responsável por controlar o estado do módulo. Cada Interactor é conectado a um Router, e ambos trabalham em conjunto para gerenciar a navegação e as interações de forma eficiente. Essa separação garante que a lógica de negócios permaneça desacoplada da lógica de navegação ou da interface do usuário.
  
- Builder: O Builder é o componente que cria as instâncias dos componentes Router, Interactor e outros que formam uma RIB. Ele segue o princípio da injeção de dependência, permitindo que as dependências necessárias sejam criadas e injetadas no momento certo. Isso melhora a modularidade e facilita os testes, pois o Builder permite criar diferentes instâncias com diferentes dependências, conforme necessário, sem impacto no resto do código.
Component: O Component gerencia a injeção de dependências, garantindo que os objetos necessários para o funcionamento de cada RIB sejam disponibilizados. Componentes podem ser gerados automaticamente por frameworks como Dagger, um framework de injeção de dependências popular no ecossistema Android. O uso de injeção de dependência ajuda a reduzir o acoplamento entre módulos, facilitando a substituição de implementações.

Cada RIB opera de maneira isolada, com responsabilidades claramente definidas. Isso faz com que seja mais fácil desenvolver, testar e manter o código, além de facilitar o desenvolvimento paralelo por várias equipes.

## Surgimento da Arquitetura RIBs

A arquitetura RIBs surgiu da necessidade de resolver os desafios de escalabilidade enfrentados pela Uber durante o desenvolvimento de seus aplicativos móveis. Por volta de 2016, a Uber estava passando por um rápido crescimento, e seu aplicativo móvel, que lida com múltiplas funcionalidades e centenas de desenvolvedores, começou a se tornar difícil de gerenciar usando arquiteturas tradicionais como MVP ou MVVM.

Essas arquiteturas tradicionais, embora eficazes para aplicativos menores, começaram a mostrar suas limitações em um ambiente altamente dinâmico e com uma vasta base de código. A complexidade de adicionar novas funcionalidades, navegar por diferentes módulos, e o gerenciamento de estado se tornaram barreiras para o desenvolvimento contínuo e ágil do aplicativo da Uber.

A solução foi criar a arquitetura RIBs, que se inspirou em algumas ideias de arquiteturas orientadas a componentes já conhecidas (como VIPER) e as evoluiu para se adaptar ao cenário de grande escala da Uber. O design modular e orientado a componentes permitiu que os engenheiros da Uber criassem funcionalidades isoladas, com equipes trabalhando em paralelo, sem impacto significativo no desempenho ou na estabilidade da aplicação.

## Propósito da Arquitetura RIBs

O principal propósito da arquitetura RIBs é permitir o desenvolvimento modular e escalável de grandes aplicativos, com foco em:

- **Modularidade**: O objetivo é dividir a aplicação em pequenos módulos, cada um responsável por uma função específica, facilitando a manutenção e a adição de novas funcionalidades.

- **Escalabilidade**: RIBs facilita a expansão do aplicativo, permitindo que equipes diferentes trabalhem em funcionalidades diferentes, sem que o código fique incontrolável. O modelo é especialmente adequado para ambientes onde há muitos desenvolvedores trabalhando simultaneamente.

- **Testabilidade**: Cada RIB é facilmente testável de forma independente, o que melhora a qualidade do software. Como os módulos são separados e têm dependências claramente definidas, a criação de testes unitários e de integração se torna mais simples.

- **Robustez**: RIBs promove a construção de sistemas mais robustos ao reduzir o risco de que mudanças em uma parte do código afetem outras áreas da aplicação, uma vez que cada RIB opera de maneira isolada.

## Problemas que a Arquitetura Resolve

A arquitetura RIBs resolve uma série de problemas que surgem em arquiteturas tradicionais quando aplicadas em grandes aplicações:

- **Gerenciamento de Complexidade**: Em aplicativos grandes, é comum que a complexidade do código aumente rapidamente. A modularização proporcionada pela RIBs resolve esse problema, permitindo que o aplicativo seja dividido em pequenas unidades funcionais, tornando o código mais gerenciável e compreensível.

- **Separação de Responsabilidades**: Cada RIB tem responsabilidades claramente definidas, o que elimina o problema de misturar lógica de negócios, navegação e interface de usuário em um mesmo módulo. Isso torna o código mais limpo e fácil de manter.

- **Desenvolvimento Paralelo**: Como as RIBs são independentes, equipes diferentes podem trabalhar simultaneamente em diferentes partes do aplicativo sem interferir no trabalho umas das outras. Isso é fundamental para aplicativos grandes e dinâmicos, como o da Uber, onde as funcionalidades são frequentemente desenvolvidas em paralelo.

- **Facilidade na Testabilidade**: Com a separação clara entre lógica de negócios, navegação e interface do usuário, os testes podem ser escritos de maneira mais eficaz. Cada RIB pode ser testada isoladamente, sem a necessidade de simular todo o aplicativo.

## Problemas que Ainda Existem no Modelo

Apesar de ser uma solução poderosa, a arquitetura RIBs também enfrenta alguns desafios:

- **Curva de Aprendizado Elevada**: O modelo RIBs é mais complexo do que arquiteturas como MVP ou MVVM, o que pode tornar sua adoção desafiadora para desenvolvedores que não estão familiarizados com seus conceitos. A necessidade de entender injeção de dependências e modularização avançada aumenta essa curva de aprendizado.

- **Sobrecarga na Estrutura**: Para aplicativos menores, a estrutura exigida pela RIBs pode ser um exagero. Em projetos simples, a formalização de componentes como Builder e Router pode adicionar complexidade desnecessária, tornando o desenvolvimento mais lento do que o necessário.

- **Complexidade de Navegação**: Embora a RIBs resolva muitos problemas de navegação em grandes sistemas, ela pode adicionar complexidade em cenários onde a navegação é simples. Definir rotas detalhadas e lidar com transições entre RIBs pode ser mais trabalho do que o necessário para aplicativos pequenos.

## Exemplos de Uso na Uber

Um exemplo de como a Uber implementou a arquitetura RIBs é no gerenciamento de fluxo de solicitação de corrida. Cada etapa do processo - desde o momento em que o usuário solicita um carro até o ponto de chegada - pode ser vista como uma RIB. O sistema é capaz de lidar com múltiplas mudanças de estado (busca por motoristas, rota em tempo real, etc.) sem que uma RIB interfira na outra, garantindo uma experiência fluida.
