package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@RequestMapping("/memory")
	public void memory() {
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
