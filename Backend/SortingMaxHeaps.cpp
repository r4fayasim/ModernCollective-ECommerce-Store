//
//  SortingMaxHeaps.cpp
//  DSA
//
//  Created by Muhammad Rafay Asim on 07/12/2025.
//
#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
#include <iomanip>

struct Product {
    int id;
    std::string name;
    int salesCount;
    int rating;
    int price;
};

class ProductMaxHeap {
private:
    std::vector<Product> heap;
    
    std::string getStarRating(int r) {
            std::string stars = "";
            for (int i = 0; i < r; i++) {
                stars += "⭐";
            }
            return stars;
        }
    
    void heapifyUp(int index) {
        if (index == 0) return;
        int parentIndex = (index - 1) / 2;
        if (heap[index].salesCount > heap[parentIndex].salesCount) {
            std::swap(heap[index], heap[parentIndex]);
            heapifyUp(parentIndex);
        }
    }
    
    void heapifyDown(int index) {
        int size = heap.size();
        int largest = index;
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        // Compare with Left Child
        if (leftChild < size && heap[leftChild].salesCount > heap[largest].salesCount) {
            largest = leftChild;
        }
        // Compare with Right Child
        if (rightChild < size && heap[rightChild].salesCount > heap[largest].salesCount) {
            largest = rightChild;
        }
        // If parent is not the largest, swap and continue sinking
        if (largest != index) {
            std::swap(heap[index], heap[largest]);
            heapifyDown(largest);
        }
    }

public:
    void addProduct(int id, std::string name, int sales, int rating, int price) {
        Product p = {id, name, sales, rating, price};
        heap.push_back(p);
        heapifyUp(heap.size() - 1);
    }

    // Gets highest seller
    Product peek() {
        if (heap.empty()) return {-1, "", -1, 0};
        return heap[0];
    }

    // Pops highest seller
    void pop() {
        if (heap.empty()) return;
        heap[0] = heap.back();
        heap.pop_back();
        heapifyDown(0);
    }

    bool isEmpty() {
        return heap.empty();
    }

   // Displays top 10 products by popping top 10 nodes in queue
    void showHomepageTop10() {
            std::cout << "\n           =================== 🔥 TRENDING NOW 🔥 ===================\n";
            // Table header
            std::cout << std::left
                      << std::setw(5)  << ""
                      << std::setw(40) << "Product Name"
                      << std::setw(12) << "Price"
                      << std::setw(15) << "Rating"
                      << "Bought by" << "\n";
            std::cout << "       ----------------------------------------------------------------------------\n";

            ProductMaxHeap tempHeap = *this;
            int count = 0;
            while (!tempHeap.isEmpty() && count < 10) {
                Product top = tempHeap.peek();
                std::cout << std::left
                          << std::setw(5)  << (count + 1)
                          << std::setw(40) << top.name
                          << "$" << std::setw(11) << top.price
                          << std::setw(15) << getStarRating(top.rating)<<"\t\t"
                          << top.salesCount << " customers\n";
                tempHeap.pop();
                count++;
            }
            std::cout << "       ============================================================================\n";
        }
    };

int main() {
    ProductMaxHeap inventory;
    inventory.addProduct(101, "White Basic Tee", 571, 4, 30);
    inventory.addProduct(102, "Tissot PRX", 122, 5, 970);
    inventory.addProduct(103, "Ralph Lauren Cable Knit Sweater", 643, 5, 200);
    inventory.addProduct(104, "Straight fit Light Washed Jeans", 1078, 3, 75);
    inventory.addProduct(105, "Black Button Down Shirt", 239, 4., 60);
    inventory.addProduct(106, "Nike Socks", 2097, 4, 15);
    inventory.addProduct(107, "Air Jordan 1's", 997, 5, 175);
    inventory.addProduct(108, "Suede Jacket", 135, 5, 550);
    inventory.addProduct(109, "Puffer Jacket", 600, 3, 330);
    inventory.addProduct(110, "Cartier Tank", 54, 5, 3000);
    inventory.addProduct(111, "Blue Quarter-Zip", 751, 4, 120);
    inventory.addProduct(112, "Davidoff Cool Water", 320, 3, 35);

    inventory.showHomepageTop10();

    return 0;
}
