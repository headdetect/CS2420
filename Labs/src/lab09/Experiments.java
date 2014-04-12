package lab09;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import lab09.SimpleHashSet.TableFullException;

import org.junit.Test;

public class Experiments
{

	@Test
	public void testSimple()
	{
		SimpleHashSet set = new SimpleHashSet(100);
		for (int i = 0; i < 100; i++)
		{
			NumberName n = new NumberName(i);
			set.add(n);
		}
		System.out.println("Number of probes => " + set.getProbeCount());
	}

	@Test
	public void test1()
	{
		for (int i = 1; i <= 30; i++)
		{
			SimpleHashSet set = new SimpleHashSet(500);

			Random r = new Random();

			for (int k = 0; k < 400; k++)
			{
				NumberName n = new NumberName(k);
				set.add(n);
			}

			set.resetProbeCount();

			for (int samples = 1; samples < 10000 * i; samples++)
			{
				set.contains(Math.abs(r.nextInt()));
			}

			System.out.println(10000 * i + "\t" + set.getProbeCount());
		}
	}

	@Test
	public void test2()
	{
		Map<Integer, Float> map = new TreeMap<Integer, Float>();

		int highestNumber = 5000;
		int setCapacity = 200;

		for (int sample = 1; sample < 20; sample++)
		{

			// Determine the number of possible elements, the set capacity, and
			// the number of tests.

			int numberOfActions = 10 * sample;

			// Create a boolean array that will indicate if each
			// possible number is in our hash set.

			boolean[] shouldBeInTheSet = new boolean[highestNumber];

			// Create a counter to indicate how many elements are in the set.

			int expectedSetSize = 0;

			// Create the set to have the specified capacity.

			SimpleHashSet set = new SimpleHashSet(setCapacity);

			// Randomly add or remove numbers to the set and then
			// verify the integrity of the set. This loop is weighted so that
			// additions are more likely to happen at the start and
			// removals are more likely to happen at the end.

			Random r = new Random(8877614); // Used for repeatability

			for (int actionCount = 0; actionCount < numberOfActions; actionCount++)
			{
				// Debug - print out the table. Comment out or uncomment as needed.
				// set.debugTable();
				// System.out.println("  " + set.getClusterMap());

				// Select a random number element.

				int number = r.nextInt(highestNumber);
				NumberName element = new NumberName(number);

				// Randomly determine an action to do with this element.

				boolean doAdd = r.nextInt(numberOfActions) > actionCount;

				if (doAdd || !doAdd)
				{
					try
					{
						// System.out.println("Adding " + element + " (hashes to location " + (Math.abs(element.hashCode()) % setCapacity) + ")");

						// Attempt to add the element.

						set.add(element);

						// If the element was in the set, nothing should have happened.

						if (!shouldBeInTheSet[number])
						{
							// The element was added. If the table was full, error.

							if (expectedSetSize == setCapacity)
							{
								System.out.flush();
								System.err.println("Set sizes disagree.  The table should have " + expectedSetSize + " elements in it, but an exception was not raised.");
								System.err.flush();
								return;
							}

							// Otherwise, increase the count.

							shouldBeInTheSet[number] = true;
							expectedSetSize++;
						}
					}
					catch (TableFullException e)
					{
						// Verify that an exception should have been raised.

						if (expectedSetSize != setCapacity)
						{
							System.out.flush();
							System.err.println("Set sizes disagree.  The table should only have " + expectedSetSize + " elements in it, but it appears to have " + setCapacity
									+ " elements in it.");
							System.err.flush();
							return;
						}
					}
				}
				else
				{
					// Delete an element.

					// System.out.println("Removing " + element + " (hashes to location " + (Math.abs(element.hashCode()) % setCapacity) + ")");

					// Attempt to remove the element.

					set.delete(element);

					// If the element was not in the set, nothing should have happened.

					if (shouldBeInTheSet[number])
					{
						// The element was removed, mark it as removed.

						shouldBeInTheSet[number] = false;
						expectedSetSize--;
					}
				}

				if (!map.containsKey(set.size()))
					map.put(set.size(), (float) set.getProbeCount());

				map.put(set.size(), (map.get(set.size()) + set.getProbeCount()) / 2);

				set.resetProbeCount();

				// Verify that the set only contains the elements that have been added but not removed.

				boolean hasError = false;

				for (int i = 0; i < highestNumber; i++)
				{
					NumberName n = new NumberName(i);

					boolean inTheSet = set.contains(n);

					if (inTheSet && !shouldBeInTheSet[i])
					{
						System.out.flush();
						System.err.println(n + " (hashes to location " + (Math.abs(n.hashCode()) % setCapacity) + ") appears to be in the set, but should not be.");
						System.err.flush();
						hasError = true;
					}

					if (!inTheSet && shouldBeInTheSet[i])
					{
						System.out.flush();
						System.err.println(n + " (hashes to location " + (Math.abs(n.hashCode()) % setCapacity) + ") does not appear to be in the set, but should be.");
						System.err.flush();
						hasError = true;
					}
				}

				// Stop testing if the validation fails.

				if (hasError)
					break;
			}
		}

		float max = 0;
		for (Entry<Integer, Float> var : map.entrySet())
		{
			max = Math.max(var.getValue(), max);
		}
		for (Entry<Integer, Float> var : map.entrySet())
		{
			System.out.println(((float) var.getKey() / (float) setCapacity) + "\t" + (var.getValue() / max));
		}
	}

