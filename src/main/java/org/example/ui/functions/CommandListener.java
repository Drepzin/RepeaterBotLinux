package org.example.ui.functions;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import org.example.entitys.NeoCommandsData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandListener implements NativeKeyListener, NativeMouseInputListener {

    private final Queue<NeoCommandsData> syncQueue = new LinkedBlockingQueue<>();

    private final Map<Integer, Long> KEY_PRESSED_TIME = new HashMap<>();

    private String path;

    private String stopButton;


    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        System.out.println(nativeEvent.getRawCode() + " : " + nativeEvent.getKeyText(nativeEvent.getKeyCode()) );
        Long time = System.nanoTime();
        if (!KEY_PRESSED_TIME.containsKey(nativeEvent.getRawCode())){
            KEY_PRESSED_TIME.put(nativeEvent.getRawCode(), time);
        }
        if (stopButton.equalsIgnoreCase(nativeEvent.getKeyText(nativeEvent.getKeyCode()))){
            stopRecording();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        Integer nativeEventCode = nativeEvent.getRawCode();
        NeoCommandsData neoCommandsData = new NeoCommandsData("keyBoard", null, null, null, nativeEvent.getRawCode(), null);
        if(KEY_PRESSED_TIME.containsKey(nativeEventCode)){
           Long timePressed = (System.nanoTime() - KEY_PRESSED_TIME.remove(nativeEventCode)) / 1_000_000;
           if(timePressed >= 1000){
               if(timePressed % 100 >= 20 && timePressed % 100 <= 34){
                   timePressed+=25;
               }
           }
           neoCommandsData.setTimePressed(timePressed);
        }
        syncQueue.add(neoCommandsData);
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
        if(nativeEvent.getButton() == 1){
            System.out.println(nativeEvent.getX() + ":" + nativeEvent.getY());
        }
        syncQueue.add(new NeoCommandsData("mouseMoved", null, nativeEvent.getY(), nativeEvent.getX(), null, null));
        syncQueue.add(new NeoCommandsData("mouseClicked", nativeEvent.getButton(), null, null, null, null));
    }

    public void stopRecording(){
        try{
            System.out.println(syncQueue.size());
            FileFunction.writeJsonFile(syncQueue, path);
            GlobalScreen.unregisterNativeHook();
        }
        catch (NativeHookException e){
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getStopButton() {
        return stopButton;
    }

    public void setStopButton(String stopButton) {
        this.stopButton = stopButton;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
