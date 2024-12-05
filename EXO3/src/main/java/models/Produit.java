package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
@AllArgsConstructor @ToString
public class Produit implements Serializable{
    @Getter @Setter
    private String nom;
    @Getter @Setter
    private double prix;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private int nbr_stock;

}

