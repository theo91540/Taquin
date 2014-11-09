package Taquin;

import java.util.ArrayList;

public class Etat
{
	private int[] etat;
	private int N;

	Etat(int[] etat, int N)
	{
		this.etat = etat;
		this.N = N;
	}

	/*
		Accesseur du tableau d'entiers
	*/
	public int[] getTab()
	{
		return this.etat;
	}

	/*
		Methode permettant d'optenir la liste des successeurs de l'etat
		a partir des 4 directions NORD, SUD, EST et OUEST si elles sont realisables
	*/
	public Etat[] getSuccesseurs()
	{
		final int NORD = 0, SUD = 1, EST = 2, OUEST = 3;
		ArrayList<Etat> successeurs = new ArrayList<Etat>();
		int trou = 0, temp;

		for(int direction=NORD; direction<=OUEST; direction++)
		{
			for(int i=0; i<etat.length; i++)
				if(etat[i]==etat.length-1)
					trou = i;

			switch(direction)
			{
				case NORD:
				{
					if((trou/this.N)>0)
					{
						int[] copie = (int[]) this.etat.clone();
						temp = copie[trou];
						copie[trou] = copie[trou-N];
						copie[trou-N] = temp;
						successeurs.add(new Etat(copie,N));
					}
					break;
				}
				case SUD:
				{
					if((trou/this.N)<N-1)
					{
						int[] copie = (int[]) this.etat.clone();
						temp = copie[trou];
						copie[trou] = copie[trou+N];
						copie[trou+N] = temp;
						successeurs.add(new Etat(copie,N));
					}					
					break;
				}
				case EST:
				{
					if((trou%this.N)<N-1)
					{
						int[] copie = (int[]) this.etat.clone();
						temp = copie[trou];
						copie[trou] = copie[trou+1];
						copie[trou+1] = temp;
						successeurs.add(new Etat(copie,N));
					}					
					break;
				}
				case OUEST:
				{
					if((trou%this.N)>0)
					{
						int[] copie = (int[]) this.etat.clone();
						temp = copie[trou];
						copie[trou] = copie[trou-1];
						copie[trou-1] = temp;
						successeurs.add(new Etat(copie,N));
					}					
					break;
				}
			}
		}

		return successeurs.toArray(new Etat[successeurs.size()]);
	}

	/*
		Methode calculant epsilon qui est la distance elementaire de chaque case de l'etat
	*/
	public int[] epsilon()
	{
		int[] epsilon = new int[N*N];
		for(int i=0; i<etat.length; i++)
		{
			epsilon[etat[i]] = Math.abs((etat[i]%N) - (i%N)) + Math.abs((etat[i]/N) - (i/N));
		}
		return epsilon;
	}

	/*
		Methode calculant la distance de l'etat a l'etat final suivant la distance de manhattan choisie
		cette distance est utilisée comme heuristique pour notre probleme de recherche
	*/
	public int distanceEtatFinal(int d)
	{
		int somme = 0;
		int[][] poids = {{36,12,12,4,1,1,4,1,0},
				 {8,7,6,5,4,3,2,1,0},
				 {8,7,6,5,4,3,2,1,0},
				 {8,7,6,5,3,2,4,1,0},
				 {8,7,6,5,3,2,4,1,0},
				 {1,1,1,1,1,1,1,1,0}};

		int[] epsilon = epsilon();
		for(int i=0;i<epsilon.length; i++)
		{
			if(N==3)
				somme += (epsilon[i] * poids[d-1][i%9]);
			else 
				somme += epsilon[i] * ((i!=epsilon.length-1)?1:0);
		}

		return somme / ((d%2==0)?1:4);

	}

	/*
		Est-ce que l'etat est un etat final ou non
		On verifie que le tableau est trie
	*/
	public boolean etatFinal()
	{
		boolean etatFinal = true;
		for(int i=0; i<etat.length-1; i++)
			if(etat[i]>etat[i+1])
				etatFinal = false;
		return etatFinal;
	}

	/* 
		Est-ce que l'etat est un etat initial soluble ou non
		On verifie pour cela que la parite du nombre d'echange 
		est la meme que celle de la distance elementaire du trou.
	*/
	public boolean estSoluble()
	{
		boolean tab_en_ordre = false;

		int[] copie = (int[]) this.etat.clone();
		int[] epsilon = epsilon();

    	int taille = copie.length;
    	int temp;
    	int nb_echanges = 0;

	    while(!tab_en_ordre)
	    {
	        tab_en_ordre = true;
	        for(int i=0 ; i < taille-1 ; i++)
	        {
	            if(copie[i] > copie[i+1])
	            {
	                temp = copie[i];
	                copie[i] = copie[i+1];
	                copie[i+1] = temp;
	                nb_echanges++;
	                tab_en_ordre = false;
	            }
	        }
	        taille--;
	    }

	    return (nb_echanges%2)==(epsilon[etat.length-1]%2);
	}

	/*
		Methode permettant de comparer deux etat entre eux
		return -1 si l'etat est inferieur
		return 1 si l'etat est superieur
		0 sinon (egaux)
	*/
	public int compareTo(Etat e2)
	{
		for(int i=0; i<etat.length; i++)
		{
			if(this.etat[i] < e2.etat[i])
				return -1;
			else if(this.etat[i] > e2.etat[i])
				return 1;
		}

		return 0;
	}

	/*
		Methode permettant de visualiser un etat
	*/
	public String toString()
	{
		String s = "";
		for(int i=0; i<etat.length; i++)
		{
			if(etat[i] == etat.length-1)
				s += "|X";
			else
				s += "|" + etat[i] ;

			if(i%N==(N-1))
				s += "|\n";
		}
		return s;
	}
}
