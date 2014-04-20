package homework10;

/**
 * A 'compressor' class is any class that can compress or decompress
 * a list of bytes.  The following interface provides definitions
 * for compress and decompress methods, as well as a method for
 * retrieving the name of the compression method.
 * 
 * Note that since the Compressor interface methods specify byte arrays,
 * no file access needs to be done during compression or decompression.
 *  
 * Also note that Compressor classes should not have any state.  This means
 * that there should not be any fields (object variables)
 * or static variables in any implementation of this Compressor interface.
 * 
 * @author Peter Jensen - CS 2420
 * @version Spring 2014
 */
public interface Compressor
{
    /**
     * Given any array of bytes that contain some data, this method returns a 
     * compressed form of the original data.  The returned, compressed bytes must
     * contain sufficient information so that the decompress method below can
     * reconstruct the original data.
     *
     * @param  data  An array of bytes that contains some data that should be compressed
     * @return       An array of bytes that contains the compressed form of the original data
     */
    public byte[] compress (byte[] data);

    
    /**
     * Given an array of bytes that contain compressed data that 
     * was compressed using this compressor, this method will reconstruct and return
     * the original, uncompressed data.  The compressed bytes must contain sufficient
     * information so that this method can reconstruct the original data bytes.
     *
     * @param  compressedData  An array of bytes that contains some data in compressed form
     * @return                 An array of bytes that contains the original, uncompressed data
     */
    public byte[] decompress (byte[] compressedData);
}
