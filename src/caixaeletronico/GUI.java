package caixaeletronico;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;

public class GUI extends JFrame {
    // painel que contém todos os botões
    private JPanel painelPrincipal;

    // botões da interface
    private JButton btnSaque;
    private JButton btnRelatorio;
    private JButton btnValorTotal;
    private JButton btnReposicao;
    private JButton btnCotaMinima;
    private JButton btnSair;

    // o objeto que faz a lógica do caixa
    private ICaixaEletronico caixa;

    public GUI(Class<?> classe) {

        // cria o objeto CaixaEletronico
        try {
            Constructor<?> construtor = classe.getDeclaredConstructor();
            caixa = (ICaixaEletronico) construtor.newInstance();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao iniciar: " + e.getMessage());
            System.exit(1);
        }

        // monta os botões e a interface
        montarUI();

        // configura a janela
        setTitle("Caixa eletronico");
        setContentPane(painelPrincipal);
        setSize(280, 340);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                acaoSair();
            }
        });

        // conecta os botões às ações
        btnSaque.addActionListener(e -> acaoSaque());
        btnRelatorio.addActionListener(e -> acaoRelatorio());
        btnValorTotal.addActionListener(e -> acaoValorTotal());
        btnReposicao.addActionListener(e -> acaoReposicao());
        btnCotaMinima.addActionListener(e -> acaoCotaMinima());
        btnSair.addActionListener(e -> acaoSair());
    }

    private void montarUI() {
        // painel organizando os botões um embaixo do outro
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        // separação em módulos (Cliente vs Admin)
        painelPrincipal.add(criarLabel("Modulo do Cliente:"));
        painelPrincipal.add(Box.createVerticalStrut(4));
        btnSaque = criarBotao("Efetuar Saque");
        painelPrincipal.add(btnSaque);

        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(criarLabel("Modulo do Administrador:"));
        painelPrincipal.add(Box.createVerticalStrut(4));
        btnRelatorio = criarBotao("Relatorio de Cedulas");
        btnValorTotal = criarBotao("Valor total disponivel");
        btnReposicao = criarBotao("Reposicao de Cedulas");
        btnCotaMinima = criarBotao("Cota Minima");
        painelPrincipal.add(btnRelatorio);
        painelPrincipal.add(Box.createVerticalStrut(4));
        painelPrincipal.add(btnValorTotal);
        painelPrincipal.add(Box.createVerticalStrut(4));
        painelPrincipal.add(btnReposicao);
        painelPrincipal.add(Box.createVerticalStrut(4));
        painelPrincipal.add(btnCotaMinima);

        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(criarLabel("Modulo de Ambos:"));
        painelPrincipal.add(Box.createVerticalStrut(4));
        btnSair = criarBotao("Sair");
        painelPrincipal.add(btnSair);
    }

    private void acaoSaque() {
        String entrada = JOptionPane.showInputDialog(this,
                "Digite o valor do saque (R$):", "Efetuar Saque",
                JOptionPane.QUESTION_MESSAGE);
        if (entrada == null) return;
        try {
            // transforma o texto em número
            int valor = Integer.parseInt(entrada.trim());
            exibirResultado("Saque", caixa.sacar(valor));
        } catch (NumberFormatException ex) {
            // se o cliente digitar letra ou deixar vazio, o sistema manda um aviso
            exibirErro("Digite somente números inteiros.");
        }
    }

    private void acaoRelatorio() {
        exibirResultado("Relatório de Cédulas", caixa.pegaRelatorioCedulas());
    }

    private void acaoValorTotal() {
        exibirResultado("Valor Total", caixa.pegaValorTotalDisponivel());
    }

    private void acaoReposicao() {
        // um "mini formulário" pra escolher a nota e a quantidade para reposição
        JComboBox<Integer> combo = new JComboBox<>(new Integer[]{100, 50, 20, 10, 5, 2});
        JTextField campoQtd = new JTextField(8);
        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.add(new JLabel("Cédula (R$):"));
        form.add(combo);
        form.add(new JLabel("Quantidade:"));
        form.add(campoQtd);
        int ok = JOptionPane.showConfirmDialog(this, form,
                "Reposição de Cédulas", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;
        try {
            int cedula = (Integer) combo.getSelectedItem();
            int qtd = Integer.parseInt(campoQtd.getText().trim());
            exibirResultado("Reposição", caixa.reposicaoCedulas(cedula, qtd));
        } catch (NumberFormatException ex) {
            exibirErro("Quantidade inválida.");
        }
    }

    private void acaoCotaMinima() {
        String entrada = JOptionPane.showInputDialog(this,
                "Digite a cota mínima (R$):", "Cota Mínima",
                JOptionPane.QUESTION_MESSAGE);
        if (entrada == null) return;
        try {
            int minimo = Integer.parseInt(entrada.trim());
            exibirResultado("Cota Mínima", caixa.armazenaCotaMinima(minimo));
        } catch (NumberFormatException ex) {
            exibirErro("Valor inválido.");
        }
    }

    private void acaoSair() {
        // antes de fechar o programa de vez, o sistema gera o extrato de tudo que foi feito
        String extrato = (caixa instanceof CaixaEletronico)
                ? ((CaixaEletronico) caixa).geraExtrato()
                : "Extrato indisponível.";
        // área de texto pra mostrar o log final
        JTextArea area = new JTextArea(extrato, 12, 30);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, new JScrollPane(area),
                "Extrato do Caixa", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    // métodos "ajudantes" pra não ter que ficar repetindo código de criar botão e label
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        // esse comando faz o botão ocupar toda a largura disponível
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    private void exibirResultado(String titulo, String texto) {
        // abre uma janela com o texto que veio da lógica do caixa
        JTextArea area = new JTextArea(texto, 8, 26);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, new JScrollPane(area),
                titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private void exibirErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}