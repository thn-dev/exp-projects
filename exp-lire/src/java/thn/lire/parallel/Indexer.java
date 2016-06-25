package thn.lire.parallel;

import java.io.File;
import java.io.IOException;

import net.semanticmetadata.lire.imageanalysis.features.global.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.imageanalysis.features.global.FCTH;
import net.semanticmetadata.lire.indexers.parallel.ParallelIndexer;

/**
 * Simple class showing the use of the ParallelIndexer, which uses up as much CPU as it can get.
 * @author Mathias Lux, mathias@juggle.at and Nektarios Anagnostopoulos, nek.anag@gmail.com
 *
 * Example from the link below
 * http://www.semanticmetadata.net/wiki/createindex/
 */
public class Indexer {
    public static void main(String[] args) throws IOException {
        // Checking if arg[0] is there and if it is a directory.
        boolean passed = false;
        if (args.length > 0) {
            File f = new File(args[0]);
            System.out.println("Indexing images in " + args[0]);
            if (f.exists() && f.isDirectory()) passed = true;
        }

        if (!passed) {
            System.out.println("No directory given as first argument.");
            System.out.println("Run \"ParallelIndexing <directory>\" to index files of a directory.");
            System.exit(1);
        }

        // use ParallelIndexer to index all photos from args[0] into "index" ... use 6 threads (actually 7 with the I/O thread).
        ParallelIndexer indexer = new ParallelIndexer(6, "index", args[0]);

        // use this to add you preferred builders. For now we go for CEDD, FCTH and AutoColorCorrelogram
        indexer.addExtractor(CEDD.class);
        indexer.addExtractor(FCTH.class);
        indexer.addExtractor(AutoColorCorrelogram.class);
        indexer.run();

        System.out.println("Finished indexing.");
    }
}
