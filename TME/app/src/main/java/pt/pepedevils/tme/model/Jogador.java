package pt.pepedevils.tme.model;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by dezvezesdez on 24/05/2017.
 */

public class Jogador implements Comparable<Jogador> {

    private String Nome;
    private int Nivel;

    private int vitorias;
    private int derrotas;
    private int empates;

    public Jogador(String nome, int nivel) {
        Nome = nome;
        Nivel = nivel;
    }

    @Override
    public String toString() {
        return Nome;
    }

    @Override
    public int hashCode() {
        String aux = characterwiseCaseNormalize(Nome);
        int res = aux.hashCode();
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Jogador) {
            if (((Jogador) obj).getNome().equalsIgnoreCase(this.getNome())) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }

    }

    private static String characterwiseCaseNormalize(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            //sb.setCharAt(i,Character.toLowerCase(Character.toUpperCase(sb.charAt(i))));
            sb.setCharAt(i, Character.toLowerCase(s.charAt(i)));
        }
        return sb.toString();
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Integer getPeso() {
        return Nivel;
    }

    public void setPeso(int nivel) {
        Nivel = nivel;
    }


    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }

    @Override
    public int compareTo(@NonNull Jogador jogador) {
        return getPeso();
    }

    public static Comparator<Jogador> NivelComparator = new Comparator<Jogador>() {

        @Override
        public int compare(Jogador jog1, Jogador jog2) {
            int diff = jog1.getPeso() - jog2.getPeso();
            return diff;
        }
    };
}
