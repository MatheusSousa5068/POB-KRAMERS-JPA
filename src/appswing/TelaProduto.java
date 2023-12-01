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

import models.Produto;
import models.Venda;
import regras_negocio.Fachada;

public class TelaProduto {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField textFieldNome;
    private JTextField textfieldPreco;
    private JTextField textFieldTipoProduto;

    private JButton botaoListar;
    private JButton botaoCriarProduto;
    private JButton botaoApagarProduto;
    private JButton botaoExibirVendas;
    private JLabel labelFeedback;
    private JLabel labelNome;
    private JLabel labelPreco;
    private JLabel labelResultados;
    private JLabel labelTipoProduto;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaProduto tela = new TelaProduto();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaProduto() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);

        frame.setResizable(false);
        frame.setTitle("Produto");
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
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    labelResultados.setText("selecionado = " + table.getValueAt(selectedRow, 0));
                } else {
                    labelResultados.setText("Nenhuma linha selecionada.");
                }
            }
        });
        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(Color.YELLOW);
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
        labelFeedback.setBounds(21, 321, 688, 14);
        frame.getContentPane().add(labelFeedback);

        labelResultados = new JLabel("Resultados:");
        labelResultados.setBounds(21, 190, 431, 14);
        frame.getContentPane().add(labelResultados);

        labelNome = new JLabel("Nome:");
        labelNome.setHorizontalAlignment(SwingConstants.LEFT);
        labelNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelNome.setBounds(21, 269, 71, 14);
        frame.getContentPane().add(labelNome);

        textFieldNome = new JTextField();
        textFieldNome.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldNome.setColumns(10);
        textFieldNome.setBounds(68, 264, 100, 20);
        frame.getContentPane().add(textFieldNome);

        botaoCriarProduto = new JButton("Criar novo produto");
        botaoCriarProduto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textFieldNome.getText().isEmpty() || textfieldPreco.getText().isEmpty()
                            || textFieldTipoProduto.getText().isEmpty()) {
                        labelFeedback.setText("Campo vazio.");
                        return;
                    }
                    String nome = textFieldNome.getText();
                    Double preco = Double.parseDouble(textfieldPreco.getText());
                    String tipoproduto = textFieldTipoProduto.getText();
                    Fachada.cadastrarProduto(nome, preco, tipoproduto);
                    labelFeedback.setText("Produto criado: " + nome);
                    listagem();
                } catch (Exception ex) {
                    labelFeedback.setText(ex.getMessage());
                }
            }
        });
        botaoCriarProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
        botaoCriarProduto.setBounds(525, 305, 153, 23);
        frame.getContentPane().add(botaoCriarProduto);

        botaoListar = new JButton("Listar");
        botaoListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        botaoListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listagem();
            }
        });
        botaoListar.setBounds(308, 11, 89, 23);
        frame.getContentPane().add(botaoListar);

        labelPreco = new JLabel("Preço:");
        labelPreco.setHorizontalAlignment(SwingConstants.LEFT);
        labelPreco.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelPreco.setBounds(281, 269, 63, 14);
        frame.getContentPane().add(labelPreco);

        textfieldPreco = new JTextField();
        textfieldPreco.setFont(new Font("Dialog", Font.PLAIN, 12));
        textfieldPreco.setColumns(10);
        textfieldPreco.setBounds(336, 264, 100, 20);
        frame.getContentPane().add(textfieldPreco);

        labelTipoProduto = new JLabel("Tipoproduto:");
        labelTipoProduto.setHorizontalAlignment(SwingConstants.LEFT);
        labelTipoProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelTipoProduto.setBounds(514, 269, 90, 14);
        frame.getContentPane().add(labelTipoProduto);

        textFieldTipoProduto = new JTextField();
        textFieldTipoProduto.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldTipoProduto.setColumns(10);
        textFieldTipoProduto.setBounds(604, 264, 100, 20);
        frame.getContentPane().add(textFieldTipoProduto);

        botaoApagarProduto = new JButton("Apagar selecionado");
        botaoApagarProduto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        String nome = (String) table.getValueAt(table.getSelectedRow(), 0);
                        Fachada.excluirProduto(nome);
                        labelFeedback.setText("Produto " + nome + " excluído.");
                        listagem();
                    } else
                        labelFeedback.setText("Não selecionado.");
                } catch (Exception ex) {
                    labelFeedback.setText(ex.getMessage());
                }
            }
        });
        botaoApagarProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
        botaoApagarProduto.setBounds(281, 213, 171, 23);
        frame.getContentPane().add(botaoApagarProduto);

        botaoExibirVendas = new JButton("Exibir vendas");
        botaoExibirVendas.setFont(new Font("Tahoma", Font.PLAIN, 12));
        botaoExibirVendas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        String nome = (String) table.getValueAt(table.getSelectedRow(), 0);
                        Produto produto = Fachada.localizarProduto(nome);

                        if (produto != null) {
                            String texto = "";
                            if (Fachada.listarVendascomProdutoP(nome).isEmpty())
                                texto = "Não existem vendas.";
                            else
                                for (Venda v : Fachada.listarVendascomProdutoP(nome)) {
                                    texto = texto + v.getId();
                                }

                            JOptionPane.showMessageDialog(frame, texto, "Vendas", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (Exception erro) {
                    labelFeedback.setText(erro.getMessage());
                }
            }
        });
        botaoExibirVendas.setBounds(47, 215, 134, 23);
        frame.getContentPane().add(botaoExibirVendas);
    }

    public void listagem() {
        try {
            List<Produto> lista = Fachada.listarProdutos();

            // model armazena todas as linhas e colunas do table
            DefaultTableModel model = new DefaultTableModel();

            // adicionar colunas no model
            model.addColumn("nome");
            model.addColumn("preço");
            model.addColumn("tipoproduto");

            // adicionar linhas no model
            for (Produto produto : lista) {
                model.addRow(new Object[] { produto.getNome(), produto.getPreco(),
                        produto.getTipoproduto().getNome() });
            }

            // atualizar model no table (visualizacao)
            table.setModel(model);

            labelResultados.setText("resultados: " + lista.size() + " objetos");
        } catch (Exception erro) {
            labelFeedback.setText(erro.getMessage());
        }
    }
}
