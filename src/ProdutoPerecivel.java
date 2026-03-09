
//gêneros alimentícios são produtos perecíveis
//o produto não pode ter a data de validade anterior ao dia atual
//o valor de venda não pode ser solicitado para um produto com data de validade vencida

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProdutoPerecivel extends Produto {

  private static final double DESCONTO = 0.25; //desconto de 25% caso o vencimento esteja com prazo de 7 dias ou menos a partir de hoje
  private static final int PRAZO_DESCONTO = 7; //prazo de 7 dias ou menos
  private LocalDate dataDeValidade;

  public ProdutoPerecivel(String desc, double precoCusto, double margemLucro, LocalDate validade){
    super(desc, precoCusto, margemLucro);
    if (dataDeValidade.isBefore(LocalDate.now())){
      throw new IllegalArgumentException("O produto está vencido!");
    } else {
      this.dataDeValidade = validade;
    }
  }
    //Calcula o valor de venda considerando o desconto caso o vencimento esteja com prazo de 7 dias ou menos a partir de hoje. O desconto é aplicado multiplicando o valor de venda original pelo fator (1 - desconto), onde o desconto é representado como uma porcentagem (por exemplo, 0.25 para 25%). Se o produto estiver vencido, uma exceção é lançada.
    @Override
    public double valorVenda() {
        double desconto = 0d;
        int diasValidade = LocalDate.now().until(dataDeValidade).getDays();
        if (diasValidade <= PRAZO_DESCONTO) {
            desconto = DESCONTO;
        }
        else {
            throw new IllegalArgumentException("O produto está fora da data de validade!");
        }
        return (precoCusto * (1 + margemLucro)) * (1 - desconto);
    }

@Override
public String toString(){
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    String dados = super.toString();
    dados += "\nVálido até " + formato.format(dataDeValidade);
    return dados;
}

  /**
   * Gera uma linha de texto a partir dos dados do produto. Preço e margem de
   * lucro vão formatados com 2 casas decimais.
   * 
   * @return Uma string no formato "2; descrição;preçoDeCusto;margemDeLucro;dataDeValidade"
   */
  public String gerarDadosTexto(){
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return String.format("2;%s;%.2f;%.2f;%s", descricao, precoCusto, margemLucro, formato.format(dataDeValidade));
  }

  
  }


  
