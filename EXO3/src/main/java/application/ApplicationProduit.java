package application;

import dao.IMetier;
import dao.MetierProduitImp;
import models.Produit;

import java.util.Scanner;

public class ApplicationProduit {

    public static void main(String[] args) {
            Scanner sc  = new Scanner(System.in);
            IMetier mp = new MetierProduitImp();
            int continuer=0;
            do{
                System.out.println("Menu : ");
                System.out.println("1. Afficher la liste des produits.\n" +
                        "2. Rechercher un produit par son nom.\n" +
                        "3. Ajouter un nouveau produit dans la liste.\n" +
                        "4. Supprimer un produit par nom.\n" +
                        "5. Sauvegarder les produits : cette option permet de sauvegarder la liste des\n" +
                        "produits dans fichier nommé produits.dat.\n" +
                        "6. Quitter ce programme.");
                System.out.println("Votre choix (Numéro) : ");
                int i=sc.nextInt();
                sc.nextLine();
                switch (i){
                    case 1 :
                        System.out.println("Les produits : "+mp.getAll());
                        break;
                    case 2 :
                        System.out.println("Entrer un mot clé : ");
                        String mtcl = sc.nextLine();
                        System.out.println(mp.findByNom(mtcl));
                        break;
                    case 3 :
                        System.out.println("Entrer le nom de nouveu produit : ");
                        String nom = sc.nextLine();
                        System.out.println("entrer son prix: ");
                        double prx = sc.nextDouble();
                        sc.nextLine();
                        System.out.println("entrer une description : ");
                        String desc = sc.nextLine();
                        System.out.println("entrer le nombre de stock : ");
                        int nbrStck = sc.nextInt();
                        sc.nextLine();
                        Produit p = new Produit(nom,prx,desc,nbrStck);
                        mp.add(p);
                        break;
                    case 4 :
                        System.out.println("donner le nom du produit à supprimer : ");
                        String nm = sc.nextLine();
                        mp.delete(nm);
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
