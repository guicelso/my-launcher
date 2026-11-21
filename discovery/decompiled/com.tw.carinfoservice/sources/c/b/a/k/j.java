package c.b.a.k;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Context f441a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Context f442b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Resources f443c;
    public PackageInfo d;

    public j(i iVar, Context context, File file) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        this.f441a = context;
        file.getAbsolutePath();
        this.f442b = new d(this);
        Resources resources = context.getResources();
        AssetManager assetManager = (AssetManager) AssetManager.class.newInstance();
        Object[] objArr = {file.getAbsolutePath()};
        Class[] clsArr = new Class[1];
        for (int i = 0; i < 1; i++) {
            clsArr[i] = objArr[i].getClass();
        }
        Method declaredMethod = AssetManager.class.getDeclaredMethod("addAssetPath", clsArr);
        declaredMethod.setAccessible(true);
        declaredMethod.invoke(assetManager, objArr);
        this.f443c = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
        PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), 64);
        this.d = packageArchiveInfo;
        if (packageArchiveInfo == null) {
            this.d = context.getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), 1);
        }
    }
}
