package Taquin;

import java.util.ArrayList;

public class CheminPriorite extends Chemin
{

	private byte priorite;

	CheminPriorite(Etat e, byte priorite)
	{
		super(e);
		this.priorite = priorite;
	}

	CheminPriorite(CheminPriorite cp)
	{
		super(cp.chemin.get(0));
		this.chemin = new ArrayList<Etat>(cp.chemin);
		this.longueur = cp.longueur;
		this.priorite = cp.priorite;
	}

	public byte getPriorite()
	{
		return this.priorite;
	}

	public void setPriorite(byte priorite)
	{
		this.priorite = priorite;
	}
}