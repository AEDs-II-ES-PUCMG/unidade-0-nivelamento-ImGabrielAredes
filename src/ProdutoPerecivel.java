import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProdutoPerecivel extends Produto {

    private double DESCONTO = 0.25;
    private int PRAZO_DESCONTO = 7;
    private LocalDate dataDeValidade;

    public ProdutoPerecivel(String desc, double precoCusto, double margemLucro, LocalDate dataDeValidade) {
        super (desc, precoCusto, margemLucro);
        if (dataDeValidade.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("O produto está vencido!");
        }
        dataDeValidade = dataDeValidade;

    }

    @Override
    public double valorDeVenda() {
        double desconto = 0d;
        int diasValidade = LocalDate.now().until(dataDeValidade).getDays();
        if (diasValidade <= PRAZO_DESCONTO) {
            desconto = DESCONTO;
        }
        return (precoCusto * (1 + margemLucro)) * (1 - desconto);
    }

    @Override
    public String toString(){
        DateTimeFormatter DataTimeFormatter = null;
        DateTimeFormatter formato = DataTimeFormatter.ofPattern("dd/mm/yyyy");

        String dados = super.toString();
        dados += "\nVálido até " + formato.format(dataDeValidade);
        return dados;
    }
}
