package appconsole;

import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar() {
		try {
			Fachada.inicializar();
			System.out.println("cadastrando tipoprodutos...");
			Fachada.cadastrarTipoProduto("Frutas");
			Fachada.cadastrarTipoProduto("Bebidas");
			Fachada.cadastrarTipoProduto("Doces");
			Fachada.cadastrarTipoProduto("Diversos");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("cadastrando produtos...");
			Fachada.cadastrarProduto("Chocolate", 4.00, "Doces");
			Fachada.cadastrarProduto("Gelatina de Morango", 3.25, "Doces");
			Fachada.cadastrarProduto("Coca-Cola", 4.50, "Bebidas");
			Fachada.cadastrarProduto("Café", 2.00, "Bebidas");
			Fachada.cadastrarProduto("Banana", 0.50, "Frutas");
			Fachada.cadastrarProduto("Morango", 1.00, "Frutas");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			System.out.println("cadastrar vendas...");
			Fachada.cadastrarVenda("01/09/2023", 50);
			Fachada.adicionarProdutoEmVenda(1, "Chocolate");
			Fachada.adicionarProdutoEmVenda(1, "Banana");
			Fachada.adicionarProdutoEmVenda(1, "Morango");
			
			Fachada.cadastrarVenda("03/09/2023", 25);
			Fachada.adicionarProdutoEmVenda(2, "Coca-Cola");
			Fachada.adicionarProdutoEmVenda(2, "Chocolate");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Cadastrar();
	}
}