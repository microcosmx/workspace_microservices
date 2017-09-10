package example.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExampleServiceImpl implements ExampleService {

    @Override
    public String test(){
        memory();
        return "final";
    }

    private void memory() {
        List<int[]> list = new ArrayList<int[]>();

        Runtime run = Runtime.getRuntime();
        int i = 1;
        while (true) {
            int[] arr = new int[1024 * 8];
            list.add(arr);

            if (i++ % 1000 == 0) {
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                System.out.print("最大内存=" + run.maxMemory() / 1024 / 1024 + "M,");
                System.out.print("已分配内存=" + run.totalMemory() / 1024 / 1024 + "M,");
                System.out.print("剩余空间内存=" + run.freeMemory() / 1024 / 1024 + "M");
                System.out.println(
                        "最大可用内存=" + (run.maxMemory() - run.totalMemory() + run.freeMemory()) / 1024 / 1024 + "M");
            }
        }
    }
}
