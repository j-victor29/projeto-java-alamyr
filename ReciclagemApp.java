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
