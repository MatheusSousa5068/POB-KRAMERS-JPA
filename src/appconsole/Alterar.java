package appconsole;

import regras_negocio.Fachada;

public class Alterar {
	public Alterar() {
		try {
			Fachada.inicializar();
			Fachada.removerProdutoDeVenda(2, "Chocolate");		
			System.out.println("chocolate removido de venda 2");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Alterar();
	}
}