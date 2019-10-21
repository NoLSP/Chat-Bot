public class Pair<T1, T2>{
    private T1 item1;
    private T2 item2;

    public Pair(T1 itm1, T2 itm2) 
    {
    	item1 = itm1;
    	item2 = itm2;
    }

    public boolean hasItem1() {
        return item1 != null;
    }

    public boolean hasItem2() {
        return item2 != null;
    }
    
    public boolean hasBoth()
    {
    	return item1 != null && item2 != null;
    }

    public T1 getItem1() {
        return item1;
    }

    public T2 getItem2() {
        return item2;
    }
}