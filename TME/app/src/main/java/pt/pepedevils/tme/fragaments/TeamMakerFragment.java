package pt.pepedevils.tme.fragaments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import pt.pepedevils.tme.R;
import pt.pepedevils.tme.model.Campeonato;
import pt.pepedevils.tme.model.Equipa;
import pt.pepedevils.tme.model.Jogador;
import pt.pepedevils.tme.utils.Helper;

public class TeamMakerFragment extends Fragment {

    private ArrayList<Jogador> jogadores = new ArrayList<>();
    private ArrayList<Jogador> red_team = new ArrayList<>();
    private ArrayList<Jogador> blue_team = new ArrayList<>();
    private View v;

    private TextView redteam_list;
    private TextView blueteam_list;

    private Button bt_save;

    public TeamMakerFragment() {
        // Required empty public constructor
    }

    public static TeamMakerFragment newInstance() {
        TeamMakerFragment fragment = new TeamMakerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jogadores = Helper.getListOnSharedPreferences(getActivity());


        //Collections.sort(jogadores);

        Log.i("Pepe", "onCreate: " + jogadores);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_team_maker, container, false);

        Button bt_balance = (Button) v.findViewById(R.id.bt_balance);
        Button bt_random = (Button) v.findViewById(R.id.bt_random);
        bt_save = (Button) v.findViewById(R.id.bt_save);

        redteam_list = (TextView) v.findViewById(R.id.redteam_list);
        blueteam_list = (TextView) v.findViewById(R.id.blueteam_list);


