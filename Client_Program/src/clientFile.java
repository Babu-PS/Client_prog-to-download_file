import java.io.*;
import java.net.*;
import java.util.*;
public class clientFile
{
	
	public static void main(String[] args) 
	{
		Socket clientSocket = null;
		String ipaddress;
		String portaddress;
		try
		{
			Scanner input = new Scanner(System.in);
			System.out.println("Enter the IP Address: ");
			ipaddress  = input.nextLine();						 // takes the IP dynamically
			System.out.println("Enter the Port Number: ");
			portaddress = input.nextLine();				        	 // takes the port number dynamically
			clientSocket = new Socket(ipaddress,Integer.parseInt(portaddress)); 	// connects to the server
			System.out.println("Enter the File name: ");
			String fileName = input.nextLine(); 					// requests file name
			input.close();
			PrintWriter socketOut = new PrintWriter(clientSocket.getOutputStream(),true);
			socketOut.println(fileName); 						//  sends file name to the server
			DataInputStream socketIn = new DataInputStream(clientSocket.getInputStream());
			int flag = socketIn.readInt();						// reads the status of the requested file
			if(flag == 0)
			{
				System.out.println(fileName + " doesn't exists on Server");
			}
			else
			{
			    String downloadFolder = "C:\\Downloads\\";
			    File requestedfile =new File(downloadFolder + fileName); 		// creates a new file instance
			    if(requestedfile.exists())
				System.out.println("File Already Exists.. Overriding now...!");
			    long sizeofFile = socketIn.readLong();					// reads the size of the requested file
			    System.out.println("The file size is : " + sizeofFile + " bytes.");
			    byte[] filearray = new byte[(int) sizeofFile];				// allocates the bytes for the new file	
			    InputStream inputStream = clientSocket.getInputStream();		// input stream of bytes
			    FileOutputStream fileOutputStream = new FileOutputStream(requestedfile); // creates a file at the location named as the arguments passed into it.
			    int bytesRead = inputStream.read(filearray, 0, filearray.length)	 //  this variable contains the number of bytes read by this method
                            int current = bytesRead;								//  variable to count the total number of bytes read
                            do
                            {
                              bytesRead = inputStream.read(filearray, current, (filearray.length-current)); 	// read the next ones if file size is large
                               if(bytesRead >= 0) 
                	            current += bytesRead;				  // increment the count until the read bytes is Zero
                            } while(bytesRead > 0);
                            fileOutputStream.write(filearray, 0 , current);			 // write to the file the data stored in the array.
                            fileOutputStream.flush();
	                    System.out.println(fileName + " is downloaded at location " + downloadFolder  );
	                    fileOutputStream.close();							// close the file writing procedure
	                    inputStream.close();
			}
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
