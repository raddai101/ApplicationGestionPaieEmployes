package com.gestionpaie.utils;

import com.gestionpaie.model.BulletinPaie;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Générateur de bulletins en HTML et CSV
 * Génère des fichiers formatés pour archivage et impression
 */
public class PDFGenerator {
    
    private static final DecimalFormat FORMAT_MONEY = new DecimalFormat("0.00");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Génère un bulletin au format HTML professionnel
     */
    public static String genererBulletinHTML(BulletinPaie bulletin) {
        try {
            String filename = String.format("Bulletin_%s_%s.html",
                    bulletin.getEmploye().getMatricule(),
                    bulletin.getDatePaie().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            
            String cheminComplet = AppConfig.getBulletinFilePath(filename);
            
            try (PrintWriter pw = new PrintWriter(new FileWriter(cheminComplet))) {
                pw.println("<!DOCTYPE html>");
                pw.println("<html lang='fr'>");
                pw.println("<head>");
                pw.println("  <meta charset='UTF-8'>");
                pw.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                pw.println("  <title>Bulletin de paie - " + bulletin.getEmploye().getMatricule() + "</title>");
                pw.println("  <style>");
                pw.println("    body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }");
                pw.println("    .container { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); max-width: 900px; margin: 0 auto; }");
                pw.println("    .header { border-bottom: 3px solid #0a66c2; padding-bottom: 20px; margin-bottom: 20px; }");
                pw.println("    .header h1 { margin: 0; color: #0a66c2; font-size: 24px; }");
                pw.println("    .company { color: #666; margin-top: 5px; }");
                pw.println("    .section { margin: 20px 0; }");
                pw.println("    .section h2 { background-color: #f0f0f0; padding: 10px; border-left: 4px solid #0a66c2; margin: 0; font-size: 14px; }");
                pw.println("    table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
                pw.println("    th { background-color: #0a66c2; color: white; padding: 10px; text-align: left; font-weight: bold; }");
                pw.println("    td { padding: 8px; border-bottom: 1px solid #ddd; }");
                pw.println("    tr:hover { background-color: #f9f9f9; }");
                pw.println("    .label { font-weight: bold; width: 50%; }");
                pw.println("    .amount { text-align: right; }");
                pw.println("    .total-section { margin-top: 20px; padding: 15px; background-color: #f0f8ff; border-left: 4px solid #0a66c2; }");
                pw.println("    .total-row { display: flex; justify-content: space-between; padding: 5px 0; font-size: 16px; }");
                pw.println("    .total-label { font-weight: bold; }");
                pw.println("    .total-amount { font-weight: bold; color: #0a66c2; }");
                pw.println("    .footer { margin-top: 30px; text-align: center; font-size: 12px; color: #999; border-top: 1px solid #ddd; padding-top: 15px; }");
                pw.println("    .net-amount { font-size: 18px; color: #28a745; }");
                pw.println("  </style>");
                pw.println("</head>");
                pw.println("<body>");
                pw.println("  <div class='container'>");
                pw.println("    <div class='header'>");
                pw.println("      <h1>BULLETIN DE PAIE</h1>");
                pw.println("      <div class='company'>Squad Brojex - Gestion de Paie</div>");
                pw.println("    </div>");
                
                // Infos employé
                pw.println("    <div class='section'>");
                pw.println("      <h2>Informations de l'employé</h2>");
                pw.println("      <table>");
                pw.println("        <tr><td class='label'>Nom:</td><td>" + safe(bulletin.getEmploye().getNom()) + "</td></tr>");
                pw.println("        <tr><td class='label'>Prénom:</td><td>" + safe(bulletin.getEmploye().getPrenom()) + "</td></tr>");
                pw.println("        <tr><td class='label'>Matricule:</td><td>" + safe(bulletin.getEmploye().getMatricule()) + "</td></tr>");
                pw.println("        <tr><td class='label'>Email:</td><td>" + safe(bulletin.getEmploye().getEmail()) + "</td></tr>");
                pw.println("      </table>");
                pw.println("    </div>");
                
                // Détails paie
                pw.println("    <div class='section'>");
                pw.println("      <h2>Détails du bulletin</h2>");
                pw.println("      <table>");
                pw.println("        <tr>");
                pw.println("          <th>Libellé</th>");
                pw.println("          <th class='amount'>Montant</th>");
                pw.println("        </tr>");
                pw.println("        <tr><td>Date de paie</td><td class='amount'>" + bulletin.getDatePaie().format(DATE_FORMATTER) + "</td></tr>");
                pw.println("        <tr><td>Salaire de base</td><td class='amount'>" + FORMAT_MONEY.format(bulletin.getSalaireBrut() - bulletin.getTotalPrimes()) + " €</td></tr>");
                pw.println("        <tr><td>Total primes</td><td class='amount'>" + FORMAT_MONEY.format(bulletin.getTotalPrimes()) + " €</td></tr>");
                pw.println("      </table>");
                pw.println("    </div>");
                
                // Salaire brut
                pw.println("    <div class='section'>");
                pw.println("      <table>");
                pw.println("        <tr style='background-color: #fff3cd;'>");
                pw.println("          <td class='label'>SALAIRE BRUT</td>");
                pw.println("          <td class='amount' style='font-weight: bold; font-size: 16px;'>" + FORMAT_MONEY.format(bulletin.getSalaireBrut()) + " €</td>");
                pw.println("        </tr>");
                pw.println("      </table>");
                pw.println("    </div>");
                
                // Retenues
                pw.println("    <div class='section'>");
                pw.println("      <h2>Retenues et cotisations</h2>");
                pw.println("      <table>");
                pw.println("        <tr>");
                pw.println("          <th>Libellé</th>");
                pw.println("          <th class='amount'>Montant</th>");
                pw.println("        </tr>");
                pw.println("        <tr><td>Total retenues</td><td class='amount'>" + FORMAT_MONEY.format(bulletin.getTotalRetenues()) + " €</td></tr>");
                pw.println("      </table>");
                pw.println("    </div>");
                
                // Résumé final
                pw.println("    <div class='total-section'>");
                pw.println("      <div class='total-row'>");
                pw.println("        <span class='total-label'>Salaire Brut:</span>");
                pw.println("        <span class='total-amount'>" + FORMAT_MONEY.format(bulletin.getSalaireBrut()) + " €</span>");
                pw.println("      </div>");
                pw.println("      <div class='total-row'>");
                pw.println("        <span class='total-label'>Retenues totales:</span>");
                pw.println("        <span class='total-amount'>-" + FORMAT_MONEY.format(bulletin.getTotalRetenues()) + " €</span>");
                pw.println("      </div>");
                pw.println("      <div class='total-row' style='border-top: 2px solid #0a66c2; padding-top: 10px; margin-top: 10px; font-size: 18px;'>");
                pw.println("        <span class='total-label'>SALAIRE NET À PAYER:</span>");
                pw.println("        <span class='net-amount'>" + FORMAT_MONEY.format(bulletin.getSalaireNet()) + " €</span>");
                pw.println("      </div>");
                pw.println("    </div>");
                
                pw.println("    <div class='footer'>");
                pw.println("      <p>Généré le " + LocalDate.now().format(DATE_FORMATTER) + "</p>");
                pw.println("      <p>Ce document est confidentiel et destiné au seul usage de l'employé.</p>");
                pw.println("    </div>");
                pw.println("  </div>");
                pw.println("</body>");
                pw.println("</html>");
            }
            
            return cheminComplet;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du bulletin HTML: " + e.getMessage(), e);
        }
    }

    /**
     * Génère un bulletin au format CSV
     */
    public static String genererBulletinCSV(BulletinPaie bulletin) {
        try {
            String filename = String.format("Bulletin_%s_%s.csv",
                    bulletin.getEmploye().getMatricule(),
                    bulletin.getDatePaie().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            
            String cheminComplet = AppConfig.getBulletinFilePath(filename);
            
            try (PrintWriter pw = new PrintWriter(new FileWriter(cheminComplet))) {
                pw.println("Bulletin de Paie");
                pw.println("Date;Nom;Prénom;Matricule;Email;Salaire Brut;Primes;Retenues;Salaire Net");
                pw.println(String.join(";",
                        bulletin.getDatePaie().format(DATE_FORMATTER),
                        safe(bulletin.getEmploye().getNom()),
                        safe(bulletin.getEmploye().getPrenom()),
                        safe(bulletin.getEmploye().getMatricule()),
                        safe(bulletin.getEmploye().getEmail()),
                        FORMAT_MONEY.format(bulletin.getSalaireBrut()),
                        FORMAT_MONEY.format(bulletin.getTotalPrimes()),
                        FORMAT_MONEY.format(bulletin.getTotalRetenues()),
                        FORMAT_MONEY.format(bulletin.getSalaireNet())
                ));
            }
            
            return cheminComplet;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du bulletin CSV: " + e.getMessage(), e);
        }
    }

    private static String safe(Object o) {
        return o == null ? "" : o.toString();
    }
}
