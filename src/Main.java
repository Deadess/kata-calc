import java.util.Scanner;

class  Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        System.out.println(calc(input));
        s.close();
    }

    public static String calc(String input) {
        InputParser ip = new InputParser(input);
        try {
            return Integer.valueOf(ip.parseInput().calculate()).toString();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;        
    }
}