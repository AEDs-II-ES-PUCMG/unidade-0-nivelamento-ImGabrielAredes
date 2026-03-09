public class ItemDePedido {

    // Atributos encapsulados
    private Produto produto;
    private int quantidade;
    double precoVenda;

    /**
     * Construtor da classe ItemDePedido.
     * O precoVenda deve ser capturado do produto no momento da criação do item,
     * garantindo que alterações futuras no preço do produto não afetem este pedido.
     */
    public ItemDePedido(Produto produto, int quantidade, double precoVenda) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }
 // retorna o produto associado ao item
  public Produto getProduto() {
    return produto;
  }

  // retorna a quantidade atual desse item no pedido
  public int getQuantidade() {
    return quantidade;
  }

  // altera a quantidade do item no pedido
  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }

  // retorna o preço de venda armazenado
  public double getPrecoVenda() {
    return precoVenda;
  }


    public double calcularSubtotal() {
        return 0;
    }

    // --- Sobrescrita do método equals ---

    /**
     * Compara a igualdade entre dois itens de pedido.
     * A regra de negócio define que dois itens são iguais se possuírem o mesmo Produto.
     */
    @Override
    public boolean equals(Object obj) {

    if (this == obj) {
    return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
    return false;
    }

    ItemDePedido outro = (ItemDePedido) obj;

    return produto.equals(outro.produto);
    }

    public double valorTotalItem() {
    return precoVenda * quantidade;
    }

}
