package Discord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Created by elhj on 2018/06/27.
 */
public class UserListFunctions {

    private long[] testUserList = new long[60];

    public void readSL(){

        try {
            Scanner scanner = new Scanner(new FileReader("src\\Discord\\UserList.txt"));

            //long[] values = new long[100];

            int i = 0;
            while (scanner.hasNextInt() || scanner.hasNextLong()) {
                if (scanner.hasNextInt()){
                    testUserList[i] = scanner.nextInt();
                }
                else {
                    testUserList[i] = scanner.nextLong();
                }

                i++;
            }



        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void printSL(){
        out.println(Arrays.toString(testUserList));
    }

    public long[] getSL(){
        return testUserList;
    }

    public void writeSL(Long newUser){
        try
        {
            //TODO- CHANGE TO REAL LIST
            //TODO: CHECK FOR DUPLICATES
            String filename= "src/Discord/testFile.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(newUser+"\n");//appends the string to the file
            fw.close();
            readSL();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
        finally{
            if(out != null){
                out.close();
            }
        }
    }
}
