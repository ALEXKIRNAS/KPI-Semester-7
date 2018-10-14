import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Lab05 {
    private static final Random random = new Random(System.currentTimeMillis());
    private static final int LISTS_SIZE = 17;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<Integer> listA = new ArrayList<>();
        List<Integer> listB = new ArrayList<>();

        for (int i = 0; i < LISTS_SIZE; ++i) {
            listA.add(random.nextInt(10));
            listB.add(random.nextInt(20));
        }

        System.out.println("List A:" + listA);
        System.out.println("List B:" + listB);

        CompletableFuture<List<Integer>> completableFutureListA = CompletableFuture
                .supplyAsync(() -> listA.stream().parallel().mapToInt(i -> i).average().orElseThrow(NoSuchElementException::new))
                .thenApplyAsync((avg) -> listA.stream().parallel().filter((i) -> i > avg).collect(Collectors.toList()))
                .thenApplyAsync((filteredListA) -> filteredListA.stream().parallel().sorted().collect(Collectors.toList()));

        CompletableFuture<List<Integer>> completableFutureListB = CompletableFuture
                .supplyAsync(() -> listB.stream().parallel().mapToInt(i -> i).average().orElseThrow(NoSuchElementException::new))
                .thenApplyAsync((avg) -> listB.stream().parallel().filter((i) -> i < avg).collect(Collectors.toList()))
                .thenApplyAsync((filteredListB) -> filteredListB.stream().parallel().sorted().collect(Collectors.toList()));

        CompletableFuture<List<Integer>> result = completableFutureListA.thenCombine(completableFutureListB, (sortedListA, sortedListB) -> {
            List<Integer> mergedList = new ArrayList<>();
            int sortedListASize = sortedListA.size();
            int sortedListBSize = sortedListB.size();

            System.out.println("Sorted List A:" + sortedListA);
            System.out.println("Sorted List B:" + sortedListB);

            int firstListPtr = 0;
            int secondListPtr = 0;

            while(firstListPtr < sortedListASize && secondListPtr < sortedListBSize) {
                if (sortedListA.get(0) < sortedListB.get(0)) {
                    firstListPtr++;
                    mergedList.add(sortedListA.remove(0));
                } else if (sortedListA.get(0).equals(sortedListB.get(0))){
                    sortedListA.remove(0);
                    firstListPtr++;
                } else {
                    sortedListB.remove(0);
                    secondListPtr++;
                }
            }

            while(firstListPtr < sortedListASize) {
                mergedList.add(sortedListA.remove(0));
                firstListPtr++;
            }

            return mergedList;
        });

        System.out.println("Result: " + result.get());
    }
}
