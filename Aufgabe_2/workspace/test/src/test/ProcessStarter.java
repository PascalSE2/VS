package test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class ProcessStarter {

    public static void main(String[] args) {
//        Byte[] barray = {'e','x','i','t'};
//        String command = null;
//        command = "java test2.TestProcess";
//        String working_dir = System.getProperty("user.dir");
//        System.out.println(working_dir);
//        working_dir += "\\bin";
//        System.out.println(working_dir);

//        builder.directory(new File(working_dir));

//        builder.command(command);

        ProcessBuilder builder = new ProcessBuilder();

        try {
            builder.command("cmd", "/c", "java", "test2.TestProcess", "prozess1");
            builder = builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
//            builder = builder.redirectErrorStream(true);
            Process p = builder.start();
            
            PrintStream out;
            out = new PrintStream(p.getOutputStream());
            out.println("exit");
            out.println("test1");
            out.println("test2");
//            out.p
            out.flush();
            
            String scanString = "";
            Scanner scan = new Scanner(System.in);
            scanString = scan.next();
            System.out.println(scanString);
            
//            scanString = reader.readLine();
//            System.out.println(scanString);
            

//            out.flush();

//            p.destroy();
            p.waitFor();

            System.out.println("prozess beendet");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void test() {
        ProcessBuilder builder = new ProcessBuilder();

            builder.command("cmd", "/c", "java", "ggtProzess.Client_ggT", "-ORBInitialHost", "localhost", "-ORBInitialPort", "1050", "Koordinator1", "Starter 1",
                    "1", "0", "5");

            try {
                builder.start();
                System.out.println("Prozess gestartet!");
            } catch (IOException e) {
                e.printStackTrace();
         
        }   // TODO Auto-generated method stub

    }
}
