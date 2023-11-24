package daodb4o;

import java.util.List;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import models.TipoProduto;

public class DAOTipoProduto extends DAO<TipoProduto>{

	public TipoProduto read (Object chave){
		String nomeTipoProduto = (String) chave;	//casting para o tipo da chave
		Query q = manager.query();
		q.constrain(TipoProduto.class);
		q.descend("nome").constrain(nomeTipoProduto);
		List<TipoProduto> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
}
