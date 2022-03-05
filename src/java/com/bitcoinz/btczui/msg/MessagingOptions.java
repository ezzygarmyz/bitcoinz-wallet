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
package com.bitcoinz.btczui.msg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;
import com.bitcoinz.btczui.Util;


/**
 * Encapsulates the messaging options that may be set.
 *
 * @author Ivan Vaklinov <ivan@vaklinov.com>
 */
public class MessagingOptions
{

	private boolean messagingIsDisabled;
	private boolean automaticallyAddUsersIfNotExplicitlyImported;
	private double  amountToSend;
	private double  transactionFee;


	public MessagingOptions()
	{
		// Default values set if not loade etc.
		this.messagingIsDisabled = true;
		this.automaticallyAddUsersIfNotExplicitlyImported = true;
		this.amountToSend = this.transactionFee = 0.0001d;
	}


	public MessagingOptions(JsonObject obj)
		 throws IOException
	{
		this.copyFromJSONObject(obj);
	}


	public MessagingOptions(File f)
		throws IOException
	{
		Reader r = null;

		try
		{
			r = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
			JsonObject obj = Util.parseJsonObject(r);

			this.copyFromJSONObject(obj);
		} finally
		{
			if (r != null)
			{
				r.close();
			}
		}
	}


	public void copyFromJSONObject(JsonObject obj)
		throws IOException
	{
		// Mandatory fields!
		this.messagingIsDisabled = obj.getBoolean("messagingdisabled", true);
		this.automaticallyAddUsersIfNotExplicitlyImported = obj.getBoolean("automaticallyaddusersifnotexplicitlyimported", true);
		this.amountToSend   = obj.getDouble("amounttosend",   0.0001d);
		this.transactionFee = obj.getDouble("transactionfee", 0.0001d);
	}


	public JsonObject toJSONObject()
	{
		JsonObject obj = new JsonObject();

		obj.set("messagingdisabled", this.messagingIsDisabled);
		obj.set("automaticallyaddusersifnotexplicitlyimported", this.automaticallyAddUsersIfNotExplicitlyImported);
		obj.set("amounttosend",	this.amountToSend);
		obj.set("transactionfee",	this.transactionFee);

		return obj;
	}


	public void writeToFile(File f)
		throws IOException
	{
		Writer w = null;

		try
		{
			w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
			w.write(this.toJSONObject().toString(WriterConfig.PRETTY_PRINT));
		} finally
		{
			if (w != null)
			{
				w.close();
			}
		}
	}


	public boolean isMessagingDisabled()
	{
		return messagingIsDisabled;
	}

	public boolean isAutomaticallyAddUsersIfNotExplicitlyImported()
	{
		return automaticallyAddUsersIfNotExplicitlyImported;
	}


	public void setMessagingDisabled(boolean messagingIsDisabled)
	{
		this.messagingIsDisabled = messagingIsDisabled;
	}

	public void setAutomaticallyAddUsersIfNotExplicitlyImported(boolean automaticallyAddUsersIfNotExplicitlyImported)
	{
		this.automaticallyAddUsersIfNotExplicitlyImported = automaticallyAddUsersIfNotExplicitlyImported;
	}


	public double getAmountToSend()
	{
		return amountToSend;
	}


	public void setAmountToSend(double amountToSend)
	{
		this.amountToSend = amountToSend;
	}


	public double getTransactionFee()
	{
		return transactionFee;
	}


	public void setTransactionFee(double transactionFee)
	{
		this.transactionFee = transactionFee;
	}

}
