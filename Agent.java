import java.awt.Color;
import java.awt.Graphics;

public class Agent extends Personne {
    public Agent (int id, boolean contam, int rayon) {
        super (id, contam, rayon);
    }
    //dessine l'agent selon son état
    public void dessine(Graphics g) {
        
		if (contamine == true) { 
			g.setColor(Color.red);           //rouge si contaminé
			g.drawOval((int)(x-(rayonCont/2)),(int)(y-(rayonCont/2)),rayonCont,rayonCont);
		} else if (immunise == true) { 
			g.setColor(Color.blue);          //bleu si immunisé
		} else { 
			g.setColor(Color.green);         //vert si état normal
		}
        g.fillRect(x-5,y-5,10,10);
    }
}
