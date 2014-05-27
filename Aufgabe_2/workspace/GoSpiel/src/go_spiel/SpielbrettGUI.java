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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class SpielbrettGUI extends JPanel implements MouseListener, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private GoSpiel spiel;
    private static boolean schwarzAmZug = true;
    private final int Rectwidth = 60;
    private final int Rectheight = 60;
    private final int Stonewidth = 45;
    private final int Stoneheight = 45;
    private final int anzFelderproZeile = 8;
    private final int anzFelderproSpalte = 8;
    private final int anzSteineproZeile = anzFelderproZeile + 1; // immer einer
                                                                 // mehr als es
                                                                 // Felder gibt
    private final int anzSteineproSpalte = anzFelderproZeile + 1; // immer einer
                                                                  // mehr als es
                                                                  // Felder gibt
    private int aktuellesX;
    private int aktuellesY;
    private final int rand = 60;

    public SpielbrettGUI(GoSpiel spiel) {
        this.setVisible(true);
        this.addMouseListener(this);
        this.setSize(Rectwidth * anzSteineproZeile + rand, Rectheight * anzSteineproZeile + rand);
        this.aktuellesX = Rectwidth * anzSteineproZeile + rand;
        this.aktuellesY = Rectheight * anzSteineproSpalte + rand;
        this.spiel = spiel;
    }

    public GoSpiel getSpiel() {
        return spiel;
    }

    @Override
    protected void paintComponent(Graphics g) {

        int x = rand;
        int y = rand;
        g.setColor(new Color(0xfaebd7));

        // +100 um ein viel groeﬂeres rechteck als das feld selber zu zeichnen
        g.fillRect(0, 0, Rectwidth * (anzSteineproSpalte + 100), Rectheight * (anzSteineproZeile + 100));
        g.setColor(new Color(0x922724));
        // +1 um ein groeﬂeres rechteck als das feld selber zu zeichnen
        g.fill3DRect(0, 0, Rectwidth * (anzSteineproSpalte + 1), Rectheight * (anzSteineproZeile + 1), true);
        g.setColor(Color.BLACK);

        for (int i = 0; i < anzFelderproSpalte; i++) {
            y = rand;
            for (int j = 0; j < anzFelderproZeile; j++) {
                g.drawRect(x, y, Rectwidth, Rectheight);
                y = y + Rectheight;
            }
            x = x + Rectwidth;
        }

        x = rand;
        y = rand;

        for (int i = 0; i < anzSteineproSpalte; i++) {

            y = rand;
            for (int j = 0; j < anzSteineproZeile; j++) {
                if (spiel.getFeld().isGesetzt(i, j)) {
                    if (spiel.getFeld().isFarbeWeiﬂ(i, j)) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                    g.fillOval(x - 22, y - 22, Stonewidth, Stoneheight);
                }
                y = y + Rectheight;
            }
            x = x + Rectwidth;
        }

        g.setColor(Color.BLACK);

        g.drawString("Schwarzer Spieler:", x + 10, y - 360);
        g.drawString("Gesetzte Steine:" + spiel.getFeld().getGesetzteSchwarze(), x + 10, y - 340);
        g.drawString("Gefangene Steine: " + spiel.getFeld().getAnzahlWeiﬂerAtari(), x + 10, y - 320);
        g.drawString("Eroberte Territorien:" + spiel.getFeld().getSchwarzTerritorium(), x + 10, y - 300);

        g.drawString("Weiﬂer Spieler:", x + 10, y - 260);
        g.drawString("Gesetzte Steine: " + spiel.getFeld().getGesetzteWeiﬂe(), x + 10, y - 240);
        g.drawString("Gefangene Steine: " + spiel.getFeld().getAnzahlSchwarzerAtari(), x + 10, y - 220);
        g.drawString("Eroberte Territorien:" + spiel.getFeld().getWeiﬂeTerritorium(), x + 10, y - 200);

        if (spiel.getPassen() >= 2) {
            int punkteSchwarz = spiel.getFeld().getAnzahlWeiﬂerAtari() + spiel.getFeld().getGesetzteSchwarze() + spiel.getFeld().getSchwarzTerritorium();
            int punkteWeiﬂ = spiel.getFeld().getAnzahlSchwarzerAtari() + spiel.getFeld().getGesetzteWeiﬂe() + spiel.getFeld().getWeiﬂeTerritorium();

            g.drawString("der Schwarze spieler hat " + punkteSchwarz + " Punkte", 100, y + 20);
            g.drawString("der Weiﬂe spieler hat " + punkteWeiﬂ + " Punkte", 100, y + 40);

            if (punkteSchwarz == punkteWeiﬂ) {
                g.drawString("Unentschieden !!!!", 100, y + 60);
            } else if (punkteSchwarz > punkteWeiﬂ) {
                g.drawString("der Schwarze spieler hat Gewonnen Glueckwunsch !!!!", 100, y + 60);
            } else {
                g.drawString("der Weiﬂe spieler hat Gewonnen Glueckwunsch !!!!", 100, y + 60);
            }
        } else {
            if (schwarzAmZug) {
                g.drawString("Der schwarze Spieler ist am zug", 100, y + 60);
            } else {
                g.drawString("Der weiﬂe Spieler ist am zug", 100, y + 60);
            }

        }

    }

    public void farbWechsel() {
        if (schwarzAmZug) {
            schwarzAmZug = false;
        } else {
            schwarzAmZug = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = (int) Math.round((e.getX() - rand) / (double) Rectwidth);
        int y = (int) Math.round((e.getY() - rand) / (double) Rectheight);

        // reagiert nur auf Mausklicke inerhalb des feldes, wenn der Platz nicht
        // schon belegt wurde, nicht zweimal gepasst wurde und wenn mann sich
        // nicht selbst ins Atari setzt

        if (spiel.getPassen() == 1 && !spiel.isSteinEntfern()) {
            if (spiel.getFeld().isFarbeWeiﬂ(x, y) != schwarzAmZug) {
                spiel.getFeld().setNichtGesetzt(x, y);
                spiel.setSteinEntfernt(true);
                farbWechsel();
            }
        } else if (x < anzSteineproSpalte && y < anzSteineproZeile && x >= 0 && y >= 0 && !spiel.getFeld().isGesetzt(x, y) && spiel.getPassen() < 2) {

            if (spiel.checkZug(x, y, schwarzAmZug)) {
                spiel.setZurueck(false);
                spiel.speicherSpielstand();

                spiel.setPassen();
                if (!schwarzAmZug) {
                    spiel.getFeld().setFarbeWeiﬂ(x, y);
                }

                farbWechsel();

                spiel.getFeld().setGesetzt(x, y);
                spiel.checkStein(x, y, true);

            }

        }
        spiel.checkTerritorium();
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    public int getAktuellesX() {
        return aktuellesX;
    }

    public int getAktuellesY() {
        return aktuellesY;
    }

}
