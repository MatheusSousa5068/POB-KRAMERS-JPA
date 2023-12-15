package regras_negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import daojpa.DAO;
import daojpa.DAOProduto;
import daojpa.DAOTipoProduto;
import daojpa.DAOVenda;
import models.Produto;
import models.TipoProduto;
import models.Venda;

public class Fachada {
	private Fachada() {
	}

	private static DAOProduto daoproduto = new DAOProduto();
	private static DAOTipoProduto daotipoproduto = new DAOTipoProduto();
	private static DAOVenda daovenda = new DAOVenda();

	public static void inicializar() {
		DAO.open();
	}

	public static void finalizar() {
		DAO.close();
	}

	public static void cadastrarProduto(String nome, double preco, String nometipoproduto) throws Exception {
		DAO.begin();
		Produto produto = daoproduto.read(nome);
		TipoProduto tipoproduto = daotipoproduto.read(nometipoproduto);
		if (produto != null)
			throw new Exception("Produto já cadastrado:" + nome);
		if (tipoproduto == null)
			throw new Exception("TipoProduto " + nometipoproduto + " inexistente");
		produto = new Produto(nome, preco, tipoproduto);

		daoproduto.create(produto);
		DAO.commit();
	}

	public static void cadastrarTipoProduto(String nome) throws Exception {
		DAO.begin();
		TipoProduto tipoproduto = daotipoproduto.read(nome);
		if (tipoproduto != null)
			throw new Exception("TipoProduto já cadastrado:" + nome);
		tipoproduto = new TipoProduto(nome);

		daotipoproduto.create(tipoproduto);
		DAO.commit();
	}

	public static Venda cadastrarVenda(String data, double desconto) throws Exception {
		DAO.begin();

		Venda venda = new Venda(data, desconto);

		daovenda.create(venda);
		DAO.commit();

		return venda;
	}

	public static void adicionarProdutoEmVenda(int idVenda, String nomeProduto) throws Exception {
		DAO.begin();
		Venda venda = daovenda.read(idVenda);
		if (venda == null)
			throw new Exception("Venda com id" + idVenda + "não existe");

		Produto produto = daoproduto.read(nomeProduto);
		if (produto == null)
			throw new Exception("Produto não existe");

		venda.adicionar(produto);
		DAO.commit();
	}

	public static void removerProdutoDeVenda(int idVenda, String nomeProduto) throws Exception {
		DAO.begin();
		Venda venda = daovenda.read(idVenda);
		if (venda == null)
			throw new Exception("Venda com id" + idVenda + "não existe");

		for (Produto p : venda.getProdutos()) {
			if (p.getNome().equals(nomeProduto)) {
				venda.remover(p);
				return;
			}
		}

		DAO.commit();

		throw new Exception("Produto não existe em venda");
	}

	public static Produto localizarProduto(String nome) {
		DAO.begin();
        return daoproduto.read(nome);
	}

	public static TipoProduto localizarTipoProduto(String nome) {
		DAO.begin();
		return daotipoproduto.read(nome);
	}

	public static Venda localizarVenda(int id) {
		DAO.begin();
		return daovenda.read(id);
	}

	public static List<Venda> vendaDataX(String dataString) {
		List<Venda> vendas = null;

		try {
			DAO.begin();

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date data = dateFormat.parse(dataString);

			vendas = daovenda.vendasDataX(data);

			DAO.commit();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			DAO.close();
		}

		return vendas;
	}

	public static List<Venda> vendasComMaisDeNProdutos(int Qntd) {
		DAO.begin();
		List<Venda> vendas = daovenda.vendasComMaisDeNProdutos(Qntd);
		DAO.commit();

		return vendas;
	}

	public static List<Venda> vendasComProdutoDePrecoX(double preco) {
		DAO.begin();
		List<Venda> vendas = daovenda.vendasComProdutoDePrecoX(preco);
		DAO.commit();

		return vendas;
	}

	public static List<Venda> listarVendascomProdutoP(String nome) {
		return daovenda.vendasComProdutoP(nome);
	}

	public static void excluirProduto(String nomeProduto) throws Exception {
		DAO.begin();
		
		Produto produto = daoproduto.read(nomeProduto);
		
		if (produto == null) {
			throw new Exception("Produto não existe: " + nomeProduto);
		}
	
		TipoProduto tipoProduto = produto.getTipoproduto();
		tipoProduto.remover(produto);
		
		DAO.commit();
	}

	public static void excluirTipoProduto(String nomeTipoProduto) throws Exception {
		DAO.begin();
		TipoProduto tipoProduto = daotipoproduto.read(nomeTipoProduto);
		if (tipoProduto == null)
			throw new Exception("Tipo de produto não existe: " + nomeTipoProduto);

		List<Produto> produtosParaMover = new ArrayList<>(tipoProduto.getProdutos());

		TipoProduto nao_alocado = daotipoproduto.read("Nao-Alocado");
		if(nao_alocado == null) {
			nao_alocado = new TipoProduto("Nao-Alocado");
			daotipoproduto.create(nao_alocado);
		}



		// lista temporária
		for (Produto p : produtosParaMover) {
			p.setTipoproduto(nao_alocado);
			tipoProduto.remover(p);
		}

		daotipoproduto.delete(tipoProduto);
		DAO.commit();
	}

	public static void excluirVenda(int id) {
		DAO.begin();
		Venda venda = daovenda.read(id);
		daovenda.delete(venda);
		DAO.commit();
	}

	public static List<Produto> listarProdutos() {
		DAO.begin();
		List<Produto> resultados = daoproduto.readAll();
		DAO.commit();
		return resultados;
	}

	public static List<TipoProduto> listarTipoProdutos() {
		DAO.begin();
		List<TipoProduto> resultados = daotipoproduto.readAll();
		DAO.commit();
		return resultados;
	}

	public static List<Venda> listarVendas() {
		DAO.begin();
		List<Venda> resultados = daovenda.readAll();
		DAO.commit();
		return resultados;
	}

	public static void adicionarProdutoEmTipoProduto(String nometipoproduto, String nomeproduto) throws Exception {
		DAO.begin();
		TipoProduto tipoproduto = daotipoproduto.read(nometipoproduto);
		if (tipoproduto == null) {
			throw new Exception("Tipoproduto não existe.");
		}

		Produto produto = daoproduto.read(nomeproduto);
		if (produto == null) {
			throw new Exception("Produto não existe.");
		}

		if (produto.getTipoproduto() != null) {
			produto.getTipoproduto().remover(produto);
		}

		produto.setTipoproduto(tipoproduto);
		tipoproduto.adicionar(produto);
		DAO.commit();
	}

	public static void removerProdutodeTipoProduto(String nometipoproduto, String nomeproduto) throws Exception {
		DAO.begin();
		TipoProduto tipoproduto = daotipoproduto.read(nometipoproduto);
		if (tipoproduto == null) {
			throw new Exception("Tipoproduto não existe.");
		}

		Produto produto = daoproduto.read(nomeproduto);
		if (produto == null) {
			throw new Exception("Produto não existe.");
		}
		
		TipoProduto nao_alocado = daotipoproduto.read("Nao-Alocado");
		if(nao_alocado == null) {
			nao_alocado = new TipoProduto("Nao-Alocado");
			daotipoproduto.create(nao_alocado);
		}

		produto.setTipoproduto(nao_alocado);
		tipoproduto.remover(produto);
		DAO.commit();
	}

}