import com.jjyu.entity.SortResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestComparator {
    public static void main(String[] args) {
        List<SortResult> sortResultList = new ArrayList<>();
        SortResult sortResult = new SortResult();
        sortResult.setRepoName("11111");
        sortResult.setPrOrder(10);
        SortResult sortResult2 = new SortResult();
        sortResult2.setRepoName("222222");
        sortResult2.setPrOrder(2);
        SortResult sortResult3 = new SortResult();
        sortResult3.setRepoName("3333333");
        sortResult3.setPrOrder(11);
        sortResultList.add(sortResult);
        sortResultList.add(sortResult2);
        sortResultList.add(sortResult3);
        System.out.println(sortResultList);
        sortResultList.sort(new Comparator<SortResult>() {
            @Override
            public int compare(SortResult o1, SortResult o2) {
                return o1.getPrOrder()-o2.getPrOrder();
            }
        });
        System.out.println(sortResultList);
    }
}
