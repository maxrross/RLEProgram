import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
public class RleProgram {
    public static String toHexString(byte[] data){
        String result = "";
        //Translates data (RLE or raw) a hexadecimal string (without delimiters).This method can also aid debugging.
        for (int i=0; i<data.length; i++){
            if (data[i]<10){
                result+=data[i];
            } else if (data[i]==10){
                result+="a";
            } else if (data[i]==11){
                result+="b";
            } else if (data[i]==12){
                result+="c";
            } else if (data[i]==13){
                result+="d";
            } else if (data[i]==14){
                result+="e";
            } else if (data[i]==15){
                result+="f";
            }
        }
        return result;
    }

    public static int countRuns(byte[] flatData){
        // Returns number of runs of data in an image data set;double this result for length of encoded (RLE) byte array.
        // Ex:countRuns(new byte[] { 15, 15, 15, 4, 4, 4, 4, 4, 4}) yields integer2.
        int groups = 1;
        int numInRow = 0;
        for (int i=1; i<flatData.length; i++) {
            if (flatData[i] != flatData[i-1]) {
                groups++;
                numInRow = 1;
            } else {
                numInRow++;
            }
            if (numInRow == 15){
                groups++;
                numInRow = 0;
            }
        }
        return groups;
    }

    public static byte[] encodeRle(byte[] flatData){
        //Returns encoding (in RLE) of the raw data passed in;used to generate RLE representation of a data.
        // Ex:encodeRle(new byte[] { 15, 15, 15, 4, 4, 4, 4, 4, 4}) yields byte array { 3, 15, 6, 4}.
        int count = 1;
        int arrayNum = 0;
        byte[] result = new byte[countRuns(flatData)*2];

        for (int j = 0; j<flatData.length-1; j++){
            if (flatData[j] == flatData[j+1]) {
                count++;
            } else {
                result[arrayNum] = (byte) count;
                result[arrayNum+1] = flatData[j];
                arrayNum+=2;
                count = 1;
            }
            if (count == 15){
                result[arrayNum] = (byte) count;
                result[arrayNum+1] = flatData[j];
                arrayNum+=2;
                count = 0;
            }
        }
        result[arrayNum] = (byte) count;
        result[arrayNum+1] = flatData[flatData.length-1];
        return result;
    }

    public static int getDecodedLength(byte[] rleData) {
        // Returns decompressed size RLE data;used to generate flat data from RLE encoding.
        // Ex:getDecodedLength(new byte[] { 3, 15, 6, 4}) yields integer 9.
        int res=0;
        for (int i=0; i < rleData.length; i++) {
            if (i % 2 == 0) //even indices in array
                res+=rleData[i];
        }
        return res;
    }

    public static byte[] decodeRle(byte[] rleData){
        // Returns the decoded data set from RLE encoded data. This decompresses RLE data for use.
        // Ex: decodeRle(new byte[] { 3, 15, 6, 4}) yields byte array { 15, 15, 15, 4, 4, 4, 4, 4, 4 }
        byte[] result = new byte[getDecodedLength(rleData)];
        int count=0;
        int lastNum =0;
        for (int i=0; i<rleData.length; i++){
            if (i%2==0){
                count = rleData[i];
            } else {
                for (int j=0; j<count; j++){
                    result[lastNum]=rleData[i];
                    lastNum++;
                }
            }
        }
        return result;
    }

    public static byte[] stringToData(String dataString){
        // Translates a string in hexadecimal format into byte data (can be raw or RLE).
        // (Inverse of #1) Ex:stringToData("3f64") yields byte array { 3, 15, 6, 4}.
        byte[] result = new byte[dataString.length()];
        //Translates data (RLE or raw) a hexadecimal string (without delimiters).This method can also aid debugging.
        for (int i=0; i<dataString.length(); i++){
            if (Character.isLetter(dataString.charAt(i))==false){
                result[i]= (byte) (dataString.charAt(i)-48);
            } else if (dataString.charAt(i)=='a'){
                result[i]=10;
            } else if (dataString.charAt(i)=='b'){
                result[i]=11;
            } else if (dataString.charAt(i)=='c'){
                result[i]=12;
            } else if (dataString.charAt(i)=='d'){
                result[i]=13;
            } else if (dataString.charAt(i)=='e'){
                result[i]=14;
            } else if (dataString.charAt(i)=='f'){
                result[i]=15;
            }
        }
        return result;
    }

    public static String toRleString(byte[]rleData){
        // Ex:toRleString(new byte[] { 15, 15, 6, 4}) yields string "15f:64"
        String result ="";
        for (int i=0; i<rleData.length; i++){
            if (i%2==1){
                if (rleData[i]<10){
                    result+=rleData[i];
                } else if (rleData[i]==10){
                    result+="a";
                } else if (rleData[i]==11){
                    result+="b";
                } else if (rleData[i]==12){
                    result+="c";
                } else if (rleData[i]==13){
                    result+="d";
                } else if (rleData[i]==14){
                    result+="e";
                } else if (rleData[i]==15){
                    result+="f";
                }
            }
            if (i > 0 && i%2==0) {
                result += ":";
            }
            if (i%2==0) {
                result += rleData[i];
            }
        }
        return result;
    }

