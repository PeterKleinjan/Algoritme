/**
 * Created by omaric on 25-Jan-15.
 */
import java.util.Scanner;

class BinarySearch
{
    public static void main(String args[])
    {
        int i, first, last, middle, f, search, array[];

        Scanner in = new Scanner(System.in);
        System.out.println("");
        f = in.nextInt();
        array = new int[f];

        System.out.println(" " + f + "");


        for (i = 0; i < f; i++)
            array[i] = in.nextInt();

        System.out.println("");
        search = in.nextInt();

        first  = 0;
        last   = f - 1;
        middle = (first + last)/2;

        while( first <= last )
        {
            if ( array[middle] < search )
                first = middle + 1;
            else if ( array[middle] == search )
            {
                System.out.println(search + " " + (middle + 1) + ".");
                break;
            }
            else
                last = middle - 1;

            middle = (first + last)/2;
        }
        if ( first > last )
            System.out.println(search + " is not present in the list.\n");
    }
}
