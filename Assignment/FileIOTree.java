/* *
 * Luke McDougall
 *
 * File IO for TreeProfiler. Can save and load tree data in CSV, binary or Serialized format.
 *
 * Last updated 19/05/2019
 * */

import java.io.*;
import java.util.*;
public class FileIOTree
{
    /* Function: loadData
     * Import: String filename
     * Export: StockDay[] array
     * Loads data from file into an array of objects to be used for testing.
     */
    public static StockDay[] loadData(String filename)
    {
        StockDay[] array = null;
        array = readCSV(filename);
        return array;
    }

    /* Function: loadData
     * Import: DSATree tree, String filename
     * Export DSATree tree
     *
     * Loads data from file into a tree. The type of data storage is specified 
     * by the file extension.
     */
    public static DSATree loadData(DSATree tree, String filename)
    {
        if(filename.endsWith(".txt"))   //Load a text file
        {
            readCSV(tree, filename);
        }
        else if(filename.endsWith(".ser"))  //Load a serialized file.
        {
            tree = readSer(filename);
            System.out.println(tree.size() + " loadData");
        }
        else if(filename.endsWith(".bin"))  //Load a binary file.
        {
            readBinary(tree, filename);
        }
        else
        {
            throw new IllegalArgumentException("Error: Filename must end with '.txt' '.bin' or '.ser'");
        }

        return tree;
    }

    /* Function: saveTree
     * Import: DSATree tree, String filename    
     * Export: None.
     *
     * Saves the data stored in a tree to a file specified by filename.
     * type of data storage is specified by file extension.
     */
    public static void saveTree(DSATree tree, String filename)
    {
        if(filename.endsWith(".txt"))   //Save to a text file
        {
            writeCSV(tree, filename);
        }
        else if(filename.endsWith(".ser"))  //Save to a serialized file
        {
            writeSer(tree, filename);
        }
        else if(filename.endsWith(".bin"))  //Save to a binary file
        {
            writeBinary(tree, filename);
        }
        else
        {
            throw new IllegalArgumentException("Error: Filename must end with '.txt' '.bin' or '.ser'");
        }
    
    }
    
    /* Function: readCSV
     * Import: String filename
     * Export: StockDay[] array
     * Reads information from a CSV file and stores it in an array of objects.
     */
    public static StockDay[] readCSV(String filename)
	{	
        int numLines = 0;
        StockDay[] array;
        String line = null;
		FileInputStream fileIn = null;
		InputStreamReader rdr = null;
		BufferedReader bufrdr = null;
		try
		{
			fileIn = new FileInputStream(filename);
			rdr = new InputStreamReader(fileIn);
			bufrdr = new BufferedReader(rdr);
			line = bufrdr.readLine();
			while(line != null) //Count number of lines in the file.
            {
				if(processLine(line))   //Only count valid lines
                {
                    numLines++;
                }
                line = bufrdr.readLine();
			}
			fileIn.close();
			
		}
		catch(IOException e)
		{
			if(fileIn != null)
			{
				try
				{
					fileIn.close();
				}
				catch(IOException e2) {}
			}
			System.out.println("Error in file processing: " + e.getMessage());
		}
        array = new StockDay[numLines]; //Use number of lines for size of array
        try
		{
			fileIn = new FileInputStream(filename);
			rdr = new InputStreamReader(fileIn);
			bufrdr = new BufferedReader(rdr);
			line = bufrdr.readLine();
            int index = 0;
			while(line != null)
			{
				
				if(processLine(line))
				{
					String[] args = line.split(",");
					try
					{
						String ticker = args[0];    //Create StockDay object
						Date d = new Date(Integer.parseInt(args[1].substring(0,4)), 
								          Integer.parseInt(args[1].substring(4,6)), 
								          Integer.parseInt(args[1].substring(6)));
						double open = Double.parseDouble(args[2]);
						double high = Double.parseDouble(args[3]);
						double low = Double.parseDouble(args[4]);
						double close = Double.parseDouble(args[5]);
						int volume = Integer.parseInt(args[6]);
						array[index] = new StockDay(ticker, d, open, high, low, close, volume);
                        index++;
					}
					catch(IllegalArgumentException e)
					{
						System.out.println("Invalid line: " + e.getMessage() + " " +args[0]);
					}
				}
				line = bufrdr.readLine();
			}
			fileIn.close();
			
		}
		catch(IOException e)
		{
			if(fileIn != null)
			{
				try
				{
					fileIn.close();
				}
				catch(IOException e2) {}
			}
			System.out.println("Error in file processing: " + e.getMessage());
		}
		return array;
	}

