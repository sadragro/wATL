package su.whs.watl.samples;

import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import su.whs.watl.text.ContentView;
import su.whs.watl.ui.ITextView;

/**
 * Created by igor n. boulliev on 17.06.15.
 */
public class TextOptionsHandler {
    private ITextView textView;
    private Context ctx;

    public TextOptionsHandler(Context context, ITextView tv) {
        ctx = context;
        textView = tv;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        ContentView.Options opts = textView.getOptions();
        int id = item.getItemId();
        if (id == R.id.bigfont) {
            setFontSize(android.R.style.TextAppearance_Large,item);
        } else if (id == R.id.medfont) {
            setFontSize(android.R.style.TextAppearance_Medium,item);
        } else if (id == R.id.smallfont) {
            setFontSize(android.R.style.TextAppearance_Small,item);
        } else if (id == R.id.filter_empty) {
            if (item.isChecked()) {
                item.setChecked(false);
                opts.setFilterEmptyLines(false)
                        .apply();
            } else {
                item.setChecked(true);
                opts.setFilterEmptyLines(true)
                        .apply();
            }
        } else if (id == R.id.additionals_paragraphs_margins) {
            if (item.isChecked()) {
                item.setChecked(false);
                opts
                        .setNewLineTopMargin(0)
                        .setNewLineLeftMargin(0)
                        .apply();
            } else {
                item.setChecked(true);
                opts
                        .setNewLineTopMargin(5)
                        .setNewLineLeftMargin(15)
                        .apply();
            }
        } else {
            return false;
        }
        return true;
    }

    MenuItem mi_textBig;
    MenuItem mi_textMed;
    MenuItem mi_textSmall;

    public void restoreState(Menu menu) {
        MenuItem item;
        ContentView.Options opts = textView.getOptions();
        item = menu.findItem(R.id.filter_empty);
        if (item!=null) {
            item.setChecked(opts.isFilterEmptyLines());
        }
        item = menu.findItem(R.id.additionals_paragraphs_margins);
        if (item!=null) {
            item.setChecked(opts.getNewLineLeftMargin()>0);
        }

        mi_textBig = menu.findItem(R.id.bigfont);
        mi_textSmall = menu.findItem(R.id.smallfont);
        mi_textMed = menu.findItem(R.id.medfont);
    }

    private void setFontSize(int appearance, MenuItem item) {
        Button b =new Button(ctx);
        b.setTextAppearance(ctx,appearance);
        float size = b.getTextSize();
        ContentView.Options opts = textView.getOptions();
        opts
                .setTextSize(size)
                .apply();
        uncheckAll();
        item.setChecked(true);
    }

    private void uncheckAll() {
        if (mi_textBig==null||mi_textMed==null||mi_textSmall==null) {
            Log.e("TextOptionsHandler", "missing call restoreState(menu) - check onCreateOptionsMenu()");
            return;
        }
        mi_textBig.setChecked(false);
        mi_textMed.setChecked(false);
        mi_textSmall.setChecked(false);
    }
}
