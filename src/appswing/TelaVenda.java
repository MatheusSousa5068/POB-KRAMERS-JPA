/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
package appswing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

//import com.db4o.ObjectContainer;

import models.Produto;
import models.Venda;
import regras_negocio.Fachada;

public class TelaVenda {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textfieldData;
	private JTextField textfieldDesconto;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JButton botaoListar;
	private JButton botaoCriarNovaVenda;
	private JButton botaoApagarVenda;
	private JLabel labelFeedback;
	private JLabel labelData;
	private JLabel labelDesconto;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel labelResultados;
	private JButton button_3;
	private JLabel labelID;
	private JTextField textFieldID;
	private JLabel labelNomeProduto;
	private JTextField textFieldNomeProduto;
	private JButton botaoAdicionarProdutoEmVenda;
	private JButton botaoRemoverProdutoDeVenda;
	private JButton botaoExibirProdutos;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaVenda tela = new TelaVenda();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaVenda() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Venda");
		frame.setBounds(100, 100, 729, 419);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
				listagem();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					labelResultados.setText("selecionado = " + (int) table.getValueAt(selectedRow, 0));
				} else {
					labelResultados.setText("Nenhuma linha selecionada.");
				}

			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(new Color(144, 238, 144));
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		labelFeedback = new JLabel("");
		labelFeedback.setForeground(Color.BLUE);
		labelFeedback.setBounds(12, 355, 688, 14);
		frame.getContentPane().add(labelFeedback);

		labelResultados = new JLabel("Resultados:");
		labelResultados.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(labelResultados);

		labelData = new JLabel("Data:");
		labelData.setHorizontalAlignment(SwingConstants.LEFT);
		labelData.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelData.setBounds(14, 269, 50, 14);
		frame.getContentPane().add(labelData);

		textfieldData = new JTextField();
		textfieldData.setFont(new Font("Dialog", Font.PLAIN, 12));
		textfieldData.setColumns(10);
		textfieldData.setBounds(70, 266, 100, 20);
		frame.getContentPane().add(textfieldData);

		labelDesconto = new JLabel("Desconto:");
		labelDesconto.setHorizontalAlignment(SwingConstants.LEFT);
		labelDesconto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelDesconto.setBounds(180, 269, 60, 14);
		frame.getContentPane().add(labelDesconto);

		textfieldDesconto = new JTextField();
		textfieldDesconto.setFont(new Font("Dialog", Font.PLAIN, 12));
		textfieldDesconto.setColumns(10);
		textfieldDesconto.setBounds(250, 266, 50, 20);
		frame.getContentPane().add(textfieldDesconto);

		botaoCriarNovaVenda = new JButton("Criar nova venda");
		botaoCriarNovaVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textfieldData.getText().isEmpty() || textfieldDesconto.getText().isEmpty()) {
						labelFeedback.setText("Campo vazio.");
						return;
					}
					String data = textfieldData.getText();
					double desconto = Double.parseDouble(textfieldDesconto.getText());

					Fachada.cadastrarVenda(data, desconto);
					labelFeedback.setText("Venda criada.");
					listagem();
				} catch (Exception ex) {
					labelFeedback.setText(ex.getMessage());
				}
			}
		});
		botaoCriarNovaVenda.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoCriarNovaVenda.setBounds(320, 265, 153, 23);
		frame.getContentPane().add(botaoCriarNovaVenda);

		botaoListar = new JButton("Listar");
		botaoListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		botaoListar.setBounds(308, 11, 89, 23);
		frame.getContentPane().add(botaoListar);

		labelID = new JLabel("ID:");
		labelID.setHorizontalAlignment(SwingConstants.LEFT);
		labelID.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelID.setBounds(21, 305, 20, 14);
		frame.getContentPane().add(labelID);

		textFieldID = new JTextField();
		textFieldID.setFont(new Font("Dialog", Font.PLAIN, 12));
		textFieldID.setColumns(10);
		textFieldID.setBounds(50, 305, 30, 20);
		frame.getContentPane().add(textFieldID);

		labelNomeProduto = new JLabel("Nome Produto:");
		labelNomeProduto.setHorizontalAlignment(SwingConstants.LEFT);
		labelNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelNomeProduto.setBounds(90, 305, 80, 14);
		frame.getContentPane().add(labelNomeProduto);

		textFieldNomeProduto = new JTextField();
		textFieldNomeProduto.setFont(new Font("Dialog", Font.PLAIN, 12));
		textFieldNomeProduto.setColumns(10);
		textFieldNomeProduto.setBounds(180, 305, 100, 20);
		frame.getContentPane().add(textFieldNomeProduto);

		botaoApagarVenda = new JButton("Apagar selecionado");
		botaoApagarVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						int idVenda = (int) table.getValueAt(table.getSelectedRow(), 0);

						Fachada.excluirVenda(idVenda);
						labelFeedback.setText("Venda deletada.");
						listagem();
					} else
						labelFeedback.setText("Não selecionado.");
				} catch (Exception ex) {
					labelFeedback.setText(ex.getMessage());
				}
			}
		});
		botaoApagarVenda.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoApagarVenda.setBounds(229, 215, 171, 23);
		frame.getContentPane().add(botaoApagarVenda);

		botaoAdicionarProdutoEmVenda = new JButton("Adicionar produto");
		botaoAdicionarProdutoEmVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textFieldID.getText().isEmpty() || textFieldNomeProduto.getText().isEmpty()) {
						labelFeedback.setText("Campo vazio.");
						return;
					}

					int id = Integer.parseInt(textFieldID.getText());
					String nomeproduto = textFieldNomeProduto.getText();
					Fachada.adicionarProdutoEmVenda(id, nomeproduto);
					labelFeedback.setText("Produto " + nomeproduto + " adicionado em Venda" + textFieldID.getText());
					listagem();
				} catch (Exception ex) {
					labelFeedback.setText(ex.getMessage());
				}
			}
		});
		botaoAdicionarProdutoEmVenda.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoAdicionarProdutoEmVenda.setBounds(300, 305, 130, 23);
		frame.getContentPane().add(botaoAdicionarProdutoEmVenda);

		botaoRemoverProdutoDeVenda = new JButton("Remover produto");
		botaoRemoverProdutoDeVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textFieldID.getText().isEmpty() || textFieldNomeProduto.getText().isEmpty()) {
						labelFeedback.setText("Campo vazio.");
						return;
					}

					int id = Integer.parseInt(textFieldID.getText());
					String nomeproduto = textFieldNomeProduto.getText();
					Fachada.removerProdutoDeVenda(id, nomeproduto);
					labelFeedback.setText("Produto " + nomeproduto + " removido de " + textFieldID.getText());
					listagem();
				} catch (Exception ex) {
					labelFeedback.setText(ex.getMessage());
				}
			}
		});
		botaoRemoverProdutoDeVenda.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoRemoverProdutoDeVenda.setBounds(440, 305, 130, 23);
		frame.getContentPane().add(botaoRemoverProdutoDeVenda);

		botaoExibirProdutos = new JButton("Exibir produtos");
		botaoExibirProdutos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoExibirProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						Object idObj = table.getValueAt(table.getSelectedRow(), 0);
						String idStr = idObj.toString();

						int id = Integer.parseInt(idStr);

						Venda venda = Fachada.localizarVenda(id);

						if (venda != null) {
							String texto = "";
							if (venda.getProdutos().isEmpty())
								texto = "Não existem produtos.";
							else
								for (Produto p : venda.getProdutos()) {
									texto = texto + p.getNome();
								}

							JOptionPane.showMessageDialog(frame, texto, "Produtos", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} catch (Exception erro) {
					labelFeedback.setText(erro.getMessage());
				}
			}
		});
		botaoExibirProdutos.setBounds(47, 215, 134, 23);
		frame.getContentPane().add(botaoExibirProdutos);

	}

	public void listagem() {
		try {
			// Ler as vendas do banco
			List<Venda> lista = Fachada.listarVendas();

			// Criar um modelo de tabela vazio
			DefaultTableModel model = new DefaultTableModel();

			// Adicionar colunas ao modelo
			model.addColumn("ID");
			model.addColumn("Data");
			model.addColumn("Valor total");
			model.addColumn("Valor pago");
			model.addColumn("Desconto");

			// Adicionar linhas ao modelo
			for (Venda venda : lista) {
				model.addRow(new Object[] { venda.getId(), venda.getData(), venda.getValortotal(), venda.getValorpago(),
						venda.getDesconto() });
			}

			// Atualizar o modelo da tabela (visualização)
			table.setModel(model);

			labelResultados.setText("Resultados: " + lista.size() + " objetos");
		} catch (Exception erro) {
			labelFeedback.setText(erro.getMessage());
		}
	}

}
