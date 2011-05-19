package org.fusioninventory.categories;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import android.content.Context;


public class Cpus extends Categories {


    /**
     * 
     */
    private static final long serialVersionUID = 4846706700566208666L;

    public Cpus(Context xCtx) {
        super(xCtx);
        // TODO Auto-generated constructor stub

        Category c = new Category(mCtx, "CPUS");

        File f = new File("/proc/cpuinfo");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f),8 * 1024);
            String infos = br.readLine();
            c.put("NAME", infos.replaceAll("(.*):\\ (.*)", "$2"));
            c.put("CORE", "");
            c.put("MANUFACTURER", "");
            c.put("SPEED", "");
            c.put("THREAD", "");
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        f = new File("/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f),8 * 1024);
            String line = br.readLine();
            Integer speed = new Integer(line);
            speed = speed / 1000;
            c.put("SPEED", speed.toString());
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.add(c);
    }
}
