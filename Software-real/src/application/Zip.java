package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip{

    public static void main(String[] args) {
        String arquivoZip = "arquivos_compactados.zip";
        String[] arquivosACompactar = {"src/arquivo1.txt", "src/arquivo2.txt", "src/arquivo3.txt"};

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(arquivoZip))) {
            for (String arquivo : arquivosACompactar) {
                ZipEntry entry = new ZipEntry(arquivo);
                zos.putNextEntry(entry);

                try (FileInputStream fis = new FileInputStream(arquivo)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
                zos.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Arquivos compactados em " + arquivoZip);
    }
}