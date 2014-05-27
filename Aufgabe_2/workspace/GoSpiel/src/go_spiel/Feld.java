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
public class Feld {

    private boolean[][][] felder;
    private final int anzSteineproZeile = 9; // immer einer mehr als es Felder gibt
    private final int anzSteineproSpalte = 9; // immer einer mehr als es Felder gibt
    private int schwarzeAtari;
    private int weiﬂeAtari;
    private int gesetzteSchwarze;
    private int gesetzteWeiﬂe;
    private int weiﬂeTerritorium;
    private int schwarzTerritorium;

    public Feld(boolean[][][] felder, int schwarzeAtari, int weiﬂeAtari, int gesetzteSchwarze, int gesetzteWeiﬂe) {
        this.felder = felder;
        this.weiﬂeAtari = weiﬂeAtari;
        this.schwarzeAtari = schwarzeAtari;
        this.gesetzteSchwarze = gesetzteSchwarze;
        this.gesetzteWeiﬂe = gesetzteWeiﬂe;
    }

    public Feld() {
        felder = new boolean[getAnzSteineproSpalte()][getAnzSteineproZeile()][2];
    }

    public boolean[][][] getFelder() {
        boolean[][][] neueFelder = new boolean[getAnzSteineproSpalte()][getAnzSteineproZeile()][3];
        ;

        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                for (int k = 0; k < felder[i][j].length; k++) {
                    neueFelder[i][j][k] = felder[i][j][k];
                }
            }
        }

        return neueFelder;
    }

    public boolean isGesetzt(int x, int y) {
        return this.felder[x][y][0];
    }

    public void setGesetzt(int x, int y) {
        this.felder[x][y][0] = true;
        if (isFarbeWeiﬂ(x, y)) {
            gesetzteWeiﬂe++;
        } else {
            gesetzteSchwarze++;
        }
    }

    public void setNichtGesetzt(int x, int y) {
        this.felder[x][y][0] = false;
        if (isFarbeWeiﬂ(x, y)) {
            gesetzteWeiﬂe--;
        } else {
            gesetzteSchwarze--;
        }
    }

    public void setDefault(int x, int y) {
        setNichtGesetzt(x, y);
        setFarbeSchwarz(x, y);
    }

    public boolean isFarbeWeiﬂ(int x, int y) {
        return this.felder[x][y][1];
    }

    public void setFarbeWeiﬂ(int x, int y) {
        this.felder[x][y][1] = true; // true --> Weiﬂerstein
    }

    public void setFarbeSchwarz(int x, int y) {
        this.felder[x][y][1] = false; // false --> Schwarzerstein
    }

    public int getAnzahlSchwarzerAtari() {
        return schwarzeAtari;
    }

    public int getAnzahlWeiﬂerAtari() {
        return weiﬂeAtari;
    }

    public int getGesetzteSchwarze() {
        return gesetzteSchwarze;
    }

    public int getGesetzteWeiﬂe() {
        return gesetzteWeiﬂe;
    }

    public void setFelder(boolean[][][] felder) {
        this.felder = felder;
    }

    public int getAnzSteineproSpalte() {
        return anzSteineproSpalte;
    }

    public int getAnzSteineproZeile() {
        return anzSteineproZeile;
    }

    public void inkrementWeiﬂeAtari() {
        this.weiﬂeAtari++;
    }

    public void inkrementSchwarzeAtari() {
        this.schwarzeAtari++;
    }

    public int getWeiﬂeTerritorium() {
        return weiﬂeTerritorium;
    }

    public void setWeiﬂeTerritorium(int weiﬂeTerritorium) {
        this.weiﬂeTerritorium = weiﬂeTerritorium;
    }

    public int getSchwarzTerritorium() {
        return schwarzTerritorium;
    }

    public void setSchwarzTerritorium(int schwarzTerritorium) {
        this.schwarzTerritorium = schwarzTerritorium;
    }

}
