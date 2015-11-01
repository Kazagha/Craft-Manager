import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;


public class XMLFilter extends FileFilter {
	
	public XMLFilter()
	{
		super();
	}

	@Override
	public boolean accept(File f) 
	{
		// Show directories
		if (f.isDirectory())
			return true;
		
		// Fetch the file name
		String name = f.getName();
		String extension = null;
		
		// Find the '. file extension' 
		int index = name.lastIndexOf(".");		
		if (index > 0)
			extension = name.substring(index);
		
		// Check if the extension exists
		if (extension != null)
		{
			// Return true if the file extension is 'CSV'
			if (extension.equalsIgnoreCase(".xml"))
				return true;
		}
	
		return false;
	}

	@Override
	public String getDescription() 
	{
		return "XML (EXtensible Markup Language)";
	}

}
