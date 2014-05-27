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
    private int wei�eAtari;
    private int gesetzteSchwarze;
    private int gesetzteWei�e;
    private int wei�eTerritorium;
    private int schwarzTerritorium;

    public Feld(boolean[][][] felder, int schwarzeAtari, int wei�eAtari, int gesetzteSchwarze, int gesetzteWei�e) {
        this.felder = felder;
        this.wei�eAtari = wei�eAtari;
        this.schwarzeAtari = schwarzeAtari;
        this.gesetzteSchwarze = gesetzteSchwarze;
        this.gesetzteWei�e = gesetzteWei�e;
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
        if (isFarbeWei�(x, y)) {
            gesetzteWei�e++;
        } else {
            gesetzteSchwarze++;
        }
    }

    public void setNichtGesetzt(int x, int y) {
        this.felder[x][y][0] = false;
        if (isFarbeWei�(x, y)) {
            gesetzteWei�e--;
        } else {
            gesetzteSchwarze--;
        }
    }

    public void setDefault(int x, int y) {
        setNichtGesetzt(x, y);
        setFarbeSchwarz(x, y);
    }

    public boolean isFarbeWei�(int x, int y) {
        return this.felder[x][y][1];
    }

    public void setFarbeWei�(int x, int y) {
        this.felder[x][y][1] = true; // true --> Wei�erstein
    }

    public void setFarbeSchwarz(int x, int y) {
        this.felder[x][y][1] = false; // false --> Schwarzerstein
    }

    public int getAnzahlSchwarzerAtari() {
        return schwarzeAtari;
    }

    public int getAnzahlWei�erAtari() {
        return wei�eAtari;
    }

    public int getGesetzteSchwarze() {
        return gesetzteSchwarze;
    }

    public int getGesetzteWei�e() {
        return gesetzteWei�e;
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

    public void inkrementWei�eAtari() {
        this.wei�eAtari++;
    }

    public void inkrementSchwarzeAtari() {
        this.schwarzeAtari++;
    }

    public int getWei�eTerritorium() {
        return wei�eTerritorium;
    }

    public void setWei�eTerritorium(int wei�eTerritorium) {
        this.wei�eTerritorium = wei�eTerritorium;
    }

    public int getSchwarzTerritorium() {
        return schwarzTerritorium;
    }

    public void setSchwarzTerritorium(int schwarzTerritorium) {
        this.schwarzTerritorium = schwarzTerritorium;
    }

}
