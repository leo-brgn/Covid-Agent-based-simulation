 import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;


public class FenetreLegende extends JPanel{
	
	public void FenetreLegende(){                                       // constructeur de FenetreLegende
		setBackground(new Color (255,255,255));    
	}
	
	public void paint(Graphics g){                              //methode qui permet le dessin des element de la legende
		
		g.setColor(Color.BLACK);                                         //definition de la couleur de l'object graphique (noir)
		g.drawString("LEGENDE :",5, (int)(this.getHeight())/30);
		g.fillOval((int)(this.getWidth()/4), (int)(this.getHeight()/10.7), 10 ,10);
		g.drawString("ETUDIANTS", (int)(this.getWidth()/3), (int)(this.getHeight()/9));
		g.fillRect((int)(this.getWidth()/4), (int)(this.getHeight()/5.5), 10 ,10);          //dessin et positionement rectangle 
		g.drawString("PROFESSEURS",(int)(this.getWidth()/3), (int)(this.getHeight()/5));  //ecriture de la legende professeur et positionement de celle ci 
		int [] x = {(int)(this.getWidth()/3.8),(int)(this.getWidth()/3.8)+4,(int)(this.getWidth()/3.8)-4};
        int [] y = {(int)(this.getHeight()/3.7),(int)(this.getHeight()/3.5),(int)(this.getHeight()/3.5)};
		Polygon p = new Polygon(x, y,x.length );
		g.drawPolygon(p);
		g.fillPolygon(p);                                                                    //dessin du triangle 
		g.drawString("AGENTS", (int)(this.getWidth()/3), (int)(this.getHeight()/3.5));
		g.setColor(Color.GREEN);
		g.drawString("Sain", 100, 415);
		g.setColor(Color.BLUE);
		g.drawString("Immunise", 100, 515);
		g.setColor(Color.RED);
		g.drawString("Contamine", 100, 615);
		
		
	}
	
}
