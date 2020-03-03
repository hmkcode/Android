package simple.plusmortproject;

public class ServivalBg extends Thread {

    public ServivalBg() {
    }

    public ServivalBg(Runnable runnable) {
        super(runnable);
    }

    public ServivalBg(Runnable runnable, String threadName) {
        super(runnable, threadName);
    }

    public ServivalBg(String threadName) {
        super(threadName);
    }

    public ServivalBg(ThreadGroup group, Runnable runnable) {
        super(group, runnable);
    }

    public ServivalBg(ThreadGroup group, Runnable runnable, String threadName) {
        super(group, runnable, threadName);
    }

    public ServivalBg(ThreadGroup group, String threadName) {
        super(group, threadName);
    }

    public ServivalBg(ThreadGroup group, Runnable runnable, String threadName, long stackSize) {
        super(group, runnable, threadName, stackSize);
    }

    @Override
    public int countStackFrames() {
        return super.countStackFrames();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public ClassLoader getContextClassLoader() {
        return super.getContextClassLoader();
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public State getState() {
        return super.getState();
    }

    @Override
    public UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return super.getUncaughtExceptionHandler();
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }

    @Override
    public boolean isInterrupted() {
        return super.isInterrupted();
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void setContextClassLoader(ClassLoader cl) {
        super.setContextClassLoader(cl);
    }

    @Override
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
        super.setUncaughtExceptionHandler(handler);
    }
}
