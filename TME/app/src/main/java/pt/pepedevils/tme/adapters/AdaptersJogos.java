package pt.pepedevils.tme.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pt.pepedevils.tme.R;
import pt.pepedevils.tme.model.Jogador;
import pt.pepedevils.tme.model.Jogos;

/**
 * Created by dezvezesdez on 26/05/2017.
 */

public class AdaptersJogos extends ArrayAdapter<Jogos>{

    public AdaptersJogos(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public AdaptersJogos(Context context, int resource, List<Jogos> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemlistjogosrow, null);
        }

        Jogos p = getItem(position);

        if (p != null) {

            TextView nome = (TextView) v.findViewById(R.id.nome);
            TextView data = (TextView) v.findViewById(R.id.data);

            TextView redteam_list = (TextView) v.findViewById(R.id.redteam_list);
            TextView blueteam_list = (TextView) v.findViewById(R.id.blueteam_list);

            nome.setText(p.getNome());
            data.setText(p.getData().toString());

            redteam_list.setText(p.getEquipa_vermelha().toString());
            blueteam_list.setText(p.getEquipa_azul().toString());

        }

        return v;
    }
}
