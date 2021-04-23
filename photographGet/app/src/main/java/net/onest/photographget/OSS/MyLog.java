package net.onest.photographget.OSS;

import android.util.Log;

public class MyLog {

    private static boolean VERBOSE_STATE = true;
    private static boolean INFO_STATE = true;
    private static boolean WARN_STATE = true;
    private static boolean ERROR_STATE = true;
    private static boolean DATA = true;

    private static String tagd = "lj_data";
    private static String tagi = "lj_info";
    private static String tage = "lj_error";
    private static String tagw = "lj_wanner";
    private static String tagv = "lj_verbose";

    public static void d(String msg){
        if(DATA){
            Log.d( tagd,msg );
        }
    }
    public static void d(String msg, Throwable thr){
        if(DATA){
            Log.d( tagd,msg,thr );
        }
    }

    public static void i( String msg){
        if(INFO_STATE){
            Log.i( tagi,msg );
        }
    }
    public static void i( String msg, Throwable thr){
        if(INFO_STATE){
            Log.i( tagi,msg,thr );
        }
    }

    public static void v( String msg) {
        if (VERBOSE_STATE) {
            Log.v(tagv, msg);
        }
    }
    public static void v( String msg, Throwable thr) {
        if (VERBOSE_STATE)
            Log.v(tagv, msg, thr);


    }
    public static void w( String msg) {
        if (WARN_STATE)
            Log.w(tagw, msg);
    }


    public static void w( String msg, Throwable thr) {
        if (WARN_STATE)
            Log.w(tagw, msg);
    }



    public static void e( String msg) {
        if (ERROR_STATE)
            Log.e(tage, msg);
    }


    public static void e(String msg, Throwable thr) {
        if (ERROR_STATE)
            Log.e(tage, msg, thr);
    }

}
