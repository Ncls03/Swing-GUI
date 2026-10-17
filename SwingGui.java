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
            titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
            frame.add(titleLabel, BorderLayout.NORTH);

            JPanel menuPanel = new JPanel();
            JPanel optionPanel = new JPanel();
            JPanel receiptPanel = new JPanel();

            JRadioButton dineIn = new JRadioButton("Dine In", true);
            JRadioButton takeOut = new JRadioButton("Take Out");
            
            ButtonGroup orderTypeGroup = new ButtonGroup();
            orderTypeGroup.add(dineIn);
            orderTypeGroup.add(takeOut);

            String[] addOnNames = {"test", "test2", "test 3"};
            int[] addOnPrices = {15, 20, 25};
            String[] drinkNames = {"Water", "Iced Tea", "Soft Drink"};
            int[] drinkPrices = {10, 15, 20};

            JCheckBox[] addOnBoxes = new JCheckBox[3];

            for(int i = 0; i < addOnNames.length; i++) {
                addOnBoxes[i] = new JCheckBox(addOnNames[i] + " - PHP " + addOnPrices[i]);
            }

            JComboBox<String> drinkComboBox = new JComboBox<>(drinkNames);

            JComboBox<String> Quantity = new JComboBox<>(new String[] {"1", "2", "3", "4", "5"});

            menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
            optionPanel.setBorder(BorderFactory.createTitledBorder("Order"));
            receiptPanel.setBorder(BorderFactory.createTitledBorder("Receipt"));

            frame.add(menuPanel, BorderLayout.WEST);
            frame.add(optionPanel, BorderLayout.CENTER);
            frame.add(receiptPanel, BorderLayout.SOUTH);

            String[] dishes = {
            "Chicken adobo with rice",
            "Pork sisig with rice",
            "Tapsilog", "Siomai rice (4 pcs)",
            "Pancit canton", "Lugaw with egg"
            };
            

            int[] dishPrices = {75, 89, 95, 49, 35, 38};

            String[] displayDishes = new String[dishes.length];
            for (int i = 0; i < dishes.length; i++) {
                displayDishes[i] = dishes[i] + " - PHP " + dishPrices[i];
            }

            JList<String> dishList = new JList<>(displayDishes);
            dishList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Menu Panel Layout
            menuPanel.setLayout(new BorderLayout(5, 5));
            menuPanel.add(new JScrollPane(dishList), BorderLayout.CENTER);
            dishList.setFont(new Font("Times New Roman", Font.PLAIN, 14));

            // Options Panel Layout
            optionPanel.setLayout(new GridLayout(0,1));
            optionPanel.add(new JLabel("Select Order Type:"));
            optionPanel.add(dineIn);
            optionPanel.add(takeOut);

            optionPanel.add(new JLabel("Select Add-ons:"));

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
            selectedLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));

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

            frame.setSize(640, 420);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
        });
    }
}