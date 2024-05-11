import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {
            makeRecord();
            System.out.println("Запись успешно создана.");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static void makeRecord() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Введите данные в следующем формате: фамилия имя отчество дата_рождения(формат: dd.mm.yyyy) номер_телефона пол(m/f): ");
            String input = reader.readLine().trim();
            
            String[] array = input.split(" ");
            if (array.length > 6) {
                throw new Exception("Введено больше параметров");
            } else if (array.length < 6) {
                throw new Exception("Введено меньше параметров");
            }

            String surname = array[0];
            String name = array[1];
            String patronymic = array[2];
            String birthDateString = array[3];
            String phoneNumberString = array[4];
            String gender = array[5];

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date birthDate;
            try {
                birthDate = dateFormat.parse(birthDateString);
            } catch (ParseException e) {
                throw new ParseException("Неверный формат даты рождения. Правильный формат: dd.mm.yyyy", e.getErrorOffset());
            }

            long phoneNumber;
            try {
                phoneNumber = Long.parseLong(phoneNumberString);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Неверный формат номера телефона. Введите целое беззнаковое число без форматирования.");
            }

            if (!gender.toLowerCase().equals("m") && !gender.toLowerCase().equals("f")) {
                throw new Exception("Неверно введен пол. Введите 'm' или 'f'.");
            }

            String fileName = "txt/" + surname.toLowerCase() + ".txt";
            File file = new File(fileName);

            if (file.exists()) {
                System.out.println("Файл с такой фамилией уже существует. Данные будут добавлены в него.");
            }

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
                if (file.length() > 0) {
                    writer.println();
                }
                writer.printf("%s %s %s %s %s %s", surname, name, patronymic, dateFormat.format(birthDate), phoneNumber, gender);
            } catch (IOException e) {
                throw new IOException("Ошибка при работе с файлом: " + e.getMessage());
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
