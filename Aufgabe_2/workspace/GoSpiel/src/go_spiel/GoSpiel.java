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
import java.util.ArrayList;

public class GoSpiel {

    private Spielstand letzterSpielstand;
    private ArrayList<Integer> atari = new ArrayList<Integer>();
    private ArrayList<Integer> teilTerritorium = new ArrayList<Integer>();
    private ArrayList<Integer> alleTerritorium = new ArrayList<Integer>();
    private Feld feld;
    private int passen;
    private boolean steinEntfern = true;
    private boolean koRegel = false; // gibt an ob Koregel an ist
    private boolean zurueck;

    public GoSpiel() {
        feld = new Feld();
    }

    public GoSpiel(ArrayList<Integer> atari, int passen) {
        super();
        this.atari = atari;
        this.feld = new Feld();
        this.passen = passen;
    }

    public void speicherSpielstand() {
        letzterSpielstand = new Spielstand(passen, steinEntfern, new Feld(feld.getFelder(), feld.getAnzahlSchwarzerAtari(), feld.getAnzahlWeiﬂerAtari(),
                feld.getGesetzteSchwarze(), feld.getGesetzteWeiﬂe()));

    }

    public void setzeZugZurueck() {
        if (letzterSpielstand != null && !zurueck) {
            zurueck = true;
            this.feld = letzterSpielstand.getFeld();
            this.passen = letzterSpielstand.getPassen();
            this.steinEntfern = letzterSpielstand.isSteinEntfernt();
        }
    }

    // es ist nicht erlaubt sich selbst ins atari zu setzen
    public boolean checkZug(int x, int y, boolean schwarz) {
        boolean check = true;
        feld.setGesetzt(x, y);

        if (!schwarz) {
            feld.setFarbeWeiﬂ(x, y);
        }

        if (!checkStein(x, y, false)) {// Fangen vor selbstmord
            if (checkAtari(x, y)) {// Prueft Selbstmord
                check = false;
            }
        }

        if (checkKoRegel(x, y)) {
            check = false;
        }

        feld.setFarbeSchwarz(x, y);
        feld.setNichtGesetzt(x, y);
        atari.clear();

        return check;
    }

    /**
     * 
     * 
     * 
     * @param x
     * @param y
     * @return
     */
    public boolean checkKoRegel(int x, int y) {
        int[][] guckXY = { { x + 1, y }, { x - 1, y }, { x, y + 1 }, { x, y - 1 } };
        boolean check = false;
        boolean[] checkGuckXY;
        int anzahlGefangenerSteine = 1;
        int count = 0;
        checkGuckXY = guckPosition(x, y);

        if (checkAtari(x, y)) {
            for (int i = 0; i < guckXY.length; i++) {

                if (checkGuckXY[i]) {
                    if (checkAtari(guckXY[i][0], guckXY[i][1])) {
                        if (atari.size() == anzahlGefangenerSteine) {
                            count++;
                            if (count == anzahlGefangenerSteine) {
                                check = true;
                            } else {
                                check = false;
                            }
                        }
                    }
                }
                atari.clear();
            }
        }

        if (!check) {
            koRegel = false;
        }

        if (check && !koRegel) {
            check = false;
            koRegel = true;
        }

        return check;
    }

    private boolean checkStein(int x1, int y1, int x2, int y2, boolean setzen) {
        boolean check = false;

        if (feld.isGesetzt(x2, y2)) {
            if (feld.isFarbeWeiﬂ(x1, y1) != feld.isFarbeWeiﬂ(x2, y2)) {
                if (checkAtari(x2, y2)) {
                    if (setzen) {
                        setAtari();
                    }
                    check = true;
                }
            }
        }
        atari.clear();
        return check;
    }

    // prueft ob feindliche Steine in der Umgebeung ins Atari gesetzt wurden
    public boolean checkStein(int x, int y, boolean setzen) {
        int[][] guckXY = { { x + 1, y }, { x - 1, y }, { x, y + 1 }, { x, y - 1 } };
        boolean check = false;
        boolean[] checkGuckXY;
        checkGuckXY = guckPosition(x, y);

        for (int i = 0; i < guckXY.length; i++) {
            if (checkGuckXY[i]) {
                if (checkStein(x, y, guckXY[i][0], guckXY[i][1], false)) {
                    check = checkStein(x, y, guckXY[i][0], guckXY[i][1], setzen);
                }

            }
        }

        return check;
    }

    //guckt von einen Stein aus in alle vier Himmelrichtungen um zu checken ob er vielleicht am rand ist
    private boolean[] guckPosition(int x, int y) {
        boolean[] checkGuckXY = new boolean[4];

        if (x < feld.getAnzSteineproSpalte() - 1) { // guckt rechts
            checkGuckXY[0] = true;
        }

        if (x != 0) {// guckt Links
            checkGuckXY[1] = true;
        }

        if (y < feld.getAnzSteineproZeile() - 1) {// guckt Oben
            checkGuckXY[2] = true;
        }

        if (y != 0) {// guckt unten
            checkGuckXY[3] = true;
        }

        return checkGuckXY;
    }

