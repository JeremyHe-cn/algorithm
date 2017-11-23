package me.alzz.algorithm

/**
 * algorithm for string
 * Created by Jeremy He on 2017/11/23.
 */

/**
 * 通过 manacher 求最长子回文
 */
fun String.manacher() : String {
    // 对数据预处理，转成奇数长度
    val CHAR_BEGIN = '$'
    val CHAR_END = '`'
    val CHAR_DELIMITER = '#'

    val chars: CharArray = kotlin.CharArray(length * 2 + 3)
    chars[0] = CHAR_BEGIN
    chars[1] = CHAR_DELIMITER
    chars[length * 2 + 2] = CHAR_END

    for (i in 0 until length) {
        chars[i * 2 + 2] = this[i]
        chars[i * 2 + 3] = CHAR_DELIMITER
    }

    var maxLenIndex = 0
    var maxLen = 0

    // 回文对称中心
    var id = 0
    // 以 id 为中心的最远的 i 值
    var mi = 0

    val p = IntArray(chars.size)
    for (i in 1 until chars.size - 1) {
        // 利用对称性找到以当前字符为中心的最小回文长度
        if (i < mi) {
            p[i] = minOf(p[2 * id - i], mi - i)
        } else {
            p[i] = 1
        }

        // 继续查找是否可能有更长
        while (chars[i - p[i]] == chars[i + p[i]]) {
            p[i]++
        }

        // 更新涉及到的最远的坐标
        if (mi < i + p[i]) {
            id = i
            mi = i + p[i]
        }

        // 记录最大回文长度的字符下标
        if (maxLen < p[i]) {
            maxLen = p[i]
            maxLenIndex = i
        }
    }

    // 取出字符串
    var str = ""
    val left = maxLenIndex - (maxLen - 1)
    val right = maxLenIndex + (maxLen - 1)
    val DEFINE_CHAR = listOf(CHAR_BEGIN, CHAR_END, CHAR_DELIMITER)
    for (i in left..right) {
        if (chars[i] !in DEFINE_CHAR) {
            str += chars[i]
        }
    }

    return str
}
