package models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String data;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<Produto> produtos = new ArrayList<>();

    private double desconto;
    private double valortotal;
    private double valorpago;

    public Venda() {
    }

    public Venda(String data, double desconto) {
        this.desconto = desconto;
        this.data = data;
        this.valortotal = 0.0;
        this.valorpago = 0.0;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void adicionar(Produto p) {
        produtos.add(p);
        this.valortotal += p.getPreco();
        this.valorpago = this.valortotal - (this.valortotal * (this.desconto / 100));
    }

    public void remover(Produto p) {
        produtos.remove(p);
        this.valortotal -= p.getPreco();
        this.valorpago = this.valortotal - (this.valortotal * (this.desconto / 100));
    }

    public Produto localizar(String nome) {
        for (Produto p : produtos)
            if (p.getNome().equals(nome))
                return p;
        return null;
    }

    @Override
    public String toString() {
        return "Venda [id=" + id + ", data=" + data + ", produtos=" + produtos + ", desconto=" + desconto
                + ", valortotal=" + valortotal + ", valorpago=" + valorpago + "]";
    }
}
