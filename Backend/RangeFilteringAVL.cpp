//
//  RangeFilteringAVL.cpp
//  DSA
//
//  Created by Muhammad Rafay Asim on 06/12/2025.
//
#include <iostream>
#include <vector>
#include <string>
#include <iomanip>

struct Product {
    int id;
    std::string name;
    int price;
    int rating;
    Product(int i, std::string n, int p, int r)
        : id(i), name(n), price(p), rating(r) {}
};

struct Node {
    Product* product;
    Node* left;
    Node* right;
    int height;
    Node(Product* p) : product(p), left(nullptr), right(nullptr), height(1) {}
};

class DualTreeStore {
private:
    Node* priceRoot;
    Node* ratingRoot;
    // Insertion based on price
    Node* insertPrice(Node* node, Product* p) {
        if (!node) return new Node(p);
        // Price based comparison
        if (p->price < node->product->price)
            node->left = insertPrice(node->left, p);
        else
            node->right = insertPrice(node->right, p);
        return node;
    }

    // Insertion based on rating
    Node* insertRating(Node* node, Product* p) {
        if (!node) return new Node(p);
        // Rating based comparison
        if (p->rating < node->product->rating)
            node->left = insertRating(node->left, p);
        else
            node->right = insertRating(node->right, p);
        // Right biasness, equal ratings pushes node to right
        return node;
    }

    // InOrder traversel
    void printInOrder(Node* node) {
        if (!node) return;
        printInOrder(node->left);
        
        std::cout << " - " << std::left << std::setw(15) << node->product->name
                  << " | Price: $" << std::setw(4) << node->product->price
                  << " | Rating: " << node->product->rating << "/5\n";
                  
        printInOrder(node->right);
    }

public:
    DualTreeStore() : priceRoot(nullptr), ratingRoot(nullptr) {}
    void addProduct(int id, std::string name, int price, int rating) {
        Product* newProd = new Product(id, name, price, rating);
        priceRoot = insertPrice(priceRoot, newProd);
        ratingRoot = insertRating(ratingRoot, newProd);
    }
    // Sorting by price
    void showSortedByPrice() {
        std::cout << "\n--- PRICE (Low to High) ---\n";
        printInOrder(priceRoot);
    }
    // Sorting by rating
    void showSortedByRating() {
        std::cout << "\n--- RATING (Low to High) ---\n";
        printInOrder(ratingRoot);
    }
};

int main() {
    DualTreeStore store;
    store.addProduct(1, "Nike Socks", 10, 3);
    store.addProduct(2, "Omega Seamaster", 5499, 5);
    store.addProduct(3, "Button down Shirt", 70, 4);
    store.addProduct(4, "Suede Jacket", 350, 4);
    store.addProduct(5, "Straight fit Light Washed Jeans", 60, 5);
    store.showSortedByPrice();
    store.showSortedByRating();

    return 0;
}
