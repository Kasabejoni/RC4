import javax.swing.*;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class encryption_output_frame extends JFrame {
	
	public JPanel panel1;
	private JButton plaintextButton;
	private JButton keyButton;
	private JButton cipherTextButton;
	private JButton saveCipherTextButton;
	private JButton doneButton;
	private String path;

	public encryption_output_frame(String path_plaintext, String  path_key,String ecryption_result_path, String title) {
	        super(title);
	        path=ecryption_result_path;
	        this.setLayout(null);
	         keyButton=new JButton("key");
	        keyButton.setBounds(25,40,100,40);
	        
	        this.add(keyButton);
	        plaintextButton=new JButton("plaintext");
	        plaintextButton.setBounds(25,90,100,40);

	        this.add(plaintextButton);
	        
	        cipherTextButton=new JButton("cipher Text");
	        cipherTextButton.setBounds(25,135,100,40);

	        this.add(cipherTextButton);
	        
	        saveCipherTextButton=new JButton("save cipher text");
	        saveCipherTextButton.setBounds(25,180,150,40);

	        this.add(saveCipherTextButton);
	        
	        doneButton=new JButton("Done!");
	        doneButton.setBounds(200,200,100,40);

	        this.add(doneButton);
	        
	        plaintextButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {

	                Runtime rt = Runtime.getRuntime();
	                try {
	                    Process p = rt.exec("notepad " + path_plaintext);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }


	            }
	        });
	        keyButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Runtime rt = Runtime.getRuntime();
	                try {
	                    Process p = rt.exec("notepad " + path_key);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
	            }
	        });
	        cipherTextButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Runtime rt = Runtime.getRuntime();
	                try {
	                    Process p = rt.exec("notepad " + path);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
	            }

	        });
	        saveCipherTextButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
				
	            	//path saves the most updated path of the encryption file so the user could save the encryption file wherever he wants. the file will be saved only at one place and will be deleted from its previous path(actually be removed)   
	            	JFileChooser fileChooser = new JFileChooser();

	            	int x=fileChooser.showSaveDialog(null);

            		if(x==JFileChooser.APPROVE_OPTION) {
            			InputStream is = null;
            		    OutputStream os = null;
            		    
            		    try {
            		    	File source=new File(path);
            		    	File dest=fileChooser.getSelectedFile();
            		    	if(!path.equals(dest.getAbsolutePath())) {
            		    		is = new FileInputStream(source);
            		    		os = new FileOutputStream(dest);
            		    		byte[] buffer = new byte[1024];
            		    		int length;
            		    		while ((length = is.read(buffer)) > 0) {
            		    			os.write(buffer, 0, length);
            		    		}
            		    		is.close();
            		    		os.close();
            		    		source.delete();
            		    		path=dest.getAbsolutePath();//
            		    	}
            		    } catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 

            			
            		}



	            }
	        });
	        doneButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0);//when the user press Done the process end and the window will close
	            }
	        });
	    }

	 }


