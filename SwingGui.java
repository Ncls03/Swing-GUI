//Anthony John Megrino & Mirkyla Nicolas

import java.awt.*;
import javax.swing.*;


public class SwingGui {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("School Canteen Kiosk");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout(8, 8));
            JLabel titleLabel = new JLabel("School Canteen Kiosk", SwingConstants.CENTER);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
            frame.add(titleLabel, BorderLayout.NORTH);

            JPanel menuPanel = new JPanel();
            JPanel optionPanel = new JPanel();
            JPanel receiptPanel = new JPanel();

            JRadioButton dineIn = new JRadioButton("Dine In", true);
            JRadioButton takeOut = new JRadioButton("Take Out");
            
            ButtonGroup orderTypeGroup = new ButtonGroup();
            orderTypeGroup.add(dineIn);
            orderTypeGroup.add(takeOut);

            //add-ons and drinks
            String[] addOnNames = {"extra rice", "extra egg", "extra toppings"};
            int[] addOnPrices = {15, 20, 25};
            String[] drinkNames = {"No drink","Water", "Iced Tea", "Soft Drink"};
            int[] drinkPrices = {0, 15, 25, 30};

            JCheckBox[] addOnBoxes = new JCheckBox[3];

            for(int i = 0; i < addOnNames.length; i++) {
                addOnBoxes[i] = new JCheckBox(addOnNames[i] + " - PHP " + addOnPrices[i]);
            }

            JComboBox<String> drinkComboBox = new JComboBox<>(drinkNames);

            JComboBox<String> Quantity = new JComboBox<>(new String[] {"1", "2", "3", "4", "5"});

            menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
            optionPanel.setBorder(BorderFactory.createTitledBorder("Options"));
            receiptPanel.setBorder(BorderFactory.createTitledBorder("Receipt"));

            frame.add(menuPanel, BorderLayout.WEST);
            frame.add(optionPanel, BorderLayout.CENTER);
            frame.add(receiptPanel, BorderLayout.SOUTH);

            //menu items and prices
            String[] dishes = {
            "Chicken Adobo with rice",
            "Pork Sisig with rice",
            "Tapsilog", "Siomai rice (4 pcs)",
            "Pancit Canton", "Lugaw with egg"
            };
            

            int[] dishPrices = {75, 89, 95, 49, 35, 38};

            //loop to display dishes with prices in the JList
            String[] displayDishes = new String[dishes.length];
            for (int i = 0; i < dishes.length; i++) {
                displayDishes[i] = dishes[i] + " - PHP " + dishPrices[i];
            }

            JList<String> dishList = new JList<>(displayDishes);
            dishList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Menu Panel Layout
            menuPanel.setLayout(new BorderLayout(8, 8));
            menuPanel.add(new JScrollPane(dishList), BorderLayout.CENTER);
            dishList.setFont(new Font("Arial", Font.PLAIN, 14));

            // Options Panel Layout
            optionPanel.setLayout(new BorderLayout(8, 8));
            optionPanel.setLayout(new GridLayout(0,1));
            optionPanel.add(new JLabel("Select Order Type:"));
            optionPanel.add(dineIn);
            optionPanel.add(takeOut);
            optionPanel.add(new JLabel("Select Add-ons:"));

            //Receipt Panel Layout
            receiptPanel.setLayout(new BorderLayout(8, 8));

            for (int i = 0; i < addOnBoxes.length; i++) {
                optionPanel.add(addOnBoxes[i]);
            }

            // Drink(Options) Panel Layout
            optionPanel.add(new JLabel("Select Drink:"));
            optionPanel.add(drinkComboBox);


            optionPanel.add(new JLabel("Select Quantity:"));
            optionPanel.add(Quantity);
            
            JLabel selectedLabel = new JLabel("Selected: none");
            menuPanel.add(selectedLabel, BorderLayout.SOUTH);

            dishList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int index = dishList.getSelectedIndex();
                    if (index != -1) {
                        selectedLabel.setText("Selected: " + dishes[index] + " - PHP " + dishPrices[index] + "");
                    } else {
                        selectedLabel.setText("Selected: none");
                    }
                }
            });

            JTextArea receiptArea = new JTextArea();
            receiptArea.setEditable(false);
            receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            receiptPanel.setLayout(new BorderLayout());
            receiptPanel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);
            JButton PlaceOrderButton = new JButton("Place Order");
            receiptPanel.add(PlaceOrderButton, BorderLayout.NORTH);

            PlaceOrderButton.addActionListener(e -> {
                int selectedDishIndex = dishList.getSelectedIndex();
                int dishPrice = dishPrices[selectedDishIndex];
                int SelectedDrinkIndex = drinkComboBox.getSelectedIndex();
                String SelectedDrink = (String) drinkComboBox.getSelectedItem();
                int drinkPrice = drinkPrices[SelectedDrinkIndex];
                int quantity = Integer.parseInt((String) Quantity.getSelectedItem());
                int takeOutFee = takeOut.isSelected() ? 15 : 0;
                

            // calculate add-ons total
            StringBuilder addOns = new StringBuilder();
            int addOnTotal = 0;
            for (int i = 0; i < addOnBoxes.length; i++) {
                if (addOnBoxes[i].isSelected()) {
                    addOns.append(String.format("  +%-22s PHP %d\n", addOnNames[i], addOnPrices[i]));
                    addOnTotal += addOnPrices[i];
                    }
                }

            int setPerPrice = dishPrice + addOnTotal + drinkPrice;
            int totalPrice = setPerPrice * quantity + takeOutFee;

            // Receipt printing
            StringBuilder receiptText = new StringBuilder();
            receiptText.append(String.format("%-25s PHP %d\n", dishes[selectedDishIndex], dishPrice));
            receiptText.append(addOns);
            if (SelectedDrinkIndex != 0) {
                receiptText.append(String.format("%-25s PHP %d\n", SelectedDrink, drinkPrice));
            }
            receiptText.append(String.format("%-25s PHP %d\n", "Per set", setPerPrice));
            receiptText.append(String.format("%-25s PHP %d\n", "Quantity", quantity));

            if (takeOut.isSelected()) {
                    receiptText.append(String.format("%-25s PHP %d\n", "Take Out Fee", takeOutFee));
                }
            
            receiptText.append(String.format("%-25s PHP %d\n", "Total", totalPrice));

                receiptArea.setText(receiptText.toString());
                receiptArea.revalidate();
                receiptArea.repaint();

            });

            frame.setSize(640, 420);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
        });
    }
}