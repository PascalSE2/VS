package stringtest;

import java.util.Scanner;

public class StringTest {

    public static void main(String[] args) {
//       String starterName = "Name";
//       String ggT_ProzessID = "15";
//       
//       Scanner scan = new Scanner(starterName + " " + ggT_ProzessID);
//       System.out.println(scan.next());
//       System.out.println(scan.next());
//       System.out.println(scan.next());
//       System.out.println(scan.next());
       
       System.out.println(Integer.MAX_VALUE);
       System.out.println(Integer.MIN_VALUE);
       
       Scanner scan_intervall = new Scanner("1-2"+"-"+"4-8");
       scan_intervall.useDelimiter("-");
//     System.out.println(scan_intervall.next());
//     System.out.println(scan_intervall.next());
//     System.out.println(scan_intervall.next());
//     System.out.println(scan_intervall.next());
       int min_anz = scan_intervall.nextInt();
       int max_anz = scan_intervall.nextInt();
       int min_delay = scan_intervall.nextInt();
       int max_delay = scan_intervall.nextInt();
    }

}
