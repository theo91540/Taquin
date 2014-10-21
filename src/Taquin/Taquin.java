package Taquin;

import java.util.*;

public class Taquin
{
	public static void main(String[] args)
	{
		final int N = 3;
		final int DISTANCE_MANHATTAN = 4;
		
		Etat etat_initial = etatAleatoire(N); 
	
		/*
			do
			{
				etat_initial = saisirEtat(N);
			} while(!etat_initial.estSoluble());
		*/

		System.out.println("Début de la résolution du Taquin ("+N+"x"+N+") ...");

		Solution solution = resolutionTaquin(etat_initial, DISTANCE_MANHATTAN);

		System.out.println(solution.toString());
	}

	public static Solution resolutionTaquin(Etat etat_initial, int d)
	{
		long startTime = System.currentTimeMillis();

		FileAttente F = new FileAttente(etat_initial, d);
		Dictionnaire D = new Dictionnaire(etat_initial);

		CheminPriorite C1 = F.getCheminMinimal();
		if(C1.getSommet().etatFinal())
			return new Solution(C1, 0, d, 1, 0);

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
					return new Solution(C, (endTime-startTime), d, D.nombreEtatsVisites(), F.getNombreEtatsSortis());
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

		return new Solution(C1, 0, d, D.nombreEtatsVisites(), F.getNombreEtatsSortis());
	}

	public static Etat saisirEtat(int N) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Saisir un Taquin ("+N+"x"+N+"): ");
		int[] e = new int[N*N];
		for(int i=0; i<e.length; i++)
			e[i] = sc.nextInt();

		return new Etat(e, N);
	}

	public static Etat etatAleatoire(int N)
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

		return new Etat(T,N);
	}

	public static void afficherEtat(Etat e)
	{
		System.out.println(e.toString());
	}
}