    public boolean checkAtari(int x, int y) {
        int[][] guckXY = { { x + 1, y }, { x - 1, y }, { x, y + 1 }, { x, y - 1 } };
        boolean[] zuChecken = new boolean[4];
        boolean check = true; // gibt true zur¸ck falls steine gefangen wurden

        boolean[] checkGuckXY;

        if (feld.isGesetzt(x, y)) {
            checkGuckXY = guckPosition(x, y);

            for (int i = 0; i < checkGuckXY.length; i++) {
                if (checkGuckXY[i]) { // guckt
                    if (feld.isGesetzt(guckXY[i][0], guckXY[i][1])) {
                        if (feld.isFarbeWeiﬂ(x, y) == feld.isFarbeWeiﬂ(guckXY[i][0], guckXY[i][1])) {
                            // guckt ob der Stein schon gesetzt wurde
                            if (!atari.contains(guckXY[i][0] * 10 + guckXY[i][1])) {
                                zuChecken[i] = true;
                            }
                        }
                    } else {
                        check = false;
                    }
                }
            }

            if (check) {
                atari.add(x * 10 + y);
                for (int i = 0; i < guckXY.length; i++) {
                    // Guckt ob der Stein schon gechekt wurde;
                    if (!atari.contains(guckXY[i][0] * 10 + guckXY[i][1]) && zuChecken[i]) {
                        check &= checkAtari(guckXY[i][0], guckXY[i][1]);
                    }
                }
            }
        }
        return check;
    }

    public void checkTerritorium() {
        teilTerritorium.clear();
        checkTerritorium(true);
        feld.setWeiﬂeTerritorium(alleTerritorium.size());
        alleTerritorium.clear();

        teilTerritorium.clear();
        checkTerritorium(false);
        feld.setSchwarzTerritorium(alleTerritorium.size());
        alleTerritorium.clear();
    }

    public void checkTerritorium(boolean farbe) {

        for (int i = 0; i < feld.getFelder().length; i++) {
            for (int j = 0; j < feld.getFelder()[i].length; j++) {

                if (!feld.isGesetzt(i, i) && !alleTerritorium.contains(i * 10 + j)) {
                    if (checkTerritorium(i, j, farbe)) {
                        alleTerritorium.addAll(teilTerritorium);

                    }
                    teilTerritorium.clear();
                }
            }
        }
    }

    public boolean checkTerritorium(int x, int y, boolean farbe) {
        int[][] guckXY = { { x + 1, y }, { x - 1, y }, { x, y + 1 }, { x, y - 1 } };

        boolean check = true;
        boolean[] zuChecken = new boolean[4];
        boolean[] checkGuckXY;

        checkGuckXY = guckPosition(x, y);
        if (!feld.isGesetzt(x, y)) {
            for (int i = 0; i < checkGuckXY.length; i++) {

                if (checkGuckXY[i]) {
                    if (!feld.isGesetzt(guckXY[i][0], guckXY[i][1])) {
                        if (!teilTerritorium.contains(guckXY[i][0] * 10 + guckXY[i][1]) && !alleTerritorium.contains(guckXY[i][0] * 10 + guckXY[i][1])) {
                            zuChecken[i] = true;
                        }
                    } else {
                        if (feld.isFarbeWeiﬂ(guckXY[i][0], guckXY[i][1]) != farbe) {
                            check = false;
                        }
                    }
                }
            }

            if (check) {
                teilTerritorium.add(x * 10 + y);
                for (int j = 0; j < zuChecken.length; j++) {
                    if (!teilTerritorium.contains(guckXY[j][0] * 10 + guckXY[j][1]) && !alleTerritorium.contains(guckXY[j][0] * 10 + guckXY[j][1])
                            && zuChecken[j]) {
                        check &= checkTerritorium(guckXY[j][0], guckXY[j][1], farbe);
                    }
                }
            }
        }

        return check;
    }

    public void setAtari() {
        int x;
        int y;
        for (Integer a : atari) {
            x = a / 10;
            y = a % 10;
            if (feld.isFarbeWeiﬂ(x, y)) {
                feld.inkrementWeiﬂeAtari();
            } else {
                feld.inkrementSchwarzeAtari();
            }

            feld.setDefault(x, y);
        }
        atari.clear();
    }

    public void passen() {
        passen++;
    }

    public void setPassen() {
        steinEntfern = false;
        passen = 0;
    }

    public boolean isSteinEntfern() {
        return steinEntfern;
    }

    public void setSteinEntfernt(boolean steinEntfern) {
        this.steinEntfern = steinEntfern;
    }

    public int getPassen() {
        return passen;
    }

    public Feld getFeld() {
        return feld;
    }

    public void setKoRegel(boolean koRegel) {
        this.koRegel = koRegel;
    }

    public boolean isZurueck() {
        return zurueck;
    }

    public void setZurueck(boolean zurueck) {
        this.zurueck = zurueck;
    }

}
