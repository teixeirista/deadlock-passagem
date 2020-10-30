package model;

/**
 *
 * @author mathe
 */
public class Trecho {
    private String partida; //Cidade de partida do trecho da viagem
    private String destino; //Cidade de destino do trecho da viagem
    private char companhia; //Companhia que executa o trecho
    
    public Trecho(String p, String d, char c) {
        partida = p;
        destino = d;
        companhia = c;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public char getCompanhia() {
        return companhia;
    }

    public void setCompanhia(char companhia) {
        this.companhia = companhia;
    }
}
