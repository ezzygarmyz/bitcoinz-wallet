/************************************************************************************************
 *  ____ _____ ______ ___   __ _____  __   _ _____ _        __    _ _      _   _   _ ___
 * | __ \_   _|_   __| __|/ _ \_   _||   \| |___  | \      / /_ _| | | ___| |_| | | |_ _|
 * |____/ | |   | |/ /   / / \ \| |  | |\ | |  / / \ \ /\ / / _` | | |/ _ \ __| | | || |
 * | ___ \| |_  | |\ \__ \ \_/ /| |_ | | \  | / /_  \ V  V / (_| | | |  __/ |_| |_| || |
 * |_____/____| |_| \____|\___/_____||_|  \_|/____|  \_/\_/ \__,_|_|_|\___|\__|\___/|___|

 * Copyright (c) 2017-2022 BitcoinZ team
 * Copyright (c) 2016 Ivan Vaklinov <ivan@vaklinov.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 **********************************************************************************/
package com.bitcoinz.btczui;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


//Dialog to get a signed message.


public class SignMessageDialog
extends JDialog{

	protected boolean isOKPressed = false;
	protected String  password    = null;

	protected JLabel     passwordLabel = null;
	protected JTextField passwordField = null;

	protected JLabel upperLabel;
	protected JLabel lowerLabel;

	protected JPanel freeSlotPanel;
	protected JPanel freeSlotPanel2;

	public SignMessageDialog(JFrame parent){
		super(parent);

		this.setTitle("Sign Message...");
	  this.setLocation(parent.getLocation().x + 50, parent.getLocation().y + 50);
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
		controlsPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JPanel tempPanel = new JPanel(new BorderLayout(0, 0));
		tempPanel.add(this.upperLabel = new JLabel(	"<html>The signature will be encoded with the following message" +
		                         										" and the private key.</html>"), BorderLayout.CENTER);
		controlsPanel.add(tempPanel);

		JLabel dividerLabel = new JLabel("   ");
		dividerLabel.setFont(new Font("Helvetica", Font.PLAIN, 8));
		controlsPanel.add(dividerLabel);

		tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		tempPanel.add(passwordLabel = new JLabel("Message: "));
		tempPanel.add(passwordField = new JTextField(30));
		controlsPanel.add(tempPanel);

		dividerLabel = new JLabel("   ");
		dividerLabel.setFont(new Font("Helvetica", Font.PLAIN, 8));
		controlsPanel.add(dividerLabel);

		this.freeSlotPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		controlsPanel.add(this.freeSlotPanel);

		this.freeSlotPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		controlsPanel.add(this.freeSlotPanel2);

		tempPanel = new JPanel(new BorderLayout(0, 0));
		tempPanel.add(this.lowerLabel = new JLabel("<html></html>"), BorderLayout.CENTER);
		controlsPanel.add(tempPanel);

		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.getContentPane().add(controlsPanel, BorderLayout.NORTH);

		// Form buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
		JButton okButon = new JButton("OK");
		buttonPanel.add(okButon);
		buttonPanel.add(new JLabel("   "));
		JButton cancelButon = new JButton("Cancel");
		buttonPanel.add(cancelButon);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		okButon.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				SignMessageDialog.this.processOK();
			}
		});

		cancelButon.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				SignMessageDialog.this.setVisible(false);
				SignMessageDialog.this.dispose();

				SignMessageDialog.this.isOKPressed = false;
				SignMessageDialog.this.password = null;
			}
		});

		this.setSize(450, 190);
		this.validate();
		this.repaint();
	}


	protected void processOK(){
		String pass = SignMessageDialog.this.passwordField.getText();

		if ((pass == null) || (pass.trim().length() <= 0)){
			JOptionPane.showMessageDialog(
				SignMessageDialog.this.getParent(),
				"The password is empty. Please enter it into the text field.", "Empty...",
				JOptionPane.ERROR_MESSAGE);
			return;
		}

		SignMessageDialog.this.setVisible(false);
		SignMessageDialog.this.dispose();

		SignMessageDialog.this.isOKPressed = true;
		SignMessageDialog.this.password = pass;
	}


	public boolean isOKPressed(){return this.isOKPressed;}
	public String getMessage(){return this.password;}

}
