package Taquin;

import java.util.ArrayList;

public class FileAttente
{
	private ArrayList<CheminPriorite> liste_chemins;
	private int nombre_etats_sortis;

	FileAttente(Etat e, int d)
	{
		this.liste_chemins = new ArrayList<CheminPriorite>();
		this.liste_chemins.add(new CheminPriorite(e, (byte) e.distanceEtatFinal(d)));
		this.nombre_etats_sortis = 0;
	}

	public void ajouterChemin(CheminPriorite cp)
	{
		int i;
		if(liste_chemins.size() == 0)
			liste_chemins.add(cp);
		else
		{
			for(i=0; i<liste_chemins.size(); i++)
				if(liste_chemins.get(i).getPriorite() > cp.getPriorite())
					break;
			liste_chemins.add(i, cp);
		}
	}

	public void supprimerCheminMinimal()
	{
		this.liste_chemins.remove(0);
		this.nombre_etats_sortis++;
	}

	public CheminPriorite getCheminMinimal()
	{
		return this.liste_chemins.get(0);
	}

	public int getTaille()
	{
		return this.liste_chemins.size();
	}

	public int getNombreEtatsSortis()
	{
		return this.nombre_etats_sortis;
	}
	
	public CheminPriorite[] getChemins()
	{
		return this.liste_chemins.toArray(new CheminPriorite[liste_chemins.size()]);
	}
}