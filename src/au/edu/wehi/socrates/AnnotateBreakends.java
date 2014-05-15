package au.edu.wehi.socrates;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import picard.cmdline.Option;
import picard.cmdline.StandardOptionDefinitions;
import picard.cmdline.Usage;
import htsjdk.samtools.util.IOUtil;
import htsjdk.samtools.reference.ReferenceSequenceFile;
import htsjdk.samtools.reference.ReferenceSequenceFileFactory;
import htsjdk.samtools.util.Log;
import htsjdk.samtools.util.ProgressLogger;
import htsjdk.samtools.SAMFileHeader;
import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SAMSequenceDictionary;

import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.variantcontext.writer.VariantContextWriter;
import htsjdk.variant.variantcontext.writer.VariantContextWriterBuilder;
import htsjdk.variant.vcf.VCFFileReader;
import htsjdk.variant.vcf.VCFHeader;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

import au.edu.wehi.socrates.vcf.VcfConstants;

/**
 * Clusters evidence that supports a common breakpoint together
 * @author Daniel Cameron
 *
 */
public class AnnotateBreakends extends CommandLineProgram {

    private static final String PROGRAM_VERSION = "0.1";

    // The following attributes define the command-line arguments
    @Usage
    public String USAGE = getStandardUsagePreamble() + "Annotates breakend calls with supporting evidence "
    		+ PROGRAM_VERSION;
    @Option(doc = "Input BAM file.",
            optional = false,
            shortName = StandardOptionDefinitions.INPUT_SHORT_NAME)
    public File INPUT;
    @Option(doc = "Breakpoint calls in VCF format",
            optional = true,
            shortName= StandardOptionDefinitions.OUTPUT_SHORT_NAME)
    public File OUTPUT;
    @Option(doc="Reference used for alignment",
    		optional = false,
    		shortName=StandardOptionDefinitions.REFERENCE_SHORT_NAME)
    public File REFERENCE;
    @Option(doc = "Picard metrics file generated by ExtractEvidence",
            optional = true)
    public File METRICS = null;
    private Log log = Log.getInstance(AnnotateBreakends.class);
    @Override
	protected int doWork() {
    	SAMFileReader.setDefaultValidationStringency(SAMFileReader.ValidationStringency.SILENT);
    	try {
    		if (METRICS == null) {
    			METRICS = FileNamingConvention.getMetrics(INPUT);
    		}
    		if (OUTPUT == null) {
    			OUTPUT = FileNamingConvention.getOutputVcf(INPUT);
    		}
    		IOUtil.assertFileIsReadable(INPUT);
    		IOUtil.assertFileIsReadable(REFERENCE);
    		IOUtil.assertFileIsReadable(METRICS);
    		IOUtil.assertFileIsWritable(OUTPUT);
    		
    		// Sequential processing of variants:
    		// open VCF
    		// (assert sorted?)
    		if (FileNamingConvention.getBreakpointVcf(INPUT).exists()) {
    			// single input file
    		}
    	} catch (IOException e) {
    		log.error(e);
    		throw new RuntimeException(e);
    	}
        return 0;
    }
	public static void main(String[] argv) {
        System.exit(new GenerateDirectedBreakpoints().instanceMain(argv));
    }
}