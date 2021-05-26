import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.util.Random;
import java.io.*;



public class MaFenetreINSALERTE extends JFrame implements ChangeListener, ActionListener {
			
		// Les Widgets à déclarer en dehors du constructeur
		protected JTextField textChoix;
		protected JSlider rayonContamination; 
		protected JSlider tempsPourContamine;
		protected JSlider tauxMortalite;
		protected JSlider dureeContamination;
		protected JSlider nbrContamineInit;
		protected JSlider nbrPersInit;
		protected JSlider tempsImmun;
		protected JButton monBoutonPause;
		protected JButton monBoutonPlay;
		protected JButton monBontonReini;
		protected ArrayList<Personne> maListe;
		protected Timer temps;
		protected int tempsEcoul;
		protected MaFenetrePanel map ;
		protected JLabel nbInitPers;
		protected JLabel dureeCont;
		protected JLabel nbInit;
		protected JLabel txMort;
		protected JLabel prbCont;
		protected JLabel raCont;
		protected JLabel tmpsImmun;
		protected EtatSimulation simulation;
		protected JLabel valTempsReel;
		protected JPanel affVal;
		protected JLabel avertissement;
		BufferedWriter fichier;
		
	public MaFenetreINSALERTE(ArrayList<Personne> maListePersonne){ // fenetre principale
		try{
				fichier = new BufferedWriter(new FileWriter("test.txt"));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize(); //utilisation du toolkit importe afin d'adapter l'affichage de la fenetre a tout type d'ecran
        final double P_ECRAN = 19/20.;
        this.setBounds(0,0,(int)(tailleEcran.width * P_ECRAN),(int)(tailleEcran.height* P_ECRAN));
		maListe = maListePersonne;
		this.setTitle("INSAlerte COVID");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		temps = new Timer(20, this);
		tempsEcoul = 0;
		valTempsReel = new JLabel();
		avertissement = new JLabel();
		
		//conteneur de reglage des parametres (bande dans la partie superieure de l'ecran
		JPanel panneauChoix = new JPanel(); 
		panneauChoix.setBounds(0,0,this.getWidth(),(int) (this.getHeight()/3.2));
		panneauChoix.setLayout(null);
		panneauChoix.setBackground(new Color (255,217,76));
		
		//conteneur contenant la legende 
		FenetreLegende panneauLegende = new FenetreLegende(); 
		panneauLegende.setBounds(0,(int) (this.getHeight()/2.5),(int) (this.getWidth()/4.28571428571),(int) (this.getHeight()/1.56));
		FenetrePresentation panPresentation = new FenetrePresentation();
		panPresentation.setBounds(0,0,(int) (this.getWidth()/4.28571428571),(int) (this.getHeight()/3.2));
		JLabel affLogo = new JLabel(new ImageIcon("logoinsa.PNG")); 
		affLogo.setBounds(0,0,(int) (this.getWidth()/4.28571428571),(int) (this.getHeight()/11));
		this.add(panPresentation);	
		this.add(panneauLegende);
		this.add(affLogo);
		
		//panneau d'affichage de la population (ensemble de points)
		affVal = new JPanel(); 
		affVal.setBounds(0,(int) (this.getHeight()/3.2),(int) (this.getWidth()/4.28571428571),(int) (this.getHeight()/1.56));
		affVal.setBackground(new Color (255,255,255));
		this.add(affVal);
		affVal.add(valTempsReel);
		affVal.add(avertissement);
		affVal.validate();
		
		//Bouton pause
		monBoutonPause = new JButton("Pause");
		monBoutonPause.setBounds((int)(panneauChoix.getWidth()/1.1),(int)(panneauChoix.getHeight()/25),(int)(panneauChoix.getWidth()/14),(int)(panneauChoix.getHeight()/3.571428571));
		monBoutonPause.setBackground(new Color(255,117,3));
		monBoutonPause.addActionListener(this);
		panneauChoix.add(monBoutonPause);
		
		//Bouton de reinitialisation de l'affichage
		monBontonReini = new JButton("Reinitialise");
		monBontonReini.setBounds((int)(panneauChoix.getWidth()/1.1),(int)(panneauChoix.getHeight()/1.470588235),(int)(panneauChoix.getWidth()/14),(int)(panneauChoix.getHeight()/3.571428571));
		monBontonReini.setBackground(new Color(242,0,0));
		monBontonReini.addActionListener(this);
		panneauChoix.add(monBontonReini);
		
		
		//Bouton replay
		monBoutonPlay = new JButton("Play");
		monBoutonPlay.setBounds((int)(panneauChoix.getWidth()/1.1),(int)(panneauChoix.getHeight()/2.77777778),(int)(panneauChoix.getWidth()/14),(int)(panneauChoix.getHeight()/3.571428571));
		monBoutonPlay.setBackground(new Color(0,255,0));
		monBoutonPlay.addActionListener(this);
		panneauChoix.add(monBoutonPlay);
		
		
		//Curseur du rayon de contamination
		rayonContamination = new JSlider(JSlider.HORIZONTAL,0, 500, 0);   //Creation//JSlider curseur = new JSlider(min, max, valeurInitiale)
		rayonContamination.setBounds((int)(panneauChoix.getWidth()/2.15),(int)(panneauChoix.getHeight()/8.3333333333),(int)(panneauChoix.getWidth()/5),(int)(panneauChoix.getHeight()/5));
		rayonContamination.setPaintTrack(true); 
        rayonContamination.setPaintTicks(true); 
        rayonContamination.setPaintLabels(true);
        rayonContamination.setMajorTickSpacing(50); 
        rayonContamination.setMinorTickSpacing(10); 
		rayonContamination.addChangeListener(this);
		raCont = new JLabel();
		raCont.setText("Entrez le rayon de contamination : "+rayonContamination.getValue());
		raCont.setBounds((int)(panneauChoix.getWidth()/2.15),(int)(panneauChoix.getHeight()/25),(int)(panneauChoix.getWidth()/4.411764706),(int)(panneauChoix.getHeight()/12.5));
		panneauChoix.add(raCont);
		panneauChoix.add(rayonContamination);
		
		//Curseur du temps de contamination
		tempsPourContamine = new JSlider(JSlider.HORIZONTAL,0, 20000, 0);  
		tempsPourContamine.setBounds((int)(panneauChoix.getWidth()/2.15),(int)(panneauChoix.getHeight()/2.2727272727), (int)(panneauChoix.getWidth()/5),(int)(panneauChoix.getHeight()/5));
		tempsPourContamine.setPaintTrack(true); 
        tempsPourContamine.setPaintTicks(true); 
        tempsPourContamine.setPaintLabels(true);
        tempsPourContamine.setMajorTickSpacing(3000); 
        tempsPourContamine.setMinorTickSpacing(1000); 
		tempsPourContamine.addChangeListener(this);
		prbCont = new JLabel();
		prbCont.setText("Entrez le temps mis pour contamine : "+tempsPourContamine.getValue());
		prbCont.setBounds((int)(panneauChoix.getWidth()/2.15),(int)(panneauChoix.getHeight()/2.77777778),(int)(panneauChoix.getWidth()/4.411764706),(int)(panneauChoix.getHeight()/12.5));
		panneauChoix.add(prbCont);
		panneauChoix.add(tempsPourContamine);
		
		//curseur du taux de mortalite
		tauxMortalite = new JSlider(JSlider.HORIZONTAL,0, 100, 0);  //JSlider curseur = new JSlider(min, max, valeurInitiale)
		tauxMortalite.setBounds((int)(panneauChoix.getWidth()/2.15),(int)(panneauChoix.getHeight()/1.315789474), (int)(panneauChoix.getWidth()/5),(int)(panneauChoix.getHeight()/5));
		tauxMortalite.setPaintTrack(true); 
        tauxMortalite.setPaintTicks(true); 
        tauxMortalite.setPaintLabels(true);
        tauxMortalite.setMajorTickSpacing(10); 
        tauxMortalite.setMinorTickSpacing(5); 
		tauxMortalite.addChangeListener(this);
		txMort = new JLabel();
		txMort.setText("Entrez le taux de mortalite : "+tauxMortalite.getValue());
		txMort.setBounds((int)(panneauChoix.getWidth()/2.15),(int)(panneauChoix.getHeight()/1.470588235),(int)(panneauChoix.getWidth()/4.411764706),(int)(panneauChoix.getHeight()/12.5));
		panneauChoix.add(txMort);
		panneauChoix.add(tauxMortalite);
		
		//Curseur du nombre de contaminees
		nbrContamineInit = new JSlider(JSlider.HORIZONTAL,0, 1000, 0);  //JSlider curseur = new JSlider(min, max, valeurInitiale)
		nbrContamineInit.setBounds((int)(panneauChoix.getWidth()/4.054054),(int)(panneauChoix.getHeight()/8.333333333333),(int)(panneauChoix.getWidth()/5),(int)(panneauChoix.getHeight()/5));
		nbrContamineInit.setPaintTrack(true); 
        nbrContamineInit.setPaintTicks(true); 
        nbrContamineInit.setPaintLabels(true);
        nbrContamineInit.setMajorTickSpacing(100); 
        nbrContamineInit.setMinorTickSpacing(50); 
		nbrContamineInit.addChangeListener(this);
		nbInit = new JLabel();
		nbInit.setText("Entrez le nombre de contamines initial : "+nbrContamineInit.getValue());
		nbInit.setBounds((int)(panneauChoix.getWidth()/4.054054),(int)(panneauChoix.getHeight()/25),(int)(panneauChoix.getWidth()/4.411764706),(int)(panneauChoix.getHeight()/12.5));
		panneauChoix.add(nbInit);
		panneauChoix.add(nbrContamineInit);
		
		//Curseur de la taille de la population
		nbrPersInit = new JSlider(JSlider.HORIZONTAL,0, 1000, 0);  //JSlider curseur = new JSlider(min, max, valeurInitiale)
		nbrPersInit.setBounds((int)(panneauChoix.getWidth()/4.054054),(int)(panneauChoix.getHeight()/2.27272727272727),(int)(panneauChoix.getWidth()/5),(int)(panneauChoix.getHeight()/5));
		nbrPersInit.setPaintTrack(true); 
        nbrPersInit.setPaintTicks(true); 
        nbrPersInit.setPaintLabels(true);
        nbrPersInit.setMajorTickSpacing(100); 
        nbrPersInit.setMinorTickSpacing(50); 
		nbrPersInit.addChangeListener(this);
		nbInitPers = new JLabel();
		nbInitPers.setText("Entrez le nombre de personnes initial : "+nbrPersInit.getValue());
		nbInitPers.setBounds((int)(panneauChoix.getWidth()/4.054054),(int)(panneauChoix.getHeight()/2.77777777778),(int)(panneauChoix.getWidth()/4.411764706),(int)(panneauChoix.getHeight()/12.5));
		panneauChoix.add(nbInitPers);
		panneauChoix.add(nbrPersInit);
		
		//Curseur de la duree de contamination
		dureeContamination = new JSlider(JSlider.HORIZONTAL,0, 20000, 0);  //JSlider curseur = new JSlider(min, max, valeurInitiale)
		dureeContamination.setBounds((int)(panneauChoix.getWidth()/4.054054),(int)(panneauChoix.getHeight()/1.315789474),(int)(panneauChoix.getWidth()/5),(int)(panneauChoix.getHeight()/5));
		dureeContamination.setPaintTrack(true); 
        dureeContamination.setPaintTicks(true); 
        dureeContamination.setPaintLabels(true);
        dureeContamination.setMajorTickSpacing(3000); 
        dureeContamination.setMinorTickSpacing(1000); 
		dureeContamination.addChangeListener(this);
		dureeCont = new JLabel();
		dureeCont.setText("Entrez un temps de contamination  : "+dureeContamination.getValue());
		dureeCont.setBounds((int)(panneauChoix.getWidth()/4.054054),(int)(panneauChoix.getHeight()/1.470588235),(int)(panneauChoix.getWidth()/4.411764706),(int)(panneauChoix.getHeight()/12.5));
		panneauChoix.add(dureeCont);
		panneauChoix.add(dureeContamination);
		
		//Curseur du temps d'immunisation
		tempsImmun = new JSlider(JSlider.HORIZONTAL,0, 20000, 0);  //JSlider curseur = new JSlider(min, max, valeurInitiale)
		tempsImmun.setBounds((int)(panneauChoix.getWidth()/1.46484375),(int)(panneauChoix.getHeight()/8.3333333333),(int)(panneauChoix.getWidth()/5),(int)(panneauChoix.getHeight()/5));
		tempsImmun.setPaintTrack(true); 
        tempsImmun.setPaintTicks(true); 
        tempsImmun.setPaintLabels(true);
        tempsImmun.setMajorTickSpacing(3000); 
        tempsImmun.setMinorTickSpacing(1000); 
		tempsImmun.addChangeListener(this);
		tmpsImmun = new JLabel();
		tmpsImmun.setText("Entrez les temps d'immunisation :  "+tempsImmun.getValue());
		tmpsImmun.setBounds((int)(panneauChoix.getWidth()/1.46484375),(int)(panneauChoix.getHeight()/25),(int)(panneauChoix.getWidth()/4.411764706),(int)(panneauChoix.getHeight()/12.5));
		panneauChoix.add(tmpsImmun);
		panneauChoix.add(tempsImmun);

		map = new MaFenetrePanel(maListe);
		map.setBounds((int) (this.getWidth()/4.285714286),(int)(this.getHeight()/3.2),(int) (this.getWidth()/1.304347826),(int) (this.getHeight()/1.56));
		
		this.add(map);
		this.add(panneauChoix);		
		
		// Pour rendre la fenetre visible
		this.setVisible(true);
		
	}
	
	
	public void actionPerformed (ActionEvent e){ //event
		if (e.getSource()== monBoutonPause){                     // interaction avec le bouton pause 
			temps.stop();                                        //arrete le temps 
		}
		
		if (e.getSource() == monBoutonPlay){                   // interaction avec le bouton play 
			if (nbrContamineInit.getValue()<=nbrPersInit.getValue()) {		// test si le nombre de personne contamine est inferieur au nombre de personne total  
				avertissement.setText("");
				temps.start();                                             // lancement du timer 
			}else {
				
				avertissement.setText("<html>Veuillez entrer au moins autant de personnes initiales<br> que de personnes contaminees ");   //creation avertissement 
				affVal.add(avertissement);                                                                                                  // affichage avertissement 
				affVal.validate();
			}
			
		}
		if(e.getSource() == temps){                 // interation avec le timer 
			
			if( tempsEcoul==0){                           // si notre compteur de temps est a 0
					simulation = new EtatSimulation(nbrContamineInit.getValue(), nbrPersInit.getValue(),  tauxMortalite.getValue(), 
												rayonContamination.getValue(), tempsPourContamine.getValue(),dureeContamination.getValue(),
												tempsImmun.getValue());       // recuperation de toute les valeur des curseur
				
			}
			simulation.mourir(tempsEcoul);                 // mise a jour des attribu de chaque point a l'aide des different methodes 
			simulation.guerison(tempsEcoul);
			simulation.finImmun(tempsEcoul);
			map.maListe2=simulation.listePersonne;
			if(tempsEcoul ==0){                          
				for (Personne unePersonne : map.maListe2){                    // si notre compteur de temps est a 0
					unePersonne.x =(int)(Math.random()*(map.getWidth()-10));   // placement des personnes 
					unePersonne.y =(int)(Math.random()*(map.getHeight()-10));  		
				}
			}
			simulation.contamination(tempsEcoul);
			simulation.majListeContact(tempsEcoul);
			
			map.repaint();
			int l = 0;
			int j = 0;
			int k = 0;
			
			
			int X=1;
			for (Personne unePersonne : map.maListe2){
				if(tempsEcoul%100==0&&!unePersonne.vaccine&&X!=0&&!unePersonne.immunise&&!unePersonne.contamine && !unePersonne.estMort) {
					if(Math.random()<0.9){
						unePersonne.immunise=true;
					}
					unePersonne.vaccine=true;
					X--;
				}
			}
							
			
			
			for (Personne unePersonne : map.maListe2){
				unePersonne.deplacement(map.getWidth(),map.getHeight());   // actualisation de le position des personnes 
				if(!unePersonne.immunise&&!unePersonne.contamine && !unePersonne.estMort) {           // compte du nombre de personne contamine
					j++;
				}
				if(unePersonne.contamine && !unePersonne.estMort) {           // compte du nombre de personne contamine
					l++;
				} if (unePersonne.immunise) {     // compte du nombre de "immunise" 
					k++;
				}
			}
			tempsEcoul=tempsEcoul+20;        // augmentation de notre compteur de temps 
			valTempsReel.setText("<html>Le nombre de personne sante est :"+j+"<br>Le nombre de personnes contaminees est :"+l+
							"<br>Le nombre d'immunise est :"+k+ "Le temps ecoule est : "+tempsEcoul) ;        // creation de la legende permetant de connaitre le nombre de mort et de contamine
			
			
			
			
			
			
			
			
			
			
			
			try{
            fichier.write(Integer.toString(j)+";");
            fichier.write(Integer.toString(tempsEcoul)+";");
            fichier.write(Integer.toString(k)+";");
            fichier.write(Integer.toString(tempsEcoul)+";");
            fichier.write(Integer.toString(l)+";");
            fichier.write(Integer.toString(tempsEcoul)+";");
            fichier.newLine();
            fichier.flush(); // on force l'ecriture sur disque pendant l'execution
            }
            catch (Exception excep) {
                excep.printStackTrace();
            }
			
			
			
			
			
			
			
			
			
			
			
			
			affVal.add(valTempsReel);                                 // affichage de celle ci 
			affVal.validate();
		}
		if(e.getSource() == monBontonReini){                          //interaction avec le bouton reinitilise 
			temps.stop();                                             // arret du timer 
			tempsPourContamine.setValue(0);                           // mise a 0 de toutes les valeur des curseurs 
			nbrPersInit.setValue(0);
			rayonContamination.setValue(0);
			tauxMortalite.setValue(0);
			nbrContamineInit.setValue(0);
			dureeContamination.setValue(0);
			tempsImmun.setValue(0);
			simulation.listePersonne.clear();
			map.maListe2.clear();
			dureeCont.setText( "Entrez le temps de contamination en jour : " +dureeContamination.getValue());    //mise a 0 des curseurs (notament sur la partit graphique) 
			nbInitPers.setText( "Entrez le nombre de personnes initial : " +nbrPersInit.getValue());
			prbCont.setText("Entrez le temps mis pour contamine : "+tempsPourContamine.getValue());
			txMort.setText("Entrez le taux de mortalite : "+tauxMortalite.getValue());
			raCont.setText("Entrez le rayon de contamination : "+rayonContamination.getValue());
			nbInit.setText("Entrez le nombre de contamines initial : "+nbrContamineInit.getValue());
			tmpsImmun.setText("Entrez les temps d'immunisation :  "+tempsImmun.getValue());
			valTempsReel.setText("");
			avertissement.setText("");
			map.repaint();
			tempsEcoul=0;                                               // mise a 0 de notre compteur temps 
			
		}
		 
	}
	public void stateChanged(ChangeEvent e){                            // interaction avec les curseur  
		if (e.getSource()== nbrPersInit && tempsEcoul == 0) {                             // test pour savoir quel curseur a ete modifie et si le programme a bien ete rehinitialise
			nbInitPers.setText( "Entrez le nombre de personnes initial : " +nbrPersInit.getValue());      // recuperation de la valeur du curseur 
		}	
		if (e.getSource()== rayonContamination && tempsEcoul == 0) {
			raCont.setText("Entrez le rayon de contamination : "+rayonContamination.getValue());
		}	
		if (e.getSource()== tempsPourContamine && tempsEcoul == 0) {
			prbCont.setText("Entrez le temps mis pour contamine : "+tempsPourContamine.getValue());
		}	
		if (e.getSource()== tauxMortalite && tempsEcoul == 0) {
			txMort.setText("Entrez le taux de mortalite : "+tauxMortalite.getValue());
		}	
		if (e.getSource()== nbrContamineInit && tempsEcoul == 0) {
			nbInit.setText("Entrez le nombre de contamines initial : "+nbrContamineInit.getValue());
		}	
		if (e.getSource()== dureeContamination && tempsEcoul == 0) {
			dureeCont.setText( "Entrez le temps de contamination en jour : " +dureeContamination.getValue());
		}	
		if (e.getSource()== tempsImmun && tempsEcoul == 0) {
			tmpsImmun.setText("Entrez les temps d'immunisation :  "+tempsImmun.getValue());
		}	
			
	}
			
}
