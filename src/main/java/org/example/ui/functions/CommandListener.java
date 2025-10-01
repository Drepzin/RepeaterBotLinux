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
        Long pressTime = System.nanoTime();

        if (!KEY_PRESSED_TIME.containsKey(nativeEvent.getRawCode())){
            KEY_PRESSED_TIME.put(nativeEvent.getRawCode(), pressTime);
        }

        NeoCommandsData pressCommand = new NeoCommandsData("keyPress", null, null, null, nativeEvent.getRawCode(), pressTime);
        syncQueue.add(pressCommand);

        if (stopButton.equalsIgnoreCase(nativeEvent.getKeyText(nativeEvent.getKeyCode()))){
            stopRecording();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        Long releaseTime = System.nanoTime();
        Integer nativeEventCode = nativeEvent.getRawCode();

         KEY_PRESSED_TIME.remove(nativeEventCode);

        NeoCommandsData releaseCommand = new NeoCommandsData("keyRelease", null, null, null, nativeEventCode, releaseTime);
        syncQueue.add(releaseCommand);
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
        Long clickTime = System.nanoTime();

        if(nativeEvent.getButton() == 1){
            System.out.println(nativeEvent.getX() + ":" + nativeEvent.getY());
        }

        syncQueue.add(new NeoCommandsData("mouseMoved", null, nativeEvent.getY(), nativeEvent.getX(), null, clickTime));
        syncQueue.add(new NeoCommandsData("mouseClick", nativeEvent.getButton(), nativeEvent.getY(), nativeEvent.getX(), null, clickTime));
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