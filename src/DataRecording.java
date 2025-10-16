import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

class DataRecording {
    private static final Gson gson = new Gson();

    public static <T> List<T> readListFromFile(File file, Class<?> clazz) {
        List<T> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Gson gson = new Gson();
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();

            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            if (jsonBuilder.length() > 0) {
                result = gson.fromJson(jsonBuilder.toString(), listType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <T> void writeListToFile(File file, List<T> data, Class<?> clazz) {
        List<T> existingData = new ArrayList<>();

        if (file.exists() && file.length() > 0) {
            existingData = readListFromFile(file, clazz);
        }

        existingData.addAll(data);

        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            String json = gson.toJson(existingData);
            writer.write(json);
            System.out.println("Данные успешно добавлены в файл: " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
