package starter;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import koordinator.StarterPOA;

public class StarterImpl extends StarterPOA {

    private HashMap<Integer, Process> process_map = new HashMap<>();
    private String koordinator_name;
    private String starter_name;
    private String host;
    private int port;
    private static int id = 1;

    public StarterImpl(String koordinator_name, String starter_name, String host, int port) {
        this.koordinator_name = koordinator_name;
        this.starter_name = starter_name;
        this.host = host;
        this.port = port;
    }

    @Override
    public void start_ggT_Prozess(int min_delay, int max_delay, int anz) {
        ProcessBuilder builder = new ProcessBuilder();
        builder = builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        for (int i = 0; i < anz; i++) {
//            builder.command("cmd", "/c", "java", "ggtProzess.Client_ggT", "-ORBInitialHost", host, "-ORBInitialPort", Integer.toString(port), koordinator_name,
//                    starter_name, Integer.toString(id), Integer.toString(min_delay), Integer.toString(max_delay));

            builder.command("java", "ggtProzess.Client_ggT", "-ORBInitialHost", host, "-ORBInitialPort", Integer.toString(port), koordinator_name,
                    starter_name, Integer.toString(id), Integer.toString(min_delay), Integer.toString(max_delay));

            try {
                process_map.put(id, builder.start());
                System.out.println("Prozess " + id + " gestartet!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            id++;
        }
    }

    public HashMap<Integer, Process> getProcess_map() {
        return process_map;
    }

    @Override
    public void exit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }).start();
    }

    @Override
    public void destroy(int id) {
        PrintStream out;
        
        if (process_map.containsKey(id)) {
            
            out = new PrintStream(process_map.get(id).getOutputStream());
            out.println("exit "+id);
            out.flush();
//            process_map.get(id).destroy();
            process_map.remove(id);
            try {
                process_map.get(id).waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void destroyAll() {
        PrintStream out;
        
        for (Integer it : process_map.keySet()) {
            
            out = new PrintStream(process_map.get(it).getOutputStream());
            out.println("exit "+it);
            out.flush();
            
            try {
                process_map.get(it).waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
        id = 0;
        process_map.clear();
    }

}
