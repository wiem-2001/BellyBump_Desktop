package tn.esprit.services;

import tn.esprit.entities.CartItem;
import tn.esprit.entities.Produit;

import java.security.PublicKey;
import java.util.*;

public class CartServices {

    private  static CartServices INSTANCE;
    public  static CartServices getInstance()
    {if(INSTANCE==null)
    {
        INSTANCE = new CartServices();
    }
return INSTANCE;
    }
    private Map<String, CartItem> entries;


    private CartServices() {
        this.entries = new HashMap<>();
    }  // Private constructor


    /* public void addProduct(String productName) {
        CartItem productEntry= entries.get(productName.toUpperCase());
        if(productEntry!=null){
            productEntry.increaseQuantity();
        }else {
            ProduitServices ps =new ProduitServices();
            Produit product =new Produit();
            product= ps.findByName(productName);
           // Produit product= Produit.ValuOf(productName);
            CartItem entry = new CartItem(product,1);
            entries.put(productName.toUpperCase(),entry);
        }
     }*/
    public void addProduct(String productName) {
        // Cherche l'entrée du produit dans le panier avec le nom du produit en majuscules
        CartItem productEntry = entries.get(productName.toUpperCase());

        if (productEntry != null) {
            // Si le produit est déjà dans le panier, augmente sa quantité
            productEntry.increaseQuantity();
        } else {
            // Si le produit n'est pas dans le panier, utilise ProduitServices pour le trouver
            ProduitServices ps = new ProduitServices();
            Produit product = ps.findByName(productName);

            if (product != null) {
                // Si le produit est trouvé, crée une nouvelle entrée de panier avec ce produit
                CartItem entry = new CartItem(product, 1);
                entries.put(productName.toUpperCase(), entry);
                // Dans CartServices.addProduct, après avoir ajouté un produit


            } else {
                // Si aucun produit n'est trouvé avec ce nom, vous pouvez choisir de traiter cette situation,
                // par exemple, en loggant une erreur ou en lançant une exception.
                System.err.println("Le produit avec le nom '" + productName + "' n'a pas été trouvé.");
            }
        }
        System.out.println("Produit ajouté : " + productName);

            System.out.println("Quantité actuelle : " + getQuantity(productName));


    }

    public void removeProduct(String productName) {

        CartItem productEntry = entries.get(productName.toUpperCase());
        if (productEntry != null) {
            productEntry.decreaseQuantity();
        }
    }

    public int getQuantity(String productName){
        CartItem entry =entries.get(productName.toUpperCase());
        if(entry!=null){
            return entry.getQuantity();
        }
        return 0;
    }

    public float CalculateTotal(){
        float total=0;
        for (CartItem entry: entries.values()) {
            float entryCost = (float) (entry.getProduct().getPrix()* entry.getQuantity());
            total = total +entryCost;
        }
        return total;
        }


 public List<CartItem> getEntries()
 {
     return new ArrayList<>(entries.values());
 }


    }




