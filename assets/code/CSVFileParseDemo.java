import java.io.*;
import java.util.*;

public class CSVFileParseDemo {

    public List<Person> parse(String filePath) {
        File file = new File(filePath);
        List<Person> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            System.out.println(br.readLine()); // 打印CSV文件数据字段标签
            while ((str = br.readLine()) != null) {
                String fileds[] = str.split(",");
                list.add(new Person(fileds[0].trim().replace("\"", ""), fileds[1].trim().replace("\"", ""),
                        Integer.parseInt(fileds[2].trim()), Float.parseFloat(fileds[3].trim().replace("\"", "")),
                        Float.parseFloat(fileds[4].trim().replace("\"", ""))));

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String args[]) {
        CSVFileParseDemo demo = new CSVFileParseDemo();
        List<Person> list = demo.parse(args[0]);
        list.stream().forEach(System.out::println);
    }

    class Person {
        String name;
        String sex;
        int age;
        float height;
        float weight;

        public Person(String name, String sex, int age, float height, float weight) {
            this.name = name;
            this.sex = sex;
            this.age = age;
            this.height = height;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Name:\t" + this.name + "\r\nSex:\t" + this.sex + "\r\nAge:\r" + this.age + "\r\nHeight:\t"
                    + this.height + "in\r\nWeight:\t" + this.weight + "lbs";
        }
    }
}
