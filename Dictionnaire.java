package Taquin;

public class Dictionnaire
{
	private Noeud racine;

	Dictionnaire(Etat e)
	{
		racine = new Noeud(e);
	}

	public void ajouterEtat(Etat e)
	{
		ajouter(racine, e);
	}

	public Noeud getRacine()
	{
		return racine;
	}

	public int nombreEtatsVisites()
	{
		return nombreNoeuds(racine);
	}

	private int nombreNoeuds(Noeud n)
	{
		if(n==null)
			return 0;
		else 
			return 1 + nombreNoeuds(n.getBranche0()) + nombreNoeuds(n.getBranche1());

	}

	private void ajouter(Noeud n, Etat e)
	{
		if(n.getEtat().compareTo(e)==-1)
			if(n.getBranche0()==null)
				n.setBranche0(new Noeud(e));
			else
				ajouter(n.getBranche0(), e);
		else
			if(n.getBranche1()==null)
				n.setBranche1(new Noeud(e));
			else
				ajouter(n.getBranche1(), e);
	}

	public boolean contientEtat(Etat e)
	{
		return contient(racine, e);
	}

	private boolean contient(Noeud n, Etat e)
	{
		if(n == null)
			return false;
		else
		{
			if(n.getEtat().compareTo(e) == -1)
				return contient(n.getBranche0(), e);
			else if(n.getEtat().compareTo(e) == 1)
				return contient(n.getBranche1(), e);
		}

		return true;
	}
}