package org.powermilk

/**
 * Function extension to merge two sorted arrays
 *
 * @param array second array to merge
 * @param shouldSort flag to inform that should sort arrays
 * @throws IllegalArgumentException when one of the arrays is empty.
 * @throws IllegalArgumentException when one of the arrays is not sorted.
 */
fun IntArray.merge(array: IntArray, shouldSort: Boolean = false): IntArray {
    require(isNotEmpty() && array.isNotEmpty()) { "Arrays cannot be empty" }

    require((isSorted() && array.isSorted()) || shouldSort) { "Arrays have to be sorted" }

    if (shouldSort) {
        this.sort()
        array.sort()
    }

    val arraySize1 = size
    val arraySize2 = array.size
    val merged = IntArray(arraySize1 + arraySize2)
    var arrayPosition1 = 0
    var arrayPosition2 = 0
    var mergedPosition = 0

    while (arrayPosition1 < arraySize1 && arrayPosition2 < arraySize2)
        if (get(arrayPosition1) < array[arrayPosition2])
            merged[mergedPosition++] = get(arrayPosition1++)
        else
            merged[mergedPosition++] = array[arrayPosition2++]

    while (arrayPosition1 < arraySize1)
        merged[mergedPosition++] = get(arrayPosition1++)

    while (arrayPosition2 < arraySize2)
        merged[mergedPosition++] = array[arrayPosition2++]

    return merged
}

/**
 * Function extension to check if array is sorted.
 */
private fun IntArray.isSorted() = this.contentEquals(this.sortedArray())

