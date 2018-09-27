package blablablog.utils.io;

import blablablog.utils.LazyGson;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.StringUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * Инструментарий для ввода вывода.
 *
 * @author max
 */
public class IOUtils {

    private static final String NEWLINE = "\n";
    private static final String[] BYTE2HEX = new String[256];
    private static final String[] HEXPADDING = new String[16];
    private static final String[] BYTEPADDING = new String[16];
    private static final char[] BYTE2CHAR = new char[256];

    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    static {
        int i;

        // Generate the lookup table for byte-to-hex-dump conversion
        for (i = 0; i < BYTE2HEX.length; i++) {
            BYTE2HEX[i] = ' ' + StringUtil.byteToHexStringPadded(i);
        }

        // Generate the lookup table for hex dump paddings
        for (i = 0; i < HEXPADDING.length; i++) {
            int padding = HEXPADDING.length - i;
            StringBuilder buf = new StringBuilder(padding * 3);
            for (int j = 0; j < padding; j++) {
                buf.append("   ");
            }
            HEXPADDING[i] = buf.toString();
        }

        // Generate the lookup table for byte dump paddings
        for (i = 0; i < BYTEPADDING.length; i++) {
            int padding = BYTEPADDING.length - i;
            StringBuilder buf = new StringBuilder(padding);
            for (int j = 0; j < padding; j++) {
                buf.append(' ');
            }
            BYTEPADDING[i] = buf.toString();
        }

        // Generate the lookup table for byte-to-char conversion
        for (i = 0; i < BYTE2CHAR.length; i++) {
            if (i <= 0x1f || i >= 0x7f) {
                BYTE2CHAR[i] = '.';
            } else {
                BYTE2CHAR[i] = (char) i;
            }
        }
    }

    public static String toHexString(byte[] bytes) {
        final char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_CHARS[v >>> 4];
            hexChars[j * 2 + 1] = HEX_CHARS[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] readFromStream(InputStream is) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int c;
        while ((c = is.read()) != -1) {
            baos.write(c);
        }
        baos.close();
        return baos.toByteArray();
    }


