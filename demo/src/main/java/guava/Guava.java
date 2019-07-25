package guava;

import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class model{
	private int i;
	private int j;
	public model(int i,int j){
		this.i = i;
		this.j = j;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
}
public class Guava {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedHashMultiset<String> test=LinkedHashMultiset.create();
		test.add("test");
		test.add("test");
		test.add("test1");
		test.add("test2");
		test.add("test");
		for(String target: test)
		{
			System.out.println(target);
		}
		LinkedHashMultimap<Integer,String> test2 = LinkedHashMultimap.create();
		test2.put(1, "test");
		test2.put(1, "test1");
		test2.put(1, "test2");
		for(Integer target: test2.keys())
		{
			System.out.println(target);
		}
		List<model> list = new ArrayList<model>();
		list.add(new model(2,4));
		list.add(new model(1,3));
		list.add(new model(1,2));
		list.add(new model(2,3));
		Collections.sort(list, new Comparator<model>(){

			public int compare(model arg0, model arg1) {
				return ComparisonChain.start()
				.compare(arg0.getI(), arg1.getI())
				.compare(arg0.getJ(), arg1.getJ())
				.result();
			}
			
		});
		for(model m:list){
			System.out.println(m.getI()+"  "+m.getJ());
		}
		Collections.shuffle(list);
		
		list.add(null);
		
		List<model> alist  = Ordering.allEqual().nullsFirst().sortedCopy(list);
		showModels(alist);
		
		Iterables.removeIf(list, new Predicate<model>(){
			public boolean apply(model s){
				return s == null;
			}
		});
		
		showModels(list);
		
		ArrayList<model> list2 = Lists.newArrayListWithCapacity(10);
		
		list2.add(new model(4,4));
		list2.add(new model(3,3));
		list2.add(new model(3,2));
		list2.add(new model(4,3));
		
		Iterable<model> it = FluentIterable.from(Iterables.concat(list, list2))
							.filter(Predicates.notNull())
							.toList();
		showModels((List<model>)it);
		
		Multimap<String,model> map = HashMultimap.create();
		map.put("123", new model(3,2));
		map.put("123", new model(3,3));
		map.put("124", new model(4,2));
		map.put("124", new model(4,3));
		
		Multimap<String,model> maps = Multimaps.filterKeys(map, new Predicate<String>(){//filter means filter in, not filter out
			public boolean apply(String s){
				return !s.equals("123");
			}
		});
		
		for(String key:maps.keySet()){
			for(model m:maps.get(key)){
			if(m != null)
				
				System.out.println(key + " " +m.getI()+"  "+m.getJ());
			
			else
				
				System.out.println("null");
			}
		}
		
		model m = null;
		Optional<model> option = Optional.fromNullable(m);
		Optional<model> opt = Optional.absent();
		Optional<model> op = Optional.of(new model(5,6));
		
		if(option.isPresent()){
			System.out.println("is present");
		}
		if(opt.isPresent()){
			System.out.println("is present");
		}
		if(op.isPresent()){
			System.out.println("is present");
		}
	}
	
	private static void showModels(List<model> it){
		for(model m:it){
			if(m != null)
				
				System.out.println(m.getI()+"  "+m.getJ());
			
			else
				
				System.out.println("null");
		}
		System.out.println("");
	}
}
