package org.powermilk

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MergeSortedArraysTest {
    private val sortedArray1 = intArrayOf(1, 18, 22, 100, 105, 1002)
    private val sortedArray2 = intArrayOf(16, 17, 19, 21, 1001)
    private val notSortedArray1 = intArrayOf(1, 22, 18, 10, 1002)
    private val notSortedArray2 = intArrayOf(17, 16, 1, 101, 11)
    private val ones = IntArray(6) { 1 }
    private val twos = IntArray(6) { 2 }

    @ParameterizedTest(name = "should throws IllegalArgumentException (empty array as input)")
    @MethodSource("emptyArrays")
    fun `should throws IllegalArgumentException (empty array as input)`(array1: IntArray, array2: IntArray) {
        val expected = "Arrays cannot be empty"
        val exception = assertThrows<IllegalArgumentException> { array1.merge(array2) }
        assertEquals(expected, exception.message)
    }

    @ParameterizedTest(name = "Test should throws IllegalArgumentException (not sorted array as input)")
    @MethodSource("notSortedArrays")
    fun `should throw illegal argument exception for not sorted array`(array1: IntArray, array2: IntArray) {
        val expected = "Arrays have to be sorted"
        val exception = assertThrows<IllegalArgumentException> { array1.merge(array2) }
        assertEquals(expected, exception.message)
    }

    @ParameterizedTest(name = "Test should merges two identical arrays")
    @MethodSource("sameArrays")
    fun `should merge two identical arrays`(array: IntArray) {
        val expected = IntArray(array.size * 2) { array.first() }
        val actual = array.merge(array)

        assertEquals(expected.contentToString(), actual.contentToString())
    }

    @Test
    fun `should merged for two arrays with the same value`() {
        val expected = ones + twos
        val actual = ones.merge(twos)

        assertEquals(expected.contentToString(), actual.contentToString())
    }

    @Test
    fun `should merged two sorted arrays`() {
        val expected = intArrayOf(1, 16, 17, 18, 19, 21, 22, 100, 105, 1001, 1002)
        val actual = sortedArray1.merge(sortedArray2)

        assertEquals(expected.contentToString(), actual.contentToString())
    }

    @Test
    fun `should merged two unsorted arrays`() {
        val expected = intArrayOf(1, 1, 10, 11, 16, 17, 18, 22, 101, 1002)
        val actual = notSortedArray1.copyOf().merge(notSortedArray2.copyOf(), true)

        assertEquals(expected.contentToString(), actual.contentToString())
    }

    private fun notSortedArrays() = listOf(
        Arguments.of(notSortedArray1, sortedArray2),
        Arguments.of(sortedArray1, notSortedArray2),
        Arguments.of(notSortedArray1, notSortedArray2)
    )

    private fun emptyArrays() = listOf(
        Arguments.of(intArrayOf(), sortedArray2),
        Arguments.of(sortedArray1, intArrayOf()),
        Arguments.of(intArrayOf(), notSortedArray2)
    )

    private fun sameArrays() = listOf(Arguments.of(ones), Arguments.of(twos))
}
