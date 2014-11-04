package Taquin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout; 
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

public class Fenetre extends JFrame
{
    private JPanel taquin;
    private JPanel bottom;
    private int K;
    private Etat[] chemin;
    private JButton suivant;
    private JButton precedent;

  public Fenetre()
  {
    this.setTitle("Taquin");
    this.setSize(300, 300);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.K = 0;
    taquin = new JPanel();
    bottom = new JPanel();

    taquin.setLayout(new GridLayout(3, 3));
    bottom.setLayout(new GridLayout(1, 2));

    Etat etat_initial = Taquin.etatAleatoire(3);
    Solution solution = Taquin.resolutionTaquin(etat_initial, 1);
    chemin = solution.getCheminPriorite().getChemin();

    this.getContentPane().add(taquin, BorderLayout.CENTER);
    this.getContentPane().add(bottom, BorderLayout.SOUTH);

    precedent = new JButton("<-");
    suivant = new JButton("->");

     precedent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event)
            {
                K--;
                display();
                getContentPane().revalidate();
            }
        });

    suivant.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event)
            {
                K++;
                display();
                getContentPane().revalidate();
            }
        });

    display();

     this.setVisible(true);
  }    

  public void afficherTaquin(Etat etat)
  {
    taquin.removeAll();
    int[] tab = etat.getTab();
    for(int i=0; i<tab.length; i++)
    {
        JButton b = new JButton(tab[i]+"");
        b.setEnabled(false);
        if(tab[i] == tab.length-1)
        {
            b.setText(" ");
            b.setBackground(Color.lightGray);
            
        }

        taquin.add(b);
    }
  }

  public void display()
  {
    afficherTaquin(chemin[K]);

    bottom.removeAll();
    if(K>0)
        bottom.add(precedent);
    else 
        bottom.add(new JPanel());

    if(K < chemin.length-1)
        bottom.add(suivant);
    else 
        bottom.add(new JPanel());
  }

  public static void main(String[] args)
  {
    new Fenetre();
  }
}