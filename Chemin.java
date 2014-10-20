package Taquin;

import java.util.ArrayList;

public class Chemin
{
	protected ArrayList<Etat> chemin;
	protected int longueur;

	Chemin(Etat e)
	{
		this.chemin = new ArrayList<Etat>();
		this.chemin.add(e);
		this.longueur = 0;
	}

	public void ajouterEtat(Etat e)
	{
		this.chemin.add(e);
		this.longueur++;
	}

	public Etat getSommet()
	{
		return chemin.get(chemin.size()-1);
	}

	public int getLongueur()
	{
		return this.longueur;
	}

	public Etat[] getChemin()
	{
		return this.chemin.toArray(new Etat[chemin.size()]);
	}
}