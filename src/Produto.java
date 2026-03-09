import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Produto {

  private static final double MARGEM_PADRAO = 0.2;
  protected String descricao; // descrição do produto
  protected double precoCusto; // preço de custo para a loja
  protected double margemLucro; // o lucro do comerciante
  // preço a ser cobrado: Preço de Venda = Preço de Custo + (Preço de Custo ×
  // Margem)

  // a função desse método é atribuir valores iniciais aos atributos da classe.
  /*
   * descrição: minímo de 3 caracteres
   * preço do produto: mínimo de R$: 0,01
   * margem de lucro: mínimo de R$: 0,01
   */
  private void init(String desc, double margemLucro, double precoCusto) {

    if ((desc.length() >= 3) && (precoCusto > 0.0) && (margemLucro > 0.0)) {
      descricao = desc;
      this.precoCusto = precoCusto;
      this.margemLucro = margemLucro;
    } else {
      throw new IllegalArgumentException("Valores inválidos para os dados do produto!!");
    }
  }

  protected Produto(String desc, double precoCusto, double margemLucro) {
    init(desc, margemLucro, precoCusto);
  }

  // nesse, não considera a margem de lucro, e sim a margem padrão, que é de 20%
  // (0.2), então chama o outro construtor, passando a margem padrão como
  // argumento.
  protected Produto(String desc, double precoCusto) {
    init(desc, MARGEM_PADRAO, precoCusto);
  }

  // Aqui retorna o valor da venda do produto, que é calculado multiplicando o
  // preço de custo pelo fator (1 + margem de lucro). O fator (1 + margem de
  // lucro) representa o aumento percentual aplicado ao preço de custo para obter
  // o preço de venda. Por exemplo, se a margem de lucro for 0.2 (20%), o fator
  // será 1.2, o que significa que o preço de venda será 120% do preço de custo.
  public double valorVenda() {
    return (precoCusto * (1.0 + margemLucro));
  }

  @Override
  public String toString() {

    NumberFormat moeda = NumberFormat.getCurrencyInstance();

    return String.format("NOME: " + descricao + ": " + moeda.format(valorVenda()));
  }

  /**
   * Igualdade de produtos: caso possuam o mesmo nome/descrição.
   * 
   * @param obj Outro produto a ser comparado
   * @return booleano true/false conforme o parâmetro possua a descrição igual ou
   *         não a este produto.
   */
  @Override
  //O método equals é sobrescrito para comparar dois objetos do tipo Produto com base em suas descrições. Ele verifica se a descrição do produto atual (this) é igual à descrição do outro produto (outro) ignorando diferenças de maiúsculas e minúsculas. Se as descrições forem iguais, o método retorna true, indicando que os produtos são considerados iguais; caso contrário, retorna false.
  //ou seja, se produto1 == produto2
  public boolean equals(Object obj) {
    Produto outro = (Produto) obj; //converte objeto para o tipo Produto
    return this.descricao.toLowerCase().equals(outro.descricao.toLowerCase()); //this.descricao se refere ao produto atual, enquanto outro.descricao se refere ao produto passado como argumento. O método toLowerCase() é usado para garantir que a comparação seja feita de forma case-insensitive, ou seja, sem considerar diferenças entre letras maiúsculas e minúsculas.

    
   
  }

  /**
     * Gera uma linha de texto a partir dos dados do produto
     * @return Uma string no formato "tipo;descrição;preçoDeCusto;margemDeLucro;[dataDeValidade]"
  */
   public abstract String gerarDadosTexto();

   /**
    * Cria um produto a partir de uma linha de dados em formato texto. A linha de dados deve estar de acordo com a formatação
    * "tipo; descrição;preçoDeCusto;margemDeLucro;[dataDeValidade]"
    * ou o funcionamento não será garantido. Os tipos são 1 para produto não perecível e 2 para perecível.
    * @param linha Linha com os dados do produto a ser criado.
    * @return Um produto com os dados recebidos.
    */

   //Este método é responsável por criar um objeto do tipo Produto a partir de uma linha de texto formatada. A linha de texto deve conter os dados do produto separados por ponto e vírgula (;), seguindo a estrutura "tipo; descrição;preçoDeCusto;margemDeLucro;[dataDeValidade]". O método analisa o tipo do produto (1 para não perecível e 2 para perecível) e, com base nisso, cria uma instância do produto correspondente, preenchendo seus atributos com os dados extraídos da linha de texto. Se o tipo for 1, um ProdutoNaoPerecivel é criado; se o tipo for 2, um ProdutoPerecivel é criado, utilizando a data de validade fornecida. O método retorna o objeto Produto criado com os dados recebidos.
   static Produto criarDoTexto(String linha){
    Produto novoProduto = null;
    String[] dados = linha.split(";");
    int tipo = Integer.parseInt(dados[0]);
    String descricao = dados[1];
    double precoCusto = Double.parseDouble(dados[2]);
    double margemLucro = Double.parseDouble(dados[3]);
    
    
    if(tipo == 1){
    novoProduto = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
    } else if (tipo == 2){
      DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      LocalDate dataValidade = LocalDate.parse(dados[4], formato);
      novoProduto = new ProdutoPerecivel(descricao, precoCusto, margemLucro, dataValidade);
        }
    return novoProduto;
   }



}