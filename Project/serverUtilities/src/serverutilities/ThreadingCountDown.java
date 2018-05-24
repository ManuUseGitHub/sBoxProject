/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverutilities;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import serviceDefinition.ServiceDef;

/**
 *
 * @author MAZE2
 */
public class ThreadingCountDown {

    private int count;
    private final int startValue;
    private Runnable endAction;
    private final long millis;
    private Class parentClass;

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public ThreadingCountDown(int startValue, long millis) {
        this.startValue = startValue;
        this.millis = millis;
        this.count = 0;
    }

    public void reset(Runnable quitAfterTimeOut) {
        this.endAction = quitAfterTimeOut;
        reset();
    }

    public void reset() {
        boolean isUp = isCountdownUp();
        count = startValue;
        if (isUp) {
            new Thread(process()).start();
        }
    }

    private Runnable process() {
        return () -> {
            while (!isCountdownUp()) {
                sleep();
                --count;
            }
            if (endAction != null) {
                endAction.run();
            }
        };
    }

    private void sleep() {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadingCountDown.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the isUp
     */
    public boolean isCountdownUp() {
        return getCount() <= 0;
    }

    public void end(Runnable endAction) {
        this.endAction = endAction;
        end();
    }

    public void end() {
        count = 0;
    }

    public void setEndAction(Runnable quitAfterTimeOut,Class parentClass) {
        this.endAction = quitAfterTimeOut;
        this.parentClass = parentClass;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    private Map<String,String> getServiceAfterInfo() {
        Map<String,String> infos = new HashMap();
        infos.put("DESC", "");
        infos.put("NAME", "no action");
        if (endAction != null) {
            for (Method method : parentClass.getMethods()) {
                if (Modifier.isPublic(method.getModifiers())){
                    ServiceDef serviceDef = method.getAnnotation(ServiceDef.class);
                    if (serviceDef != null) {
                        infos.put("PARAMS", Arrays.toString(serviceDef.params()));
                        infos.put("DESC", serviceDef.desc());
                        infos.put("NAME", method.getName());
                    }
                }
            }
        }
        return infos;
    }

    @Override
    public String toString() {
        Map<String,String> actionInfos = getServiceAfterInfo();
        String description = actionInfos.get("DESC");
        String actionName = "\nClosing action: "+actionInfos.get("NAME");
        description = !description.isEmpty()?"\nEffect: "+description:"";
        return String.format("countdown is at %d/%d \nup [%s]%s%s", count, startValue, isCountdownUp(),actionName,description);
    }

}