    public static byte[] readFromStream(InputStream is, int length) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(length);
        int c;
        while ((c = is.read()) != -1) {
            baos.write(c);
        }
        baos.close();
        return baos.toByteArray();
    }


    public static String readStringFromStream(InputStream is) throws IOException {
        return new String(readFromStream(is), "UTF-8");
    }

    public static byte[] readFile(File file) throws IOException {
        byte[] result = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(result);
        fis.close();
        return result;
    }

    public static byte[] readFile2(String path) throws IOException {
        return readFile(new File(path));
    }

    /**
     * Read bean from JSON text file.
     *
     * @param filepath Path top file
     * @param clazz    Class of bean
     * @return Instance of bean or null
     * @throws IOException If any error
     */
    public static <T> T readBeanFromJsonFile(String filepath, Class<T> clazz) throws IOException {
        String jsonContent = readFileToString(filepath);
        return LazyGson.fromJson(jsonContent, clazz);
    }

    /**
     * Write bean to plain text json file.
     *
     * @param filepath Target filename
     * @param object   Bean instance to save
     * @throws IOException If any error
     */
    public static void writeBeanToJsonFile(String filepath, Object object) throws IOException {
        String jsonContent = LazyGson.objectToGson(object);
        if (jsonContent == null) {
            throw new IOException("JSON content is empty.");
        }
        writeBytesToFile(jsonContent.getBytes(), filepath);
    }

    /*
        Dublicated method
     */
    @Deprecated
    public static byte[] readFile2(File file) throws IOException {
        return readFile(file);
    }

    public static String readFileToString(String path) throws IOException {
        File file = new File(path);
        byte[] result = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(result);
        fis.close();
        return new String(result, "UTF-8");
    }

    public static String readFileToString(File file) throws IOException {
        byte[] result = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(result);
        fis.close();
        return new String(result, "UTF-8");
    }

    public static boolean writeBytesToFile(byte[] array, String filename) throws IOException {
        return writeBytesToFile(array, new File(filename));
    }

    public static boolean writeBytesToFile(byte[] array, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(array);
        fos.close();
        return true;
    }

    public static void printDumpBytes(String eventName, byte[] array) {
        System.out.println(formatByteBuf(eventName, array));
    }

    public static void printDumpBytes(byte[] array, int startIdx, int length) {
        System.out.println(dumpByteArray(array, startIdx, length));
    }

    public static String dumpByteArray(byte[] array) {
        return dumpByteArray(array, 0, array.length);
    }

    public static String dumpByteArray(byte[] array, int startIdx, int length) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (int i = 0; i < length; i++) {
            byte value = array[i + startIdx];
            if (value >= 0 && value < 10) {
                sb.append("00");
            }
            if (value >= 10 && value < 100) {
                sb.append("0");
            }

            sb.append(Byte.toString(value).toUpperCase());
            sb.append(' ');

            if (index == 40) {
                sb.append("\n");
                index = 0;
            }
            index++;
        }
        sb.append("\n");
        return sb.toString();
    }

    public static String formatByteBuf(String eventName, byte[] array_buf) {
        ByteBuf buf = Unpooled.buffer(array_buf.length);
        buf.writeBytes(array_buf);
        return formatByteBuf(eventName, buf);
    }

    public static String formatByteBuf(String eventName, ByteBuf buf) {
        int length = buf.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder dump = new StringBuilder(rows * 80 + eventName.length()
                + 16);

        dump.append(eventName).append('(').append(length).append('B').append(
                ')');
        dump
                .append(NEWLINE)
                .append(
                        "         +-------------------------------------------------+")
                .append(NEWLINE)
                .append(
                        "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |")
                .append(NEWLINE)
                .append(
                        "+--------+-------------------------------------------------+----------------+");

        final int startIndex = buf.readerIndex();
        final int endIndex = buf.writerIndex();

        int i;
        for (i = startIndex; i < endIndex; i++) {
            int relIdx = i - startIndex;
            int relIdxMod16 = relIdx & 15;
            if (relIdxMod16 == 0) {
                dump.append(NEWLINE);
                dump.append(Long
                        .toHexString(relIdx & 0xFFFFFFFFL | 0x100000000L));
                dump.setCharAt(dump.length() - 9, '|');
                dump.append('|');
            }
            dump.append(BYTE2HEX[buf.getUnsignedByte(i)]);
            if (relIdxMod16 == 15) {
                dump.append(" |");
                for (int j = i - 15; j <= i; j++) {
                    dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
                }
                dump.append('|');
            }
        }

        if ((i - startIndex & 15) != 0) {
            int remainder = length & 15;
            dump.append(HEXPADDING[remainder]);
            dump.append(" |");
            for (int j = i - remainder; j < i; j++) {
                dump.append(BYTE2CHAR[buf.getUnsignedByte(j)]);
            }
            dump.append(BYTEPADDING[remainder]);
            dump.append('|');
        }

        dump
                .append(NEWLINE)
                .append(
                        "+--------+-------------------------------------------------+----------------+");

        return dump.toString();
    }

    public static boolean isFileExist(String filepath) {
        File file = new File(filepath);
        return file.exists() && file.isFile();
    }

    public static boolean isFolderExist(String filepath) {
        File file = new File(filepath);
        return file.exists() && file.isDirectory();
    }

    private static Gson gson = new Gson();

    public static <T> T loadJson(String filename, Class<T> classOfT) throws IOException {
        return gson.fromJson(IOUtils.readFileToString(filename), classOfT);
    }

    public static byte[] readFromResources(final String path) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null) {
            classLoader = Class.class.getClassLoader();
        }
        try (InputStream input = classLoader.getResourceAsStream(path)) {
            return readFromStream(input);
        }
    }

    public static File getFileFromResources(final String path) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null) {
            classLoader = Class.class.getClassLoader();
        }
        URL resource = classLoader.getResource(path);
        if (resource != null) {
            return new File(resource.getFile());
        }
        return null;
    }

    public static boolean deleteFileSafe(String filepath) {
        try {
            File file = new File(filepath);
            if (!file.exists()) {
                return false;
            }
            return file.delete();
        } catch (Exception e) {
            return false;
        }
    }

    public static void redirectInputToOutputStream(final InputStream is, final OutputStream os) throws IOException {
        final int chinkSize = 1024;
        final byte[] buffer = new byte[chinkSize];

        while (is.available() >= chinkSize) {
            is.read(buffer);
            os.write(buffer);
            System.out.println(is.available());
        }
        if (is.available() > 0) {
            final byte[] lastBuffer = new byte[is.available()];
            is.read(lastBuffer);
            os.write(lastBuffer);
        }
    }

    /**
     * Create new empty file
     *
     * @param path path to file
     * @return result of file creating
     */
    public static boolean createEmptyFile(String path) throws IOException {
        File file = new File(path);
        return file.createNewFile();
    }


    public static BufferedImage readImageFromBytes(byte[] imageContent) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(imageContent));
    }


}
