package com.kaano8.androidcore.settings

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceFragmentCompat
import com.kaano8.androidcore.R

class SettingsFragment : PreferenceFragmentCompat() {

    private val args: SettingsFragmentArgs by navArgs()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        Toast.makeText(context, args.setting, Toast.LENGTH_SHORT).show()
    }
}