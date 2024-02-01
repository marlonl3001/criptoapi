## Aplicativo Cripto Api

### BuildInfo
 * Gradle: 8.2
 * Kotlin: 1.9.0
 * Java: 1.8
 * compileSdk: 34
 * targetSdk: "34"
 * minSdk: "26"

### Recursos utilizados
 * Jetpack Compose
 * Material3
 * MVVM
 * Navigation
 * Room
 * Paging3
 * RemoteMediator
 * Hilt (injeção de depencência)
 * Retrofit/OkHttp
 * Gson
 * Compose Animation

### Features
 * ExchangesScreen - Tela com que carrega as informações paginadas das exchanges. Os dados são buscados utilizando RemoteMediator, que é responsável por 
 buscar os dados da API, salvar no Room e verificar quando é necessário atualizar os dados.
 * ExchangeDetailScreen - Tela que mostra detalhes da exchange selecionada, com dados de volume transacionado e gráfico com dados das principais criptomoedas
   nas últimas 24 horas.

### À disposição para dúdidas =D
