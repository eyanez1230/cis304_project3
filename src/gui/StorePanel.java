package gui;

import business.Cart;
import business.Inventory;
import business.Product;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public final class StorePanel extends JPanel implements ActionListener, ListSelectionListener {

    Inventory inventory = new Inventory();
    Cart cart = new Cart();
    private JButton listInventoryBtn, helpBtn, quitBtn, addToCartBtn, viewCartBtn, removeFromCartBtn, previewPlayBtn, previewStopBtn;
    private JList<String> inventoryList;
    private JScrollPane scrollPane, scrollPane2;
    private DefaultListModel model;
    private Clip clip;
    private String name, selectedItem, productID;
    private JTextArea itemDetails;
    private JLabel image;
    private ImageIcon icon;
    private String imagePath;
    private String fontFamily;

    StorePanel(String fontFamily, String defaultImage) {
        this.fontFamily = fontFamily;
        this.imagePath = defaultImage;

        renderButtons();
        renderMusicSampler();
        renderListView();
        renderDetailsView();
        renderProductImage();
    }

    public void renderButtons() {
        // Create buttons
        listInventoryBtn = new JButton("List Inventory");
        addToCartBtn = new JButton("Add Selected To Cart");
        removeFromCartBtn = new JButton("Remove From Cart");
        viewCartBtn = new JButton("View Cart");

        listInventoryBtn.setForeground(Color.red);
        listInventoryBtn.setFont(new Font(fontFamily, Font.PLAIN, 12));
        addToCartBtn.setForeground(Color.red);
        addToCartBtn.setFont(new Font(fontFamily, Font.PLAIN, 12));
        removeFromCartBtn.setForeground(Color.red);
        removeFromCartBtn.setFont(new Font(fontFamily, Font.PLAIN, 12));
        viewCartBtn.setForeground(Color.red);
        viewCartBtn.setFont(new Font(fontFamily, Font.PLAIN, 12));

        // Set tooltips for buttons
        viewCartBtn.setToolTipText("View items in your cart.");
        removeFromCartBtn.setToolTipText("Removes an item from the cart.");
        listInventoryBtn.setToolTipText("List all items available in our inventory.");
        addToCartBtn.setToolTipText("Add an item to the cart.");

        // Set buttons to disabled
        addToCartBtn.setEnabled(false);
        removeFromCartBtn.setEnabled(false);

        // Register event listeners
        listInventoryBtn.addActionListener(this);
        addToCartBtn.addActionListener(this);
        removeFromCartBtn.addActionListener(this);
        viewCartBtn.addActionListener(this);

        JPanel buttonCommands = new JPanel();
        // Create border for this panel
        buttonCommands.setBorder(BorderFactory.createTitledBorder(null, "Commands", TitledBorder.CENTER, TitledBorder.TOP, new Font(fontFamily, Font.PLAIN, 14)));
        buttonCommands.add(listInventoryBtn);
        buttonCommands.add(addToCartBtn);
        buttonCommands.add(removeFromCartBtn);
        buttonCommands.add(viewCartBtn);

        // Add buttons to panel
        add(buttonCommands, BorderLayout.NORTH);
    }

    public void renderMusicSampler() {
        // Initiate the buttons for play and stop
        previewPlayBtn = new JButton("Play");
        previewStopBtn = new JButton("Stop");

        previewPlayBtn.setFont(new Font(fontFamily, Font.PLAIN, 12));
        previewStopBtn.setFont(new Font(fontFamily, Font.PLAIN, 12));

        // Set player commands to the JPanel
        JPanel playerCommands = new JPanel();

        // Create border for the panel
        playerCommands.setBorder(BorderFactory.createTitledBorder(null, "Preview Music", TitledBorder.CENTER, TitledBorder.TOP, new Font(fontFamily, Font.PLAIN, 14)));

        // Add player commands to the frame
        playerCommands.add(previewPlayBtn);
        playerCommands.add(previewStopBtn);

        // Add event listeners
        previewPlayBtn.addActionListener(this);
        previewStopBtn.addActionListener(this);

        // Disable the buttons 
        previewPlayBtn.setEnabled(false);
        previewStopBtn.setEnabled(false);

        previewPlayBtn.setForeground(Color.red);
        previewStopBtn.setForeground(Color.red);

        // Add the buttons to the panel
        add(playerCommands, BorderLayout.NORTH);
    }

    public void renderListView() {
        // Data for the list
        model = new DefaultListModel();
        // Paramater is the data that will be in the list.
        inventoryList = new JList(model);
        // Set border to this panel
        inventoryList.setBorder(BorderFactory.createTitledBorder(null, "Inventory Items", TitledBorder.LEFT, TitledBorder.TOP, new Font(fontFamily, Font.PLAIN, 14)));
        inventoryList.setFont(new Font(fontFamily, Font.PLAIN, 14));
        // Add a scroll pane for panel to enable scrolability
        JScrollPane scrollPane = new JScrollPane(inventoryList);
        // Set dimensions for the panel
        scrollPane.setPreferredSize(new Dimension(925, 100));

        // Add list listener to the inventory list.  Each time option is clicked, an event is fired.
        inventoryList.addListSelectionListener(this);

        JPanel topPanel = new JPanel();
        topPanel.add(scrollPane, BorderLayout.NORTH);

        // Add top panel to the main panel
        add(topPanel);
    }

    public void renderDetailsView() {
        // Text area for details
        itemDetails = new JTextArea(9, 82);

        JScrollPane areaScrollPane = new JScrollPane(itemDetails);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        itemDetails.setFont(new Font(fontFamily, Font.PLAIN, 14));
        itemDetails.setEditable(false);
        itemDetails.setBorder(BorderFactory.createTitledBorder(null, "Product Details", TitledBorder.LEFT, TitledBorder.TOP, new Font(fontFamily, Font.PLAIN, 14)));

        JPanel bottomPanel = new JPanel();
        // Place objects on panel       
        bottomPanel.add(areaScrollPane, BorderLayout.SOUTH);
        // Add bottom panel to the main panel
        add(bottomPanel);
    }

    public void renderProductImage() {
        try {
            // Get the image by the URL
            URL imgURL = getClass().getResource(imagePath);
            icon = new ImageIcon(imgURL);
            image = new JLabel(icon);

            // Build panel for the image
            JPanel productImage = new JPanel();
            productImage.add(image);

            // Display the image
            add(productImage, BorderLayout.WEST);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No image available for this product.");
        }
    }

    public void actionPerformed(ActionEvent event) {
        // Set the action performed to an object 
        Object source = event.getSource();

        // If a certain action was triggered, perform certain actions
        if (source == listInventoryBtn) {
            // Remove all elements from the table to reset it
            model.removeAllElements();

            // Set inventory items to an array list
            ArrayList<String> inventoryItems = getInventoryItems();

            // Loop through inventory items
            for (String S : inventoryItems) {
                model.addElement(S);
            }

            // Set buttons to enabled or disabled
            addToCartBtn.setEnabled(true);
            removeFromCartBtn.setEnabled(false);
            listInventoryBtn.setEnabled(false);

            // Change what border reads
            inventoryList.setBorder(BorderFactory.createTitledBorder(null, "Inventory Items", TitledBorder.LEFT, TitledBorder.TOP, new Font(fontFamily, Font.PLAIN, 14)));

        } else if (source == addToCartBtn) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String dateNow = dateFormat.format(date);

            this.addToCart(productID);

            model.removeAllElements();
            ArrayList<String> inventoryList = getInventoryItems();

            for (String S : inventoryList) {
                model.addElement(S);
            }

            // Update product information in the textarea
            String details = getProductDetails(productID);
            itemDetails.setText(details);
        } else if (source == viewCartBtn) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }

            previewPlayBtn.setEnabled(false);
            previewStopBtn.setEnabled(false);

            model.removeAllElements();
            ArrayList<String> cartItems = getCartItems();

            for (String S : cartItems) {
                model.addElement(S);
            }

            if (cartItems.size() < 1) {
                removeFromCartBtn.setEnabled(false);
            }

            removeFromCartBtn.setEnabled(true);
            addToCartBtn.setEnabled(false);
            listInventoryBtn.setEnabled(true);

            // Change what border reads           
            inventoryList.setBorder(BorderFactory.createTitledBorder(null, "Items in Cart", TitledBorder.LEFT, TitledBorder.TOP, new Font(fontFamily, Font.PLAIN, 14)));

        } else if (source == removeFromCartBtn) {
            // Set current date format
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String dateNow = dateFormat.format(date);

            // Remove from cart by productID
            this.removeFromCart(productID);

            // Set list inventory button to enabled
            listInventoryBtn.setEnabled(true);

            // Update cart
            model.removeAllElements();

            // Set all cart items to an array list
            ArrayList<String> cartItems = getCartItems();

            // Loop through array list
            for (String S : cartItems) {
                // Add iitem to the model variable which will be displayed on the list
                model.addElement(S);
            }
        } else if (source == previewPlayBtn) {
            // Get product inventory contents from the hashmap by key
            Product product = (Product) inventory.contents.get(productID);

            // Set music file which is the name of the artist.wav
            String musicFile = "preview/" + product.ID + ".wav";

            // Play the audio file and if file is playing, toggle the start/stop buttons
            playAudio(musicFile);
            previewPlayBtn.setEnabled(false);
            previewStopBtn.setEnabled(true);

        } else if (source == previewStopBtn) {
            // If stop is clicked, stop the clip from playing
            clip.stop();
            // Toggle the start/stop buttons
            previewPlayBtn.setEnabled(true);
            previewStopBtn.setEnabled(false);
        }
    }

    public void playAudio(String songfile) {
        try {
            // Get the audio file and play it
            URL url = getClass().getResource(songfile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            // If there is no preview available, show there is no preview available
            JOptionPane.showMessageDialog(null, "No preview available.");
        }
    }

    public void valueChanged(ListSelectionEvent event) {
        // Stop the clip if it is running and something else is clicked.
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }

        if (inventoryList.getSelectedIndex() >= 0) {
            // Get inventory items from the hashmap and get the selected value and convert to string
            String listItem = inventoryList.getSelectedValue().toString();
            // Parse the string before the :
            String[] itemFields = listItem.split(": ");
            productID = itemFields[0];

            // Get product details by ID
            String details = getProductDetails(productID);
            // Set the text from the details into the textarea
            itemDetails.setText(details);

            // get product information by KEy from the hashmap.
            Product product = (Product) inventory.contents.get(productID);

            // Set image path URL by the product url set inside the class.
            imagePath = product.imageURL;

            try {
                // Get image url for the product selected
                URL imgURL = getClass().getResource(imagePath);
                icon = new ImageIcon(imgURL);
                // Set the image onto the panel
                image.setIcon(icon);

                // Toggle the play buttons if it is/is not music
                if (product.medium.equals("Music")) {
                    previewPlayBtn.setEnabled(true);
                } else if (product.medium != "Music") {
                    previewPlayBtn.setEnabled(false);
                    previewStopBtn.setEnabled(false);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, imagePath);
            }
        }
    }

    String getProductDetails(String key) {
        String msg;

        // Check if key is found in hashmap
        if (inventory.contents.containsKey(key)) {
            // Find inventory information by key in hashmap and convert to string
            msg = inventory.contents.get(key).toString();
        } else {
            msg = "\n" + "Please select a product \n";
        }
        return msg;
    }

    public void addToCart(String key) {
        String msg = "";

        // Get information from product hashmap by key
        Product product = (Product) inventory.contents.get(key);

        // Check if key exists in the inventory hashmap
        if (inventory.contents.containsKey(key)) {
            // Check if product reads "Out of stock" or if product is a string of 0.  String of zero should never happen but we use this just in case
            if (product.qty == 0) {
                JOptionPane.showMessageDialog(null, product.name + " is currently out of stock. You cannot add this to your cart.");
            } else if (product.qty >= 0) { // Added this to not parse string if it reads out of stock. Crashes if we do.
                // Parse string to integer 
                int inventoryCount = product.qty;

                // Set the quantity that you have in the cart and add to hashmap.
                if (!cart.cartItems.containsKey(key)) {
                    String name = product.name;
                    int qty = 1;

                    // Add cart information to the cart hashmap.
                    Cart cartDB = new Cart(key, name, qty);
                    cart.cartItems.put(cartDB.ID, cartDB);
                } else {
                    Cart cartDB = (Cart) cart.cartItems.get(key);
                    cartDB.qty++;
                }

                // Subtract from inventory everytime we add to our cart.
                int updatedQty = inventoryCount - 1;

                product.qty = updatedQty;

                // Display message
                JOptionPane.showMessageDialog(null, product.name + " added to cart successfully.");
            }
        } else {
            // Display message
            JOptionPane.showMessageDialog(null, "Please select a product from inventory");
        }

    }

    public void removeFromCart(String key) {
        String msg = "";
        Product product = (Product) inventory.contents.get(key);

        if (cart.cartItems.containsKey(key) && inventory.contents.containsKey(key)) {
            Cart cartDB = (Cart) cart.cartItems.get(key);

            // Remove from cart completely if the quantity goes from 1 to 0.
            if (cartDB.qty <= 1) {
                cart.cartItems.remove(key);
            } else {
                cartDB.qty--;
            }

            JOptionPane.showMessageDialog(null, product.name + " removed from cart successfully.");

            // Add back to inventory
            if (product.qty >= 0) {
                product.qty++;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an item from your cart to remove.");
        }
    }

    ArrayList<String> getCartItems() {

        //Get product keys and return listing and build an array list
        ArrayList<String> keys = new ArrayList(cart.cartItems.keySet());
        Collections.sort(keys);

        // Loop through all the keys in array list
        ArrayList<String> cartList = new ArrayList<String>();

        for (int index = 0; index < keys.size(); index++) {
            // Find information in product hashmap from product class
            Cart cartDB = (Cart) cart.cartItems.get(keys.get(index));

            // Output product information
            cartList.add(cartDB.ID + ": " + cartDB.name + " - Quantity in Cart: " + cartDB.qty);
        }
        return cartList;
    }

    ArrayList<String> getInventoryItems() {
        String productQty = "";

        ArrayList<String> keys = new ArrayList(inventory.contents.keySet());
        Collections.sort(keys);

        ArrayList<String> list = new ArrayList<String>();
        for (int index = 0; index < keys.size(); index++) {
            Product product = (Product) inventory.contents.get(keys.get(index));

            if (product.qty == 0) {
                productQty = "Out of stock";
            } else {
                productQty = Integer.toString(product.qty);
            }

            list.add(product.ID + ": " + product.name + " - Quantity Available: " + productQty);
        }

        return list;
    }
}
