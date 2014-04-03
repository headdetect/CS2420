package homework08;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A few tests for the SpecialtySet class.  The last test demonstrates a novel
 * way to count comparisons.  Students should add more tests (especially more
 * simple tests) to validate their SpecialtySet.
 * 
 * @author Peter Jensen
 * @version 2/22/2014
 */
public class SpecialtySetTest
{

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp () throws Exception
    {        
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown () throws Exception
    {        
    }

    /**
     * Test the size of a newly constructed set.
     */
    @Test
    public void test01 ()
    {
        SpecialtySet<Double> s = new SpecialtySet<Double>();
        assertEquals("An newly constructed set should have a 0 size: ", 0, s.size());
    }

    /**
     * A simple add/contains test.
     */    
    @Test
    public void test02 ()
    {
        SpecialtySet<String> s = new SpecialtySet<String>();
        s.add("Hello");
        s.add("Helloo");
        s.add("Hellooo");
        
        // Bug in test, not your code, so fix the test first.
        assertEquals("The set should contain 'Hello': ", true, s.contains("Hello"));
        assertEquals("The set should contain 'Helloo': ", true, s.contains("Helloo"));
        assertEquals("The set should contain 'Hellooo': ", true, s.contains("Hellooo"));
    }
    
    /**
     * A long running add/remove/contains test that uses the
     * helper class below.  This test is VERY incomplete.
     */    
    @Test
    public void test03 ()
    {
        // Our set.
        
        SpecialtySet<TrackedInteger> s = new SpecialtySet<TrackedInteger>();
        
        // A known good set to validate against.
        
        Set<Integer> v = new TreeSet<Integer>();
        
        // A random number generator seeded to give back the same sequence each time.
        
        Random r = new Random(8675309);
        
        // A debugging variable.
        
        int totalActions = 0;
        
        // Repeatedly 'visit' sequences of numbers.
        
        for (int repeat = 0; repeat < 100; repeat++)
        {
            // Pick a base, length, step
            
            int base = r.nextInt(1000);
            int length = r.nextInt(50) + 50;
            int step = r.nextInt(3) + 1;
            
            // Do an action the appropriate number of times.
            
            for (int i = 0; i < length; i++)
            {
                // Make the next integer in the sequence.
                
                TrackedInteger ti = new TrackedInteger(base + i * step);   
                
                // Pick an action.
                
                int action = r.nextInt(3);
            
                // Do the action.
              
                totalActions++;
                
                if (action == 0)
                {
                    s.add(ti);    // Change our set 
                	v.add(ti.i);  // Also change the known good set
                }
                else if (action == 1)
                {
                    s.remove(ti); // Change our set
                	v.remove(ti.i);  // Also change the known good set
                }
                else if (action == 2)
                {
                    // The 'contains' method should report identically for both sets.
                	
                    assertEquals(s.contains(ti), v.contains(ti.i));
                }
            }
        }

        // If the specialty set is coded properly, a relatively small number of
        //   comparisons are done.
        assertTrue ("Maximum comparison count test: " + TrackedInteger.comparisonCount + " <= 44536", TrackedInteger.comparisonCount <= 44536);
        
        
        // Uncomment if needed.
        
         System.out.println (totalActions);
         System.out.println (TrackedInteger.comparisonCount);        
         //assertEquals(s.validate(), true);
    }
    
    /**
     * A helper class with a static variable for tracking all comparisons
     * made with any of this type of object.
     *
     * @author Peter Jensen
     * @version 2/22/2014
     */
    private static class TrackedInteger implements Comparable<TrackedInteger>
    {
        static long comparisonCount = 0;
        
        Integer i;
        
        TrackedInteger(int i)
        {
            this.i = i;    
        }
        
        @Override
        public int compareTo (TrackedInteger o)
        {
            comparisonCount++;
            return i.compareTo(o.i);
        }
        
        @Override
        public boolean equals (Object o)
        {
            return (o instanceof TrackedInteger) ? ((TrackedInteger) o).compareTo(this) == 0 : false;
        }
        
        @Override
        public String toString ()
        {
            return "" + i;
        }

    }
    
}
