package models;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Produto {

	@Id
	private String nome;

	@ManyToOne
	private TipoProduto tipoProduto;

	private double preco;

	@ManyToMany(mappedBy = "produtos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Venda> vendas = new ArrayList<>();

	public Produto() {
	}

	public Produto(String nome, double preco, TipoProduto tipoProduto) {
		this.nome = nome;
		this.preco = preco;
		this.tipoProduto = tipoProduto;
		this.tipoProduto.adicionar(this); // Relacionamento Inverso
	}

	public TipoProduto getTipoproduto() {
		return tipoProduto;
	}

	public void setTipoproduto(TipoProduto tipoproduto) {
		this.tipoProduto = tipoproduto;
		tipoproduto.adicionar(this);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		return "Produto [nome=" + nome + ", tipoproduto=" + tipoProduto.getNome() + ", preco=" + preco + "]";
	}
}
