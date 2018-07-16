package pt.pepedevils.tme.model;

import java.util.Date;

/**
 * Created by dezvezesdez on 26/05/2017.
 */

public class Campeonato {

    private Equipa vermelha;
    private Equipa azul;
    private Date dataCriacao;

    public Campeonato(Equipa vermelha, Equipa azul, Date dataCriacao) {
        this.vermelha = vermelha;
        this.azul = azul;
        this.dataCriacao = dataCriacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Equipa getVermelha() {
        return vermelha;
    }

    public void setVermelha(Equipa vermelha) {
        this.vermelha = vermelha;
    }

    public Equipa getAzul() {
        return azul;
    }

    public void setAzul(Equipa azul) {
        this.azul = azul;
    }
}
