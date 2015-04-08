package com.manyasoft.booleamsearch;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
	
	public AdminSQLiteOpenHelper(Context context, String nombre, CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table publicacion(titulo text,resumen text, autor text, rutaimg text,rating text)");
        db.execSQL("create table paomitidas(palabra text)");
        db.execSQL("create table indtables(nomtab text)");
        
        
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists publicacion");
        db.execSQL("drop table if exists paomitidas");
        db.execSQL("drop table if exists indtables");
        dropTableOnUpgrade();
       
        db.execSQL("create table publicacion(titulo text,resumen text, autor text,  rutaimg text,rating text)");
        db.execSQL("create table paomitidas(palabra text)");
        db.execSQL("create table indtables(nomtab text)");
        createTableOnUpgrade();
    }
    
    
    public Cursor getSelect(String query){
    	SQLiteDatabase bd =this.getWritableDatabase();
    	Cursor filas = bd.rawQuery(query, null);
    	return filas;
    }
    
    public int checkTablaPublications(){
    	SQLiteDatabase bd = this.getWritableDatabase();
    	Cursor mCount= bd.rawQuery("select count(*) from indtables", null);
    	mCount.moveToFirst();
    	int count= Integer.parseInt(mCount.getString(0));
    	return count;
    }
    
    public boolean checkTabla(String query){
    	SQLiteDatabase bd = this.getWritableDatabase();
    	Cursor mCount= bd.rawQuery(query, null);
    	mCount.moveToFirst();
    	int count= Integer.parseInt(mCount.getString(0));
    	if(count>0){
    		return true;//hay datos
    	}
    	return false;
    }
    	
    public void createTable(String nomTable,String autor,String titulo, String resumen){
    	SQLiteDatabase bd = this.getWritableDatabase();
    	bd.execSQL("INSERT INTO indtables VALUES ('"+nomTable+"')");
    	bd.execSQL("create table "+nomTable+" (palabra text,tipo text)");
    
    	autor=autor.toLowerCase().replace(",", "").replace(":", "").replace(".", "").replace(";", "").replace("_", "").replace("-", "").replace("\"", "").replace("\'", "");
    	titulo=titulo.toLowerCase().replace(",", "").replace(":", "").replace(".", "").replace(";", "").replace("_", "").replace("-", "").replace("\"", "").replace("\'", "");
    	resumen=resumen.toLowerCase().replace(",", "").replace(":", "").replace(".", "").replace(";", "").replace("_", "").replace("-", "").replace("\"", "").replace("¡", "").replace("!", "").replace("\'", "").replace("“", "").replace("«", "").replace("»", "").replace("”","");
    	
    	Cursor filas = bd.rawQuery("select * from paomitidas", null); 
    	if(filas.moveToFirst()){
    				do {
    					autor=autor.replace(" "+filas.getString(0).toString()+" ", " ");
    				} while (filas.moveToNext());
    	}
    	String[] arrayAutor=autor.trim().split("\\s+");
    	for(int i=0;i<arrayAutor.length;i++){
    		Cursor fa = bd.rawQuery("select * from "+nomTable+" where palabra='"+arrayAutor[i]+"' and tipo='A'", null); 
  	    	if(fa.moveToFirst()){
  	    	}else{
  	    		bd.execSQL("INSERT INTO "+nomTable+" VALUES ('"+arrayAutor[i]+"','A')");
  	    	}
		}
    	
    	Cursor filas2 = bd.rawQuery("select * from paomitidas", null); 
    	if(filas2.moveToFirst()){
    				do {
    					titulo=titulo.replace(" "+filas2.getString(0).toString()+" ", " ");
    				} while (filas2.moveToNext());
    	}
    	String[] arrayTitulo=titulo.trim().split("\\s+");
    	for(int i=0;i<arrayTitulo.length;i++){
    		Cursor ft = bd.rawQuery("select * from "+nomTable+" where palabra='"+arrayTitulo[i]+"' and tipo='T'", null); 
  	    	if(ft.moveToFirst()){
  	    	}else{
  	    		bd.execSQL("INSERT INTO "+nomTable+" VALUES ('"+arrayTitulo[i]+"','T')");
  	    	}
    	}
    
    	Cursor filas3 = bd.rawQuery("select * from paomitidas", null); 
    	if(filas3.moveToFirst()){
    				do{
    					resumen=resumen.replace(" "+filas3.getString(0).toString()+" ", " ");
    				} while (filas3.moveToNext()); 
    	}
    	String[] arrayResumen=resumen.trim().split("\\s+");
    	for(int i=0;i<arrayResumen.length;i++){
    		Cursor fr = bd.rawQuery("select * from "+nomTable+" where palabra='"+arrayResumen[i]+"' and tipo='R'", null); 
  	    	if(fr.moveToFirst()){
  	    	}else{
  	    	  	bd.execSQL("INSERT INTO "+nomTable+" VALUES ('"+arrayResumen[i]+"','R')");
  	    	}
		}
    }
    
    public void dropTableOnUpgrade(){
    	SQLiteDatabase bd = this.getWritableDatabase();
    	Cursor filas = bd.rawQuery("select * from indtables", null); 
    	if(filas.moveToFirst()){
    		do {
    			bd.execSQL("drop table if exists "+filas.getString(0).toString());
            } while (filas.moveToNext());    
    	}
    }
    
    public void createTableOnUpgrade(){
    	SQLiteDatabase bd = this.getWritableDatabase();
    	Cursor filas = bd.rawQuery("select * from indtables", null); 
    	if(filas.moveToFirst()){
    		do {
    			bd.execSQL("create table "+filas.getString(0).toString()+" (word text,tag text)");
            } while (filas.moveToNext()); 
    	}
    }
    
    public void insertPublications(){
    	
    	SQLiteDatabase bd = this.getWritableDatabase();
    	bd.execSQL("INSERT INTO paomitidas VALUES ('a')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('ac')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('ahi')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('ahí')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('al')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('alli')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('asi')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('así')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('como')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('con')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('dc')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('de')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('del')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('desde')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('e')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('el')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('él')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('ella')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('ellos')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('en')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('es')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('ese')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('esta')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('este')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('estos')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('fue')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('ha')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('hacia')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('hasta')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('i')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('la')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('las')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('le')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('lo')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('los')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('más')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('mas')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('mucho')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('muchos')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('no')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('nos')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('o')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('para')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('pero')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('por')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('pues')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('que')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('qué')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('se')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('sido')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('sin')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('sino')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('son')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('su')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('sus')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('suyo')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('tenia')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('u')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('un')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('una')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('unos')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('va')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('y')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('ya')");

    	bd.execSQL("INSERT INTO publicacion VALUES ('Alicia en el País de las Maravillas','El sorprendente viaje de la joven Alicia se desenvuelve entre misterios incomprensibles, adivinanzas absurdas e historias interminables. En un paisaje donde todo es enorme o diminuto, cientos de personajes variopintos y alocados juegan al cricket o toman el té durante días y días. Es el país donde el tiempo se estira y encoge, donde Alicia crece y se hace diminuta, donde un conejo siempre llega tarde y donde una reina loca no hace sino gritar: !Qué le corten la cabeza!','Lewis Carroll','aliciapaismaravillas','4')");
    	createTable("aliciapaismaravillas","Lewis Carroll","Alicia en el País de las Maravillas"," El sorprendente viaje de la joven Alicia se desenvuelve entre misterios incomprensibles, adivinanzas absurdas e historias interminables. En un paisaje donde todo es enorme o diminuto, cientos de personajes variopintos y alocados juegan al cricket o toman el té durante días y días. Es el país donde el tiempo se estira y encoge, donde Alicia crece y se hace diminuta, donde un conejo siempre llega tarde y donde una reina loca no hace sino gritar: !Qué le corten la cabeza!");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('El Mago De Oz','Doroty vive en una granja de Kansas con sus tíos. De pronto se levanta un ciclón que arranca a su casa con ella oculta en su interior, y la lleva por el aire hasta el increíble país de Oz. Desde ese momento, Doroty trata de encontrar un camino de vuelta a Kansas, pero el único que puede ayudarla es el Mago de Oz. Alli comienza una travesía en busca del mago y de superar las pruebas que le imponen. A lo largo del camino se encuentra con extraños amigos que la siguen pues también quieren participar en los beneficios de conocer al Gran Mago. Ellos son el Espantapájaros, el Leñador de Hojalata y el León Cobarde','Lyman Frank Baum','magodeoz','5')");
    	createTable("magodeoz","Lyman Frank Baum"," El Mago De Oz","Doroty vive en una granja de Kansas con sus tíos. De pronto se levanta un ciclón que arranca a su casa con ella oculta en su interior, y la lleva por el aire hasta el increíble país de Oz. Desde ese momento, Doroty trata de encontrar un camino de vuelta a Kansas, pero el único que puede ayudarla es el Mago de Oz. Alli comienza una travesía en busca del mago y de superar las pruebas que le imponen. A lo largo del camino se encuentra con extraños amigos que la siguen pues también quieren participar en los beneficios de conocer al Gran Mago. Ellos son el Espantapájaros, el Leñador de Hojalata y el León Cobarde");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Cantar de Mio Cid','Mío Cid, es un personaje histórico real, su nombre es Rodrigo Díaz de Vivar y nació hacia el año 1043. Educado en la corte del rey Fernando I, mantuvo una gran amistad con su hijo Sancho II. Alfonso VI, hermano de Sancho y sucesor suyo en el trono no fue amigo del Cid, procurando pues el destierro de este, y ahí comienza a revelarse el marco escénico donde ocurre primeramente esta historia convertida en leyenda épica. Fue escrito alrededor del 1140 d.C, 40 años después de la muerte del Cid. El poema se divide en tres partes: primero- destierro; segundo-conquista de Valencia y matrimonio de hijas; tercero-Venganza y justicia de Mío Cid.','Anonimo','cantardemiocid','5')");
    	createTable("cantardemiocid","Anonimo","Cantar de Mio Cid","Mío Cid, es un personaje histórico real, su nombre es Rodrigo Díaz de Vivar y nació hacia el año 1043. Educado en la corte del rey Fernando I, mantuvo una gran amistad con su hijo Sancho II. Alfonso VI, hermano de Sancho y sucesor suyo en el trono no fue amigo del Cid, procurando pues el destierro de este, y ahí comienza a revelarse el marco escénico donde ocurre primeramente esta historia convertida en leyenda épica. Fue escrito alrededor del 1140 d.C, 40 años después de la muerte del Cid. El poema se divide en tres partes: primero- destierro; segundo-conquista de Valencia y matrimonio de hijas; tercero-Venganza y justicia de Mío Cid.");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Lazarillo de Tormes','Lazarillo es un niño nacido en el rio Tormes que a una temprana edad es entregado a un amo ciego. Este amo lo acaba avandonando y lazaro debe ir por las calles de amo en amo y arreglandoselas con travesuras y engaños para comer y salir adelante. A medida que se va haciendo mayor, va perdienmdo la inocencia que tenia','Anonimo','ellazarillodetormes','4')");
    	createTable("ellazarillodetormes","Anonimo","Lazarillo de Tormes","Lazarillo es un niño nacido en el rio Tormes que a una temprana edad es entregado a un amo ciego. Este amo lo acaba avandonando y lazaro debe ir por las calles de amo en amo y arreglandoselas con travesuras y engaños para comer y salir adelante. A medida que se va haciendo mayor, va perdienmdo la inocencia que tenia");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Iliada','Unos pocos días antes del último de los diez años que duró el asedio de los aqueos a la ciudad de Troya, proporcionan el marco cronológico a los acontecimientos narrados en la Ilíada, el poema más antiguo de la literatura occidental. Producto de una larga tradición oral, la epopeya, como advierte su autor en el primer verso , relata la historia de las consecuencias de una pasión humana. Aquiles, encolerizado por el ultraje de Agamenón, que como caudillo de la expedición griega le ha arrebatado a Briseida, su parte del botín, decide retirarse del combate. Pero no tardará mucho en volver a él, con furia renovada, a raíz de la muerte de su compañero Patroclo a manos de los troyanos. Alrededor de estos hechos, la Ilíada nos presenta un mundo de ideales heroicos y hazañas guerreras, en el que los dioses poseen un protagonismo y una presencia permanentes','Homero','lailiada','5')");
    	createTable("lailiada","Homero","Iliada"," Unos pocos días antes del último de los diez años que duró el asedio de los aqueos a la ciudad de Troya, proporcionan el marco cronológico a los acontecimientos narrados en la Ilíada, el poema más antiguo de la literatura occidental. Producto de una larga tradición oral, la epopeya, como advierte su autor en el primer verso , relata la historia de las consecuencias de una pasión humana. Aquiles, encolerizado por el ultraje de Agamenón, que como caudillo de la expedición griega le ha arrebatado a Briseida, su parte del botín, decide retirarse del combate. Pero no tardará mucho en volver a él, con furia renovada, a raíz de la muerte de su compañero Patroclo a manos de los troyanos. Alrededor de estos hechos, la Ilíada nos presenta un mundo de ideales heroicos y hazañas guerreras, en el que los dioses poseen un protagonismo y una presencia permanentes");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Odisea','Junto con la “Ilíada”, la “Odisea” constituye una de las piedras angulares de la cultura occidental. El relato que hace Homero de las aventuras de Odiseo en su camino de regreso desde Troya hasta su patria, Ítaca, ha sido desde siempre una mina inagotable de motivos e imágenes para escritores y artistas. Episodios como el del encuentro con los Cíclopes y Polifemo, con las Sirenas, con la maga Circe o la ninfa Calipso, así como la venganza que Odiseo lleva a cabo sobre los pretendientes de su mujer, Penélope, son sólo algunas de las semillas que han fecundado sin cesar la imaginación de los hombres.','Homero','laodisea','5')");
    	createTable("laodisea","Homero","Odisea","Junto con la “Ilíada”, la “Odisea” constituye una de las piedras angulares de la cultura occidental. El relato que hace Homero de las aventuras de Odiseo en su camino de regreso desde Troya hasta su patria, Ítaca, ha sido desde siempre una mina inagotable de motivos e imágenes para escritores y artis tas. Episodios como el del encuentro con los Cíclopes y Polifemo, con las Sirenas, con la maga Circe o la ninfa Calipso, así como la venganza que Odiseo lleva a cabo sobre los pretendientes de su mujer, Penélope, son sólo algunas de las semillas que han fecundado sin cesar la imaginación de los hombres."); 
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Paco Yunque','El cuento es el relato de las vicisitudes de un niño tímido y de origen humilde, Paco Yunque, durante su primer día de clases, en el cual debe soportar los maltratos y humillaciones de otro niño, Humberto Grieve, hijo de los patrones de su madre.','Cesar Vallejo','pacoyunque','4')");
    	createTable("pacoyunque","Cesar Vallejo","Paco Yunque"," El cuento es el relato de las vicisitudes de un niño tímido y de origen humilde, Paco Yunque, durante su primer día de clases, en el cual debe soportar los maltratos y humillaciones de otro niño, Humberto Grieve, hijo de los patrones de su madre.");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('El Tungsteno','Es una obra de denuncia contra los peligros de la penetración imperialista en el Perú que se realiza por intermedio de las grandes transnacionales mineras, las cuales son apoyadas por la oligarquía local, así como por otros oportunistas, cuyo único interés es el mayor lucro posible, para lo cual no tienen escrúpulos en expropiar a precio irrisorrio las tierras de los nativos, pagar a los obreros salarios ínfimos y cometer una serie de crímenes, abusos y tropelías contra la población local, todo a nombre de la «modernidad» y el «progreso». Sin embargo, para el autor, una luz de esperanza se ilumina a través de idealistas que se proponen luchar por la justicia social.','Cesar Vallejo','eltungsteno','4')");
    	createTable("eltungsteno","Cesar Vallejo"," El tungsteno"," Es una obra de denuncia contra los peligros de la penetración imperialista en el Perú que se realiza por intermedio de las grandes transnacionales mineras, las cuales son apoyadas por la oligarquía local, así como por otros oportunistas, cuyo único interés es el mayor lucro posible, para lo cual no tienen escrúpulos en expropiar a precio irrisorrio las tierras de los nativos, pagar a los obreros salarios ínfimos y cometer una serie de crímenes, abusos y tropelías contra la población local, todo a nombre de la «modernidad» y el «progreso». Sin embargo, para el autor, una luz de esperanza se ilumina a través de idealistas que se proponen luchar por la justicia social.");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Los Miserables','El tema de la obra los Miserables es el perdón como lo hace el Arzobispo con Jean Valjean y luego Jean Valjean perdona a Javert. El relato de Los Miserables comienza cuando Juan Valjean es condenado a prisión por un pequeño hurto. Cuando logra huir, la cárcel lo ha convertido en un ser embrutecido y marginado por la sociedad. Pero la aparición de un buen hombre le hará comprender que puede elegir entre el bien y el mal. Desde ese momento sus actos serán desinteresados y estarán encaminados a ayudar a los demás. En Los Miserables Víctor Hugo describe la realidad desesperanzada de los sectores bajos del París de mediados de siglo XIX y retrata magistralmente una época plagada de revolucion y cambios que marcarán el principio de una sociedad más justa.','Victor Hugo','losmiserables','5')");
    	createTable("losmiserables","Victor Hugo"," Los Miserables"," El tema de la obra los Miserables es el perdón como lo hace el Arzobispo con Jean Valjean y luego Jean Valjean perdona a Javert. El relato de Los Miserables comienza cuando Juan Valjean es condenado a prisión por un pequeño hurto. Cuando logra huir, la cárcel lo ha convertido en un ser embrutecido y marginado por la sociedad. Pero la aparición de un buen hombre le hará comprender que puede elegir entre el bien y el mal. Desde ese momento sus actos serán desinteresados y estarán encaminados a ayudar a los demás. En Los Miserables Víctor Hugo describe la realidad desesperanzada de los sectores bajos del París de mediados de siglo XIX y retrata magistralmente una época plagada de revolucion y cambios que marcarán el principio de una sociedad más justa.");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('La Metamorfosis','Al despertar Gregorio Samsa una mañana, tras un sueño intranquilo, encontrose en su cama convertido en un monstruoso insecto. Al despertar Gregorio Samsa una mañana, tras un sueño intranquilo, encontrose en su cama convertido en un monstruoso insecto. Hallábase echado sobre el duro caparazón de su espalda, y, al alzar un poco la cabeza, vio la figura convexa de su vientre oscuro, surcado por curvadas callosidades, cuya prominencia apenas si podía aguantar la colcha, que estaba visiblemente a punto de escurrirse hasta el suelo. Innumerables patas, lamentablemente escuálidas en comparación con el grosor ordinario de sus piernas, ofrecían a sus ojos el espectáculo de una agitación sin consistencia','Franz Kafka','lametamorfosis','5')");
    	createTable("lametamorfosis","Franz Kafka"," La Metamorfosis"," Al despertar Gregorio Samsa una mañana, tras un sueño intranquilo, encontrose en su cama convertido en un monstruoso insecto. Al despertar Gregorio Samsa una mañana, tras un sueño intranquilo, encontrose en su cama convertido en un monstruoso insecto. Hallábase echado sobre el duro caparazón de su espalda, y, al alzar un poco la cabeza, vio la figura convexa de su vientre oscuro, surcado por curvadas callosidades, cuya prominencia apenas si podía aguantar la colcha, que estaba visiblemente a punto de escurrirse hasta el suelo. Innumerables patas, lamentablemente escuálidas en comparación con el grosor ordinario de sus piernas, ofrecían a sus ojos el espectáculo de una agitación sin consistencia");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Crimen y Castigo','El escritor narra el asesinato de una avara anciana usurera llamada Elena Ivanovna, cometido por el estudiante universitario Raskolnikov que tenia problemas económicos para continuar con sus estudios, Raskolnikov, después de matar a la prestamista y a su hermana con un hacha se apodera de todas las alhajas. Luego se deshace del arma homicida y esconde las alhajas en el hueco de un patio, cerca de un edificio.','Fiodor Dostoievski','crimenycastigo','4')");    	
    	createTable("crimenycastigo","Fiodor Dostoievski","Crimen y Castigo"," El escritor narra el asesinato de una avara anciana usurera llamada Elena Ivanovna, cometido por el estudiante universitario Raskolnikov que tenia problemas económicos para continuar con sus estudios, Raskolnikov, después de matar a la prestamista y a su hermana con un hacha se apodera de todas las alhajas. Luego se deshace del arma homicida y esconde las alhajas en el hueco de un patio, cerca de un edificio.");   	
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Fabla Salvaje','El argumento se centra en la locura de un campesino de los Andes, Balta Espinar. Luego de observarse en un espejo que se hace trizas ante su reflejo, Balta es preso de una angustia espantosa. De un momento a otro se siente observado por un ser fantasmal y termina por creer que se trata del amante de su esposa Adelaida, quien se halla embarazada. Preso de celos, insulta y maltrata a su mujer de la manera más innoble. Abandona luego su cabaña, se sube a un risco y contempla el paisaje que parece tranquilizarlo por un momento, cuando de pronto siente nuevamente la presencia del ser misterioso que le roza la espalda; voltea ansioso para descubrirlo, pero pierde el equilibrio y cae al abismo. Ese mismo día su esposa da a luz, ignorante del espantoso fin de su esposo.','Cesar Vallejo','fablasalvaje','4')");
    	createTable("fablasalvaje","Cesar Vallejo","Fabla salvaje"," El argumento se centra en la locura de un campesino de los Andes, Balta Espinar. Luego de observarse en un espejo que se hace trizas ante su reflejo, Balta es preso de una angustia espantosa. De un momento a otro se siente observado por un ser fantasmal y termina por creer que se trata del amante de su esposa Adelaida, quien se halla embarazada. Preso de celos, insulta y maltrata a su mujer de la manera más innoble. Abandona luego su cabaña, se sube a un risco y contempla el paisaje que parece tranquilizarlo por un momento, cuando de pronto siente nuevamente la presencia del ser misterioso que le roza la espalda; voltea ansioso para descubrirlo, pero pierde el equilibrio y cae al abismo. Ese mismo día su esposa da a luz, ignorante del espantoso fin de su esposo.");
    }
    
    public void insertPublications2(){
    		SQLiteDatabase bd = this.getWritableDatabase();
    		bd.execSQL("INSERT INTO publicacion VALUES ('El Mundo es Ancho y Ajeno','Novela de dimensiones épicas que relata la resistencia heroica de unacomunidad indígena ante una injusta expropiación de tierras, EL MUNDO ESANCHO Y AJENO es la culminación del genio narrativo de ciro alegria y de la literatura indigenista. Los dos alcaldes que dirigensucesivamente la comunidad simbolizan dos posturas contrapuestas: «Entrela actitud resignadamente estoica y de alianza mística con la tierra deRosendo Maqui ?escribe Mario Vargas Llosa a propósito de la novela? y ladecididamente moderna y revolucionaria de Benito Castro, parecequebrarse toda esperanza? Pero a ningún lector se le escapa que, a pesarde su aparente derrota, queda en estas páginas inconmoviblemente en pieel hombre indio».','Ciro Alegria','elmundoesanchoyajeno','4')");
        	createTable("elmundoesanchoyajeno","Ciro Alegria"," El Mundo es Ancho y Ajeno","Novela de dimensiones épicas que relata la resistencia heroica de unacomunidad indígena ante una injusta expropiación de tierras, EL MUNDO ESANCHO Y AJENO es la culminación del genio narrativo de ciro alegria y de la literatura indigenista. Los dos alcaldes que dirigensucesivamente la comunidad simbolizan dospos turas contrapuestas: «Entrela actitud resignadamente estoica y de alianza mística con la tierra deRosendo Maqui ?escribe Mario Vargas Llosa a propósito de la novela? y ladecididamente moderna y revolucionaria de Benito Castro, parecequebrarse toda esperanza? Pero a ningún lector se le escapa que, a pesarde su aparente derrota, queda en estas páginas inconmoviblemente en pieel hombre indio».");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('La Fiesta del Chivo','En La Fiesta del Chivo asistimos a un doble retorno. Mientras Urania Cabral visita a su padre en Santo Domingo, volvemos a 1961, cuando la capital dominicana aún se llamaba Ciudad Trujillo. Allí un hombre que no suda tiraniza a tres millones de personas sin saber que se gesta una maquiavélica transición a la democracia. Vargas Llosa, un clásico contemporáneo, relata el fin de una era dando voz, entre otros personajes históricos, al implacable general Trujillo, apodado el Chivo, y al sosegado y hábil doctor Balaguer (sempiterno presidente de la República Dominicana). Con una precisión difícilmente superables, este peruano universal muestra que política puede consistir en abrirse camino entre cadáveres, y que un ser inocente puede convertirse en un regalo truculento','Mario Vargas Llosa','lafiestadelchivo','5')");
        	createTable("lafiestadelchivo","Mario Vargas Llosa"," La Fiesta del Chivo"," En La Fiesta del Chivo asistimos a un doble retorno. Mientras Urania Cabral visita a su padre en Santo Domingo, volvemos a 1961, cuando la capital dominicana aún se llamaba Ciudad Trujillo. Allí un hombre que no suda tiraniza a tres millones de personas sin saber que se gesta una maquiavélica transición a la democracia. Vargas Llosa, un clásico contemporáneo, relata el fin de una era dando voz, entre otros personajes históricos, al implacable general Trujillo, apodado el Chivo, y al sosegado y hábil doctor Balaguer (sempiterno presidente de la República Dominicana). Con una precisión difícilmente superables, este peruano universal muestra que política puede consistir en abrirse camino entre cadáveres, y que un ser inocente puede convertirse en un regalo truculento");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('Oliver Twist','La historia de un niño huérfano perdido en los bajos fondos londinenses le sirve a Dickens para reflejar el mundo del hampa, la miseria y la hipocresía social, en una historia plagada de estereotipos, siempre superados por la maestría del autor. El hilo central, las peripecias de Oliver desde sus comienzos en la más absolut a pobreza hasta su ascenso, se entreteje con asuntos tangenciales, que a veces resultan predominantes, pues son esenciales para el propósito del autor: la denuncia social a través de la descripción del Londres de la época, con sus lacras sociales y morales... En definitiva, Oliver Twist es una historia de buenos y malos, donde se mezclan lo jovial, lo sentimental, lo lúgubre y lo trágico','Charles Dickens','olivertwist','5')");
        	createTable("olivertwist","Charles Dickens","Oliver Twist"," La historia de un niño huérfano perdido en los bajos fondos londinenses le sirve a Dickens para reflejar el mundo del hampa, la miseria y la hipocresía social, en una historia plagada de estereotipos, siempre superados por la maestría del autor. El hilo central, las peripecias de Oliver desde sus comienzos en la más absolut a pobreza hasta su ascenso, se entreteje con asuntos tangenciales, que a veces resultan predominantes, pues son esenciales para el propósito del autor: la denuncia social a través de la descripción del Londres de la época, con sus lacras sociales y morales... En definitiva, Oliver Twist es una historia de buenos y malos, donde se mezclan lo jovial, lo sentimental, lo lúgubre y lo trágico");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('Pantaleón y las Visitadoras','Pantaleón Pantoja, un capitán del Ejército recientemente ascendido, recibe la misión de establecer un servicio de prostitución para las Fuerzas Armadas del Perú en el más absoluto secreto militar. Estricto cumplidor del deber que le ha sido asignado, Pantaleón se traslada a Iquitos, en plena selva, para llevar a cabo su com etido, pero se entrega a esta misión con tal obcecación que termina por poner en peligro el engranaje que él mismo ha puesto en movimiento. Mario Vargas Llosa utiliza esta anécdota para subrayar la hipocresía de las instituciones que se llaman ejemplares y del oficio más viejo del mundo.','Mario Vargas Llosa','pantaleonylasvisitadoras','5')");
        	createTable("pantaleonylasvisitadoras","Mario Vargas Llosa","Pantaleón y las Visitadoras","Pantaleón Pantoja, un capitán del Ejército recientemente ascendido, recibe la misión de establecer un servicio de prostitución para las Fuerzas Armadas del Perú en el más absoluto secreto militar. Estricto cumplidor del deber que le ha sido asignado, Pantaleón se traslada a Iquitos, en plena selva, para llevar a cabo su com etido, pero se entrega a esta misión con tal obcecación que termina por poner en peligro el engranaje que él mismo ha puesto en movimiento. Mario Vargas Llosa utiliza esta anécdota para subrayar la hipocresía de las instituciones que se llaman ejemplares y del oficio más viejo del mundo.");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('Conversación en La Catedral','Zavalita y el zambo Ambrosio conversan en La Catedral. Estamos en Perú, durante el «ochenio» dictatorial del general Manuel A. Odría. Unas cuantas cervezas y un río de palabras en libertad para responder a la palabra amordazada por la dictadura. Conversación en La Catedral no es, sin embargo, una novela histórica. Sus personajes, las historias que éstos cuentan, los fragmentos que van encajando, conforman la descripción minuciosa de un envilecimiento colectivo, el repaso de todos los caminos que hacen desembocar a un pueblo entero en la frustración. Conversación en La Catedral es algo más que un hito en el derrotero literario de Mario Vargas Llosa: es un punto de referencia insoslayable, un dato fijo en la historia de la literatura actual.','Mario Vargas Llosa','conversacionenlacatedral','4')");
        	createTable("conversacionenlacatedral","Mario Vargas Llosa","Conversación en La Catedral","Zavalita y el zambo Ambrosio conversan en La Catedral. Estamos en Perú, durante el «ochenio» dictatorial del general Manuel A. Odría. Unas cuantas cervezas y un río de palabras en libertad para responder a la palabra amordazada por la dictadura. Conversación en La Catedral no es, sin embargo, una novela histórica. Sus personajes, las historias que éstos cuentan, los fragmentos que van encajando, conforman la descripción minuciosa de un envilecimiento colectivo, el repaso de todos los caminos que hacen desembocar a un pueblo entero en la frustración. Conversación en La Catedral es algo más que un hito en el derrotero literario de Mario Vargas Llosa: es un punto de referencia insoslayable, un dato fijo en la historia de la literatura actual.");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('Los Perros Hambrientos','En sus páginas late el drama del hombre y la tierra, de la sequía y el hambre, aproximándose al mayor problema del hombre peruano: la propiedad de la tierra, su lucha diaria por vencer la agreste naturaleza y la dificultad de las relaciones humanas entre las desproporciones que determinan los papeles antagónicos de agua-sequía, blanco-indio, pobreza-riqueza, cosmovisión occidental- cosmovisión andina. Todo ello estableciendo dos mundos paralelos, el de los hombres y el de los perros, que ante el hambre no dudan en matarse devorarse entre ellos.','Ciro Alegria','losperroshambrientos','5')");
        	createTable("losperroshambrientos","Ciro Alegria"," Los Perros Hambrientos"," En sus páginas late el drama del hombre y la tierra, de la sequía y el hambre, aproximándose al mayor problema del hombre peruano: la propiedad de la tierra, su lucha diaria por vencer la agreste naturaleza y la dificultad de las relaciones humanas entre las desproporciones que determinan los papeles antagónicos de agua-sequía, blanco-indio, pobreza-riqueza, cosmovisión occidental- cosmovisión andina. Todo ello estableciendo dos mundos paralelos, el de los hombres y el de los perros, que ante el hambre no dudan en matarse devorarse entre ellos.");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('La Ciudad y los Perros','La ciudad y los perros no solamente es un ataque contra la crueldad ejercida a un grupo de jóvenes alumnos del Colegio Militar Leoncio Prado, sino también una crítica frontal al concepto erróneo de la virilidad, de sus funciones y de las consecuencias de una educación castrense malentendida. Aunada a la brutalidad propia de la vida militar, a lo largo de las páginas de esta extraordinaria novela, la vehemencia y la pasión de la juventud se desbocan hasta llegar a una furia, una rabia y un fanatismo que anulan toda sensibilidad.','Mario Vargas Llosa','laciudadylosperros','5')");
        	createTable("laciudadylosperros","Mario Vargas Llosa"," La Ciudad y los Perros"," La ciudad y los perros no solamente es un ataque contra la crueldad ejercida a un grupo de jóvenes alumnos del Colegio Militar Leoncio Prado, sino también una crítica frontal al concepto erróneo de la virilidad, de sus funciones y de las consecuencias de una educación castrense malentendida. Aunada a la brutalidad propia de la vida militar, a lo largo de las páginas de esta extraordinaria novela, la vehemencia y la pasión de la juventud se desbocan hasta llegar a una furia, una rabia y un fanatismo que anulan toda sensibilidad.");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('Las Aventuras de Huckleberry Finn','Huckleberry Finn, el amigo de Tom Sawyer, es un muchacho aventurero al que no le gusta la escuela sino andar por ahí a su aire. Sin embargo, la vida de Huck no es fácil, su padre lo maltrata y tiene que vivir con la viuda Douglas, una mujer que se ha propuesto civilizarlo. Huck decide escapar de todo y emprende un viaje por el río Misisipi. En su huida le acompañará Jim, un esclavo negro que busca su libertad. Entre lo dos se forjará una especial amistad.','Mark Twain','aventurasdehuckleberryfinn','5')");
        	createTable("aventurasdehuckleberryfinn","Mark Twain"," Las Aventuras de Huckleberry Finn","Huckleberry Finn, el amigo de Tom Sawyer, es un muchacho aventurero al que no le gusta la escuela sino andar por ahí a su aire. Sin embargo, la vida de Huck no es fácil, su padre lo maltrata y tiene que vivir con la viuda Douglas, una mujer que se ha propuesto civilizarlo. Huck decide escapar de todo y emprende un viaje por el río Misisipi. En su huida le acompañará Jim, un esclavo negro que busca su libertad. Entre lo dos se forjará una especial amistad.");

        	bd.execSQL("INSERT INTO publicacion VALUES ('Las Aventuras de Tom Sawyer','No hay nadie como Tom Sawyer para conseguir que sus amigos trabajen por él sin que se den cuenta, para realizar conjuros que no siempre funcionan, para meterse en líos y salir bien parado, para buscar tesoros junto a su gran amigo Huckleberry, o para formar una banda de peligrosos forajidos. Estas son sus increíbles aventuras, una de las obras maestras de Mark Twain','Mark Twain','lasaventurasdetomsawyer','5')");
        	createTable("lasaventurasdetomsawyer","Mark Twain"," Las Aventuras de Tom Sawyer"," No hay nadie como Tom Sawyer para conseguir que sus amigos trabajen por él sin que se den cuenta, para realizar conjuros que no siempre funcionan, para meterse en líos y salir bien parado, para buscar tesoros junto a su gran amigo Huckleberry, o para formar una banda de peligrosos forajidos. Estas son sus increíbles aventuras, una de las obras maestras de Mark Twain");
        	
    	}
    
}
    