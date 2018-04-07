// use ScheduledThreadPoolExecutor
// run tasks at predefined times

package NewLibrary;

import java.util.*;
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

    private Calendar lastTime = Calendar.getInstance();
    {
        // Adjust date to the half hour
        lastTime.set(Calendar.MINUTE, 30);
        lastTime.set(Calendar.SECOND, 00);
    }

    private float lastTemp = 65.0f;
    private int tempDirection = +1;
    private float lastHumidity = 50.0f;
    private int humidityDirection = +1;
    private Random rand = new Random();
    List<DataPoint> data = Collections.synchronizedList(new ArrayList<DataPoint>());

    class CollectData implements Runnable {
        @Override
        public void run() {
            System.out.println("Collecting data");
            synchronized (GreenhouseScheduler.this) {
                // prevent the interval is longer than it is
                lastTime.set(Calendar.MINUTE, lastTime.get(Calendar.MINUTE) + 30);
                // 1 in 5 chances of reversing the direction
                if(rand.nextInt(5) == 4) {
                    tempDirection = -tempDirection;
                }
                // store previous value
                lastTemp = lastTemp + tempDirection * (1.0f + rand.nextFloat());
                if(rand.nextInt(5) == 4) {
                    humidityDirection = -humidityDirection;
                }
                // store previous value
                lastHumidity = lastHumidity + humidityDirection * rand.nextFloat();
                // Calendar must be cloned, otherwise all
                // Datapoints hold references to the same lastTime.
                // For a basic object like Calendar, clone() is ok
                data.add(new DataPoint((Calendar)lastTime.clone(), lastTemp, lastHumidity));
            }
        }
    }

    public static void main(String[] args) {
        GreenhouseScheduler gh = new GreenhouseScheduler();
        gh.schedule(gh.new Terminate(), 5000);

        gh.reapeat(gh.new Bell(), 0, 1000);
        gh.reapeat(gh.new ThermostatNight(), 0, 2000);
        gh.reapeat(gh.new LightOn(), 0, 200);
        gh.reapeat(gh.new LightOff(), 0, 400);
        gh.reapeat(gh.new WaterOn(), 0, 600);
        gh.reapeat(gh.new WaterOff(), 0, 800);
        gh.reapeat(gh.new ThermostatDay(), 0, 1400);
        gh.reapeat(gh.new CollectData(), 500, 500);

    }
}

