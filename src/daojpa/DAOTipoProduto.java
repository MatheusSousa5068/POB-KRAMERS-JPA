package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import models.TipoProduto;

public class DAOTipoProduto extends DAO<TipoProduto> {

    public TipoProduto read(Object chave) {
        try {
            String nomeTipoProduto = (String) chave;
            TypedQuery<TipoProduto> q = manager.createQuery("SELECT tp FROM TipoProduto tp WHERE tp.nome = :nome", TipoProduto.class);
            q.setParameter("nome", nomeTipoProduto);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<TipoProduto> readAll() {
        TypedQuery<TipoProduto> query = manager.createQuery("SELECT tp FROM TipoProduto tp", TipoProduto.class);
        return query.getResultList();
    }
}
