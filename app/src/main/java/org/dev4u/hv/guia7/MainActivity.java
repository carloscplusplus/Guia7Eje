package org.dev4u.hv.guia7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import modelo.AdaptadorCategoria;
import modelo.Categoria;
import modelo.DB;
import modelo.Producto;

public class MainActivity extends AppCompatActivity {

    private DB db;
    private AdaptadorCategoria adaptadorCategoria;
    private ListView listView, listViewp;
    private TextView lblId_Cat, lblId_prod;
    private EditText txtNombre_Cat, txtNombre_prod;
    private Button   btnGuardar,btnEliminar, btnAcceder;
    //lista de datos (categoria)
    private ArrayList<Categoria> lstCategoria;
    //lista de datos (productos)
    private ArrayList<Producto> lstProductos;
    //sirve para manejar la eliminacion
    private Categoria categoria_temp=null;
    public static int GUARDADO=47;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializando los controles
        lblId_Cat               = (TextView) findViewById(R.id.lblId_cat_main);
        txtNombre_Cat           = (EditText) findViewById(R.id.txtCategoria);
        lblId_prod              = (TextView) findViewById(R.id.lblId_prod);

        btnGuardar              = (Button)   findViewById(R.id.btnGuardar);
        btnAcceder              = (Button) findViewById(R.id.btnAcceder);
        btnEliminar             = (Button)   findViewById(R.id.btnEliminar);
        listView                = (ListView) findViewById(R.id.lstCategoria);
        listViewp               = (ListView) findViewById(R.id.lstProductos);
        //inicializando lista y db
        db                      = new DB(this);
        lstCategoria            = db.getArrayCategoria(
                                    db.getCursorCategoria()
                                  );
        if(lstCategoria==null)//si no hay datos
            lstCategoria = new ArrayList<>();
        adaptadorCategoria      = new AdaptadorCategoria(this,lstCategoria);
        listView.setAdapter(adaptadorCategoria);
        //listeners
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seleccionar(lstCategoria.get(position));
            }
        });
        //seteando el adaptador al listview
        listViewp.setAdapter(adaptadorCategoria);
        btnAcceder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //preparo el activity
                Intent intent = new Intent(MainActivity.this, ListaProducto.class);
                intent.putExtra("parametro", categoria_temp.getId_categoria());
                //inicio el activitiy y quiero como mensaje un RESULT_OK
                startActivityForResult(intent,GUARDADO);
            }

        });
        //limpiando
        limpiar();
    }
    private void guardar(){
        Categoria categoria = new Categoria(lblId_Cat.getText().toString(),txtNombre_Cat.getText().toString());
        categoria_temp=null;
        if(db.guardar_O_ActualizarCategoria(categoria)){
            Toast.makeText(this,"Se guardo categoria",Toast.LENGTH_SHORT).show();
            //TODO limpiar los que existen y agregar los nuevos
            lstCategoria.clear();
            lstCategoria.addAll(db.getArrayCategoria(
                    db.getCursorCategoria()
            ));

            adaptadorCategoria.notifyDataSetChanged();
            limpiar();
        }else{
            Toast.makeText(this,"Ocurrio un error al guardar",Toast.LENGTH_SHORT).show();
        }
    }
    private void eliminar(){
        if(categoria_temp!=null){
            db.borrarCategoria(categoria_temp.getId_categoria());
            lstCategoria.remove(categoria_temp);
            adaptadorCategoria.notifyDataSetChanged();
            categoria_temp=null;
            Toast.makeText(this,"Se elimino categoria",Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }
    private void seleccionar(Categoria categoria){
        categoria_temp = categoria;
        lblId_Cat.setText(categoria_temp.getId_categoria());
        txtNombre_Cat.setText(categoria_temp.getNombre());
    }
    private void limpiar(){
        lblId_Cat.setText(null);
        txtNombre_Cat.setText(null);

    }
}