	@Test
	public void test3()
	{
		Map<Integer, Float> map = new TreeMap<Integer, Float>();

		int highestNumber = 5000;
		int setCapacity = 200;

		for (int sample = 1; sample < 20; sample++)
		{

			// Determine the number of possible elements, the set capacity, and
			// the number of tests.

			int numberOfActions = 10 * sample;

			// Create a boolean array that will indicate if each
			// possible number is in our hash set.

			boolean[] shouldBeInTheSet = new boolean[highestNumber];

			// Create a counter to indicate how many elements are in the set.

			int expectedSetSize = 0;

			// Create the set to have the specified capacity.

			SimpleHashSet set = new SimpleHashSet(setCapacity);

			// Randomly add or remove numbers to the set and then
			// verify the integrity of the set. This loop is weighted so that
			// additions are more likely to happen at the start and
			// removals are more likely to happen at the end.

			Random r = new Random(8877614); // Used for repeatability

			for (int actionCount = 0; actionCount < numberOfActions; actionCount++)
			{
				// Debug - print out the table. Comment out or uncomment as needed.
				// set.debugTable();
				// System.out.println("  " + set.getClusterMap());

				// Select a random number element.

				int number = r.nextInt(highestNumber);
				NumberName element = new NumberName(number);

				// Randomly determine an action to do with this element.

				boolean doAdd = r.nextInt(numberOfActions) > actionCount;

				if (doAdd || !doAdd)
				{
					try
					{
						// System.out.println("Adding " + element + " (hashes to location " + (Math.abs(element.hashCode()) % setCapacity) + ")");

						// Attempt to add the element.

						set.add(element);

						// If the element was in the set, nothing should have happened.

						if (!shouldBeInTheSet[number])
						{
							// The element was added. If the table was full, error.

							if (expectedSetSize == setCapacity)
							{
								System.out.flush();
								System.err.println("Set sizes disagree.  The table should have " + expectedSetSize + " elements in it, but an exception was not raised.");
								System.err.flush();
								return;
							}

							// Otherwise, increase the count.

							shouldBeInTheSet[number] = true;
							expectedSetSize++;
						}
					}
					catch (TableFullException e)
					{
						// Verify that an exception should have been raised.

						if (expectedSetSize != setCapacity)
						{
							System.out.flush();
							System.err.println("Set sizes disagree.  The table should only have " + expectedSetSize + " elements in it, but it appears to have " + setCapacity
									+ " elements in it.");
							System.err.flush();
							return;
						}
					}
				}
				else
				{
					// Delete an element.

					// System.out.println("Removing " + element + " (hashes to location " + (Math.abs(element.hashCode()) % setCapacity) + ")");

					// Attempt to remove the element.

					set.delete(element);

					// If the element was not in the set, nothing should have happened.

					if (shouldBeInTheSet[number])
					{
						// The element was removed, mark it as removed.

						shouldBeInTheSet[number] = false;
						expectedSetSize--;
					}
				}

				// Verify that the set only contains the elements that have been added but not removed.

				boolean hasError = false;

				for (int i = 0; i < highestNumber; i++)
				{
					NumberName n = new NumberName(i);

					set.resetProbeCount();

					boolean inTheSet = set.contains(n);

					if (!map.containsKey(set.size()))
						map.put(set.size(), (float) set.getProbeCount());

					map.put(set.size(), (map.get(set.size()) + set.getProbeCount()) / 2);

					set.resetProbeCount();

					if (inTheSet && !shouldBeInTheSet[i])
					{
						System.out.flush();
						System.err.println(n + " (hashes to location " + (Math.abs(n.hashCode()) % setCapacity) + ") appears to be in the set, but should not be.");
						System.err.flush();
						hasError = true;
					}

					if (!inTheSet && shouldBeInTheSet[i])
					{
						System.out.flush();
						System.err.println(n + " (hashes to location " + (Math.abs(n.hashCode()) % setCapacity) + ") does not appear to be in the set, but should be.");
						System.err.flush();
						hasError = true;
					}
				}

				// Stop testing if the validation fails.

				if (hasError)
					break;
			}
		}

		float max = 0;
		for (Entry<Integer, Float> var : map.entrySet())
		{
			max = Math.max(var.getValue(), max);
		}
		for (Entry<Integer, Float> var : map.entrySet())
		{
			System.out.println(((float) var.getKey() / (float) setCapacity) + "\t" + (var.getValue() / max));
		}
	}