    /* Function: readCSV
     * Import: DSATree tree, String filename
     * Export: None.
     * Reads data from a CSV file and store it in the passed tree.
     */
    public static void readCSV(DSATree tree, String filename)
	{
		String line = null;
		FileInputStream fileIn = null;
		InputStreamReader rdr = null;
		BufferedReader bufrdr = null;
		try
		{
			fileIn = new FileInputStream(filename);
			rdr = new InputStreamReader(fileIn);
			bufrdr = new BufferedReader(rdr);
			line = bufrdr.readLine();
			while(line != null)
			{
				
				if(processLine(line))
				{
					String[] args = line.split(",");
					try
					{
						String ticker = args[0];
						Date d = new Date(Integer.parseInt(args[1].substring(0,4)), 
								          Integer.parseInt(args[1].substring(4,6)), 
								          Integer.parseInt(args[1].substring(6)));
						double open = Double.parseDouble(args[2]);
						double high = Double.parseDouble(args[3]);
						double low = Double.parseDouble(args[4]);
						double close = Double.parseDouble(args[5]);
						int volume = Integer.parseInt(args[6]);
						tree.insert(ticker, new StockDay(ticker, d, open, high, low, close, volume));
					}
					catch(IllegalArgumentException e)
					{
						System.out.println("Invalid line: " + e.getMessage() + " " +args[0]);
					}
				}
				line = bufrdr.readLine();
			}
			fileIn.close();
			
		}
		catch(IOException e)
		{
			if(fileIn != null)
			{
				try
				{
					fileIn.close();
				}
				catch(IOException e2) {}
			}
			System.out.println("Error in file processing: " + e.getMessage());
		}
	}

    /* Function: processLine
     * Import: String line
     * Export: boolean valid
     *
     * Parses line to determine if it can be stored as a StockDay object.
     * Returns true if it can false if not.
     */
    private static boolean processLine(String line)
	{
		String[] strA = line.split(",");
		boolean valid = true;
		if(strA.length == 7)
		{
			if(strA[1].length() == 8)
			{
				try
				{
					Integer.parseInt(strA[1]);
				}
				catch(NumberFormatException e)
				{
					valid = false;
				}
				
				try
				{
					Double.parseDouble(strA[2]);
				}
				catch(NumberFormatException e)
				{
					valid = false;
				}
				try
				{
					Double.parseDouble(strA[3]);
				}
				
				catch(NumberFormatException e)
				{
					valid = false;
				}
				
				try
				{
					Double.parseDouble(strA[4]);
				}
				catch(NumberFormatException e)
				{
					valid = false;
				}
				
				try
				{
					Double.parseDouble(strA[5]);
				}
				catch(NumberFormatException e)
				{
					valid = false;
				}
				
				try
				{
					Integer.parseInt(strA[6]);
				}
				catch(NumberFormatException e)
				{
					valid = false;
				}
			}
			else
			{
				valid = false;
			}	
		}
		else 
		{
			valid = false;
		}
		return valid;
	}

    /* Function: writeCSV
     * Import: DSATree tree, String filename
     * Export: None
     * Saves data stored in passed tree to a file specified by file name in CSV format.
     */
    public static void writeCSV(DSATree tree, String filename)
    {
        FileOutputStream fileOut = null;
        PrintWriter pw;
        try 
        {
            fileOut = new FileOutputStream(filename);
            pw = new PrintWriter(fileOut);
            pw.println(tree.toString());
            pw.close();
            fileOut.close();
        }
        catch(IOException e)
        {
            if(fileOut != null)
            {
                try
                {
                    fileOut.close();
                }
                catch(IOException e2) {}
            }
            System.out.println("Error in file processing: " + e.getMessage());
        }
    }
    
