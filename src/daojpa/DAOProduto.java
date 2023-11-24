package daojpa;

import java.util.List;


import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import models.Produto;

public class DAOProduto extends DAO<Produto> {

    public Produto read(Object chave) {
        try {
            String nome = (String) chave;
            TypedQuery<Produto> q = manager.createQuery("SELECT p FROM Produto p WHERE p.nome = :nome", Produto.class);
            q.setParameter("nome", nome);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Produto> readAll() {
        TypedQuery<Produto> query = manager.createQuery("SELECT p FROM Produto p", Produto.class);
        return query.getResultList();
    }
}
