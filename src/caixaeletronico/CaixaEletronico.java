package caixaeletronico;

public class CaixaEletronico implements ICaixaEletronico {

    // Matriz 6x2: coluna 0 = valor da cedula, coluna 1 = quantidade disponivel
    private int[][] cedulas = {
            {100, 100},
            {50,  200},
            {20,  300},
            {10,  350},
            {5,   450},
            {2,   500}
    };

    // Valor minimo de cedulas que o caixa precisa manter para continuar operando
    private int cotaMinima = 0;

    @Override
    public String pegaValorTotalDisponivel() { // método_ testado!!! TUDO OK
        int total = 0;

        for (int i = 0; i < cedulas.length; i++) {
            // mostra a quantidade de notas do caixa
            System.out.println("O caixa tem " + cedulas[i][1] + " notas de R$ " + cedulas[i][0]);

            // soma o valor da nota x quantidade da nota
            total += cedulas[i][0] * cedulas[i][1];
        }
        return "O total restante do caixa é de R$: " + total;
    }

    @Override
    public String sacar(Integer valor) {
        // TODO: implementar logica de saque (algoritmo guloso)
        return "";
    }

    @Override
    public String pegaRelatorioCedulas() {
        // inicia a string de resposta com um cabeçalho
        String resposta = "=== Relatório de Cédulas ===\n";

        // corre cada linha da matriz (cada tipo de cédula)
        for (int i = 0; i < cedulas.length; i++) {
            // cedulas[i][0] = valor da nota | cedulas[i][1] = quantidade disponível
            resposta += "Nota R$ " + cedulas[i][0] + ": " + cedulas[i][1] + " unidade(s)\n";
        }

        return resposta;
    }

    @Override
    public String reposicaoCedulas(Integer cedula, Integer quantidade) {
        // TODO: implementar logica de reposicao
        return "";
    }

    @Override
    public String armazenaCotaMinima(Integer minimo) {
        // TODO: implementar logica de armazenamento da cota minima
        return "";
    }

    public static void main(String[] args) {
        // TODO: descomentar quando a classe GUI estiver disponivel
        // GUI janela = new GUI(CaixaEletronico.class);
        // janela.show();
    }
}