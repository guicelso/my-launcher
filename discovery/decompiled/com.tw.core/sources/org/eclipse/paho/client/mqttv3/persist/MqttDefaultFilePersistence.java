package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.internal.FileLock;
import org.eclipse.paho.client.mqttv3.internal.MqttPersistentData;

/* JADX INFO: loaded from: classes.dex */
public class MqttDefaultFilePersistence implements MqttClientPersistence {
    private static FilenameFilter FILENAME_FILTER = null;
    private static final String LOCK_FILENAME = ".lck";
    private static final String MESSAGE_BACKUP_FILE_EXTENSION = ".bup";
    private static final String MESSAGE_FILE_EXTENSION = ".msg";
    private File clientDir;
    private File dataDir;
    private FileLock fileLock;

    public MqttDefaultFilePersistence() {
        this(System.getProperty("user.dir"));
    }

    public MqttDefaultFilePersistence(String str) {
        this.clientDir = null;
        this.fileLock = null;
        this.dataDir = new File(str);
    }

    private void checkIsOpen() throws MqttPersistenceException {
        if (this.clientDir == null) {
            throw new MqttPersistenceException();
        }
    }

    private static FilenameFilter getFilenameFilter() {
        if (FILENAME_FILTER == null) {
            FILENAME_FILTER = new PersistanceFileNameFilter(MESSAGE_FILE_EXTENSION);
        }
        return FILENAME_FILTER;
    }

    private File[] getFiles() throws MqttPersistenceException {
        checkIsOpen();
        File[] fileArrListFiles = this.clientDir.listFiles(getFilenameFilter());
        if (fileArrListFiles != null) {
            return fileArrListFiles;
        }
        throw new MqttPersistenceException();
    }

    private boolean isSafeChar(char c) {
        return Character.isJavaIdentifierPart(c) || c == '-';
    }

