package com.manyasoft.booleamsearch;

import java.util.ArrayList;






import android.app.Dialog;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainFragment extends Fragment{

	View caRootView;
	TabHost tabhost;
	AdminSQLiteOpenHelper admin_ca;
	GridView gridView;
	AutoCompleteTextView atv_text;
	EditText txtpalabra1;
	EditText txtpalabra2;
	EditText txtpalabra3;
	Spinner spnoperador2;
	Spinner spnoperador3;
	Spinner spntag1;
	Spinner spntag2;
	Spinner spntag3;
	Spinner spntag4;
	Button btnsearch;
	String tag1;
	String tag2;
	String tag3;
	int op2;
	int op3;
	int op4;
	
	private Tools tools;
	private GridviewAdapter mAdapter;
    private ArrayList<Publicacion> arrayPublicacion = new ArrayList<Publicacion>();
    public ArrayList<Publicacion> arrayPublicacion_Copy = new ArrayList<Publicacion>();	
    private ArrayList<String> arrayAllTables = new ArrayList<String>();
    private ArrayList<String> arrayValidTables = new ArrayList<String>();
    private ArrayList<String> arrayValidTablesword1 = new ArrayList<String>();
    private ArrayList<String> arrayValidTablesword2 = new ArrayList<String>();
    private ArrayList<String> arrayValidTablesword3 = new ArrayList<String>();
    
    

	public MainFragment() {}
    @Override
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        caRootView = inflater.inflate(R.layout.fragment_main, container, false);
        admin_ca = new AdminSQLiteOpenHelper(getActivity(),"administracion", null, 1);
        tools = new Tools(getActivity().getApplicationContext());
        
        tabhost=(TabHost) caRootView.findViewById(R.id.tabhost);
        tabhost.setup();
        TabSpec spec=tabhost.newTabSpec("tab1");
        spec.setIndicator("PUBLICACIONES");
        spec.setContent(R.id.tab1);
        tabhost.addTab(spec);
        
        TabSpec spac=tabhost.newTabSpec("tab2");
        spac.setIndicator("otro tab");
        spac.setContent(R.id.tab2);
        tabhost.addTab(spac);        

        inicializarArrayproductoList();
        inicializarWidgets();
        showAdapterOnGrid(arrayPublicacion);   
        getAllTables();
        
		return caRootView;
    }//finish          ONCREATE()
    
    public void getAllTables(){
    	try {
        	Cursor filas = admin_ca.getSelect("select * from indtables");
        	if(filas.moveToFirst()){
            	 do {
            	        arrayAllTables.add(filas.getString(0));
                 } while (filas.moveToNext());    
        	}		
		} catch (Exception e) {
			tools.showLongMessage("error obetener tablas! "+e.getMessage());
		} 
    }
    
    public void inicializarArrayproductoList(){
    	arrayPublicacion.clear();
        try {
	        	Cursor filas = admin_ca.getSelect("select * from publicacion");
	        	if(filas.moveToFirst()){
	            	 do {
	            		 Publicacion pro = new Publicacion(); 
	            		 	pro.setTiulo(filas.getString(0));
	            		 	pro.setAutor(filas.getString(2));
	            	    	pro.setResumen(filas.getString(1));
	            	    	pro.setRating(filas.getString(4));
	            	    	pro.setImagen(filas.getString(3));
	            	        arrayPublicacion.add(pro);
	                 } while (filas.moveToNext());    
	        	}		
		} catch (Exception e) {
			e.printStackTrace();
			tools.showLongMessage("error select Publicaciones! "+e.getMessage());
		} 
    }
    
    public void inicializarArrayPublicacionListSecondary(ArrayList<String> filteredTables){
    	arrayPublicacion_Copy.clear();
        try {
	        	for (int i=0;i<filteredTables.size();i++){
					Cursor filas = admin_ca.getSelect("select * from publicacion where rutaimg='"+filteredTables.get(i).toString()+"'");
					if (filas.moveToFirst()) {
						do {
							Publicacion pro = new Publicacion();
							pro.setTiulo(filas.getString(0));
							pro.setAutor(filas.getString(2));
							pro.setResumen(filas.getString(1));
							pro.setRating(filas.getString(4));
							pro.setImagen(filas.getString(3));
							arrayPublicacion_Copy.add(pro);
						} while (filas.moveToNext());
					}
				}	
	        	showAdapterOnGrid(arrayPublicacion_Copy);
		} catch (Exception e) {
			tools.showLongMessage("error select Publicaciones! "+e.getMessage());
		} 
    }
    
    public void showAdapterOnGrid(final ArrayList<Publicacion> arrayList){
    	gridView = (GridView) caRootView.findViewById(R.id.gv_all_ca);
    	
    	if (arrayList.size()!=0) {
			mAdapter = new GridviewAdapter(getActivity(), arrayList);
			gridView.setAdapter(mAdapter);
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					try {
						buidRegisterDialog(mAdapter.getItem(position));
					} catch (Exception e) {
						tools.showShortMessage("no salio" + e.getMessage());
					}
				}
			});
		}
    }
    
    private void buidRegisterDialog(final Publicacion publicacion_dial){
    	
    	final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.dialog_registerpro);
		dialog.setTitle(publicacion_dial.getTiulo());
		dialog.setCancelable(true);
		
		//Button btncancel_dialog=(Button) dialog.findViewById(R.id.btn_gotopedido_dialog);
		
		ImageView img_producto_dia =(ImageView) dialog.findViewById(R.id.img_producto_dialog);
		img_producto_dia.setImageResource(getResources().getIdentifier("drawable/" + publicacion_dial.getImagen(), null, getActivity().getPackageName()));
		 
		TextView txt_nom_dia=(TextView) dialog.findViewById(R.id.txt_nomproducto_dialog);
		txt_nom_dia.setText(publicacion_dial.getTiulo());
		
		TextView txt_cod_dia=(TextView) dialog.findViewById(R.id.txt_codigo_dialog);
		txt_cod_dia.setText(publicacion_dial.getAutor());
		
		EditText txt_desc_dia = (EditText) dialog.findViewById(R.id.txt_desc_dialog);
		txt_desc_dia.setText(publicacion_dial.getResumen());
			
		dialog.show();
    }
    
    public void setedit2(boolean valor){
    	txtpalabra2.setEnabled(valor);
		spnoperador2.setEnabled(valor);
		spntag2.setEnabled(valor);
    }
    
    public void setedit3(boolean valor){
    	txtpalabra3.setEnabled(valor);
		spnoperador3.setEnabled(valor);
		spntag3.setEnabled(valor);
    }
    
    
    public void seteditB(boolean valor){
    	btnsearch.setEnabled(valor);
    }
    
    public void inicializarWidgets(){
    	
    	txtpalabra1=(EditText) caRootView.findViewById(R.id.txtpalabra1);
        txtpalabra1.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable arg0) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {
				if (!txtpalabra1.getText().toString().equals("")) {
					setedit2(true);
					seteditB(true);
				}else{
					setedit2(false);
					seteditB(false);
				}
			}});
        txtpalabra2=(EditText) caRootView.findViewById(R.id.txtpalabra2);
        txtpalabra2.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable arg0) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {
				if (!txtpalabra2.getText().toString().equals("")) {
			        setedit3(true);
				}else{
			        setedit3(false);
				}
			}});
        txtpalabra3=(EditText) caRootView.findViewById(R.id.txtpalabra3);
        spnoperador2=(Spinner) caRootView.findViewById(R.id.spnoperador2);
        spnoperador3=(Spinner) caRootView.findViewById(R.id.spnoperador3);
        spntag1=(Spinner) caRootView.findViewById(R.id.spntag1);
        spntag2=(Spinner) caRootView.findViewById(R.id.spntag2);
        spntag3=(Spinner) caRootView.findViewById(R.id.spntag3);
        
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.array_spnoperadores, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnoperador2.setAdapter(adapter);
		spnoperador2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
				if(position==0) {op2=0;}//"y";
				else if(position==1) {op2=1;}//"o";
				else if(position==2) {op2=2;}//"not";
				}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}});
		spnoperador3.setAdapter(adapter);
		spnoperador3.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
				if(position==0) {op3=0;}//"y";
				else if(position==1) {op3=1;}//"o";
				else if(position==2) {op3=2;}//"not";
				}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}});
		
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.array_spntag, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spntag1.setAdapter(adapter2);
		spntag1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
				if(position==0) {tag1="T";}//"titulo";
				else if(position==1) {tag1="R";}//"resumen";
				else if(position==2) {tag1="A";}//"autor";
				}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}});
		spntag2.setAdapter(adapter2);
		spntag2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
				if(position==0) {tag2="T";}//"titulo";
				else if(position==1) {tag2="R";}//"resumen";
				else if(position==2) {tag2="A";}//"autor";
				}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}});
		spntag3.setAdapter(adapter2);
		spntag3.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
				if(position==0) {tag3="T";}//"titulo";
				else if(position==1) {tag3="R";}//"resumen";
				else if(position==2) {tag3="A";}//"autor";
				}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}});
		btnsearch=(Button) caRootView.findViewById(R.id.btnsearch);
		btnsearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				buscar();
			}});
		
		setedit2(false);
        setedit3(false);
        seteditB(false);
    }
    
    
    public void buscar(){
    	arrayValidTables.clear();
    	arrayValidTablesword1.clear();
    	arrayValidTablesword2.clear();
    	arrayValidTablesword3.clear();
    	//solo un edittext lleno
    	if (!txtpalabra1.getText().toString().equals("") && txtpalabra2.getText().toString().equals("") && txtpalabra3.getText().toString().equals("")) {
    		arrayValidTables=searchWord(txtpalabra1.getText().toString().trim().toLowerCase(),tag1);
    		if(arrayValidTables.size()!=0) inicializarArrayPublicacionListSecondary(arrayValidTables);
    		else tools.showLongMessage("No encontrado");
    		
    	}else if (!txtpalabra1.getText().toString().equals("") && !txtpalabra2.getText().toString().equals("") && txtpalabra3.getText().toString().equals("")) {
    		arrayValidTablesword1=searchWord(txtpalabra1.getText().toString().trim().toLowerCase(),tag1);
    		arrayValidTablesword2=searchWord(txtpalabra2.getText().toString().trim().toLowerCase(),tag2);
    		switch(op2){
    		case 0:{arrayValidTables=operadorAND(arrayValidTablesword1,arrayValidTablesword2);}break;
    		case 1:{arrayValidTables=operadorOR(arrayValidTablesword1,arrayValidTablesword2);}break;
    		case 2:{arrayValidTables=operadorNOT(arrayValidTablesword1,arrayValidTablesword2);}break;
    		}
    		
    		if(arrayValidTables.size()!=0) inicializarArrayPublicacionListSecondary(arrayValidTables);
    		else tools.showLongMessage("No encontrado");
    	}else if (!txtpalabra1.getText().toString().equals("") && !txtpalabra2.getText().toString().equals("") && !txtpalabra3.getText().toString().equals("")) {
    		arrayValidTablesword1=searchWord(txtpalabra1.getText().toString().trim().toLowerCase(),tag1);
    		arrayValidTablesword2=searchWord(txtpalabra2.getText().toString().trim().toLowerCase(),tag2);
    		arrayValidTablesword3=searchWord(txtpalabra3.getText().toString().trim().toLowerCase(),tag3);
    		if(op2==0 && op3==0){ arrayValidTables=operadorAND(operadorAND(arrayValidTablesword1,arrayValidTablesword2),arrayValidTablesword3);
    		}else if(op2==0 && op3==1){ arrayValidTables=operadorOR(operadorAND(arrayValidTablesword1,arrayValidTablesword2),arrayValidTablesword3);
    		}else if(op2==0 && op3==2){ arrayValidTables=operadorNOT(operadorAND(arrayValidTablesword1,arrayValidTablesword2),arrayValidTablesword3);
    		}else if(op2==1 && op3==0){ arrayValidTables=operadorAND(operadorOR(arrayValidTablesword1,arrayValidTablesword2),arrayValidTablesword3);
    		}else if(op2==1 && op3==1){ arrayValidTables=operadorOR(operadorOR(arrayValidTablesword1,arrayValidTablesword2),arrayValidTablesword3);
    		}else if(op2==1 && op3==2){ arrayValidTables=operadorNOT(operadorOR(arrayValidTablesword1,arrayValidTablesword2),arrayValidTablesword3);
    		}else if(op2==2 && op3==0){ arrayValidTables=operadorAND(operadorNOT(arrayValidTablesword1,arrayValidTablesword2),arrayValidTablesword3);
    		}else if(op2==2 && op3==1){ arrayValidTables=operadorOR(operadorNOT(arrayValidTablesword1,arrayValidTablesword2),arrayValidTablesword3);
    		}else if(op2==2 && op3==2){ arrayValidTables=operadorNOT(operadorNOT(arrayValidTablesword1,arrayValidTablesword2),arrayValidTablesword3);
    		}
    		if(arrayValidTables.size()!=0) inicializarArrayPublicacionListSecondary(arrayValidTables);
    		else tools.showLongMessage("No encontrado");
    	}
    }
    	
    	
    	
    
    
    public ArrayList<String> searchWord(String word,String tag){
    	ArrayList<String> arrayTempValidTables = new ArrayList<String>();
    	for(int x=0;x<arrayAllTables.size();x++){
			if(admin_ca.checkTabla("select count(*) from "+arrayAllTables.get(x).toString()+" where palabra='"+word+"' and tipo='"+tag+"'")){
				arrayTempValidTables.add(arrayAllTables.get(x).toString());
			}
		}
    	return arrayTempValidTables;
    }
    
    public ArrayList<String> operadorAND(ArrayList<String> tables1, ArrayList<String> tables2){
    	ArrayList<String> arrayTempValidTables = new ArrayList<String>();
    	for(int x=0;x<tables2.size();x++){
    		if(tables1.contains(tables2.get(x).toString())){
    			arrayTempValidTables.add(tables2.get(x).toString());
    		}
		}
    	return arrayTempValidTables;
    }
    
    public ArrayList<String> operadorOR(ArrayList<String> tables1, ArrayList<String> tables2){
    	ArrayList<String> arrayTempValidTables = tables1;
    	for(int x=0;x<tables2.size();x++){
    		if(!arrayTempValidTables.contains(tables2.get(x).toString())){
    			arrayTempValidTables.add(tables2.get(x).toString());
    		}
		}
    	return arrayTempValidTables;
    }
    
    public ArrayList<String> operadorNOT(ArrayList<String> tables1, ArrayList<String> tables2){
    	ArrayList<String> arrayTempValidTables = new ArrayList<String>();
    	for(int x=0;x<tables1.size();x++){
    		if(!tables2.contains(tables1.get(x).toString())){
    			arrayTempValidTables.add(tables1.get(x).toString());
    		}
		}
    	return arrayTempValidTables;
    }
}
