import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*; 

public class ECommerceApp2 {
    public static final Color COLOR_BG_MAIN = Color.decode("#FFF5EA");
    public static final Color COLOR_TOP_STRIP = Color.decode("#2d2d2d"); 
    public static final Color COLOR_TEXT_DARK = Color.BLACK;
    public static final Color COLOR_TEXT_LIGHT = Color.WHITE;
    public static final String FONT_NAME = "Open Sauce"; 

    private JFrame frame;
    private BackendSystem backend; 
    private JPanel productGrid;    
    private JLabel cartLabel;      

    public ECommerceApp2() {
        backend = new BackendSystem(); 
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Modern Collective Studio");
        frame.setBounds(100, 100, 1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(COLOR_BG_MAIN); 

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        frame.add(scrollPane);

        // Top Strip
        JPanel topStripPanel = new JPanel(new BorderLayout());
        topStripPanel.setBackground(COLOR_TOP_STRIP);
        topStripPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
        topStripPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel leftStrip = new JLabel("English | اُرْدُو");
        leftStrip.setForeground(COLOR_TEXT_LIGHT);
        leftStrip.setFont(new Font(FONT_NAME, Font.PLAIN, 12));

        JLabel centerStrip = new JLabel("20% Off Your First App Order With Code FIRST", SwingConstants.CENTER);
        centerStrip.setForeground(COLOR_TEXT_LIGHT);
        centerStrip.setFont(new Font(FONT_NAME, Font.BOLD, 12));

        JLabel rightStrip = new JLabel("Deliver to: Islamabad 📍");
        rightStrip.setForeground(COLOR_TEXT_LIGHT);
        rightStrip.setFont(new Font(FONT_NAME, Font.PLAIN, 12));

        topStripPanel.add(leftStrip, BorderLayout.WEST);
        topStripPanel.add(centerStrip, BorderLayout.CENTER);
        topStripPanel.add(rightStrip, BorderLayout.EAST);
        mainContainer.add(topStripPanel);

        // Main Header
        JPanel mainHeaderPanel = new JPanel(new BorderLayout());
        mainHeaderPanel.setBackground(COLOR_BG_MAIN);
        mainHeaderPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        mainHeaderPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // A. Left
        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        leftHeader.setBackground(COLOR_BG_MAIN);
        JLabel logoLbl = new JLabel("Modern Collective Studio");
        logoLbl.setFont(new Font(FONT_NAME, Font.BOLD, 28));
        JLabel tab1 = new JLabel("FASHION"); tab1.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        JLabel tab2 = new JLabel("BEAUTY");  tab2.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        leftHeader.add(logoLbl);
        leftHeader.add(tab1);
        leftHeader.add(tab2);

        // B. Center: Search Bar & Filter
        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        searchContainer.setBackground(COLOR_BG_MAIN);
        
        JTextField searchField = new JTextField("Search...");
        searchField.setPreferredSize(new Dimension(250, 40)); 
        searchField.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15), new EmptyBorder(0, 15, 0, 15)));

        // Filter Inputs
        JTextField minPriceField = new JTextField("");
        minPriceField.setPreferredSize(new Dimension(60, 40));
        JTextField maxPriceField = new JTextField("");
        maxPriceField.setPreferredSize(new Dimension(60, 40));
        JButton filterBtn = new JButton("Go");
        filterBtn.setBackground(Color.DARK_GRAY);
        filterBtn.setForeground(Color.WHITE);
        filterBtn.setPreferredSize(new Dimension(50, 40));

        // Filter Logic
        filterBtn.addActionListener(e -> {
            try {
                double min = Double.parseDouble(minPriceField.getText());
                double max = Double.parseDouble(maxPriceField.getText());
                java.util.List<Product> filtered = backend.getProductsByPriceRange(min, max);
                showSearchResultsWindow(filtered, "Price Filter: $" + min + " - $" + max);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers.");
            }
        });

        // Search Logic
        searchField.addActionListener(e -> {
            String text = searchField.getText(); 
            if (!text.trim().isEmpty()) {
                java.util.List<Product> searchResults = backend.searchProducts(text);
                showSearchResultsWindow(searchResults, text);
                searchField.setText("");
            }
        });
        
        searchContainer.add(searchField);
        searchContainer.add(new JLabel("Price:"));
        searchContainer.add(minPriceField);
        searchContainer.add(new JLabel("-"));
        searchContainer.add(maxPriceField);
        searchContainer.add(filterBtn);

        // C. Right
        JPanel rightIcons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        rightIcons.setBackground(COLOR_BG_MAIN);
        JLabel userIcon = new JLabel("👤 Account");
        userIcon.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        
        cartLabel = new JLabel("🛒 Cart (0)"); 
        cartLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        cartLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Open Cart Window on Click
        cartLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCartWindow();
            }
        });
        
        rightIcons.add(userIcon);
        rightIcons.add(cartLabel);
        // Add header sections
        mainHeaderPanel.add(leftHeader, BorderLayout.WEST);
        mainHeaderPanel.add(searchContainer, BorderLayout.CENTER);
        mainHeaderPanel.add(rightIcons, BorderLayout.EAST);
        mainContainer.add(mainHeaderPanel);

        // 3. Navigation bar
        JPanel navBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10)); 
        navBarPanel.setBackground(COLOR_BG_MAIN);
        navBarPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY));
        navBarPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        // 1. Clothing (IDs 111 - 123)
        JLabel clothingBtn = new JLabel("CLOTHING");
        clothingBtn.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        clothingBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clothingBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Fetch IDs 111 to 123
                java.util.List<Product> results = backend.getProductsByIdRange(111, 123);
                showSearchResultsWindow(results, "Category: Clothing");
            }
        });
        
        // 2. Shoes (IDs 101 - 110)
        JLabel shoesBtn = new JLabel("SHOES");
        shoesBtn.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        shoesBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        shoesBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Fetch IDs 101 to 110
                java.util.List<Product> results = backend.getProductsByIdRange(101, 110);
                showSearchResultsWindow(results, "Category: Shoes");
            }
        });

        // 3. Accessories (IDs 124 - 130)
        JLabel accessBtn = new JLabel("ACCESSORIES");
        accessBtn.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        accessBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        accessBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Fetch IDs 124 to 130
                java.util.List<Product> results = backend.getProductsByIdRange(124, 130);
                showSearchResultsWindow(results, "Category: Accessories");
            }
        });
        navBarPanel.add(clothingBtn);
        navBarPanel.add(shoesBtn);
        navBarPanel.add(accessBtn);

        mainContainer.add(navBarPanel);

        // 4. Hero Panel (Main pic on home page)
        JPanel heroPanel = new JPanel();
        heroPanel.setBackground(COLOR_BG_MAIN);
        
        ImageIcon originalIcon = new ImageIcon("Home page pic.png"); 
        
        if (originalIcon.getIconWidth() > 0) {
            Image scaledImage = originalIcon.getImage().getScaledInstance(1150, 400, Image.SCALE_SMOOTH);
            JLabel heroLabel = new JLabel(new ImageIcon(scaledImage));
            heroPanel.add(heroLabel);
        } else {
            heroPanel.setPreferredSize(new Dimension(1150, 400));
            JLabel errorLbl = new JLabel("❌ Image not found. Check filename 'Home page pic.png'");
            errorLbl.setForeground(Color.RED);
            heroPanel.add(errorLbl);
        }

        mainContainer.add(heroPanel);

        // 5. Trending section
        JPanel gridHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        gridHeader.setBackground(COLOR_BG_MAIN);
        JLabel gridTitle = new JLabel("🔥 Trending Now");
        gridTitle.setFont(new Font(FONT_NAME, Font.BOLD, 22));
        gridHeader.add(gridTitle);
        mainContainer.add(gridHeader);
        
        // 6. Product Grid
        this.productGrid = new JPanel(new GridLayout(0, 4, 20, 20)); 
        this.productGrid.setBackground(COLOR_BG_MAIN);
        this.productGrid.setBorder(new EmptyBorder(0, 30, 30, 30));
        // Get trending items using max heap
        java.util.List<Product> trendingProducts = backend.getTopTrending(12);

        for (Product p : trendingProducts) {
            this.productGrid.add(createProductCard(p));
        }

        mainContainer.add(productGrid);

        frame.setVisible(true);
    } 


    // Cart Window
    private void showCartWindow() {
        JDialog cartWindow = new JDialog(frame, "Your Shopping Cart", true);
        cartWindow.setSize(550, 600); 
        cartWindow.setLocationRelativeTo(frame);
        
        JLabel title = new JLabel("Your Cart", SwingConstants.CENTER);
        title.setFont(new Font(FONT_NAME, Font.BOLD, 24));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        cartWindow.add(title, BorderLayout.NORTH);
        
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(Color.WHITE);
        
        if (backend.cart.isEmpty()) {
            JPanel emptyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            emptyPanel.setBackground(Color.WHITE);
            JLabel emptyLbl = new JLabel("Your cart is empty!");
            emptyLbl.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
            emptyPanel.add(emptyLbl);
            itemsPanel.add(emptyPanel);
        } else {
            // Logic: Iterate IDs -> Get Details
            for (int id : backend.cart.keySet()) {
                Product p = backend.inventory.get(id); 
                int qty = backend.cart.get(id);
                double subtotal = p.price * qty;
                
                JPanel row = new JPanel(new BorderLayout(15, 0)); 
                row.setBackground(Color.WHITE);
                row.setBorder(new EmptyBorder(10, 20, 10, 20));
                
                // Thumbnail Images
                JLabel imgLabel = new JLabel();
                imgLabel.setPreferredSize(new Dimension(60, 60));
                ImageIcon originalIcon = new ImageIcon(p.imagePath);
                if (originalIcon.getIconWidth() > 0) {
                    Image scaled = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    imgLabel.setIcon(new ImageIcon(scaled));
                } else {
                    imgLabel.setOpaque(true);
                    imgLabel.setBackground(Color.LIGHT_GRAY);
                    imgLabel.setText("No Img");
                }
                row.add(imgLabel, BorderLayout.WEST);

                // Name & Qty (center)
                JLabel nameLbl = new JLabel("<html>" + p.name + "<br><font color='gray'>Qty: " + qty + "</font></html>");
                nameLbl.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
                row.add(nameLbl, BorderLayout.CENTER);

                // Price & remove (right)
                JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
                rightPanel.setBackground(Color.WHITE);
                JLabel priceLbl = new JLabel("$" + String.format("%.2f", subtotal));
                priceLbl.setFont(new Font(FONT_NAME, Font.BOLD, 15));
                // Remove Button
                JButton removeBtn = new JButton("-");
                removeBtn.setBackground(Color.RED);
                removeBtn.setForeground(Color.BLACK); 
                removeBtn.setFocusPainted(false);
                removeBtn.setBorderPainted(false);
                removeBtn.setPreferredSize(new Dimension(45, 30));
                removeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                // Product removal logic
                removeBtn.addActionListener(e -> {
                    //1. Removing from backend
                    backend.removeFromCart(p.id);
                    //2. Updating Main Screen Counter
                    cartLabel.setText("🛒 Cart (" + backend.getCartCount() + ")");
                    //3. Refreshing window
                    cartWindow.dispose();
                    showCartWindow();
                });
                rightPanel.add(priceLbl);
                rightPanel.add(removeBtn);
                row.add(rightPanel, BorderLayout.EAST);

                itemsPanel.add(row);
                itemsPanel.add(new JSeparator());
            }
        }
        
        JScrollPane scroll = new JScrollPane(itemsPanel);
        cartWindow.add(scroll, BorderLayout.CENTER);
        // Bottom Total Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        bottomPanel.setBackground(new Color(245, 245, 245));

        double total = backend.getCartTotal();
        JLabel totalLbl = new JLabel("Total: $" + String.format("%.2f", total));
        totalLbl.setFont(new Font(FONT_NAME, Font.BOLD, 20));

        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.setBackground(Color.BLACK);
        checkoutBtn.setForeground(Color.BLACK);
        checkoutBtn.setPreferredSize(new Dimension(120, 40));
        checkoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
 
        checkoutBtn.addActionListener(e -> {
            if (backend.getCartCount() > 0) {
                cartWindow.dispose(); // Close the cart
                showCheckoutWindow(); // Open the bill
            } else {
                JOptionPane.showMessageDialog(cartWindow, "Your cart is empty.");
            }
        });

        bottomPanel.add(totalLbl, BorderLayout.WEST);
        bottomPanel.add(checkoutBtn, BorderLayout.EAST);
        
        cartWindow.add(bottomPanel, BorderLayout.SOUTH);
        cartWindow.setVisible(true);
    }

    // Window for search results
    private void showSearchResultsWindow(java.util.List<Product> results, String query) {
        JDialog resultWindow = new JDialog(frame, "Search Results: " + query, true);
        resultWindow.setSize(800, 600);
        resultWindow.setLocationRelativeTo(frame); 
        resultWindow.setLayout(new BorderLayout());

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        header.setBackground(COLOR_BG_MAIN);
        JLabel title = new JLabel("Found " + results.size() + " results for \"" + query + "\"");
        title.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        header.add(title);
        resultWindow.add(header, BorderLayout.NORTH);

        JPanel resultGrid = new JPanel(new GridLayout(0, 3, 15, 15)); 
        resultGrid.setBackground(COLOR_BG_MAIN);
        resultGrid.setBorder(new EmptyBorder(20, 20, 20, 20));

        if (results.isEmpty()) {
            JLabel noRes = new JLabel("No items found.");
            noRes.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
            resultGrid.add(noRes);
        } else {
            for (Product p : results) {
                resultGrid.add(createProductCard(p));
            }
        }

        JScrollPane scroll = new JScrollPane(resultGrid);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        resultWindow.add(scroll, BorderLayout.CENTER);
        resultWindow.setVisible(true);
    }

    //PRODUCT CARD
    private JPanel createProductCard(Product p) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));

        // 1. Image
        JLabel imgLabel = new JLabel();
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon originalIcon = new ImageIcon(p.imagePath); 
        if (originalIcon.getIconWidth() > 0) {
            Image scaledImg = originalIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaledImg));
        } else {
            imgLabel.setPreferredSize(new Dimension(180, 180));
            imgLabel.setMaximumSize(new Dimension(180, 180));
            imgLabel.setOpaque(true);
            imgLabel.setBackground(new Color(245, 245, 245));
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imgLabel.setText("No Image");
        }

        // 2. Text
        JLabel nameLbl = new JLabel(p.name);
        nameLbl.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel priceLbl = new JLabel("$" + p.price);
        priceLbl.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        priceLbl.setForeground(new Color(0, 100, 0));
        priceLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

       // 3. Action buttons for adding product to acrt
        JButton addToCartBtn = new JButton("Add to Cart");
        addToCartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToCartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addToCartBtn.setBackground(Color.BLACK);
        addToCartBtn.setForeground(Color.BLACK);

        addToCartBtn.addActionListener(e -> {
            // Add to Cart
            backend.addToCart(p.id);
            cartLabel.setText("🛒 Cart (" + backend.getCartCount() + ")"); 
            // Check for Recommendations
            java.util.List<Product> recs = backend.getRecommendations(p.id);
            if (!recs.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Added to cart!\n\nPeople who bought this also bought:\n");
                for (Product rec : recs) {
                    sb.append("• ").append(rec.name).append(" ($").append(rec.price).append(")\n"); //show recs
                }
                JOptionPane.showMessageDialog(frame, sb.toString(), "Recommendation", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Added " + p.name + " to cart!"); // no recommendations
            }
        });

        card.add(imgLabel);
        card.add(Box.createVerticalStrut(10)); 
        card.add(nameLbl);
        card.add(priceLbl);
        card.add(Box.createVerticalStrut(8));
        card.add(addToCartBtn);

        return card;
    }

    // Final bill & Discount window
    private void showCheckoutWindow() {
        JDialog billWindow = new JDialog(frame, "Checkout", true);
        billWindow.setSize(400, 350);
        billWindow.setLocationRelativeTo(frame);
        billWindow.setLayout(new BorderLayout());
        double originalTotal = backend.getCartTotal();
        
        // final bill
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        centerPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        centerPanel.setBackground(Color.WHITE);

        JLabel subtotalLbl = new JLabel("Subtotal: $" + String.format("%.2f", originalTotal));
        subtotalLbl.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        
        // Discount Section
        JPanel couponPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        couponPanel.setBackground(Color.WHITE);
        
        JTextField codeField = new JTextField(10);
        JButton applyBtn = new JButton("Apply Code");
        applyBtn.setBackground(Color.DARK_GRAY);
        applyBtn.setForeground(Color.BLACK);
        
        couponPanel.add(new JLabel("Code: "));
        couponPanel.add(codeField);
        couponPanel.add(Box.createHorizontalStrut(10));
        couponPanel.add(applyBtn);

        JLabel discountLbl = new JLabel("Discount: $0.00");
        discountLbl.setForeground(new Color(0, 150, 0)); // Green text
        discountLbl.setFont(new Font(FONT_NAME, Font.PLAIN, 14));

        JLabel finalTotalLbl = new JLabel("Total to Pay: $" + String.format("%.2f", originalTotal));
        finalTotalLbl.setFont(new Font(FONT_NAME, Font.BOLD, 22));

        centerPanel.add(subtotalLbl);
        centerPanel.add(couponPanel);
        centerPanel.add(discountLbl);
        centerPanel.add(finalTotalLbl);
        
        billWindow.add(centerPanel, BorderLayout.CENTER);

        // 3. Use discount code FIRST (20% OFF)
        final double[] finalPrice = {originalTotal}; 
        applyBtn.addActionListener(e -> {
            String code = codeField.getText().trim().toUpperCase();
            if (code.equals("FIRST")) {
                double discountAmount = originalTotal * 0.20; // 20%
                finalPrice[0] = originalTotal - discountAmount;
                
                discountLbl.setText("Discount (20%): -$" + String.format("%.2f", discountAmount));
                finalTotalLbl.setText("Total to Pay: $" + String.format("%.2f", finalPrice[0]));
                JOptionPane.showMessageDialog(billWindow, "Code FIRST applied successfully!");
                applyBtn.setEnabled(false); // Prevent double clicking
            } else {
                JOptionPane.showMessageDialog(billWindow, "Invalid Code!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 4. Confirm Button
        JButton payBtn = new JButton("Confirm Order");
        payBtn.setBackground(Color.BLACK);
        payBtn.setForeground(Color.BLACK);
        payBtn.setPreferredSize(new Dimension(100, 50));
        payBtn.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        
        payBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Order Placed Successfully!\nThank you for shopping.");
            backend.clearCart();
            cartLabel.setText("🛒 Cart (0)");
            billWindow.dispose();
        });
        
        billWindow.add(payBtn, BorderLayout.SOUTH);
        billWindow.setVisible(true);
    }

    // method for rounded borders
    private static class RoundedBorder extends javax.swing.border.AbstractBorder {
        private int radius;
        RoundedBorder(int radius) { this.radius = radius; }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
        @Override public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ECommerceApp2());
    }
}

