package com.example.mark.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.util.Log;

public class Libs {

    private static final String TAG = "libs";

    private static int hour=0,minite;
    private static String[] time;

    public static String answerSpeech(Context context, String string) {



        String[] strings = string.split(" ");

        if (strings[0].equalsIgnoreCase(StringConstant.STR_SET) && strings.length>1) {
            if (strings[1].equalsIgnoreCase(StringConstant.STR_ALARM) && strings.length>2) {
                if (strings[2].equalsIgnoreCase(StringConstant.STR_AT) && strings.length>3) {
                    try {
                        time = strings[3].split(":");
                        try {
                            hour = Integer.parseInt(time[0]);
                        }catch (NumberFormatException e){
                            Log.i(TAG,time[0]+" not number");
                        }
                        try {
                            minite = Integer.parseInt(time[1]);
                        }catch (NumberFormatException e){
                            Log.i(TAG,time[1]+" not number");
                        }
                        try {
                            Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
                            i.putExtra(AlarmClock.EXTRA_HOUR, hour);
                            i.putExtra(AlarmClock.EXTRA_MINUTES, minite);
                            i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                            context.startActivity(i);
                        }catch (Exception e){
                            Log.i(TAG,"alarm erroe");
                        }



                    } catch (NumberFormatException e) {
                        Log.i(TAG, "not time");
                    }

                    return StringConstant.STR_ALARM+" "+StringConstant.STR_IS +" "+StringConstant.STR_SET;

                }else {
                    return StringConstant.STR_I_DONT_GET_YOU;
                }
            }else {
                return StringConstant.STR_I_DONT_GET_YOU;
            }
        } else {

            switch (string.toLowerCase()) {
                case StringConstant.STR_HEY_MARK:
                    return StringConstant.STR_I_AM_FINE_HOW_CAN_I_HELP_YOU;

                case StringConstant.STR_HELLO:
                    return StringConstant.STR_HI_THERE;

                case StringConstant.STR_HI:
                    return StringConstant.STR_HI_THERE + " " + StringConstant.STR_HOW_CAN_I_HELP_YOU;

                case StringConstant.STR_WHAT_IS_YOUR_NAME:
                    return StringConstant.STR_MY_NAME_IS_MARK + " " + StringConstant.STR_I_AM_HERE_TO_HELP_YOU;

                case StringConstant.STR_TELL_ME_JOKE:
                    return StringConstant.STR_JOKE1;

                default:
                    return StringConstant.STR_I_DONT_GET_YOU;
            }
        }
    }
}