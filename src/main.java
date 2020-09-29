import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class main extends JFrame {
	
    public JFrame frame = new JFrame();
    public static byte[] key_to_encryprion;
    public byte[] key_to_encryption_new;
    static String path_plaintext = null;
    static String path_key = null;
    public String plaintext;
    public static byte[] plaintext_for_encryption;
    public byte[] S = new byte[256];
    public byte[] T = new byte[256];
    static String ecryption_result_path = null;


  public static void main(String[] args) throws IOException {
	
	//Build the GUI through frames and buttons
    JFrame frame = new JFrame("welcome frame");
  
    ImagePanel panel = new ImagePanel(new ImageIcon("encryption1.jpg").getImage());
    
    frame.setSize(350, 350);
    frame.setLocationRelativeTo(null);
    frame.getContentPane().add(new JPanelWithBackground("encryption1.jpg"));
    JLabel welcome = new JLabel("Welcome to Encryption Frame!\n");;
    frame.setLayout(null);
    welcome.setBounds(105,10, 380, 20);
    frame.getContentPane().add(welcome);
    JButton key_button = new JButton("Import Key");

    
    key_button.setBounds(25,40,100,40);
    frame.add(key_button);
    
    JButton plaintext_button = new JButton("Import Plaintext");
    plaintext_button.setBounds(200,40,125,40);
    frame.add(plaintext_button);
    
    JButton encrypt_button = new JButton("Encrypt");
    encrypt_button.setBounds(100,90,100,40);
    frame.add(encrypt_button);
    
    JButton next_btn = new JButton("Next");
    next_btn.setBounds(220,250,100,40);
    frame.add(next_btn);
    frame.getContentPane().add(panel);
    plaintext_button.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
        	JFileChooser plaintext_chooser = new JFileChooser();
            int result_plaintext=plaintext_chooser.showOpenDialog(null);
            
            if(result_plaintext == JFileChooser.APPROVE_OPTION) { //allows the user to regret and press "cancle" 
            File plaintext_input = plaintext_chooser.getSelectedFile();
            path_plaintext = plaintext_input.getAbsolutePath();

            String plaintext = "";
            try {


                File file = new File(path_plaintext);

                Scanner input = new Scanner(file);

                while (input.hasNextLine()) {
                    String line = input.nextLine();
                    plaintext = plaintext + line + " ";
                }
                plaintext_for_encryption = plaintext.substring(0, plaintext.length() - 1).getBytes();

                input.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }
        
        }	
        
    });
    key_button.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
        	 String[] splitted;
             ArrayList<Integer> key_to_int = new ArrayList<>();
             String help = "";
             JFileChooser key_chooser = new JFileChooser();
             int result_key = key_chooser.showOpenDialog(null);

             if(result_key == JFileChooser.APPROVE_OPTION) {//only if the user pressed on "open" the key  will be processed else nothing will happen
            	 File key_input = key_chooser.getSelectedFile();
            	 path_key = key_input.getAbsolutePath();

            	 try {

            		 File file = new File(path_key);

            		 Scanner input = new Scanner(file);

            		 while (input.hasNextLine()) {
            			 String line = input.nextLine();
            			 help = help + line + " ";
            			 splitted = line.split("\\s+");

            			 for (int i = 0; i < splitted.length; i++) {

            				 key_to_int.add(Integer.parseInt(splitted[i]));

            			 }

            		 }

            		 input.close();

            	 } catch (Exception ex) {
            		 ex.printStackTrace();
            	 }

            	 //convert key to byte
            	 byte[] key_to_byte = new byte[key_to_int.size()];
            	 for (int i = 0; i < key_to_int.size(); i++) {
            		 key_to_byte[i] = (byte) ((int) key_to_int.get(i));
            	 }

            	 key_to_encryprion = help.substring(0, help.length() - 1).getBytes();

             }
        }

             

    });
    
    encrypt_button.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
        	 try {
        		 
        		 //the following if statement make sure that the encryption process will necessarily happen with both key and plaintext
                 if (path_key==null && path_plaintext==null ) {
                	 
                	 JOptionPane.showMessageDialog(null, "Encription  failed! Please insert Key and Plaintext!","Can not Encrypt",1);
                 }
                 
                 if(path_key==null && !(path_plaintext==null)) {
                	 
                	 JOptionPane.showMessageDialog(null, "Encription failed! Pleas insert Key","Can not Encrypt",1);
                	 
                 }
                 
                 if(!(path_key==null) && path_plaintext==null) {

                	 JOptionPane.showMessageDialog(null, "Encription failed! Pleas insert Plaintext","Can not Encrypt",1);

                 }
                 else if (path_key != null && path_key != null){
                 byte[] key = key_to_encryprion;
                 RC4 rc = new RC4(new String(key));
                 String plainText = new String(plaintext_for_encryption);
                 
                 System.out.println("The key is: " + rc.keytoprint);
                 System.out.println("The Plaintext is: " + plainText);
                 byte[] enText = rc.encrypt(plaintext_for_encryption);
                 String encrypt_txt=bytesToHex(enText);
                 
                 System.out.println("The Encrypted text is: " + encrypt_txt);

                 File file = new File( "encrypted.txt"); // put the file inside the folder
                 file.createNewFile(); // create the file
                 ecryption_result_path=file.getAbsolutePath();

                 try {
                     String content = encrypt_txt;

                     Files.write(Paths.get(ecryption_result_path), content.getBytes());


                 } catch (IOException exception) {
                 } 
                 JOptionPane.showMessageDialog(null, "Encription finished successfully!","Encryption successed",1);
//         }


             }
        	 }catch (Exception exception) {
                 exception.printStackTrace();
             }
        	 
         }
     });

    next_btn.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
        	frame.dispose();

            encryption_output_frame frame2 = new encryption_output_frame(path_plaintext, path_key, ecryption_result_path, "encryption_output_frame");
            ImagePanel panel = new ImagePanel(new ImageIcon("encryption1.jpg").getImage());
       frame2.pack();
       frame2.setLocationRelativeTo(null);
       frame2.setSize(350,350);
       frame2.setVisible(true);
            frame2.getContentPane().add(panel);
            frame2.setLocationRelativeTo(null);

        }
    });
        	
       
   

    frame.setVisible(true);

  }
  static class ImagePanel extends JPanel {

	  private Image img;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }


}
  private static String bytesToHex(byte[] hashInBytes) {

      StringBuilder sb = new StringBuilder();
      for (byte b : hashInBytes) {
          sb.append(String.format("%02x ", b));
      }
      return sb.toString();

  }
}

