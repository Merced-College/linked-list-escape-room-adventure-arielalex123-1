package game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextArea sceneDescription;
	private static JLabel lblImage;
	private static JButton btnChoice1;
	private static JButton btnChoice2;
	private static Scene currentScene;
	private static AdventureGame game;
	private static JTextField userInventory;
	private JTextArea txtItemsRoom;
	private JButton btnPickUpItem;
	

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI frame = new GameGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GameGUI() {
		game = new AdventureGame();
		
		currentScene = game.getCurrentScene();
		// updateSceneDisplay();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 781, 550);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Choose Your Own Adventure Game");
		lblTitle.setBounds(211, 0, 339, 79);
		lblTitle.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 20));
		contentPane.add(lblTitle);
		
		lblImage = new JLabel("");
		
		ImageIcon icon = new ImageIcon(GameGUI.class.getResource("/images2/lobby.png"));
		Image img = icon.getImage();
		
		Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);
		
		lblImage.setIcon(new ImageIcon(GameGUI.class.getResource("/images2/lobby.png")));
		lblImage.setBounds(232, 54, 300, 300);
		contentPane.add(lblImage);
		
		sceneDescription = new JTextArea();
		sceneDescription.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 12));
		sceneDescription.setBounds(232, 411, 301, 32);
		contentPane.add(sceneDescription);
		
		btnChoice1 = new JButton("New button");
		btnChoice1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextId = currentScene.getChoices().get(0).getNextSceneId();
	            currentScene = game.getScenses().findSceneById(nextId);
	           
	            updateSceneDisplay();
	            
	            if(currentScene.getSceneId() == 5) {
	            	checkWinCondition();
	            }
	            
			}
		});
		btnChoice1.setBounds(29, 158, 126, 66);
		contentPane.add(btnChoice1);
		
		btnChoice2 = new JButton("New button");
		btnChoice2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextId = currentScene.getChoices().get(1).getNextSceneId();
	            currentScene = game.getScenses().findSceneById(nextId);
	            
	            updateSceneDisplay();
	            
	            
	            if(currentScene.getSceneId() == 5) {
	            	checkWinCondition();
	            }
			}
		});
		btnChoice2.setBounds(636, 158, 119, 66);
		contentPane.add(btnChoice2);
		
		userInventory = new JTextField();
		userInventory.setText("Inventory: empty");
		userInventory.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 14));
		userInventory.setColumns(10);
		userInventory.setBounds(231, 454, 300, 46);
		contentPane.add(userInventory);
		
		txtItemsRoom = new JTextArea();
		txtItemsRoom.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 13));
		txtItemsRoom.setLineWrap(true);
		txtItemsRoom.setWrapStyleWord(true);
		txtItemsRoom.setEditable(false);
		txtItemsRoom.setColumns(10);
		txtItemsRoom.setBounds(41, 350, 159, 79);
		contentPane.add(txtItemsRoom);
		
		btnPickUpItem = new JButton("Pick Up Item");
		btnPickUpItem.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 13));
		btnPickUpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item itemInRoom = currentScene.getItem();
				if(itemInRoom != null) {
					game.getPlayer().addItem(itemInRoom);
					currentScene.removeItem();
					updateSceneDisplay();
				}
			}
		});
		btnPickUpItem.setBounds(63, 440, 109, 32);
		contentPane.add(btnPickUpItem);
		
		updateSceneDisplay();
	}
	
	public void checkWinCondition() {
		boolean hasKeycard = game.getPlayer().hasItem("Keycard");
        boolean hasCodeNote = game.getPlayer().hasItem("Code Note");

        if (hasKeycard && hasCodeNote) {
            javax.swing.JOptionPane.showMessageDialog(this, "You used the Keycard and the Code Note to unlock the exit. \n You escaped. You win!");
        } else {
        	javax.swing.JOptionPane.showMessageDialog(this, "The exit will not open. \n You are missing the required items. \n  To win, you need: Keycard and Code Note.");
        }
        btnChoice1.setEnabled(false);
        btnChoice2.setEnabled(false);
        btnPickUpItem.setEnabled(false);
	}
	
	public void updateSceneDisplay() { 

	sceneDescription.setText(currentScene.getDescription());
	System.out.println(currentScene);
	Item itemInRoom = currentScene.getItem();
	if(itemInRoom == null) {
		txtItemsRoom.setText("Nothing Here");
	}
	else {
		txtItemsRoom.setText(currentScene.getItem().toString());
	}
	
	userInventory.setText(game.getPlayer().getInventoryText());
	
	ImageIcon icon = new ImageIcon (currentScene.getImagePath());
	Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); 
	lblImage.setIcon(new ImageIcon(img));
	
	btnChoice1.setText("<html>" + currentScene.getChoices().get(0).getText() + "<html>"); 
	btnChoice2.setText("<html>"+ currentScene.getChoices().get(1).getText() + "<html>");
	btnPickUpItem.setVisible(currentScene.getItem() != null);
	}
}
