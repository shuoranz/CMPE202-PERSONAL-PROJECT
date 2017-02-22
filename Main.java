import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileReader;

import net.sourceforge.plantuml.FileFormat; 
import net.sourceforge.plantuml.FileFormatOption; 
import net.sourceforge.plantuml.SourceStringReader; 
import net.sourceforge.plantuml.syntax.SyntaxChecker; 
import net.sourceforge.plantuml.syntax.SyntaxResult;

public class Main {
	public static void main(String[] args) throws IOException{
		
		//System.out.println("test begin");
		StringBuilder plantUmlSource = new StringBuilder();
        plantUmlSource.append("@startuml\n");
		try {
		    File file = new File("src/Hello1.java");
		    Scanner scanner = new Scanner(file);
		    String lines;
		    while(scanner.hasNextLine()){
		    	lines = scanner.nextLine();
		    	//plantUmlSource.append(lines);
		    	System.out.println(lines);
		    }
		    scanner.close();
		    
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}

        plantUmlSource.append("Alice -> Bob: Authentication Request\n");

        plantUmlSource.append("Bob --> Alice: Authentication Response\n");

        plantUmlSource.append("@enduml");
        
        SourceStringReader reader = new SourceStringReader(plantUmlSource.toString());

        FileOutputStream output = new FileOutputStream(new File("Hello1.png"));

        reader.generateImage(output, new FileFormatOption(FileFormat.PNG, false));

	}
}
