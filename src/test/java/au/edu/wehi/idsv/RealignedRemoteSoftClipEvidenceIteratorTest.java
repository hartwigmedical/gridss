package au.edu.wehi.idsv;

import static org.junit.Assert.assertEquals;
import htsjdk.samtools.SAMRecord;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;


public class RealignedRemoteSoftClipEvidenceIteratorTest extends TestHelper {
	@Test
	public void should_iterate_over_remote_coordinate_as_local() {
		SAMRecord[] orderedRealigned = new SAMRecord[] {
			withReadName("2#3#0#fr3", Read(0, 10, "15M"))[0],
			withReadName("1#2#0#fr2", Read(1, 10, "15M"))[0],
			withReadName("0#1#0#fr1", Read(1, 15, "15M"))[0],
			withReadName("2#3#0#br2", Read(2, 10, "15M"))[0],
			
		};		
		SAMRecord[] softClip = new SAMRecord[] {
				SoftClipEvidence.create(SES(), FWD, withReadName("r3", Read(2, 3, "15S15M15S"))[0], orderedRealigned[0]).getSAMRecord(),
				SoftClipEvidence.create(SES(), FWD, withReadName("r2", Read(1, 2, "15S15M15S"))[0], orderedRealigned[1]).getSAMRecord(),
				SoftClipEvidence.create(SES(), FWD, withReadName("r1", Read(0, 1, "15S15M15S"))[0], orderedRealigned[2]).getSAMRecord(),
				SoftClipEvidence.create(SES(), BWD, withReadName("r2", Read(1, 2, "15S15M15S"))[0], orderedRealigned[3]).getSAMRecord(),
		};
		List<RealignedRemoteSoftClipEvidence> result = Lists.newArrayList(new RealignedRemoteSoftClipEvidenceIterator(SES(), Lists.newArrayList(orderedRealigned).iterator(), Lists.newArrayList(softClip).iterator(), Lists.newArrayList(softClip).iterator()));
		assertEquals(4, result.size());
		assertEquals(FWD, result.get(0).getBreakendSummary().direction2);
		assertEquals(FWD, result.get(1).getBreakendSummary().direction2);
		assertEquals(FWD, result.get(2).getBreakendSummary().direction2);
		assertEquals(BWD, result.get(3).getBreakendSummary().direction2);
		assertEquals("Rfr3", result.get(0).getEvidenceID());
		assertEquals("Rfr2", result.get(1).getEvidenceID());
		assertEquals("Rfr1", result.get(2).getEvidenceID());
		assertEquals("Rbr2", result.get(3).getEvidenceID());
	}
}
