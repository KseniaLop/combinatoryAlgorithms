import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String inPath = "input1.txt";
    private static boolean[] used;
    private static List<List<Integer>> components = new ArrayList<>();
    private static List<List<Integer>> graph = new ArrayList<>();

    public static void main(String[] args) {
        readData(inPath);
        int componentIndex = 1;
        for(int i = 1; i < used.length; i++){
            if(! used[i]) {
                dfs(i, componentIndex);
                componentIndex++;
            }
        }
        String output = (componentIndex - 1) + "\n";
        for(List<Integer> component : components){
            if(component.isEmpty())
                continue;
            component.sort((i1, i2) ->{
                if(i1 > i2)
                    return 1;
                if(i1 < i2)
                    return -1;
                return 0;
            } );
            for(int node : component){
                output += node + " ";
            }
            output += "0\n";
        }
        writeData(output);
    }

    private static void dfs(int v, int componentIndex){
        used[v] = true;
        components.get(componentIndex).add(v);
        for(int i = 0; i < graph.get(v).size(); i++){
            int next = graph.get(v).get(i);
            if(! used[next])
                dfs(next, componentIndex);
        }
    }

    private static void readData(String filePath){
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filePath), StandardCharsets.UTF_8))){
            String line = reader.readLine();
            int n = Integer.parseInt(line);
            used = new boolean[n+1];
            for(int i = 0; i < n+1; i++)
                used[i] = false;
            for(int i = 0; i < n+1; i++){
                components.add(new ArrayList<>());
                graph.add(new ArrayList<>());
            }
            int i = 1;
            while (i < n+1 && (line = reader.readLine()) != null) {
                String[] nodes = line.split(" ");
                int counter = 0;
                for(String v : nodes) {
                    int nodeNumber = Integer.parseInt(v);
                    if (nodeNumber != 0)
                        graph.get(i).add(nodeNumber);
                    if (nodeNumber == 1)
                        graph.get(i).add(counter + 1);
                    counter++;
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void writeData(String output){
        try(FileWriter writer = new FileWriter("out.txt", false))
        {
            writer.write(output);
            writer.flush();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
