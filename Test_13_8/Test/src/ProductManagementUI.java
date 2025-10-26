import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;

public class ProductManagementUI extends JFrame {
    private JTextField txtProductCode;
    private JTextField txtNewPrice;
    private JButton btnEdit;
    private JButton btnExit;
    private JTable tblProducts;
    private DefaultTableModel tableModel;
    private ArrayList<Product> productList;
    
    public ProductManagementUI(ArrayList<Product> products) {
        this.productList = products;
        initializeUI();
        setupEventHandlers();
        loadDataToTable();
    }
    
    private void initializeUI() {
        // Thiết lập form
        setTitle("Quản Lý Sản Phẩm");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Sản Phẩm"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Label và TextField cho mã sản phẩm
        JLabel lblProductCode = new JLabel("Mã sản phẩm:");
        txtProductCode = new JTextField(15);
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(lblProductCode, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtProductCode, gbc);
        
        // Label và TextField cho giá mới
        JLabel lblNewPrice = new JLabel("Giá mới:");
        txtNewPrice = new JTextField(15);
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(lblNewPrice, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNewPrice, gbc);
        
        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnEdit = new JButton("Sửa");
        btnExit = new JButton("Thoát");
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnExit);
        
        // Thêm vào panel nhập liệu
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(buttonPanel, gbc);
        
        // Bảng sản phẩm với cột giá
        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Thương hiệu", "Danh mục", "Loại sản phẩm", "Giá"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bảng chỉ đọc
            }
        };
        
        tblProducts = new JTable(tableModel);
        tblProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tblProducts);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Sản Phẩm"));
        
        // Thêm các panel vào form
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Thiết lập Tab Order
        txtProductCode.setNextFocusableComponent(txtNewPrice);
        txtNewPrice.setNextFocusableComponent(btnEdit);
        btnEdit.setNextFocusableComponent(btnExit);
        btnExit.setNextFocusableComponent(txtProductCode);
        
        // Thiết lập focus ban đầu
        txtProductCode.requestFocusInWindow();
        
        // Thiết lập trạng thái button ban đầu
        btnEdit.setEnabled(false);
    }
    
    private void setupEventHandlers() {
        // Sự kiện cho nút Sửa
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProduct();
            }
        });
        
        // Sự kiện cho nút Thoát
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // Sự kiện cho TextField mã sản phẩm - chỉ chấp nhận chữ và số
        txtProductCode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetterOrDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume(); // Không cho phép nhập ký tự không hợp lệ
                }
            }
        });
        
        // Sự kiện cho TextField mã sản phẩm - kiểm tra để enable/disable nút Sửa
        txtProductCode.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { checkInput(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { checkInput(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { checkInput(); }
        });
        
        // Sự kiện cho TextField giá mới
        txtNewPrice.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { checkInput(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { checkInput(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { checkInput(); }
        });
    }
    
    private void checkInput() {
        // Kiểm tra input để enable/disable nút Sửa
        String productCode = txtProductCode.getText().trim();
        
        // Nút Sửa chỉ enable khi có mã sản phẩm
        boolean hasValidInput = !productCode.isEmpty();
        
        btnEdit.setEnabled(hasValidInput);
    }
    
    private void loadDataToTable() {
        // Xóa dữ liệu cũ
        tableModel.setRowCount(0);
        
        // Thêm dữ liệu từ danh sách sản phẩm
        for (Product product : productList) {
            String priceValue = "";
            if (product instanceof Electronics) {
                Electronics elec = (Electronics) product;
                priceValue = String.format("%.2f", elec.getBasePrice());
            } else if (product instanceof Clothing) {
                Clothing cloth = (Clothing) product;
                priceValue = String.format("%.2f", cloth.getManufacturingCost());
            } else if (product instanceof FoodItem) {
                priceValue = "N/A"; // FoodItem không có giá
            }
            
            Object[] row = {
                product.productCode,
                product.productName,
                product.brand,
                product.category,
                product.getProductType(),
                priceValue
            };
            tableModel.addRow(row);
        }
    }
    
    private void editProduct() {
        String productCode = txtProductCode.getText().trim();
        String newPriceStr = txtNewPrice.getText().trim();
        
        // Kiểm tra độ dài mã sản phẩm phải đúng 8 ký tự
        if (productCode.length() != 8) {
            JOptionPane.showMessageDialog(this, 
                "Mã sản phẩm ko hợp lệ", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtProductCode.requestFocusInWindow();
            return;
        }
        
        try {
            double newPrice = Double.parseDouble(newPriceStr);
            
            // Tìm sản phẩm trong bảng theo mã
            int foundRow = -1;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String tableProductCode = (String) tableModel.getValueAt(i, 0);
                if (tableProductCode.equals(productCode)) {
                    foundRow = i;
                    break;
                }
            }
            
            if (foundRow == -1) {
                // Không tìm thấy sản phẩm
                JOptionPane.showMessageDialog(this, 
                    "Mã sản phẩm không tìm thấy", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtProductCode.requestFocusInWindow();
                return;
            }
            
            // Tìm sản phẩm trong danh sách để cập nhật
            Product foundProduct = null;
            for (Product product : productList) {
                if (product.productCode.equals(productCode)) {
                    foundProduct = product;
                    break;
                }
            }
            
            if (foundProduct != null) {
                // Cập nhật giá trong đối tượng
                if (foundProduct instanceof Electronics) {
                    Electronics elec = (Electronics) foundProduct;
                    elec.updateBasePrice(newPrice);
                } else if (foundProduct instanceof Clothing) {
                    Clothing cloth = (Clothing) foundProduct;
                    cloth.updateManufacturingCost(newPrice);
                } else if (foundProduct instanceof FoodItem) {
                    JOptionPane.showMessageDialog(this, 
                        "Không thể cập nhật giá cho sản phẩm FoodItem!", 
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                    txtProductCode.requestFocusInWindow();
                    return;
                }
                
                // Cập nhật giá trong bảng
                tableModel.setValueAt(String.format("%.2f", newPrice), foundRow, 5);
                
                // Chọn dòng đã cập nhật
                tblProducts.setRowSelectionInterval(foundRow, foundRow);
                
                JOptionPane.showMessageDialog(this, 
                    "Đã cập nhật giá thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                
                // Xóa input và trả focus về mã sản phẩm
                txtProductCode.setText("");
                txtNewPrice.setText("");
                txtProductCode.requestFocusInWindow();
                
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi: Không tìm thấy sản phẩm trong danh sách", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtProductCode.requestFocusInWindow();
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Giá mới không hợp lệ!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNewPrice.requestFocusInWindow();
        }
    }
}
