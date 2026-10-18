// Anthony John Megriño & Mirkyla Nicolas

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;


public class SwingGui {
    static int queueNumber = 101;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("School Canteen Kiosk");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout(8, 8));
            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenuItem newOrderItem = new JMenuItem("New Order");
            JMenuItem exitItem = new JMenuItem("Exit");

            newOrderItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));

            fileMenu.add(newOrderItem);
            fileMenu.addSeparator();
            fileMenu.add(exitItem);

            JMenu helpMenu = new JMenu("Help");
            JMenuItem aboutItem = new JMenuItem("About");
            helpMenu.add(aboutItem);

            menuBar.add(fileMenu);
            menuBar.add(helpMenu);

            frame.setJMenuBar(menuBar);


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

            //Add-ons and Drinks
            String[] addOnNames = {"Extra rice", "Fried egg", "Chili garlic"};
            int[] addOnPrices = {15, 18, 8};
            String[] drinkNames = {"No drink","Iced Tea", "Buko Juice", "Bottled Water"};
            int[] drinkPrices = {0, 22, 28, 25};

            JCheckBox[] addOnBoxes = new JCheckBox[3];

            for(int i = 0; i < addOnNames.length; i++) {
                addOnBoxes[i] = new JCheckBox(addOnNames[i] + " - PHP " + addOnPrices[i]);
            }

            JComboBox<String> drinkComboBox = new JComboBox<>(drinkNames);
            JComboBox<String> Quantity = new JComboBox<>(new String[] {"1", "2", "3", "4", "5"});
            JCheckBox discountCheckBox = new JCheckBox("Senior Citizen/PWD 20% Discount");


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
            dishList.setFont(dishList.getFont().deriveFont(14.0f));

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
            
            optionPanel.add(new JLabel("Discount"));
            optionPanel.add(discountCheckBox);

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

                // Warning Dialog
                if(selectedDishIndex == - 1) {
                    JOptionPane.showMessageDialog(frame, "Please pick a dish first.", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int dishPrice = dishPrices[selectedDishIndex];
                int SelectedDrinkIndex = drinkComboBox.getSelectedIndex();
                String SelectedDrink = (String) drinkComboBox.getSelectedItem();
                int drinkPrice = drinkPrices[SelectedDrinkIndex];
                int quantityVal = Quantity.getSelectedIndex() + 1;
                int takeOutFee = takeOut.isSelected() ? 5 : 0;
        

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
            int initialTotal = setPerPrice * quantityVal + takeOutFee;
            

            // Receipt 
            StringBuilder receiptText = new StringBuilder();
            receiptText.append(String.format("%-25s PHP %d\n", dishes[selectedDishIndex], dishPrice));
            receiptText.append(addOns);
            if (SelectedDrinkIndex != 0) {
                receiptText.append(String.format("%-25s PHP %d\n", SelectedDrink, drinkPrice));
            }
            receiptText.append(String.format("%-25s PHP %d\n", "Per set", setPerPrice));
            receiptText.append(String.format("%-25s     %d\n", "Quantity", quantityVal));

            if (takeOut.isSelected()) {
                    receiptText.append(String.format("%-25s PHP %d\n", "Take Out Fee", takeOutFee));
                }
            int discountPrice = 0;
            if (discountCheckBox.isSelected()){
                discountPrice = (int) Math.round(initialTotal * 0.20);
                receiptText.append(String.format("%-25s PHP %d\n", "Discount", discountPrice));

            }

            int totalPrice = initialTotal - discountPrice;
            receiptText.append(String.format("%-25s PHP %d\n", "Total", totalPrice));

            // Confirmation Dialog
            int answer = JOptionPane.showConfirmDialog(frame, "Your total is PHP " + totalPrice +
            ". Place this order?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if(answer != JOptionPane.YES_OPTION) {
                return;
            }

            // Cash and Change
            double cashGiven = 0;
            boolean validCash = false;

            while (!validCash) {
                String cashInput = JOptionPane.showInputDialog(
                    frame,
                    "Your total is PHP " + totalPrice + "\nEnter cash given:",
                    "Cash Payment", JOptionPane.QUESTION_MESSAGE
                );

                if (cashInput == null) {
                    return;
                }

                try {
                    cashGiven = Double.parseDouble(cashInput);

                    if (cashGiven < totalPrice) {
                        JOptionPane.showMessageDialog(
                            frame,
                            "Insufficient cash. Please enter enough money.",
                            "Insufficient Cash", JOptionPane.WARNING_MESSAGE
                        );
                    } else if (cashGiven < 0) {
                        JOptionPane.showMessageDialog(
                            frame,
                            "Cash cannot be negative.",
                            "Invalid Cash", JOptionPane.ERROR_MESSAGE
                        );
                    } else {
                        validCash = true;
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Invalid input! Please enter a valid cash amount.",
                        "Input Error", JOptionPane.ERROR_MESSAGE
                    );
                }
            }

            double change = cashGiven - totalPrice;

            JOptionPane.showMessageDialog(
                frame,
                String.format(
                    "Cash given: PHP %.2f\nTotal: PHP %d\nChange: PHP %.2f",
                    cashGiven, totalPrice, change
                ),
                "Payment Successful",
                JOptionPane.INFORMATION_MESSAGE
            );


            // Queue Number
            int currentQueueNumber = queueNumber;

            receiptText.append(String.format("%-25s %d\n", "Queue Number", currentQueueNumber));

            receiptArea.setText(receiptText.toString());
            receiptArea.revalidate();
            receiptArea.repaint();

            // Information Dialog
            JOptionPane.showMessageDialog(frame, "Thank you for your order!\nYour queue number is " +
            currentQueueNumber + ".", "Order Successful", JOptionPane.INFORMATION_MESSAGE);

            queueNumber++;

            });

            newOrderItem.addActionListener(e -> {
                dishList.clearSelection();
            
                dineIn.setSelected(true);
                takeOut.setSelected(false);
            
                for (JCheckBox addOn : addOnBoxes) {
                    addOn.setSelected(false);
                }
            
                drinkComboBox.setSelectedIndex(0);
                Quantity.setSelectedIndex(0);
                discountCheckBox.setSelected(false);
            
                receiptArea.setText("");
                selectedLabel.setText("Selected: none");
            });
            
            exitItem.addActionListener(e -> System.exit(0));
            
            aboutItem.addActionListener(e ->
                JOptionPane.showMessageDialog(
                    frame,
                    "School Canteen Kiosk\n"
                    + "Developed by:\n"
                    + "Anthony John Megriño\n"
                    + "Mirkyla Nicolas",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE
                )
            );

            frame.setSize(640, 420);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
        });
    }
}