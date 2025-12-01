public class CalculadoraReciclagem {

    // --- CONSTANTES DE CONVERSÃO ---
    /** Quantidade de tampinhas de 2L e 1L que equivalem a 1 quilograma */
    private static final double TAMPINHAS_POR_KG_GARRAFAS_GRANDES = 500.0;
    
    /** Quantidade de tampinhas de água mineral que equivalem a 1 quilograma */
    private static final double TAMPINHAS_POR_KG_AGUA_MINERAL = 1000.0;
    
    /** Preço de mercado por quilograma de tampinhas recicladas (em reais) */
    private static final double PRECO_POR_KG = 0.98;

    // --- ENUM PERÍODO DE CONSUMO ---
    /**
     * Enum para representar o período de consumo e seu fator de anualização.
     * 
     * Cada período possui um fator que converte a quantidade consumida no período
     * para uma projeção anual.
     */
    public enum Periodo {
        DIARIO(365.0),      // 365 dias em um ano
        SEMANAL(52.0),      // 52 semanas em um ano
        MENSAL(12.0),       // 12 meses em um ano
        ANUAL(1.0);         // Já é anual, sem conversão

        private final double fatorAnualizacao;

        Periodo(double fatorAnualizacao) {
            this.fatorAnualizacao = fatorAnualizacao;
        }

        public double getFatorAnualizacao() {
            return fatorAnualizacao;
        }
    }

    // --- CÁLCULO PRINCIPAL ---
    /**
     * Calcula o potencial anual de reciclagem de tampinhas PET.
     *
     * Este método realiza 4 etapas:
     * 1. Contabiliza o total de tampinhas geradas no período
     * 2. Converte para quilogramas (usando diferentes proporções por tipo)
     * 3. Anualiza o peso (projeta para 12 meses)
     * 4. Calcula o valor financeiro (peso anual × preço por kg)
     *
     * @param garrafas2L Quantidade de garrafas PET de 2 litros consumidas no período
     * @param garrafas1L Quantidade de garrafas PET de 1 litro consumidas no período
     * @param garrafasAguaMineral Quantidade de garrafas PET de água mineral consumidas no período
     * @param periodo O período de consumo (Diário, Semanal, Mensal, Anual)
     * 
     * @return Um objeto ResultadoCalculo com o peso anual e valor anual estimado
     */
    public ResultadoCalculo calcularPotencialAnual(
            int garrafas2L,
            int garrafas1L,
            int garrafasAguaMineral,
            Periodo periodo) {

        // ETAPA 1: Contabilizar o total de tampinhas no período