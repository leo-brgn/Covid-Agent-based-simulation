import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MaFenetrePanel extends JLabel {
	
	protected ArrayList<Personne> maListe2;
	
	public MaFenetrePanel(ArrayList<Personne> maListePersonne){ 
		maListe2 = maListePersonne;
		JPanel game = new JPanel(); 
		game.setLayout(null);
		
		
	}

	public void paint(Graphics g){ //methode paint permettant d'afficher en temps reel la population de professeurs
		g.setColor(new Color (250,250,250));
		g.fillRect(0,0, this.getWidth(), this.getHeight());
            for (int i = 0;i<maListe2.size();i++){
            	if(!maListe2.get(i).estMort)
            		maListe2.get(i).dessine(g);   
		}
    }
	
}
