package dao;

import models.Produit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MetierProduitImp implements IMetier<Produit>{
   private List<Produit> produits = new ArrayList<>();
   Produit p1 = new Produit("okkk",12,"olsm",1);

    private final String nomFile = "produits.dat";

    @Override
    public Produit add(Produit o) {
        produits.add(o);
        System.out.println(o.getNom() +" ajouté avec succès");
        return o;
    }

    @Override
    public List<Produit> getAll() {
        try {
        produits.clear();
        File file = new File(nomFile);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while (true) {
                if ((line = bufferedReader.readLine()) == null) break;
            String[] values = line.split("/");
            Produit produit = new Produit(values[0], Double.parseDouble(values[1]), values[2], Integer.parseInt(values[3]));
            produits.add(produit);
        }

        bufferedReader.close();
        return produits;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Produit findByNom(String nom) {
        Produit prod= produits.stream().filter(produit -> produit.getNom().equals(nom)).findFirst().orElse(null);
        if (prod==null){
            System.out.println(nom+" n'existe pas");
        }
        return prod;
    }

    @Override
    public void delete(String nom) {
        boolean supp = produits.removeIf(produit -> produit.getNom().equals(nom));
        if(supp){
            System.out.println("Produit supprimé avec succès");
        }
        else {
            System.out.println("ce produit n'existe pas !");
        }
    }
    public void saveAll() {
        try {
            FileWriter fileWriter = new FileWriter(nomFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Produit p : produits) {
                bufferedWriter.write(p.getNom()+ "/" + p.getPrix() + "/"+ p.getDescription()  + "/" + p.getNbr_stock());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
