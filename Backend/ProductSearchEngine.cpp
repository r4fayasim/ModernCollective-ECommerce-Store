//
//  ProductSearchEngine.cpp
//  DSA
//
//  Created by Muhammad Rafay Asim on 04/12/2025.
//
#include <iostream>
#include <string>
#include <vector>
#include <unordered_map>
#include <sstream>
#include <algorithm>

class ProductSearchEngine {
private:
    std::unordered_map<std::string, std::vector<int>> invertedIndex;

    std::string toLowerCase(std::string str) {
        std::transform(str.begin(), str.end(), str.begin(), ::tolower);
        return str;
    }

public:
    void addProduct(int productId, const std::string& productName) {
        std::stringstream ss(productName);
        std::string word;

        while (ss >> word) {
            word = toLowerCase(word);
            invertedIndex[word].push_back(productId);
        }
    }
    std::vector<int> searchByKeyword(std::string keyword) {
        keyword = toLowerCase(keyword);

        if (invertedIndex.find(keyword) != invertedIndex.end()) {
            return invertedIndex[keyword];
        } else {
            return {};
        }
    }
};

int main() {
    ProductSearchEngine engine;

    std::cout << "Indexing products...\n";
    engine.addProduct(101, "Men's Winter Fleece Jacket");
    engine.addProduct(102, "Summer Beach Shorts");
    engine.addProduct(103, "Winter Leather Gloves");
    engine.addProduct(104, "Waterproof Hiking Boots");
    engine.addProduct(105, "Women's Winter Cardigan");

    std::string query = "Winter";
    std::cout << "Searching for: " << query << "...\n";

    std::vector<int> results = engine.searchByKeyword(query);

    if (results.empty()) {
        std::cout << "No products found.\n";
    } else {
        std::cout << "Found " << results.size() << " products with IDs: ";
        for (int id : results) {
            std::cout << id << " ";
        }
        std::cout << "\n";
    }
    return 0;
}