    public static byte[]stringToRle(String rleString){
        // Ex:stringToRle("15f:64") yields byte array { 15, 15, 6, 4}.
        //check if each run is 3 char or 2 char before semicolon
        //use split then multiply by 2 to determine length
        String[] a = rleString.split(":");
        int point = 0;
        byte[] result = new byte[a.length*2];
        String twoChars;
        for (int i=0; i<a.length; i++) {
            if (a[i].length() == 3) {
                    if (Character.isLetter(a[i].charAt(0))==false){
                        twoChars = a[i].charAt(0)+""+a[i].charAt(1);
                        result[point]= (byte)Integer.parseInt(twoChars);
                        result[point+1]= (byte)Integer.parseInt(a[i].charAt(2)+"");
                        point++;
                    } else if (a[i].charAt(i)=='a'){
                        result[point]=10;
                    } else if (a[i].charAt(i)=='b'){
                        result[point]=11;
                    } else if (a[i].charAt(i)=='c'){
                        result[point]=12;
                    } else if (a[i].charAt(i)=='d'){
                        result[point]=13;
                    } else if (a[i].charAt(i)=='e'){
                        result[point]=14;
                    } else if (a[i].charAt(i)=='f'){
                        result[point]=15;
                    }
                    point++;
            }
            if (a[i].length() == 2) {
                for (int j=0; j<2; j++) {
                    if (Character.isLetter(a[i].charAt(j)) == false) {
                        String oneChar = a[i].charAt(j) + "";
                        result[point] = (byte) Integer.parseInt(oneChar);
                    } else if (a[i].charAt(j) == 'a') {
                        result[point] = 10;
                    } else if (a[i].charAt(j) == 'b') {
                        result[point] = 11;
                    } else if (a[i].charAt(j) == 'c') {
                        result[point] = 12;
                    } else if (a[i].charAt(j) == 'd') {
                        result[point] = 13;
                    } else if (a[i].charAt(j) == 'e') {
                        result[point] = 14;
                    } else if (a[i].charAt(j) == 'f') {
                        result[point] = 15;
                    }
                    point++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //1. Display Welcome message
        System.out.println("Welcome to the RLE image encoder! \n");
        //2. Display color test with message
        System.out.println("Displaying Spectrum Image:");
        ConsoleGfx.displayImage(ConsoleGfx.testRainbow);

        byte[] imageData = null;

        //3. Display the menu - part A: while loop or if-else chains
        // option 1, option 2, and option 6
        int selection = -1;
        while (selection !=0) {
            System.out.println("\nRLE Menu \n--------");
            System.out.println("0. Exit");
            System.out.println("1. Load File");
            System.out.println("2. Load Test Image");
            System.out.println("3. Read RLE String");
            System.out.println("4. Read RLE Hex String");
            System.out.println("5. Read Data Hex String");
            System.out.println("6. Display Image");
            System.out.println("7. Display RLE String");
            System.out.println("8. Display Hex RLE Data");
            System.out.println("9. Display Hex Flat Data \n");

            //prompt for input
            System.out.print("Select a Menu Option: ");
            selection = scan.nextInt();


            if (selection == 1) {
                //3.1 - option 1
                // ConsoleGfx.loadFile(userInput) and you want to sture the returned byte[] into imageData array
                System.out.print("Enter name of file to load: ");
                String fileName = scan.next();
                imageData = ConsoleGfx.loadFile(fileName);

            } else if (selection == 2) {
                //3.2 - option 2
                // store ConsoleGfx.testImage into the imageData array
                imageData = ConsoleGfx.testImage;
                System.out.println("Test image data loaded.");

            } else if (selection == 6) {
                //3.6 - option 6
                System.out.println("Displaying image...");
                ConsoleGfx.displayImage(imageData);

            } else if (selection == 0) {
                //exits program
                System.exit(0);
            } else if (selection == 3) {
                //decoding rle string by getting string, converting it, then decoding it
                System.out.println("Enter an RLE string to be decoded: ");
                String RleString = scan.next();
                imageData = stringToRle(RleString);
                imageData = decodeRle(imageData);
            } else if (selection == 4) {
                //decodes the hex string
                System.out.print("Enter the hex string holding RLE data: ");
                String rle = scan.next();
                imageData = stringToData(rle);
                imageData = decodeRle(imageData);
            } else if (selection == 5) {
                //turns string to data
                System.out.print("Enter the hex string holding flat data: ");
                String flat = scan.next();
                imageData = stringToData(flat);
            } else if (selection == 7) {
                //displaying rle string
                imageData = encodeRle(imageData);
                String rleString = toRleString(imageData);
                System.out.print("RLE representation: " + rleString);
            } else if (selection == 8) {
                //prints rlehex values
                String rleHex = "";
                byte[] encodeImage = encodeRle(imageData);
                rleHex = toHexString(encodeImage);
                System.out.print("RLE hex values: " + rleHex);
            } else if (selection == 9) {
                //flat hex values printed
                String flatHex  = toHexString(imageData);
                System.out.print("Flat hex values: " + flatHex);
            }

        }
    }
}
