package daojpa;

import java.util.Date;
import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.Venda;

public class DAOVenda extends DAO<Venda> {

    public Venda read(Object chave) {
        try {
            int id = (int) chave;
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

    public List<Venda> vendasDataX(Date data) {
        try {
            TypedQuery<Venda> q = manager.createQuery("select v from Venda v where v.data = :data", Venda.class);
            q.setParameter("data", data);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Venda> vendasComMaisDeNProdutos(int quantidadeMinima) {
        TypedQuery<Venda> q = manager.createQuery(
                "select v from Venda v where size(v.produtos) > :quantidadeMinima",
                Venda.class
        );
        q.setParameter("quantidadeMinima", quantidadeMinima);

        return q.getResultList();
    }

    public List<Venda> vendasComProdutoDePrecoX(double precoDesejado) {
        TypedQuery<Venda> q = manager.createQuery(
                "select distinct v from Venda v join v.produtos p where p.preco = :precoDesejado",
                Venda.class
        );
        q.setParameter("precoDesejado", precoDesejado);

        return q.getResultList();
    }

    public List<Venda> vendasComProdutoP(String nomeProdutoDesejado) {
        TypedQuery<Venda> q = manager.createQuery(
                "select distinct v from Venda v join v.produtos p where p.nome = :nomeProdutoDesejado",
                Venda.class
        );
        q.setParameter("nomeProdutoDesejado", nomeProdutoDesejado);

        return q.getResultList();
    }

}
