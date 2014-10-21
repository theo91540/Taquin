package Taquin;

public class Solution
{

	private CheminPriorite chemin;
	private long duree;
	private int distance_choisie;
	private int nb_etats_visistes;
	private int nb_etats_sortis;

	Solution(CheminPriorite cp, long duree, int distance, int nb_visiste, int nb_sorti)
	{
		this.chemin = new CheminPriorite(cp);
		this.duree = duree;
		this.distance_choisie = distance;
		this.nb_etats_visistes = nb_visiste;
		this.nb_etats_sortis = nb_sorti;
	}

	public CheminPriorite getCheminPriorite()
	{
		return this.chemin;
	}

	public long getDuree()
	{
		return this.duree;
	}

	public int getDistanceChoisie()
	{
		return this.distance_choisie;
	}

	public int getNbVisites()
	{
		return this.nb_etats_visistes;
	}

	public int getNbSortis()
	{
		return this.nb_etats_sortis;
	}

	public String toString()
	{
		String s = "";
		Etat[] etats = chemin.getChemin();

		for(int i=0; i<etats.length; i++)
			s += ("Etape n°" + i + ":\n" + etats[i].toString() + "\n");	
		
		s += ("Solution du taquin trouvé en " + this.getDuree() + "ms.\n");
		s += ("Distance de Manhattan choisie: d" + this.getDistanceChoisie() + "\n");
		s += ("Nombre d'etat parcouru: " + this.getNbVisites() + "\n");
		s += ("Nombre d'etat sortis de la file: " + this.getNbSortis() + "\n");
		s += ("Taille du chemin de la solution: " + this.getCheminPriorite().getLongueur() + "\n");

		return s;
	}
}