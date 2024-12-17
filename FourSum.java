import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum extends JFrame {
    private JTextField numsInputField;
    private JTextField targetInputField;
    private JTextArea outputArea;

    public FourSum() {
        setTitle("Four Sum Calculator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Enter nums (comma-separated):"));
        numsInputField = new JTextField();
        inputPanel.add(numsInputField);

        inputPanel.add(new JLabel("Enter target:"));
        targetInputField = new JTextField();
        inputPanel.add(targetInputField);

        JButton calculateButton = new JButton("Calculate");
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Button Action Listener
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateFourSum();
            }
        });
    }

    private void calculateFourSum() {
        String numsText = numsInputField.getText();
        String targetText = targetInputField.getText();

        outputArea.setText(""); // Clear previous output

        try {
            // Parse inputs
            String[] numsArray = numsText.split(",");
            int[] nums = Arrays.stream(numsArray)
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int target = Integer.parseInt(targetText);

            // Call fourSum logic
            List<List<Integer>> result = fourSum(nums, target);

            // Display results
            if (result.isEmpty()) {
                outputArea.append("No quadruplets found.");
            } else {
                for (List<Integer> quadruplet : result) {
                    outputArea.append(quadruplet.toString() + "\n");
                }
            }
        } catch (NumberFormatException ex) {
            outputArea.append("Invalid input! Please enter numbers correctly.");
        }
    }

    private List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // Skip duplicates
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // Skip duplicates
                int left = j + 1;
                int right = n - 1;
                long remaining = (long) target - nums[i] - nums[j];

                while (left < right) {
                    long sum = (long) nums[left] + nums[right];
                    if (sum == remaining) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // Move pointers and skip duplicates
                        left++;
                        right--;
                        while (left < right && nums[left] == nums[left - 1]) left++;
                        while (left < right && nums[right] == nums[right + 1]) right--;
                    } else if (sum < remaining) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FourSum().setVisible(true);
            }
        });
    }
}
