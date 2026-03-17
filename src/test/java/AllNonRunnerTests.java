import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class AllNonRunnerTests {

    // =========================================================
    // NumberSearch.getNextLargest()
    //
    // Behavior: sorts the array, then returns the first value
    // strictly greater than searchNum. Returns -1 if none exists.
    // =========================================================

    @Test
    public void testNumberSearch_BasicNextLargest() {
        // Original tests retained
        assertEquals(4, NumberSearch.getNextLargest(new int[]{1, 2, 3, 4, 5}, 3));
        assertEquals(15, NumberSearch.getNextLargest(new int[]{10, 30, 20, 40, 50, 15}, 12));
        assertEquals(-1, NumberSearch.getNextLargest(new int[]{1, 2, 3}, 3));
    }

    @Test
    public void testNumberSearch_SearchNumNotInArray() {
        // Target is not present in the array at all — should still find next larger
        assertEquals(10, NumberSearch.getNextLargest(new int[]{5, 10, 15}, 7));
    }

    @Test
    public void testNumberSearch_SearchNumIsLargest() {
        // Target equals the largest element — nothing larger exists
        assertEquals(-1, NumberSearch.getNextLargest(new int[]{1, 2, 3, 4, 5}, 5));
    }

    @Test
    public void testNumberSearch_SearchNumLargerThanAll() {
        // Target exceeds every element in the array
        assertEquals(-1, NumberSearch.getNextLargest(new int[]{1, 2, 3}, 100));
    }

    @Test
    public void testNumberSearch_SearchNumSmallerThanAll() {
        // Target is smaller than every element — should return the minimum
        assertEquals(1, NumberSearch.getNextLargest(new int[]{3, 1, 2}, 0));
    }

    @Test
    public void testNumberSearch_DuplicateValues() {
        // Array contains duplicates of the search number — next larger should still work
        assertEquals(5, NumberSearch.getNextLargest(new int[]{3, 3, 5, 7}, 3));
    }

    @Test
    public void testNumberSearch_DuplicatesOfNextLargest() {
        // Multiple copies of the "next largest" value — should still return that value once
        assertEquals(5, NumberSearch.getNextLargest(new int[]{3, 5, 5, 7}, 3));
    }

    @Test
    public void testNumberSearch_SingleElementFound() {
        // Single element array where no larger value exists
        assertEquals(-1, NumberSearch.getNextLargest(new int[]{5}, 5));
    }

    @Test
    public void testNumberSearch_SingleElementLarger() {
        // Single element array where the element is larger than the target
        assertEquals(5, NumberSearch.getNextLargest(new int[]{5}, 3));
    }

    @Test
    public void testNumberSearch_UnsortedInput() {
        // Method sorts internally — verify it handles unsorted input correctly
        assertEquals(20, NumberSearch.getNextLargest(new int[]{50, 10, 40, 20, 30}, 15));
    }


    // =========================================================
    // NumberShifter.makeLucky7Array()
    //
    // Behavior: returns an int[] of the given size where every
    // element is in the range [1, 10] (random values).
    // =========================================================

    @Test
    public void testNumberShifter_MakeLucky7Array_BasicSize() {
        // Original test retained
        int[] array = NumberShifter.makeLucky7Array(10);
        assertNotNull(array);
        assertEquals(10, array.length);
        for (int value : array) {
            assertTrue(value >= 1 && value <= 10,
                "Expected value in [1,10] but got: " + value);
        }
    }

    @Test
    public void testNumberShifter_MakeLucky7Array_SizeOne() {
        int[] array = NumberShifter.makeLucky7Array(1);
        assertNotNull(array);
        assertEquals(1, array.length);
        assertTrue(array[0] >= 1 && array[0] <= 10);
    }

    @Test
    public void testNumberShifter_MakeLucky7Array_LargeSize() {
        int[] array = NumberShifter.makeLucky7Array(1000);
        assertNotNull(array);
        assertEquals(1000, array.length);
        for (int value : array) {
            assertTrue(value >= 1 && value <= 10,
                "Expected value in [1,10] but got: " + value);
        }
    }


    // =========================================================
    // NumberShifter.shiftEm()
    //
    // Behavior: moves all 7s to the front of the array (in-place),
    // filling remaining spots with the displaced values.
    // All original elements must be preserved.
    // =========================================================

    @Test
    public void testNumberShifter_ShiftEm_Basic() {
        // Original test — just checks index 0
        int[] before = {1, 2, 7, 4};
        NumberShifter.shiftEm(before);
        assertEquals(7, before[0]);
    }

    @Test
    public void testNumberShifter_ShiftEm_AllElementsPreserved() {
        // After shifting, all original values must still be in the array
        int[] before = {1, 2, 7, 4};
        int[] original = before.clone();
        NumberShifter.shiftEm(before);

        int[] sortedBefore = before.clone();
        Arrays.sort(sortedBefore);
        Arrays.sort(original);
        assertArrayEquals(original, sortedBefore,
            "shiftEm must not add or remove elements");
    }

    @Test
    public void testNumberShifter_ShiftEm_Multiple7s() {
        // All 7s should bubble to the front
        int[] arr = {3, 7, 1, 7, 5};
        NumberShifter.shiftEm(arr);
        assertEquals(7, arr[0]);
        assertEquals(7, arr[1]);
    }

    @Test
    public void testNumberShifter_ShiftEm_Multiple7sPreserveElements() {
        int[] arr = {3, 7, 1, 7, 5};
        int[] original = arr.clone();
        NumberShifter.shiftEm(arr);
        Arrays.sort(arr);
        Arrays.sort(original);
        assertArrayEquals(original, arr);
    }

    @Test
    public void testNumberShifter_ShiftEm_No7s() {
        // No 7s present — array should be unchanged
        int[] arr = {1, 2, 3, 4};
        int[] expected = {1, 2, 3, 4};
        NumberShifter.shiftEm(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testNumberShifter_ShiftEm_All7s() {
        // Every element is 7 — array should stay all 7s
        int[] arr = {7, 7, 7};
        NumberShifter.shiftEm(arr);
        assertArrayEquals(new int[]{7, 7, 7}, arr);
    }

    @Test
    public void testNumberShifter_ShiftEm_7AlreadyAtFront() {
        // 7 is already first — array should be unchanged
        int[] arr = {7, 1, 2, 3};
        int[] expected = {7, 1, 2, 3};
        NumberShifter.shiftEm(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testNumberShifter_ShiftEm_7AtEnd() {
        // 7 at the very last position
        int[] arr = {1, 2, 3, 7};
        NumberShifter.shiftEm(arr);
        assertEquals(7, arr[0]);

        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        assertArrayEquals(new int[]{1, 2, 3, 7}, sorted);
    }


    // =========================================================
    // NumberSorter.getSortedDigitArray()
    //
    // Behavior: extracts each digit of the number into an array,
    // then returns it sorted in ascending order.
    // =========================================================

    @Test
    public void testNumberSorter_Basic() {
        // Original test retained — verifies length and contents
        int[] sorted = NumberSorter.getSortedDigitArray(567891);
        assertNotNull(sorted);
        assertEquals(6, sorted.length);
        int[] sortedNatural = Arrays.stream(sorted).sorted().toArray();
        assertArrayEquals(new int[]{1, 5, 6, 7, 8, 9}, sortedNatural);
    }

    @Test
    public void testNumberSorter_ReturnedArrayIsActuallySorted() {
        // Verifies the array comes back in ascending order, not just containing correct digits
        int[] result = NumberSorter.getSortedDigitArray(567891);
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue(result[i] <= result[i + 1],
                "Array is not sorted at index " + i + ": " + result[i] + " > " + result[i + 1]);
        }
    }

    @Test
    public void testNumberSorter_SingleDigit() {
        int[] result = NumberSorter.getSortedDigitArray(5);
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(5, result[0]);
    }

    @Test
    public void testNumberSorter_RepeatingDigits() {
        // 1122 → digits [1, 1, 2, 2] sorted
        int[] result = NumberSorter.getSortedDigitArray(1122);
        assertNotNull(result);
        assertEquals(4, result.length);
        assertArrayEquals(new int[]{1, 1, 2, 2}, result);
    }

    @Test
    public void testNumberSorter_AlreadySortedDigits() {
        // 1234 — digits already in ascending order
        int[] result = NumberSorter.getSortedDigitArray(1234);
        assertArrayEquals(new int[]{1, 2, 3, 4}, result);
    }

    @Test
    public void testNumberSorter_ReverseSortedDigits() {
        // 4321 — digits in descending order, should be returned ascending
        int[] result = NumberSorter.getSortedDigitArray(4321);
        assertArrayEquals(new int[]{1, 2, 3, 4}, result);
    }

    @Test
    public void testNumberSorter_ContainsZeroDigit() {
        // 5010 → digits [0, 0, 1, 5] sorted — note: leading zeros disappear in int
        // 5010 has digits 5, 0, 1, 0 → sorted: [0, 0, 1, 5]
        int[] result = NumberSorter.getSortedDigitArray(5010);
        assertArrayEquals(new int[]{0, 0, 1, 5}, result);
    }


    // =========================================================
    // WordSort
    //
    // Behavior: splits on spaces, sorts alphabetically (natural
    // Java string order), toString() formats as "word N :: word\n"
    // with a trailing "\n\n".
    // =========================================================

    @Test
    public void testWordSort_Basic() {
        // Original test retained
        WordSort wordSort = new WordSort("dog cat apple");
        wordSort.sort();
        assertEquals("word 0 :: apple\nword 1 :: cat\nword 2 :: dog\n\n\n",
            wordSort.toString());
    }

    @Test
    public void testWordSort_SingleWord() {
        WordSort wordSort = new WordSort("hello");
        wordSort.sort();
        assertEquals("word 0 :: hello\n\n\n", wordSort.toString());
    }

    @Test
    public void testWordSort_AlreadySorted() {
        WordSort wordSort = new WordSort("apple banana cherry");
        wordSort.sort();
        assertEquals("word 0 :: apple\nword 1 :: banana\nword 2 :: cherry\n\n\n",
            wordSort.toString());
    }

    @Test
    public void testWordSort_TwoWords() {
        WordSort wordSort = new WordSort("zebra apple");
        wordSort.sort();
        assertEquals("word 0 :: apple\nword 1 :: zebra\n\n\n", wordSort.toString());
    }

    @Test
    public void testWordSort_CaseSensitivity() {
        // Java's natural String sort puts uppercase before lowercase
        WordSort wordSort = new WordSort("banana Apple cherry");
        wordSort.sort();
        // 'A' (65) < 'b' (98) < 'c' (99) in Unicode
        assertEquals("word 0 :: Apple\nword 1 :: banana\nword 2 :: cherry\n\n\n",
            wordSort.toString());
    }

    @Test
    public void testWordSort_ToStringWithoutSort() {
        // toString() before sort() should still produce correctly formatted output,
        // just in original (unsorted) order
        WordSort wordSort = new WordSort("dog cat apple");
        String out = wordSort.toString();
        assertTrue(out.startsWith("word 0 :: "));
        assertTrue(out.endsWith("\n\n\n"));
    }

    @Test
    public void testWordSort_FiveWords() {
        WordSort wordSort = new WordSort("mango kiwi apple banana cherry");
        wordSort.sort();
        assertEquals(
            "word 0 :: apple\nword 1 :: banana\nword 2 :: cherry\nword 3 :: kiwi\nword 4 :: mango\n\n\n",
            wordSort.toString());
    }


    // =========================================================
    // WordSortTwo
    //
    // Behavior: same split/sort logic, but toString() formats as
    // plain "word\n" lines with a trailing "\n\n" (no index prefix).
    // =========================================================

    @Test
    public void testWordSortTwo_Basic() {
        // Original test retained
        WordSortTwo wordSortTwo = new WordSortTwo("zebra apple banana");
        wordSortTwo.sort();
        assertEquals("apple\nbanana\nzebra\n\n\n", wordSortTwo.toString());
    }

    @Test
    public void testWordSortTwo_SingleWord() {
        WordSortTwo wordSortTwo = new WordSortTwo("hello");
        wordSortTwo.sort();
        assertEquals("hello\n\n\n", wordSortTwo.toString());
    }

    @Test
    public void testWordSortTwo_AlreadySorted() {
        WordSortTwo wordSortTwo = new WordSortTwo("apple banana cherry");
        wordSortTwo.sort();
        assertEquals("apple\nbanana\ncherry\n\n\n", wordSortTwo.toString());
    }

    @Test
    public void testWordSortTwo_TwoWords() {
        WordSortTwo wordSortTwo = new WordSortTwo("mango apple");
        wordSortTwo.sort();
        assertEquals("apple\nmango\n\n\n", wordSortTwo.toString());
    }

    @Test
    public void testWordSortTwo_CaseSensitivity() {
        // Same Unicode ordering applies — uppercase sorts before lowercase
        WordSortTwo wordSortTwo = new WordSortTwo("banana Apple cherry");
        wordSortTwo.sort();
        assertEquals("Apple\nbanana\ncherry\n\n\n", wordSortTwo.toString());
    }

    @Test
    public void testWordSortTwo_SetWords() {
        // Tests the setWords() method replaces words correctly
        WordSortTwo wordSortTwo = new WordSortTwo("zebra apple");
        wordSortTwo.setWords("mango kiwi apple");
        wordSortTwo.sort();
        assertEquals("apple\nkiwi\nmango\n\n\n", wordSortTwo.toString());
    }

    @Test
    public void testWordSortTwo_ToStringWithoutSort() {
        // toString() before sort() should still format correctly (unsorted order)
        WordSortTwo wordSortTwo = new WordSortTwo("dog cat apple");
        String out = wordSortTwo.toString();
        // Should end with the double newline footer and not contain index prefixes
        assertTrue(out.endsWith("\n\n\n"));
        assertFalse(out.contains("word 0 ::"),
            "WordSortTwo.toString() should not contain index prefix like WordSort does");
    }

    @Test
    public void testWordSortTwo_FiveWords() {
        WordSortTwo wordSortTwo = new WordSortTwo("mango kiwi apple banana cherry");
        wordSortTwo.sort();
        assertEquals("apple\nbanana\ncherry\nkiwi\nmango\n\n\n", wordSortTwo.toString());
    }
}