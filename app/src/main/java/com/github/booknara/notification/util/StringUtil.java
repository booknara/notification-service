package com.github.booknara.notification.util;

/**
 * 
 * @author : Daehee Han(@daniel_booknara)
 * @version : 1.0.0
 */
public class StringUtil {
    
    private StringUtil() { }
	
	/**
	 * Check string is null or empty
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;

    }

	/**
	 * Check string is null or empty
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;

    }
	
	/**
	 * Check any string is null or empty
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isAnyEmpty(String str1, String str2) {
        return isEmpty(str1) || isEmpty(str2);

    }

	
	/**
	 * Check all string is null or empty
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isAllEmpty(String str1, String str2) {
        return isEmpty(str1) && isEmpty(str2);

    }
	
	/**
	 * Check string is contain character 
	 * @param str
	 * @return
	 */
	public static boolean isContainCharacter(String str) {
		if(str == null || str.trim().length() ==0)
			return false;
			
		for(int i=0; i < str.length(); i++)
			if(!Character.isDigit(str.charAt(i)))
				return false;
	
		return true;
	}
	
	/**
	 * Check string is number 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		try {
			Long.parseLong(str);
		} catch(NumberFormatException e) {
			return false;
		}
		
		return true;
	}

    /**
     * Trim white space
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (isEmpty(str))
            return null;

        return str.replace(String.valueOf((char) 160), " ").trim();
    }

    /**
	 * Make path
	 * 
	 * @param prefix
	 * @param postfix
	 * @return
	 */
	public static String makePath(String prefix, String postfix) {
		if(isAnyEmpty(prefix, postfix))
			return "";
		
		return prefix + " > " + postfix;
	}
}