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
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;

public class Fenetre extends JFrame
{
    private JPanel top;
    private JPanel taquin;
    private JPanel bottom;
    private int etape;
    private Etat[] chemin;
    private Etat etat_initial;
    private Solution solution;
    private JLabel label;
    private JTextField jtf[];
    private JButton suivant;
    private JButton precedent;
    private JButton valider;
    private JOptionPane jop;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu_fichier = new JMenu("Fichier");
    private JMenu menu_fichier_nouveau = new JMenu("Nouveau");
    private JMenu menu_edition = new JMenu("Edition");
    private JMenu menu_edition_distance = new JMenu("Distance");
    private JMenu menu_informations = new JMenu("Informations");
    private JMenu menu_aide = new JMenu("?");
    private JMenuItem item1 = new JMenuItem("Aléatoire");
    private JMenuItem item2 = new JMenuItem("Manuel");
    private JMenuItem item3 = new JMenuItem("Début");
    private JMenuItem item4 = new JMenuItem("Fin");
    private JMenuItem item5 = new JMenuItem("Quitter");
    private JMenuItem item6 = new JMenuItem("A propos");
    private JMenuItem item7 = new JMenuItem("Solution");
    private JRadioButtonMenuItem jcmi1 = new JRadioButtonMenuItem("D1");
    private JRadioButtonMenuItem jcmi2 = new JRadioButtonMenuItem("D2");
    private JRadioButtonMenuItem jcmi3 = new JRadioButtonMenuItem("D3");
    private JRadioButtonMenuItem jcmi4 = new JRadioButtonMenuItem("D4");
    private JRadioButtonMenuItem jcmi5 = new JRadioButtonMenuItem("D5");
    private JRadioButtonMenuItem jcmi6 = new JRadioButtonMenuItem("D6");
    private ButtonGroup bg = new ButtonGroup();
  
  public Fenetre(boolean saisir_taquin)
  {
    this.setTitle("Résolution de taquin");
    this.setSize(300, 300);
    this.setLocationRelativeTo(null);
    
    initMenu();
    
    jop = new JOptionPane();

    etape = 0;
    
    top = new JPanel();
    taquin = new JPanel();
    bottom = new JPanel();

    taquin.setLayout(new GridLayout(3, 3));
    bottom.setLayout(new GridLayout(1, 2));

    etat_initial = Taquin.etatAleatoire(3);
    solution = Taquin.resolutionTaquin(etat_initial, distanceChoisie());
    chemin = solution.getCheminPriorite().getChemin();

    this.getContentPane().add(top, BorderLayout.NORTH);
    this.getContentPane().add(taquin, BorderLayout.CENTER);
    this.getContentPane().add(bottom, BorderLayout.SOUTH);

    label = new JLabel();
    precedent = new JButton("<-");
    suivant = new JButton("->");
    valider = new JButton("Valider");

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

    valider.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event)
            {
                int[] tab = new int[9];
                for(int i=0; i<tab.length; i++)
                    tab[i] = Integer.parseInt(jtf[i].getText());
                
                etat_initial = new Etat(tab, 3);
                if(etat_initial.estSoluble())
                {
                    solution = Taquin.resolutionTaquin(etat_initial, distanceChoisie());
                    chemin = solution.getCheminPriorite().getChemin();
                    etape = 0;
                    display();
                }
                else 
                {
                    jop.showMessageDialog(null, "Le taquin est insoluble.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    if(saisir_taquin)
        saisie();
    else
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
    item3.setEnabled(true);
    item4.setEnabled(true);
    item7.setEnabled(true);

    label.setText("Etape " + etape + " sur " + (chemin.length-1));
    top.removeAll();
    top.add(label);

    afficherTaquin(chemin[etape]);

    bottom.removeAll();
    bottom.add((etape > 0?precedent:(new JPanel())));
    bottom.add((etape < chemin.length-1?suivant:(new JPanel())));

    getContentPane().revalidate();
  }

  public void saisie()
  {
    item3.setEnabled(false);
    item4.setEnabled(false);
    item7.setEnabled(false);

    label.setText("Saisir le taquin initial :");
    top.removeAll();
    top.add(label);

    taquin.removeAll();
    
    jtf = new JTextField[9];
    for(int i=0; i<9; i++)
    {
        jtf[i] = new JTextField();
        taquin.add(jtf[i]);
    }

    bottom.removeAll();

    bottom.add(valider);
   
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
    menu_edition.addSeparator();
    menu_edition.add(menu_edition_distance);
    menu_edition_distance.add(jcmi1);
    menu_edition_distance.add(jcmi2);
    menu_edition_distance.add(jcmi3);
    menu_edition_distance.add(jcmi4);
    menu_edition_distance.add(jcmi5);
    menu_edition_distance.add(jcmi6);
    menu_informations.add(item7);
    menu_aide.add(item6);
    menuBar.add(menu_fichier);
    menuBar.add(menu_edition);
    menuBar.add(menu_informations);
    menuBar.add(menu_aide);
    setJMenuBar(menuBar);

    bg.add(jcmi1);
    bg.add(jcmi2);
    bg.add(jcmi3);
    bg.add(jcmi4);
    bg.add(jcmi5);
    bg.add(jcmi6);

    jcmi1.setSelected(true);

    item1.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        etat_initial = Taquin.etatAleatoire(3);
        solution = Taquin.resolutionTaquin(etat_initial, distanceChoisie());
        chemin = solution.getCheminPriorite().getChemin();
        etape = 0;
        display();
      }
    });

    item2.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        saisie();
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
        jop.showMessageDialog(null, "Programme réalisé par par Maxime Kermarquer et Théo Chelim.\nPour un projet d'I.A. à l'université d'Evry, en L3 CILS.", "Information", JOptionPane.INFORMATION_MESSAGE);
      }
    });

    item7.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
        String s = "";
        s += ("Solution du taquin trouvé en " + solution.getDuree() + "ms.\n");
        s += ("Distance de Manhattan choisie: d" + solution.getDistanceChoisie() + "\n");
        s += ("Nombre d'etat parcouru: " + solution.getNbVisites() + "\n");
        s += ("Nombre d'etat sortis de la file: " + solution.getNbSortis() + "\n");
        s += ("Taille du chemin de la solution: " + solution.getCheminPriorite().getLongueur() + "\n");
        jop.showMessageDialog(null, s, "Information", JOptionPane.INFORMATION_MESSAGE);
      }
    });
  }

  public int distanceChoisie()
  {
    if(jcmi1.isSelected())
        return 1;
    else if(jcmi2.isSelected())
        return 2;
    else if(jcmi3.isSelected())
        return 3;
    else if(jcmi4.isSelected())
        return 4;
    else if(jcmi5.isSelected())
        return 5;
    else 
        return 6;
  }

  public static void main(String[] args)
  {
    new Fenetre(false);
  }
}