        bt_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(jogadores, Jogador.NivelComparator); //ordena por ordem do seu nivel
                ConstrutorDasEquipas();
                BalancearEquipas();
                GuardarButtonToggle();
            }
        });

        bt_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.shuffle(jogadores); //faz o random
                ConstrutorDasEquipas();
                GuardarButtonToggle();
            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //criar equipas

                Equipa vermelha = new Equipa(red_team);
                Equipa azul = new Equipa(blue_team);
                Date actual_date = new Date();

                Campeonato camp = new Campeonato(vermelha, azul, actual_date);

                try {
                //get arraylist from sharedprefferences com campeonatos
                ArrayList<Campeonato> todos_os_campeonatos = new ArrayList<>();
                SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Gson gson = new Gson();
                int total = appSharedPrefs.getInt("total_campeonatos", 0);

                if (total > 0) {
                    for (int i = 0; i < total; i++) {
                        String json = appSharedPrefs.getString("campeonato" + i, "");
                        Campeonato n = gson.fromJson(json, Campeonato.class);
                        todos_os_campeonatos.add(n);
                    }
                }


                //add campeonato no array list e voltar a guardar
                todos_os_campeonatos.add(camp);
                total = todos_os_campeonatos.size();

                SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                prefsEditor.putInt("total_campeonatos", total);


                    gson = new Gson();
                    for (int i = 0; i < total; i++) {
                        String json = gson.toJson(todos_os_campeonatos.get(i));
                        prefsEditor.putString("campeonato" + i, json);
                    }


                prefsEditor.apply();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //fechar todos os fragemnts e voltar a pagina inicial   ????????

                //List<Fragment> all_frags = ((AppCompatActivity)getActivity()).getSupportFragmentManager().getFragments();
                List<Fragment> all_frags = getFragmentManager().getFragments();
                if(all_frags != null){
                    if (all_frags.size() == 0) {
                        getActivity().onBackPressed();
                    } else {
                        for (Fragment frag : all_frags) {
                            getActivity().getSupportFragmentManager().beginTransaction().remove(frag).commit();
                        }
                    }
                }else{
                    getActivity().onBackPressed();
                }


            }
        });


        return v;
    }

    private void GuardarButtonToggle() {
        if (bt_save.getVisibility() != View.VISIBLE) {
            bt_save.setVisibility(View.VISIBLE);
        }
    }

    private void BalancearEquipas() {


        int soma_nivel_red = 0;
        int soma_nivel_azul = 0;

        label1:
        for (Jogador jog : red_team) {
            soma_nivel_red += jog.getPeso();
        }
        for (Jogador jog : blue_team) {
            soma_nivel_azul += jog.getPeso();
        }

        if (soma_nivel_red == soma_nivel_azul) {
            Toast.makeText(getActivity(), "Perfeitamente equilibradas", Toast.LENGTH_SHORT).show();
        } else if (soma_nivel_red > soma_nivel_azul) {
            Toast.makeText(getActivity(), "equipa vermelha mais forte", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "equipa azul mais forte", Toast.LENGTH_SHORT).show();

            try {
                int diff = soma_nivel_azul - soma_nivel_red;
                if (diff >= 2 && (diff % 2) == 0) {
                    label1:
                    for (Jogador jog_blue : blue_team) {
                        for (Jogador jog_red : red_team) {
                            int diff_player = diff / 2;
                            if (jog_blue.getPeso() > jog_red.getPeso() && jog_blue.getPeso() - jog_red.getPeso() == diff_player) {
                                blue_team.remove(jog_blue);
                                red_team.remove(jog_red);
                                blue_team.add(jog_red);
                                red_team.add(jog_blue);
                                ConstrutorDasEquipas(red_team, blue_team);
                                BalancearEquipas();
                                break label1;
                            }
                        }//fim for
                    }//fim for
                }//fim desnivel de 2
                else {
                    //quando é impar, tornar par e fazer aproximação
                    if (diff != 1) {
                        Toast.makeText(getActivity(), "vsdvsvsfvvs", Toast.LENGTH_SHORT).show();
                        label1:
                        for (Jogador jog_blue : blue_team) {
                            for (Jogador jog_red : red_team) {
                                //int diff_player = diff - 1;
                                if (jog_blue.getPeso() > jog_red.getPeso() && (jog_blue.getPeso() - jog_red.getPeso()) > 0) {
                                    blue_team.remove(jog_blue);
                                    red_team.remove(jog_red);
                                    blue_team.add(jog_red);
                                    red_team.add(jog_blue);
                                    ConstrutorDasEquipas(red_team, blue_team);
                                    BalancearEquipas();
                                    break label1;
                                }
                            }//fim for
                        }//fim for
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }//fim else

    }

    //substituir e usar estetstettsttetst
    //usar este
    //usar este
    //usar este
    //usar este
    //usar este
    //usar este
    //usar este
    //usar este
    private void AlogPlayersChange(ArrayList<Jogador> equipa_forte, ArrayList<Jogador> equipa_fraca, int diff) {
        try {
            if (diff >= 2 && (diff % 2) == 0) { //para diferença com numeros par - faz equilibrio
                label1:
                for (Jogador jog_blue : equipa_forte) {
                    for (Jogador jog_red : equipa_fraca) {
                        int diff_player = diff / 2;
                        if (jog_blue.getPeso() > jog_red.getPeso() && jog_blue.getPeso() - jog_red.getPeso() == diff_player) {
                            equipa_forte.remove(jog_blue);
                            equipa_fraca.remove(jog_red);
                            equipa_forte.add(jog_red);
                            equipa_fraca.add(jog_blue);
                            ConstrutorDasEquipas(equipa_fraca, equipa_forte);
                            BalancearEquipas();
                            break label1;
                        }
                    }
                }
            } else {//quando é impar, fazer aproximação e iteração até ficar mais equilibrado possivel
                if (diff != 1) {
                    label1:
                    for (Jogador jog_blue : equipa_forte) {
                        for (Jogador jog_red : equipa_fraca) {
                            //int diff_player = diff - 1;
                            if (jog_blue.getPeso() > jog_red.getPeso() && (jog_blue.getPeso() - jog_red.getPeso()) > 0) {
                                equipa_forte.remove(jog_blue);
                                equipa_fraca.remove(jog_red);
                                equipa_forte.add(jog_red);
                                equipa_fraca.add(jog_blue);
                                ConstrutorDasEquipas(equipa_fraca, equipa_forte);
                                BalancearEquipas();
                                break label1;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void ConstrutorDasEquipas(ArrayList<Jogador> team_red, ArrayList<Jogador> team_blue) {

        String total_red = "";
        String total_blue = "";

        red_team = new ArrayList<>();
        blue_team = new ArrayList<>();

        for (Jogador jog_red : team_red) {
            String aux = jog_red.getNome() + "  nivel: " + jog_red.getPeso() + "\n";
            total_red += aux;
            red_team.add(jog_red);
        }
        for (Jogador jog_blue : team_blue) {
            String aux = jog_blue.getNome() + "  nivel: " + jog_blue.getPeso() + "\n";
            total_blue += aux;
            blue_team.add(jog_blue);
        }

        redteam_list.setText(total_red);
        blueteam_list.setText(total_blue);

    }


    private void ConstrutorDasEquipas() {
        String total_red = "";
        String total_blue = "";
        boolean switcher = true;

        red_team = new ArrayList<>();
        blue_team = new ArrayList<>();

        for (Jogador jog : jogadores) {

            if (switcher) {
                String aux = jog.getNome() + "  nivel: " + jog.getPeso() + "\n";
                total_red += aux;
                red_team.add(jog);
                switcher = false;
            } else {
                String aux = jog.getNome() + "  nivel: " + jog.getPeso() + "\n";
                total_blue += aux;
                blue_team.add(jog);
                switcher = true;
            }

        }

        redteam_list.setText(total_red);
        blueteam_list.setText(total_blue);

    }

}