    private void restoreBackups(File file) throws MqttPersistenceException {
        File[] fileArrListFiles = file.listFiles(new PersistanceFileFilter(MESSAGE_BACKUP_FILE_EXTENSION));
        if (fileArrListFiles == null) {
            throw new MqttPersistenceException();
        }
        for (int i = 0; i < fileArrListFiles.length; i++) {
            File file2 = new File(file, fileArrListFiles[i].getName().substring(0, fileArrListFiles[i].getName().length() - MESSAGE_BACKUP_FILE_EXTENSION.length()));
            if (!fileArrListFiles[i].renameTo(file2)) {
                file2.delete();
                fileArrListFiles[i].renameTo(file2);
            }
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void clear() throws MqttPersistenceException {
        checkIsOpen();
        for (File file : getFiles()) {
            file.delete();
        }
        this.clientDir.delete();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void close() {
        synchronized (this) {
            if (this.fileLock != null) {
                this.fileLock.release();
            }
            if (getFiles().length == 0) {
                this.clientDir.delete();
            }
            this.clientDir = null;
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public boolean containsKey(String str) throws MqttPersistenceException {
        checkIsOpen();
        return new File(this.clientDir, String.valueOf(str) + MESSAGE_FILE_EXTENSION).exists();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public MqttPersistable get(String str) throws MqttPersistenceException {
        checkIsOpen();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(this.clientDir, String.valueOf(str) + MESSAGE_FILE_EXTENSION));
            int iAvailable = fileInputStream.available();
            byte[] bArr = new byte[iAvailable];
            for (int i = 0; i < iAvailable; i += fileInputStream.read(bArr, i, iAvailable - i)) {
            }
            fileInputStream.close();
            return new MqttPersistentData(str, bArr, 0, bArr.length, null, 0, 0);
        } catch (IOException e) {
            throw new MqttPersistenceException(e);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public Enumeration keys() throws MqttPersistenceException {
        checkIsOpen();
        File[] files = getFiles();
        Vector vector = new Vector(files.length);
        for (File file : files) {
            String name = file.getName();
            vector.addElement(name.substring(0, name.length() - MESSAGE_FILE_EXTENSION.length()));
        }
        return vector.elements();
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void open(String str, String str2) throws MqttPersistenceException {
        if (this.dataDir.exists() && !this.dataDir.isDirectory()) {
            throw new MqttPersistenceException();
        }
        if (!this.dataDir.exists() && !this.dataDir.mkdirs()) {
            throw new MqttPersistenceException();
        }
        if (!this.dataDir.canWrite()) {
            throw new MqttPersistenceException();
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (isSafeChar(cCharAt)) {
                stringBuffer.append(cCharAt);
            }
        }
        stringBuffer.append("-");
        for (int i2 = 0; i2 < str2.length(); i2++) {
            char cCharAt2 = str2.charAt(i2);
            if (isSafeChar(cCharAt2)) {
                stringBuffer.append(cCharAt2);
            }
        }
        synchronized (this) {
            if (this.clientDir == null) {
                this.clientDir = new File(this.dataDir, stringBuffer.toString());
                if (!this.clientDir.exists()) {
                    this.clientDir.mkdir();
                }
            }
            try {
                this.fileLock = new FileLock(this.clientDir, LOCK_FILENAME);
            } catch (Exception e) {
            }
            restoreBackups(this.clientDir);
        }
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x009b */
    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void put(java.lang.String r7, org.eclipse.paho.client.mqttv3.MqttPersistable r8) throws org.eclipse.paho.client.mqttv3.MqttPersistenceException {
        /*
            r6 = this;
            r6.checkIsOpen()
            java.io.File r1 = new java.io.File
            java.io.File r0 = r6.clientDir
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = java.lang.String.valueOf(r7)
            r2.<init>(r3)
            java.lang.String r3 = ".msg"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r0, r2)
            java.io.File r2 = new java.io.File
            java.io.File r0 = r6.clientDir
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = java.lang.String.valueOf(r7)
            r3.<init>(r4)
            java.lang.String r4 = ".msg"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = ".bup"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r0, r3)
            boolean r0 = r1.exists()
            if (r0 != 0) goto L77
        L46:
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            r0.<init>(r1)     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            byte[] r3 = r8.getHeaderBytes()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            int r4 = r8.getHeaderOffset()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            int r5 = r8.getHeaderLength()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            r0.write(r3, r4, r5)     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            byte[] r3 = r8.getPayloadBytes()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            if (r3 != 0) goto L84
        L60:
            java.io.FileDescriptor r3 = r0.getFD()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            r3.sync()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            r0.close()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            boolean r0 = r2.exists()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            if (r0 != 0) goto La3
        L70:
            boolean r0 = r2.exists()
            if (r0 != 0) goto Lb4
        L76:
            return
        L77:
            boolean r0 = r1.renameTo(r2)
            if (r0 != 0) goto L46
            r2.delete()
            r1.renameTo(r2)
            goto L46
        L84:
            byte[] r3 = r8.getPayloadBytes()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            int r4 = r8.getPayloadOffset()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            int r5 = r8.getPayloadLength()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            r0.write(r3, r4, r5)     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            goto L60
        L94:
            r0 = move-exception
            org.eclipse.paho.client.mqttv3.MqttPersistenceException r3 = new org.eclipse.paho.client.mqttv3.MqttPersistenceException     // Catch: java.lang.Throwable -> L9b
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L9b
            throw r3     // Catch: java.lang.Throwable -> L9b
        L9b:
            r0 = move-exception
            boolean r3 = r2.exists()
            if (r3 != 0) goto La7
        La2:
            throw r0
        La3:
            r2.delete()     // Catch: java.io.IOException -> L94 java.lang.Throwable -> L9b
            goto L70
        La7:
            boolean r3 = r2.renameTo(r1)
            if (r3 != 0) goto La2
            r1.delete()
            r2.renameTo(r1)
            goto La2
        Lb4:
            boolean r0 = r2.renameTo(r1)
            if (r0 != 0) goto L76
            r1.delete()
            r2.renameTo(r1)
            goto L76
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence.put(java.lang.String, org.eclipse.paho.client.mqttv3.MqttPersistable):void");
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttClientPersistence
    public void remove(String str) throws MqttPersistenceException {
        checkIsOpen();
        File file = new File(this.clientDir, String.valueOf(str) + MESSAGE_FILE_EXTENSION);
        if (file.exists()) {
            file.delete();
        }
    }
}
