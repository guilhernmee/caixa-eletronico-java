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
        int total = 0;
        // Calcula o total disponível no caixa
        for (int i = 0; i < cedulas.length; i++) {
            total += cedulas[i][0] * cedulas[i][1]; // O valor da nota multiplicado pela qtd disponivel
        }

        // Se o caixa está abaixo da cota mínima, encerra o atendimento
        if (total <= cotaMinima) {
            return "Caixa Vazio: Chame o Operador";
        }

        // Array temporário para guardar quantas notas de cada tipo serão usadas
        // O desconto real só acontece depois de confirmar que o saque é possível
        int[] notasUsadas = new int[cedulas.length];
        int restante = valor;

        // Tenta pagar com as maiores notas primeiro (100, 50, 20, 10, 5, 2)
        for (int i = 0; i < cedulas.length; i++) {

            // Só usa essa nota se ela couber no restante e houver quantidade disponível
            if (cedulas[i][0] <= restante && cedulas[i][1] > 0) {

                // Calcula quantas notas desse tipo são necessárias
                int quantidade = restante / cedulas[i][0];

                // Se precisar de mais notas do que tem disponível, usa só o que tem
                if (quantidade > cedulas[i][1]) {
                    quantidade = cedulas[i][1];
                }

                // Guarda temporariamente a quantidade usada e atualiza o restante
                notasUsadas[i] = quantidade;
                restante -= cedulas[i][0] * quantidade;
            }
        }

        // Se após percorrer todas as notas ainda sobrou restante, o saque não é possível
        if (restante != 0) {
            return "Saque não realizado por falta de cédulas";
        }

        // Soma o total de cédulas que seriam emitidas neste saque
        int totalCedulas = 0;
        for (int i = 0; i < notasUsadas.length; i++) {
            totalCedulas += notasUsadas[i];
        }

        // Não pode emitir mais de 30 cédulas em um único saque
        if (totalCedulas > 30) {
            return "Saque não realizado: excede o limite de 30 cédulas por operação";
        }

        // Saque confirmado: desconta as notas do caixa e monta a resposta
        String resposta = "=== Saque de R$ " + valor + " ===\n\n";
        for (int i = 0; i < cedulas.length; i++) {
            if (notasUsadas[i] > 0) {
                cedulas[i][1] -= notasUsadas[i];
                resposta += "R$ " + cedulas[i][0] + " x " + notasUsadas[i] + " notas\n";
            }
        }

        return resposta;
    }




    @Override
    public String reposicaoCedulas(Integer cedula, Integer quantidade) {
        // percorre a matriz procurando a cédula informada pelo usuário
        for (int i = 0; i < cedulas.length; i++) {

            // verifica se o valor da linha atual bate com a cédula solicitada
            if (cedulas[i][0] == cedula) {

                // soma a quantidade reposta à quantidade já existente na matriz
                cedulas[i][1] += quantidade;

                return "Reposição realizada! Nota R$ " + cedula +
                        " agora tem " + cedulas[i][1] + " unidade(s).";
            }
        }

        // Se chegar aqui, a cédula informada não existe no caixa
        return "Cédula de R$ " + cedula + " não reconhecida.";
    }

    // Salva o valor mínimo que o caixa precisa ter para continuar atendendo
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