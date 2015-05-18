package au.edu.wehi.idsv.debruijn.positional;

/**
 * Evidence contribution to the given kmer position of a single piece of evidence
 * @author cameron.d
 *
 */
public class KmerSupportNode extends KmerNode {
	public long kmer() { return evidence.kmer(offset); }
	public int startPosition() { return evidence.startPosition() - evidence.errorWidth() + offset; }
	public int endPosition() { return evidence.startPosition() + evidence.errorWidth() + offset; }
	public int weight() { return evidence.weight(offset); }
	public boolean isReference() { return evidence.isAnchored(offset); }
	private final int offset;
	private final Evidence evidence;
	public KmerSupportNode(Evidence evidence, int offset) {
		this.evidence = evidence;
		this.offset = offset;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((evidence == null) ? 0 : evidence.hashCode());
		result = prime * result + offset;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KmerSupportNode other = (KmerSupportNode) obj;
		if (evidence == null) {
			if (other.evidence != null)
				return false;
		} else if (!evidence.equals(other.evidence))
			return false;
		if (offset != other.offset)
			return false;
		return true;
	}
}