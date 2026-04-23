#include <iostream>
#include <string>
#include <vector>
#include <iomanip>

struct ProductNode {
    int id;
    std::string name;
    double price;
    int stockCount;
    ProductNode* next;

    ProductNode(int i, std::string n, double p, int s)
        : id(i), name(n), price(p), stockCount(s), next(nullptr) {}
};

struct CartItem {
    int productId;
    std::string name;
    double price;
    int quantity;
};

class ManualShoppingSystem {
private:
    static const int TABLE_SIZE = 10;
    ProductNode* inventoryBuckets[TABLE_SIZE];
    
    std::vector<CartItem> cart;

    int hashFunction(int id) {
        return id % TABLE_SIZE;
    }

public:
    ManualShoppingSystem() {
        for (int i = 0; i < TABLE_SIZE; i++) inventoryBuckets[i] = nullptr;
    }

    // Add to Inventory (Manual Hashing)
    void addProduct(int id, std::string name, double price, int stock) {
        int index = hashFunction(id);
        ProductNode* newNode = new ProductNode(id, name, price, stock);

        if (inventoryBuckets[index] == nullptr) {
            inventoryBuckets[index] = newNode;
        } else {
            ProductNode* current = inventoryBuckets[index];
            while(current->next != nullptr) current = current->next;
            current->next = newNode;
        }
    }

    ProductNode* findProduct(int id) {
        int index = hashFunction(id);
        ProductNode* current = inventoryBuckets[index];
        
        while (current != nullptr) {
            if (current->id == id) return current;
            current = current->next;
        }
        return nullptr;
    }

    // Adding to cart
    void addToCart(int id) {
        ProductNode* product = findProduct(id);
        
        if (product == nullptr) {
            std::cout << "❌ Error: ID " << id << " not found in inventory.\n";
            return;
        }
        if (product->stockCount <= 0) {
            std::cout << "⚠️ Sorry, " << product->name << " is out of stock.\n";
            return;
        }

        product->stockCount--;

        bool foundInCart = false;
        for (auto &item : cart) {
            if (item.productId == id) {
                item.quantity++;
                foundInCart = true;
                break;
            }
        }

        if (!foundInCart) {
            cart.push_back({id, product->name, product->price, 1});
        }

        std::cout << "✅ Added " << product->name << ". Stock left: " << product->stockCount << "\n";
    }

    void viewCart() {
        std::cout << "\n--- 🛒 MANUAL CART ---\n";
        double total = 0;
        for (auto item : cart) {
            double sub = item.price * item.quantity;
            total += sub;
            std::cout << item.name << " x" << item.quantity << " = $" << sub << "\n";
        }
        std::cout << "Total: $" << total << "\n";
    }
};

int main() {
    ManualShoppingSystem store;
    store.addProduct(101, "Nike Socks", 25.00, 10);
    store.addProduct(102, "Sweater", 90.00, 2);

    store.addToCart(101);
    store.addToCart(101);
    store.addToCart(102);
    store.addToCart(999); 

    store.viewCart();
    return 0;
}
