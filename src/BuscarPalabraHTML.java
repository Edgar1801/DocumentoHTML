import javax.swing.text.*;
import javax.swing.text.html.*;
import java.io.*;
import java.util.*;

public class BuscarPalabraHTML {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java BuscarPalabraHTML <archivo.html> <palabra>");
            System.exit(1);
        }
        String nombreArchivo = args[0];
        String palabra = args[1];
        List<Integer> posiciones = new ArrayList<>();
        try {
            FileReader fr = new FileReader(nombreArchivo);
            HTMLEditorKit htmlKit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) htmlKit.createDefaultDocument();
            htmlKit.read(fr, doc, 0);
            String contenido = doc.getText(0, doc.getLength());
            int index = contenido.indexOf(palabra);
            while (index >= 0) {
                posiciones.add(index);
                index = contenido.indexOf(palabra, index + 1);
            }
            if (posiciones.isEmpty()) {
                System.out.println("No se encontraron ocurrencias de la palabra '" + palabra + "' en el documento.");
            } else {
                System.out.println("Ocurrencias de la palabra '" + palabra + "' en el archivo '" + nombreArchivo + "':");
                for (int pos : posiciones) {
                    System.out.println("Posicion: " + pos);
                }
                String logFileName = "file-" + palabra + ".log";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName))) {
                    writer.write("Archivo: " + nombreArchivo + "\n");
                    writer.write("Palabra: " + palabra + "\n");
                    writer.write("Posiciones:\n");
                    for (int pos : posiciones) {
                        writer.write("Posicion: " + pos + "\n");
                    }
                }

                System.out.println("Bitacora generada: " + logFileName);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado - " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo HTML.");
        } catch (BadLocationException e) {
            System.err.println("Error al procesar el contenido del documento.");
        }
    }
}