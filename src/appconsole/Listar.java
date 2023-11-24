
package appconsole;

import daojpa.Util;
import models.Produto;
import models.TipoProduto;
import models.Venda;
import regras_negocio.Fachada;

public class Listar {

	public Listar() {
		try {
			Fachada.inicializar();
			System.out.println("\n---listagem de vendas:");
			for(Venda v: Fachada.listarVendas())
				System.out.println(v);

			System.out.println("\n---listagem de produtos:");
			for(Produto c: Fachada.listarProdutos())
				if(c.getTipoproduto() == null) {
					
				} else {
					System.out.println(c);
				}
				
			
			System.out.println("\n---listagem de tipos de produtos:");
			for(TipoProduto c: Fachada.listarTipoProdutos())
				System.out.println(c);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}
	public static void main(String[] args) {
		new Listar();
	}
}