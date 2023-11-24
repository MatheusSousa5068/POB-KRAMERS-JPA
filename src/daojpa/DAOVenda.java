package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.Venda;

public class DAOVenda extends DAO<Venda> {

    public Venda read(Object chave) {
        try {
            int id = (int) chave;
            TypedQuery<Venda> q = manager.createQuery("SELECT v FROM Venda v WHERE v.id = :id", Venda.class);
            q.setParameter("id", id);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Venda> vendasDataX(String dataX) {
        TypedQuery<Venda> q = manager.createQuery("SELECT v FROM Venda v WHERE v.data = :dataX", Venda.class);
        q.setParameter("dataX", dataX);
        return q.getResultList();
    }

    public List<Venda> vendasComProdutoDePrecoX(double precoX) {
        TypedQuery<Venda> q = manager.createQuery("SELECT v FROM Venda v JOIN v.produtos p WHERE p.preco = :precoX", Venda.class);
        q.setParameter("precoX", precoX);
        return q.getResultList();
    }

    public List<Venda> vendasComMaisDeNProdutos(int N) {
        TypedQuery<Venda> q = manager.createQuery("SELECT v FROM Venda v WHERE SIZE(v.produtos) > :n", Venda.class);
        q.setParameter("n", N);
        return q.getResultList();
    }

    public List<Venda> vendasComProdutoP(String nomeProdutoP) {
        TypedQuery<Venda> q = manager.createQuery("SELECT v FROM Venda v JOIN v.produtos p WHERE p.nome = :nomeProdutoP", Venda.class);
        q.setParameter("nomeProdutoP", nomeProdutoP);
        return q.getResultList();
    }
}
