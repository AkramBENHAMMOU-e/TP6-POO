package application;

import dao.IMetier;
import dao.MetierClientImp;
import models.Client;

import java.util.Scanner;

public class ApplicationClient {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        IMetier mp = new MetierClientImp();
        int continuer;
        do{
            System.out.println("Menu : ");
            System.out.println("1. Afficher la liste des clients.\n" +
                    "2. Rechercher un client par son nom.\n" +
                    "3. Ajouter un nouveau client dans la liste.\n" +
                    "4. Supprimer un client par nom.\n" +
                    "5. Sauvegarder les clients : cette option permet de sauvegarder la liste des\n" +
                    "clients dans fichier nommé clients.dat.\n" +
                    "6. Quitter ce programme.");
            System.out.println("Votre choix (Numéro) : ");
            int i=sc.nextInt();
            sc.nextLine();
            switch (i){
                case 1 :
                    System.out.println("Les clients : "+mp.getAll());
                    break;
                case 2 :
                    System.out.println("Entrer un nom : ");
                    String mtcl = sc.nextLine();
                    System.out.println(mp.findByNom(mtcl));
                    break;
                case 3 :
                    System.out.println("Entrer le nom : ");
                    String nom = sc.nextLine();
                    System.out.println("entrer le prenom: ");
                    String prenom = sc.nextLine();
                    System.out.println("entrer l'adresse: ");
                    String adresse = sc.nextLine();
                    System.out.println("entrer le num de telephone : ");
                    String tel = sc.nextLine();
                    System.out.println("entrer l'email : ");
                    String email = sc.nextLine();
                    Client p = new Client(nom,adresse,prenom,tel,email);
                    mp.add(p);
                    break;
                case 4 :
                    System.out.println("donner le nom du client à supprimer : ");
                    String nm = sc.nextLine();
                    mp.delete(nm);
                    System.out.println("Client suppimé avec succès ");
                    break;
                case 5 :
                    mp.saveAll();
                    break;
                default:
                    System.out.println("Choix invalide");
            }
            System.out.println("Voulez vous rechoisir : NON(0) OUI(1)");
            continuer = sc.nextInt();
            sc.nextLine();

        }while (continuer==1);
    }

}
