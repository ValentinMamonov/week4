import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        //1.1
        System.out.println("--------------------------------------------");
        Scanner sc = new Scanner(System.in);

        System.out.print("Что ищем: ");
        String search = sc.nextLine();

        System.out.println("Введите строки, где ищем (ввод пустой строки завершает ввод):");
        String[] strings = new String[100];
        int n = 0;

        while (true) {
            String line = sc.nextLine();
            if (line.isEmpty()) break;
            strings[n++] = line;
        }

        int count = 0;
        for (int i = 0; i < n; i++) {
            if (strings[i].contains(search)) {
                count++;
            }
        }
        System.out.println("Количество вхождений: " + count);

        //1.2
        System.out.println("--------------------------------------------");
        System.out.print("Введите строку: ");
        String text = sc.nextLine();
        double entropy = calculateEntropy(text);
        System.out.printf("Энтропия: %.2f%n", entropy);

        //1.3
        System.out.println("--------------------------------------------");
        System.out.print("Введите пакет показаний: ");
        String packet = sc.nextLine();

        // Разделяем на показания по '@'
        String[] readings = packet.split("@");

        int maxSensors = 100;
        int[] ids = new int[maxSensors];
        double[] sums = new double[maxSensors];
        int[] counts = new int[maxSensors];
        int sensorCount = 0;

        for (String reading : readings) {
            if (reading.length() < 2) continue;
            int id = Integer.parseInt(reading.substring(0, 2));
            double temp = Double.parseDouble(reading.substring(2));

            int idx = -1;
            for (int i = 0; i < sensorCount; i++) {
                if (ids[i] == id) {
                    idx = i;
                    break;
                }
            }

            if (idx == -1) {
                ids[sensorCount] = id;
                sums[sensorCount] = temp;
                counts[sensorCount] = 1;
                sensorCount++;
            } else {
                sums[idx] += temp;
                counts[idx]++;
            }
        }

        double[] averages = new double[sensorCount];
        for (int i = 0; i < sensorCount; i++) {
            averages[i] = sums[i] / counts[i];
        }

        System.out.print("Сортировать по (id/temperature): ");
        String field = sc.nextLine().toLowerCase();

        // Сортировка пузырьком
        for (int i = 0; i < sensorCount - 1; i++) {
            for (int j = 0; j < sensorCount - i - 1; j++) {
                boolean swap = false;
                if (field.equals("id")) {
                    if (ids[j] > ids[j + 1]) swap = true;
                } else if (field.equals("temperature")) {
                    if (averages[j] > averages[j + 1]) swap = true;
                }
                if (swap) {
                    // меняем id
                    int tmpId = ids[j];
                    ids[j] = ids[j + 1];
                    ids[j + 1] = tmpId;
                    // меняем средние температуры
                    double tmpAvg = averages[j];
                    averages[j] = averages[j + 1];
                    averages[j + 1] = tmpAvg;
                }
            }
        }

        System.out.println("\nРезультат:");
        for (int i = 0; i < sensorCount; i++) {
            System.out.printf("%d %.1f%n", ids[i], averages[i]);
        }

        //2.1
        System.out.println("--------------------------------------------");
        System.out.print("Введите email: ");
        String email = sc.nextLine();

        // Простое регулярное выражение для email
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

        if (Pattern.matches(regex, email)) {
            System.out.println("Email корректен");
        } else {
            System.out.println("Email некорректен");
        }

        //2.2
        System.out.println("--------------------------------------------");
        System.out.print("Введите IPv6 (полная форма): ");
        String ip = sc.nextLine();

        // Регулярное выражение для полной формы IPv6
        String regex2 = "^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$";

        if (Pattern.matches(regex2, ip)) {
            System.out.println("IPv6 корректен");
        } else {
            System.out.println("IPv6 некорректен");
        }


        //2.3
        System.out.println("--------------------------------------------");
        System.out.println("Введите строки (пустая строка для завершения):");
        while (true) {
            String line = sc.nextLine();
            if (line.isEmpty()) break;

            // заменяем все символы, повторяющиеся ≥3 раза, на один
            String result = line.replaceAll("(.)\\1{2,}", "$1");
            System.out.println(result);
        }

    }
    public static double calculateEntropy(String text) {
        if (text == null || text.isEmpty()) return 0;

        text = text.toLowerCase();
        int length = text.length();

        int[] freq = new int[256]; // для ASCII
        for (int i = 0; i < length; i++) {
            freq[text.charAt(i)]++;
        }

        double entropy = 0;
        for (int i = 0; i < 256; i++) {
            if (freq[i] > 0) {
                double p = (double) freq[i] / length;
                entropy -= p * (Math.log(p) / Math.log(2));
            }
        }
        return entropy;
    }

}
