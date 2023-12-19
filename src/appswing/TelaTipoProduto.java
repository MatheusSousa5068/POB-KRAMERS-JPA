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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

//import com.db4o.ObjectContainer;

import models.Produto;
import models.TipoProduto;
import models.Venda;
import regras_negocio.Fachada;

public class TelaTipoProduto {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textFieldNome;
	private JTextField textFieldNomeTipoProduto;
	private JButton botaoListar;
	private JButton botaoCriarTipoProduto;
	private JButton botaoApagarTipoProduto;
	private JLabel labelFeedback;
	private JLabel labelNome;
	private JLabel labelNomeTipoProduto;
	private JLabel labelNomeProduto;
	private JLabel labelResultados;

	private JButton button_3;
	private JTextField textFieldNomeProduto;
	private JButton botaoAdicionarProdutoemTipoProduto;
	private JButton botaoRemoverProdutodeTipoProduto;
	private JButton botaoExibirProdutos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaTipoProduto tela = new TelaTipoProduto();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaTipoProduto() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		
		frame.setResizable(false);
		frame.setTitle("TipoProduto");
		frame.setBounds(100, 100, 729, 385);
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
				labelResultados.setText("selecionado="+ (String) table.getValueAt( table.getSelectedRow(), 0));
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.ORANGE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		labelFeedback = new JLabel("");		//label de mensagem
		labelFeedback.setForeground(Color.BLUE);
		labelFeedback.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(labelFeedback);

		labelResultados = new JLabel("Resultados:");
		labelResultados.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(labelResultados);

		labelNome = new JLabel("Nome:");
		labelNome.setHorizontalAlignment(SwingConstants.LEFT);
		labelNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelNome.setBounds(21, 265, 71, 14);
		frame.getContentPane().add(labelNome);

		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Dialog", Font.PLAIN, 12));
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(68, 265, 100, 20);
		frame.getContentPane().add(textFieldNome);

		labelNomeTipoProduto = new JLabel("Nome TipoProduto:");
		labelNomeTipoProduto.setHorizontalAlignment(SwingConstants.LEFT);
		labelNomeTipoProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelNomeTipoProduto.setBounds(21, 305, 110, 14);
		frame.getContentPane().add(labelNomeTipoProduto);

		textFieldNomeTipoProduto = new JTextField();
		textFieldNomeTipoProduto.setFont(new Font("Dialog", Font.PLAIN, 12));
		textFieldNomeTipoProduto.setColumns(10);
		textFieldNomeTipoProduto.setBounds(125, 305, 100, 20);
		frame.getContentPane().add(textFieldNomeTipoProduto);

		labelNomeProduto = new JLabel("Nome Produto:");
		labelNomeProduto.setHorizontalAlignment(SwingConstants.LEFT);
		labelNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelNomeProduto.setBounds(240, 305, 80, 14);
		frame.getContentPane().add(labelNomeProduto);

		textFieldNomeProduto = new JTextField();
		textFieldNomeProduto.setFont(new Font("Dialog", Font.PLAIN, 12));
		textFieldNomeProduto.setColumns(10);
		textFieldNomeProduto.setBounds(320, 305, 100, 20);
		frame.getContentPane().add(textFieldNomeProduto);
		
