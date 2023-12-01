/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import models.Aluno;
import models.Pessoa;

public class DAOAluno  extends DAO<Aluno>{
	//nome � usado como campo unico 
	public Aluno read (Object chave) {
		String nome = (String) chave;	//casting para o tipo da chave
		Query q = manager.query();
		q.constrain(Pessoa.class);
		q.descend("nome").constrain(nome);
		List<Aluno> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
}

