package caixaeletronico; // Define que esta classe pertence ao pacote "caixaeletronico"

import java.util.ArrayList; // Importa a classe ArrayList para usar listas dinâmicas

public class CaixaEletronico implements ICaixaEletronico { // Declara a classe pública e diz que ela cumpre o contrato da interface ICaixaEletronico

    // Matriz 6x2: coluna 0 = valor da cedula, coluna 1 = quantidade disponivel
    private int[][] cedulas = { // Declara e inicializa uma matriz privada de inteiros com 6 linhas e 2 colunas
            {100, 100}, // Cédula de R$100, com 100 unidades disponíveis
            {50, 200},  // Cédula de R$50, com 200 unidades disponíveis
            {20, 300},  // Cédula de R$20, com 300 unidades disponíveis
            {10, 350},  // Cédula de R$10, com 350 unidades disponíveis
            {5, 450},   // Cédula de R$5, com 450 unidades disponíveis
            {2, 500}    // Cédula de R$2, com 500 unidades disponíveis
    };

    private ArrayList<String> historico = new ArrayList<>(); // Cria uma lista dinâmica de Strings para guardar o histórico de operações

    // Valor minimo de cedulas que o caixa precisa manter para continuar operando
    private int cotaMinima = 0; // Declara a cota mínima como zero (sem restrição por padrão)

    @Override // Indica que este método está sendo implementado a partir da interface
    public String pegaValorTotalDisponivel() {
        int total = 0; // Inicializa o acumulador do total com zero

        for (int i = 0; i < cedulas.length; i++) { // Percorre cada linha da matriz de cédulas
            // soma o valor da nota x quantidade da nota
            total += cedulas[i][0] * cedulas[i][1]; // Multiplica o valor da cédula pela sua quantidade e acumula no total
        }
        return "O total restante do caixa é de R$: " + total; // Retorna uma String com o valor total calculado
    }

    @Override // Indica que este método está sendo implementado a partir da interface
    public String pegaRelatorioCedulas() {
        // Cria String de cabeçalho
        String texto = "=== Relatório de Cédulas ===\n\n"; // Inicializa a String de resultado com o cabeçalho do relatório

        for (int i = 0; i < cedulas.length; i++) { // Percorre cada linha da matriz de cédulas
            // A cada looping é adicionado a frase a String texto
            texto += "R$ " + cedulas[i][0] + " - " + cedulas[i][1] + " cédulas\n"; // Concatena o valor e a quantidade de cada cédula na String
        }

        return texto; // Retorna o relatório completo como String
    }

    @Override // Indica que este método está sendo implementado a partir da interface
    public String sacar(Integer valor) {
        int total = 0; // Inicializa o acumulador do total disponível com zero

        // condicional de validação de valor
        if (valor == null || valor <= 0) { // Verifica se o valor informado é nulo ou menor/igual a zero
            return "Valor inválido para saque"; // Retorna mensagem de erro se o valor for inválido
        }

        // Calcula o total disponível no caixa
        for (int i = 0; i < cedulas.length; i++) { // Percorre todas as cédulas para somar o total disponível
            total += cedulas[i][0] * cedulas[i][1]; // Acumula o valor de cada tipo de cédula (valor x quantidade)
        }

        // Se o caixa está abaixo da cota mínima, encerra o atendimento
        if (total <= cotaMinima) { // Verifica se o total disponível é menor ou igual à cota mínima definida
            return "Caixa Vazio: Chame o Operador"; // Retorna mensagem de caixa indisponível
        }

        // Array temporário para guardar quantas notas de cada tipo serão usadas
        // O desconto real só acontece depois de confirmar que o saque é possível
        int[] notasUsadas = new int[cedulas.length]; // Cria um array com o mesmo número de posições que a matriz de cédulas
        int restante = valor; // Inicializa o restante com o valor total do saque

        // Tenta pagar com as maiores notas primeiro (100, 50, 20, 10, 5, 2)
        for (int i = 0; i < cedulas.length; i++) { // Percorre as cédulas da maior para a menor

            // Só usa essa nota se ela couber no restante e houver quantidade disponível
            if (cedulas[i][0] <= restante && cedulas[i][1] > 0) { // Verifica se o valor da cédula cabe no restante E se há cédulas desse tipo disponíveis

                // Calcula quantas notas desse tipo são necessárias
                int quantidade = restante / cedulas[i][0]; // Divide o restante pelo valor da cédula para saber quantas são necessárias

                // Se precisar de mais notas do que tem disponível, usa só o que tem
                if (quantidade > cedulas[i][1]) { // Verifica se a quantidade necessária supera o estoque disponível
                    quantidade = cedulas[i][1]; // Limita a quantidade ao máximo disponível no caixa
                }

                // Guarda temporariamente a quantidade usada e atualiza o restante
                notasUsadas[i] = quantidade; // Armazena no array temporário quantas notas desse tipo serão usadas
                restante -= cedulas[i][0] * quantidade; // Subtrai do restante o valor total das notas usadas
            }
        }

        // Se após percorrer todas as notas ainda sobrou restante, o saque não é possível
        if (restante != 0) { // Verifica se ainda há valor a ser pago após usar todas as cédulas possíveis
            return "Saque não realizado por falta de cédulas"; // Retorna mensagem de erro pois não foi possível completar o saque
        }

        // Soma o total de cédulas que seriam emitidas neste saque
        int totalCedulas = 0; // Inicializa o contador de cédulas com zero
        for (int i = 0; i < notasUsadas.length; i++) { // Percorre o array de notas usadas
            totalCedulas += notasUsadas[i]; // Acumula a quantidade total de cédulas que serão emitidas
        }

        // Não pode emitir mais de 30 cédulas em um único saque
        if (totalCedulas > 30) { // Verifica se o total de cédulas excede o limite permitido por operação
            return "Saque não realizado: excede o limite de 30 cédulas por operação"; // Retorna mensagem de erro por excesso de cédulas
        }

        // Saque confirmado: desconta as notas do caixa e monta a resposta
        String resposta = "=== Saque de R$ " + valor + " ===\n\n"; // Inicializa a String de resposta com o cabeçalho do comprovante
        for (int i = 0; i < cedulas.length; i++) { // Percorre todas as cédulas para atualizar o estoque e montar o comprovante
            if (notasUsadas[i] > 0) { // Só processa as cédulas que foram realmente usadas no saque
                cedulas[i][1] -= notasUsadas[i]; // Desconta do estoque a quantidade de cédulas usadas
                resposta += "R$ " + cedulas[i][0] + " x " + notasUsadas[i] + " notas\n"; // Adiciona ao comprovante a linha com o valor e quantidade da cédula
            }
        }

        historico.add("SAQUE: R$ " + valor); // Registra o saque no histórico de operações

        return resposta; // Retorna o comprovante do saque com as cédulas utilizadas
    }