		botaoCriarTipoProduto = new JButton("Criar novo tipoproduto");
		botaoCriarTipoProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textFieldNome.getText().isEmpty()) {
						labelFeedback.setText("Campo vazio.");
						return;
					}
					String nome = textFieldNome.getText();
					Fachada.cadastrarTipoProduto(nome);
					labelFeedback.setText("Tipoproduto criado: " + nome);
					listagem();
				}
				catch(Exception ex) {
					labelFeedback.setText(ex.getMessage());
				}
			}
		});
		botaoCriarTipoProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoCriarTipoProduto.setBounds(200, 265, 153, 23);
		frame.getContentPane().add(botaoCriarTipoProduto);

		botaoAdicionarProdutoemTipoProduto = new JButton("Adicionar produto");
		botaoAdicionarProdutoemTipoProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textFieldNomeTipoProduto.getText().isEmpty() || textFieldNomeProduto.getText().isEmpty()) {
						labelFeedback.setText("Campo vazio.");
						return;
					}

					String nometipoproduto = textFieldNomeTipoProduto.getText();
					String nomeproduto = textFieldNomeProduto.getText();
					Fachada.adicionarProdutoEmTipoProduto(nometipoproduto, nomeproduto);
					labelFeedback.setText("Produto " + nomeproduto + " adicionado em " + nometipoproduto);
					listagem();
				}
				catch(Exception ex) {
					labelFeedback.setText(ex.getMessage());
				}
			}
		});
		botaoAdicionarProdutoemTipoProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoAdicionarProdutoemTipoProduto.setBounds(425,305, 130,23);
		frame.getContentPane().add(botaoAdicionarProdutoemTipoProduto);

		botaoRemoverProdutodeTipoProduto = new JButton("Remover produto");
		botaoRemoverProdutodeTipoProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textFieldNomeTipoProduto.getText().isEmpty() || textFieldNomeProduto.getText().isEmpty()) {
						labelFeedback.setText("Campo vazio.");
						return;
					}

					String nometipoproduto = textFieldNomeTipoProduto.getText();
					String nomeproduto = textFieldNomeProduto.getText();
					Fachada.removerProdutodeTipoProduto(nometipoproduto, nomeproduto);
					labelFeedback.setText("Produto " + nomeproduto + " removido de " + nometipoproduto);
					listagem();
				}
				catch(Exception ex) {
					labelFeedback.setText(ex.getMessage());
				}
			}
		});
		botaoRemoverProdutodeTipoProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoRemoverProdutodeTipoProduto.setBounds(560,305, 130,23);
		frame.getContentPane().add(botaoRemoverProdutodeTipoProduto);

		botaoListar = new JButton("Listar");
		botaoListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		botaoListar.setBounds(308, 11, 89, 23);
		frame.getContentPane().add(botaoListar);

		botaoApagarTipoProduto = new JButton("Apagar selecionado");
		botaoApagarTipoProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){	
						labelFeedback.setText("Não implementado." );
						String nome = (String) table.getValueAt( table.getSelectedRow(), 0);
						Fachada.excluirTipoProduto(nome);
						labelFeedback.setText("Tipoproduto" + nome + "apagado.");
						listagem();
					}
					else
						labelFeedback.setText("Não selecionado.");
				}
				catch(Exception ex) {
					labelFeedback.setText(ex.getMessage());
				}
			}
		});
		botaoApagarTipoProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoApagarTipoProduto.setBounds(229, 215, 171, 23);
		frame.getContentPane().add(botaoApagarTipoProduto);

		botaoExibirProdutos = new JButton("Exibir produtos");
		botaoExibirProdutos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botaoExibirProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){
						String nome = (String) table.getValueAt( table.getSelectedRow(), 0);
						TipoProduto tipoproduto = Fachada.localizarTipoProduto(nome);

						if (tipoproduto != null) {
							String texto="";
							if (tipoproduto.getProdutos().isEmpty())
								texto = "Não existem produtos.";
							else
								for (Produto p : tipoproduto.getProdutos()) {
									texto = texto + p.getNome();
								}

							JOptionPane.showMessageDialog(frame, texto, "Produtos", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				catch(Exception erro) {
					labelFeedback.setText(erro.getMessage());
				}
			}
		});
		botaoExibirProdutos.setBounds(47, 215, 134, 23);
		frame.getContentPane().add(botaoExibirProdutos);
	}

	public void listagem() {
		try{
			List<TipoProduto> lista = Fachada.listarTipoProdutos();

			// model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("nome");

			//adicionar linhas no model
			for(TipoProduto tp : lista) {
				model.addRow(new Object[]{tp.getNome()}); // falta reomver o segundo getNome
			}

			//atualizar model no table (visualizacao)
			table.setModel(model);

			labelResultados.setText("resultados: "+lista.size()+ " objetos");
		}
		catch(Exception erro){
			labelFeedback.setText(erro.getMessage());
		}
	}
}
