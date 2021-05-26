import java.awt.Color;
import java.awt.Graphics;

public class Etudiant extends Personne {
        
    public Etudiant (int id, boolean contam , int rayon) {
		super (id, contam, rayon);
        rayonCont = rayon;
    }
    
    public void dessine(Graphics g){
		
		if ( contamine == true){
			g.setColor(Color.red);
			g.drawOval((int)(x-(rayonCont/2)),(int)(y-(rayonCont/2)),rayonCont,rayonCont);
		} else if(immunise == true){ 
			g.setColor(Color.blue);
		} else{
			g.setColor(Color.green);
		}
		g.fillOval(x-5,y-5,10,10);        
		
    }
}
