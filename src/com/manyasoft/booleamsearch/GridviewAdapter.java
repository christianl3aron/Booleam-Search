package com.manyasoft.booleamsearch;

import java.util.ArrayList;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class GridviewAdapter extends BaseAdapter
{
    private ArrayList<Publicacion> listPublicacion;
    private Context activity;
 
    public GridviewAdapter(Context activity,ArrayList<Publicacion> listProducto) {
        super();
        this.listPublicacion = listProducto;
        this.activity = activity;
    }
 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listPublicacion.size();
    }
 
    @Override
    public Publicacion getItem(int position) {
        // TODO Auto-generated method stub
        return listPublicacion.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
 
    public static class ViewHolder
    {
        public ImageView img_publicacion;
        public TextView txt_titulo;
        public TextView txt_autor;
        public RatingBar rating;
        
        public static ViewHolder generate(View convertView) {
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.img_publicacion=(ImageView) convertView.findViewById(R.id.img_publicacion_item);
			viewHolder.txt_titulo=(TextView) convertView.findViewById(R.id.txt_titulo);
			viewHolder.txt_autor=(TextView) convertView.findViewById(R.id.txt_autor);
			viewHolder.rating=(RatingBar) convertView.findViewById(R.id.bar_rating_item);
			
			return viewHolder;
		}
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    	ViewHolder view;
        if(convertView==null){
            view = new ViewHolder();
            convertView = View.inflate(activity,R.layout.item_gridview, null);
            view=ViewHolder.generate(convertView);
            convertView.setTag(view);
        }else{
            view = (ViewHolder) convertView.getTag();
        }
        view.txt_titulo.setText(String.valueOf(position+1)+".  "+listPublicacion.get(position).getTiulo());
        view.txt_autor.setText(listPublicacion.get(position).getAutor());
        view.rating.setRating(Float.valueOf(listPublicacion.get(position).getRating()));
        view.img_publicacion.setImageResource(activity.getResources().getIdentifier("drawable/" + listPublicacion.get(position).getImagen(), null, activity.getPackageName()));
 
        return convertView;
    }
}