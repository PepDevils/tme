package pt.pepedevils.tme.model;
import java.util.ArrayList;

/**
 * Created by dezvezesdez on 26/05/2017.
 */

public class Equipa {

    private ArrayList<Jogador> jogadores;


    public Equipa(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
