package pt.pepedevils.tme.model;

import java.util.Date;

/**
 * Created by dezvezesdez on 26/05/2017.
 */

public class Jogos {

    private String Nome;
    private Date data;
    private Campeonato campeonato;
    private Equipa equipa_vermelha;
    private Equipa equipa_azul;

    public Jogos(String nome, Date data, Campeonato campeonato, Equipa equipa_vermelha, Equipa equipa_azul) {
        Nome = nome;
        this.data = data;
        this.campeonato = campeonato;
        this.equipa_vermelha = equipa_vermelha;
        this.equipa_azul = equipa_azul;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Equipa getEquipa_vermelha() {
        return equipa_vermelha;
    }

    public void setEquipa_vermelha(Equipa equipa_vermelha) {
        this.equipa_vermelha = equipa_vermelha;
    }

    public Equipa getEquipa_azul() {
        return equipa_azul;
    }

    public void setEquipa_azul(Equipa equipa_azul) {
        this.equipa_azul = equipa_azul;
    }
}
