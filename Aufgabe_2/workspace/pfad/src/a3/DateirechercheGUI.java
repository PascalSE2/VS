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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;

public class DateirechercheGUI {
    JFileChooser fc;
    JFrame frame = new JFrame();
    JFormattedTextField nofField = new JFormattedTextField(NumberFormat.getNumberInstance());
    JFormattedTextField nodField = new JFormattedTextField(NumberFormat.getNumberInstance());
    JLabel nofLabel = new JLabel("Bitte geben sie die anzahl der Dateien an:  ");
    JLabel nodLabel = new JLabel("Bitte geben sie die anzahl der Verzeichnisse an:  ");
    JButton suchenButton = new JButton("Suchen");
    JButton speicherButton = new JButton("Speichern");
    File eingabePfad;
    File ausgabePfad;
    
    boolean suche;

    Dateirecherche recherche = new Dateirecherche();

    public void start() {

        nofField.setValue(0);
        nodField.setValue(0);
        
        suchenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                recherche.setNof(Integer.parseInt(nofField.getText()));
                recherche.setNod(Integer.parseInt(nodField.getText()));
                dateiAuswahl();
                recherche.suche(eingabePfad, 0);
                System.out.println(recherche);
                suche = true;
            }
        });

        speicherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (suche) {
                    ausgabe(recherche);
                }

            }
        });

        frame.setLayout(new GridLayout(0, 2, 6, 3));
        frame.add(nofLabel);
        frame.add(nodLabel);
        frame.add(nofField);
        frame.add(nodField);
        frame.add(suchenButton);
        frame.add(speicherButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

    }

    private void dateiAuswahl() {
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.showOpenDialog(null);
        eingabePfad = fc.getSelectedFile();

    }

    private void ausgabe(Dateirecherche recherche) {
        fc.showSaveDialog(null);
        ausgabePfad = fc.getSelectedFile();
        try {
            FileWriter fw = new FileWriter(ausgabePfad);
            fw.write(recherche.toString());
            fw.close();
        } catch (IOException e) {
            System.err.println("Pfad existiert nicht!!!");
        }
    }
}
