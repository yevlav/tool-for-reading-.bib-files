import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
	
	public static String readFile(String name){
		
		Scanner fileReader = null; 
		String fileInfo = "";
		
		try {
			fileReader = new Scanner(new FileInputStream(name));
			
			while(fileReader.hasNext() == true ) {
		        fileInfo += fileReader.nextLine() + "\n";
		    }
		}
		catch(IOException e) {
			System.out.println("\nCould not open input file " + name + " for reading. \nPlease check if file exists! Program will terminate after closing any opened files.");
		    System.exit(0);
		}
		return fileInfo;
	}
	
	//method to create 3 files for each .bib
	public static void createFiles() {
		
		int number = 1;
		
		while(number <= 10) {
			String s1 = "IEEE" + number + ".json";
			String s2 = "ACM" + number + ".json";
			String s3 = "NJ" + number + ".json";

			File file;
			file = new File(s1);
			try {
				file.createNewFile();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			file = new File(s2);
			try {
				file.createNewFile();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			file = new File(s3); 
			try {
				file.createNewFile();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			number++;
		}
	}
	
	public static void deleteFile(File fileToDelete) {
		fileToDelete.delete();
	}
	
	public static void processFilesForValidation() {
		
		int number = 1;
		int counter = 0;
		
		while(number <= 10) {
			String fileName = "Latex" + number + ".bib";
			//if file is not valid -> delete 3 created files
			if(validateFile(readFile(fileName), fileName) != null) {
				System.out.println(validateFile(readFile(fileName), fileName));	
				
				File file;	
				file = new File("IEEE" + number + ".json");
				deleteFile(file);
				file = new File("ACM" + number + ".json");
				deleteFile(file);
				file = new File("NJ" + number + ".json");
				
			}
			//if file is valid -> write info to the file
			else {
				File file1 = new File("IEEE" + number + ".json");
				File file2 = new File("ACM" + number + ".json");
				File file3 = new File("NJ" + number + ".json");
				PrintWriter pw = null;
					
				//IEEE
				try {
					String result = "";	  
					String myContent = "";
					String[] parts = readFile(fileName).split("@ARTICLE");
					    
					for(int i = 0; i < parts.length; i++) {
						String[] fields = parts[i].split("\n");	
					  			
					  	for(int j = 1; j < fields.length; j++) {
					  				
					  		if((fields[j].indexOf("author={") != -1) && fields[j].indexOf("},") != -1) {
					  			myContent = fields[j].substring(fields[j].indexOf("author={")+8, fields[j].indexOf("},")) + ". ";
					  		}
					  				
					  		if((fields[j].indexOf("title={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += "'" + fields[j].substring(fields[j].indexOf("title={")+7, fields[j].indexOf("},")) + "', ";
						  	}
					  		if((fields[j].indexOf("journal={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += fields[j].substring(fields[j].indexOf("journal={")+9, fields[j].indexOf("},")) + ", ";
						  	}
					  				
					  		if((fields[j].indexOf("volume={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += "vol." + fields[j].substring(fields[j].indexOf("volume={")+8, fields[j].indexOf("},")) + ", ";
						  	}
					  				
					  		if((fields[j].indexOf("number={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += "no." + fields[j].substring(fields[j].indexOf("number={")+8, fields[j].indexOf("},")) + ", ";
						  	}
					  				
					  		if((fields[j].indexOf("pages={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += "p." + fields[j].substring(fields[j].indexOf("pages={")+7, fields[j].indexOf("},")) + ", ";
						  	}
					  				
					  		if((fields[j].indexOf("month={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += fields[j].substring(fields[j].indexOf("month={")+7, fields[j].indexOf("},"));
						  	}
//					  		if((fields[j].indexOf("year={") != -1) && fields[j].indexOf("},") != -1) {
//							  	myContent += fields[j].substring(fields[j].indexOf("year={")+6, fields[j].indexOf("},"));
//						  	}		
					  	}
					  	result += myContent + "\n";
					}
					pw = new PrintWriter(file1);
					pw.write(result);
					pw.close();
				} 
				catch(IOException e) {
					e.printStackTrace();
				}
								 
				//ACM
				try {
					String result = "";	  
					String myContent = "";
					int num = 1;
					String[] parts = readFile(fileName).split("@ARTICLE");
						
					for(int i = 0; i < parts.length; i++) {
						String[] fields = parts[i].split("\n");	
							
						for(int j = 1; j < fields.length; j++) {
								
							if((fields[j].indexOf("author={") != -1) && fields[j].indexOf("and") != -1) {
								myContent = "[" + num + "] " + fields[j].substring(fields[j].indexOf("author={")+8, fields[j].indexOf("and")) + " et al. ";
							  	num++;
							}
							
//							if((fields[j].indexOf("year={") != -1) && fields[j].indexOf("},") != -1) {
//							  	myContent += fields[j].substring(fields[j].indexOf("year={")+6, fields[j].indexOf("},"));	
//						  	}
							
							if((fields[j].indexOf("title={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += fields[j].substring(fields[j].indexOf("title={")+7, fields[j].indexOf("},")) + ". ";
						  	}
							
							if((fields[j].indexOf("journal={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += fields[j].substring(fields[j].indexOf("journal={")+9, fields[j].indexOf("},")) + ". ";
						  	}
							
							if((fields[j].indexOf("volume={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += fields[j].substring(fields[j].indexOf("volume={")+8, fields[j].indexOf("},")) + ", ";
						  	}
							
							if((fields[j].indexOf("number={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += fields[j].substring(fields[j].indexOf("number={")+8, fields[j].indexOf("},")) + ", ";
						  	}
							
							if((fields[j].indexOf("year={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += "(" + fields[j].substring(fields[j].indexOf("year={")+6, fields[j].indexOf("},")) + "), ";	
						  	}
							
							if((fields[j].indexOf("pages={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += fields[j].substring(fields[j].indexOf("pages={")+7, fields[j].indexOf("},")) + ". ";
						  	}
							
							if((fields[j].indexOf("doi={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += "DOI:https://doi.org/" + fields[j].substring(fields[j].indexOf("doi={")+5, fields[j].indexOf("},")) + ". ";
						  	}
						}
						result += myContent + "\n";
					}
					pw = new PrintWriter(file2);
					pw.write(result);
					pw.close();
				} 
				catch(IOException e) {
					e.printStackTrace();
				}
					 
				//NJ
				try {
					String result = "";	  
					String myContent = "";
					String[] parts = readFile(fileName).split("@ARTICLE");
						
					for(int i = 0; i < parts.length; i++) {
						String[] fields = parts[i].split("\n");	
							
						for(int j= 1; j < fields.length; j++) {

							if((fields[j].indexOf("author={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent = fields[j].substring(fields[j].indexOf("author={")+8, fields[j].indexOf("},")).replace("and", "&") + ". ";
							}
							if((fields[j].indexOf("title={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += fields[j].substring(fields[j].indexOf("title={")+7, fields[j].indexOf("},")) + ". ";
						  	}
							if((fields[j].indexOf("journal={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += fields[j].substring(fields[j].indexOf("journal={")+9, fields[j].indexOf("},")) + ". ";
						  	}
							if((fields[j].indexOf("volume={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += fields[j].substring(fields[j].indexOf("volume={")+8, fields[j].indexOf("},")) + ", ";
							}
							if((fields[j].indexOf("pages={") != -1) && fields[j].indexOf("},") != -1) {
								myContent += fields[j].substring(fields[j].indexOf("pages={")+7, fields[j].indexOf("},")) + ". ";
							}
							if((fields[j].indexOf("year={") != -1) && fields[j].indexOf("},") != -1) {
							  	myContent += "(" + fields[j].substring(fields[j].indexOf("year={")+6, fields[j].indexOf("},")) + "), ";	
						  	}				
						}
						result += myContent + "\n";
					}
					pw = new PrintWriter(file3);
					pw.write(result);
					pw.close();
				} 
				catch(IOException e) {
					e.printStackTrace();
				}
				counter++;
			}
			number++;
		}
		System.out.println("\nA total of " + (10 - counter) + " files were invalid, and could not be processed. All other " + counter + " 'Valid' files have been created.");
		
		userInput();
	}
	public static void userInput() {
		
		Scanner scanner = new Scanner(System.in);
		//user has 2 tries
		for(int i = 1; i <= 2; i++) {
			System.out.print("\nPlease enter the name of one of the files that you need to review: ");
			String inputName = scanner.nextLine();
			File f = new File(inputName);
			BufferedReader bufferedReader = null;
			
			try {
				bufferedReader = new BufferedReader(new FileReader(f));
				String readingLine;
				System.out.println("\nHere are the contents of the successfully created json File: " + f);
				//reading each line
				while((readingLine = bufferedReader.readLine()) != null){
					System.out.println(readingLine);
				}
				System.out.println("\nGoodbye! Hope you have enjoyed creating the needed files using BibCreator.");
				System.exit(0);
			}
			catch (IOException e) {
			System.out.println("\nCould not open input file. File does not exist; possibly it could not be created.");
			System.out.println("However, you will be allowed another chance to enter another file name");
			}
		}
		System.out.println("\nGoodbye! Hope you have enjoyed creating the needed files using BibCreator.");
		System.exit(0);
		scanner.close();
	}
	
	public static String validateFile(String fileInfo, String nameOfFile) {
		
		String[] parts = fileInfo.split("@ARTICLE");
		
		for(int i = 0; i < parts.length; i++) {
			String[] fields = parts[i].split("\n");
			
			for(int j = 1; j < fields.length; j++) {
				
				if((fields[j].indexOf("{") !=-1) && (fields[j].indexOf("}")!=-1) && (fields[j].indexOf("{")+1 == fields[j].indexOf("}"))) {
					String info = "\n\nError: Detected Empty Field!\n====================\nProblem detected with input file: " + nameOfFile + "\nFile is Invalid: Field '" + fields[j].substring(0, fields[j].indexOf("=")) + "' is Empty. Processing stopped at this point. Other empty fields may be present as well!";	
					return info;
				}
			}
		}
		return null;
	}
	

	public static void main(String[] args) throws IOException {
		
		System.out.println("Welcome to BibCreator!");
		
		int number = 1;
		String fileTitle = "Latex";
		//checking if files exist
		while(number <= 10) {
			String s = fileTitle + number + ".bib";
			readFile(s);
			number++;
		}
		
		createFiles();
		processFilesForValidation();
		
	}	
}