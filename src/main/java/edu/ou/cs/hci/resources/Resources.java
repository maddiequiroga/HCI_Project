//******************************************************************************
// Copyright (C) 2018-2022 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Wed Jan 17 12:41:31 2022 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20180206 [weaver]:	Original file (for CS course homeworks).
// 20200212 [weaver]:	Added RSRC plus several CSV and image loading methods.
// 20200216 [weaver]:	Switched to Apache Commons CSV for better CSV parsing.
// 20200228 [weaver]:	Added putCSVData() method for writing data.
// 20220117 [weaver]:	Updated FORMAT to work with Apache Commons CSV 1.9.0.
//
//******************************************************************************
// Notes:
//
// You can find information about the excellent Apache Commons CSV library here:
// https://commons.apache.org/proper/commons-csv/
//
//******************************************************************************

package edu.ou.cs.hci.resources;

//import java.lang.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.scene.image.Image;
import javax.swing.ImageIcon;
import org.apache.commons.csv.*;

//******************************************************************************

/**
 * The <CODE>Resources</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Resources
{
	//**********************************************************************
	// Private Class Members
	//**********************************************************************

	// For reading CSV data files from Excel. A header line is not required.
	//private static final CSVFormat	FORMAT =
	//	CSVFormat.DEFAULT.withAllowMissingColumnNames(true);
	private static final CSVFormat	FORMAT =
		CSVFormat.Builder.create().setAllowMissingColumnNames(true).build();

	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	// Relative path of the resources package
	public static final String	RSRC		= "edu/ou/cs/hci/resources/";

	//**********************************************************************
	// Public Class Methods (Convenience Methods, Data Files)
	//**********************************************************************

	// Slurps comma separated values (CSV) in a resource file located relative
	// to this class file. Resource files can be in subdirectories. If so,
	// specify the filename with a relative path. For example, use filename
	// "foo/data.csv" to slurp "edu/ou/cs/hci/resources/foo/data.csv".
	public static List<List<String>>	getCSVData(String filename)
	{
		return getCSVData(getResource(filename));
	}

	// Slurps the lines in a resource file located relative to this class file.
	// Resource files can be in subdirectories. If so, specify the filename
	// with a relative path. For example, use filename "docs/names.txt" to
	// slurp the lines in the file "edu/ou/cs/hci/resources/docs/names.txt".
	public static ArrayList<String>	getLines(String filename)
	{
		return getLines(getResource(filename));
	}

	//**********************************************************************
	// Public Class Methods (Convenience Methods, Swing ImageIcons)
	//**********************************************************************

	// Loads the image in a resource file located relative to this class file.
	// Resource files can be in subdirectories. If so, specify the filename
	// with a relative path. For example, use filename "images/foo.png" to
	// load the PNG image in the file "edu/ou/cs/hci/resources/images/foo.png".
	public static ImageIcon	getSwingImageIcon(String filename)
	{
		return new ImageIcon(getResource(filename));
	}

	// Older method name used by swingmvc. Retained for backward compatibility.
	public static ImageIcon	getImage(String filename)
	{
		return new ImageIcon(getResource(filename));
	}

	//**********************************************************************
	// Public Class Methods (Convenience Methods, JavaFX Images)
	//**********************************************************************

	// Loads the image in a resource file located relative to this class file.
	// Resource files can be in subdirectories. If so, specify the filename
	// with a relative path. For example, use filename "images/foo.png" to
	// load the PNG image in the file "edu/ou/cs/hci/resources/images/foo.png".
	public static Image	getFXImage(String filename, double w, double h,
									   boolean preserveRatio, boolean smooth)
	{
		return new Image(RSRC + filename, w, h, preserveRatio, smooth);
	}

	public static Image	getFXImage(String filename, double w, double h)
	{
		return new Image(RSRC + filename, w, h, false, true);
	}

	public static Image	getFXImage(String filename)
	{
		return new Image(RSRC + filename);
	}

	//**********************************************************************
	// Public Class Methods (Class Resources)
	//**********************************************************************

	// Gets the URL of a resource file located relative to this class file.
	// Resource files can be in subdirectories. If so, specify the filename
	// with a relative path. For example, use filename "docs/names.txt" to
	// get the URL of the file "edu/ou/cs/hci/resources/docs/names.txt".
	public static final URL	getResource(String filename)
	{
		return Resources.class.getResource(filename);
	}

	//**********************************************************************
	// Public Class Methods (Line Slurpers)
	//**********************************************************************

	// Slurps comma separated values (CSV), at the specified URL, as a
	// list of data records. Each data record is itself a list of strings.
	public static List<List<String>>	getCSVData(URL url)
	{
		List<List<String>>		data = new ArrayList<List<String>>();

		try
		{
			InputStream		is = url.openStream();
			InputStreamReader	ir = new InputStreamReader(is);
			BufferedReader		br = new BufferedReader(ir);

			for (CSVRecord item : FORMAT.parse(br))
			{
				ArrayList<String>	record = new ArrayList<String>();

				for (String attribute : item)
					record.add(attribute);

				data.add(record);
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

		return data;
	}

	// Slurps a comma separated values (CSV) file, at the specified URL, as a
	// list of data records. Each data record is itself a list of strings. This
	// version doesn't work when fields contain commas, even if inside quotes.
	// public static List<List<String>>	getCSVData(URL url)
	// {
	// 	ArrayList<String>	lines = getLines(url);
	// 	List<List<String>>	data = new ArrayList<List<String>>();

	// 	for (String item : lines)
	// 	{
	// 		// Skip empty lines and lines that start with '#'
	// 		if ((item.length() == 0) || (item.charAt(0) == '#'))
	// 			continue;

	// 		StringTokenizer	st = new StringTokenizer(item, ",");
	// 		ArrayList<String>	record = new ArrayList<String>();

	// 		while (st.hasMoreTokens())
	// 			record.add(st.nextToken());

	// 		data.add(record);
	// 	}

	// 	return data;
	// }

	// Spouts comma separated values (CSV), into the specified File, as a
	// list of data records. Each data record is itself a list of strings.
	public static void	putCSVData(File file, List<List<String>> data)
	{
		try
		{
			FileWriter			fw = new FileWriter(file);
			BufferedWriter		bw = new BufferedWriter(fw);
			CSVPrinter			printer = new CSVPrinter(bw, FORMAT);

			for(List<String> record : data)
				printer.printRecord(record);

			printer.flush();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	// Slurps the lines from the specified URL into an array of strings.
	public static ArrayList<String>	getLines(URL url)
	{
		ArrayList<String>		v = new ArrayList<String>();

		try
		{
			InputStream		is = url.openStream();
			InputStreamReader	isr = new InputStreamReader(is);
			BufferedReader		r = new BufferedReader(isr);

			appendLines(r, v);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return v;
	}

	// Appends lines from the specified reader to an array of strings.
	public static void	appendLines(Reader r, ArrayList<String> v)
		throws IOException
	{
		LineNumberReader	lr = new LineNumberReader(r);
		String				line;

		do
		{
			line = lr.readLine();

			if (line != null)
				v.add(line);
		}
		while (line != null);
	}
}

//******************************************************************************
