package a.b.d.i;

import a.b.e.f.b0;
import android.database.Cursor;
import android.util.Log;
import android.widget.Filter;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class d extends Filter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public a f169a;

    public interface a {
    }

    public d(a aVar) {
        this.f169a = aVar;
    }

    @Override // android.widget.Filter
    public CharSequence convertResultToString(Object obj) {
        return ((b0) this.f169a).c((Cursor) obj);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0038  */
    @Override // android.widget.Filter
    public Filter.FilterResults performFiltering(CharSequence charSequence) {
        Cursor cursorH;
        b0 b0Var = (b0) this.f169a;
        Objects.requireNonNull(b0Var);
        String string = charSequence == null ? "" : charSequence.toString();
        if (b0Var.m.getVisibility() == 0 && b0Var.m.getWindowVisibility() == 0) {
            try {
                cursorH = b0Var.h(b0Var.n, string, 50);
                if (cursorH != null) {
                    cursorH.getCount();
                } else {
                    cursorH = null;
                }
            } catch (RuntimeException e) {
                Log.w("SuggestionsAdapter", "Search suggestions query threw an exception.", e);
            }
        } else {
            cursorH = null;
        }
        Filter.FilterResults filterResults = new Filter.FilterResults();
        if (cursorH != null) {
            filterResults.count = cursorH.getCount();
            filterResults.values = cursorH;
        } else {
            filterResults.count = 0;
            filterResults.values = null;
        }
        return filterResults;
    }

    @Override // android.widget.Filter
    public void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
        a aVar = this.f169a;
        Cursor cursor = ((c) aVar).d;
        Object obj = filterResults.values;
        if (obj == null || obj == cursor) {
            return;
        }
        ((b0) aVar).b((Cursor) obj);
    }
}