// BACKEND CLASSES
class Product {
    int id;
    String name;
    double price;
    int sales;    
    int rating;   
    String imagePath; 

    public Product(int id, String name, double price, int sales, int rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sales = sales;
        this.rating = rating;
        this.imagePath = id + ".jpg"; 
    }
}

class BackendSystem {
    // 1. Inventory (Hash Map)
    public HashMap<Integer, Product> inventory = new HashMap<>();

    // 2. Trending items (Max Heap)
    public PriorityQueue<Product> trendingHeap = new PriorityQueue<>((a, b) -> b.sales - a.sales);

    // 3. Cart Management (Hash Map)
    public HashMap<Integer, Integer> cart = new HashMap<>();

    // 4. Search Engine (Inverted Index)
    public HashMap<String, java.util.List<Product>> searchIndex = new HashMap<>();

    // 5. Price Range Engine (TreeMap)
    public TreeMap<Double, java.util.List<Product>> priceMap = new TreeMap<>();

    // 6. Recommendation System (Hash Map)
    public HashMap<Integer, java.util.List<Integer>>productGraph= new HashMap<>();

    public BackendSystem() {
        initDatabase();
    }
    
    // Recommendation methods
    public void connectProducts(int id1, int id2){
        productGraph.putIfAbsent(id1, new ArrayList<>());
        productGraph.putIfAbsent(id2, new ArrayList<>());
        if (!productGraph.get(id1).contains(id2)) productGraph.get(id1).add(id2);
        if (!productGraph.get(id2).contains(id1)) productGraph.get(id2).add(id1);
    }

