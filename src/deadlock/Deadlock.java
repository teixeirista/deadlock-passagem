package deadlock;

import java.util.ArrayList;
import java.util.Scanner;
import model.Trecho;

/**
 *
 * @author mathe
 */
public class Deadlock {
    
    static ArrayList<Trecho> listaTrechos = new ArrayList(); //Lista contendo os três trechos de viagem
    
    static int totais[] = new int[3]; //Vetor dos recursos totais
    static int alocados[] = new int[3]; //Vetor dos recursos alocados
    static int disponiveis[] = new int[3]; //Vetor dos recursos disponíveis
    
    static int matrizAlocados[][] = new int[3][3]; //Matriz dos recursos alocados para cada processo
    static int matrizRequisitados[][] = new int[3][3]; //Matriz dos recursos alocados para cada processo

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        geraTrechos(); //Insere os três trechos pré-designados na lista
        inicializaVetores(); //Inicializa os vetores e matrizes necessários com os valores pré-estabelecidos
        
        //Executa a reserva das passagens dos três passageiros
        for(int i = 0; i < 3; i++){
            compraPassagem(i+1);
        }
        
        //Exibe os recursos disponíveis
        System.out.println("\nRecursos disponíveis:");
        for (int i = 0; i < 3; i++) {
            System.out.print(disponiveis[i] + " ");
        }
        System.out.println(" ");
        
        int op; //Variável utilizada para escolha da opção do menu
        while(true) { //Repete enquanto a opção de encerrar a aplicação não é escolhida
            if(verificaConcluiu()) { //Verifica se as matrizes de recursos alocados e requeridos estão vazias
                System.out.println("\nNão há processos para serem executados.");
                System.exit(0); //Sai da aplicação caso não tenha mais processos a fazer
            } else {
                //Menu
                System.out.println("\nEscolha uma opção:");
                System.out.println("1 - Alocar recursos");
                System.out.println("2 - Executar processo");
                System.out.println("3 - Encerrar aplicação");
            
                //Recebe a entrada do usuário
                Scanner ent = new Scanner(System.in);
                op = ent.nextInt();
            
                if(op == 1) {
                    alocaRecursos(); //Se a opção 1 for escolhida, aloca os recursos
                } else if(op == 2) {
                    executaProcesso(); //Se a opção 2 for escolhida, executa os processos
                } else if (op == 3) {
                    System.exit(0); //Se a opção 3 for escolhida, encerra o programa
                }
            }
        }
    }
    
    /**
     * Executa a reserva de passagens dos três compradores
     * @param aux 
     */
    public static void compraPassagem(int aux) {
        int a; //Auxiliar que recebe a entrada do usuário
        
        for(int i = 0; i < listaTrechos.size(); i++) {
            //Menu que pede a quantidade de passagens para um determinado trecho
            System.out.println("\nDigite quantas passagens o passageiro " + aux + " vai querer pro trecho:");
            System.out.println((i+1) + " - Partida: " + listaTrechos.get(i).getPartida() + ", Destino: " + listaTrechos.get(i).getDestino());
            System.out.println("(Mínimo 0 e máximo 30)");
            
            Scanner ent = new Scanner(System.in); //Recebe o valor que o usuário digitou
            a = Math.abs(ent.nextInt());
            
            matrizRequisitados[aux-1][i] = a; //Insere o valor digitado na matriz de recursos requisitados
        }
        
        //Exibe a tabela de recursos requisitados
        System.out.println("\nTabela de recursos requisitados:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) { 
                System.out.print(matrizRequisitados[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
    
    /**
     * Insere os três trechos da viagem
     */
    public static void geraTrechos() {
        listaTrechos.add(new Trecho("Belém", "Fortaleza", 'A'));
        listaTrechos.add(new Trecho("Fortaleza", "São Paulo", 'B'));
        listaTrechos.add(new Trecho("São Paulo", "Curitiba", 'C'));
    }
    
    /**
     * Aloca os recursos para um determinado processo, quando é possível
     */
    public static void alocaRecursos() {
        for(int i=0; i<3; i++) { //Percorre as três linhas
            //Verifica se existem recursos disponíveis suficientes para os recursos rquisitados
            if(matrizRequisitados[i][0] <= disponiveis[0] && matrizRequisitados[i][1] <= disponiveis[1] && matrizRequisitados[i][2] <= disponiveis[2]) {
                for(int j=0; j<3; j++) { //Percorre as colunas
                    disponiveis[j] -= matrizRequisitados[i][j]; //Subtrai os recursos requisitados dos disponíveis
                    matrizAlocados[i][j] += matrizRequisitados[i][j]; //Aloca os recursos requisitados
                    matrizRequisitados[i][j] = 0; //Zera os recursos requisitados
                }
            }
        }
        exibeStatus(); //Exibe o status das matrizes e vetores
    }
    
    /**
     * Executa os processos com recursos já alocados, quando é possível
     */
    public static void executaProcesso() {
        for(int i=0; i < 3; i++) { //Percorre as três linhas
            //Verifica se o processo possui recursos alocados para sua execução
            if(matrizAlocados[i][0] != 0 || matrizAlocados[i][1] != 0 || matrizAlocados[i][2] != 0) {
                for(int j=0; j < 3; j++) { //Percorre as colunas
                    disponiveis[j] += matrizAlocados[i][j]; //Recoloca os recursos alocados como disponíveis
                    matrizAlocados[i][j] = 0; //Zera o recurso alocado
                }
                //Informa que a viagem do passageiro conseguiu ser confirmada
                System.out.println("\nA viagem do passageiro " + (i+1) + " foi confimada!");
            }
        }
        exibeStatus(); //Exibe o status das matrizes e vetores
    }
    
    /**
     * Inicializa os vetores e matrizes
     */
    public static void inicializaVetores() {
        for (int i = 0; i < 3; i++) { //Percorre as linhas das matrizes ou as colunas dos vetores
            totais[i] = 30; //Determina a quantidade de passagens total de cada trecho
            disponiveis[i] = 30; //Determina a quantidade de passagens disponíveis no início
            alocados[i] = 0; //Determina a quantidade de passagens compradas
            for (int j=0; j < 3; j++) { //Percorre as colunas das matrizes
                matrizAlocados[i][j] = 0; //Determina que nenhum recurso foi alocado ainda
            }
        }
    }
    
    /**
     * Verifica se as matrizes de recursos requisitados e alocados estão vazias
     * @return Retorna verdadeiro ou falso de acordo com a verificação
     */
    public static boolean verificaConcluiu() {
        for(int i=0; i < 3; i++) { //Percorre as linhas
            for (int j=0; j < 3; j++) { //Percorre as colunas
                //Verifica se as matrizes estão vazias
                if(matrizRequisitados[i][j] != 0 || matrizAlocados[i][j] != 0)
                    return false; //Se não, retorna falso
            }
        }
        return true; //Se sim, retorna verdadeiro
    }
    
    /**
     * Exibe os status dos vetores e matrizes
     */
    public static void exibeStatus() {
        System.out.println("\nTabela de recursos requisitados:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(matrizRequisitados[i][j] + " ");
            }
            System.out.println(" ");
        }
        
        System.out.println("\nTabela de recursos alocados:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(matrizAlocados[i][j] + " ");
            }
            System.out.println(" ");
        }
        
        System.out.println("\nRecursos disponíveis:");
        for (int i = 0; i < 3; i++) {
            System.out.print(disponiveis[i] + " ");
        }
        System.out.println(" ");
    }
}