	@Test
	public void test4()
	{
		Map<Integer, Float> map = new TreeMap<Integer, Float>();

		int highestNumber = 5000;
		int setCapacity = 200;

		for (int sample = 1; sample < 20; sample++)
		{

			// Determine the number of possible elements, the set capacity, and
			// the number of tests.

			int numberOfActions = 10 * sample;

			// Create a boolean array that will indicate if each
			// possible number is in our hash set.

			boolean[] shouldBeInTheSet = new boolean[highestNumber];

			// Create a counter to indicate how many elements are in the set.

			int expectedSetSize = 0;

			// Create the set to have the specified capacity.

			SimpleHashSet4 set = new SimpleHashSet4(setCapacity);

			// Randomly add or remove numbers to the set and then
			// verify the integrity of the set. This loop is weighted so that
			// additions are more likely to happen at the start and
			// removals are more likely to happen at the end.

			Random r = new Random(8877614); // Used for repeatability

			for (int actionCount = 0; actionCount < numberOfActions; actionCount++)
			{
				// Debug - print out the table. Comment out or uncomment as needed.
				// set.debugTable();
				// System.out.println("  " + set.getClusterMap());

				// Select a random number element.

				int number = r.nextInt(highestNumber);
				NumberName element = new NumberName(number);

				// Randomly determine an action to do with this element.

				boolean doAdd = r.nextInt(numberOfActions) > actionCount;

				if (doAdd || !doAdd)
				{
					try
					{
						// System.out.println("Adding " + element + " (hashes to location " + (Math.abs(element.hashCode()) % setCapacity) + ")");

						// Attempt to add the element.

						set.add(element);

						// If the element was in the set, nothing should have happened.

						if (!shouldBeInTheSet[number])
						{
							// The element was added. If the table was full, error.

							if (expectedSetSize == setCapacity)
							{
								System.out.flush();
								System.err.println("Set sizes disagree.  The table should have " + expectedSetSize + " elements in it, but an exception was not raised.");
								System.err.flush();
								return;
							}

							// Otherwise, increase the count.

							shouldBeInTheSet[number] = true;
							expectedSetSize++;
						}
					}
					catch (TableFullException e)
					{
						// Verify that an exception should have been raised.

						if (expectedSetSize != setCapacity)
						{
							System.out.flush();
							System.err.println("Set sizes disagree.  The table should only have " + expectedSetSize + " elements in it, but it appears to have " + setCapacity
									+ " elements in it.");
							System.err.flush();
							return;
						}
					}
				}
				else
				{
					// Delete an element.

					// System.out.println("Removing " + element + " (hashes to location " + (Math.abs(element.hashCode()) % setCapacity) + ")");

					// Attempt to remove the element.

					set.delete(element);

					// If the element was not in the set, nothing should have happened.

					if (shouldBeInTheSet[number])
					{
						// The element was removed, mark it as removed.

						shouldBeInTheSet[number] = false;
						expectedSetSize--;
					}
				}

				// Verify that the set only contains the elements that have been added but not removed.

				boolean hasError = false;

				for (int i = 0; i < highestNumber; i++)
				{
					NumberName n = new NumberName(i);

					set.resetProbeCount();

					boolean inTheSet = set.contains(n);

					if (!map.containsKey(set.size()))
						map.put(set.size(), (float) set.getProbeCount());

					map.put(set.size(), (map.get(set.size()) + set.getProbeCount()) / 2);

					set.resetProbeCount();

					if (inTheSet && !shouldBeInTheSet[i])
					{
						System.out.flush();
						System.err.println(n + " (hashes to location " + (Math.abs(n.hashCode()) % setCapacity) + ") appears to be in the set, but should not be.");
						System.err.flush();
						hasError = true;
					}

					if (!inTheSet && shouldBeInTheSet[i])
					{
						System.out.flush();
						System.err.println(n + " (hashes to location " + (Math.abs(n.hashCode()) % setCapacity) + ") does not appear to be in the set, but should be.");
						System.err.flush();
						hasError = true;
					}
				}

				// Stop testing if the validation fails.

				if (hasError)
					break;
			}
		}

		float max = 0;
		for (Entry<Integer, Float> var : map.entrySet())
		{
			max = Math.max(var.getValue(), max);
		}
		for (Entry<Integer, Float> var : map.entrySet())
		{
			System.out.println(((float) var.getKey() / (float) setCapacity) + "\t" + (var.getValue() / max));
		}
	}
}
