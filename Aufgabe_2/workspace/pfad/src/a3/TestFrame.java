package a3;

/*
 * Praktikum:           P2P
 * Semester:            SS12
 * Team:                S3T5
 * Namen der Team-Mitglieder mit Matrikel-Nr.:
 *                      Borchert Pascal #2049713
 *                      Malsch Michael #1908805
 * Aufgabe:             A3
 * Datum der Abnahme    15.06.12
 * Kontrolleur:         Behnke
 * 
 * */
public class TestFrame {

    public static void main(String[] args) {
        TestFrame test = new TestFrame();
        test.start();
    }

    public void start() {
        DateirechercheGUI recherche = new DateirechercheGUI();
        recherche.start();
        //        System.out.println(recherche);
    }
}
