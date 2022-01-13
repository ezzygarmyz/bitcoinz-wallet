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


import java.awt.Font;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;



/**
 * Dialog to get the user password - to encrypt a wallet.
 *
 * @author Ivan Vaklinov <ivan@vaklinov.com>
 */
public class PasswordEncryptionDialog
	extends PasswordDialog
{
	protected JTextField passwordConfirmationField = null;

	public PasswordEncryptionDialog(JFrame parent)
	{
		super(parent);

		this.upperLabel.setText(
			"<html>The wallet.dat file will be encrypted with a password. If the operation is successful, " +
		  "bitcoinzd will automatically stop and will need to be restarted. The GUI wallet will also be stopped " +
		  "and will need to be restarted. Please enter the password:</html>");

		this.lowerLabel.setText(
			 "<html><span style=\"font-weight:bold; color:red;\">" +
	     "WARNING: The wallet encryption is experimental. " +
	   	 "Make sure you have a valide backup.</span></html>");


		JLabel confLabel = new JLabel("Confirmation: ");
		this.freeSlotPanel.add(confLabel);
		this.freeSlotPanel.add(passwordConfirmationField = new JPasswordField(30));
		this.passwordLabel.setPreferredSize(confLabel.getPreferredSize());


		JLabel dividerLabel = new JLabel("   ");
		dividerLabel.setFont(new Font("Helvetica", Font.PLAIN, 8));
		this.freeSlotPanel2.add(dividerLabel);










		this.setSize(460, 300);
		this.validate();
		this.repaint();
	}


	protected void processOK()
	{
		String password     = this.passwordField.getText();
		String confirmation = this.passwordConfirmationField.getText();

		if (password == null)
		{
			password = "";
		}

		if (confirmation == null)
		{
			confirmation = "";
		}

		if (!password.equals(confirmation))
		{
			JOptionPane.showMessageDialog(
				this.getParent(),
				"The password and the confirmation do not match!", "Password mismatch...",
				JOptionPane.ERROR_MESSAGE);
			return;
		}

		super.processOK();
	}

}
