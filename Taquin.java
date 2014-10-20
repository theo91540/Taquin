package Taquin;

import java.util.*;

public class Taquin
{
	public static void main(String[] args)
	{
		final int N = 3;
		
		int[] e1 = {0,1,2,3,4,5,8,6,7};
		int[] e2 = {1,3,8,5,7,6,4,2,0};
		int[] e3 = {8,7,6,5,4,3,2,1,0};

		Etat etat_initial = new Etat(e3, N); 

		//System.out.println("Saisir un Taquin ("+N+"x"+N+"): ");
		//Etat etat_initial = saisirEtatInitial(N);

		System.out.println("Début de la resolution du Taquin ...");

		//CheminPriorite chemin_resolution = resolutionTaquin(etat_initial, 1);
		//CheminPriorite chemin_resolution = resolutionTaquin(etat_initial, 2);
		//CheminPriorite chemin_resolution = resolutionTaquin(etat_initial, 3);
		//CheminPriorite chemin_resolution = resolutionTaquin(etat_initial, 4);
		//CheminPriorite chemin_resolution = resolutionTaquin(etat_initial, 5);
		CheminPriorite chemin_resolution = resolutionTaquin(etat_initial, 6);

		Etat[] chemin = chemin_resolution.getChemin();

		for(int i=0; i<chemin.length; i++)
			afficherEtat(chemin[i]);		
	}

	public static CheminPriorite resolutionTaquin(Etat etat_initial, int d)
	{
		long startTime = System.currentTimeMillis();

		FileAttente F = new FileAttente(etat_initial, d);
		Dictionnaire D = new Dictionnaire(etat_initial);

		CheminPriorite C1 = F.getCheminMinimal();
		if(C1.getSommet().etatFinal())
			return C1;

		boolean fini = false;
		while(!fini && F.getTaille()>0)
		{ 
			CheminPriorite C = F.getCheminMinimal();
			F.supprimerCheminMinimal();
			Etat E = C.getSommet();
			Etat[] S = E.getSuccesseurs();
			for(int i=0; i<S.length; i++)
			{
				if(S[i].etatFinal())
				{
					fini = true;
					C.ajouterEtat(S[i]);
					long endTime = System.currentTimeMillis();
					System.out.println("Solution du taquin trouvé en " + (endTime-startTime) + "ms.");
					System.out.println("Nombre d'etat parcouru: " + D.nombreEtatsVisites());
					System.out.println("Nombre d'etat sortis de la file: " + F.getNombreEtatsSortis());
					System.out.println("Taille du chemin de la solution: " + C.getChemin().length);
					return C;
				}
							
				if(!D.contientEtat(S[i]))
				{
					D.ajouterEtat(S[i]);
					C1 = new CheminPriorite(C);
					C1.ajouterEtat(S[i]);
					C1.setPriorite((byte) (C.getLongueur() + S[i].distanceEtatFinal(d)));
					F.ajouterChemin(C1);
				}
			}
		}

		return C1;
	}

	public static Etat saisirEtatInitial(int N) 
	{
		Scanner sc = new Scanner(System.in);

		int[] e = new int[N*N];
		for(int i=0; i<e.length; i++)
			e[i] = sc.nextInt();

		return new Etat(e, N);
	}

	public static void afficherEtat(Etat e)
	{
		System.out.println(e.toString());
	}
}