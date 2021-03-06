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
    private int weißeAtari;
    private int gesetzteSchwarze;
    private int gesetzteWeiße;
    private int weißeTerritorium;
    private int schwarzTerritorium;

    public Feld(boolean[][][] felder, int schwarzeAtari, int weißeAtari, int gesetzteSchwarze, int gesetzteWeiße) {
        this.felder = felder;
        this.weißeAtari = weißeAtari;
        this.schwarzeAtari = schwarzeAtari;
        this.gesetzteSchwarze = gesetzteSchwarze;
        this.gesetzteWeiße = gesetzteWeiße;
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
        if (isFarbeWeiß(x, y)) {
            gesetzteWeiße++;
        } else {
            gesetzteSchwarze++;
        }
    }

    public void setNichtGesetzt(int x, int y) {
        this.felder[x][y][0] = false;
        if (isFarbeWeiß(x, y)) {
            gesetzteWeiße--;
        } else {
            gesetzteSchwarze--;
        }
    }

    public void setDefault(int x, int y) {
        setNichtGesetzt(x, y);
        setFarbeSchwarz(x, y);
    }

    public boolean isFarbeWeiß(int x, int y) {
        return this.felder[x][y][1];
    }

    public void setFarbeWeiß(int x, int y) {
        this.felder[x][y][1] = true; // true --> Weißerstein
    }

    public void setFarbeSchwarz(int x, int y) {
        this.felder[x][y][1] = false; // false --> Schwarzerstein
    }

    public int getAnzahlSchwarzerAtari() {
        return schwarzeAtari;
    }

    public int getAnzahlWeißerAtari() {
        return weißeAtari;
    }

    public int getGesetzteSchwarze() {
        return gesetzteSchwarze;
    }

    public int getGesetzteWeiße() {
        return gesetzteWeiße;
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

    public void inkrementWeißeAtari() {
        this.weißeAtari++;
    }

    public void inkrementSchwarzeAtari() {
        this.schwarzeAtari++;
    }

    public int getWeißeTerritorium() {
        return weißeTerritorium;
    }

    public void setWeißeTerritorium(int weißeTerritorium) {
        this.weißeTerritorium = weißeTerritorium;
    }

    public int getSchwarzTerritorium() {
        return schwarzTerritorium;
    }

    public void setSchwarzTerritorium(int schwarzTerritorium) {
        this.schwarzTerritorium = schwarzTerritorium;
    }

}
