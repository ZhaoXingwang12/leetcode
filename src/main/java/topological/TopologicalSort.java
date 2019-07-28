package topological;

import java.util.*;

/**
 * 实现拓扑排序方法一：
 *1、从 DAG 图中选择一个 没有前驱（即入度为0）的顶点并输出。
 *2、从图中删除该顶点和所有以它为起点的有向边。
 *3、重复 1 和 2 直到当前的 DAG 图为空或当前图中不存在无前驱的顶点为止。后一种情况说明有向图中必然存在环。
 */

public class TopologicalSort {

    private static Map<String, List<String>> findMappings(List<Task> tasks) {
        HashMap<String, List<String>> ret = new HashMap<>();
        if (tasks == null || tasks.isEmpty()) {
            return ret;
        }
        for (Task task : tasks) {
            if (ret.containsKey(task.getValue())) {
                if (task.getKey() != null) {
                    ret.get(task.getValue()).add(task.getKey());
                }
            } else {
                ret.put(task.getValue(), new ArrayList<String>() {
                    {
                        if (task.getKey() != null) {
                            add(task.getKey());
                        }
                    }
                });
            }
        }
        return ret;
    }

    private static Map<String, Integer> computeInDegree(Map<String, List<String>> depends) {
        HashMap<String, Integer> inDegreeMap = new HashMap<>();
        if(depends==null||depends.isEmpty()){
            return inDegreeMap;
        }
        depends.forEach((key,value)->inDegreeMap.put(key,value.size()));
        return inDegreeMap;
    }

    private static void println(Map<String, Integer> inDegreeMap,List<Task> tasks){
        if(inDegreeMap==null||inDegreeMap.size() ==0){
            System.out.println("parameters error");
        }
        int num = inDegreeMap.size();
        while (num>0){
            Set<String> keys = inDegreeMap.keySet();
            for (String key : keys) {
                Integer count = inDegreeMap.get(key);
                if(count==0){
                    System.out.println(key);
                    num--;
                    inDegreeMap.put(key,-1);
                    decIndegree(key,tasks,inDegreeMap);
                }
            }
        }
    }

    private static void decIndegree(String key,List<Task> tasks,Map<String, Integer> inDegreeMap){
        for (Task task : tasks) {
            if(key.equals(task.getKey())){
                Integer value = inDegreeMap.get(task.getValue());
                if(value !=null){
                    inDegreeMap.put(task.getValue(),--value);
                }
            }
        }

    }

    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("1","4"));
        tasks.add(new Task("1","2"));
        tasks.add(new Task("2","3"));
        tasks.add(new Task("4","3"));
        tasks.add(new Task("3","5"));
        tasks.add(new Task("4","5"));
        tasks.add(new Task("4","6"));
        tasks.add(new Task("5","6"));
        tasks.add(new Task(null,"1"));
        Map<String, List<String>> ret = findMappings(tasks);
        Map<String, Integer> inDegreeMap = computeInDegree(ret);
        println(inDegreeMap,tasks);
    }
}

class Task {
    private String key;
    private String value;

    public Task() {
    }

    public Task(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


