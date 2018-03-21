package SharingResources;

// abstract class for EvenChecker
public abstract class IntGenerator {
    private volatile boolean cancelled = false;
    public abstract int next();
    // allow to be cancelled
    public void cancel() { cancelled = true; }
    public boolean isCancelled() { return cancelled; }
}
