package com.adel.ehcache.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Adel.Albediwy
 */
public class GZipUtilz {

    public static <T> byte[] compress(final T obj) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
        final ObjectOutputStream objectOut = new ObjectOutputStream(gzipOut);
        objectOut.writeObject(obj);
        objectOut.close();
        return baos.toByteArray();
    }

    public static <T> T decompress(final byte[] compressedData) throws IOException, ClassNotFoundException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
        final GZIPInputStream gzipIn = new GZIPInputStream(bais);
        final ObjectInputStream objectIn = new ObjectInputStream(gzipIn);
        final T obj = (T) objectIn.readObject();
        objectIn.close();
        return obj;
    }

}
