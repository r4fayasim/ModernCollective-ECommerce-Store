//
//  RecommendationSystem.cpp
//  DSA
//
//  Created by Muhammad Rafay Asim on 13/12/2025.
//
#include <iostream>
#include <vector>
#include <string>
#include <unordered_map>
#include <map>
#include <algorithm>
#include <set>

class RecommendationEngine {
private:
    // Product -> List of Users who bought it
    std::unordered_map<int, std::vector<int>> productBuyers;
    // User -> List of Products they bought
    std::unordered_map<int, std::vector<int>> userPurchases;
    // Map Product ID to Name for display
    std::unordered_map<int, std::string> productNames;

public:
    void addProduct(int id, std::string name) {
        productNames[id] = name;
    }
    // Create edge: User <---> Product
    void recordPurchase(int userId, int productId) {
        // Link Product to User
        productBuyers[productId].push_back(userId);
        // Link User to Product
        userPurchases[userId].push_back(productId);
    }

    void recommend(int currentProductID) {
        std::cout << "\n--- Recommendations for '" << productNames[currentProductID] << "' ---\n";
        // Check who bought that specific product
        if (productBuyers.find(currentProductID) == productBuyers.end()) {
            std::cout << "No data available for recommendations.\n"; //if no one bought it then no recommendations are available
            return;
        }

        std::vector<int>& buyers = productBuyers[currentProductID];
        // Map to count frequency of other products
        std::map<int, int> frequencyMap;

        // Check wghat else thaat specific user bought
        for (int user : buyers) {
            // Get this user's entire history
            std::vector<int>& history = userPurchases[user];

            for (int otherProduct : history) {
                // Don't recommend the item they are already buying
                if (otherProduct != currentProductID) {
                    frequencyMap[otherProduct]++;
                }
            }
        }

        // Sort by Frequency (Most popular first)
        std::vector<std::pair<int, int>> sortedItems;
        for (auto const& [prodId, count] : frequencyMap) {
            sortedItems.push_back({prodId, count});
        }
        // Sort descending (Highest count first)
        std::sort(sortedItems.begin(), sortedItems.end(),
                  [](const std::pair<int, int>& a, const std::pair<int, int>& b) {
            return a.second > b.second;
        });

        // Display Top 3
        if (sortedItems.empty()) {
            std::cout << "No related purchases found.\n";
        } else {
            int limit = 0;
            for (auto const& item : sortedItems) {
                if (limit >= 3) break;
                std::cout << "👉 " << productNames[item.first]
                          << " (Bought together " << item.second << " times)\n";
                limit++;
            }
        }
        std::cout << "----------------------------------------------\n";
    }
};

int main() {
    RecommendationEngine amazon;

    // 1. Setup Catalog
    amazon.addProduct(101, "PlayStation 5");
    amazon.addProduct(102, "Controller");
    amazon.addProduct(103, "Headset");
    amazon.addProduct(104, "FIFA 25");
    amazon.addProduct(105, "Toaster"); // Unrelated item

    // 2. Simulate User Purchases (Building the Graph)
    // User 1: The "Gamer" Bundle
    amazon.recordPurchase(1, 101); // PS5
    amazon.recordPurchase(1, 102); // Controller
    amazon.recordPurchase(1, 104); // FIFA

    // User 2: Another Gamer
    amazon.recordPurchase(2, 101); // PS5
    amazon.recordPurchase(2, 104); // FIFA

    // User 3: Audio Fan
    amazon.recordPurchase(3, 101); // PS5
    amazon.recordPurchase(3, 103); // Headset

    // User 4: Just hungry
    amazon.recordPurchase(4, 105); // Toaster

    // 3. THE TEST: Viewing "PlayStation 5"
    // Expected: FIFA (2 hits), Controller (1 hit), Headset (1 hit). Toaster (0 hits).
    amazon.recommend(101);

    // 4. THE TEST: Viewing "FIFA 25"
    // Expected: PS5 (bought together by User 1 & 2)
    amazon.recommend(104);

    return 0;
}
