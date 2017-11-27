package modelo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.dev4u.hv.guia7.R;

import java.util.List;

/**
 * Created by Carlos on 18/11/2017.
 */

public class AdaptadorProducto extends ArrayAdapter<Producto> {

    public AdaptadorProducto(Context context, List<Producto> objects) {
        super(context, 0, objects);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obteniendo el dato
        Producto contacto = getItem(position);
        //TODO inicializando el layout correspondiente al item (Producto)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }
        TextView lblNombre = (TextView) convertView.findViewById(R.id.lblNombre_prod);
        TextView lblId_prod = (TextView) convertView.findViewById(R.id.lblId_prod);
       // TextView lblDescripcion = (TextView) convertView.findViewById(R.id.);
        // mostrar los datos
        lblNombre.setText(contacto.getNombre());
        lblId_prod.setText(contacto.getId_producto());
        // Return la convertView ya con los datos
        return convertView;
    }
}
