import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DossierContact {

    Map<String,String> contacts = new HashMap<>();

    public DossierContact() {
       readDataFiles();
    }

    private void readDataFiles() {
        File dossier = new File("./contacts");
        if(dossier.exists() && dossier.isDirectory()){
            System.out.println("Dossier contacts existe");
            File[] fichiers = dossier.listFiles();
            if (fichiers != null) {
                for (File f : fichiers) {
                    try {
                        FileReader fr = new FileReader(f);
                        BufferedReader br = new BufferedReader(fr);
                        String nom = f.getName().split("\\.")[0];
                        String numTele = br.readLine();
                        contacts.put(nom,numTele);
                        br.close();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
        }
        else{
            boolean created = dossier.mkdir();
            if (created){
                System.out.println("Dossier contacts créé avec succès");
            }
            else{
                System.out.println("Erreur lors de la création du dossier contacts");
            }
        }
    }

    public void ajouterContact(String nom, String numTele){
        if (contacts.containsKey(nom)) {
            System.out.println("Contact already exists. Use changeNumber to modify.");
        } else {

            try {
                FileWriter fw = new FileWriter("./contacts/" + nom + ".txt");
                BufferedWriter bf = new BufferedWriter(fw);
                bf.write(numTele);
                System.out.println("Contact ajouté avec succès");
                bf.close();
                contacts.put(nom, numTele);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public void changerNumero(String nom, String nouveauNumero) {
        if (contacts.containsKey(nom)) {
           contacts.replace(nom,nouveauNumero);
           contacts.forEach((k,v)->System.out.println(k+" : "+v));
           ajouterContact(nom,nouveauNumero);
           System.out.println("Numéro de téléphone modifié avec succès");
            }
        else {
            System.out.println("Contact introuvable");
        }
    }

    public void supprimerContact(String nom){
        contacts.remove(nom);
        if (new File("./contacts/"+nom+".txt").delete()){
            System.out.println("Contact supprimé avec succès");
        }
        else{
            System.out.println("Erreur lors de la suppression du contact");
        }

    }

    public void rechercherContact(String nom) {
        if (contacts.containsKey(nom)) {
            System.out.println(nom + " : " + contacts.get(nom));
        } else {
            System.out.println("Contact introuvable");
        }
    }





}
