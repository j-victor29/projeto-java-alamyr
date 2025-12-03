import javax.swing.*;       // componentes de interface
import java.awt.*;          // classes gráficas
import java.text.DecimalFormat; // formata números

public class ReciclagemApp extends JFrame { // janela principal do app

    private JTextField campoGarrafas2L;     // campo pro usuário digitar garrafas 2L
    private JTextField campoGarrafas1L;     // campo pro usuário digitar garrafas 1L
    private JTextField campoGarrafasAgua;   // campo pro usuário digitar água
    private JComboBox<CalculadoraReciclagem.PeriodoColeta> selectPeriodo; // seletor do período
    private JButton botaoCalcular;          // botão pra calcular

    private JLabel labelPesoAno;            // mostra o peso anual
    private JLabel labelValorAno;           // mostra o valor anual

    private static final DecimalFormat FORMATO_PESO = new DecimalFormat("#,##0.000"); // 0,000 kg
    private static final DecimalFormat FORMATO_VALOR = new DecimalFormat("R$ #,##0.00"); // R$ 0,00

    public ReciclagemApp() {
        configurarJanela();     // ajeita a janela
        iniciarComponentes();   // cria os campos e botões
        adicionarEventos();     // adiciona ações
        setVisible(true);       // mostra tudo
    }

    private void configurarJanela() {
        setTitle("PescaViva | Calculadora de Reciclagem de Tampinhas PET");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fecha ao clicar no X
        setLocationRelativeTo(null); // centraliza
    }

    private void iniciarComponentes() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Calculadora de Reciclagem de Tampinhas PET");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        gbc.gridwidth = 1;
        adicionarLinha(painel, gbc, "Garrafas PET 2L:", campoGarrafas2L = new JTextField("0", 10), 1);
        adicionarLinha(painel, gbc, "Garrafas PET 1L:", campoGarrafas1L = new JTextField("0", 10), 2);
        adicionarLinha(painel, gbc, "Garrafas Água Mineral:", campoGarrafasAgua = new JTextField("0", 10), 3);

        selectPeriodo = new JComboBox<>(CalculadoraReciclagem.PeriodoColeta.values());
        adicionarLinha(painel, gbc, "Período de consumo:", selectPeriodo, 4);

        botaoCalcular = new JButton("Calcular Potencial Anual");
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        painel.add(botaoCalcular, gbc);
        gbc.gridwidth = 1;

        JSeparator sep = new JSeparator();
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        painel.add(sep, gbc);
        gbc.gridwidth = 1;

        labelPesoAno = new JLabel(FORMATO_PESO.format(0.0) + " kg");
        labelPesoAno.setFont(new Font("Arial", Font.BOLD, 14));
        adicionarLinha(painel, gbc, "Peso Total (Anual):", labelPesoAno, 7);

        labelValorAno = new JLabel(FORMATO_VALOR.format(0.0));
        labelValorAno.setFont(new Font("Arial", Font.BOLD, 14));
        adicionarLinha(painel, gbc, "Valor Potencial (Anual):", labelValorAno, 8);

        this.add(painel);
    }

    private void adicionarLinha(JPanel painel, GridBagConstraints gbc, String texto, JComponent comp, int linha) {
        gbc.gridx = 0; gbc.gridy = linha;
        painel.add(new JLabel(texto), gbc);
        gbc.gridx = 1;
        painel.add(comp, gbc);
    }

    private void adicionarEventos() {
        botaoCalcular.addActionListener(e -> executarCalculo());
    }

    private void executarCalculo() {
        try {
            int qtd2L = Integer.parseInt(campoGarrafas2L.getText().trim());
            int qtd1L = Integer.parseInt(campoGarrafas1L.getText().trim());
            int qtdAgua = Integer.parseInt(campoGarrafasAgua.getText().trim());
            CalculadoraReciclagem.PeriodoColeta periodo =
                    (CalculadoraReciclagem.PeriodoColeta) selectPeriodo.getSelectedItem();

            if (qtd2L < 0 || qtd1L < 0 || qtdAgua < 0) { // não aceita números negativos
                JOptionPane.showMessageDialog(this, "Valores não podem ser negativos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CalculadoraReciclagem calc = new CalculadoraReciclagem();
            CalculadoraReciclagem.Resultado resultado = calc.calcularAnual(qtd2L, qtd1L, qtdAgua, periodo);

            labelPesoAno.setText(FORMATO_PESO.format(resultado.getPesoKgAnual()) + " kg");
            labelValorAno.setText(FORMATO_VALOR.format(resultado.getValorAnual()));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite apenas números!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReciclagemApp());
    }
}