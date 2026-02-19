package com.gestionpaie.utils;

import com.gestionpaie.model.BulletinPaie;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Générateur simple de bulletin en CSV (remplace l'usage d'iText).
 */
public class PDFGenerator {

    public static void genererBulletin(BulletinPaie bulletin, String chemin) {
        // Ecrit un fichier CSV minimal contenant les infos du bulletin
        try (PrintWriter pw = new PrintWriter(new FileWriter(chemin))) {
            pw.println("Nom;Prenom;Matricule;DatePaie;SalaireBrut;TotalPrimes;TotalRetenues;SalaireNet");
            pw.println(String.join(";",
                    safe(bulletin.getEmploye().getNom()),
                    safe(bulletin.getEmploye().getPrenom()),
                    safe(bulletin.getEmploye().getMatricule()),
                    String.valueOf(bulletin.getDatePaie()),
                    String.valueOf(bulletin.getSalaireBrut()),
                    String.valueOf(bulletin.getTotalPrimes()),
                    String.valueOf(bulletin.getTotalRetenues()),
                    String.valueOf(bulletin.getSalaireNet())
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String safe(Object o) {
        return o == null ? "" : o.toString();
    }
}
package com.gestionpaie.utils;

import com.gestionpaie.model.BulletinPaie;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Générateur simple de bulletin en CSV (remplace l'usage d'iText).
 */
public class PDFGenerator {

    public static void genererBulletin(BulletinPaie bulletin, String chemin) {
        // Ecrit un fichier CSV minimal contenant les infos du bulletin
        try (PrintWriter pw = new PrintWriter(new FileWriter(chemin))) {
            pw.println("Nom;Prenom;Matricule;DatePaie;SalaireBrut;TotalPrimes;TotalRetenues;SalaireNet");
            pw.println(String.join(";",
                    safe(bulletin.getEmploye().getNom()),
                    safe(bulletin.getEmploye().getPrenom()),
                    safe(bulletin.getEmploye().getMatricule()),
                    String.valueOf(bulletin.getDatePaie()),
                    String.valueOf(bulletin.getSalaireBrut()),
                    String.valueOf(bulletin.getTotalPrimes()),
                    String.valueOf(bulletin.getTotalRetenues()),
                    String.valueOf(bulletin.getSalaireNet())
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String safe(Object o) {
        return o == null ? "" : o.toString();
    }
}
