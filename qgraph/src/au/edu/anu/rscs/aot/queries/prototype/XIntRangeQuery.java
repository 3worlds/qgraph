package au.edu.anu.rscs.aot.queries.prototype;

import au.edu.anu.rscs.aot.collections.DynamicList;
import fr.ens.biologie.generic.Sizeable;

public class XIntRangeQuery extends XQuery {
	private int min;
	private int max;
	private Sizeable localItem;

	public XIntRangeQuery(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public XQuery process(Object input) {
		initProcess(input);
		localItem = (Sizeable) result;
		long size = localItem.size();
		satisfied = size >= min && size <= max;
		if (!satisfied)
			failMsg =  "Size of " + localItem.getClass().getSimpleName() + " is crap";
		return this;
	}


	public static void main(String[] args) {
		DynamicList<Integer> sizeable = new DynamicList<>();
		sizeable.add(1);
		XSequenceQuery sq = new XSequenceQuery(new XIntRangeQuery(2, 3),new XIntRangeQuery(5,9));
		sq.process(sizeable);
		if (!sq.satisfied) {
			System.out.println(sq.failMsg);

		}
	}

}
