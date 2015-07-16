
package playgo.systemmodel.classes.generated;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import il.ac.wis.cs.s2a.runtime.Utils;
import il.ac.wis.cs.systemmodel.SMBaseClass;
import il.ac.wis.cs.systemmodel.SystemModelGen;


/**
 * This file was automatically generated using PlayGo system-model.
 * Usage of the generated code is permitted for non-commercial research and evaluation purposes..
 * This class shouldn't be changed. Any change to this class will be overridden!!!
 * 
 */
public class ClockGenerated
    extends SMBaseClass
{

    private static long time = 0;
    private static String stringTime = "0";
    private static String timeFormat = "HH:mm:ss";
    private static Timer timer = null;
    private static Runnable clockRunnable = null;
    private static int tickInterval = 0;
    private static boolean autoTick = false;
    private static boolean startFromSysTime = false;
    private static boolean stopClock = true;
    private boolean firstTime = true;

    public ClockGenerated(java.lang.String name) {
        super(name);
        Properties properties = new Properties();
        java.lang.String runtimePropertiesPath = Utils.getRuntimePropertiesPath();
        try {
            properties.load(new java.io.FileInputStream(runtimePropertiesPath));
        } catch (FileNotFoundException _x) {
            _x.printStackTrace();
        } catch (IOException _x) {
            _x.printStackTrace();
        }
        tickInterval=1000;
        if ((properties.getProperty("clockTickInterval") !=null)) {
            tickInterval = Integer.parseInt(properties.getProperty("clockTickInterval"));
        }
        autoTick=true;
        if ((properties.getProperty("clockAutoTicks") !=null)) {
            autoTick= Boolean.parseBoolean(properties.getProperty("clockAutoTicks"));
        }
        startFromSysTime= Boolean.parseBoolean(properties.getProperty("clockStartFromSysTime"));
        timeFormat= properties.getProperty("stringTimeFormat");
    }

    @SystemModelGen
    public static long getTime() {
        return time;
    }

    @SystemModelGen
    public String getName() {
        return name;
    }

    @SystemModelGen
    public static String getStringTime() {
        return stringTime;
    }

    @SystemModelGen
    public static int getTickInterval() {
        return tickInterval;
    }

    @SystemModelGen
    public static boolean isAutoTick() {
        return autoTick;
    }

    @SystemModelGen
    public static boolean getAutoTick() {
        return autoTick;
    }

    @SystemModelGen
    public void setTickInterval(int arg0) {
        if ((tickInterval!=arg0)) {
            tickInterval=arg0;
        }
        initTimer();
        System.out.println(">>> method call: >>> setTickInterval(int) : void");
    }

    @SystemModelGen
    public void tick() {
        time = time + tickInterval;

        formatStringTime();
        //System.out.println(">>> method call: >>> tick() : void, time = " + time);
        return ;
    }

    @SystemModelGen
    private void formatStringTime() {
        java.text.SimpleDateFormat format = null;
        try {
            format = new java.text.SimpleDateFormat(timeFormat);
        } catch (Exception _x) {
            format = new java.text.SimpleDateFormat("HH:mm:ss");
        }
        java.util.Date d = new java.util.Date(time);
        stringTime = format.format(d);
    }

    @SystemModelGen
    public void start() {
        if (startFromSysTime) {
            time = (System.currentTimeMillis());
        } else {
            time = (0);
        }
        formatStringTime();
        System.out.println(">>> starting timer >>>");
        stopClock=false;
        System.out.println(">>> method call: >>> start() : void");
        return ;
    }

    @SystemModelGen
    public void stop() {
        stopClock=true;
        System.out.println(">>> method call: >>> stop() : void");
        return ;
    }

    @SystemModelGen
    public void restart() {
        stop();
        start();
        System.out.println(">>> method call: >>> restart() : void");
        return ;
    }

    @SystemModelGen
    public void initTimer() {
        if ((clockRunnable==null)) {
            clockRunnable = new Runnable() {
            @Override
            public void run() {
             if(!stopClock)
            tick();
            }
            };
        }
        if ((timer!=null)) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
         @Override
        public void run() { 
        clockRunnable.run(); 
        }
        },2000,tickInterval);
    }

    @SystemModelGen
    public void startTimer() {
        if ((!firstTime)) {
            return ;
        }
        playgo.systemmodel.classes.Clock clock = playgo.systemmodel.classes.Clock.getInstance();
        clock.initTimer();
        if ((clock.isAutoTick())) {
            clock.start();
        }
        firstTime = false;
    }

}
