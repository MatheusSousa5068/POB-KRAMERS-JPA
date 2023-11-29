/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.Venda;

public class DAOVenda extends DAO<Venda> {

    public Venda read(Object chave) {
        try {
            Long id = (Long) chave;
            TypedQuery<Venda> q = manager.createQuery("select v from Venda v where v.id=:vid", Venda.class);
            q.setParameter("vid", id);
            Venda v = q.getSingleResult();
            return v;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Venda> readAll() {
        TypedQuery<Venda> query = manager.createQuery("select v from Venda v order by v.id", Venda.class);
        return query.getResultList();
    }

    // Se houver algum relacionamento específico para Venda, adicione métodos aqui.

}
