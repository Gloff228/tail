import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileNotFoundException

class Tests{

    @Test
    fun extracting(){
        extracting(listOf("src/test/input/123.txt"), "src/test/output/output.txt", null, 2)
        assertEquals(File("src/test/output/expectedOutput1.txt").readLines(), File("src/test/output/output.txt").readLines())
        File("src/test/output/output.txt").delete()

        extracting(listOf("src/test/input/numLines.txt", "src/test/input/poem.txt"), "src/test/output/output.txt", 5, null)
        assertEquals(File("src/test/output/expectedOutput2.txt").readLines(), File("src/test/output/output.txt").readLines())
        File("src/test/output/output.txt").delete()

        extracting(listOf("src/test/input/strLines.txt", "src/test/input/123.txt", "src/test/input/numLines.txt"), "src/test/output/output.txt", null, 24)
        assertEquals(File("src/test/output/expectedOutput3.txt").readLines(), File("src/test/output/output.txt").readLines())
        File("src/test/output/output.txt").delete()

        extracting(listOf("src/test/input/numString.txt", "src/test/input/123.txt", "src/test/input/poem.txt"), "src/test/output/output.txt", 10, null)
        assertEquals(File("src/test/output/expectedOutput4.txt").readLines(), File("src/test/output/output.txt").readLines())
        File("src/test/output/output.txt").delete()

        extracting(listOf("src/test/input/numString.txt", "src/test/input/123.txt", "src/test/input/poem.txt"), "src/test/output/output.txt", null, 0)
        assertEquals(File("src/test/output/expectedOutput5.txt").readLines(), File("src/test/output/output.txt").readLines())
        File("src/test/output/output.txt").delete()

        assertThrows(FileNotFoundException::class.java){ extracting(listOf("src/test/input/fileNotFound.txt"), "src/test/output/outputNotFound.txt", null, 12345) }
    }
}

