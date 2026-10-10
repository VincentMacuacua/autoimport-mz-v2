# AutoImport MZ

Aplicação desktop em Java Swing para gestão de clientes de uma importadora de
viaturas: registo de clientes, cálculo de direitos aduaneiros e IVA conforme o
tipo de cliente (Particular/Normal, Particular/Doutorado, Empresarial/Estado,
Empresarial/Revendedor), pesquisa, edição, remoção, exportação de recibos em
PDF e um dashboard com estatísticas de vendas.

Desenvolvido por Vicente Macuácua como projeto académico de Programação
Orientada a Objetos, e posteriormente reorganizado como projeto de portefólio
(estrutura Maven, testes, segurança de credenciais).

## Funcionalidades

- **Autenticação** com registo de utilizadores e password.
- **Gestão de clientes**: adicionar, procurar, editar estado da compra e remover.
- **Hierarquia de classes** `Cliente` (abstrata) → `Particular` / `Empresarial`
  → `Normal`, `Doutorado`, `Estado`, `Revendedor`, cada um com a sua própria
  regra de cálculo de imposto/IVA/desconto.
- **Relatórios**: tabela de todos os clientes, tabela por tipo, exportação de
  compras para PDF (via iText).
- **Dashboard** com totais acumulados por tipo de cliente e margem da empresa.
- **Extra**: leitor de música e de vídeo (o utilizador escolhe o ficheiro).

## Tecnologias

- Java 17+ (Swing)
- FlatLaf (look-and-feel moderno), com paleta própria em `autoimport.ui.Tema`
- Maven
- iText 5 (geração de PDF)
- JUnit 5 (testes da lógica de cálculo de impostos)

## Estrutura do projeto

```
src/main/java/autoimport/
  model/        Cliente e as suas subclasses, Usuario, interface Calculos_API
  service/      Calculos, Pesquisa, Remover, Alteracao, Validacao
  persistence/  Ficheiro (leitura/escrita em disco), ExportarComprasPDF
  ui/           Janelas Swing (Login, Menu, formulários, tabelas, relatórios) e Tema (cores e componentes)
  util/         ResourcePaths (carregamento de imagens/média), PasswordUtil (hash)
src/main/resources/
  imagens/ musica/ video/    assets usados pela interface
src/test/java/autoimport/    testes JUnit da lógica de negócio
packaging/                   ícone do executável (usado pelo empacotar.ps1)
```

## Como correr

### Executável Windows (recomendado)

```powershell
.\empacotar.ps1
```

Gera `dist\AutoImportMZ\AutoImportMZ.exe`. O executável inclui o seu próprio
Java, por isso abre com duplo clique, sem precisar de Java, Maven ou
`JAVA_HOME` configurados. A pasta `dist\AutoImportMZ` pode ser copiada para
outro PC Windows tal como está.

### Durante o desenvolvimento

```bash
./mvnw compile exec:java
```

Ou gerar só o `.jar` (com todas as dependências incluídas):

```bash
./mvnw package
java -jar target/autoimport-mz-standalone.jar
```

### Onde ficam os dados

Os utilizadores, clientes e o PDF exportado ficam em
`%USERPROFILE%\AutoImportMZ\data` (por exemplo
`C:\Users\<nome>\AutoImportMZ\data`), seja qual for a forma como a app é
lançada. A pasta é criada automaticamente na primeira execução; para usar
outra, arranca com `-Dautoimport.dados=<caminho>`.

Na primeira execução ainda não há utilizadores: usa o botão **Criar conta**
no ecrã de login. Depois de o registares, ele passa a aparecer na lista.

## Testes

```bash
mvn test
```

Os testes cobrem a lógica de cálculo de impostos/IVA/desconto de cada tipo de
cliente (`src/test/java/autoimport/model`) e os acumuladores de relatório
(`src/test/java/autoimport/service`), que são a parte do sistema com mais
regras de negócio e mais fácil de testar isoladamente da interface gráfica.

## Segurança de credenciais

As passwords são guardadas em `users.txt` (na pasta de dados) como hash SHA-256 (ver
`autoimport.util.PasswordUtil`), não em texto simples. Isto é suficiente para
um projeto de portefólio, mas não é o que se usaria em produção — falta sal
por utilizador e um algoritmo pensado para passwords (ex. bcrypt/argon2), que
tornam ataques de força bruta mais lentos.

## Limitações conhecidas / próximos passos

- Os valores monetários usam `float`/`double` em vez de `BigDecimal`, o que é
  aceitável aqui mas não é ideal para cálculos financeiros de precisão.
- A persistência é feita em ficheiros de texto (`;` como separador) em vez de
  uma base de dados — funciona bem à escala de um projeto académico, mas não
  escala nem lida bem com escrita concorrente.
- `Menu` e `NovoClinete` ainda misturam construção de interface com lógica de
  eventos; foram divididos em métodos privados por secção para ficarem
  legíveis, mas uma versão futura poderia separar isso num padrão MVC mais
  explícito.

## Nota sobre os ficheiros de música/vídeo

Os ficheiros em `src/main/resources/musica/` e `src/main/resources/video/`
são conteúdo de terceiros usados apenas como amostras locais para testar o
leitor de música/vídeo — não estão cobertos pela licença MIT deste repositório
(ver `LICENSE`).
