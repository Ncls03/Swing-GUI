//Anthony John Megrino & Mirkyla Nicolas

import javax.swing.*;
import java.awt.*;


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
            JPanel orderPanel = new JPanel();
            JPanel receiptPanel = new JPanel();


            menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
            orderPanel.setBorder(BorderFactory.createTitledBorder("Order"));
            receiptPanel.setBorder(BorderFactory.createTitledBorder("Receipt"));

            frame.add(menuPanel, BorderLayout.WEST);
            frame.add(orderPanel, BorderLayout.CENTER);
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

            menuPanel.setLayout(new BorderLayout(5, 5));
            menuPanel.add(new JScrollPane(dishList), BorderLayout.CENTER);
            dishList.setFont(new Font("Times New Roman", Font.PLAIN, 14));

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