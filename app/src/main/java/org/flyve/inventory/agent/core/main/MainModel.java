/*
 * Copyright Teclib. All rights reserved.
 *
 * Flyve MDM is a mobile device management software.
 *
 * Flyve MDM is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * Flyve MDM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @author    Rafael Hernandez
 * @copyright Copyright Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/android-mdm-agent
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */

package org.flyve.inventory.agent.core.main;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;

import org.flyve.inventory.agent.R;
import org.flyve.inventory.agent.adapter.DrawerAdapter;
import org.flyve.inventory.agent.ui.FragmentAbout;
import org.flyve.inventory.agent.ui.FragmentHelp;
import org.flyve.inventory.agent.ui.FragmentHome;
import org.flyve.inventory.agent.utils.FlyveLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainModel implements Main.Model {

    private Main.Presenter presenter;
    private ArrayList<HashMap<String, String>> arrDrawer;

    public MainModel(Main.Presenter presenter) {
        this.presenter = presenter;
    }

    public void requestPermission(Activity activity) {
        boolean isGranted = true;
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if(result != PackageManager.PERMISSION_GRANTED) {
            isGranted = false;
        }

        result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result != PackageManager.PERMISSION_GRANTED) {
            isGranted = false;
        }

        if(!isGranted) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                    },
                    1);
        }
    }

    public void loadFragment(FragmentManager fragmentManager, android.support.v7.widget.Toolbar toolbar, Map<String, String> menuItem) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        toolbar.setTitle(menuItem.get("name"));

        // Home
        if (menuItem.get("id").equals("1")) {
            FragmentHome f = new FragmentHome();
            fragmentTransaction.replace(R.id.containerView, f).commit();
            return;
        }

        // Help
        if (menuItem.get("id").equals("4")) {
            FragmentHelp f = new FragmentHelp();
            fragmentTransaction.replace(R.id.containerView, f).commit();
            return;
        }

        // About
        if (menuItem.get("id").equals("5")) {
            FragmentAbout f = new FragmentAbout();
            fragmentTransaction.replace(R.id.containerView, f).commit();
            return;
        }
    }

    public List<HashMap<String, String>> getMenuItem() {
        return arrDrawer;
    }

    public Map<String, String> setupDrawer(Activity activity, ListView lst) {
        arrDrawer = new ArrayList<>();

        // Information
        HashMap<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("name", activity.getResources().getString(R.string.drawer_inventory));
        map.put("img", "ic_info");
        arrDrawer.add(map);

        // Help
        map = new HashMap<>();
        map.put("id", "4");
        map.put("name", activity.getResources().getString(R.string.drawer_help));
        map.put("img", "ic_help");
        arrDrawer.add(map);

        // About
        map = new HashMap<>();
        map.put("id", "5");
        map.put("name", activity.getResources().getString(R.string.drawer_about));
        map.put("img", "ic_about");
        arrDrawer.add(map);

        try {
            // load adapter
            DrawerAdapter adapter = new DrawerAdapter(activity, arrDrawer);
            lst.setAdapter(adapter);

            // Select Information on load //
            return arrDrawer.get(0);
        } catch(Exception ex) {
            FlyveLog.e(ex.getMessage());
        }

        return null;
    }
}
