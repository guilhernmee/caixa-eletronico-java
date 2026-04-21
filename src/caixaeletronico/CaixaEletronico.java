package caixaeletronico;

public class CaixaEletronico implements ICaixaEletronico {

    // Matriz 6x2: coluna 0 = valor da cedula, coluna 1 = quantidade disponivel
    private int[][] cedulas = {
            {100, 100},
            {50, 200},
            {20, 300},
            {10, 350},
            {5, 450},
            {2, 500}
    };

    // Valor minimo de cedulas que o caixa precisa manter para continuar operando
    private int cotaMinima = 0;

    @Override
    public String pegaValorTotalDisponivel() {
        int total = 0;

        for (int i = 0; i < cedulas.length; i++) {
            // soma o valor da nota x quantidade da nota
            total += cedulas[i][0] * cedulas[i][1];
        }
        return "O total restante do caixa é de R$: " + total;
    }

    @Override
    public String pegaRelatorioCedulas() {
        // Cria String de cabeçalho
        String texto = "=== Relatório de Cédulas ===\n\n";
        for (int i = 0; i < cedulas.length; i++) {

            // A cada looping é adicionado a frase a String texto
            texto += "R$ " + cedulas[i][0] + " - " + cedulas[i][1] + " cédulas\n";
        }

        return texto;
    }

    @Override
    public String sacar(Integer valor) {
        // TODO: implementar logica de saque (algoritmo guloso)
        return "";
    }



    @Override
    public String reposicaoCedulas(Integer cedula, Integer quantidade) {
        // TODO: implementar logica de reposicao
        return "";
    }

    @Override
    public String armazenaCotaMinima(Integer minimo) {
        cotaMinima = minimo;
        return "Cota mínima definida: R$ " + minimo;
    }

    public static void main(String[] args) {
        // TODO: descomentar quando a classe GUI estiver disponivel
        // GUI janela = new GUI(CaixaEletronico.class);
        // janela.show();

    }
}