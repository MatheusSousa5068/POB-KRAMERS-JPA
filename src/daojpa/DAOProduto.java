/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.Produto;

public class DAOProduto extends DAO<Produto> {

    public Produto read(Object chave) {
        try {
            String codigo = (String) chave;
            TypedQuery<Produto> q = manager.createQuery("select p from Produto p where p.nome=:cod", Produto.class);
            q.setParameter("cod", codigo);
            Produto p = q.getSingleResult();
            return p;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Produto> readAll() {
        TypedQuery<Produto> query = manager.createQuery("select p from Produto p LEFT JOIN FETCH p.vendas order by p.nome", Produto.class);
        return query.getResultList();
    }

    public List<Produto> produtosNVendas(int n) {
        TypedQuery<Produto> q = manager.createQuery("select p from Produto p LEFT JOIN FETCH p.vendas where size(p.vendas) = :x", Produto.class);
        q.setParameter("x", n);
        return q.getResultList();
    }
}
