package blablablog.utils.io;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Base64;

public class IOTools {

    private static final Logger log = Logger.getLogger(IOTools.class.getName());
    private static final int BUFFER_DELTA_SIZE = 1024 * 100;

    public static void writeBufferToFile(String filename, byte[] buffer) throws IOException {
        log.trace("write " + buffer.length + " byte(s) to " + filename);
        try {
            File file = new File(filename);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(buffer);
            }
        } catch (IOException x) {
            log.warn(x.toString(), x);
            throw x;
        }
    }

    public static byte[] readBufferFromStream(InputStream is, int length) throws IOException {
        if (length <= 0) {
            return null;
        }
        try {
            log.trace("read " + length + " byte(s) from stream to bytes array");
            byte[] buffer = new byte[length];
            int cnt;
            int cursor = 0;
            do {
                cnt = is.read(buffer, cursor, buffer.length - cursor);
                cursor = cursor + cnt;
            } while (cnt > 0);
            log.trace("reading complete " + cursor + " byte(s)");
            return buffer;
        } catch (IOException x) {
            log.warn(x.toString(), x);
            throw x;
        }
    }

    public static byte[] readBufferFromStream(InputStream is) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 * 50);
            int cnt = 1;
            byte[] buffer = new byte[1024];
            while (cnt > 0) {
                cnt = is.read(buffer, 0, buffer.length);
                if (cnt > 0) {
                    baos.write(buffer, 0, cnt);
                }
            }
            return baos.toByteArray();
        } catch (IOException x) {
            log.warn(x.toString(), x);
            throw x;
        }
    }

    public static void writeBufferToFile(byte[] buffer, String filename) throws IOException {
        try {
            File file = new File(filename);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(buffer, 0, buffer.length);
            }
        } catch (IOException x) {
            log.warn(x.toString(), x);
            throw x;
        }
    }

    public static byte[] readFileToBuffer(File file) throws IOException {
        byte[] buffer;
        try (FileInputStream fis = new FileInputStream(file)) {
            buffer = new byte[(int) file.length()];
            int cursor = 0;
            int size;
            do {
                size = fis.read(buffer, cursor, buffer.length - cursor);
                cursor += size;

            } while (size > 0);
        }
        return buffer;
    }

    public static byte[] readFileToBuffer(String filename) throws IOException {
        return readFileToBuffer(new File(filename));
    }

    public static String readStringFromStream(InputStream is) throws IOException {
        byte[] buff = readBufferFromStream(is);
        return new String(buff);
    }

    public static void mkdir(String folder) {
        File file = new File(folder);
        if (file.exists()) {
            log.warn("Unable to create folder " + folder + ". Already exist.");
            return;
        }
        log.trace("Create folder " + folder);
        file.mkdir();
    }


    public static byte[] readFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        int c;
        while ((c = is.read()) != -1) {
            boas.write(c);
        }
        return boas.toByteArray();
    }

    public static byte[] readFile(String path) throws IOException {
        File file = new File(path);
        ByteArrayOutputStream boas = new ByteArrayOutputStream((int) file.length());
        FileInputStream fis = new FileInputStream(file);
        int c = fis.read();
        while (c >= 0) {
            boas.write(c);
            c = fis.read();
        }
        fis.close();
        boas.close();
        return boas.toByteArray();
    }

    public static boolean writeBytesToFile(byte[] array, String filename) {
        return writeBytesToFile(array, new File(filename));
    }

    public static boolean writeBytesToFile(byte[] array, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(array);
            fos.close();
            return true;
        } catch (IOException x) {
            return false;
        }
    }

    public static Object loadObject(String filepath) throws IOException, ClassNotFoundException {
        File file = new File(filepath);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    public static void writeObject(String filepath, Object object) throws IOException {
        File file = new File(filepath);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(object);
        oos.close();
    }

    public static void writePNGImage(BufferedImage image, String filename) {
        log.trace("write PNG image to " + filename);
        try {
            ImageIO.write(image, "PNG", new File(filename));
        } catch (IOException e) {
            System.out.println("ERROR: filename=" + filename + "; " + e.getMessage());
        }
    }

    public static BufferedImage readImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            //System.out.println("ERROR: filename=" + filename + "; " + e.getMessage());
            return null;
        }
    }

    public static String intToString(int num, int digits) {
        StringBuffer s = new StringBuffer(digits);
        int zeroes = digits - (int) (Math.log(num) / Math.log(10)) - 1;
        if (num == 0) {
            zeroes = 3;
        }
        for (int i = 0; i < zeroes; i++) {
            s.append(0);
        }
        return s.append(num).toString();
    }

    public static void copyFile(String sourcePath, String targetPath) throws IOException {
        byte[] buffer = new byte[BUFFER_DELTA_SIZE];
        InputStream is = new FileInputStream(new File(sourcePath));
        OutputStream os = new FileOutputStream(new File(targetPath));

        int size = is.read(buffer);
        while (size > 0) {
            os.write(buffer, 0, size);
            size = is.read(buffer);
        }
        os.close();
        is.close();
    }

    public static String readStringFromFile(File file) throws IOException {
        return new String(readBufferFromStream(new FileInputStream(file)), Charset.forName("UTF-8"));
    }

    public static String readStringFromFileSafe(File file) {
        try {
            return readStringFromFile(file);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
        return null;
    }

    public static boolean writeBufferToFile(File file, String content) {
        return writeBytesToFile(content.getBytes(), file);
    }

    public static File[] getFolderFiles(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return new File[]{};
        }
        File[] list = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.equals(".") || name.equals("..")) {
                    return false;
                }
                return true;
            }
        });
        return list;
    }

    public static BufferedImage readImageFromBase64String(String base64str) throws IOException {
        byte[] buffer = Base64.getDecoder().decode(base64str);
        return readImageFromBytes(buffer);
    }

    public static BufferedImage readImageFromBytes(byte[] buffer) throws IOException {
        ByteArrayInputStream baos = new ByteArrayInputStream(buffer);
        return ImageIO.read(baos);
    }

}
