import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;


public class FenetrePresentation extends JLabel{
	
	public void FenetrePresentation(){  //constructeur de la fenetre
		
		setBackground(new Color (255,255,255));                // definition couleur du fond 

	}
	
	public void paint(Graphics g){
		g.setColor(Color.BLACK);                                         //definition de la couleur de l'object graphique (noir)
		g.drawString("BIENVENUE DANS NOTRE SIMULATEUR DE VIRUS !!!", (int)(this.getWidth()/10),(int)(this.getHeight()/3));     //ecriture et postionement des differents textes  
		g.drawString("(Veuillez entrer les caracteristiques du virus avant   ",(int)(this.getWidth()/10) ,(int)(this.getHeight()/2));
		g.drawString("de cliquer sur Play)",(int)(this.getWidth()/3),(int)(this.getHeight()/1.5));
		g.drawString("Ce simulateur vous est propose par Antonin, Saren",(int)(this.getWidth()/10),(int)(this.getHeight()/1.3));
		g.drawString(" Hoel et Adrien !",(int)(this.getWidth()/2.2),(int)(this.getHeight()/1.1));
	
	}

}

