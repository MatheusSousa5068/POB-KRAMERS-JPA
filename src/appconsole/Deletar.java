package appconsole;

import regras_negocio.Fachada;

public class Deletar {

	public Deletar() {
		try {
			Fachada.inicializar();
			Fachada.excluirTipoProduto("Doces");		 
			System.out.println("Doces excluído");
			
//            Fachada.excluirProduto("Chocolate");
//			System.out.println("Chocolate Excluído");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}
	public static void main(String[] args) {
		new Deletar();
	}
}