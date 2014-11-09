package Taquin;

import java.util.*;

public class Taquin
{
	private static boolean continuer = true;

	public static void main(String[] args)
	{
		do
		{
			Scanner sc = new Scanner(System.in);
			int choix;

			System.out.println("\n##############################");
			System.out.println("###### Menu Principal : ######");
			System.out.println("##############################\n");
			System.out.println("1) Saisir un taquin puis résolution en console");
			System.out.println("2) Saisir un taquin puis résolution graphique");
			System.out.println("3) Résolution d'un taquin aléatoire en console");
			System.out.println("4) Résolution d'un taquin aléatoire graphique");
			System.out.println("5) Expérimentations sur la résolution de taquin");
			System.out.println("6) Quitter le programme\n");
			System.out.print("Votre choix : ");
			
			choix = sc.nextInt();

			menu(choix);

		} while(continuer);

		System.out.println("Fin du programme.");
	}

	public static void menu(int choix)
	{
		final int N = 4;

		switch(choix)
		{
			case 1:
			{
				console(saisirEtat(saisirN()));
				break;
			}
			case 2:
			{
				new Fenetre(true);
				break;
			}
			case 3:
			{
				console(etatAleatoire(saisirN()));
				break;
			}
			case 4:
			{
				new Fenetre(false);
				break;
			}
			case 5:
			{
				experimentations();
				break;
			}
			case 6:
			{
				continuer = false;
				break;
			}
			default:
			{
				System.out.println("Ce choix est invalide.");
				break;
			}
		}
	}

	public static void experimentations()
	{
		final int NB_MAX = 1_000;

		long startTime = System.currentTimeMillis();
		Etat[] etats_initiaux = new Etat[NB_MAX];
		
		for(int i=0; i<NB_MAX; i++)
		{
			etats_initiaux[i] = etatAleatoire(3);
		}
		
		for(int d=1; d<=6; d++)
		{
			ArrayList<Solution> solutions = new ArrayList<Solution>();
			for(int i=0; i<NB_MAX; i++)
			{
				solutions.add(resolutionTaquin(etats_initiaux[i], d));
			}
			
			long somme_duree = 0;
			long somme_longueur = 0;
			long somme_visites = 0;
			long somme_sortis = 0;
			for(int i=0; i<solutions.size(); i++)
			{
				somme_duree += solutions.get(i).getDuree();
				somme_longueur += solutions.get(i).getCheminPriorite().getLongueur();
				somme_visites += solutions.get(i).getNbVisites();
				somme_sortis += solutions.get(i).getNbSortis();
			}
			
			System.out.println("* Résolution de taquins pour la distance d" + d + ": ");
			System.out.println("\tTemps moyen de résolution d'un taquin pour d"+d+" ("+NB_MAX+" taquins résolus): " + (somme_duree/solutions.size()) + "ms.");
			System.out.println("\tLongueur moyenne de la solution d'un taquin pour d"+d+" ("+NB_MAX+" taquins résolus): " + (somme_longueur/solutions.size()) + ".");
			System.out.println("\tNombre d'etats visites en moyenne pour d"+d+" ("+NB_MAX+" taquins résolus): " + (somme_visites/solutions.size()) + ".");
			System.out.println("\tNombre d'etats sortis de la file en moyenne pour d"+d+" ("+NB_MAX+" taquins résolus): " + (somme_sortis/solutions.size()) + ".\n");
		}

		long endTime = System.currentTimeMillis();

		System.out.println("Durée du programme pour 6x"+NB_MAX+" taquins à résoudre: " + ((endTime-startTime)/1000)/60 + "min" + ((endTime-startTime)/1000)%60 + "s.");
	
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
			k = r.nextInt((N*N)-1); 
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

	public static void console(Etat initial)
	{
		int d;
		Scanner sc = new Scanner(System.in);
		Etat etat_initial = initial; 
		
		do 
		{
			System.out.println("Saisir la distance de Manhattan a utiliser : ");
			d = sc.nextInt();
		
			if(d<1 || d>6)
				System.out.println("La distance de Manhattan doit etre comprise entre 1 et 6.");
	
		} while(d<1 || d>6);
	
		Solution s = resolutionTaquin(etat_initial, d);
		System.out.println(s.toString());
	}

	public static int saisirN()
	{
		int d;
		Scanner sc = new Scanner(System.in);
		
		do 
		{
			System.out.println("Saisir N : ");
			d = sc.nextInt();
		
			if(d<2)
				System.out.println("N doit etre superieur a 2.");
	
		} while(d<2);
	
		return d;
	}
}