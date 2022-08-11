import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Cows_bulls {
    public static void main(String[] args) throws IOException {
        int cow = 0;
        int bull = 0;
        int[] arr = new int[4];
        int num;
        int k = FindFile.readFileByFilter("logs.txt")+1;
        UpdateFile.appendUsingOutputStream("logs.txt","Game " + k + "\n");
        arr[0] = (int)(Math.random() * 10);
        for (int i = 1; i < 4; i++) {
            do {
                num = (int) (Math.random() * 10);
            }
            while (isDublicate(arr, i, num));
            arr[i] = num;
        }
        String rand = Arrays.toString(arr);
        StringBuilder builder = new StringBuilder();
        Arrays.stream(arr).forEach(builder::append);

        System.out.println("Вводите числа, чтобы угадать!");
        //System.out.println(builder); // Что было загадано
        UpdateFile.appendUsingOutputStream("logs.txt",builder + "\n");
        Scanner sc = new Scanner(System.in);
        int[] input = new int[4];
        int[] sync = new int[4];
        int x;
        do {
            cow = 0;
            bull = 0;
            String str = sc.next();
            UpdateFile.appendUsingOutputStream("logs.txt", str + " ");
            for (int i = 0; i < 4; i++) {
                input[i] = Integer.valueOf(str.charAt(i)) - 48;
                sync[i] = rand.indexOf(Integer.toString(input[i]));
            }

            for (int i = 0; i < 4; i++) {
                if (sync[i] == i * 3 + 1) {
                    bull += 1;
                } else if (sync[i] != -1) {
                    cow += 1;
                }
            }
            switch (bull) {
                case 0:
                    System.out.print(bull + " быков ");
                    UpdateFile.appendUsingOutputStream("logs.txt", bull + " быков ");
                    break;
                case 1:
                    System.out.print(bull + " бык ");
                    UpdateFile.appendUsingOutputStream("logs.txt", bull + " бык ");
                    break;
                default:
                    System.out.print(bull + " быка ");
                    UpdateFile.appendUsingOutputStream("logs.txt", bull + " быка ");
                    break;
            }
            switch (cow) {
                case 0:
                    System.out.println(cow + " коров");
                    UpdateFile.appendUsingOutputStream("logs.txt", cow + " коров\n");
                    break;
                case 1:
                    System.out.println(cow + " корова");
                    UpdateFile.appendUsingOutputStream("logs.txt", cow + " корова\n");
                    break;
                default:
                    System.out.println(cow + " коровы");
                    UpdateFile.appendUsingOutputStream("logs.txt", cow + " коровы\n");
                    break;
            }
        } while (bull != 4);
        System.out.println("ПОБЕДА!");
    }

    static boolean isDublicate(int[] arr, int i, int num) {
        for (int j = 0; j < i; j++) {
            if (arr[j] == num) {
                return true;
            }
        }
        return false;
    }
}

class UpdateFile {

    public static void main(String[] args) {
        String filePath = "/Users/prologistic/update.txt";
        String appendText = "Этой строкой мы будем обновлять существующий файл";
        // обновляем файл с помощью FileWriter
        appendUsingFileWriter(filePath, appendText);

        // обновляем файл с помощью OutputStream
        appendUsingOutputStream(filePath, appendText);
    }

    // обновляем файл с помощью FileWriter
    static void appendUsingOutputStream(String fileName, String data) {
        OutputStream os = null;
        try {
            //в конструкторе FileOutputStream используем флаг true, который обозначает обновление содержимого файла
            os = new FileOutputStream(new File(fileName), true);
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // добавляем информацию в файл с помощью FileWriter
    private static void appendUsingFileWriter(String filePath, String text) {
        File file = new File(filePath);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file,true);
            fr.write(text);

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class FindFile {
    public static int readFileByFilter(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int x = 0;
        String game = "Game";
        while (reader.ready()) {
            String tmp = reader.readLine();
            if (tmp.contains(game)) {
                x += 1;
            }
        }
        reader.close();
        return x;
    }
}
