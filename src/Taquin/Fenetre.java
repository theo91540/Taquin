package Taquin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout; 
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Fenetre extends JFrame
{
    private JPanel top;
    private JPanel taquin;
    private JPanel bottom;
    private int etape;
    private Etat[] chemin;
    private JLabel label;
    private JButton suivant;
    private JButton precedent;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu_fichier = new JMenu("Fichier");
    private JMenu menu_fichier_nouveau = new JMenu("Nouveau");
    private JMenu menu_edition = new JMenu("Edition");
    private JMenu menu_aide = new JMenu("?");
    private JMenuItem item1 = new JMenuItem("Aléatoire");
    private JMenuItem item2 = new JMenuItem("Manuel");
    private JMenuItem item3 = new JMenuItem("Début");
    private JMenuItem item4 = new JMenuItem("Fin");
    private JMenuItem item5 = new JMenuItem("Quitter");
    private JMenuItem item6 = new JMenuItem("A propos");
  
  public Fenetre()
  {
    this.setTitle("Résolution de taquin");
    this.setSize(300, 300);
    this.setLocationRelativeTo(null);
    
    etape = 0;
    
    top = new JPanel();
    taquin = new JPanel();
    bottom = new JPanel();

    taquin.setLayout(new GridLayout(3, 3));
    bottom.setLayout(new GridLayout(1, 2));

    Etat etat_initial = Taquin.etatAleatoire(3);
    Solution solution = Taquin.resolutionTaquin(etat_initial, 1);
    chemin = solution.getCheminPriorite().getChemin();

    this.getContentPane().add(top, BorderLayout.NORTH);
    this.getContentPane().add(taquin, BorderLayout.CENTER);
    this.getContentPane().add(bottom, BorderLayout.SOUTH);

    label = new JLabel();
    precedent = new JButton("<-");
    suivant = new JButton("->");

     precedent.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event)
            {
                etape--;
                display();
            }
        });

    suivant.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event)
            {
                etape++;
                display();
            }
        });

    display();

    initMenu();

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
    label.setText("Etape " + etape + " sur " + (chemin.length-1));
    top.removeAll();
    top.add(label);

    afficherTaquin(chemin[etape]);

    bottom.removeAll();
    bottom.add((etape > 0?precedent:(new JPanel())));
    bottom.add((etape < chemin.length-1?suivant:(new JPanel())));

    getContentPane().revalidate();
  }

  public void initMenu()
  {
    menu_fichier_nouveau.add(item1);
    menu_fichier_nouveau.add(item2);
    menu_fichier.add(menu_fichier_nouveau);
    menu_fichier.add(item5);
    menu_edition.add(item3);
    menu_edition.add(item4);
    menu_aide.add(item6);
    menuBar.add(menu_fichier);
    menuBar.add(menu_edition);
    menuBar.add(menu_aide);
    setJMenuBar(menuBar);

    item1.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        Etat etat_initial = Taquin.etatAleatoire(3);
        Solution solution = Taquin.resolutionTaquin(etat_initial, 1);
        chemin = solution.getCheminPriorite().getChemin();
        etape = 0;
        display();
      }
    });

    item3.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        etape = 0;
        display();
      }
    });

    item4.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        etape = chemin.length - 1;
        display();
      }
    });

    item5.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        System.exit(0);
      }
    });

    item6.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        JOptionPane jop = new JOptionPane();
        jop.showMessageDialog(null, "Programme réalisé par par Maxime Kermarquer et Théo Chelim.\nPour un projet d'I.A. à l'université d'Evry, en L3 CILS.", "Information", JOptionPane.INFORMATION_MESSAGE);
      }
    });
  }

  public static void main(String[] args)
  {
    new Fenetre();
  }
}