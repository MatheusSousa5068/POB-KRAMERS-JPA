package daodb4o;

import java.util.List;

import com.db4o.query.Query;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;

import models.Venda;

public class DAOVenda  extends DAO<Venda>{

	public Venda read (Object chave){
		int id = (int) chave;	
		Query q = manager.query();
		q.constrain(Venda.class);
		q.descend("id").constrain(id);
		List<Venda> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

	//metodo da classe DAO sobrescrito DAOVenda para
	//criar "id" sequencial 
	public void create(Venda obj){
		int novoid = super.gerarId();  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store(obj);
	}

	//--------------------------------------------
	//  consultas de Venda
	//--------------------------------------------
	public List<Venda> vendasDataX(String dataX){
		Query q1 = manager.query();
		q1.constrain(Venda.class);
		q1.descend("data").constrain(dataX);
		List<Venda> resultados = q1.execute();
		return resultados;
	}

	public List<Venda> vendasComProdutoDePrecoX(double precoX){
		Query q1 = manager.query();
		q1.constrain(Venda.class);
		q1.descend("produtos").descend("preco").constrain(precoX);
		List<Venda> resultados = q1.execute(); 
		return resultados;
	}

	public List<Venda> vendasComMaisDeNProdutos(int N) {
		Query q1 = manager.query();
		q1.constrain(Venda.class);
		q1.constrain(new FiltroQtndProdutosEmVenda(N));
		List<Venda> resultados = q1.execute();

		return resultados;
	}


	public List<Venda> vendasComProdutoP(String nomeProdutoP) {
		Query q1 = manager.query();
		q1.constrain(Venda.class);
		q1.descend("produtos").descend("nome").constrain(nomeProdutoP);
		List<Venda> resultados = q1.execute();
		return resultados;
	}
}

class FiltroQtndProdutosEmVenda implements Evaluation {
	private int n;
	public FiltroQtndProdutosEmVenda(int N) {
		this.n = N;
	}

	public void evaluate(Candidate candidate) {
		Venda v = (Venda) candidate.getObject();
		if (v.getProdutos().size() > n)
			candidate.include(true);
		else
			candidate.include(false);
	}
}