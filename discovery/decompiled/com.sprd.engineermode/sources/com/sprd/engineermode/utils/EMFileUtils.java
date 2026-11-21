package com.sprd.engineermode.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class EMFileUtils {
    private static final String TAG = "FileUtils";

    public static void write(String path, String content, boolean append) {
        File file = new File(path);
        write(file, content, append);
    }

    public static void write(File file, String content, boolean append) {
        FileOutputStream fos = null;
        try {
            try {
                try {
                    if (file.exists()) {
                        Log.d(TAG, "File is exists");
                    } else {
                        Log.d(TAG, "file is not exist, creating...");
                        if (!file.createNewFile()) {
                            Log.d(TAG, "creat fail！");
                            if (0 != 0) {
                                try {
                                    fos.close();
                                    return;
                                } catch (Exception e) {
                                    Log.e(TAG, e.toString(), e);
                                    return;
                                }
                            }
                            return;
                        }
                        Log.d(TAG, "creat success！");
                    }
                    FileOutputStream fos2 = new FileOutputStream(file, append);
                    fos2.write(content.getBytes(StandardCharsets.UTF_8));
                    fos2.close();
                } catch (Throwable th) {
                    if (0 != 0) {
                        try {
                            fos.close();
                        } catch (Exception e2) {
                            Log.e(TAG, e2.toString(), e2);
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                Log.e(TAG, e3.toString(), e3);
                if (0 == 0) {
                } else {
                    fos.close();
                }
            }
        } catch (Exception e4) {
            Log.e(TAG, e4.toString(), e4);
        }
    }

    public static String read(String filePath) {
        File file = new File(filePath);
        return read(file);
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0095 A[Catch: Exception -> 0x0091, TryCatch #4 {Exception -> 0x0091, blocks: (B:35:0x008d, B:39:0x0095, B:41:0x009a), top: B:55:0x008d }] */
    /* JADX WARN: Code duplicated, block: B:41:0x009a A[Catch: Exception -> 0x0091, TRY_LEAVE, TryCatch #4 {Exception -> 0x0091, blocks: (B:35:0x008d, B:39:0x0095, B:41:0x009a), top: B:55:0x008d }] */
    public static String read(File file) {
        StringBuilder strBuilder = new StringBuilder();
        if (file.exists()) {
            BufferedReader buffReader = null;
            FileInputStream fis = null;
            InputStreamReader isReader = null;
            try {
                try {
                    String newLine = System.lineSeparator();
                    fis = new FileInputStream(file);
                    isReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
                    buffReader = new BufferedReader(isReader);
                    while (true) {
                        String lineStr = buffReader.readLine();
                        if (lineStr == null) {
                            break;
                        }
                        strBuilder.append(lineStr);
                        strBuilder.append(newLine);
                    }
                    if (strBuilder.length() >= newLine.length()) {
                        strBuilder.setLength(strBuilder.length() - newLine.length());
                    }
                    String string = strBuilder.toString();
                    try {
                        fis.close();
                        isReader.close();
                        buffReader.close();
                    } catch (Exception e) {
                        Log.e(TAG, e.toString(), e);
                    }
                    return string;
                } catch (Throwable th) {
                    if (fis != null) {
                        try {
                            fis.close();
                            if (isReader != null) {
                                isReader.close();
                            }
                            if (buffReader != null) {
                                buffReader.close();
                            }
                        } catch (Exception e2) {
                            Log.e(TAG, e2.toString(), e2);
                            throw th;
                        }
                    } else {
                        if (isReader != null) {
                            isReader.close();
                        }
                        if (buffReader != null) {
                            buffReader.close();
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                Log.e(TAG, e3.toString(), e3);
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (Exception e4) {
                        Log.e(TAG, e4.toString(), e4);
                        return null;
                    }
                }
                if (isReader != null) {
                    isReader.close();
                }
                if (buffReader != null) {
                    buffReader.close();
                    return null;
                }
                return null;
            }
        }
        Log.e(TAG, "File not found: " + file.getPath());
        return null;
    }

    public static void newFolder(String folderPath) {
        File myFilePath = new File(folderPath);
        try {
            if (myFilePath.isDirectory()) {
                Log.d(TAG, "the directory is exists!");
            } else if (myFilePath.mkdirs()) {
                Log.d(TAG, "create directory success");
            } else {
                Log.d(TAG, "create directory fail");
            }
        } catch (Exception e) {
            Log.d(TAG, "create directory fail");
            Log.e(TAG, e.toString(), e);
        }
    }

    public static boolean deleteFolder(String folder) {
        return deleteFolder(new File(folder));
    }

    public static boolean deleteFolder(File folder) {
        try {
            if (folder.isDirectory()) {
                File[] childs = folder.listFiles();
                if (childs != null && childs.length > 0) {
                    boolean result = false;
                    for (File child : childs) {
                        try {
                            if (child.isFile()) {
                                result = child.delete();
                                if (!result) {
                                    return false;
                                }
                            } else if (child.isDirectory() && !(result = deleteFolder(child))) {
                                return false;
                            }
                        } catch (Exception e) {
                            e = e;
                            Log.e(TAG, e.toString(), e);
                            return false;
                        }
                    }
                }
                boolean result2 = folder.delete();
                return result2;
            }
            Log.d(TAG, "it is not a directory: " + folder);
            return false;
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static boolean isFileDirExits(String folderPath) {
        File myFilePath = new File(folderPath);
        return myFilePath.isDirectory();
    }

    public static ArrayList<String> getDirectoryName(String path) {
        File[] files;
        File f = new File(path);
        if (!f.exists() || !f.isDirectory() || (files = f.listFiles()) == null) {
            return null;
        }
        ArrayList<String> dirList = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                dirList.add(file.getName());
            }
        }
        return dirList;
    }
}
