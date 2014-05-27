package go_spiel;

/*
 * Praktikum:           P2P
 * Semester:            SS12
 * Team:                S3T5
 * Namen der Team-Mitglieder mit Matrikel-Nr.:
 *                      Borchert Pascal #2049713
 *                      Malsch Michael #1908805
 * Aufgabe:             A2
 * Datum der Abnahme    8.06.12
 * Kontrolleur:         Behnke
 * 
 * */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GoGUI {
    private JFrame spielFrame = new JFrame();
    private JButton zurueckButton = new JButton("Zurueck");
    private JButton passenButton = new JButton("Passen");
    private GoSpiel spiel = new GoSpiel();
    SpielbrettGUI spielbrett = new SpielbrettGUI(spiel);
    JPanel anzeige = new JPanel();

    public static void main(String[] args) {
        GoGUI g = new GoGUI();
        g.start();
    }

    public void start() {
        passenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                spielbrett.getSpiel().passen();
                spielbrett.repaint();
            }
        });

        zurueckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!spiel.isZurueck()) {
                    spiel.setzeZugZurueck();
                    spielbrett.farbWechsel(); // geht man ein zug zurueck muss man auch die
                                              // farbe wechseln
                    spielbrett.repaint();
                    spiel.checkTerritorium();
                }
            }
        });
        int x = spielbrett.getAktuellesX();
        int y = spielbrett.getAktuellesY();

        spielFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        spielFrame.setVisible(true);
        spielFrame.setSize(x + 200, y + 120);
        spielbrett.setLayout(null);

        zurueckButton.setBounds(x + 10, y - 50, 150, 50);
        passenButton.setBounds(x + 10, y - 110, 150, 50);
        spielbrett.add(zurueckButton);
        spielbrett.add(passenButton);
        spielFrame.add(spielbrett);

    }

    public boolean checkZug(int x, int y, boolean[][] felder) {
        boolean check = false;

        return check;
    }

}
