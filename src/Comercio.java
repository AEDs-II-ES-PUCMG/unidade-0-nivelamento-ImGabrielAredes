import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Comercio {

  /** Para inclusão de novos produtos no vetor */
  static final int MAX_PRODUTOS = 10; // static porque é uma constante, ou seja, o valor não pode ser alterado

  /**
   * Nome do arquivo de dados. O arquivo deve estar localizado na raiz do projeto
   */
  static String nomeArquivoDados; // não tem o final, porque o nome do arquivo pode ser diferente, então é
                                  // necessário receber o nome do arquivo como argumento para o programa.

  /** Scanner para leitura do teclado */
  static Scanner teclado;

  /**
   * Vetor de produtos cadastrados. Sempre terá espaço para 10 novos produtos a
   * cada execução
   */
  static Produto[] produtosCadastrados;

  /** Quantidade de produtos cadastrados atualmente no vetor */
  static int quantosProdutos;

  /** Gera um efeito de pausa na CLI. Espera por um enter para continuar */
  static void pausa() {
    System.out.println("Digite enter para continuar...");
    teclado.nextLine();
  }

  /** Cabeçalho principal da CLI do sistema */
  static void cabecalho() {
    System.out.println("AEDII COMÉRCIO DE COISINHAS");
    System.out.println("===========================");
  }

  /**
   * Imprime o menu principal, lê a opção do usuário e a retorna (int).
   * Perceba que poderia haver uma melhor modularização com a criação de uma
   * classe Menu.
   * 
   * @return Um inteiro com a opção do usuário
   */
  static int menu() {
    cabecalho();
    System.out.println("1- Listar todos os produtos");
    System.out.println("2- Prcurar e listar um produto");
    System.out.println("3- Cadastrar novo produto");
    System.out.println("0- Sair");
    System.out.println("Digite sua opção: ");
    return Integer.parseInt(teclado.nextLine()); // essa linha lê a opção do usuário e a converte para inteiro, já que o
                                                 // método nextLine() retorna uma string. O Integer.parseInt() é usado
                                                 // para converter a string lida em um número inteiro, que é o tipo de
                                                 // dado esperado para a opção do menu.

    /**
     * Lê os dados de um arquivo texto e retorna um vetor de produtos. Arquivo no
     * formato:
     * N (quntidade de produtos)
     * tipo;descrição;preçoDeCusto;margemDeLucro;[dataDeValidade]
     * Deve haver uma linha para cada produto. Retorna um vetor vazio em caso de
     * problemas com o arquivo.
     * 
     * @param nomeArquivoDado Nome do arquivo de dados a ser aberto.
     * @return Um vetor com os produtos carregados, ou vazio em caso de problema de
     *         leitura.
     */

  }

  static Produto[] lerProdutos(String nomeArquivoDados) {

    try {

      Scanner arquivo = new Scanner(new File(nomeArquivoDados));

      String primeiraLinha = arquivo.nextLine();
      int quantidade = Integer.parseInt(primeiraLinha);

      quantosProdutos = quantidade;

      Produto[] vetorProdutos = new Produto[quantidade + MAX_PRODUTOS];

      for (int i = 0; i < quantidade; i++) {
        String linha = arquivo.nextLine();
        vetorProdutos[i] = Produto.criarDoTexto(linha);
      }

      return vetorProdutos;

    } catch (Exception e) {
      System.out.println("Erro ao abrir o arquivo.");
      return new Produto[0];
    }
  }

  /** Lista todos os produtos cadastrados, numerados, um por linha */
  static void listarTodosOsProdutos() {
    for (int i = 0; i < quantosProdutos; i++) {
      System.out.println((i + 1) + " - " + produtosCadastrados[i].toString());
    }
  }

  /**
   * Localiza um produto no vetor de cadastrados, a partir do nome (descrição), e
   * impreme seus dados.
   * A busca não é sensível ao caso. Em caso de não encontrar o produto, imprime
   * mensagem padrão
   */
  static void localizarProdutos() {
    System.out.println("Digite o nome do produto para ser localizado: ");
    String nomeProduto = teclado.nextLine();
    for (int i = 0; i < quantosProdutos; i++) {
      if (produtosCadastrados[i].descricao.equalsIgnoreCase(nomeProduto)) {
        System.out.println(produtosCadastrados[i].toString());
        return;
      }
    }
    System.out.println("Produto não encontrado.");
  }

  /**
   * Rotin de cadastro de um novo produto: pergunta ao usuário o tipo do produto,
   * lê os dados correspondentes, cria o objeto adequado de acordo com o tipo,
   * inclui no vetor. Este método pode ser feito com um nível muito melhor de
   * modularização. As diversas fases da lógica poderiam ser encapsuladas em
   * outros métodos.
   */
  static void cadastrarProduto() {
    System.out.println("Digite o tipo do produto para cadastro (1 - Não Perecível, 2 - Perecível): ");
    int tipo = teclado.nextInt();
    teclado.nextLine();
    System.out.println("Digite a descrição do produto: ");
    String descricao = teclado.nextLine();
    System.out.println("Digite o preço de custo do produto: ");
    double precoCusto = teclado.nextDouble();
    System.out.println("Digite a margem de lucro do produto (ex: 0.2 para 20%): ");
    double margemLucro = teclado.nextDouble();

    Produto novoProduto;

    if (tipo == 1) {
      novoProduto = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
    } else {
      System.out.println("Digite a data de validade do produto (dd/MM/YYYY): ");
      DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      LocalDate data = LocalDate.parse(teclado.nextLine(), formato);
      novoProduto = new ProdutoPerecivel(descricao, precoCusto, margemLucro, data);
    }
    produtosCadastrados[quantosProdutos] = novoProduto;
    quantosProdutos++;
  }

  /**
   * Salva os dados dos produtos cadastrados no arquivo csv informado. Sobescreve
   * todo o conteúdo do arquivo.
   * 
   * @param nomeArquivo Nome do arquivo a ser gravado.
   * @throws FileNotFoundException
   */
  public static void salvarProdutos(String nomeArquivo) {

    try {

      PrintWriter writer = new PrintWriter(nomeArquivo);

      writer.println(quantosProdutos);

      for (int i = 0; i < quantosProdutos; i++) {
        writer.println(produtosCadastrados[i].toString());
      }

      writer.close();

    } catch (Exception e) {
      System.out.println("Erro ao salvar o arquivo.");
    }

  }

  public static void main(String[] args) throws Exception {

    teclado = new Scanner(System.in, Charset.forName("ISO-8859-2"));
    nomeArquivoDados = "dadosProdutos.csv";

    produtosCadastrados = lerProdutos(nomeArquivoDados);

    int opcao = -1;

    do {

        opcao = menu();

        switch (opcao) {
            case 1 -> listarTodosOsProdutos();
            case 2 -> localizarProdutos();
            case 3 -> cadastrarProduto();
        }

        pausa();

    } while(opcao != 0);

    salvarProdutos(nomeArquivoDados);

    teclado.close();
}
}
