package com.sistek.desktopapp;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FrameManager {
	
	private RestClient restClient;
	private SerialPortConnection serialPortConnection;
	
	private JFrame frame;

	public FrameManager(RestClient restClient, SerialPortConnection serialPortConnection) {
		this.frame = new JFrame();
		this.restClient = restClient;
		this.serialPortConnection = serialPortConnection;
	}
	
	public void startApplication() {
		createLoginPanel();
		setFrameProperties();
	}
	
	public void setFrameProperties() {
		frame.setTitle("Sistek Test Desktop Uygulaması");
        frame.setSize(400, 600);  
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setVisible(true); 
	}
	
	private void createLoginPanel() {
        final JPanel panel = new JPanel();  
        panel.setLayout(new FlowLayout());  
        
        JLabel title = new JLabel("Giriş Yap", JLabel.LEFT);
        title.setFont(new Font("VERDANA", Font.BOLD,20));
        title.setPreferredSize(new Dimension(350, 50));
        panel.add(title);
        
        JLabel usernameLabel = new JLabel("Kullanıcı adı  : ", JLabel.LEFT);
        usernameLabel.setPreferredSize(new Dimension(350, 30));
        final JTextField usernameField = new JTextField(30);
        usernameField.setPreferredSize(new Dimension(400, 30));
        usernameField.setColumns(30);
        panel.add(usernameLabel); 
        panel.add(usernameField);
        
        JLabel passwordLabel = new JLabel("Şifre : ", JLabel.LEFT); 
        passwordLabel.setPreferredSize(new Dimension(350, 30));
        final JPasswordField passwordField = new JPasswordField(30);
        passwordField.setPreferredSize(new Dimension(400, 30));
        passwordField.setColumns(30);
        panel.add(passwordLabel); 
        panel.add(passwordField);
        
        JButton button = new JButton();  
        button.setText("Giris Yap"); 
        button.setSize(new Dimension(100,30));
        panel.add(button);
        
        final JLabel loginUnsuccessful = new JLabel("Giriş başarısız. Tekrar deneyin.");
        loginUnsuccessful.setVisible(false);
        loginUnsuccessful.setPreferredSize(new Dimension(350, 30));
        panel.add(loginUnsuccessful);
        
        frame.add(panel); 
                
        button.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mousePressed(MouseEvent e) {
				String loginSuccessful = restClient.loginToWebApp(usernameField.getText(), passwordField.getText());
				if(loginSuccessful.equals("SUCCESS")) {
					frame.remove(panel);
					createBarcodePanel();
					setFrameProperties();
					
					startSerialPortConnection();
					
				} else {
					loginUnsuccessful.setVisible(true);
				}
				super.mousePressed(e);
			}

		});
	}
	
	private void startSerialPortConnection() {
		Thread serialPortThread = new Thread(serialPortConnection);
		serialPortThread.start();
	}

	private void createBarcodePanel() {
        final JPanel panel = new JPanel();  
        panel.setLayout(new FlowLayout());  
        
        JLabel title = new JLabel("Sistek Test Desktop Uygulaması", JLabel.LEFT);
        title.setFont(new Font("VERDANA", Font.BOLD,20));
        title.setPreferredSize(new Dimension(350, 50));
        panel.add(title);
        
        JButton sendBarcode = new JButton();  
        sendBarcode.setText("Barkod Gönder");
        sendBarcode.setPreferredSize(new Dimension(350, 50));
        panel.add(sendBarcode);
        
        JButton listBarcodes = new JButton();  
        listBarcodes.setText("Barkodları Listele");  
        listBarcodes.setPreferredSize(new Dimension(350, 50));
        panel.add(listBarcodes);
        
        frame.add(panel); 
                
        sendBarcode.addMouseListener(new MouseAdapter() {
        	@Override
			public void mousePressed(MouseEvent e) {
        		frame.remove(panel);
        		createSendBarcodePanel();
        		setFrameProperties();
				super.mousePressed(e);
			}


		});
        
        listBarcodes.addMouseListener(new MouseAdapter() {
        	@Override
			public void mousePressed(MouseEvent e) {
        		frame.remove(panel);
        		createListBarcodesPanel();
        		setFrameProperties();
				super.mousePressed(e);
			}
		});
		
	}
	
	private void createSendBarcodePanel() {
        final JPanel panel = new JPanel();  
        panel.setLayout(new FlowLayout());  
        
        JLabel title = new JLabel("Barkod gönder", JLabel.LEFT);
        title.setFont(new Font("VERDANA", Font.BOLD,20));
        title.setPreferredSize(new Dimension(350, 50));
        panel.add(title);
        
        JLabel barcodeLabel = new JLabel("Barkod  : ", JLabel.LEFT);
        barcodeLabel.setPreferredSize(new Dimension(350, 30));
        final JTextField barcodeField = new JTextField(30);
        barcodeField.setPreferredSize(new Dimension(350, 30));
        panel.add(barcodeLabel); 
        panel.add(barcodeField );
        
        final JLabel successLabel = new JLabel("Barkod kaydedildi", JLabel.LEFT);
        successLabel.setPreferredSize(new Dimension(250, 30));
        successLabel.setVisible(false);
        panel.add(successLabel);
        
        JButton sendBarcode = new JButton();  
        sendBarcode.setText("Gönder"); 
        sendBarcode.setPreferredSize(new Dimension(250, 50));
        panel.add(sendBarcode);
        
        JButton listBarcodes = new JButton();  
        listBarcodes.setText("Barkodları Listele"); 
        listBarcodes.setPreferredSize(new Dimension(250, 50));
        panel.add(listBarcodes);
        
        frame.add(panel); 
                
        listBarcodes.addMouseListener(new MouseAdapter() {
        	@Override
			public void mousePressed(MouseEvent e) {
        		frame.remove(panel);
        		createListBarcodesPanel();
        		setFrameProperties();
				super.mousePressed(e);
			}
		});
        
        sendBarcode.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String barcode = barcodeField.getText();
				if (!barcode.equals("")) {
					String success = restClient.sendBarcode(barcode);
					if (success.equals("SUCCESS")) {
						successLabel.setVisible(true);
						barcodeField.setText("");
					}
				}
				super.mousePressed(e);
			}
		});
                
	}	

	private void createListBarcodesPanel() {
		List<String> barcodes = restClient.listBarcodes();
		
        final JPanel panel = new JPanel();  
        panel.setLayout(new FlowLayout());  
        
        JLabel title = new JLabel("Barkodlar", JLabel.LEFT);
        title.setFont(new Font("VERDANA", Font.BOLD,20));
        title.setPreferredSize(new Dimension(350, 50));
        panel.add(title);
        
        for (String barcode : barcodes) {
            JLabel barcodeLabel = new JLabel(barcode, JLabel.LEFT);
            barcodeLabel.setPreferredSize(new Dimension(350, 30));
            panel.add(barcodeLabel); 	
		}
         
        JButton sendBarcode = new JButton();  
        sendBarcode.setText("Barkod Gönder");  
        panel.add(sendBarcode);
        
        frame.add(panel); 
                
        sendBarcode.addMouseListener(new MouseAdapter() {
        	@Override
			public void mousePressed(MouseEvent e) {
        		frame.remove(panel);
        		createSendBarcodePanel();
        		setFrameProperties();
				super.mousePressed(e);
			}
		});
		
	}

}
