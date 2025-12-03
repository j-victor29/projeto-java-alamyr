public class CalculadoraReciclagem { // Classe principal

    private static final double TAMPINHAS_POR_KG_GARRAFAS_GRANDES = 500.0; // 500 tampinhas = 1kg (2L/1L)
    private static final double TAMPINHAS_POR_KG_AGUA_MINERAL = 1000.0;    // 1000 tampinhas = 1kg (água)
    private static final double PRECO_POR_KG = 0.98;                       // Valor do kg reciclado

    public enum Periodo { // Enum dos períodos
        DIARIO(365.0),     // Multiplica por 365
        SEMANAL(52.0),     // Multiplica por 52
        MENSAL(12.0),      // Multiplica por 12
        ANUAL(1.0);        // Já anual

        private final double fatorAnualizacao; // Guarda o multiplicador

        Periodo(double fatorAnualizacao) { // Construtor do enum
            this.fatorAnualizacao = fatorAnualizacao; // Armazena o fator
        }

        public double getFatorAnualizacao() { return fatorAnualizacao; } // Retorna fator
    }

    public ResultadoCalculo calcularPotencialAnual( // Método que calcula tudo
            int garrafas2L,            // Qtd de 2L
            int garrafas1L,            // Qtd de 1L
            int garrafasAguaMineral,   // Qtd de água
            Periodo periodo) {         // Período informado

        int totalTampinhasPeriodo =
                garrafas2L + garrafas1L + garrafasAguaMineral; // Soma as tampinhas (1 por garrafa)

        double pesoKgGrandes =
                (garrafas2L + garrafas1L) / TAMPINHAS_POR_KG_GARRAFAS_GRANDES; // Peso kg das grandes

        double pesoKgAgua =
                garrafasAguaMineral / TAMPINHAS_POR_KG_AGUA_MINERAL; // Peso kg das de água

        double pesoKgPeriodo =
                pesoKgGrandes + pesoKgAgua; // Peso total do período (kg)

        double pesoKgAnual =
                pesoKgPeriodo * periodo.getFatorAnualizacao(); // Converte para equivalente anual

        double valorAnual =
                pesoKgAnual * PRECO_POR_KG; // Calcula o valor em R$ por ano

        return new ResultadoCalculo(pesoKgAnual, valorAnual); // Retorna os resultados
    }

    public static class ResultadoCalculo { // Classe do resultado
        private final double pesoKgAnual; // Peso anual em kg
        private final double valorAnual;  // Valor anual em R$

        public ResultadoCalculo(double pesoKgAnual, double valorAnual) { // Construtor
            this.pesoKgAnual = pesoKgAnual; // Salva peso
            this.valorAnual = valorAnual;   // Salva valor
        }

        public double getPesoKgAnual() { return pesoKgAnual; } // Retorna peso anual
        public double getValorAnual() { return valorAnual; }   // Retorna valor anual
    }
}