    @Override // Indica que este método está sendo implementado a partir da interface
    public String reposicaoCedulas(Integer cedula, Integer quantidade) {

        // validação da quantidade e nota correta
        if (quantidade == null || quantidade <= 0) { // Verifica se a quantidade é nula ou inválida
            return "Quantidade inválida"; // Retorna mensagem de erro para quantidade inválida
        }
        if (cedula == null || cedula <= 0) { // Verifica se o valor da cédula é nulo ou inválido
            return "Valor de cédula inválido"; // Retorna mensagem de erro para cédula inválida
        }

        // percorre a matriz procurando a cédula informada pelo usuário
        for (int i = 0; i < cedulas.length; i++) { // Percorre todas as linhas da matriz de cédulas

            // verifica se o valor da linha atual bate com a cédula solicitada
            if (cedulas[i][0] == cedula) { // Compara o valor da cédula na matriz com o valor informado pelo usuário

                // soma a quantidade reposta à quantidade já existente na matriz
                cedulas[i][1] += quantidade; // Adiciona a quantidade reposta ao estoque atual daquela cédula

                // Faz a gravação de reposição no arraylist
                historico.add("REPOSIÇÃO: " + quantidade + " notas de R$ " + cedula); // Registra a reposição no histórico

                return "Reposição realizada! Nota R$ " + cedula +
                        " agora tem " + cedulas[i][1] + " unidade(s)."; // Retorna mensagem de confirmação com o novo estoque
            }
        }

        // Se chegar aqui, a cédula informada não existe no caixa
        return "Cédula de R$ " + cedula + " não reconhecida."; // Retorna erro caso a cédula não exista na matriz
    }

    // Salva o valor mínimo que o caixa precisa ter para continuar atendendo
    @Override // Indica que este método está sendo implementado a partir da interface
    public String armazenaCotaMinima(Integer minimo) {
        if(minimo <=0){
            return "Cota mínima inválida";
        }
        cotaMinima = minimo; // Atribui o valor informado à variável de cota mínima
        return "Cota mínima definida: R$ " + minimo; // Retorna mensagem de confirmação com o valor definido
    }

    public String geraExtrato() { // Método público que gera o extrato completo de operações do caixa
        String extrato = "=== EXTRATO DO CAIXA ===\n\n"; // Inicializa a String do extrato com o cabeçalho
        for (int i = 0; i < historico.size(); i++) { // Percorre todos os registros do histórico
            extrato += historico.get(i) + "\n"; // Adiciona cada registro ao extrato com quebra de linha
        }
        extrato += "\n========================\n"; // Adiciona o rodapé de fechamento ao extrato
        return extrato; // Retorna o extrato completo como String
    }

    public static void main(String[] args) { // Método principal: ponto de entrada do programa
        GUI janela = new GUI(CaixaEletronico.class); // Cria a janela gráfica passando a classe CaixaEletronico como referência
        janela.show(); // Exibe a janela na tela
    }
}
