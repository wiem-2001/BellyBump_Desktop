package tn.esprit.entities;

public class CartItem {
    private Produit product;
    private int quantity;

    public CartItem(Produit product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Produit getProduct() {
        return product;
    }

    public void setProduct(Produit product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity += quantity;
    }

    public double getTotalPrice() {
        return quantity * product.getPrix();
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }

    public void increaseQuantity()
    {
        this.quantity++;
    }
    public void decreaseQuantity()
    {
        if(this.quantity>0)
        {
            this.quantity--;
        }
    }
}
