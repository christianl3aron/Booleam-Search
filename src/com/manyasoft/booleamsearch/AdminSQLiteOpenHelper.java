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
    	resumen=resumen.toLowerCase().replace(",", "").replace(":", "").replace(".", "").replace(";", "").replace("_", "").replace("-", "").replace("\"", "").replace("�", "").replace("!", "").replace("\'", "").replace("�", "").replace("�", "").replace("�", "").replace("�","");
    	
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
    	bd.execSQL("INSERT INTO paomitidas VALUES ('ah�')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('al')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('alli')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('asi')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('as�')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('como')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('con')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('dc')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('de')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('del')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('desde')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('e')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('el')");
    	bd.execSQL("INSERT INTO paomitidas VALUES ('�l')");
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
    	bd.execSQL("INSERT INTO paomitidas VALUES ('m�s')");
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
    	bd.execSQL("INSERT INTO paomitidas VALUES ('qu�')");
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

    	bd.execSQL("INSERT INTO publicacion VALUES ('Alicia en el Pa�s de las Maravillas','El sorprendente viaje de la joven Alicia se desenvuelve entre misterios incomprensibles, adivinanzas absurdas e historias interminables. En un paisaje donde todo es enorme o diminuto, cientos de personajes variopintos y alocados juegan al cricket o toman el t� durante d�as y d�as. Es el pa�s donde el tiempo se estira y encoge, donde Alicia crece y se hace diminuta, donde un conejo siempre llega tarde y donde una reina loca no hace sino gritar: !Qu� le corten la cabeza!','Lewis Carroll','aliciapaismaravillas','4')");
    	createTable("aliciapaismaravillas","Lewis Carroll","Alicia en el Pa�s de las Maravillas"," El sorprendente viaje de la joven Alicia se desenvuelve entre misterios incomprensibles, adivinanzas absurdas e historias interminables. En un paisaje donde todo es enorme o diminuto, cientos de personajes variopintos y alocados juegan al cricket o toman el t� durante d�as y d�as. Es el pa�s donde el tiempo se estira y encoge, donde Alicia crece y se hace diminuta, donde un conejo siempre llega tarde y donde una reina loca no hace sino gritar: !Qu� le corten la cabeza!");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('El Mago De Oz','Doroty vive en una granja de Kansas con sus t�os. De pronto se levanta un cicl�n que arranca a su casa con ella oculta en su interior, y la lleva por el aire hasta el incre�ble pa�s de Oz. Desde ese momento, Doroty trata de encontrar un camino de vuelta a Kansas, pero el �nico que puede ayudarla es el Mago de Oz. Alli comienza una traves�a en busca del mago y de superar las pruebas que le imponen. A lo largo del camino se encuentra con extra�os amigos que la siguen pues tambi�n quieren participar en los beneficios de conocer al Gran Mago. Ellos son el Espantap�jaros, el Le�ador de Hojalata y el Le�n Cobarde','Lyman Frank Baum','magodeoz','5')");
    	createTable("magodeoz","Lyman Frank Baum"," El Mago De Oz","Doroty vive en una granja de Kansas con sus t�os. De pronto se levanta un cicl�n que arranca a su casa con ella oculta en su interior, y la lleva por el aire hasta el incre�ble pa�s de Oz. Desde ese momento, Doroty trata de encontrar un camino de vuelta a Kansas, pero el �nico que puede ayudarla es el Mago de Oz. Alli comienza una traves�a en busca del mago y de superar las pruebas que le imponen. A lo largo del camino se encuentra con extra�os amigos que la siguen pues tambi�n quieren participar en los beneficios de conocer al Gran Mago. Ellos son el Espantap�jaros, el Le�ador de Hojalata y el Le�n Cobarde");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Cantar de Mio Cid','M�o Cid, es un personaje hist�rico real, su nombre es Rodrigo D�az de Vivar y naci� hacia el a�o 1043. Educado en la corte del rey Fernando I, mantuvo una gran amistad con su hijo Sancho II. Alfonso VI, hermano de Sancho y sucesor suyo en el trono no fue amigo del Cid, procurando pues el destierro de este, y ah� comienza a revelarse el marco esc�nico donde ocurre primeramente esta historia convertida en leyenda �pica. Fue escrito alrededor del 1140 d.C, 40 a�os despu�s de la muerte del Cid. El poema se divide en tres partes: primero- destierro; segundo-conquista de Valencia y matrimonio de hijas; tercero-Venganza y justicia de M�o Cid.','Anonimo','cantardemiocid','5')");
    	createTable("cantardemiocid","Anonimo","Cantar de Mio Cid","M�o Cid, es un personaje hist�rico real, su nombre es Rodrigo D�az de Vivar y naci� hacia el a�o 1043. Educado en la corte del rey Fernando I, mantuvo una gran amistad con su hijo Sancho II. Alfonso VI, hermano de Sancho y sucesor suyo en el trono no fue amigo del Cid, procurando pues el destierro de este, y ah� comienza a revelarse el marco esc�nico donde ocurre primeramente esta historia convertida en leyenda �pica. Fue escrito alrededor del 1140 d.C, 40 a�os despu�s de la muerte del Cid. El poema se divide en tres partes: primero- destierro; segundo-conquista de Valencia y matrimonio de hijas; tercero-Venganza y justicia de M�o Cid.");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Lazarillo de Tormes','Lazarillo es un ni�o nacido en el rio Tormes que a una temprana edad es entregado a un amo ciego. Este amo lo acaba avandonando y lazaro debe ir por las calles de amo en amo y arreglandoselas con travesuras y enga�os para comer y salir adelante. A medida que se va haciendo mayor, va perdienmdo la inocencia que tenia','Anonimo','ellazarillodetormes','4')");
    	createTable("ellazarillodetormes","Anonimo","Lazarillo de Tormes","Lazarillo es un ni�o nacido en el rio Tormes que a una temprana edad es entregado a un amo ciego. Este amo lo acaba avandonando y lazaro debe ir por las calles de amo en amo y arreglandoselas con travesuras y enga�os para comer y salir adelante. A medida que se va haciendo mayor, va perdienmdo la inocencia que tenia");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Iliada','Unos pocos d�as antes del �ltimo de los diez a�os que dur� el asedio de los aqueos a la ciudad de Troya, proporcionan el marco cronol�gico a los acontecimientos narrados en la Il�ada, el poema m�s antiguo de la literatura occidental. Producto de una larga tradici�n oral, la epopeya, como advierte su autor en el primer verso , relata la historia de las consecuencias de una pasi�n humana. Aquiles, encolerizado por el ultraje de Agamen�n, que como caudillo de la expedici�n griega le ha arrebatado a Briseida, su parte del bot�n, decide retirarse del combate. Pero no tardar� mucho en volver a �l, con furia renovada, a ra�z de la muerte de su compa�ero Patroclo a manos de los troyanos. Alrededor de estos hechos, la Il�ada nos presenta un mundo de ideales heroicos y haza�as guerreras, en el que los dioses poseen un protagonismo y una presencia permanentes','Homero','lailiada','5')");
    	createTable("lailiada","Homero","Iliada"," Unos pocos d�as antes del �ltimo de los diez a�os que dur� el asedio de los aqueos a la ciudad de Troya, proporcionan el marco cronol�gico a los acontecimientos narrados en la Il�ada, el poema m�s antiguo de la literatura occidental. Producto de una larga tradici�n oral, la epopeya, como advierte su autor en el primer verso , relata la historia de las consecuencias de una pasi�n humana. Aquiles, encolerizado por el ultraje de Agamen�n, que como caudillo de la expedici�n griega le ha arrebatado a Briseida, su parte del bot�n, decide retirarse del combate. Pero no tardar� mucho en volver a �l, con furia renovada, a ra�z de la muerte de su compa�ero Patroclo a manos de los troyanos. Alrededor de estos hechos, la Il�ada nos presenta un mundo de ideales heroicos y haza�as guerreras, en el que los dioses poseen un protagonismo y una presencia permanentes");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Odisea','Junto con la �Il�ada�, la �Odisea� constituye una de las piedras angulares de la cultura occidental. El relato que hace Homero de las aventuras de Odiseo en su camino de regreso desde Troya hasta su patria, �taca, ha sido desde siempre una mina inagotable de motivos e im�genes para escritores y artistas. Episodios como el del encuentro con los C�clopes y Polifemo, con las Sirenas, con la maga Circe o la ninfa Calipso, as� como la venganza que Odiseo lleva a cabo sobre los pretendientes de su mujer, Pen�lope, son s�lo algunas de las semillas que han fecundado sin cesar la imaginaci�n de los hombres.','Homero','laodisea','5')");
    	createTable("laodisea","Homero","Odisea","Junto con la �Il�ada�, la �Odisea� constituye una de las piedras angulares de la cultura occidental. El relato que hace Homero de las aventuras de Odiseo en su camino de regreso desde Troya hasta su patria, �taca, ha sido desde siempre una mina inagotable de motivos e im�genes para escritores y artis tas. Episodios como el del encuentro con los C�clopes y Polifemo, con las Sirenas, con la maga Circe o la ninfa Calipso, as� como la venganza que Odiseo lleva a cabo sobre los pretendientes de su mujer, Pen�lope, son s�lo algunas de las semillas que han fecundado sin cesar la imaginaci�n de los hombres."); 
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Paco Yunque','El cuento es el relato de las vicisitudes de un ni�o t�mido y de origen humilde, Paco Yunque, durante su primer d�a de clases, en el cual debe soportar los maltratos y humillaciones de otro ni�o, Humberto Grieve, hijo de los patrones de su madre.','Cesar Vallejo','pacoyunque','4')");
    	createTable("pacoyunque","Cesar Vallejo","Paco Yunque"," El cuento es el relato de las vicisitudes de un ni�o t�mido y de origen humilde, Paco Yunque, durante su primer d�a de clases, en el cual debe soportar los maltratos y humillaciones de otro ni�o, Humberto Grieve, hijo de los patrones de su madre.");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('El Tungsteno','Es una obra de denuncia contra los peligros de la penetraci�n imperialista en el Per� que se realiza por intermedio de las grandes transnacionales mineras, las cuales son apoyadas por la oligarqu�a local, as� como por otros oportunistas, cuyo �nico inter�s es el mayor lucro posible, para lo cual no tienen escr�pulos en expropiar a precio irrisorrio las tierras de los nativos, pagar a los obreros salarios �nfimos y cometer una serie de cr�menes, abusos y tropel�as contra la poblaci�n local, todo a nombre de la �modernidad� y el �progreso�. Sin embargo, para el autor, una luz de esperanza se ilumina a trav�s de idealistas que se proponen luchar por la justicia social.','Cesar Vallejo','eltungsteno','4')");
    	createTable("eltungsteno","Cesar Vallejo"," El tungsteno"," Es una obra de denuncia contra los peligros de la penetraci�n imperialista en el Per� que se realiza por intermedio de las grandes transnacionales mineras, las cuales son apoyadas por la oligarqu�a local, as� como por otros oportunistas, cuyo �nico inter�s es el mayor lucro posible, para lo cual no tienen escr�pulos en expropiar a precio irrisorrio las tierras de los nativos, pagar a los obreros salarios �nfimos y cometer una serie de cr�menes, abusos y tropel�as contra la poblaci�n local, todo a nombre de la �modernidad� y el �progreso�. Sin embargo, para el autor, una luz de esperanza se ilumina a trav�s de idealistas que se proponen luchar por la justicia social.");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Los Miserables','El tema de la obra los Miserables es el perd�n como lo hace el Arzobispo con Jean Valjean y luego Jean Valjean perdona a Javert. El relato de Los Miserables comienza cuando Juan Valjean es condenado a prisi�n por un peque�o hurto. Cuando logra huir, la c�rcel lo ha convertido en un ser embrutecido y marginado por la sociedad. Pero la aparici�n de un buen hombre le har� comprender que puede elegir entre el bien y el mal. Desde ese momento sus actos ser�n desinteresados y estar�n encaminados a ayudar a los dem�s. En Los Miserables V�ctor Hugo describe la realidad desesperanzada de los sectores bajos del Par�s de mediados de siglo XIX y retrata magistralmente una �poca plagada de revolucion y cambios que marcar�n el principio de una sociedad m�s justa.','Victor Hugo','losmiserables','5')");
    	createTable("losmiserables","Victor Hugo"," Los Miserables"," El tema de la obra los Miserables es el perd�n como lo hace el Arzobispo con Jean Valjean y luego Jean Valjean perdona a Javert. El relato de Los Miserables comienza cuando Juan Valjean es condenado a prisi�n por un peque�o hurto. Cuando logra huir, la c�rcel lo ha convertido en un ser embrutecido y marginado por la sociedad. Pero la aparici�n de un buen hombre le har� comprender que puede elegir entre el bien y el mal. Desde ese momento sus actos ser�n desinteresados y estar�n encaminados a ayudar a los dem�s. En Los Miserables V�ctor Hugo describe la realidad desesperanzada de los sectores bajos del Par�s de mediados de siglo XIX y retrata magistralmente una �poca plagada de revolucion y cambios que marcar�n el principio de una sociedad m�s justa.");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('La Metamorfosis','Al despertar Gregorio Samsa una ma�ana, tras un sue�o intranquilo, encontrose en su cama convertido en un monstruoso insecto. Al despertar Gregorio Samsa una ma�ana, tras un sue�o intranquilo, encontrose en su cama convertido en un monstruoso insecto. Hall�base echado sobre el duro caparaz�n de su espalda, y, al alzar un poco la cabeza, vio la figura convexa de su vientre oscuro, surcado por curvadas callosidades, cuya prominencia apenas si pod�a aguantar la colcha, que estaba visiblemente a punto de escurrirse hasta el suelo. Innumerables patas, lamentablemente escu�lidas en comparaci�n con el grosor ordinario de sus piernas, ofrec�an a sus ojos el espect�culo de una agitaci�n sin consistencia','Franz Kafka','lametamorfosis','5')");
    	createTable("lametamorfosis","Franz Kafka"," La Metamorfosis"," Al despertar Gregorio Samsa una ma�ana, tras un sue�o intranquilo, encontrose en su cama convertido en un monstruoso insecto. Al despertar Gregorio Samsa una ma�ana, tras un sue�o intranquilo, encontrose en su cama convertido en un monstruoso insecto. Hall�base echado sobre el duro caparaz�n de su espalda, y, al alzar un poco la cabeza, vio la figura convexa de su vientre oscuro, surcado por curvadas callosidades, cuya prominencia apenas si pod�a aguantar la colcha, que estaba visiblemente a punto de escurrirse hasta el suelo. Innumerables patas, lamentablemente escu�lidas en comparaci�n con el grosor ordinario de sus piernas, ofrec�an a sus ojos el espect�culo de una agitaci�n sin consistencia");
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Crimen y Castigo','El escritor narra el asesinato de una avara anciana usurera llamada Elena Ivanovna, cometido por el estudiante universitario Raskolnikov que tenia problemas econ�micos para continuar con sus estudios, Raskolnikov, despu�s de matar a la prestamista y a su hermana con un hacha se apodera de todas las alhajas. Luego se deshace del arma homicida y esconde las alhajas en el hueco de un patio, cerca de un edificio.','Fiodor Dostoievski','crimenycastigo','4')");    	
    	createTable("crimenycastigo","Fiodor Dostoievski","Crimen y Castigo"," El escritor narra el asesinato de una avara anciana usurera llamada Elena Ivanovna, cometido por el estudiante universitario Raskolnikov que tenia problemas econ�micos para continuar con sus estudios, Raskolnikov, despu�s de matar a la prestamista y a su hermana con un hacha se apodera de todas las alhajas. Luego se deshace del arma homicida y esconde las alhajas en el hueco de un patio, cerca de un edificio.");   	
    	
    	bd.execSQL("INSERT INTO publicacion VALUES ('Fabla Salvaje','El argumento se centra en la locura de un campesino de los Andes, Balta Espinar. Luego de observarse en un espejo que se hace trizas ante su reflejo, Balta es preso de una angustia espantosa. De un momento a otro se siente observado por un ser fantasmal y termina por creer que se trata del amante de su esposa Adelaida, quien se halla embarazada. Preso de celos, insulta y maltrata a su mujer de la manera m�s innoble. Abandona luego su caba�a, se sube a un risco y contempla el paisaje que parece tranquilizarlo por un momento, cuando de pronto siente nuevamente la presencia del ser misterioso que le roza la espalda; voltea ansioso para descubrirlo, pero pierde el equilibrio y cae al abismo. Ese mismo d�a su esposa da a luz, ignorante del espantoso fin de su esposo.','Cesar Vallejo','fablasalvaje','4')");
    	createTable("fablasalvaje","Cesar Vallejo","Fabla salvaje"," El argumento se centra en la locura de un campesino de los Andes, Balta Espinar. Luego de observarse en un espejo que se hace trizas ante su reflejo, Balta es preso de una angustia espantosa. De un momento a otro se siente observado por un ser fantasmal y termina por creer que se trata del amante de su esposa Adelaida, quien se halla embarazada. Preso de celos, insulta y maltrata a su mujer de la manera m�s innoble. Abandona luego su caba�a, se sube a un risco y contempla el paisaje que parece tranquilizarlo por un momento, cuando de pronto siente nuevamente la presencia del ser misterioso que le roza la espalda; voltea ansioso para descubrirlo, pero pierde el equilibrio y cae al abismo. Ese mismo d�a su esposa da a luz, ignorante del espantoso fin de su esposo.");
    }
    
    public void insertPublications2(){
    		SQLiteDatabase bd = this.getWritableDatabase();
    		bd.execSQL("INSERT INTO publicacion VALUES ('El Mundo es Ancho y Ajeno','Novela de dimensiones �picas que relata la resistencia heroica de unacomunidad ind�gena ante una injusta expropiaci�n de tierras, EL MUNDO ESANCHO Y AJENO es la culminaci�n del genio narrativo de ciro alegria y de la literatura indigenista. Los dos alcaldes que dirigensucesivamente la comunidad simbolizan dos posturas contrapuestas: �Entrela actitud resignadamente estoica y de alianza m�stica con la tierra deRosendo Maqui ?escribe Mario Vargas Llosa a prop�sito de la novela? y ladecididamente moderna y revolucionaria de Benito Castro, parecequebrarse toda esperanza? Pero a ning�n lector se le escapa que, a pesarde su aparente derrota, queda en estas p�ginas inconmoviblemente en pieel hombre indio�.','Ciro Alegria','elmundoesanchoyajeno','4')");
        	createTable("elmundoesanchoyajeno","Ciro Alegria"," El Mundo es Ancho y Ajeno","Novela de dimensiones �picas que relata la resistencia heroica de unacomunidad ind�gena ante una injusta expropiaci�n de tierras, EL MUNDO ESANCHO Y AJENO es la culminaci�n del genio narrativo de ciro alegria y de la literatura indigenista. Los dos alcaldes que dirigensucesivamente la comunidad simbolizan dospos turas contrapuestas: �Entrela actitud resignadamente estoica y de alianza m�stica con la tierra deRosendo Maqui ?escribe Mario Vargas Llosa a prop�sito de la novela? y ladecididamente moderna y revolucionaria de Benito Castro, parecequebrarse toda esperanza? Pero a ning�n lector se le escapa que, a pesarde su aparente derrota, queda en estas p�ginas inconmoviblemente en pieel hombre indio�.");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('La Fiesta del Chivo','En La Fiesta del Chivo asistimos a un doble retorno. Mientras Urania Cabral visita a su padre en Santo Domingo, volvemos a 1961, cuando la capital dominicana a�n se llamaba Ciudad Trujillo. All� un hombre que no suda tiraniza a tres millones de personas sin saber que se gesta una maquiav�lica transici�n a la democracia. Vargas Llosa, un cl�sico contempor�neo, relata el fin de una era dando voz, entre otros personajes hist�ricos, al implacable general Trujillo, apodado el Chivo, y al sosegado y h�bil doctor Balaguer (sempiterno presidente de la Rep�blica Dominicana). Con una precisi�n dif�cilmente superables, este peruano universal muestra que pol�tica puede consistir en abrirse camino entre cad�veres, y que un ser inocente puede convertirse en un regalo truculento','Mario Vargas Llosa','lafiestadelchivo','5')");
        	createTable("lafiestadelchivo","Mario Vargas Llosa"," La Fiesta del Chivo"," En La Fiesta del Chivo asistimos a un doble retorno. Mientras Urania Cabral visita a su padre en Santo Domingo, volvemos a 1961, cuando la capital dominicana a�n se llamaba Ciudad Trujillo. All� un hombre que no suda tiraniza a tres millones de personas sin saber que se gesta una maquiav�lica transici�n a la democracia. Vargas Llosa, un cl�sico contempor�neo, relata el fin de una era dando voz, entre otros personajes hist�ricos, al implacable general Trujillo, apodado el Chivo, y al sosegado y h�bil doctor Balaguer (sempiterno presidente de la Rep�blica Dominicana). Con una precisi�n dif�cilmente superables, este peruano universal muestra que pol�tica puede consistir en abrirse camino entre cad�veres, y que un ser inocente puede convertirse en un regalo truculento");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('Oliver Twist','La historia de un ni�o hu�rfano perdido en los bajos fondos londinenses le sirve a Dickens para reflejar el mundo del hampa, la miseria y la hipocres�a social, en una historia plagada de estereotipos, siempre superados por la maestr�a del autor. El hilo central, las peripecias de Oliver desde sus comienzos en la m�s absolut a pobreza hasta su ascenso, se entreteje con asuntos tangenciales, que a veces resultan predominantes, pues son esenciales para el prop�sito del autor: la denuncia social a trav�s de la descripci�n del Londres de la �poca, con sus lacras sociales y morales... En definitiva, Oliver Twist es una historia de buenos y malos, donde se mezclan lo jovial, lo sentimental, lo l�gubre y lo tr�gico','Charles Dickens','olivertwist','5')");
        	createTable("olivertwist","Charles Dickens","Oliver Twist"," La historia de un ni�o hu�rfano perdido en los bajos fondos londinenses le sirve a Dickens para reflejar el mundo del hampa, la miseria y la hipocres�a social, en una historia plagada de estereotipos, siempre superados por la maestr�a del autor. El hilo central, las peripecias de Oliver desde sus comienzos en la m�s absolut a pobreza hasta su ascenso, se entreteje con asuntos tangenciales, que a veces resultan predominantes, pues son esenciales para el prop�sito del autor: la denuncia social a trav�s de la descripci�n del Londres de la �poca, con sus lacras sociales y morales... En definitiva, Oliver Twist es una historia de buenos y malos, donde se mezclan lo jovial, lo sentimental, lo l�gubre y lo tr�gico");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('Pantale�n y las Visitadoras','Pantale�n Pantoja, un capit�n del Ej�rcito recientemente ascendido, recibe la misi�n de establecer un servicio de prostituci�n para las Fuerzas Armadas del Per� en el m�s absoluto secreto militar. Estricto cumplidor del deber que le ha sido asignado, Pantale�n se traslada a Iquitos, en plena selva, para llevar a cabo su com etido, pero se entrega a esta misi�n con tal obcecaci�n que termina por poner en peligro el engranaje que �l mismo ha puesto en movimiento. Mario Vargas Llosa utiliza esta an�cdota para subrayar la hipocres�a de las instituciones que se llaman ejemplares y del oficio m�s viejo del mundo.','Mario Vargas Llosa','pantaleonylasvisitadoras','5')");
        	createTable("pantaleonylasvisitadoras","Mario Vargas Llosa","Pantale�n y las Visitadoras","Pantale�n Pantoja, un capit�n del Ej�rcito recientemente ascendido, recibe la misi�n de establecer un servicio de prostituci�n para las Fuerzas Armadas del Per� en el m�s absoluto secreto militar. Estricto cumplidor del deber que le ha sido asignado, Pantale�n se traslada a Iquitos, en plena selva, para llevar a cabo su com etido, pero se entrega a esta misi�n con tal obcecaci�n que termina por poner en peligro el engranaje que �l mismo ha puesto en movimiento. Mario Vargas Llosa utiliza esta an�cdota para subrayar la hipocres�a de las instituciones que se llaman ejemplares y del oficio m�s viejo del mundo.");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('Conversaci�n en La Catedral','Zavalita y el zambo Ambrosio conversan en La Catedral. Estamos en Per�, durante el �ochenio� dictatorial del general Manuel A. Odr�a. Unas cuantas cervezas y un r�o de palabras en libertad para responder a la palabra amordazada por la dictadura. Conversaci�n en La Catedral no es, sin embargo, una novela hist�rica. Sus personajes, las historias que �stos cuentan, los fragmentos que van encajando, conforman la descripci�n minuciosa de un envilecimiento colectivo, el repaso de todos los caminos que hacen desembocar a un pueblo entero en la frustraci�n. Conversaci�n en La Catedral es algo m�s que un hito en el derrotero literario de Mario Vargas Llosa: es un punto de referencia insoslayable, un dato fijo en la historia de la literatura actual.','Mario Vargas Llosa','conversacionenlacatedral','4')");
        	createTable("conversacionenlacatedral","Mario Vargas Llosa","Conversaci�n en La Catedral","Zavalita y el zambo Ambrosio conversan en La Catedral. Estamos en Per�, durante el �ochenio� dictatorial del general Manuel A. Odr�a. Unas cuantas cervezas y un r�o de palabras en libertad para responder a la palabra amordazada por la dictadura. Conversaci�n en La Catedral no es, sin embargo, una novela hist�rica. Sus personajes, las historias que �stos cuentan, los fragmentos que van encajando, conforman la descripci�n minuciosa de un envilecimiento colectivo, el repaso de todos los caminos que hacen desembocar a un pueblo entero en la frustraci�n. Conversaci�n en La Catedral es algo m�s que un hito en el derrotero literario de Mario Vargas Llosa: es un punto de referencia insoslayable, un dato fijo en la historia de la literatura actual.");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('Los Perros Hambrientos','En sus p�ginas late el drama del hombre y la tierra, de la sequ�a y el hambre, aproxim�ndose al mayor problema del hombre peruano: la propiedad de la tierra, su lucha diaria por vencer la agreste naturaleza y la dificultad de las relaciones humanas entre las desproporciones que determinan los papeles antag�nicos de agua-sequ�a, blanco-indio, pobreza-riqueza, cosmovisi�n occidental- cosmovisi�n andina. Todo ello estableciendo dos mundos paralelos, el de los hombres y el de los perros, que ante el hambre no dudan en matarse devorarse entre ellos.','Ciro Alegria','losperroshambrientos','5')");
        	createTable("losperroshambrientos","Ciro Alegria"," Los Perros Hambrientos"," En sus p�ginas late el drama del hombre y la tierra, de la sequ�a y el hambre, aproxim�ndose al mayor problema del hombre peruano: la propiedad de la tierra, su lucha diaria por vencer la agreste naturaleza y la dificultad de las relaciones humanas entre las desproporciones que determinan los papeles antag�nicos de agua-sequ�a, blanco-indio, pobreza-riqueza, cosmovisi�n occidental- cosmovisi�n andina. Todo ello estableciendo dos mundos paralelos, el de los hombres y el de los perros, que ante el hambre no dudan en matarse devorarse entre ellos.");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('La Ciudad y los Perros','La ciudad y los perros no solamente es un ataque contra la crueldad ejercida a un grupo de j�venes alumnos del Colegio Militar Leoncio Prado, sino tambi�n una cr�tica frontal al concepto err�neo de la virilidad, de sus funciones y de las consecuencias de una educaci�n castrense malentendida. Aunada a la brutalidad propia de la vida militar, a lo largo de las p�ginas de esta extraordinaria novela, la vehemencia y la pasi�n de la juventud se desbocan hasta llegar a una furia, una rabia y un fanatismo que anulan toda sensibilidad.','Mario Vargas Llosa','laciudadylosperros','5')");
        	createTable("laciudadylosperros","Mario Vargas Llosa"," La Ciudad y los Perros"," La ciudad y los perros no solamente es un ataque contra la crueldad ejercida a un grupo de j�venes alumnos del Colegio Militar Leoncio Prado, sino tambi�n una cr�tica frontal al concepto err�neo de la virilidad, de sus funciones y de las consecuencias de una educaci�n castrense malentendida. Aunada a la brutalidad propia de la vida militar, a lo largo de las p�ginas de esta extraordinaria novela, la vehemencia y la pasi�n de la juventud se desbocan hasta llegar a una furia, una rabia y un fanatismo que anulan toda sensibilidad.");
        	
        	bd.execSQL("INSERT INTO publicacion VALUES ('Las Aventuras de Huckleberry Finn','Huckleberry Finn, el amigo de Tom Sawyer, es un muchacho aventurero al que no le gusta la escuela sino andar por ah� a su aire. Sin embargo, la vida de Huck no es f�cil, su padre lo maltrata y tiene que vivir con la viuda Douglas, una mujer que se ha propuesto civilizarlo. Huck decide escapar de todo y emprende un viaje por el r�o Misisipi. En su huida le acompa�ar� Jim, un esclavo negro que busca su libertad. Entre lo dos se forjar� una especial amistad.','Mark Twain','aventurasdehuckleberryfinn','5')");
        	createTable("aventurasdehuckleberryfinn","Mark Twain"," Las Aventuras de Huckleberry Finn","Huckleberry Finn, el amigo de Tom Sawyer, es un muchacho aventurero al que no le gusta la escuela sino andar por ah� a su aire. Sin embargo, la vida de Huck no es f�cil, su padre lo maltrata y tiene que vivir con la viuda Douglas, una mujer que se ha propuesto civilizarlo. Huck decide escapar de todo y emprende un viaje por el r�o Misisipi. En su huida le acompa�ar� Jim, un esclavo negro que busca su libertad. Entre lo dos se forjar� una especial amistad.");

        	bd.execSQL("INSERT INTO publicacion VALUES ('Las Aventuras de Tom Sawyer','No hay nadie como Tom Sawyer para conseguir que sus amigos trabajen por �l sin que se den cuenta, para realizar conjuros que no siempre funcionan, para meterse en l�os y salir bien parado, para buscar tesoros junto a su gran amigo Huckleberry, o para formar una banda de peligrosos forajidos. Estas son sus incre�bles aventuras, una de las obras maestras de Mark Twain','Mark Twain','lasaventurasdetomsawyer','5')");
        	createTable("lasaventurasdetomsawyer","Mark Twain"," Las Aventuras de Tom Sawyer"," No hay nadie como Tom Sawyer para conseguir que sus amigos trabajen por �l sin que se den cuenta, para realizar conjuros que no siempre funcionan, para meterse en l�os y salir bien parado, para buscar tesoros junto a su gran amigo Huckleberry, o para formar una banda de peligrosos forajidos. Estas son sus incre�bles aventuras, una de las obras maestras de Mark Twain");
        	
    	}
    
}
    