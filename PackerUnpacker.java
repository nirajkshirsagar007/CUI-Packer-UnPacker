import java.io.*;
import java.util.*;

class PackerUnpacker {
    public static void main(String[] args) {
        Scanner sobj = new Scanner(System.in);
        int choice;
        
        System.out.println("---------------------------------------------------------------------");
        System.out.println("-------------- Marvellous Packer Unpacker CUI Module ----------------");
        System.out.println("---------------------------------------------------------------------");
        
        System.out.println("Select an operation:");
        System.out.println("1. Pack Files");
        System.out.println("2. Unpack Files");
        System.out.println("3. Exit");
        
        System.out.print("Enter your choice: ");
        choice = sobj.nextInt();
        sobj.nextLine();
        
        switch (choice) {
            case 1:
                packFiles(sobj);
                break;
            case 2:
                unpackFiles(sobj);
                break;
            case 3:
                System.out.println("Exiting application...");
                return;
            default:
                System.out.println("Invalid choice! Please select 1 or 2.");
        }
    }
    
    public static void packFiles(Scanner sobj) {
        String DirName, PackName, Header;
        int i, j, iRet, iCount = 0;
        FileOutputStream fopackobj = null;
        FileInputStream fiobj = null;
        boolean bret;
        File Packobj, fobj;
        
        try {
            byte Buffer[] = new byte[1024];
            
            System.out.println("---------------------------- Packing Activity -----------------------");
            
            System.out.print("Enter the directory name for packing: ");
            DirName = sobj.nextLine();
            
            System.out.print("Enter the packing file name to create: ");
            PackName = sobj.nextLine();
            
            Packobj = new File(PackName);
            bret = Packobj.createNewFile();
            if (!bret) {
                System.out.println("Unable to create packed file. Exiting...");
                return;
            }
            
            fopackobj = new FileOutputStream(Packobj);
            fobj = new File(DirName);
            
            if (fobj.exists()) {
                System.out.println("Directory successfully opened for packing.");
                File Arr[] = fobj.listFiles();
                
                for (i = 0; i < Arr.length; i++) {
                    if (Arr[i].getName().endsWith(".txt")) {
                        Header = Arr[i].getName() + " " + Arr[i].length();
                        
                        for (j = Header.length(); j < 100; j++) {
                            Header += " ";
                        }
                        
                        fopackobj.write(Header.getBytes(), 0, 100);
                        
                        fiobj = new FileInputStream(Arr[i]);
                        while ((iRet = fiobj.read(Buffer)) != -1) {
                            fopackobj.write(Buffer, 0, iRet);
                        }
                        fiobj.close();
                        iCount++;
                    }
                }
                
                System.out.println("Total files scanned: " + Arr.length);
                System.out.println("Total files packed: " + iCount);
                fopackobj.close();
            } else {
                System.out.println("Directory does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e);
        }
    }
    
    public static void unpackFiles(Scanner sobj) {
        int FileSize, iRet, iCount = 0;
        String PackName, SHeader;
        File fobj, fobjX;
        FileOutputStream foobj;
        FileInputStream fiobj;
        
        try {
            System.out.println("---------------------------- Unpacking Activity -----------------------");
            
            System.out.print("Enter the packed file name to unpack: ");
            PackName = sobj.nextLine();
            
            fobj = new File(PackName);
            
            if (!fobj.exists()) {
                System.out.println("Packed file does not exist.");
                return;
            }
            
            fiobj = new FileInputStream(fobj);
            byte Header[] = new byte[100];
            
            while ((iRet = fiobj.read(Header)) != -1) {
                SHeader = new String(Header).trim();
                String Arr[] = SHeader.split(" ");
                
                fobjX = new File(Arr[0]);
                fobjX.createNewFile();
                
                System.out.println("File extracted: " + Arr[0]);
                
                foobj = new FileOutputStream(fobjX);
                FileSize = Integer.parseInt(Arr[1]);
                byte Buffer[] = new byte[FileSize];
                
                fiobj.read(Buffer);
                foobj.write(Buffer, 0, FileSize);
                
                foobj.close();
                iCount++;
            }
            fiobj.close();
            
            System.out.println("Total files extracted: " + iCount);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e);
        }
    }
}
