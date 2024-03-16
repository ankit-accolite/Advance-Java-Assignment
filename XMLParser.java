import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XMLParser {
    
    public void filterLicenses(String FileName) throws Exception
    {
        FileWriter validwriter = new FileWriter("ValidLicense.txt",true);
        FileWriter Invalidwriter = new FileWriter("InvalidLicense.txt",true);
        
        LocalDate date = LocalDate.now();
        
        validwriter.write("Valid Licenses of File "+FileName+" : \n");
        Invalidwriter.write("Expired Licenses of File "+FileName+" : \n");
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String dateF = date.format(dtf);

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //Creating New Instance of Document Builder Factory
            
            DocumentBuilder builder = factory.newDocumentBuilder(); //Creating New Instance of Document Builder
            Document doc = builder.parse(new File(FileName));   //Parsing the xml file to access document
            
            Element rootEle = doc.getDocumentElement();
            NodeList listoflicenses = rootEle.getElementsByTagName("License");
            
            System.out.println("Total Number of Licenses in file "+FileName+" : "+listoflicenses.getLength());
            for(int i=0;i<listoflicenses.getLength();i++)
            {
                Element ele = (Element)listoflicenses.item(i);
                String expdate = ele.getAttribute("License_Expiration_Date");
                
                boolean valid = true;
                String max = maxOfDates(expdate,dateF);
                if(max.equals(dateF))
                valid = false;

                if(valid)
                {
                    expdate = "Expiry Date : "+expdate;
                    String dse = "Date_Status_Effective : "+ele.getAttribute("Date_Status_Effective");
                    String lc = "License_Class : "+ele.getAttribute("License_Class");
                    String lcc = "License_Class_Code : "+ele.getAttribute("License_Class_Code");
                    String lid = "License_Issue_Date : "+ele.getAttribute("License_Issue_Date");
                    String ln = "License_Number : "+ele.getAttribute("License_Number");
                    validwriter.write(dse+" , "+lc+" , "+lcc+" , "+lid+" , "+ln+" , "+expdate+" \n");
                    System.out.println("Valid License : "+ln+" "+expdate);
                }
                else
                {
                    expdate = "Expiry Date : "+expdate;
                    String dse = "Date_Status_Effective : "+ele.getAttribute("Date_Status_Effective");
                    String lc = "License_Class : "+ele.getAttribute("License_Class");
                    String lcc = "License_Class_Code : "+ele.getAttribute("License_Class_Code");
                    String lid = "License_Issue_Date : "+ele.getAttribute("License_Issue_Date");
                    String ln = "License_Number : "+ele.getAttribute("License_Number");
                    Invalidwriter.write(dse+" , "+lc+" , "+lcc+" , "+lid+" , "+ln+" , "+expdate+" \n");
                    System.out.println("Expired License : "+ln+" "+expdate);
                }
            }
        }
        catch(Exception e){}
        validwriter.close();
        Invalidwriter.close();
    }

    public void mergeFiles(String fileA , String fileB) throws Exception
    {
        FileWriter writer = new FileWriter("Merged.txt");
        FileReader reader = new FileReader(fileA);
        BufferedReader brd = new BufferedReader(reader);
        String line;
        while((line = brd.readLine())!=null)
        {
            writer.write(line+"\n");
        }

        reader = new FileReader(fileB);
        brd = new BufferedReader(reader);
        while((line = brd.readLine())!=null)
        {
            writer.write(line+"\n");
        }

        writer.close();
        reader.close();
    }
    
    private String maxOfDates(String a , String b)
    {
        String[] ar1 = a.split("/");
        String[] ar2 = b.split("/");

        int year_a = Integer.parseInt(ar1[2]);
        int year_b = Integer.parseInt(ar2[2]);
        int month_a = Integer.parseInt(ar1[1]);
        int month_b = Integer.parseInt(ar1[1]);
        int day_a = Integer.parseInt(ar1[0]);
        int day_b = Integer.parseInt(ar1[0]);
    
        if(year_a!=year_b)  
            return year_a>year_b?a:b;
        else if(month_a!=month_b)
            return month_a>month_b?a:b;
        else
            return day_a>day_b?a:b;
    }
}