    public java.util.List<Product> getRecommendations(int productID){
        java.util.List<Product> recommendations = new ArrayList<>();
        if (productGraph.containsKey(productID)) {
            for (int neighborId : productGraph.get(productID)) {
                if (inventory.containsKey(neighborId)) {
                    recommendations.add(inventory.get(neighborId));
                }
            }
        }
        return recommendations;
    }

    // Cart Methods
    public void addToCart(int productId) {
        cart.put(productId, cart.getOrDefault(productId, 0) + 1);
    }

    public void removeFromCart(int productId){
       if (cart.containsKey(productId)) {
            int currentQty = cart.get(productId);
            if (currentQty > 1) {
                cart.put(productId, currentQty - 1);
            } 
            else {
                cart.remove(productId);
            }
        }
    }

    public void clearCart() {
        cart.clear();
    }

    public int getCartCount() {
        int total = 0;
        for (int qty : cart.values()) total += qty;
        return total;
    }

    public double getCartTotal() {
        double total = 0;
        for (int id : cart.keySet()) {
            if (inventory.containsKey(id)) {
                Product p = inventory.get(id); 
                total += p.price * cart.get(id);
            }
        }
        return total;
    }

    private void initDatabase() {
        // 30 sample items (shoes, clothes, accessories)
        addProduct(101, "Nike Air Force 1 shoes", 110.00, 5000, 5);
        addProduct(102, "Adidas Ultraboost shoes", 180.00, 3200, 5);
        addProduct(103, "Converse Chuck Taylor shoes", 65.00, 8000, 4);
        addProduct(104, "Vans Old Skool shoes", 70.00, 6500, 5);
        addProduct(105, "Dr. Martens 1460 shoes", 170.00, 1200, 4);
        addProduct(106, "New Balance 550 shoes", 120.00, 4100, 5);
        addProduct(107, "Asics Gel-Kayano shoes", 160.00, 900, 4);
        addProduct(108, "Timberland Premium shoes", 198.00, 1500, 5);
        addProduct(109, "Birkenstock Arizona shoes", 110.00, 2800, 4);
        addProduct(110, "Puma Suede Classic shoes", 75.00, 1100, 4);
        addProduct(111, "Levis 501 Original", 80.00, 7000, 5);
        addProduct(112, "North Face Nuptse", 320.00, 2500, 5);
        addProduct(113, "Patagonia Fleece", 140.00, 1800, 5);
        addProduct(114, "Calvin Klein Tee", 35.00, 4000, 4);
        addProduct(115, "Ralph Lauren Polo", 95.00, 3100, 5);
        addProduct(116, "Zara Oversized Blazer", 89.90, 900, 3);
        addProduct(117, "HM Essential Hoodie", 25.00, 9500, 4);
        addProduct(118, "Uniqlo Airism Tee", 15.00, 6200, 5);
        addProduct(119, "Reebok Sweatpants", 98.00, 5400, 5);
        addProduct(120, "Nike Pro Shorts", 30.00, 4800, 4);
        addProduct(121, "Adidas Track Pants", 50.00, 3300, 4);
        addProduct(122, "Canada Goose Parka", 1200.00, 400, 5);
        addProduct(123, "Tommy Hilfiger Jacket", 150.00, 850, 4);
        addProduct(124, "Ray-Ban Aviator", 160.00, 2100, 5);
        addProduct(125, "Casio G-Shock", 110.00, 3500, 5);
        addProduct(126, "Apple Watch Series 9", 399.00, 6000, 5);
        addProduct(127, "Sony WH-1000XM5", 348.00, 2900, 5);
        addProduct(128, "Herschel Little America", 110.00, 1400, 4);
        addProduct(129, "Tissot Gentleman", 450.00, 600, 5);
        addProduct(130, "Rolex Submariner", 9500.00, 50, 5);

        // connecting products for recommendations
        connectProducts(101, 120);
        connectProducts(101, 102);
        connectProducts(102, 121);
        connectProducts(130, 129);
        connectProducts(129, 124);
        connectProducts(115, 105);
        connectProducts(118, 123);
        connectProducts(106, 111);
        connectProducts(114, 122);
        connectProducts(103, 107);
        connectProducts(126, 127);
    }

