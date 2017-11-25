package facesnap.emazdoor.com.facesnap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Ahmed on 11/23/2017.
 */

public class Settings extends ListActivity {
    private String funcs[] = {"Preferences", "About"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(Settings.this, android.R.layout.simple_expandable_list_item_1, funcs));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (funcs[position]) {
            case "Preferences":
                startActivity(new Intent(this, Preferences.class));
                break;
            case "About":
                break;
            default:
                break;
        }
    }
}
