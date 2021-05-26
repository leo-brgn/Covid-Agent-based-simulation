import java.lang.Math;
import java.util.ArrayList;



public class EtatSimulation {
	protected int nombreContamine;
	protected int nombreTotalGens;
	protected int tauxContamination;
	protected int tauxMortal;
	protected int rayCont;
	protected int tmpsPourContamine;
	protected int durCont;
	protected int tempsPourContamine;
	protected int tempsImmunise;
	protected ArrayList < Personne> listePersonne;
	
	
	public  EtatSimulation (int nbCont , int nbrPersonne , int tauxMort, int rCont, int tmpsPrCont, int tmpsCont, int tmpsImmunise){
		
		nombreTotalGens = nbrPersonne;
		nombreContamine = nbCont;
		tauxMortal = tauxMort;
		rayCont = rCont;
		tmpsPourContamine = tmpsPrCont;
		durCont = tmpsCont;
		tempsImmunise = tmpsImmunise;
		listePersonne = new ArrayList < Personne > () ;
		tempsPourContamine = 10;		
		for ( int i = 0 ; i < nombreTotalGens - nombreContamine ; i ++ ) {
			Personne p = GenerePersonne (i, false) ;
			 listePersonne.add( p ) ;
		}
		for ( int i = nombreTotalGens - nombreContamine ; i < nombreTotalGens ; i ++ ) {
			Personne p = GenerePersonne (i, true) ;
			 listePersonne.add( p ) ;
		}
	
	
	}
	
	public Personne GenerePersonne (int id, boolean cont) {
		double typePersonne = Math.random();
		if ( typePersonne < 0.1){
			Agent p = new Agent(id, cont,rayCont);//modifier les arguments lors de la creation de la liste!!
			return p;
		}else{ 
			if ( typePersonne < 0.25 ){
				Professeur p = new Professeur (id, cont,rayCont);
				return p;
			}else{ 
				Etudiant p = new Etudiant (id, cont,rayCont);
				return p;
			}
		}
	}
	  
		   
	
	public void contamination (int temps){
		for ( Personne unePersonne : listePersonne){
			if ( (unePersonne.contamine == false) && (unePersonne.immunise == false) ){
				for (Contact unContact : unePersonne.listContact)
					if (unContact.duree>tmpsPourContamine) {
						unePersonne.contamine = true;
						unePersonne.dateContamination = temps;
					}
			}
				
		}
	}
	
	public void majListeContact(int temps) {
		for ( Personne unePersonne : listePersonne){
			if (unePersonne.contamine == false && !unePersonne.estMort){
				for ( Personne unePersonne2 : listePersonne){
					
					if ( unePersonne.estProche(unePersonne2, rayCont)==true && unePersonne2.contamine == true && !unePersonne2.estMort ){
						unePersonne.majUnCasContact(temps, unePersonne2);
					}else {
						Contact unContact = unePersonne.getPersonneDejaContact(unePersonne2);
						if(unContact!=null) {
							unePersonne.retirerUnCasContact(unContact);
						}

					}
				}
			}
		}
	}
	
	
	public void mourir(int temps){
		for ( int i =listePersonne.size()-1; i>=0; i--){
			int chance = (int) (Math.random()*100);
			if ( (listePersonne.get(i).contamine == true) && (temps - listePersonne.get(i).dateContamination >= durCont) && (chance < tauxMortal) && !listePersonne.get(i).estMort){
				listePersonne.get(i).estMort = true;
				listePersonne.get(i).immunise = false;
				listePersonne.get(i).contamine = false;
				
			}
		}
	}	

	public void guerison(int temps){//faire parcourir la liste et actualisee a chaque fois si contamine ou non en posant condition sur contamination= true
		for ( int i =0; i<listePersonne.size(); i++){
			if ((temps - listePersonne.get(i).dateContamination)> durCont && listePersonne.get(i).contamine == true && !listePersonne.get(i).estMort){
	            listePersonne.get(i).contamine = false;
	            listePersonne.get(i).immunise = true;   
			}
        }
   }
   
   public void finImmun(int temps){//faire parcourir la liste et actualiser a chaque fois si contamine ou non en posant condition sur contamination= true
		for ( int i =0; i<listePersonne.size(); i++){
			if ((temps - listePersonne.get(i).dateContamination)> (durCont+tempsImmunise) && listePersonne.get(i).immunise == true && !listePersonne.get(i).estMort){
				listePersonne.get(i).immunise = false;   
			}
        }
   }
	
}
	

