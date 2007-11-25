/*
 * timing.java
 * 
 * Created on 11-Sep-2007, 01:30:40
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gcreator.compilers.gjava.api;

import org.gcreator.compilers.gjava.core.basicgame;

/**
     * Good games require careful timing of things happening. Fortunately G-Java does most of the timing for you.
     */
    public class Timing {

        /**
         * Speed of the game in the current room (in steps per second).
         */
        public static double room_speed;
        /**
         * Number of frames that are actually drawn per second.
         */
        public static double fps = basicgame.Current.averageFPS;
        /**
         * Number of milliseconds that have passed since the system was started.
         */
        public static double current_time = Date.currentTime();
        /**
         *  The current year.
         */
        public static double current_year = Date.get_year(Date.currentDate());

        /**
         *  The current month.
         */
        public static double current_month = Date.get_month(Date.currentDate());
        /**
         * The current day.
         */
        public static double current_day = Date.get_day(Date.currentDate());
        /**
         * The current day of the week (1=sunday, ..., 7=saturday).
         */
        public static double current_weekday = Date.get_weekday(Date.currentDate());
        /**
         * The current hour.
         */
        public static double current_hour = Date.get_hour(Date.currentTime());
        /**
         * The current minute.
         */
        public static double current_minute = Date.get_minute(Date.currentTime());
        /**
         * The current second.
         */
        public static double current_second = Date.get_second(Date.currentTime());

        /**
         * Sleeps numb milliseconds.
         * @param numb
         */
        public static void sleep(long numb) {
            try {
                java.lang.Thread.sleep(numb);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

