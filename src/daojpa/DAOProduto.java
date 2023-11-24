package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import models.Produto;

public class DAOProduto  extends DAO<Produto>{

	public Produto read (Object chave){
		String nome = (String) chave;	
		Query q = manager.query();
		q.constrain(Produto.class);
		q.descend("nome").constrain(nome);
		List<Produto> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
}