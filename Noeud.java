package Taquin;

public class Noeud
{
	private final Etat etat;
	private Noeud branche0, branche1;

	Noeud(Etat e)
	{
		this.etat = e;
		this.branche0 = null;
		this.branche1 = null;
	}

	public Etat getEtat()
	{
		return etat;
	}

	public Noeud getBranche0()
	{
		return this.branche0;
	}

	public Noeud getBranche1()
	{
		return this.branche1;
	}

	public void setBranche0(Noeud n)
	{
		this.branche0 = n;
	}

	public void setBranche1(Noeud n)
	{
		this.branche1 = n;
	}
}