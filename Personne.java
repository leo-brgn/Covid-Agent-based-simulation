import java.lang.Math;
import java.awt.*;
import java.util.ArrayList;

public abstract class Personne {
	
	
	protected int idPersonne; //ATTRIBUTS
    protected String numTel;
    protected int rayonCont;
    protected double dateContamination;
    protected boolean immunise;
    protected boolean contamine;
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected double proba;
    protected ArrayList < Contact> listContact;
    protected boolean estMort;
    protected boolean vaccine;
   
    
    public  Personne (int id, boolean contam, int rayon) {
    	idPersonne = id;
        immunise = false;
        rayonCont = rayon;
        estMort = false;
        dateContamination = 0;       //initilisation a -1 car non contamine
        contamine = contam;
        immunise = false;
        proba = Math.random()*2;
        dx = (int) ((Math.random() * 10.0 - 5.0) ) ; 
		dy = (int) ((Math.random() * 10.0 - 5.0)  ) ;
		listContact = new ArrayList<Contact>();
		vaccine = false;
    }
    
    
    public void deplacement (int taillex, int tailley) {
    	if( x+dx>0 && x+dx<taillex-10 && y+dy>0 && y+dy<tailley-10 && dx!=0 && dy!=0){
			x = x+dx;
			y = y+dy;
		} else {
	        dx = (int) ((Math.random() * 10.0 - 5.0) ) ;  // TODO 500 constante !!!!
			dy = (int) ((Math.random() * 10.0 - 5.0)  ) ;  // TODO 500 constante !!!!
		}
    }
    

    public boolean estProche (Personne unGens, int rayon1){
        boolean b = false;
        if (Math.sqrt(((x-(unGens.x))*(x-(unGens.x)))+((y-(unGens.y))*(y-(unGens.y)))) < rayon1){//mettre rayon de contamination 
            b = true;
        }
        return b;
    }    
    
    public boolean estLoin (Personne unGens, int rayon1){
        boolean b = false;
        if (Math.sqrt(((x-(unGens.x))*(x-(unGens.x)))+((y-(unGens.y))*(y-(unGens.y)))) > rayon1){//mettre rayon de contamination 
            b = true;
        }
        return b;
    }   
    
    public abstract void dessine(Graphics g);
    
    public void majUnCasContact(int now, Personne unePersonne) {
    	Contact monContact = getPersonneDejaContact(unePersonne);
    	if(monContact!=null) {
    		monContact.majDuree(now);
    	} else {
    		Contact unContact = new Contact(unePersonne, now);
    		listContact.add(unContact);
    	}
    }
    public void retirerUnCasContact(Contact unContact) {
    	listContact.remove(unContact);
    }
    
    public Contact getPersonneDejaContact(Personne unePersonne) {
    	for ( Contact unContact : listContact){
    		if(unContact.personneCont.equals(unePersonne)){
    			return unContact;
    		}else{
    			return null;
    		}
    	}
    	return null;
    }
    
    
    public boolean equals(Personne o) {
        if (this.idPersonne == o.idPersonne) {
        	return true;
        } else {
        	return false;
        }
    }
    
    
}

    
    
