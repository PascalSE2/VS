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
import java.text.DecimalFormat;

public class Dateirecherche {

    File[] groessteDateien;
    File[] groessteVerzeichnisse;
    Long[] groeesteVerzeichnisseLength;

    int nof;
    int nod;

    public long suche(File file, long dirLength) {

        if (file != null && file.canRead()) {
            File[] fileList = file.listFiles();
            if (fileList != null) {
                for (File f : fileList) {
                    if (f.isDirectory()) {
                        dirLength += suche(f, 0);

                    } else {
                        addAlleDateien(f);
                    }
                    dirLength += f.length();
                }
                addAlleVerzeichnisse(file, dirLength);
            }
        }

        return dirLength;
    }

    private void addAlleDateien(File file) {

        for (int i = 0; i < groessteDateien.length; i++) {
            if (groessteDateien[i] != null) {
                if (file.length() > groessteDateien[i].length()) {
                    File temp = groessteDateien[i];
                    groessteDateien[i] = file;
                    file = temp;
                }

            } else {
                groessteDateien[i] = file;
                break;
            }
        }
    }

    public void addAlleVerzeichnisse(File file, long dirLength) {

        for (int i = 0; i < groessteVerzeichnisse.length; i++) {
            if (groessteVerzeichnisse[i] != null) {
                if (dirLength >= groeesteVerzeichnisseLength[i]) {
                    File temp1 = groessteVerzeichnisse[i];
                    groessteVerzeichnisse[i] = file;
                    file = temp1;

                    Long temp2 = groeesteVerzeichnisseLength[i];
                    groeesteVerzeichnisseLength[i] = dirLength;
                    dirLength = temp2;
                }

            } else {
                groessteVerzeichnisse[i] = file;
                groeesteVerzeichnisseLength[i] = dirLength;
                break;
            }
        }
    }

    private String listToString(File[] alleDateien, String tmp, int ende, boolean verzeichnisse) {
        int count = 0;
        DecimalFormat df = new DecimalFormat(",###,###,###,###,###,###,###,###,###");

        tmp += "  Y   Z\r\n" + "  o   e       P   T   G   M   K   B\r\n" + "  t   t   E   e   e   i   e   i   y\r\n"
                + "  t   t   x   t   r   g   g   l   t\r\n" + "..a ..a ..a ..a ..a ..a ..a ..o ..e\r\n\r\n";

        for (int i = 0; i < alleDateien.length; i++) {
            if (alleDateien[i] != null) {
                if (count < ende) {
                    if (verzeichnisse) {
                        tmp += String.format(" %34s %s\r\n", df.format(groeesteVerzeichnisseLength[i]), alleDateien[i]);
                    } else {
                        tmp += String.format(" %34s %s\r\n", df.format(alleDateien[i].length()), alleDateien[i]);
                    }

                }
            }

            count++;
        }

        tmp += "\r\n..Y ..Z ..E ..P ..T ..G ..M ..K ..B\r\n" + "  o   e   x   e   e   i   e   i   y\r\n" + "  t   t   a   t   r   g   g   l   t \r\n"
                + "  t   t       a   a   a   a   o   e \r\n" + "  a   a       \r\n\r\n";
        return tmp;
    }

    public void setNof(int nof) {
        this.nof = nof;
        groessteDateien = new File[nof];
    }

    public void setNod(int nod) {
        this.nod = nod;
        groessteVerzeichnisse = new File[nod];
        groeesteVerzeichnisseLength = new Long[nod];
    }

    public String toString() {
        String tmp = "";

        tmp += "The top " + nof + " file:\r\n" + "=======================\r\n";
        tmp = listToString(groessteDateien, tmp, nof, false);

        tmp += "The top " + nod + " directories:\r\n" + "=======================\r\n";
        tmp = listToString(groessteVerzeichnisse, tmp, nod, true);

        return tmp;
    }

}
