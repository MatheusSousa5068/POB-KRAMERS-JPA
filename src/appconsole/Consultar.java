package appconsole;

import models.Venda;
import regras_negocio.Fachada;

public class Consultar {

	public Consultar() {
		try {
			Fachada.inicializar();

			System.out.println("consultas... \n");
			System.out.println("\nVendas na data 01/09/2023");
			for(Venda v : Fachada.vendaDataX("01/09/2023"))
				System.out.println(v);


			System.out.println("\nVendas com mais de 1 Produto");
			for(Venda v: Fachada.vendasComMaisDeNProdutos(1))
				System.out.println(v);


			System.out.println("\nVendas com produtos de preço de R$4,50");
			for(Venda v : Fachada.vendasComProdutoDePrecoX(4.5))
				System.out.println(v);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Consultar();
	}
}