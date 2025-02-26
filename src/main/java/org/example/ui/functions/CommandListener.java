package org.example.ui.functions;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import org.example.entitys.CommandsData;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandListener implements NativeKeyListener, NativeMouseInputListener {

    private final Queue<CommandsData> syncQueue = new LinkedBlockingQueue<>();

    private String path;

    private String stopButton;

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        System.out.println("Tecla pressionada: " + nativeEvent.getRawCode() + " | Texto: " + nativeEvent.getKeyText(nativeEvent.getKeyCode()));
        syncQueue.add(new CommandsData("keyBoard", null, null, null, nativeEvent.getRawCode()));
        if (stopButton.equalsIgnoreCase(nativeEvent.getKeyText(nativeEvent.getKeyCode()))){
            System.out.println("opa");
            stopRecording();
        }
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
        if(nativeEvent.getButton() == 1){
            System.out.println(nativeEvent.getX() + ":" + nativeEvent.getY());
        }
        syncQueue.add(new CommandsData("mouseMoved", null, nativeEvent.getY(), nativeEvent.getX(), null));
        syncQueue.add(new CommandsData("mouseClicked", nativeEvent.getButton(), null, null, null));
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
