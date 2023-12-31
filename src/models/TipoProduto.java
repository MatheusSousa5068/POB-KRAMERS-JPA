package models;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

@Entity
public class TipoProduto {

    @Id
    private String nome;

    @OneToMany(mappedBy = "tipoProduto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produto> produtos = new ArrayList<>();

    public TipoProduto() {
    }
    
    public TipoProduto(String nome) {
    	this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void adicionar(Produto p){
        produtos.add(p);
    }

    public void remover(Produto p){
        produtos.remove(p);
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public Produto localizar(String nome){
        for(Produto p: produtos)
            if (p.getNome().equals(nome))
                return p;
        return null;
    }

    @Override
    public String toString() {
        String texto = "TipoProduto [nome=" + nome + "] produtos: [";
        for(Produto p: produtos) {
            texto += p.getNome() + " ";
        }
        texto += "]";
        return texto;
    }
}
