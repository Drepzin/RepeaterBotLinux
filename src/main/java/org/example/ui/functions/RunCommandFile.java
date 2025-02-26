package org.example.ui.functions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entitys.CommandsData;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RunCommandFile {
    private static Map<Integer, Integer> KEY_CODE_MAP = Map.ofEntries(
            // Letras
            Map.entry(97, KeyEvent.VK_A),
            Map.entry(98, KeyEvent.VK_B),
            Map.entry(99, KeyEvent.VK_C),
            Map.entry(100, KeyEvent.VK_D),
            Map.entry(101, KeyEvent.VK_E),
            Map.entry(102, KeyEvent.VK_F),
            Map.entry(103, KeyEvent.VK_G),
            Map.entry(104, KeyEvent.VK_H),
            Map.entry(105, KeyEvent.VK_I),
            Map.entry(106, KeyEvent.VK_J),
            Map.entry(107, KeyEvent.VK_K),
            Map.entry(108, KeyEvent.VK_L),
            Map.entry(109, KeyEvent.VK_M),
            Map.entry(110, KeyEvent.VK_N),
            Map.entry(111, KeyEvent.VK_O),
            Map.entry(112, KeyEvent.VK_P),
            Map.entry(113, KeyEvent.VK_Q),
            Map.entry(114, KeyEvent.VK_R),
            Map.entry(115, KeyEvent.VK_S),
            Map.entry(116, KeyEvent.VK_T),
            Map.entry(117, KeyEvent.VK_U),
            Map.entry(118, KeyEvent.VK_V),
            Map.entry(119, KeyEvent.VK_W),
            Map.entry(120, KeyEvent.VK_X),
            Map.entry(121, KeyEvent.VK_Y),
            Map.entry(122, KeyEvent.VK_Z),

            //FS
            Map.entry(65470, KeyEvent.VK_F1),
            Map.entry(65471, KeyEvent.VK_F2),
            Map.entry(65472, KeyEvent.VK_F3),
            Map.entry(65473, KeyEvent.VK_F4),
            Map.entry(65474, KeyEvent.VK_F5),
            Map.entry(65475, KeyEvent.VK_F6),
            Map.entry(65476, KeyEvent.VK_F7),
            Map.entry(65477, KeyEvent.VK_F8),
            Map.entry(65478, KeyEvent.VK_F9),
            Map.entry(65479, KeyEvent.VK_F10),
            Map.entry(65480, KeyEvent.VK_F11),
            Map.entry(65481, KeyEvent.VK_F12)
            );

    private static Map<Integer, Integer> MOUSE_BUTTONS = Map.ofEntries(
            Map.entry(1, InputEvent.BUTTON1_DOWN_MASK),
            Map.entry(2, InputEvent.BUTTON2_DOWN_MASK),
            Map.entry(3, InputEvent.BUTTON3_DOWN_MASK)
    );

    public static void runCommandFile(String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(path);
            java.util.List<CommandsData> commandsData = mapper.readValue(file, new TypeReference<List<CommandsData>>() {
            });
            for (CommandsData cd : commandsData) {
                runCommand(cd);
            }
        }
        catch (IOException e){
            throw new RuntimeException(e.getMessage());
    }
    }

    private static void runCommand(CommandsData cd){
        Integer coordinateX = cd.getCoordinateX();
        Integer coordinateY = cd.getCoordinateY();
        Integer keyCode = cd.getKeyTyped();
        Integer mouseButton = cd.getMouseButton();

        try {
            Robot robot = new Robot();
            switch (cd.getType()){
                case "mouseMoved": robot.mouseMove(coordinateX, coordinateY);
                Thread.sleep(1000);
                System.out.println(coordinateX + ":" + coordinateY);
                break;

                case "keyBoard": robot.keyPress(KEY_CODE_MAP.get(keyCode));
                Thread.sleep(500);
                robot.keyRelease(KEY_CODE_MAP.get(keyCode));
                break;

                case "mouseClicked": robot.mousePress(MOUSE_BUTTONS.get(mouseButton));
                Thread.sleep(1000);
                robot.mouseRelease(MOUSE_BUTTONS.get(mouseButton));
                    System.out.println(mouseButton);
                break;
            }
        }
        catch (AWTException | InterruptedException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}