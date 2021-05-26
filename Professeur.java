import java.awt.Color;
import java.awt.Graphics;

public class Professeur extends Personne {
    
    public Professeur (int id, boolean contam , int rayon) {
		super (id, contam, rayon);
        
    }
    
    public void dessine(Graphics g){
		if ( contamine == true){
			g.setColor(Color.red);
			g.drawOval((int)(x-(rayonCont/2)),(int)(y-(rayonCont/2)),(int)rayonCont,(int)rayonCont);
		}else if(immunise == true){ 
			g.setColor(Color.blue);
		} else { 
			g.setColor(Color.green);
		}
        g.fillRect(x-5,y-5,10,10);
	}
    
}
