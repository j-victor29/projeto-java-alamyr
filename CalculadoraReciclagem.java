public class CalculadoraReciclagem { // calcula a reciclagem das tampinhas

    private static final double TAMPINHAS_GRANDES_POR_KG = 500.0;   // 500 tampinhas de 1L e 2L = 1 kg
    private static final double TAMPINHAS_AGUA_POR_KG = 1000.0;     // 1000 tampinhas de água = 1 kg
    private static final double PRECO_KG = 0.98;                    // preço por kg reciclado

    public enum PeriodoColeta { // tipo de período que o usuário escolhe
        DIARIO(365.0),   // todo dia multiplica por 365
        SEMANAL(52.0),   // toda semana, 52 vezes por ano
        MENSAL(12.0),    // todo mês, 12 vezes
        ANUAL(1.0);      // uma vez por ano

        private final double multiplicadorAnual; // fator de multiplicação pro ano

        PeriodoColeta(double multiplicadorAnual) {
            this.multiplicadorAnual = multiplicadorAnual;
        }

        public double getMultiplicador() {
            return multiplicadorAnual;
        }
    }

    public Resultado calcularAnual(
            int tampinhas2L,        // tampinhas de garrafa 2L
            int tampinhas1L,        // tampinhas de garrafa 1L
            int tampinhasAgua,      // tampinhas de água mineral
            PeriodoColeta periodo   // qual período foi escolhido
    ) {
        double pesoGrandes = (tampinhas2L + tampinhas1L) / TAMPINHAS_GRANDES_POR_KG; // peso em kg das grandes
        double pesoAgua = tampinhasAgua / TAMPINHAS_AGUA_POR_KG; // peso em kg da água
        double pesoTotal = pesoGrandes + pesoAgua; // peso total do período
        double pesoAnual = pesoTotal * periodo.getMultiplicador(); // converte pro ano
        double valorAnual = pesoAnual * PRECO_KG; // valor em reais

        return new Resultado(pesoAnual, valorAnual);
    }

    // ----- CLASSE QUE GUARDA O RESULTADO -----
    public static class Resultado {
        private final double pesoEmKgAno;        // Peso total em kg no ano
        private final double valorEmReaisAno;    // Valor total em reais no ano

        public Resultado(double pesoEmKgAno, double valorEmReaisAno) { // Guarda os valores
            this.pesoEmKgAno = pesoEmKgAno;
            this.valorEmReaisAno = valorEmReaisAno;
        }

        public double getPesoKgAnual() { return pesoEmKgAno; }         // Retorna o peso
        public double getValorAnual() { return valorEmReaisAno; } // Retorna o valor
    }
}