package pt.pepedevils.tme.fragaments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import pt.pepedevils.tme.R;
import pt.pepedevils.tme.model.Jogador;
import pt.pepedevils.tme.utils.Helper;

public class NewCampaignFragment extends Fragment {

    //
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    private final String PLUS_ONE_URL = "http://developer.android.com";
    private Button add_player;

    private View v;

    private ArrayList<Jogador> jogadores = new ArrayList<>();


    public NewCampaignFragment() {
        // Required empty public constructor
    }


    public static NewCampaignFragment newInstance() {
        NewCampaignFragment fragment = new NewCampaignFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get jogadores from sharedpreferences
        jogadores = Helper.getListOnSharedPreferences(getActivity());


    }


    private void ButtonsFunctions(final View v) {
        add_player = (Button) v.findViewById(R.id.add_player);
        add_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (jogadores.size() != 10) {


                    int nivel = 0;
                    try {
                        String name = ((AppCompatEditText) (v.findViewById(R.id.edt_player_name))).getText().toString();
                        nivel = Integer.parseInt(((AppCompatEditText) (v.findViewById(R.id.edt_player_weight))).getText().toString());

                        label1:
                        if (name.equalsIgnoreCase("") || nivel == 0) {
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.player_aviso), Toast.LENGTH_SHORT).show();

                        } else {
                            Jogador jg = new Jogador(name, nivel);
                            jogadores.add(jg);
                            Set<Jogador> set = new HashSet<>(jogadores);
                            if(set.size() < jogadores.size()){
                                jogadores.remove(jg);
                                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.player_aviso_allready), Toast.LENGTH_SHORT).show();
                            }else {
                                ActualizarListaJogadores(v);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.player_aviso), Toast.LENGTH_SHORT).show();

                    }


                } else {
                    Toast.makeText(getActivity(), "Limite de Jogadores", Toast.LENGTH_SHORT).show();
                }


            }
        });


        Button sub_team = (Button) v.findViewById(R.id.sub_team);
        sub_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jogadores.size() == 10) {
                    Toast.makeText(getActivity(), "Team Ready", Toast.LENGTH_SHORT).show();
                    //eliminar toast
                    //gravar equipa nos shareds preferences
                    //fechar Fragment

                    Helper.setListOnSharedPreferences(getActivity(),jogadores);

                    Fragment frag = TeamMakerFragment.newInstance();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.container, frag);
                    transaction.addToBackStack(null);
                    transaction.commit();


                } else {
                    int aux = jogadores.size() - 10;
                    aux = aux * (-1);
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.make_ten) + " " + aux, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void ActualizarListaJogadores(View v) {

        if (jogadores.get(0) != null) {
            final ScrollView myRoot = (ScrollView) v.findViewById(R.id.scrollView);
            LimparTodasAsViewsDoScroll(myRoot);

            LinearLayout a = new LinearLayout(getActivity());
            a.setOrientation(LinearLayout.VERTICAL);

            for (int i = 0; i < jogadores.size(); i++) {
                final View row = getActivity().getLayoutInflater().inflate(R.layout.row_player, null);
                Jogador jg = jogadores.get(i);

                int numero = (int) (i + 1);
                TextView num_player = (TextView) row.findViewById(R.id.num_player);
                String aux = "Nº: " + numero;
                num_player.setText(aux);

                TextView name_player = (TextView) row.findViewById(R.id.name_player);
                aux = "Nome: " + jg.getNome();
                name_player.setText(aux);
                name_player.setTag(jg.getNome());

                TextView level_player = (TextView) row.findViewById(R.id.level_player);
                aux = "Nivel: " + jg.getPeso();
                level_player.setText(aux);

                Button bt_eliminar_ = (Button) row.findViewById(R.id.bt_eliminar_);
                bt_eliminar_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ButtonRowDelete(myRoot, row);
                    }
                });


                a.addView(row);
            }


            myRoot.addView(a);
        }else{
            jogadores = new ArrayList<>();
        }


    }

    private void ButtonRowDelete(ScrollView myRoot, View row) {
        for (int i = 0; i < jogadores.size(); i++) {
            Jogador jog = jogadores.get(i);
            String name = (String) ((TextView)(row.findViewById(R.id.name_player))).getTag();
            if(jog.getNome().equalsIgnoreCase(name)){
                jogadores.remove(jog);
                //myRoot.removeView(row.getRootView());
                //myRoot.removeAllViews();

                ((ViewGroup)row.getParent()).removeView(row);
                Toast.makeText(getActivity(), "eliminado", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    private void LimparTodasAsViewsDoScroll(ScrollView myRoot) {
        for (int i = 0; i < myRoot.getChildCount(); i++) {
            myRoot.removeViewAt(i);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_new_campaign, container, false);

        //actualizar view com os jogadores da sharedpreferences
        ActualizarListaJogadores(v);

        ButtonsFunctions(v);


        return v;
    }


}
