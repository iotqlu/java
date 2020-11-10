import java.io.*;

class TextFileReadDemo {

    public void print(String filePath) {
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        TextFileReadDemo demo = new TextFileReadDemo();
        demo.print(args[0]);
    }

}