    // product Methods
    private void addProduct(int id, String name, double price, int sales, int rating) {
        Product p = new Product(id, name, price, sales, rating);
        inventory.put(id, p);
        trendingHeap.add(p);
        
        String[] keywords = name.toLowerCase().split(" ");
        for (String word : keywords) {
            searchIndex.putIfAbsent(word, new ArrayList<>());
            searchIndex.get(word).add(p);
        }

        priceMap.putIfAbsent(price, new ArrayList<>());
        priceMap.get(price).add(p);
    }

    public java.util.List<Product> getProductsByIdRange(int startId, int endId) {
        java.util.List<Product> categoryList = new ArrayList<>();
        for (int i = startId; i <= endId; i++) {
            if (inventory.containsKey(i)) {
                categoryList.add(inventory.get(i));
            }
        }
        return categoryList;
    }

    public java.util.List<Product> searchProducts(String query) {
        String keyword = query.toLowerCase().trim();
        if (searchIndex.containsKey(keyword)) {
            return searchIndex.get(keyword);
        }
        return new ArrayList<>(); 
    }

    public java.util.List<Product> getProductsByPriceRange(double min, double max) {
        java.util.List<Product> result = new ArrayList<>();
        java.util.Map<Double, java.util.List<Product>> subMap = priceMap.subMap(min, true, max, true);
        for (java.util.List<Product> productList : subMap.values()) {
            result.addAll(productList);
        }
        return result;
    }
    
    public java.util.List<Product> getTopTrending(int limit) {
        java.util.List<Product> topList = new ArrayList<>();
        PriorityQueue<Product> tempHeap = new PriorityQueue<>(trendingHeap);
        for (int i = 0; i < limit && !tempHeap.isEmpty(); i++) {
            topList.add(tempHeap.poll()); 
        }
        return topList;
    }
}