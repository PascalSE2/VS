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
import java.io.File;
import java.util.Comparator;

public class DateiLaengenComparator implements Comparator<Object> {

    @Override
    public int compare(Object f1, Object f2) {
        int comp = 0;
        if (f1 instanceof File && f2 instanceof File) {
            comp = (int) (((File) f2).length() - ((File) f1).length());
        }
        return comp;
    }

}
