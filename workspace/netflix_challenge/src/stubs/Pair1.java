package stubs;

import java.io.*;

import org.apache.hadoop.io.WritableComparable;
//following http://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/tuple/Pair.html
public class Pair1 implements WritableComparable<Pair1> {

	Integer l;
	Double r;

	public Pair1() {

	}
	public Pair1(int left, double right) {
		this.l = left;
		this.r = right;    
	}

	public void write(DataOutput out) throws IOException {
		out.writeInt(l);
		out.writeDouble(r);
	}

	public void readFields(DataInput in) throws IOException {
		l = in.readInt();
		r = in.readDouble();
	}
	public int compareTo(Pair1 other) {
		int ret = 0;

		if (equals(other)){
			ret = 0;
		}
		else{
			int LeftResult = l.compareTo(other.l);
			int RightResult = r.compareTo(other.r);
			if (LeftResult > 0){
				ret = 1;
			}
			if (LeftResult < 0){
				ret = -1;
			}
			if (LeftResult == 0){
				if (RightResult > 0){
					ret = 1;
				}
				if (RightResult < 0){
					ret = -1;
				}
			}
		}
		return ret;
	}
	public String toString() {
		return "(" + l + "," + r + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair1 other = (Pair1) obj;

		if((l==other.l)&&(r==(other.r)))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((l == null) ? 0 : l.hashCode());
		result = prime * result + ((r == null) ? 0 : r.hashCode());
		return result;
	}
	public int getLeft() {
		return l;
	}
	public Double getRight() {
		return r;
	}
}
