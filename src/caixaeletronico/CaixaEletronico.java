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
    public String pegaValorTotalDisponivel() {
        // TODO: implementar logica de soma total
        return "";
    }

    @Override
    public String sacar(Integer valor) {
        // TODO: implementar logica de saque (algoritmo guloso)
        return "";
    }

    @Override
    public String pegaRelatorioCedulas() {
        // TODO: implementar logica de relatorio
        return "";
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