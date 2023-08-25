import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress ork = new GameProgress(5000, 1200, 54, 5);
        GameProgress mag = new GameProgress(3000, 1700, 55, 62);
        GameProgress adk = new GameProgress(3500, 1800, 52, 60);

        saveGame("C:/Games/Games/savegames/ork.dat", ork);
        saveGame("C:/Games/Games/savegames/mag.dat", mag);
        saveGame("C:/Games/Games/savegames/adk.dat", adk);

        List<String> files = new ArrayList<>();
        files.add("C:/Games/Games/savegames/ork.dat");
        files.add("C:/Games/Games/savegames/mag.dat");
        files.add("C:/Games/Games/savegames/adk.dat");

        zipFiles("C:/Games/Games/savegames/games.zip", files);

        deleteFiles(files);
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress);
            System.out.println("Игровой прогресс сохранен");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения игрового процесса");
            e.printStackTrace();
        }
    }

    public static void zipFiles(String zipFilePath, List<String> files) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {
            for (String filePath : files) {
                File file = new File(filePath);
                FileInputStream fileInputStream = new FileInputStream(file);

                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, bytesRead);
                }

                fileInputStream.close();
                zipOutputStream.closeEntry();
            }

            System.out.println("Файлы заархивированы");
        } catch (IOException e) {
            System.out.println("Ошибка архивирования файлов");
            e.printStackTrace();
        }
    }

    public static void deleteFiles(List<String> files) {
        for (String filePath : files) {
            File file = new File(filePath);
            if (!file.delete()) {
                System.out.println("Ошибка удаления файла: " + filePath);
            }
        }

        System.out.println("Файл успешно удален");
    }


}