    /* Function: writeSer
     * Import: DSATree tree, String filename
     * Export: None
     * Saves tree passed tree object in a serialized file specified by filename.
     */
    public static void writeSer(DSATree tree, String filename)
	{
		Scanner sc = new Scanner(System.in);
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut;
		try 
		{
			fileOut = new FileOutputStream(filename);
			objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(tree);
			objOut.close();
			fileOut.close();
		}
		catch(IOException e)
		{
			if(fileOut != null)
			{
				try
				{
					fileOut.close();
				}
				catch(IOException e2) {}
			}
			System.out.println("Error in file processing:  " + e.getMessage());
		}
	}
    
    /* Function: readSer
     * Import: String filename
     * Export: DSATree tree
     * Loads a tree object from a serialized file specified by filename and returns it
     */
    public static DSATree readSer(String filename)
	{
		FileInputStream fileIn = null;
		ObjectInputStream objIn;
        DSATree tree = null;
		try
		{
			fileIn = new FileInputStream(filename);
			objIn = new ObjectInputStream(fileIn);
			tree = (DSATree)objIn.readObject();
            System.out.println(tree.size() + " readSer");
			objIn.close();
			fileIn.close();
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Error in file processing: " + e.getMessage());
		}
		catch(IOException e2)
		{
			if(fileIn != null)
			{
				try
				{
					fileIn.close();
				}
				catch(IOException e3) {}
			}
			System.out.println("Error in file processing: " + e2.getMessage());
		}
        return tree;
	}

    /* Function: writeBinary
     * Import: DSATree tree, String filename
     * Export: None
     *
     * Saves data stored in passed tree in binary format in a file specified by
     * filename.
     */
    public static void writeBinary(DSATree tree, String filename)
    {
        DSAQueue vals = tree.values();
        FileOutputStream fileOut = null;
        DataOutputStream data;
        
        try
        {
            fileOut = new FileOutputStream(filename);
            data = new DataOutputStream(fileOut);
            data.writeInt(vals.getSize());  //Number of objects to be stored.
            while(!vals.isEmpty())
            {
                StockDay d = (StockDay)vals.dequeue();
                data.writeUTF(d.getTicker());
                data.writeInt(d.getYear());
                data.writeInt(d.getMonth());
                data.writeInt(d.getDay());
                data.writeDouble(d.getOpen());
                data.writeDouble(d.getHigh());
                data.writeDouble(d.getLow());
                data.writeDouble(d.getClose());
                data.writeInt(d.getVolume());
            }
            fileOut.close();
        }
        catch(IOException e)
        {
            if(fileOut != null)
            {
                try
                {
                    fileOut.close();
                }
                catch(IOException e2) {}
            }
            System.out.println("Error in file processing " + e.getMessage());
        }
    }

    /* Function: readBinary
     * Import: DSATree tree, String filename
     * Export: None
     * Reads data from a binary file specified by filename and stores it in passed tree
     */
    public static void readBinary(DSATree tree, String filename)
    {
        FileInputStream fileIn = null;
        DataInputStream data;

        try
        {
            fileIn = new FileInputStream(filename);
            data = new DataInputStream(fileIn);
            
            int numObjects = data.readInt();    //Number of objects stored in this file.
            for(int i = 0; i < numObjects; i++)
            {
                String ticker = data.readUTF();
                Date d = new Date(data.readInt(), data.readInt(), data.readInt());
                double open = data.readDouble();
                double high = data.readDouble();
                double low = data.readDouble();
                double close = data.readDouble();
                int volume = data.readInt();

                StockDay s = new StockDay(ticker, d, open, high, low, close, volume);
                tree.insert(s.getTicker(), s); 
            }
            fileIn.close();
        }
        catch(EOFException e)
        {
            System.out.println("End of file reached before all expected data was loaded.");
        }
        catch(IOException e)
        {
            if(fileIn != null)
            {
                try
                {
                    fileIn.close();
                }
                catch(IOException e2) {}
            }
            System.out.println("Error in file processing " + e.getMessage());
        }
    }   
}
