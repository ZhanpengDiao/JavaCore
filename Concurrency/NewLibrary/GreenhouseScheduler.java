// use ScheduledThreadPoolExecutor
// run tasks at predefined times

package NewLibrary;

import java.util.Calendar;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GreenhouseScheduler {
    private volatile boolean light = false;
    private volatile boolean water = false;
    private String thermostat = "Day"; // because it is not volatile, its getter/setter need to set as synchronized

    public synchronized String getThermostat() {
        return thermostat;
    }

    public synchronized void setThermostat(String value) {
        thermostat = value;
    }

    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);

    public void schedule(Runnable event, long delay) { // run only once
        scheduler.schedule(event, delay, TimeUnit.MILLISECONDS);
    }
    public void reapeat(Runnable event, long initialDelay, long period) { // run and repeat at an interval
        scheduler.scheduleAtFixedRate(event, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    class LightOn implements Runnable {
        @Override
        public void run() {
            // put hardware control code here
            // to physically turn on light
            System.out.println("Turning on lights");
            light = true;
        }
    }

    class LightOff implements Runnable {
        @Override
        public void run() {
            // put hardware control code here
            // to physically turn off light
            System.out.println("Turning off lights");
            light = false;
        }
    }

    class WaterOn implements Runnable {
        @Override
        public void run() {
            // put hardware control code here
            System.out.println("Turning greenhouse water on");
            water = true;
        }
    }

    class WaterOff implements Runnable {
        @Override
        public void run() {
            // put hardware control code here
            System.out.println("Turning greenhouse water off");
            water = false;
        }
    }

    class ThermostatNight implements Runnable {
        @Override
        public void run() {
            // put hardware control code here
            System.out.println("Thermostat to night setting");
            setThermostat("Night");
        }
    }

    class ThermostatDay implements Runnable {
        @Override
        public void run() {
            // put hardware control code here
            System.out.println("Thermostat to day setting");
            setThermostat("Day");
        }
    }

    class Bell implements Runnable {
        @Override
        public void run() {
            System.out.println("Bing!");
        }
    }

    class Terminate implements Runnable {
        @Override
        public void run() {
            System.out.println("Terminating");
            scheduler.shutdownNow();
            // must start a separate task to do this job
            // since the scheduler has been shut down
            new Thread() {
                public void run() {
                    for(DataPoint d: data) // reporting
                        System.out.println(d);
                }
            }.start();
        }
    }

    // data collection
    static class DataPoint {
        final Calendar time;
        final float temperature;
        final float humidity;
        public DataPoint(Calendar d, float temp, float hum) {
            time = d;
            temperature = temp;
            humidity = hum;
        }
        public String toString() {
            return time.getTime() + String.format(" temperature: %1$.1f humidity: %2$.2f", temperature, humidity);
        }
    }
}
