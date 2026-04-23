# Modern Collective Studio - E-Commerce Architecture 🛍️

A comprehensive dual-architecture project demonstrating the practical application of Data Structures and Algorithms (DSA). This repository contains both the high-performance algorithmic backend (written in C++) and the final interactive graphical prototype (written in Java Swing).

## 📂 Repository Structure

* **`/Backend`:** The core implementation of the Data Structures natively coded in C++ to run via the Xcode terminal. This serves as the mathematical and logical proof of the system's efficiency.
* **`/GUI`:** The final prototype, migrating the algorithmic logic into a fully interactive, responsive Java desktop application.

## 🚀 Key Features & DSA Implementations

This project was built to showcase optimal time complexity for common e-commerce operations:

* **Fast Product Lookups & Cart Management ($O(1)$)**
  * **Data Structure:** Hash Map
  * **Use Case:** The master inventory and the user's shopping cart are mapped by Product ID, allowing instant retrieval, addition, and removal of items regardless of catalog size.
* **Trending Products Engine ($O(\log N)$)**
  * **Data Structure:** Max Priority Queue (Heap)
  * **Use Case:** Automatically sorts and ranks the top-trending items based on sales volume, ensuring the homepage dynamically displays the most popular products efficiently.
* **Price Range Filtering ($O(\log N + K)$)**
  * **Data Structure:** Red-Black Tree (TreeMap)
  * **Use Case:** Products are mapped by price. The application fetches products within a user-defined price range instantly, without scanning the entire database.
* **Instant Search Engine ($O(1)$)**
  * **Data Structure:** Inverted Index 
  * **Use Case:** Product names are tokenized into keywords upon creation, allowing users to find products by name or category instantly without expensive array traversals.
* **"Frequently Bought Together" Recommendations ($O(V+E)$)**
  * **Data Structure:** Graph 
  * **Use Case:** Tracks relationship edges between product nodes. When a user adds an item to their cart, adjacent nodes are fetched as intelligent cross-sell recommendations.

## 💻 Tech Stack
* **Languages:** C++, Java (Swing, AWT)
* **Design Pattern:** Custom MVC (Model-View-Controller) separating the backend logic from the graphical interface.

## 🏃‍♂️ How to Run the GUI
Ensure you have the JDK installed. Navigate to the `GUI` folder, compile, and run:

```bash
javac ECommerceApp2.java
java ECommerceApp2
