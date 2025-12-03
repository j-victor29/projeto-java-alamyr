import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class ReciclagemApp extends JFrame {

    // --- CAMPOS DE ENTRADA DO USUÁRIO ---
    private JTextField txtGarrafas2L;
    private JTextField txtGarrafas1L;
    private JTextField txtGarrafasAguaMineral;
    private JComboBox<CalculadoraReciclagem.Periodo> cmbPeriodo;
    private JButton btnCalcular;

    // --- CAMPOS DE SAÍDA (RESULTADOS) ---
    private JLabel lblPesoAnual;
    private JLabel lblValorAnual;

    // --- FORMATADORES DE NÚMERO ---
    private static final DecimalFormat DF_PESO = new DecimalFormat("#,##0.000");
    private static final DecimalFormat DF_VALOR = new DecimalFormat("R$ #,##0.00");

    // --- CONSTRUTOR ---
    public ReciclagemApp() {
        configurarJanela();
        inicializarComponentes();
        adicionarOuvintes();
        setVisible(true);
    }

    // --- CONFIGURAÇÃO DA JANELA PRINCIPAL ---
    private void configurarJanela() {
        setTitle("Equipe: PescaViva | Calculadora de Reciclagem de Tampinhas PET");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
    }

    // --- INICIALIZAÇÃO DOS COMPONENTES VISUAIS ---
    private void inicializarComponentes() {
        JPanel painel = criarPainelPrincipal();
        this.add(painel);
    }

    // --- CRIAÇÃO DO PAINEL PRINCIPAL ---
    private JPanel criarPainelPrincipal() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Seção de Título
        adicionarTitulo(painel, gbc);

        // Seção de Entrada
        adicionarCamposEntrada(painel, gbc);

        // Botão de Cálculo
        adicionarBotaoCalcular(painel, gbc);

        // Seção de Resultados
        adicionarSeparador(painel, gbc);
        adicionarCamposResultado(painel, gbc);

        return painel;
    }

    // --- ADICIONA O TÍTULO ---
    private void adicionarTitulo(JPanel painel, GridBagConstraints gbc) {
        JLabel lblTitulo = new JLabel("Calculadora de Reciclagem de Tampinhas PET");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painel.add(lblTitulo, gbc);
    }

    // --- ADICIONA OS CAMPOS DE ENTRADA ---
    private void adicionarCamposEntrada(JPanel painel, GridBagConstraints gbc) {
        gbc.gridwidth = 1;

        // Campo: Garrafas 2L
        adicionarCampo(painel, gbc, "Garrafas PET 2L consumidas:", 
                       txtGarrafas2L = new JTextField("0", 10), 1);

        // Campo: Garrafas 1L
        adicionarCampo(painel, gbc, "Garrafas PET 1L consumidas:", 
                       txtGarrafas1L = new JTextField("0", 10), 2);

        // Campo: Garrafas Água Mineral
        adicionarCampo(painel, gbc, "Garrafas de Água Mineral consumidas:", 
                       txtGarrafasAguaMineral = new JTextField("0", 10), 3);

        // Campo: Período
        cmbPeriodo = new JComboBox<>(CalculadoraReciclagem.Periodo.values());
        adicionarCampo(painel, gbc, "Período de consumo:", cmbPeriodo, 4);
    }

    // --- MÉTODO AUXILIAR PARA ADICIONAR UM CAMPO ---
    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, String rotulo, 
                               JComponent componente, int linha) {
        gbc.gridx = 0;
        gbc.gridy = linha;
        painel.add(new JLabel(rotulo), gbc);
        
        gbc.gridx = 1;
        painel.add(componente, gbc);
    }

    // --- ADICIONA O BOTÃO DE CALCULAR ---
    private void adicionarBotaoCalcular(JPanel painel, GridBagConstraints gbc) {
        btnCalcular = new JButton("Calcular Potencial Anual");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 15, 5);
        painel.add(btnCalcular, gbc);
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;
    }

    // --- ADICIONA SEPARADOR VISUAL ---
    private void adicionarSeparador(JPanel painel, GridBagConstraints gbc) {
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        painel.add(separator, gbc);
        gbc.gridwidth = 1;
    }

    // --- ADICIONA OS CAMPOS DE RESULTADO ---
    private void adicionarCamposResultado(JPanel painel, GridBagConstraints gbc) {
        // Peso Total
        lblPesoAnual = new JLabel(DF_PESO.format(0.0) + " kg");
        lblPesoAnual.setFont(new Font("Arial", Font.BOLD, 14));
        adicionarCampo(painel, gbc, "Peso Total de Tampinhas (Anual):",
        // Valor Potencial
        lblValorAnual = new JLabel(DF_VALOR.format(0.0));
        lblValorAnual.setFont(new Font("Arial", Font.BOLD, 14));
        adicionarCampo(painel, gbc, "Valor Potencial de Reciclagem (Anual):", lblValorAnual, 8);
    }

    // --- ADICIONA OUVINTES DE EVENTOS ---
    private void adicionarOuvintes() {
        btnCalcular.addActionListener(e -> executarCalculo());
    }

    // --- EXECUTA O CÁLCULO ---
    private void executarCalculo() {
        try {
            // Coleta os valores do usuário
            int garrafas2L = Integer.parseInt(txtGarrafas2L.getText().trim());
            int garrafas1L = Integer.parseInt(txtGarrafas1L.getText().trim());
            int garrafasAguaMineral = Integer.parseInt(txtGarrafasAguaMineral.getText().trim());
            CalculadoraReciclagem.Periodo periodo = (CalculadoraReciclagem.Periodo) cmbPeriodo.getSelectedItem();

            // Valida os valores
            validarEntradas(garrafas2L, garrafas1L, garrafasAguaMineral);

            // Realiza o cálculo
            CalculadoraReciclagem calculadora = new CalculadoraReciclagem();
            CalculadoraReciclagem.ResultadoCalculo resultado = calculadora.calcularPotencialAnual(
                    garrafas2L, garrafas1L, garrafasAguaMineral, periodo
            );

            // Exibe os resultados na tela
            exibirResultados(resultado);

        } catch (NumberFormatException ex) {
            exibirErro("Entrada Inválida", 
                      "Por favor, insira apenas números inteiros válidos para o consumo de garrafas.");
        } catch (IllegalArgumentException ex) {
            exibirErro("Erro de Lógica", ex.getMessage());
        } catch (Exception ex) {
            exibirErro("Erro Inesperado", "Ocorreu um erro: " + ex.getMessage());
        }
    }

    // --- VALIDA AS ENTRADAS DO USUÁRIO ---
    private void validarEntradas(int garrafas2L, int garrafas1L, int garrafasAguaMineral) {
        if (garrafas2L < 0 || garrafas1L < 0 || garrafasAguaMineral < 0) {
            throw new IllegalArgumentException("Os valores de consumo não podem ser negativos.");
        }
    }

    // --- EXIBE OS RESULTADOS ---
    private void exibirResultados(CalculadoraReciclagem.ResultadoCalculo resultado) {
        lblPesoAnual.setText(DF_PESO.format(resultado.getPesoKgAnual()) + " kg");
        lblValorAnual.setText(DF_VALOR.format(resultado.getValorAnual()));
    }

    // --- EXIBE MENSAGEM DE ERRO ---
    private void exibirErro(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
    }

    // --- MÉTODO PRINCIPAL ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReciclagemApp());
    }
}