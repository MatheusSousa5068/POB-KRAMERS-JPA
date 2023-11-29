/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.TipoProduto;

public class DAOTipoProduto extends DAO<TipoProduto> {

    public TipoProduto read(Object chave) {
        try {
            String tipo = (String) chave;
            TypedQuery<TipoProduto> q = manager.createQuery("select tp from TipoProduto tp where tp.nome=:tip", TipoProduto.class);
            q.setParameter("tip", tipo);
            TipoProduto tp = q.getSingleResult();
            return tp;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<TipoProduto> readAll() {
        TypedQuery<TipoProduto> query = manager.createQuery("select tp from TipoProduto tp order by tp.nome", TipoProduto.class);
        return query.getResultList();
    }

    // Se houver algum relacionamento específico para TipoProduto, adicione métodos aqui.

}
