package a.b.e.f;

import android.R;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class ba extends a.b.d.i.g implements View.OnClickListener {
    public final SearchView l;
    public final SearchableInfo m;
    public final Context n;
    public final WeakHashMap<String, Drawable.ConstantState> o;
    public final int p;
    public boolean q;
    public int r;
    public ColorStateList s;
    public int t;
    public int u;
    public int v;
    public int w;
    public int x;
    public int y;

    private static final class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final TextView f312a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final TextView f313b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final ImageView f314c;
        public final ImageView d;
        public final ImageView e;

        public a(View view) {
            this.f312a = (TextView) view.findViewById(R.id.text1);
            this.f313b = (TextView) view.findViewById(R.id.text2);
            this.f314c = (ImageView) view.findViewById(R.id.icon1);
            this.d = (ImageView) view.findViewById(R.id.icon2);
            this.e = (ImageView) view.findViewById(a.b.e.b.f.edit_query);
        }
    }

    public ba(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), null, true);
        this.q = false;
        this.r = 1;
        this.t = -1;
        this.u = -1;
        this.v = -1;
        this.w = -1;
        this.x = -1;
        this.y = -1;
        this.l = searchView;
        this.m = searchableInfo;
        this.p = searchView.getSuggestionCommitIconResId();
        this.n = context;
        this.o = weakHashMap;
    }

    public static String a(Cursor cursor, int i) {
        if (i == -1) {
            return null;
        }
        try {
            return cursor.getString(i);
        } catch (Exception e) {
            Log.e("SuggestionsAdapter", "unexpected error retrieving valid column from cursor, did the remote process die?", e);
            return null;
        }
    }

    public static String a(Cursor cursor, String str) {
        return a(cursor, cursor.getColumnIndex(str));
    }

    public Cursor a(SearchableInfo searchableInfo, String str, int i) {
        String suggestAuthority;
        String[] strArr = null;
        if (searchableInfo == null || (suggestAuthority = searchableInfo.getSuggestAuthority()) == null) {
            return null;
        }
        Uri.Builder builderFragment = new Uri.Builder().scheme("content").authority(suggestAuthority).query("").fragment("");
        String suggestPath = searchableInfo.getSuggestPath();
        if (suggestPath != null) {
            builderFragment.appendEncodedPath(suggestPath);
        }
        builderFragment.appendPath("search_suggest_query");
        String suggestSelection = searchableInfo.getSuggestSelection();
        if (suggestSelection != null) {
            strArr = new String[]{str};
        } else {
            builderFragment.appendPath(str);
        }
        String[] strArr2 = strArr;
        if (i > 0) {
            builderFragment.appendQueryParameter("limit", String.valueOf(i));
        }
        return this.d.getContentResolver().query(builderFragment.build(), null, suggestSelection, strArr2, null);
    }

    public Drawable a(Uri uri) throws FileNotFoundException {
        int identifier;
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            throw new FileNotFoundException("No authority: " + uri);
        }
        try {
            Resources resourcesForApplication = this.d.getPackageManager().getResourcesForApplication(authority);
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments == null) {
                throw new FileNotFoundException("No path: " + uri);
            }
            int size = pathSegments.size();
            if (size == 1) {
                try {
                    identifier = Integer.parseInt(pathSegments.get(0));
                } catch (NumberFormatException unused) {
                    throw new FileNotFoundException("Single path segment is not a resource ID: " + uri);
                }
            } else {
                if (size != 2) {
                    throw new FileNotFoundException("More than two path segments: " + uri);
                }
                identifier = resourcesForApplication.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority);
            }
            if (identifier != 0) {
                return resourcesForApplication.getDrawable(identifier);
            }
            throw new FileNotFoundException("No resource found for: " + uri);
        } catch (PackageManager.NameNotFoundException unused2) {
            throw new FileNotFoundException("No package found for authority: " + uri);
        }
    }

    public void a(int i) {
        this.r = i;
    }

    @Override // a.b.d.i.c
    public void a(View view, Context context, Cursor cursor) throws FileNotFoundException {
        Drawable drawableA;
        String string;
        Drawable drawable;
        CharSequence charSequenceA;
        a aVar = (a) view.getTag();
        int i = this.y;
        int i2 = i != -1 ? cursor.getInt(i) : 0;
        if (aVar.f312a != null) {
            String strA = a(cursor, this.t);
            TextView textView = aVar.f312a;
            textView.setText(strA);
            textView.setVisibility(TextUtils.isEmpty(strA) ? 8 : 0);
        }
        if (aVar.f313b != null) {
            String strA2 = a(cursor, this.v);
            if (strA2 != null) {
                if (this.s == null) {
                    TypedValue typedValue = new TypedValue();
                    this.d.getTheme().resolveAttribute(a.b.e.b.a.textColorSearchUrl, typedValue, true);
                    this.s = this.d.getResources().getColorStateList(typedValue.resourceId);
                }
                SpannableString spannableString = new SpannableString(strA2);
                spannableString.setSpan(new TextAppearanceSpan(null, 0, 0, this.s, null), 0, strA2.length(), 33);
                charSequenceA = spannableString;
            } else {
                charSequenceA = a(cursor, this.u);
            }
            if (TextUtils.isEmpty(charSequenceA)) {
                TextView textView2 = aVar.f312a;
                if (textView2 != null) {
                    textView2.setSingleLine(false);
                    aVar.f312a.setMaxLines(2);
                }
            } else {
                TextView textView3 = aVar.f312a;
                if (textView3 != null) {
                    textView3.setSingleLine(true);
                    aVar.f312a.setMaxLines(1);
                }
            }
            TextView textView4 = aVar.f313b;
            textView4.setText(charSequenceA);
            textView4.setVisibility(TextUtils.isEmpty(charSequenceA) ? 8 : 0);
        }
        ImageView imageView = aVar.f314c;
        if (imageView != null) {
            int i3 = this.w;
            if (i3 == -1) {
                drawableA = null;
            } else {
                drawableA = a(cursor.getString(i3));
                if (drawableA == null) {
                    ComponentName searchActivity = this.m.getSearchActivity();
                    String strFlattenToShortString = searchActivity.flattenToShortString();
                    if (this.o.containsKey(strFlattenToShortString)) {
                        Drawable.ConstantState constantState = this.o.get(strFlattenToShortString);
                        drawable = constantState == null ? null : constantState.newDrawable(this.n.getResources());
                    } else {
                        PackageManager packageManager = this.d.getPackageManager();
                        try {
                            ActivityInfo activityInfo = packageManager.getActivityInfo(searchActivity, 128);
                            int iconResource = activityInfo.getIconResource();
                            if (iconResource != 0) {
                                drawable = packageManager.getDrawable(searchActivity.getPackageName(), iconResource, activityInfo.applicationInfo);
                                if (drawable == null) {
                                    string = "Invalid icon resource " + iconResource + " for " + searchActivity.flattenToShortString();
                                    Log.w("SuggestionsAdapter", string);
                                    drawable = null;
                                }
                            } else {
                                drawable = null;
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            string = e.toString();
                        }
                        this.o.put(strFlattenToShortString, drawable == null ? null : drawable.getConstantState());
                    }
                    drawableA = drawable != null ? drawable : this.d.getPackageManager().getDefaultActivityIcon();
                }
            }
            imageView.setImageDrawable(drawableA);
            if (drawableA == null) {
                imageView.setVisibility(4);
            } else {
                imageView.setVisibility(0);
                drawableA.setVisible(false, false);
                drawableA.setVisible(true, false);
            }
        }
        ImageView imageView2 = aVar.d;
        if (imageView2 != null) {
            int i4 = this.x;
            Drawable drawableA2 = i4 != -1 ? a(cursor.getString(i4)) : null;
            imageView2.setImageDrawable(drawableA2);
            if (drawableA2 == null) {
                imageView2.setVisibility(8);
            } else {
                imageView2.setVisibility(0);
                drawableA2.setVisible(false, false);
                drawableA2.setVisible(true, false);
            }
        }
        int i5 = this.r;
        if (i5 != 2 && (i5 != 1 || (i2 & 1) == 0)) {
            aVar.e.setVisibility(8);
            return;
        }
        aVar.e.setVisibility(0);
        aVar.e.setTag(aVar.f312a.getText());
        aVar.e.setOnClickListener(this);
    }

    @Override // a.b.d.i.c
    public View b(Context context, Cursor cursor, ViewGroup viewGroup) {
        View viewInflate = this.k.inflate(this.i, viewGroup, false);
        viewInflate.setTag(new a(viewInflate));
        ((ImageView) viewInflate.findViewById(a.b.e.b.f.edit_query)).setImageResource(this.p);
        return viewInflate;
    }

    public final void b(Cursor cursor) {
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras == null || extras.getBoolean("in_progress")) {
        }
    }

    @Override // a.b.d.i.c, a.b.d.i.d.a
    public void changeCursor(Cursor cursor) {
        if (this.q) {
            Log.w("SuggestionsAdapter", "Tried to change cursor after adapter was closed.");
            if (cursor != null) {
                cursor.close();
                return;
            }
            return;
        }
        try {
            Cursor cursorA = a(cursor);
            if (cursorA != null) {
                cursorA.close();
            }
            if (cursor != null) {
                this.t = cursor.getColumnIndex("suggest_text_1");
                this.u = cursor.getColumnIndex("suggest_text_2");
                this.v = cursor.getColumnIndex("suggest_text_2_url");
                this.w = cursor.getColumnIndex("suggest_icon_1");
                this.x = cursor.getColumnIndex("suggest_icon_2");
                this.y = cursor.getColumnIndex("suggest_flags");
            }
        } catch (Exception e) {
            Log.e("SuggestionsAdapter", "error changing cursor and caching columns", e);
        }
    }

    @Override // a.b.d.i.c, a.b.d.i.d.a
    public CharSequence convertToString(Cursor cursor) {
        String strA;
        String strA2;
        if (cursor == null) {
            return null;
        }
        String strA3 = a(cursor, cursor.getColumnIndex("suggest_intent_query"));
        if (strA3 != null) {
            return strA3;
        }
        if (this.m.shouldRewriteQueryFromData() && (strA2 = a(cursor, cursor.getColumnIndex("suggest_intent_data"))) != null) {
            return strA2;
        }
        if (!this.m.shouldRewriteQueryFromText() || (strA = a(cursor, cursor.getColumnIndex("suggest_text_1"))) == null) {
            return null;
        }
        return strA;
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        try {
            if (!this.f174a) {
                return null;
            }
            this.f176c.moveToPosition(i);
            if (view == null) {
                view = a(this.d, this.f176c, viewGroup);
            }
            a(view, this.d, this.f176c);
            return view;
        } catch (RuntimeException e) {
            Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", e);
            View viewA = a(this.d, this.f176c, viewGroup);
            if (viewA != null) {
                ((a) viewA.getTag()).f312a.setText(e.toString());
            }
            return viewA;
        }
    }

    @Override // a.b.d.i.c, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            return super.getView(i, view, viewGroup);
        } catch (RuntimeException e) {
            Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", e);
            View viewB = b(this.d, this.f176c, viewGroup);
            if (viewB != null) {
                ((a) viewB.getTag()).f312a.setText(e.toString());
            }
            return viewB;
        }
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        b(getCursor());
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        b(getCursor());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof CharSequence) {
            this.l.a((CharSequence) tag);
        }
    }

    @Override // a.b.d.i.d.a
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        String string = charSequence == null ? "" : charSequence.toString();
        if (this.l.getVisibility() == 0 && this.l.getWindowVisibility() == 0) {
            try {
                Cursor cursorA = a(this.m, string, 50);
                if (cursorA != null) {
                    cursorA.getCount();
                    return cursorA;
                }
            } catch (RuntimeException e) {
                Log.w("SuggestionsAdapter", "Search suggestions query threw an exception.", e);
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:53:0x0131  */
    public final Drawable a(String str) throws FileNotFoundException {
        Drawable drawableA = null;
        if (str != null && !str.isEmpty() && !"0".equals(str)) {
            try {
                int i = Integer.parseInt(str);
                String str2 = "android.resource://" + this.n.getPackageName() + "/" + i;
                Drawable.ConstantState constantState = this.o.get(str2);
                Drawable drawableNewDrawable = constantState == null ? null : constantState.newDrawable();
                if (drawableNewDrawable != null) {
                    return drawableNewDrawable;
                }
                Drawable drawableB = a.b.d.b.a.b(this.n, i);
                if (drawableB != null) {
                    this.o.put(str2, drawableB.getConstantState());
                }
                return drawableB;
            } catch (Resources.NotFoundException unused) {
                Log.w("SuggestionsAdapter", "Icon resource not found: " + str);
                return null;
            } catch (NumberFormatException unused2) {
                Drawable.ConstantState constantState2 = this.o.get(str);
                Drawable drawableNewDrawable2 = constantState2 == null ? null : constantState2.newDrawable();
                if (drawableNewDrawable2 != null) {
                    return drawableNewDrawable2;
                }
                Uri uri = Uri.parse(str);
                try {
                    if ("android.resource".equals(uri.getScheme())) {
                        try {
                            drawableA = a(uri);
                        } catch (Resources.NotFoundException unused3) {
                            throw new FileNotFoundException("Resource does not exist: " + uri);
                        }
                    } else {
                        InputStream inputStreamOpenInputStream = this.n.getContentResolver().openInputStream(uri);
                        if (inputStreamOpenInputStream == null) {
                            throw new FileNotFoundException("Failed to open " + uri);
                        }
                        try {
                            Drawable drawableCreateFromStream = Drawable.createFromStream(inputStreamOpenInputStream, null);
                            try {
                                inputStreamOpenInputStream.close();
                            } catch (IOException e) {
                                Log.e("SuggestionsAdapter", "Error closing icon stream for " + uri, e);
                            }
                            drawableA = drawableCreateFromStream;
                        } catch (Throwable th) {
                            try {
                                inputStreamOpenInputStream.close();
                            } catch (IOException e2) {
                                Log.e("SuggestionsAdapter", "Error closing icon stream for " + uri, e2);
                            }
                            throw th;
                        }
                    }
                } catch (FileNotFoundException e3) {
                    Log.w("SuggestionsAdapter", "Icon not found: " + uri + ", " + e3.getMessage());
                    if (drawableA != null) {
                        this.o.put(str, drawableA.getConstantState());
                    }
                    return drawableA;
                }
                if (drawableA != null) {
                    this.o.put(str, drawableA.getConstantState());
                }
            }
        }
        return drawableA;
    }
}
