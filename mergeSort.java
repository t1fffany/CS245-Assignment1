import java.util.ArrayList;
public class mergeSort {
    private ArrayList<Actor> inputArray;

    /**
     *
     * @return returns the sorted arrayList
     */
    public ArrayList<Actor> getSortedArray() {
        return inputArray;
    }

    /**
     *
     * @param inputArray the arrayList we want to sort
     */
    public mergeSort(ArrayList<Actor> inputArray){
        this.inputArray = inputArray;
    }


    public void sortGivenArray(){
        divide(0, this.inputArray.size()-1);
    }

    /**
     *
     * @param startIndex the left-most index of arrayList
     * @param endIndex the right-most index of arrayList
     */
    public void divide(int startIndex,int endIndex){

        //Divide till you breakdown your list to single element
        if(startIndex < endIndex && (endIndex - startIndex)>=1){
            int mid = (endIndex + startIndex)/2;
            divide(startIndex, mid);
            divide(mid+1, endIndex);

            //merging Sorted array produce above into one sorted array
            merger(startIndex,mid,endIndex);
        }
    }

    /**
     *
     * @param startIndex the left-most index of ArrayList
     * @param midIndex the midPoint of arrayList
     * @param endIndex the right-most index of ArrayList
     */
    public void merger(int startIndex,int midIndex,int endIndex){

        //Below is the mergedarray that will be sorted array Array[i-midIndex] , Array[(midIndex+1)-endIndex]
        ArrayList<Actor> mergedSortedArray = new ArrayList<Actor>();

        int leftIndex = startIndex;
        int rightIndex = midIndex+1;

        while(leftIndex<=midIndex && rightIndex<=endIndex){
            if(inputArray.get(leftIndex).getName().compareTo(inputArray.get(rightIndex).getName()) <= 0){
                mergedSortedArray.add(inputArray.get(leftIndex));
                leftIndex++;
            }else{
                mergedSortedArray.add(inputArray.get(rightIndex));
                rightIndex++;
            }
        }

        //One of the other while loops will run
        while(leftIndex<=midIndex){
            mergedSortedArray.add(inputArray.get(leftIndex));
            leftIndex++;
        }

        while(rightIndex<=endIndex){
            mergedSortedArray.add(inputArray.get(rightIndex));
            rightIndex++;
        }

        int i = 0;
        int j = startIndex;

        //Sets the sorted array to initial, inputArray
        while(i < mergedSortedArray.size()){
            inputArray.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }

}
