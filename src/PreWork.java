public class PreWork {

    // 1. sum by parity
    // input: {5,12,8,5,3,11,7,2,3,16,4}
    // output: {30, 46}
    public static int[] sumByParity(int[] arr){
        int[] res = new int [2];
        for (int i=0; i < arr.length; i++) {
            if (i % 2 == 0) //even indices in array
                res[0] +=arr[i];
            else // odd indices
                res[1] += arr[i];
        }
        return res;
    }

    // 2. input: {3,2,5,4,4,4,5,5,5}, {3,2,5,4,4,4,5,5,5,5}
    //    output: false, true
    // {3,3,3,3,2,1,5,5,6,6,6,} => true
    // {3,3,3,2,1,5,5,6,6,6} => false
    public static boolean consecutiveFours(int[] arr){
        int count = 1;
        for (int i = 0; i < arr.length-1; i++){
            if (arr[i] == arr[i+1]) {
                count++;
            } else {
                count = 1;
            }
            if (count >= 4){
                return true;
            }
        }
        return false;
    }
}
