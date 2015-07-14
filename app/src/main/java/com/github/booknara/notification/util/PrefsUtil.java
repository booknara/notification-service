package com.github.booknara.notification.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.github.booknara.notification.BuildConfig;


/**
 * Utility of Preferences
 *
 * @author : Daehee Han(@daniel_booknara)
 * @version : 1.1.0
 *
 */
public class PrefsUtil {
    private PrefsUtil() { }

    /**
     * Get SharedPreferences instance
     *
     * @param ctx
     * @return
     */
    private static SharedPreferences getSharedPreference(Context ctx) {
        return ctx.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    /**
     * Add KEY and value
     *
     * @param ctx
     * @param key
     * @param value
     * @return
     */
    public static boolean addBoolean(Context ctx, String key, boolean value) {
        SharedPreferences prefs = getSharedPreference(ctx);

        return prefs != null && prefs.edit().putBoolean(key, value).commit();
    }

    public static boolean addInteger(Context ctx, String key, int value) {
        SharedPreferences prefs = getSharedPreference(ctx);

        return prefs != null && prefs.edit().putInt(key, value).commit();
    }

    public static boolean addString(Context ctx, String key, String value) {
        if(StringUtil.isEmpty(key) || StringUtil.isEmpty(value))
            return false;

        SharedPreferences prefs = getSharedPreference(ctx);

        return prefs != null && prefs.edit().putString(key, value).commit();
    }

    /**
     * Add KEY and value
     *
     * @param ctx
     * @param resId
     * @param value
     * @return
     */
    public static boolean addBoolean(Context ctx, int resId, boolean value) {
        return addBoolean(ctx, ctx.getString(resId), value);
    }

    public static boolean addInteger(Context ctx, int resId, int value) {
        return addInteger(ctx, ctx.getString(resId), value);
    }

    public static boolean addString(Context ctx, int resId, String value) {
        return addString(ctx, ctx.getString(resId), value);
    }

    /**
     * Add preferences value
     *
     * @param prefs
     * @param key
     * @param value
     * @return
     */
    public static boolean addBoolean(SharedPreferences prefs, String key, boolean value) {
        return prefs != null && prefs.edit().putBoolean(key, value).commit();
    }

    public static boolean addInteger(SharedPreferences prefs, String key, int value) {
        return prefs != null && prefs.edit().putInt(key, value).commit();
    }

    public static boolean addString(SharedPreferences prefs, String key, String value) {
        return prefs != null && !(StringUtil.isEmpty(key) || StringUtil.isEmpty(value)) && prefs.edit().putString(key, value).commit();

    }

    /**
     * Get preferences value
     * @param prefs
     * @param key
     * @return
     */
    public static boolean getBoolean(SharedPreferences prefs, String key) {
        return !(prefs == null || StringUtil.isEmpty(key)) && prefs.getBoolean(key, false);

    }

    public static int getInteger(SharedPreferences prefs, String key) {
        if(prefs == null || StringUtil.isEmpty(key))
            return 0;

        return prefs.getInt(key, 0);
    }

    public static String getString(SharedPreferences prefs, String key) {
        if(prefs == null || StringUtil.isEmpty(key))
            return "";

        return prefs.getString(key, "");
    }

    /**
     * Get preferences value
     * @param ctx
     * @param key
     * @return
     */
    public static boolean getBoolean(Context ctx, String key) {
        return !(ctx == null || StringUtil.isEmpty(key)) && getSharedPreference(ctx).getBoolean(key, false);

    }

    public static int getInteger(Context ctx, String key) {
        if(ctx == null || StringUtil.isEmpty(key))
            return 0;

        return getSharedPreference(ctx).getInt(key, 0);
    }

    public static String getString(Context ctx, String key) {
        if(ctx == null || StringUtil.isEmpty(key))
            return "";

        return getSharedPreference(ctx).getString(key, "");
    }

    /**
     * Get preferences value
     * @param ctx
     * @param resId
     * @return
     */

    public static boolean getBoolean(Context ctx, int resId) {
        return getBoolean(ctx, ctx.getString(resId));
    }

    public static int getInteger(Context ctx, int resId) {
        return getInteger(ctx, ctx.getString(resId));
    }

    public static String getString(Context ctx, int resId) {
        return getString(ctx, ctx.getString(resId));
    }

    /**
     * Get preferences defValue
     * @param ctx
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        return !(ctx == null || StringUtil.isEmpty(key)) && getSharedPreference(ctx).getBoolean(key, defValue);

    }

    public static int getInteger(Context ctx, String key, int defValue) {
        if(ctx == null || StringUtil.isEmpty(key))
            return 0;

        return getSharedPreference(ctx).getInt(key, defValue);
    }

    public static String getString(Context ctx, String key, String defValue) {
        if(ctx == null || StringUtil.isEmpty(key))
            return "";

        return getSharedPreference(ctx).getString(key, defValue);
    }

    /**
     * Get preferences defValue
     * @param ctx
     * @param resId
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context ctx, int resId, boolean defValue) {
        return getBoolean(ctx, ctx.getString(resId), defValue);
    }

    public static int getInteger(Context ctx, int resId, int defValue) {
        return getInteger(ctx, ctx.getString(resId), defValue);
    }

    public static String getString(Context ctx, int resId, String defValue) {
        return getString(ctx, ctx.getString(resId), defValue);
    }

    /**
     * Remove preferences value
     *
     * @param prefs
     * @param key
     * @return
     */
    public static boolean remove(SharedPreferences prefs, String key) {
        return !(prefs == null || StringUtil.isEmpty(key)) && prefs.edit().remove(key).commit();
    }

    /**
     * Remove preferences value
     *
     * @param ctx
     * @param key
     * @return
     */
    public static boolean remove(Context ctx, String key) {
        return !(ctx == null || StringUtil.isEmpty(key)) && getSharedPreference(ctx).edit().remove(key).commit();
    }

    /**
     * Remove preferences value
     *
     * @param ctx
     * @param resId
     * @return
     */

    public static boolean remove(Context ctx, int resId) {
        return remove(ctx, ctx.getString(resId));
    }
}