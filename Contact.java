
public class Contact{

	protected int dateDebutContact;
	protected Personne personneCont;
	protected int duree;
	
	public Contact(Personne personne1, int dateDebut ) {
		dateDebutContact = dateDebut;
		personneCont = personne1;
	}

	public void majDuree(int now) {
		duree = now-dateDebutContact;
	}
	
	
}
