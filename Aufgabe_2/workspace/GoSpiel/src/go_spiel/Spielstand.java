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
public class Spielstand {
    private int altPassen;
    private boolean altSteinEntfernt;
    private Feld feld;

    public Spielstand(int altPassen, boolean altSteinEntfern, Feld feld) {
        super();
        this.altPassen = altPassen;
        this.altSteinEntfernt = altSteinEntfern;
        this.feld = feld;
    }

    public int getPassen() {
        return altPassen;
    }

    public boolean isSteinEntfernt() {
        return altSteinEntfernt;
    }

    public Feld getFeld() {
        return feld;
    }

}
