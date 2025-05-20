import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ArquivoUtil {
    public static void salvarArquivo(String nomeArquivo, List<String> linhas) {
        try {
            Files.write(Paths.get(nomeArquivo), linhas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    public static List<String> lerArquivo(String nomeArquivo) {
        try {
            return Files.readAllLines(Paths.get(nomeArquivo));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}