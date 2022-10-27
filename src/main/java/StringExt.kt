import java.util.*

fun main(args: Array<String>) {
    //1. 默认尾部追加
    println("1. 默认尾部追加")
    "你好世界".space(i = 0).print()
    "|".print()
    println()

    //2. 头部前面追加空白
    println("2. 头部前面追加空白")
    "|".print()
    "你好世界".spaceHead(i = 1).print()
    "|".print()
    "你好世界".spaceHead(i = 2).print()
    "|".print()
    println()

    //3. 中间插入空白
    println("3. 中间插入空白")
    "|".print()
    "你好世界".space(i = 1, appendIndex = 1, isBeforeHead = false).print()
    "|".print()
    "你好世界".spaceCenter(i = 2, appendIndex = 1).print()
    "|".print()
    println()

    //4. 尾部追加空白
    println("4. 尾部追加空白")
    "|".print()
    "你好世界".spaceEnd(i = 1).print()
    "|".print()
    "你好世界".spaceEnd(i = 2).print()
    "|".print()
    println()

    println("-----------------------")

    /*"你好世界".substring(0, 3 + 1).println()//endIndex 可以越界
    val text = "你好世界"
    val appendIndex = (2 + 1)
    val headStr = text.substring(0, appendIndex)
    val endStr = text.substring(appendIndex, text.length)
    val centerStr = "abc"
    "$headStr$centerStr$endStr".println()*/

    "|".print()
    appendSpace("你好世界", spaceCount = 1, isBeforeHead = true).print()//头部之前, appendIndex = 0 可加可不加
    "|".print()
    appendSpace("你好世界", appendIndex = "你好世界".length - 1, spaceCount = 1, isBeforeHead = false).print()//尾部追加
    "|".print()
    appendSpace("你好世界", appendIndex = 1, spaceCount = 1, isBeforeHead = false).print()//中间插入
    "|".print()
}

/**
 * 字符串加入空白
 *
 * 中文空格(\u3000 和 &#12288; 效果相同)
 * 1. \u3000 用于代码中动态添加中文空格
 * 2. &#12288; 用于 string.xml
 * 3. https://juejin.cn/post/7128302603431051278/
 *
 * @param text 字符串
 * @param appendIndex 拼接空白的起始位置, 尾部追加时为 text.length-1
 * @param spaceCount 需要拼接的空白数量
 * @param isBeforeHead 仅在头部前面追加空白时为 true , 此时 appendIndex 参数没用到
 */
fun appendSpace(text: String?, appendIndex: Int = 0, spaceCount: Int = 0, isBeforeHead: Boolean = false): String {
    var s: String? = text
    if (s == null) s = ""
    val space = String.format(Locale.getDefault(), format = "%s", "\u3000")
    var isCenterMode = false
    var isCenterSpace = ""
    var i = 0
    while (i < spaceCount) {
        if (isBeforeHead || text.isNullOrBlank()) {//Head
            s = (space + s)
        } else if (appendIndex == (text.length - 1)) {//End
            s += space
        } else {//Center
            if (appendIndex < 0 || appendIndex > text.length - 1) {
                //注: 当索引位置越界,按照尾部追加处理,没有避免抛出异常,可以根据需求改动
                s += space
            } else {
                isCenterMode = true
                isCenterSpace += space
            }
        }
        i += 1
    }
    //字符串中间加入空白
    if (isCenterMode) {
        val realIndex = (appendIndex + 1)
        val head = s?.substring(0, realIndex)
        val end = s?.substring(realIndex, s.length)
        s = "$head$isCenterSpace$end"
    }
    return s ?: ""
}

//默认尾部追加, i 为需要拼接空白的数量
fun String?.space(i: Int = 0, appendIndex: Int = (this?.length ?: 0), isBeforeHead: Boolean = false) =
    appendSpace(text = this, appendIndex = appendIndex, spaceCount = i, isBeforeHead = isBeforeHead)

fun String?.spaceHead(i: Int = 0) = appendSpace(text = this, appendIndex = 0, spaceCount = i, isBeforeHead = true)

fun String?.spaceCenter(i: Int = 0, appendIndex: Int) =
    appendSpace(text = this, appendIndex = appendIndex, spaceCount = i, isBeforeHead = false)

fun String?.spaceEnd(i: Int = 0) =
    appendSpace(text = this, appendIndex = (this?.length ?: 0), spaceCount = i, isBeforeHead = false)


////////////////////////////////////////////////////////////////////////////////////////////////////////
//旧版
//fun appendSpace(text: String?, isAddHead: Boolean = false, spaceCount: Int = 0): String {
//    var s: String? = text
//    if (s == null) s = ""
//    val space = String.format(Locale.getDefault(), format = "%s", "\u3000")
//    var i = 0
//    while (i < spaceCount) {
//        if (isAddHead) {
//            s = (space + s)
//        } else {
//            s += space
//        }
//        i += 1
//    }
//    return s ?: ""
//}

//fun String?.space(i: Int = 0, isAddHead: Boolean = false) =
//    appendSpace(text = this, isAddHead = isAddHead, spaceCount = i)

//Log
fun String?.print() = print(this)
fun String?.println() = println(this)