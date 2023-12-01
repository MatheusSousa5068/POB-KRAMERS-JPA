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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

//import com.db4o.ObjectContainer;

import models.Venda;
import regras_negocio.Fachada;

public class TelaConsulta {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JLabel labelFeedback;
	private JLabel labelResultados;

	//private ObjectContainer manager;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConsulta tela = new TelaConsulta();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaConsulta() {
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
		frame.setTitle("Consulta");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
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
		table.setBackground(Color.LIGHT_GRAY);
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

		labelResultados = new JLabel("Resultados: ");
		labelResultados.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(labelResultados);

		button = new JButton("Consultar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				if(index<0)
					labelResultados.setText("Consulta não selecionada.");
				else
					switch(index) {
					case 0:
						String data = JOptionPane.showInputDialog("Digite a data:");
						List<Venda> resultado1 = Fachada.vendaDataX(data);
						listagemVenda(resultado1);
						break;
					case 1: 
						double preco = Double.parseDouble(JOptionPane.showInputDialog("Digite o preço:"));
						List<Venda> resultado2 = Fachada.vendasComProdutoDePrecoX(preco);
						listagemVenda(resultado2);
						break;
					case 2: 
						int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade:"));
						List<Venda> resultado3 = Fachada.vendasComMaisDeNProdutos(quantidade);
						listagemVenda(resultado3);
						break;
					}

			}
		});
		button.setBounds(606, 10, 89, 23);
		frame.getContentPane().add(button);

		comboBox = new JComboBox();
		comboBox.setToolTipText("Selecione a consulta");
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Vendas da data X", "Vendas com produtos de preço X", "Vendas com mais de X produtos"}));
		comboBox.setBounds(21, 10, 513, 22);
		frame.getContentPane().add(comboBox);
	}

	public void listagemVenda(List<Venda> lista) {
		try{
			// o model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("ID");
			model.addColumn("Data");
			model.addColumn("Valor total");
			model.addColumn("Valor pago");
			model.addColumn("Desconto");

			//adicionar linhas no model
			for(Venda venda : lista) {
				model.addRow(new Object[]{venda.getId(), venda.getData(), venda.getValortotal(), venda.getValorpago(), venda.getDesconto()});
			}
			//atualizar model no table (visualizacao)
			table.setModel(model);

			labelResultados.setText("Resultados: "+ lista.size() + " objetos");
		}
		catch(Exception erro){
			labelFeedback.setText(erro.getMessage());
		}
	}
}
