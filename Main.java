 public class Main {
    public static void main(String[] args) {
        XMLParser obj = new XMLParser();
        try
        {
            obj.filterLicenses("License1.xml");
            obj.filterLicenses("License2.xml");
            obj.mergeFiles("ValidLicense.txt","InvalidLicense.txt");
            System.out.println("Files Merged Into Merged.txt");
        }
        catch(Exception e){}
    }
    }
 