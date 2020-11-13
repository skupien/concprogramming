package lab2;

public class Shop {
    int clients;
    int shoppingCarts;

    public Shop(int clients, int shoppingCarts) {
        this.clients = clients;
        this.shoppingCarts = shoppingCarts;
    }

    public void runShop() {
        CountingSemaphore semaphore = new CountingSemaphore(this.shoppingCarts);
        for(int i=0; i<this.clients; i++) {
            new Thread(
                    new ClientThread(semaphore)
            ).start();
        }
    }

}
