package pt.pepedevils.tme.fragaments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pt.pepedevils.tme.R;
import pt.pepedevils.tme.adapters.AdaptersJogos;
import pt.pepedevils.tme.model.Campeonato;
import pt.pepedevils.tme.model.Jogos;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampaignFragment extends Fragment implements View.OnClickListener {

    private View v;
    private List<Jogos> jogos = new ArrayList<>();
    private Campeonato campeonato_escolhido;
    private AdaptersJogos adptrjogos;


    public CampaignFragment() {
        // Required empty public constructor
    }

    public static CampaignFragment newInstance() {
        CampaignFragment fragment = new CampaignFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_campaign, container, false);

        final ListView listview = (ListView) v.findViewById(R.id.listview);

        Button bt_ganhou = (Button) v.findViewById(R.id.bt_ganhou);
        Button bt_empatou = (Button) v.findViewById(R.id.bt_empatou);
        Button bt_perdeu = (Button) v.findViewById(R.id.bt_perdeu);
        Button bt_limpar = (Button) v.findViewById(R.id.bt_limpar);
        Button bt_equilibrar = (Button) v.findViewById(R.id.bt_equilibrar);
        bt_ganhou.setOnClickListener(this);
        bt_empatou.setOnClickListener(this);
        bt_perdeu.setOnClickListener(this);
        bt_limpar.setOnClickListener(this);
        bt_equilibrar.setOnClickListener(this);


        //get Campeonatos
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Gson gson = new Gson();
        final ArrayList<Campeonato> todos_os_campeonatos = new ArrayList<>();
        int total = sp.getInt("total_campeonatos", 0);

        if (total > 0) {
            for (int i = 0; i < total; i++) {
                String json = sp.getString("campeonato" + i, "");
                Campeonato n = gson.fromJson(json, Campeonato.class);
                todos_os_campeonatos.add(n);
            }
        }

        List<String> stringdates = new ArrayList<>();

        for (Campeonato c : todos_os_campeonatos) {
            stringdates.add(c.getDataCriacao().toString());
        }

        //fazer um custom aadapter para campeonatos
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringdates);

        listview.setAdapter(arrayAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "item" + i, Toast.LENGTH_SHORT).show();

                campeonato_escolhido = todos_os_campeonatos.get(i);

                RelativeLayout relativelayout = (RelativeLayout) v.findViewById(R.id.relativelayout);
                relativelayout.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
                ((TextView)v.findViewById(R.id.title_escolha)).setText("");


                ListView listview2 = (ListView) v.findViewById(R.id.listview2);
                Jogos j = new Jogos("Jogo Nº" + jogos.size()+1,new Date(),campeonato_escolhido,campeonato_escolhido.getVermelha(), campeonato_escolhido.getAzul());
                jogos.add(j);
                jogos.add(j);
                jogos.add(j);
                jogos.add(j);
                jogos.add(j);

                adptrjogos = new AdaptersJogos(getActivity(),i,jogos);

                listview2.setAdapter(adptrjogos);

            }
        });

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_limpar:
                jogos.clear();
                adptrjogos.notifyDataSetChanged();
                break;

            case R.id.bt_equilibrar:

                break;

            case R.id.bt_ganhou:
                int number =  jogos.size()+1;
                Jogos j = new Jogos("Jogo Nº" + number,new Date(),campeonato_escolhido,campeonato_escolhido.getVermelha(), campeonato_escolhido.getAzul());
                jogos.add(j);
                adptrjogos.notifyDataSetChanged();
                break;

            case R.id.bt_empatou:

                break;

            case R.id.bt_perdeu:

                break;

            default:
                break;
        }
    }
}
