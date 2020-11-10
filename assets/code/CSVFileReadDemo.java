import java.io.*;

public class CSVFileReadDemo {

    public void print(String filePath) {
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                String fileds[] = str.split(",");
                for (String s : fileds) {
                    s = s.replace("\"", "");
                    System.out.print(s);
                    System.out.print("\t");
                }
                System.out.println();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        CSVFileReadDemo demo = new CSVFileReadDemo();
        demo.print(args[0]);
    }
}
