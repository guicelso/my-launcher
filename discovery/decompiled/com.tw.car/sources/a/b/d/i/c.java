package a.b.d.i;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

/* JADX INFO: loaded from: classes.dex */
public abstract class c extends BaseAdapter implements Filterable, d.a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f174a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f175b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Cursor f176c;
    public Context d;
    public int e;
    public a f;
    public DataSetObserver g;
    public d h;

    private class a extends ContentObserver {
        public a() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            c.this.a();
        }
    }

    private class b extends DataSetObserver {
        public b() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            c cVar = c.this;
            cVar.f174a = true;
            cVar.notifyDataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            c cVar = c.this;
            cVar.f174a = false;
            cVar.notifyDataSetInvalidated();
        }
    }

    public c(Context context, Cursor cursor, boolean z) {
        a(context, cursor, z ? 1 : 2);
    }

    public Cursor a(Cursor cursor) {
        Cursor cursor2 = this.f176c;
        if (cursor == cursor2) {
            return null;
        }
        if (cursor2 != null) {
            a aVar = this.f;
            if (aVar != null) {
                cursor2.unregisterContentObserver(aVar);
            }
            DataSetObserver dataSetObserver = this.g;
            if (dataSetObserver != null) {
                cursor2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.f176c = cursor;
        if (cursor != null) {
            a aVar2 = this.f;
            if (aVar2 != null) {
                cursor.registerContentObserver(aVar2);
            }
            DataSetObserver dataSetObserver2 = this.g;
            if (dataSetObserver2 != null) {
                cursor.registerDataSetObserver(dataSetObserver2);
            }
            this.e = cursor.getColumnIndexOrThrow("_id");
            this.f174a = true;
            notifyDataSetChanged();
        } else {
            this.e = -1;
            this.f174a = false;
            notifyDataSetInvalidated();
        }
        return cursor2;
    }

    public abstract View a(Context context, Cursor cursor, ViewGroup viewGroup);

    public void a() {
        Cursor cursor;
        if (!this.f175b || (cursor = this.f176c) == null || cursor.isClosed()) {
            return;
        }
        this.f174a = this.f176c.requery();
    }

    public void a(Context context, Cursor cursor, int i) {
        b bVar;
        if ((i & 1) == 1) {
            i |= 2;
            this.f175b = true;
        } else {
            this.f175b = false;
        }
        boolean z = cursor != null;
        this.f176c = cursor;
        this.f174a = z;
        this.d = context;
        this.e = z ? cursor.getColumnIndexOrThrow("_id") : -1;
        if ((i & 2) == 2) {
            this.f = new a();
            bVar = new b();
        } else {
            bVar = null;
            this.f = null;
        }
        this.g = bVar;
        if (z) {
            a aVar = this.f;
            if (aVar != null) {
                cursor.registerContentObserver(aVar);
            }
            DataSetObserver dataSetObserver = this.g;
            if (dataSetObserver != null) {
                cursor.registerDataSetObserver(dataSetObserver);
            }
        }
    }

    public abstract void a(View view, Context context, Cursor cursor);

    public abstract View b(Context context, Cursor cursor, ViewGroup viewGroup);

    @Override // a.b.d.i.d.a
    public abstract void changeCursor(Cursor cursor);

    @Override // a.b.d.i.d.a
    public abstract CharSequence convertToString(Cursor cursor);

    @Override // android.widget.Adapter
    public int getCount() {
        Cursor cursor;
        if (!this.f174a || (cursor = this.f176c) == null) {
            return 0;
        }
        return cursor.getCount();
    }

    @Override // a.b.d.i.d.a
    public Cursor getCursor() {
        return this.f176c;
    }

    @Override // android.widget.Filterable
    public Filter getFilter() {
        if (this.h == null) {
            this.h = new d(this);
        }
        return this.h;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        Cursor cursor;
        if (!this.f174a || (cursor = this.f176c) == null) {
            return null;
        }
        cursor.moveToPosition(i);
        return this.f176c;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        Cursor cursor;
        if (this.f174a && (cursor = this.f176c) != null && cursor.moveToPosition(i)) {
            return this.f176c.getLong(this.e);
        }
        return 0L;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (!this.f174a) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (this.f176c.moveToPosition(i)) {
            if (view == null) {
                view = b(this.d, this.f176c, viewGroup);
            }
            a(view, this.d, this.f176c);
            return view;
        }
        throw new IllegalStateException("couldn't move cursor to position " + i);
    }
}
