package infilms.asee.giiis.unex.es.thenoworder.ui.settings;


import android.os.Bundle;
import android.preference.PreferenceFragment;


import infilms.asee.giiis.unex.es.thenoworder.R;

public class SettingsFragment extends PreferenceFragment {

    public static final String KEY_PREF_MESA = "pref_tables";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);


    }



}
