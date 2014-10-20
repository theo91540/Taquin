package Taquin;

import java.util.*;

public class Taquin
{
	public static void main(String[] args)
	{
		final int N = 3;
		final int DISTANCE_MANHATTAN = 4;

		Etat etat_initial = new Etat(etatAleatoire(N), N); 
	
		System.out.println("Début de la resolution du Taquin ("+N+"x"+N+") ...");

		CheminPriorite chemin_resolution = resolutionTaquin(etat_initial, DISTANCE_MANHATTAN);

		Etat[] chemin = chemin_resolution.getChemin();

		for(int i=0; i<chemin.length; i++)
		{
			System.out.println("Etape n°" + i + ":");
			afficherEtat(chemin[i]);		
		}
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
					System.out.println("Distance de Manhattan choisie: d" + d);
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
		System.out.println("Saisir un Taquin ("+N+"x"+N+"): ");
		int[] e = new int[N*N];
		for(int i=0; i<e.length; i++)
			e[i] = sc.nextInt();

		return new Etat(e, N);
	}

	public static int[] etatAleatoire(int N)
	{
		int[] T = new int[N*N];
		int temp, k, trou = 0;
		for(int i=0; i<T.length; i++)
			T[i] = i;
		
		for(int i=0; i<T.length; i++)
		{
			Random r = new Random();
			k = r.nextInt(N-1); 
			temp = T[i];
			T[i] = T[k];
			T[k] = temp;
		}	
		
		if(!(new Etat(T,N)).estSoluble())
		{
			for(int i=0; i<T.length; i++)
				if(T[i] == T.length-1)
					trou = i;
			temp = T[(trou+1)%N];
			T[(trou+1)%N] = T[(trou+2)%N];
			T[(trou+2)%N] = temp;
		}

		return T;
	}

	public static void afficherEtat(Etat e)
	{
		System.out.println(e.toString());
	}
}