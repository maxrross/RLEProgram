import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RleProgramTest {
    /*@Test
    public void countRunsTest(){
        byte[] flatData = {1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5};
        int groups = 25; // expected result
        assertEquals(groups, RleProgram.countRuns(flatData));
    }
    @Test
    public void sumByParityTest1(){
        int[] arr = {5,12,8,5,3,11,7,2,3,16,4};
        int[] res = {30,46};
        assertArrayEquals(res, PreWork.sumByParity(arr));
    }
    @Test
    public void getDecodedLengthTest(){
        byte[] rleData = { 3, 15, 6, 4 };
        assertEquals(9, RleProgram.getDecodedLength(rleData));
    }
    @Test
    public void encodeRleTest(){
        byte[] flatData = {4,4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1 };
        byte[] res = {2,4,15,1,15,1,5,1};
        assertArrayEquals(res, RleProgram.encodeRLE(flatData));
    }
    @Test
    public void toHexStringTest(){
        byte[] data = { 3, 15, 6, 4};
        String result = "3f64";
        assertEquals(result,RleProgram.toHexString(data));
    }
    @Test
    public void decodeRleTest(){
        byte[] data = {2,4,15,1,15,1,5,1,1,8,1,7};
        byte[] res = {4,4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,8,7};
        assertArrayEquals(res, RleProgram.decodeRle(data));
    }
    @Test
    public void stringToDataTest(){
        String input = "3f64";
        byte[] res = { 3, 15, 6, 4};
        assertEquals(res, RleProgram.stringToData(input));
    }
    public static void main(String[] args) {
        System.out.print(Arrays.toString(RleProgram.stringToData("3f64")));
    }*/
    @Test
    public void toRleStringTest(){
        // Ex:toRleString(new byte[] { 15, 15, 6, 4}) yields string "15f:64"
        byte[] input = {15, 15, 6, 4};
        String res = "15f:64";
        assertEquals(res, RleProgram.toRleString(input));
    }
    /*
    @Test
    public void stringToRleTest(){
        // Ex:stringToRle("15f:64") yields byte array { 15, 15, 6, 4}.
        String input = "15f:64";
        byte[] res = { 15, 15, 6, 4};
        assertEquals(res, RleProgram.stringToRle(input));
    }*/
}
