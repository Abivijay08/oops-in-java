import java.io.*; 
import java.util.*;

public class rec {
    
    String F = "Record.txt"; Scanner S = new Scanner(System.in);

    void cF() throws IOException {new File(F).createNewFile();}
    void wF(String d) throws IOException {try(FileWriter f = new FileWriter(F)){f.write(d);}}
    void aF(String d) throws IOException {try(FileWriter f = new FileWriter(F, true)){f.write(d);}}
    
    void rF() throws IOException {
        try(BufferedReader b = new BufferedReader(new FileReader(F))){
            System.out.println("Records:"); b.lines().forEach(System.out::println);
        }
    }

    void uF(String id, String nD) throws IOException {
        File f = new File(F); List<String> l = new ArrayList<>();
        try(BufferedReader b = new BufferedReader(new FileReader(f))){
            String line; while((line = b.readLine()) != null) l.add(line.startsWith(id + " ") ? nD : line);
        }
        try(FileWriter fw = new FileWriter(f)){
            for(String s : l) fw.write(s + "\n");
        }
    }
    
    private String gR(){
        System.out.print("ID: ");String id=S.nextLine();
        System.out.print("Name: ");String n=S.nextLine();
        System.out.print("Age: ");String a=S.nextLine();
        return id+" "+n+" "+a+"\n";
    }

    public static void main(String[] args) {
        rec app = new rec();
        
        try {app.cF(); System.out.println("File ready.");} 
        catch (IOException e) {System.err.println("FATAL: Cannot init file."); return;}
        
        while(true){
            System.out.println("\n1.Overwrite \n2.Append \n3.Read \n4.Update \n5.Exit");
            System.out.print("Choose: ");
            
            try {
                int c = Integer.parseInt(app.S.nextLine());
                
                try { 
                    if (c == 1 || c == 2) {
                        System.out.print("No.of Students: "); int n = Integer.parseInt(app.S.nextLine());
                        StringBuilder sb = new StringBuilder(); for(int i=0; i<n; i++) sb.append(app.gR());
                        if (c == 1) app.wF(sb.toString()); else app.aF(sb.toString());
                        System.out.println(c==1 ? "Overwritten." : "Appended.");
                    } else if (c == 3) {
                        app.rF();
                    } else if (c == 4) {
                        System.out.print("ID to update: ");String uId = app.S.nextLine();
                        System.out.println("NEW:"); String nR = app.gR().trim();
                        app.uF(uId, nR);
                        System.out.println("Updated.");
                    } else if (c == 5) {
                        System.out.println("Bye!"); app.S.close(); return;
                    } else {
                        System.out.println("Invalid choice.");
                    }
                } catch (IOException fileEx) {
                    System.err.println("FILE ERROR: Operation failed.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input.");
            }
        }
    }
}
